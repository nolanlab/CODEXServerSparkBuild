package org.nolanlab.CODEX.viewer.viewerclient.i5d;

import ij.ImagePlus;
import ij.Macro;
import ij.VirtualStack;
import ij.plugin.FolderOpener;
import ij.plugin.HyperStackConverter;
import org.nolanlab.CODEX.viewer.viewerclient.ExperimentWrapper;
import java.util.Arrays;

public class CODEXImageOpener {

    public ExperimentWrapper experimentWrapper;

    public CODEXImageOpener(ExperimentWrapper experimentWrapper){
        this.experimentWrapper = experimentWrapper;
    }

    public MultiCompositeImage openMontage(int region){
        FolderOpener fo = new FolderOpener();
        Macro.setOptions("sort use");
        fo.openAsVirtualStack(true);

        ImagePlus img = fo.openFolder(experimentWrapper.getMontageFolder(region).getAbsolutePath());

        VirtualStack vs = (VirtualStack)img.getStack();

        String [] fileNames = Arrays.stream(experimentWrapper.getMontageFolder(region).listFiles(ff->ff.getName().contains(".tif"))).map(ff->ff.getName()).toArray(String[]::new);

        String [] copyFileNames = Arrays.copyOf(fileNames,fileNames.length);

        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i]=fileNames[i]+"_i"+String.format("%06d",i+1);
        }

        int idxZ = fileNames[0].toLowerCase().indexOf("_z");
        int idxT = fileNames[0].toLowerCase().indexOf("_t");
        int idxC = fileNames[0].toLowerCase().indexOf("_c");

        //reordering to Z, C, T (which will give TCZ of the hyperstack)
        Arrays.sort(fileNames, (o1, o2) -> {
            String o1r = (idxZ>0?o1.substring(idxZ,idxZ+5):"noZ")+o1.substring(idxT,idxT+5)+o1.substring(idxC,idxC+5);
            String o2r = (idxZ>0?o2.substring(idxZ,idxZ+5):"noZ")+o2.substring(idxT,idxT+5)+o2.substring(idxC,idxC+5);
            return  o1r.compareTo(o2r);
        });

        for (String f2 : fileNames) {
            System.out.println(f2);
        }

        img.setStack(vs.sortDicom(fileNames,copyFileNames,6));


        //img = HyperStackConverter.toHyperStack(img,21,1,1,"TCZ","composite");

        return new MultiCompositeImage("Stitched Image: user=Yury, exp=thymus, reg=1",img.getStack(), img.getNChannels()*img.getNFrames(),img.getNSlices(),0);
    }

}
