/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nolanlab.CODEX.driffta;


import ij.IJ;
import ij.ImagePlus;
import ij.plugin.Concatenator;
import ij.plugin.MontageMaker;
import org.nolanlab.CODEX.controller.RscCodexController;
import org.nolanlab.CODEX.utils.TileObject;
import org.nolanlab.CODEX.utils.codexhelper.BestFocusHelper;
import org.nolanlab.CODEX.utils.logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nikolay Samusik
 */
public class MakeMontage {

    private static File[] seqFiles;

    public static void createMontages(String dirTiles, String dirStitched, int factor) throws Exception {
        File mkMonDir = new File(dirStitched);
        File tilesDir = new File(dirTiles);
        mkMonDir.mkdirs();
        tilesDir.mkdirs();
        BestFocusHelper bfHelper = new BestFocusHelper();
        File bfFile = new File(tilesDir + File.separator + "bestFocus.json");
        Map<String, Integer> tileVsBf = bfHelper.load(bfFile);
        runMontageAlgo(tilesDir, mkMonDir, tileVsBf, factor);
    }

    public static void createMontages(String user, String expName, String fc) throws Exception {
        String serverConfig = RscCodexController.getServerHomeDir() + File.separator + "data";
        String mkMonLoc = serverConfig + File.separator + user + File.separator + expName +File.separator + "processed" + File.separator + "stitched";
        int factor = Integer.parseInt(fc);

        File mkMonDir = new File(mkMonLoc);
        if(!mkMonDir.exists()) {
            mkMonDir.mkdir();
        }
        File tilesDir = new File(mkMonDir.getParentFile() + File.separator + "tiles");
        createMontages(tilesDir.getAbsolutePath(),mkMonDir.getAbsolutePath(),factor);
    }

    public static void runMontageAlgo(File tilesDir, File mkMonDir, Map<String, Integer> tileVsBf, int factor) throws Exception {
        logger.print("Creating tileMap.txt file...");
        createTileMap(tilesDir);
        for (int reg = 1; reg < 1000; reg++) {
            logger.print("Stitching region#"+reg);

            String regString = String.format("reg%03d",reg);

            File [] tileFolders  = tilesDir.listFiles(f->f.getName().startsWith(regString));

            Arrays.sort(tileFolders, (o1, o2) -> {
                int [] xy1 = extractXYCoord(o1);
                int [] xy2 = extractXYCoord(o2);
                int ydiff = xy1[1]-xy2[1];
                int xdiff = xy1[0]-xy2[0];

                if(ydiff==0) return xdiff;
                return ydiff;
            });

            if(tileFolders.length==0){
                break;
            }

            int maxX = 0;
            int maxY = 0;

            for (int i = 0; i < tileFolders.length; i++) {
                int[] xy = extractXYCoord(tileFolders[i]);
                maxX = Math.max(xy[0],maxX);;
                maxY = Math.max(xy[1],maxY);;
            }

            File [][] FolderTIFFs= new File[tileFolders.length][];

            for (int i = 0; i < tileFolders.length; i++) {
                final int bestFocZ = tileVsBf.get(tileFolders[i].getName());
                FolderTIFFs[i] = tileFolders[i].listFiles(f->f.getName().endsWith(".tif") && f.getName().toLowerCase().contains(String.format("_z%03d",bestFocZ)));
                Arrays.sort(FolderTIFFs[i], (o1, o2) -> o1.getName().compareTo(o2.getName()));
                if(i>0){
                    if(FolderTIFFs[i].length!=FolderTIFFs[0].length){
                        throw new IllegalStateException("Folder "+ tileFolders[i]+ "contains a wrong num of images for z-plane" + bestFocZ +", numImg = " +FolderTIFFs[i].length +", expected based on the fist" +
                                "folder = " + FolderTIFFs[0].length);
                    }
                }
            }

            int stackSize = FolderTIFFs[0].length;

            int numTiles = tileFolders.length;


            MontageMaker mm = new MontageMaker();
            for (int i = 0; i < stackSize; i++) {
                ImagePlus singlePlane[] = new ImagePlus[numTiles];
                System.out.println("Making montage for plane#"+i);
                for (int j = 0; j < numTiles; j++) {
                    singlePlane[j] = IJ.openImage(FolderTIFFs[j][i].getAbsolutePath());
                    if(singlePlane[j]==null){
                        throw new IllegalStateException("tile image is mull:"+FolderTIFFs[j][i].getAbsolutePath());
                    }
                }
                ImagePlus stack = new Concatenator().concatenate(singlePlane,false);
                ImagePlus out = mm.makeMontage2(stack, maxX,maxY,1.0/factor,1,stackSize,1,0,false);
                String title = singlePlane[0].getTitle();
                IJ.save(out, mkMonDir+File.separator+String.format("reg%03d",reg)+File.separator+"montage_"+regString+title.substring(title.indexOf("_t"),title.indexOf("_t")+5)+title.substring(title.indexOf("_c"),title.indexOf("_c")+5));
            }
        }

    }

    public static void createTileMap(File tilesDir) throws IOException{
        LinkedList<TileObject> tiles = new LinkedList<>();

        File tm =new File(tilesDir.getParent()+ File.separator + "tileMap.txt");
        if(tm.exists()){
            logger.print("tile map exists, skipping");
            return;
        }

        File [] tileDirs = tilesDir.listFiles(f->f.isDirectory()&&f.getName().startsWith("reg"));

        Arrays.sort(tileDirs,(o1,o2)->o1.getName().compareTo(o2.getName()));
        logger.print("enumerating tiles");

        for(File imgSeqDir : tileDirs) {
           for(File f: imgSeqDir.listFiles(f2->f2.getName().endsWith(".tif"))) {
                   SimpleImageInfo imageInfo = new SimpleImageInfo(f);
                   System.out.println("MIME type : " + imageInfo.getMimeType() + " width : " + imageInfo.getWidth() + " height : " + imageInfo.getHeight());
                   TileObject aTile = new TileObject(imageInfo.getWidth(),imageInfo.getHeight(), f.getName());
                   tiles.add(aTile);
                   break;
            }
        }

        int xPos = 0;
        int yPos = 0;

        if(tiles == null || tiles.isEmpty()) {
            throw new IllegalStateException("tileMap is empty!");
        }
        TileObject firstTile = tiles.getFirst();

        List<Integer> xTracker = new LinkedList<>();
        List<Integer> refList = new LinkedList<>();

        try (PrintWriter bwTileMap = new PrintWriter(tilesDir.getParent()+ File.separator + "tileMap.txt")) {
            System.out.println("Writing tileMap.txt");
            if(tilesDir == null) {
                throw new IOException("Output directory to store tileMap.txt not found!");
            }

            bwTileMap.write("RegionNumber\tTileX\tTileY\tXposition\tYposition");
            bwTileMap.println();
            for (TileObject currentTile : tiles) {
                if (firstTile.getRegionNumber() != currentTile.getRegionNumber()) {
                    xPos = 0;
                    yPos = 0;
                    firstTile = currentTile;
                    xTracker = new LinkedList<>();
                    refList = new LinkedList<>();
                }

                bwTileMap.write("" + currentTile.getRegionNumber() + "\t");
                bwTileMap.write("" + currentTile.getXNumber() + "\t");
                bwTileMap.write("" + currentTile.getYNumber() + "\t");
                if (firstTile.getXNumber() == currentTile.getXNumber()) {
                    if (refList.isEmpty()) {
                        bwTileMap.write("" + xPos + "\t");
                        bwTileMap.write("" + yPos);
                        bwTileMap.println();
                    } else {
                        xPos = 0;
                        xPos += refList.get(currentTile.getYNumber() - 1);
                        bwTileMap.write("" + xPos + "\t");
                        bwTileMap.write("" + yPos);
                        bwTileMap.println();
                    }
                    xTracker.add(currentTile.getWidth());
                    yPos += currentTile.getHeight();
                } else if (firstTile.getXNumber() != currentTile.getXNumber()) {
                    if (!refList.isEmpty()) {
                        for (int i = 0; i < refList.size(); i++) {
                            refList.set(i, refList.get(i)+currentTile.getWidth());
                        }
                    }
                    else {
                        for (int i = 0; i < xTracker.size(); i++) {
                            refList.add(xTracker.get(i));
                        }
                    }
                    xTracker.clear();
                    xTracker.add(currentTile.getWidth());
                    xPos = 0;
                    xPos += refList.get(currentTile.getYNumber() - 1);
                    yPos = 0;
                    bwTileMap.write("" + xPos + "\t");
                    bwTileMap.write("" + yPos);
                    bwTileMap.println();
                    yPos += currentTile.getHeight();
                }
                firstTile = currentTile;
            }
            bwTileMap.flush();
        } catch (IOException e) {
            logger.showException(e);
        }
    }

    public static int[] extractXYCoord(File f) {
        String[] s = f.getName().split("[_\\.]");
        int[] ret = new int[]{Integer.parseInt(s[1].substring(1)), Integer.parseInt(s[2].substring(1))};
        return ret;
    }
}
