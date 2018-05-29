package org.nolanlab.CODEX.viewer.viewerclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ij.*;
import ij.plugin.*;
import org.nolanlab.CODEX.driffta.Experiment;
import org.nolanlab.CODEX.driffta.MakeMontage;
import org.nolanlab.CODEX.utils.TileObject;
import org.nolanlab.CODEX.utils.logger;
import org.nolanlab.CODEX.viewer.viewerclient.i5d.CODEXImageOpener;
import org.nolanlab.CODEX.viewer.viewerclient.i5d.MultiCompositeImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;

public class ClientTest {


    public static void remapFiles(String pathName) throws IOException {
        File f = new File(pathName);
        File bf = new File(f.getPath()+File.separator+"bestFocus");
        File bfJ = new File(f.getPath()+File.separator+"tiles"+ File.separator +"bestFocus.json");

        HashMap<String,Integer> bestFocusMap = new HashMap<>();

        for(File f2: bf.listFiles()) {
            if(f2.getName().endsWith(".tif")){
                if(f2.getName().contains("montage")){

                }else {
                    int zIndex= f2.getName().indexOf("_Z");
                    int z = Integer.parseInt(f2.getName().substring(zIndex+2,zIndex+4));
                    bestFocusMap.put(f2.getName().substring(0,zIndex),z);
                }
            }
        }

        if(!bfJ.exists()){
            try (Writer writer = new FileWriter(bfJ)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(bestFocusMap, writer);
                writer.close();
            }
        }

    }


    public static void main(String[] args) throws Exception{

        /*
        logger.print("file remapping");
        remapFiles("X:\\Nikolay\\U54_Feb15-2018_Cureline_LN2\\processed");
        MakeMontage.createMontages("X:\\Nikolay\\U54_Feb15-2018_Cureline_LN2\\processed\\tiles","X:\\Nikolay\\U54_Feb15-2018_Cureline_LN2\\processed\\stitched", 2);
        System.exit(0);
        */

        File f = new File("X:\\Nikolay\\U54_Feb15-2018_Block2_HNSCC_20x_HiRes_7x9_zpitch900");

        ExperimentWrapper er = new ExperimentWrapper(f);

        CODEXImageOpener io = new CODEXImageOpener(er);

        MultiCompositeImage mci = io.openMontage(1);

        new ImageJ().setVisible(true);

        mci.show();

    }


}
