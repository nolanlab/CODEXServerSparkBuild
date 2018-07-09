package org.nolanlab.CODEX.controller;

import com.google.gson.Gson;
import ij.IJ;
import org.nolanlab.CODEX.clustering.clsserver.ClusterConfig;
import org.nolanlab.CODEX.gating.gatingserver.*;
import org.nolanlab.CODEX.segm.segmserver.RunSegm;
import org.nolanlab.CODEX.utils.codexhelper.ExperimentHelper;
import org.nolanlab.CODEX.utils.codexhelper.GatingHelper;
import org.nolanlab.CODEX.utils.logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.nolanlab.CODEX.driffta.Driffta.log;
import static spark.Spark.*;

public class RscCodexController {

    public static String serverHomeDir;
    private static RunSegm rs = new RunSegm();
    public static List<String> XYnames = new ArrayList<>();
    public static ExperimentHelper expHelper = new ExperimentHelper();

    public static String getDataHomeDir() {
        return serverHomeDir+File.separator+"data";
    }

    public static String getServerHomeDir() {
        return serverHomeDir;
    }

    public static void setServerHomeDir(String dir) {
        serverHomeDir = dir;
    }



    public static void main(String[] args) {

        setServerHomeDir(args[0]);
        staticFiles.location("/public");
        staticFiles.externalLocation(args[0] + File.separator + "cache");
        staticFiles.externalLocation(getDataHomeDir());
        staticFiles.expireTime(60);
        init();
        Gson gson = new Gson();

        //Common
        get("/getUserList", "application/json", (request, response) -> {
            List<String> users = Arrays.asList(new File(getDataHomeDir()).listFiles(f->f.isDirectory())).stream().map(f->f.getName()).collect(Collectors.toList());
            return users;
        }, gson::toJson);

        get("/getExperimentList", "application/json",(request, response) -> {
            String user = (request.queryParams("user"));
            List<String> exp = Arrays.asList(new File(getDataHomeDir()+File.separator+user).listFiles(f->f.isDirectory())).stream().map(f->f.getName()).collect(Collectors.toList());
            return exp;
        }, gson::toJson);

        get("/getExperiment","application/json", (request, response) -> {
            String user = (request.queryParams("user"));
            String experiment = request.queryParams("exp");
            try{
                String content = new Scanner(new File(getDataHomeDir()+File.separator+user+File.separator+experiment+"/Experiment.json")).useDelimiter("\\Z").next();
                return content;
            }catch (Exception e){
                e.printStackTrace();
                return e.fillInStackTrace().toString();
            }
        });

        //Uploader
        post("/runDriffta", "application/octet-stream", (request, response) -> {
            String user = request.queryParams("user");
            String expName = request.queryParams("exp");
            String reg = request.queryParams("reg");
            String tile = request.queryParams("tile");

            try {
//                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"C:\\Users\\Nikolay\\IdeaProjects\\CODEXServer_Spark_rebuild\\out\\artifacts\\CODEXServer_Spark_rebuild_jar\\CODEXServer.jar\" org.nolanlab.CODEX.driffta.Driffta \"" + user + "\" \"" + expName + "\" " + reg + " " + tile + " " + serverHomeDir);
                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"CODEXServer.jar\" org.nolanlab.CODEX.driffta.Driffta \"" + user + "\" \"" + expName + "\" " + reg + " " + tile + " " + serverHomeDir);
                pb.redirectErrorStream(true);

                log("Starting driffta in a new process: " + pb.command().toString());
                Process proc = pb.start();

                expHelper.waitAndPrint(proc);
                log("Driffta process done");

                //Driffta.drifftaProcessing(user, expName, reg, tile);
            }
            catch(Exception e) {
                e.printStackTrace();
                return e.fillInStackTrace().toString();
            }

            return "Processed files uploaded at: /" + user + "/" + expName + "/processed";
        });

        post("/makeMontage", "application/octet-stream", (request, response) -> {
            String user = request.queryParams("user");
            String expName = request.queryParams("exp");
            String factor = request.queryParams("fc");

            try {
//                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"C:\\Users\\Nikolay\\IdeaProjects\\CODEXServer_Spark_rebuild\\out\\artifacts\\CODEXServer_Spark_rebuild_jar\\CODEXServer.jar\" org.nolanlab.CODEX.driffta.MakeMontage \"" + user + "\" \"" + expName + "\" " + factor + " " + serverHomeDir);
                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"CODEXServer.jar\" org.nolanlab.CODEX.driffta.MakeMontage \"" + user + "\" \"" + expName + "\" " + factor + " " + serverHomeDir);
                pb.redirectErrorStream(true);

                log("Starting Make montage in a new process: " + pb.command().toString());
                Process proc = pb.start();

                expHelper.waitAndPrint(proc);
                log("Make montage process done");

                //MakeMontage.createMontages(user, expName, factor);
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.fillInStackTrace().toString();
            }

            return "Montages created at: /" + user + "/" + expName + "/processed/stitched";
        });


        //Segmentation
        get("/getProgress", "application/json", (request, response) -> String.valueOf(rs.getProgress()));

        get("/runSegm", "application/json", (request, response) -> {
            String user = request.queryParams("user");
            String exp = request.queryParams("exp");
            String radius = request.queryParams("radius");
            String maxCutOff = request.queryParams("maxCutOff");
            String minCutOff = request.queryParams("minCutOff");
            String relativeCutOff = request.queryParams("relativeCutOff");
            String nucStainChannel = request.queryParams("nucStainChannel");
            String nucStainCycle = request.queryParams("nucStainCycle");
            String membStainChannel = request.queryParams("membStainChannel");
            String membStainCycle = request.queryParams("membStainCycle");
            String segmName = request.queryParams("segmName");

            boolean showImage = false;
            boolean use_membrane = false;

//            SegConfigParam segParam = new SegConfigParam();
            String[] segArgs = new String[17];

            segArgs[0] = segmName;
            segArgs[1] = getDataHomeDir()  + File.separator + user + File.separator + exp + File.separator + "processed";
            segArgs[2] = String.valueOf(showImage);
            segArgs[3] = radius;
            segArgs[4] = String.valueOf(use_membrane);
            segArgs[5] = maxCutOff;
            segArgs[6] = minCutOff;
            segArgs[7] = relativeCutOff;
            segArgs[8] = nucStainChannel;
            segArgs[9] = nucStainCycle;
            segArgs[10] = membStainChannel;
            segArgs[11] = membStainCycle;
            segArgs[12] = String.valueOf(1.0);
            segArgs[13] = String.valueOf(false);
            segArgs[14] = String.valueOf(false);
            segArgs[15] = String.valueOf(0);
            segArgs[16] = String.valueOf(false);

//            File rootDir = new File(getDataHomeDir()  + File.separator + user + File.separator + exp + File.separator + "processed");
//
//            segParam.setSegmName(segmName);
//            segParam.setRootDir(rootDir);
//            segParam.setShowImage(showImage);
//            segParam.setRadius(Integer.parseInt(radius));
//            segParam.setUse_membrane(use_membrane);
//            segParam.setMaxCutoff(Double.parseDouble(maxCutOff));
//            segParam.setMinCutoff(Double.parseDouble(minCutOff));
//            segParam.setRelativeCutoff(Double.parseDouble(relativeCutOff));
//            segParam.setNuclearStainChannel(Integer.parseInt(nucStainChannel));
//            segParam.setNuclearStainCycle(Integer.parseInt(nucStainCycle));
//            segParam.setMembraneStainChannel(Integer.parseInt(membStainChannel));
//            segParam.setMembraneStainCycle(Integer.parseInt(membStainCycle));
//            segParam.setInner_ring_size(1.0);
//            segParam.setCount_puncta(false);
//            segParam.setDont_inverse_memb(false);
//            segParam.setConcentricCircles(0);
//            segParam.setDelaunay_graph(false);

            logger.print("Starting Main Segmentation...");

            try {

//                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"C:\\Users\\Nikolay\\IdeaProjects\\CODEXServer_Spark_rebuild\\out\\artifacts\\CODEXServer_Spark_rebuild_jar\\CODEXServer.jar\" org.nolanlab.CODEX.segm.segmserver.RunSegm "
                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"CODEXServer.jar\" org.nolanlab.CODEX.segm.segmserver.RunSegm "
                        + segArgs[0] + " " + segArgs[1] + " " + segArgs[2] + " " + segArgs[3] + " " + segArgs[4] + " " + segArgs[5] + " " + segArgs[6] + " " + segArgs[6] + " " + segArgs[8] + " "
                        + segArgs[9] + " " + segArgs[10] + " " + segArgs[11] + " " + segArgs[12] + " " + segArgs[13] + " " + segArgs[14] + " " + segArgs[15] + " " + segArgs[16]);
                pb.redirectErrorStream(true);

                log("Starting segmentation in a new process: " + pb.command().toString());
                Process proc = pb.start();

                expHelper.waitAndPrint(proc);
                log("Segmentation process done");

                //rs.runSeg(segParam);
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.fillInStackTrace().toString();
            }
            logger.print("Main segmentation done");

            logger.print("Starting Concatenate results");
            try {
//                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"C:\\Users\\Nikolay\\IdeaProjects\\CODEXServer_Spark_rebuild\\out\\artifacts\\CODEXServer_Spark_rebuild_jar\\CODEXServer.jar\" org.nolanlab.CODEX.segm.segmserver.ConcatenateResults " + "\"" + segArgs[1] + File.separator +"segm" + File.separator + segArgs[0] + "\"");
                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"CODEXServer.jar\" org.nolanlab.CODEX.segm.segmserver.ConcatenateResults " + "\"" + segArgs[1] + File.separator +"segm" + File.separator + segArgs[0] + "\"");
                pb.redirectErrorStream(true);

                log("Starting ConcatenateResults in a new process: " + pb.command().toString());
                Process proc = pb.start();

                expHelper.waitAndPrint(proc);
                log("ConcatenateResults process done");

                //ConcatenateResults.callConcatenateResults(new File(segParam.getRootDir() + File.separator +"segm" + File.separator + segmName));
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.fillInStackTrace().toString();
            }
            logger.print("ConcatenateResults done");

            try {
//                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"C:\\Users\\Nikolay\\IdeaProjects\\CODEXServer_Spark_rebuild\\out\\artifacts\\CODEXServer_Spark_rebuild_jar\\CODEXServer.jar\" org.nolanlab.CODEX.segm.segmserver.MakeFCS "
                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"CODEXServer.jar\" org.nolanlab.CODEX.segm.segmserver.MakeFCS "
                        + segArgs[0] + " " + segArgs[1] + " " + segArgs[2] + " " + segArgs[3] + " " + segArgs[4] + " " + segArgs[5] + " " + segArgs[6] + " " + segArgs[7] + " " + segArgs[8] + " "
                        + segArgs[9] + " " + segArgs[10] + " " + segArgs[11] + " " + segArgs[12] + " " + segArgs[13] + " " + segArgs[14] + " " + segArgs[15] + " " + segArgs[16] + " " + serverHomeDir);
                pb.redirectErrorStream(true);

                log("Starting MakeFCS in a new process: " + pb.command().toString());
                Process proc = pb.start();

                expHelper.waitAndPrint(proc);
                log("MakeFCS process done");
                //MakeFCS.callMakeFcs(segParam);
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.fillInStackTrace().toString();
            }

            File checkOut = new File(segArgs[1] + File.separator + "segm" + File.separator + segmName + File.separator + "FCS");
            for(File compUncomp : checkOut.listFiles()) {
                if(compUncomp.isDirectory()) {
                    File[] txtFiles = compUncomp.listFiles(t -> t.getName().endsWith(".txt"));
                    File[] fcsFiles = compUncomp.listFiles(f -> f.getName().endsWith(".fcs"));
                    if (txtFiles.length == 0 || fcsFiles.length == 0) {
                        return "Segmentation didn't run properly. Either fcs files/txt files were not created!";
                    }
                }
            }
            //response.redirect("html");
            String res = "Segmentation Ran Successfully! Check the processed folder at: " + user+File.separator+exp+File.separator;
            return res;
        }, gson::toJson);



        //Gating
        get("/getSegTimestampsForGate", "application/json", (request, response) -> {
            GatingConfiguration gatingConfiguration = new GatingConfiguration();
            gatingConfiguration.setUser(request.queryParams("user"));
            gatingConfiguration.setExp(request.queryParams("exp"));
            return gatingConfiguration.listSegmTimestamps();
        }, gson::toJson);

        get("/getXYListForGate", "application/json", (request, response) -> {
            GatingConfiguration gatingConfiguration = new GatingConfiguration();
            gatingConfiguration.setUser(request.queryParams("user"));
            gatingConfiguration.setExp(request.queryParams("exp"));
            gatingConfiguration.setTStamp(request.queryParams("tstamp"));
            XYnames = gatingConfiguration.listCombinedNames();
            return XYnames;
        }, gson::toJson);

        get("/getBiaxialPlot", (request, response) -> {

            String user = request.queryParams("user");
            String exp = request.queryParams("exp");
            String fcs = request.queryParams("FCS");
            String X = request.queryParams("X");
            String Y = request.queryParams("Y");
            String tstamp = request.queryParams("tstamp");

            int x = Integer.parseInt(X);
            int y = Integer.parseInt(Y);

            if(x == -1 || y == -1) {
                throw new IllegalStateException("X or Y cant be -1!");
            }

            System.out.println("drawing the biaxial: X=" +x +  " Y=" + y);


            File fcsDir = new File(getDataHomeDir() + File.separator + user + File.separator + exp + File.separator + "processed" + File.separator + "segm" + File.separator + tstamp + File.separator
                    + "FCS" + File.separator + fcs);

            File[] fcsFile = fcsDir.listFiles(file -> file.getName().endsWith(".fcs"));

            if(fcsFile == null && fcsFile.length!=1){
                throw new IllegalStateException("invalid number of files for region:" + "reg001" + " numFiles"+fcsFile.length);
            }

            GateFilter gf = new GateFilter(fcsFile[0], Config.BIAXIAL_PLOT_SIZE);

            BufferedImage bi = gf.getPlot(x,y, Config.biaxialPlotColorMapper);

            File _imagesDir = new File(new File(getDataHomeDir()).getParent() + File.separator + "cache"+ File.separator + "_images/");
            if(!_imagesDir.exists()) {
                _imagesDir.mkdir();
            }
            File tempFile = File.createTempFile("biaxial", ".png", _imagesDir);

            ImageIO.write(bi,"PNG", tempFile);

            return "/_images/"+tempFile.getName();
        }, gson::toJson);


        get("/saveGate", "application/json",(request, response) -> {
            //[{"x":[78,172,172,78],"y":[52,52,138,138]}]
            String gateName = request.queryParams("gateName");
            String user = request.queryParams("user");
            String exp = request.queryParams("exp");
            String tstamp = request.queryParams("tstamp");
            String X = request.queryParams("X");
            String Y = request.queryParams("Y");
            String fcs = request.queryParams("FCS");
            String coordinates = request.queryParams("coordinates");

            PolygonForGate polygonForGate = new PolygonForGate();
            Polygon p = polygonForGate.createPolygon(coordinates);

            GateParamForJson gateParamForJson = new GateParamForJson();
            gateParamForJson.setGateName(gateName);
            gateParamForJson.setPolygon(p);


            gateParamForJson.setX(XYnames.get(Integer.parseInt(X)));
            gateParamForJson.setY(XYnames.get(Integer.parseInt(Y)));

            GatingHelper gatingHelper = new GatingHelper();
            gatingHelper.saveGateAsJson(user, exp, tstamp, fcs, gateParamForJson);


            File fcsDir = new File(getDataHomeDir() + File.separator + user + File.separator + exp + File.separator + "processed" + File.separator + "segm" + File.separator + tstamp + File.separator
                    + "FCS" + File.separator + fcs);

            File[] fcsFile = fcsDir.listFiles(file -> file.getName().endsWith(".fcs"));

            if(fcsFile == null && fcsFile.length!=1){
                throw new IllegalStateException("invalid number of files for region:" + "reg001" + " numFiles"+fcsFile.length);
            }

            GateFilter gf = new GateFilter(fcsFile[0], Config.BIAXIAL_PLOT_SIZE);

            int xInd = Integer.parseInt(X);
            int yInd = Integer.parseInt(Y);

            gf.setGate(xInd,yInd,p, gateName);

            return gateParamForJson;
        }, gson::toJson);

        get("/getGates", "application/json", (request, response) -> {
            GatingConfiguration gatingConfiguration = new GatingConfiguration();
            gatingConfiguration.setUser(request.queryParams("user"));
            gatingConfiguration.setExp(request.queryParams("exp"));
            gatingConfiguration.setTStamp(request.queryParams("tstamp"));
            gatingConfiguration.setFcs(request.queryParams("FCS"));

            return gatingConfiguration.listGates();
        }, gson::toJson);

        get("/loadGateConfig", "application/json", (request, response) -> {
            GatingConfiguration gatingConfiguration = new GatingConfiguration();
            gatingConfiguration.setUser(request.queryParams("user"));
            gatingConfiguration.setExp(request.queryParams("exp"));
            gatingConfiguration.setTStamp(request.queryParams("tstamp"));
            gatingConfiguration.setFcs(request.queryParams("FCS"));
            String X = request.queryParams("X");
            String Y = request.queryParams("Y");
            if(X != null && Y != null) {
                gatingConfiguration.setSelectedX(XYnames.get(Integer.parseInt(X)));
                gatingConfiguration.setSelectedY(XYnames.get(Integer.parseInt(Y)));
            }
            gatingConfiguration.setSelectedGate(request.queryParams("gate"));

            //change parameter here
            return gatingConfiguration.getGateJson();
        }, gson::toJson);



        //Clustering
        get("/getClusterCols", "application/json", (request, response) -> {
            ClusterConfig clusterConfig = new ClusterConfig();
            clusterConfig.setUser(request.queryParams("user"));
            clusterConfig.setExp(request.queryParams("exp"));
            clusterConfig.setTStamp(request.queryParams("tstamp"));
            return clusterConfig.listCombinedNames();
        }, gson::toJson);

        get("runClustering", "application/json", (request, response) -> {
            String user = request.queryParams("user");
            String exp = request.queryParams("exp");
            String tstamp = request.queryParams("tstamp");
            String fcs = request.queryParams("FCS");
            String parentGate = request.queryParams("parentGate");
            String clustCols = request.queryParams("clustCols");
            String limitEvents = request.queryParams("limitEvents");
            String transformation = request.queryParams("transformation");
            String scalingFactor = request.queryParams("scalingFactor");
            String noiseThreshold = request.queryParams("noiseThreshold");
            String rescale = request.queryParams("rescale");
            String quantile = request.queryParams("quantile");
            String rescaleSeparately = request.queryParams("rescaleSeparately");
            String clusteringName = request.queryParams("clusteringName");

            String[] clusterArgs = new String[11];

            clusterArgs[0] = clusteringName;
            clusterArgs[1] = getDataHomeDir() + File.separator + user + File.separator + exp
                    + File.separator + "processed" + File.separator + "segm" + File.separator + tstamp + File.separator
                    + "FCS" + File.separator + fcs + File.separator + clusteringName;
            clusterArgs[2] = parentGate;
            clusterArgs[3] = clustCols;
            clusterArgs[4] = limitEvents;
            clusterArgs[5] = transformation;
            clusterArgs[6] = scalingFactor;
            clusterArgs[7] = noiseThreshold;
            clusterArgs[8] = rescale;
            clusterArgs[9] = quantile;
            clusterArgs[10] = rescaleSeparately;


//            ClusteringConfigParam clusteringConfigParam = new ClusteringConfigParam();
//
//            File clusteringDir = new File(getDataHomeDir() + File.separator + user + File.separator + exp
//                    + File.separator + "processed" + File.separator + "segm" + File.separator + tstamp + File.separator
//                    + "FCS" + File.separator + fcs + File.separator + clusteringName);
//
//            if(!clusteringDir.exists()) {
//                clusteringDir.mkdir();
//            }
//
//            clusteringConfigParam.setClusteringDir(clusteringDir);
//            clusteringConfigParam.setClusteringName(clusteringName);
//            clusteringConfigParam.setGateName(parentGate);
//            clusteringConfigParam.setClustCols(clustCols);
//            clusteringConfigParam.setLimitEvents(Integer.parseInt(limitEvents));
//            clusteringConfigParam.setTransformation(transformation);
//            clusteringConfigParam.setScalingFactor(Integer.parseInt(scalingFactor));
//            clusteringConfigParam.setNoiseThreshold(Double.parseDouble(noiseThreshold));
//            clusteringConfigParam.setRescale(rescale);
//            clusteringConfigParam.setQuantile(Double.parseDouble(quantile));
//            clusteringConfigParam.setRescaleSeparately(Boolean.parseBoolean(rescaleSeparately));

            logger.print("Starting main Clustering...");

            try {

                //ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"C:\\Users\\Nikolay\\IdeaProjects\\CODEXServer_Spark_rebuild\\out\\artifacts\\CODEXServer_Spark_rebuild_jar\\CODEXServer.jar\" org.nolanlab.CODEX.clustering.clsserver.RunClustering "
                ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "java -Xms5G -Xmx48G -Xmn50m -cp \"CODEXServer.jar\" org.nolanlab.CODEX.clustering.clsserver.RunClustering "
                        + clusterArgs[0] + " " + clusterArgs[1] + " " + clusterArgs[2] + " " + clusterArgs[3] + " " + clusterArgs[4] + " " + clusterArgs[5] + " " + clusterArgs[6] + " "
                        + clusterArgs[7] + " " + clusterArgs[8] + " " + clusterArgs[9] + " " + clusterArgs[10] + " " + serverHomeDir);
                pb.redirectErrorStream(true);

                log("Starting clustering in a new process: " + pb.command().toString());
                Process proc = pb.start();

                expHelper.waitAndPrint(proc);
                log("Clustering process done");

                //rc.runClustering(clusteringConfigParam);
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.fillInStackTrace().toString();
            }
            logger.print("Clustering done");

            File checkOut = new File(clusterArgs[1] + File.separator + "out");

            String res;
            if(!checkOut.exists() || !checkOut.isDirectory()) {
                res = "Out is either not a directory or was not created!";
            }
            else {
                File[] fcsFile = checkOut.listFiles(f -> f.getName().toLowerCase().endsWith(".fcs"));
                if(fcsFile.length > 0) {
                    res = "Clustering Ran Successfully! The out folder with the FCS was created.";
                }
                else {
                    res = "Fcs file was not created for the gate specified!";
                }
            }
            return res;
        }, gson::toJson);



        //Viewer
        get("/getStitchedImageList", "application/octet-stream", (request, response) -> {
            String user = request.queryParams("user");
            String experiment = request.queryParams("exp");
            int region = Integer.parseInt(request.queryParams("reg"));

            final String path = getDataHomeDir()+File.separator+user+File.separator+experiment+"/stitched/"+"reg"+String.format("%03d",region);

            List<String> exp = Arrays.asList(new File(path).listFiles(f->f.getName().endsWith(".tif"))).stream().map(f->f.getAbsolutePath().substring(getDataHomeDir().length()).replaceAll("\\\\",File.separator)).collect(Collectors.toList());

            return  exp;
        }, gson::toJson);

        get("/getImage", "application/octet-stream", (request, response) -> {
            String user = request.queryParams("user");
            String experiment = request.queryParams("exp");
            int region = Integer.parseInt(request.queryParams("reg"));
            int tileX = Integer.parseInt(request.queryParams("tileX"));
            int tileY = Integer.parseInt(request.queryParams("tileY"));
            int channel = Integer.parseInt(request.queryParams("ch"));
            int z = Integer.parseInt(request.queryParams("z"));

            String folderName = "reg"+String.format("%03d",region)+"_X"+String.format("%02d",tileX)+"_Y"+String.format("%02d",tileY);

            String path = getDataHomeDir()+File.separator+user+File.separator+experiment+File.separator+folderName+File.separator+folderName+"_z"+String.format("%03d",z)+"_c"+String.format("%03d",channel)+".tif";

            System.out.println(path);
            return ImageIO.write((RenderedImage)IJ.openImage(path).getProcessor().createImage(), "PNG",response.raw().getOutputStream());
        });
    }
}
