package org.nolanlab.CODEX.segm.segmserver;

import org.apache.commons.io.FilenameUtils;
import org.nolanlab.CODEX.utils.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nikolay Samusik
 */
public class ConcatenateResults {

    public static void callConcatenateResults(File dir) throws Exception {

        File fcsDir = new File(dir + File.separator + "FCS");
        File compDir = new File(fcsDir + File.separator + "compensated");
        File uncompDir = new File(fcsDir + File.separator + "uncompensated");

        ArrayList<String> regions = new ArrayList<>();

        for (File f : compDir.listFiles(f -> f.getName().startsWith("reg")&&f.getName().contains("_X")&&f.getName().contains("_Expression")&&(f.getName().endsWith(".txt")||f.getName().endsWith(".csv")))){
            String reg= f.getName().split("_")[0];
            if(!regions.contains(reg)) {
                regions.add(reg);
            }
        }

        for (File f : uncompDir.listFiles(f -> f.getName().startsWith("reg")&&f.getName().contains("_X")&&f.getName().contains("_Expression")&&(f.getName().endsWith(".txt")||f.getName().endsWith(".csv")))){
            String reg= f.getName().split("_")[0];
            if(!regions.contains(reg)) {
                regions.add(reg);
            }
        }
        
        System.out.println("Found regions: " + regions.toString());
        System.out.println("Creating a map to calculate X and Y offsets...");
        LinkedHashMap<TileObject, ArrayList<Integer>> map = createMapFromTileMap(dir);

        for (String reg : regions) {
            
        for (String st : new String[]{"_Uncompensated.txt", "_Compensated.txt"}) {
            String headerLine = null;
            File txtDir;
            if(st.contains("_Uncom")) {
                txtDir = uncompDir;
            }
            else {
                txtDir = compDir;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(txtDir.getAbsolutePath() + File.separator + reg + st));
            for (File f : txtDir.listFiles(f -> f.getName().endsWith(st) && f.getName().startsWith(reg)&&f.getName().contains("_X")&&f.getName().toLowerCase().contains("compensated"))) {
                System.out.println("Concatenating: " + f.getName());
                BufferedReader br = new BufferedReader(new FileReader(f));

                String s = br.readLine();
                if (s == null) {
                    continue;
                }
                if (headerLine == null) {
                    headerLine = s;
                    bw.write("Filename:Filename\t" + headerLine);
                }
                TileObject fileTo = new TileObject();
                fileTo.createTileFromFileNameWithoutImage(FilenameUtils.removeExtension(f.getName()));
                if(map == null || map.isEmpty()) {
                    logger.print("The map created from tileMap is empty. Please check the tileMap.txt file!");
                    return;
                }
                List<Integer> a = map.get(fileTo);
                if(a == null || a.isEmpty()) {
                    logger.print("The offsets are empty or null. Please check the tileMap.txt file!");
                }
                while ((s = br.readLine()) != null) {
                    String[] sArr = s.split("\\t");

                    int x = Integer.parseInt(sArr[2]);
                    int y = Integer.parseInt(sArr[3]);
                    int xOffset = a.get(0) + x;
                    int yOffset = a.get(1) + y;
                    sArr[2] = Integer.toString(xOffset);
                    sArr[3] = Integer.toString(yOffset);

                    s = String.join("\t", sArr);
                    bw.write("\n" + f.getName().split("\\.tif")[0] + "\t" + s);
                }
            }
            bw.flush();
            bw.close();
        }
        }

    }

    /**
     * Compute the offset for each tile to find the position of X and Y in the region
     */
    private static LinkedHashMap<TileObject, ArrayList<Integer>> createMapFromTileMap(File dir) throws IOException {

        BufferedReader br = null;
        String xPos = "";
        String yPos = "";
        LinkedHashMap<TileObject, ArrayList<Integer>> tifWithXposYpos = new LinkedHashMap<>();
        try {
            br = new BufferedReader(new FileReader(dir.getParentFile().getParentFile() + File.separator + "tileMap.txt"));
            boolean columnName= true;
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (columnName) {
                    columnName = false;
                    continue;
                }
                String[] aRow = currentLine.split("\\t");
                if(aRow != null && aRow.length != 0) {
                    String region = aRow[0];
                    int regInt = Integer.parseInt(region);

                    String tileX = aRow[1];
                    int tileXInt = Integer.parseInt(tileX);

                    String tileY = aRow[2];
                    int tileYInt = Integer.parseInt(tileY);

                    xPos = aRow[3];
                    yPos = aRow[4];

                    String fileName = String.format("reg%03d_X%02d_Y%02d", regInt, tileXInt, tileYInt);
                    TileObject to = new TileObject();
                    to = to.createTileFromFileNameWithoutImage(fileName);

                    //List to hold the X offset and Y offset
                    ArrayList<Integer> arr = new ArrayList<>();
                    if(xPos != null && yPos != null) {
                        arr.add(Integer.parseInt(xPos));
                        arr.add(Integer.parseInt(yPos));
                    }

                    if(!tifWithXposYpos.containsKey(to)) {
                        tifWithXposYpos.put(to, arr);
                    }
                }
            }
        }
        catch(IOException e) {
            logger.print(e.getMessage());
        }
        finally {
            br.close();
        }
        return tifWithXposYpos;
    }
}
