/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nolanlab.CODEX.segm.segmserver;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.nolanlab.CODEX.controller.RscCodexController;
import org.nolanlab.CODEX.segm.segmclient.SegConfigParam;
import org.nolanlab.CODEX.utils.codexhelper.ExperimentHelper;

import java.io.*;
import java.util.*;

/**
 *
 * @author Nikolay
 */
public class MakeFCS {

    private static File dir;
    private static File fcsDir;
    private static File compDir;
    private static File uncompDir;
    private static ExperimentHelper expHelper = new ExperimentHelper();
    private static String serverHome = "";

    public static void main(String[] args) throws Exception {

        SegConfigParam segParam = new SegConfigParam();

        segParam.setSegmName(args[0]);
        segParam.setRootDir(new File(args[1]));
        segParam.setShowImage(Boolean.parseBoolean(args[2]));
        segParam.setRadius(Integer.parseInt(args[3]));
        segParam.setUse_membrane(Boolean.parseBoolean(args[4]));
        segParam.setMaxCutoff(Double.parseDouble(args[5]));
        segParam.setMinCutoff(Double.parseDouble(args[6]));
        segParam.setRelativeCutoff(Double.parseDouble(args[7]));
        segParam.setNuclearStainChannel(Integer.parseInt(args[8]));
        segParam.setNuclearStainCycle(Integer.parseInt(args[9]));
        segParam.setMembraneStainChannel(Integer.parseInt(args[10]));
        segParam.setMembraneStainCycle(Integer.parseInt(args[11]));
        segParam.setInner_ring_size(Double.parseDouble(args[12]));
        segParam.setCount_puncta(Boolean.parseBoolean(args[13]));
        segParam.setDont_inverse_memb(Boolean.parseBoolean(args[14]));
        segParam.setConcentricCircles(Integer.parseInt(args[15]));
        segParam.setDelaunay_graph(Boolean.parseBoolean(args[16]));
        serverHome = args[17];

        dir = new File(segParam.getRootDir() + File.separator + "segm" + File.separator + segParam.getSegmName());
        fcsDir = new File(dir + File.separator + "FCS");
        compDir = new File(fcsDir + File.separator + "compensated");
        uncompDir = new File(fcsDir + File.separator + "uncompensated");

        if(!dir.exists()) {
            throw new IllegalStateException("Directory does not exist: " + dir.getPath());
        }

        ArrayList<File> concatFiles = new ArrayList<>();

        for (File f : compDir.listFiles(f -> f.getName().startsWith("reg") && (!f.getName().contains("_X")) && (f.getName().toLowerCase().contains("compensated"))  && f.getName().endsWith(".txt"))) {
            concatFiles.add(f);
        }

        for (File f : uncompDir.listFiles(f -> f.getName().startsWith("reg") && (!f.getName().contains("_X")) && (f.getName().toLowerCase().contains("compensated"))  && f.getName().endsWith(".txt"))) {
            concatFiles.add(f);
        }

        System.out.println("Found regions: " + concatFiles.toString());

        for (File reg : concatFiles) {
            processFile(reg, segParam, null);
        }
    }

    public static void processFile(File f, SegConfigParam segParam, String blankCycIDXString) throws IOException, InterruptedException {

        CSVReader csv = new CSVReader(new FileReader(f), '\t');
        Iterator<String[]> it = csv.iterator();

        String[] header = it.next();

        String nuclearStainChannelS = String.valueOf(segParam.getNuclearStainChannel());
        int nuclearStainCycle = segParam.getMembraneStainCycle();

        String[] blankCycIdxS = (blankCycIDXString == null) ? new String[0] : blankCycIDXString.split(",");
        int[] blankCycleIdx = new int[blankCycIdxS.length];
        for (int i = 0; i < blankCycIdxS.length; i++) {
            blankCycleIdx[i] = Integer.parseInt(blankCycIdxS[i]);
        }

        int size_idx = 0;

        for (int i = 0; i < header.length; i++) {
            if (header[i].equals("size")) {
                size_idx = i;
            }
        }

        int offset = size_idx;

        String outPath = f.getAbsolutePath().replaceAll("\\.txt", ".csv");
        File out = new File(outPath);
        CSVWriter outWr = new CSVWriter(new FileWriter(out), ',');

        String[] splitHeader = split(header, 1)[1];

        for (int i = 0; i < offset; i++) {
            splitHeader[i] += ":" + splitHeader[i];
        }

        String[][] chNames = null;
        File chNamesF = new File(dir.getParentFile().getParentFile().getParentFile() + File.separator + "channelNames.txt");
        if (chNamesF.exists()) {
            System.out.println("Found channel names file!");
            List<String[]> chNamesL = new CSVReader(new FileReader(chNamesF), '\t').readAll();
            chNames = chNamesL.toArray(new String[chNamesL.size()][]);
        } else {
            throw new IllegalStateException("channelNames.txt file does not exist! Exiting...");
        }

        if (chNames.length < splitHeader.length - offset) {
            throw new IllegalStateException("ChannelNames file is too short: " + chNames.length + ", expected " + (splitHeader.length - offset));
        }

        for (int i = offset; i < splitHeader.length; i++) {
            splitHeader[i] += ":" + chNames[i - offset][0];
        }

        splitHeader = append(splitHeader, "Fiter1:Profile_Homogeneity");

        //System.out.println(Arrays.toString(splitHeader));
        outWr.writeNext(splitHeader);

        while (it.hasNext()) {
            String[] l = it.next();
            if (l[l.length - 1].isEmpty()) {
                l = Arrays.copyOf(l, l.length - 1);
            }
            double size = getSize(l, offset);

            String[] l2 = split(l, 1)[1];

            double sum = 0;
            double sumsq = 0;
            for (int k = offset; k < l.length; k++) {
                try {
                    double d = Double.parseDouble(l[k]);
                    sum += d;
                    sumsq += d * d;
                } catch (NumberFormatException e) {
                    System.err.println("Corrupt number: " + (l[k]) + "\nRow length" + l.length + "\nheader len" + header.length + "\nindex:" + k + "\nString: " + Arrays.toString(l));
                    e.printStackTrace();
                }
            }
            l2 = append(l2, String.valueOf(sum / Math.sqrt(sumsq)));
            outWr.writeNext(l2);
        }

        outWr.flush();
        outWr.close();
        System.out.println("Making FCS command:");
        String s  = "-InputFile:\"" + outPath + "\"";
        System.out.println(s);

        String libConfig = serverHome + File.separator + "lib";
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", libConfig + File.separator + "csv2fcs.jar", s);
        Process p = pb.start();
        pb.redirectErrorStream(true);
        System.out.println("Starting process: " + pb.command().toString());
        expHelper.waitAndPrint(p);

        System.out.println("MakeFCS done");
    }

    private static double[] getVecForCycle(String[] line, int offset, int numReadoutChannels, int cycle) {
        double[] out = new double[numReadoutChannels];
        int idx = ((cycle - 1) * numReadoutChannels) + offset;
        for (int i = 0; i < numReadoutChannels; i++) {
            out[i] = Double.parseDouble(line[idx + i]);
        }
        return out;
    }

    private static double getSize(String[] line, int offset) {
        return Double.parseDouble(line[offset - 1]);
    }

    private static String[] append(String[] src, String s) {
        String[] out = Arrays.copyOf(src, src.length + 1);
        out[src.length] = s;
        return out;
    }

    private static String[][] split(String[] src, int idx) {
        String[][] out = new String[2][];
        out[0] = Arrays.copyOf(src, idx);
        out[1] = Arrays.copyOfRange(src, idx, src.length);
        return out;
    }

}
