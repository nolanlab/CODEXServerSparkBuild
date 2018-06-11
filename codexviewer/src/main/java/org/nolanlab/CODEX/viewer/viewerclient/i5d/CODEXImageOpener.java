package org.nolanlab.CODEX.viewer.viewerclient.i5d;

import com.google.common.io.LineReader;
import ij.ImagePlus;
import ij.Macro;
import ij.VirtualStack;
import ij.plugin.FolderOpener;
import ij.plugin.HyperStackConverter;
import org.nolanlab.CODEX.utils.logger;
import org.nolanlab.CODEX.viewer.viewerclient.ExperimentWrapper;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CODEXImageOpener {

    public ExperimentWrapper experimentWrapper;

    public CODEXImageOpener(ExperimentWrapper experimentWrapper){
        this.experimentWrapper = experimentWrapper;
    }

    public MultiCompositeImage openMontage(int region) throws Exception{
        FolderOpener fo = new FolderOpener();
        Macro.setOptions("sort use");
        fo.openAsVirtualStack(true);

        ImagePlus img = fo.openFolder(experimentWrapper.getMontageFolder(region).getAbsolutePath());

        VirtualStack vs = (VirtualStack)img.getStack();

        String [] fileNames = Arrays.stream(experimentWrapper.getMontageFolder(region).listFiles(ff->ff.getName().contains(".tif"))).map(ff->ff.getName()).toArray(String[]::new);

        LineReader lr =  new LineReader(new FileReader(experimentWrapper.getChannelNamesFile()));

        List<String> chN = new ArrayList<String>();
        String s;
        while((s=lr.readLine())!=null){
            chN.add(s);
            logger.print(s);
        }

        String [] copyFileNames = chN.toArray(new String[chN.size()]);

       copyFileNames = Arrays.copyOf(copyFileNames, fileNames.length);

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

        String first = fileNames[0];
        String last = fileNames[fileNames.length-1];

        fileNames[0]=last;
        fileNames[fileNames.length-1] = first;

        for (String f2 : fileNames) {
            System.out.println(f2);
        }

        VirtualStack vs2 = (VirtualStack) vs.sortDicom(fileNames,copyFileNames,6);

        logger.print("First label:" + vs2.getSliceLabel(1));
        //img = HyperStackConverter.toHyperStack(img,21,1,1,"TCZ","composite");

        return new MultiCompositeImage("Stitched Image: user=Yury, exp=thymus, reg=1",vs2, img.getNChannels()*img.getNFrames(),img.getNSlices(),0);
    }

}
