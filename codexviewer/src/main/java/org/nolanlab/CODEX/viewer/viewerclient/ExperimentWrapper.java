package org.nolanlab.CODEX.viewer.viewerclient;

import org.nolanlab.CODEX.driffta.Experiment;
import org.nolanlab.CODEX.utils.codexhelper.ExperimentHelper;

import java.io.File;

public class ExperimentWrapper {

    public File root;
    public Experiment exp;

    public ExperimentWrapper (File experimentRoot) throws  Exception{
        root = experimentRoot;
        exp = new ExperimentHelper().loadFromJSON(new File(experimentRoot+File.separator+"Experiment.json"));
    }

    public File getRoot() {
        return root;
    }

    public File getTilesBaseFolder (){
        return  new File(root.getAbsolutePath()+ File.separator+"processed" + File.separator+"tiles");
    }

    public File getStitchedBaseFolder (){
        return  new File(root.getAbsolutePath()+ File.separator+"processed" + File.separator+"stitched");
    }

    public File getTileFolder (int reg, int x, int y){
        return  new File(getTilesBaseFolder().getAbsolutePath()+ File.separator+String.format("reg%03d_X%02d_Y%02d",reg,x,y));
    }

    public File getMontageFolder (int reg){
        return  new File(getStitchedBaseFolder().getAbsolutePath() + File.separator+String.format("reg%03d",reg));
    }


}
