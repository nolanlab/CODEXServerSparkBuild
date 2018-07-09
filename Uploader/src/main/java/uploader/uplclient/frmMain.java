/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uploader.uplclient;


import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.ZProjector;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.nolanlab.CODEX.driffta.Experiment;
import org.nolanlab.CODEX.utils.codexhelper.ExperimentHelper;
import uploader.uplclient.microscope.Microscope;
import uploader.uplclient.microscope.MicroscopeFactory;
import org.nolanlab.CODEX.utils.*;
import org.nolanlab.CODEX.utils.codexhelper.MicroscopeTypeEnum;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import static org.nolanlab.CODEX.driffta.Driffta.loadServerConfigFromJson;


/**
 *
 * @author Nikolay Samusik
 */
public class frmMain extends JFrame {

    private ButtonGroup buttonGroup1;
    private JButton cmdStart;
    private ExperimentView experimentView;
    private JProgressBar prg;
    private JSpinner spinGPU = new JSpinner();
    private JSpinner spinRAM = new JSpinner();
    private JButton cmdStop;
    private JTextField configField = new JTextField(5);
    private ExperimentHelper expHelper;
    private JTextArea textArea = new JTextArea(15,30);
    private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea, "");
    private ArrayList<Process> allProcess = new ArrayList<>();
    private static boolean color = false;

    /**
     * Creates new form frmMain
     */
    public frmMain() {
        expHelper = new ExperimentHelper();
        System.setOut(new PrintStream(taOutputStream));
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        File dir = new File(System.getProperty("user.home"));

        try {
            File in = new File(dir.getCanonicalPath() + File.separator + "config.txt");
            if(in != null && !in.isDirectory() && !in.exists()) {
                numberOfGpuDialog();
            }
        } catch (Exception e) {
            logger.showException(e);
            System.exit(0);
        }

        buttonGroup1 = new ButtonGroup();
        experimentView = new ExperimentView();
        prg = new JProgressBar();
        cmdStart = new JButton();
        cmdStop = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("CODEXuploader");

        try {
            File workingDir = new File(".");
            if(workingDir != null) {
                ImageIcon img = new ImageIcon(workingDir.getCanonicalPath() + File.separator + "codexlogo.png");
                if (img != null) {
                    setIconImage(img.getImage());
                }
            }
        } catch(Exception e) {
            logger.showException(e);
        }

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel newPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        newPanel.setLayout(gridBag);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=2;
        c.weightx =1;
        c.fill  = GridBagConstraints.HORIZONTAL;
        newPanel.add(experimentView, c);

        JScrollPane pane = new JScrollPane(newPanel);
        pane.setLayout(new ScrollPaneLayout());
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(980,200));
        c = new GridBagConstraints();

        c.gridx=0;
        c.gridy=3;
        c.weightx =1;
        c.fill  = GridBagConstraints.HORIZONTAL;
        newPanel.add(scrollPane, c);

        prg.setMaximumSize(new java.awt.Dimension(320, 20));
        prg.setMinimumSize(new java.awt.Dimension(10, 20));
        prg.setName(""); // NOI18N
        prg.setPreferredSize(new java.awt.Dimension(300, 20));
        c = new GridBagConstraints();

        c.gridx=0;
        c.gridy=4;
        c.weightx =0;
        c.fill  = GridBagConstraints.NONE;

        newPanel.add(prg, c);

        String upload = "Process & Upload";

        //default
        cmdStart.setText(upload);

        cmdStart.setAlignmentX(0.5F);
        cmdStart.setAlignmentY(0.0F);
        cmdStart.setHorizontalTextPosition(SwingConstants.CENTER);
        cmdStart.setMaximumSize(new java.awt.Dimension(150, 30));
        cmdStart.setMinimumSize(new java.awt.Dimension(150, 30));
        cmdStart.setPreferredSize(new java.awt.Dimension(150, 30));
        cmdStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    cmdStartActionPerformed(evt);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        //Stop button
        cmdStop.setText("Stop");
        cmdStop.setEnabled(false);
        cmdStop.setAlignmentX(0.5F);
        cmdStop.setAlignmentY(0.0F);
        cmdStop.setHorizontalTextPosition(SwingConstants.CENTER);
        cmdStop.setMaximumSize(new java.awt.Dimension(150, 30));
        cmdStop.setMinimumSize(new java.awt.Dimension(150, 30));
        cmdStop.setPreferredSize(new java.awt.Dimension(150, 30));
        cmdStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStopActionPerformed(evt);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(cmdStart);
        buttonPanel.add(cmdStop);

        c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=5;
        c.weighty = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.fill  = GridBagConstraints.NONE;

        newPanel.add(buttonPanel, c);

        pane.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

        mainPanel.add(pane, BorderLayout.CENTER);
        getContentPane().add(mainPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Mouseevent to open the filechooser option to specify config.txt TMP_SSD_DRIVE content.
     * @param evt
     */
    private void configFieldDirMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDirMouseReleased
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if(jfc.getSelectedFile() != null) {
                configField.setText(jfc.getSelectedFile().getAbsolutePath());
            }
        }
        fireStateChanged();
    }

    private void configFieldDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirActionPerformed
    }

    private void fireStateChanged() {
        PropertyChangeListener[] chl = this.getListeners(PropertyChangeListener.class);
        for (PropertyChangeListener c : chl) {
            c.propertyChange(new PropertyChangeEvent(this, "dir", "...", configField.getText()));
        }
    }

    /*
        Method to create a new dialog box to be input at the start-up of the application, when it is run
        on the machine the first time.
     */
    public void numberOfGpuDialog() {

        JPanel gpuPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        gpuPanel.setLayout(gridBag);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx=0;
        c.gridy=0;
        gpuPanel.add(new JLabel("Number of GPUs: \t"), c);


        c= new GridBagConstraints();
        c.gridx=1;
        c.gridy=0;
        gpuPanel.add(spinGPU, c);
        spinGPU.setMaximumSize(new java.awt.Dimension(3000, 20));
        spinGPU.setMinimumSize(new java.awt.Dimension(60, 20));
        spinGPU.setPreferredSize(new java.awt.Dimension(60, 20));
        spinGPU.setModel(new SpinnerNumberModel(1, 1, 200, 1));

        c= new GridBagConstraints();
        c.gridx=0;
        c.gridy=1;
        gpuPanel.add(new JLabel("\nTMP_SSD_DRIVE: \t"), c);

        c= new GridBagConstraints();
        c.gridx=1;
        c.gridy=1;
        configField.setMaximumSize(new java.awt.Dimension(3000, 20));
        configField.setMinimumSize(new java.awt.Dimension(300, 20));
        configField.setPreferredSize(new java.awt.Dimension(300, 20));
        gpuPanel.add(configField, c);
        configField.setText("...");
        configField.setEnabled(false);
        configField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                configFieldDirMouseReleased(evt);
            }
        });
        configField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configFieldDirActionPerformed(evt);
            }
        });

        c= new GridBagConstraints();
        c.gridx=0;
        c.gridy=2;
        gpuPanel.add(new JLabel("\nMax RAM size: \t"), c);

        c= new GridBagConstraints();
        c.gridx=1;
        c.gridy=2;
        spinRAM.setMaximumSize(new java.awt.Dimension(3000, 20));
        spinRAM.setMinimumSize(new java.awt.Dimension(60, 20));
        spinRAM.setPreferredSize(new java.awt.Dimension(60, 20));
        gpuPanel.add(spinRAM, c);
        spinRAM.setModel(new SpinnerNumberModel(48, 4, 256, 4));

        int result = JOptionPane.showConfirmDialog(null, gpuPanel,
                "Specify configuration", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        else {
            //Add content to config.txt
            try {
                File dir = new File(System.getProperty("user.home"));
                if(configField.getText().equals(null) || configField.getText().equalsIgnoreCase("...")) {
                    JOptionPane.showMessageDialog(this,"Could not save config.txt file, please specify directory for TMP_SSD_DRIVE");
                    System.exit(0);
                }
                if(StringUtils.isBlank(spinGPU.getValue().toString())) {
                    JOptionPane.showMessageDialog(this,"Could not save config.txt file, please enter value for number of GPUs");
                    System.exit(0);
                }
                if(StringUtils.isBlank(spinRAM.getValue().toString())) {
                    JOptionPane.showMessageDialog(this,"Could not save config.txt file, please enter value for RAM size");
                    System.exit(0);
                }
                String str = configField.getText().replaceAll("\\\\", "/");
                List<String> lines = Arrays.asList("TMP_SSD_DRIVE="+str, "numGPU="+spinGPU.getValue(), "maxRAM="+spinRAM.getValue());
                Path file = Paths.get(dir.getCanonicalPath() + File.separator + "config.txt");
                Files.write(file, lines, Charset.forName("UTF-8"));
            }
            catch(IOException e) {
               logger.showException(e);
               JOptionPane.showMessageDialog(this,"Could not save the config.txt file");
               System.exit(0);
            }
        }
    }

    /*
    Replaces tile overlap in percent with pixel value in the exp.json file
     */
    private void replaceTileOverlapInExp(File dir, Experiment exp) {
        if(dir != null) {
            for (File cyc : dir.listFiles()) {
                if (cyc != null && cyc.isDirectory()) {
                    File[] cycFiles = cyc.listFiles(tif->tif != null && !tif.isDirectory() && tif.getName().endsWith(".tif"));
                    ImagePlus imp = IJ.openImage(cycFiles[0].getAbsolutePath());
                    exp.setTile_overlap_X((int)((double)(exp.getTile_overlap_X() *imp.getWidth()/100)));
                    exp.setTile_overlap_Y((int)((double)(exp.getTile_overlap_Y()*imp.getHeight()/100)));
                    break;
                }
            }
        }
    }

    public void copyFileFromSourceToDest(File source, File dest) {
        try {
            FileUtils.copyFileToDirectory(source, dest);
        } catch (IOException e) {
            log(e.getMessage());
        }
    }

    /**
     * Copy the required files to the uploader cache of the server so that driffta can run off of this
     * @param baseDir
     * @param dest
     * @param region
     * @param tile
     * @param exp
     */
    public void copyFileToServer(String baseDir, File dest, int region, int tile, Experiment exp) {
        try {
            if(!dest.exists()) {
                dest.mkdirs();
            }
            copyConfigFiles(baseDir, dest);

            color = exp.getChannel_arrangement().toLowerCase().trim().equals("color");
            final ImagePlus[] stack = new ImagePlus[exp.getNum_cycles() * exp.getNum_z_planes() * exp.getChannel_names().length];
            ExecutorService es = Executors.newWorkStealingPool(Math.min(32, Runtime.getRuntime().availableProcessors() * 2));
            HashSet<Callable<String>> alR = new HashSet<>();

            for (int cycle = exp.getCycle_lower_limit(); cycle <= exp.getCycle_upper_limit(); cycle++) {
                final int cycF = cycle;
                final String sourceDir = baseDir + File.separator + expHelper.getDirName(cycle, region, baseDir, exp);
                for (int chIdx = 0; chIdx < exp.getChannel_names().length; chIdx++) {
                    final int chIdxF = chIdx;
                    final String ch = exp.getChannel_names()[chIdx];
                    for (int zSlice = 1; zSlice <= exp.getNum_z_planes(); zSlice++) {
                        final int lz = zSlice;
                        final String sourceFileName = sourceDir + File.separator + expHelper.getSourceFileName(sourceDir, tile, zSlice, chIdxF, exp);
                        final int idx = ((exp.getNum_z_planes() * exp.getChannel_names().length) * (cycle - exp.getCycle_lower_limit())) + (exp.getChannel_names().length * (zSlice - 1)) + chIdx;

                        if (!new File(sourceFileName).exists()) {
                            if (!expHelper.getDirName(cycF, region, baseDir, exp).startsWith("HandE")) {
                                log("Source file does not exist: " + sourceFileName);
                            }
                            continue;
                        }

                        // final String destFileName = tmpDestDir + File.separator + "Cyc" + cycle + "_reg" + region + "_" + expHelper.getSourceFileName(sourceDir, tile, zSlice, chIdxF, exp);
                        String destFileName = dest.getAbsolutePath() + File.separator + "Cyc" + cycle + "_reg" + region + "_" + expHelper.getSourceFileName(sourceDir, tile, zSlice, chIdxF, exp);
                        final String cmd = "./lib/tiffcp -c none \"" + sourceFileName + "\" \"" + destFileName + "\"";
                        final String cmdLinux = "tiffcp -c none \"" + sourceFileName + "\" \"" + destFileName + "\"";

                        if (new File(destFileName).exists()) {
                            if (new File(destFileName).length() > 10000) {
                                //log("File already exists, skipping: " + destFileName);
                                continue;
                            }
                        }

                        if (!MicroscopeTypeEnum.KEYENCE.toString().equals(exp.getMicroscope().toString())) {
                            alR.add(() -> {
                                Opener o = new Opener();
                                stack[idx] = o.openImage(sourceFileName);
                                if (stack[idx] != null) {
                                    if (color || stack[idx].getStack().getSize() != 1) {
                                        if (!expHelper.getDirName(cycF, region, baseDir, exp).startsWith("HandE")) {
                                            ZProjector zp = new ZProjector();
                                            log("Flattening " + stack[idx].getTitle() + ", nslices" + stack[idx].getNSlices() + "ch=" + stack[idx].getNChannels() + "stacksize=" + stack[idx].getStack().getSize());
                                            zp.setImage(stack[idx]);
                                            zp.setMethod(ZProjector.MAX_METHOD);
                                            zp.doProjection();
                                            stack[idx] = zp.getProjection();
                                        }
                                    }
                                    return "Image opened: " + destFileName;
                                } else {
                                    return "Image opening failed: " + sourceFileName;
                                }
                            });
                        } else {
                            alR.add(() -> {
                                //log("Opening file: " + sourceFileName);
                                File f1 = new File(destFileName);
                                do {
                                    Process p = null;
                                    if (SystemUtils.IS_OS_WINDOWS) {
                                        p = Runtime.getRuntime().exec(cmd);
                                    } else if (SystemUtils.IS_OS_LINUX) {
                                        log("Source: " + sourceFileName);
                                        log("Destination: " + destFileName);
                                        p = Runtime.getRuntime().exec(cmdLinux);
                                        //log("Ran well");
                                    }
                                    if (p != null) {
                                        p.waitFor();
                                        //log(p.getOutputStream().toString());
                                        //log(p.getErrorStream().toString());
                                        //log("Ran here also");
                                    }
                                    if (!f1.exists()) {
                                        log("Copy process finished but the dest file does not exist: " + destFileName + " trying again.");
                                    }
                                } while (!f1.exists());

                                Opener o = new Opener();
                                stack[idx] = o.openImage(destFileName);
                                new File(destFileName).deleteOnExit();

                                if (stack[idx] != null) {
                                    if (color || stack[idx].getStack().getSize() != 1) {
                                        if (!expHelper.getDirName(cycF, region, baseDir, exp).startsWith("HandE")) {
                                            ZProjector zp = new ZProjector();
                                            log("Flattening " + stack[idx].getTitle() + ", nslices" + stack[idx].getNSlices() + "ch=" + stack[idx].getNChannels() + "stacksize=" + stack[idx].getStack().getSize());
                                            zp.setImage(stack[idx]);
                                            zp.setMethod(ZProjector.MAX_METHOD);
                                            zp.doProjection();
                                            stack[idx] = zp.getProjection();
                                        }
                                    }
                                    //stack[idx].setTitle(channelNames[(cycF - 1) * exp.channel_names.length + chIdxF]);
                                    return "Image opened: " + destFileName;
                                } else {
                                    return "Image opening failed: " + sourceFileName;
                                }
                            });
                        }
                    }
                }
            }

            log("Submitting file opening jobs");
            log("Working on it...");

            List<Future<String>> lst = es.invokeAll(alR);
            log("All file opening jobs submitted");

            for (Future<String> future : lst) {
                future.get();
            }

            es.shutdown();
            es.awaitTermination(100000, TimeUnit.HOURS);

        }
        catch (Exception e) {
            log(e.getMessage());
        }
    }

    public Thread cmdStartActionPerformed(java.awt.event.ActionEvent evt) throws Exception {//GEN-FIRST:event_cmdStartActionPerformed
       Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                File dir = new File(experimentView.getPath());

                if(dir == null || dir.getName().equals("...")) {
                    log("Please select an experiment folder and try again!");
                }

                Experiment exp = experimentView.getExperiment();
                replaceTileOverlapInExp(dir, exp);

                String microscopeType = exp != null && exp.getMicroscope() != null ? exp.getMicroscope().toString() : "";
                if(microscopeType == null || microscopeType.equals("")) {
                    JOptionPane.showMessageDialog(null, "Microscope type is invalid");
                }
                Microscope microscope = MicroscopeFactory.getMicroscope(microscopeType);
                //Included a feature to check if the product of region size X and Y is equal to the number of tiles
                File expJSON = null;
                if(microscope.isTilesAProductOfRegionXAndY(dir, experimentView)) {
                    expJSON = new File(dir + File.separator + "Experiment.json");
                    expHelper.saveToFile(exp, expJSON);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Check the values of Region Size X and Y and then try again!");
                    return;
                }

                //Included a feature to check if the channelNames.txt file is present
                if (!experimentView.isChannelNamesPresent(dir)) {
                    JOptionPane.showMessageDialog(null, "channelNames.txt file is not present in the experiment folder. Please check and try again!");
                    return;
                }

                cmdStart.setEnabled(false);
                cmdStop.setEnabled(true);

                log("Verifying names...");

                for (File f : dir.listFiles(file -> file.isDirectory() && file.getName().startsWith("Cyc"))) {
                    String name = f.getName();
                    String[] s = name.split("_");
                    if (s.length > 2) {
                        f.renameTo(new File(dir + File.separator + s[0] + "_" + s[1]));
                    }
                }

                File f = new File(".\\");

                f.getAbsolutePath();

                int totalCount = exp.getRegion_names().length * exp.getRegion_width() * exp.getRegion_height();

                prg.setMaximum(totalCount);

                int currCnt = 1;

                String selectedServer = experimentView.getServerName().getSelectedItem().toString();
                String upC = "codex_server_uploader_cache";

                File uploaderJson = new File("uploaderconfig.json");
                String remoteIp = expHelper.loadIpFromJson(uploaderJson, selectedServer);

                for (int reg : exp.getRegIdx()) {
                    for (int tile = 1; tile <= exp.getRegion_height() * exp.getRegion_width(); tile++) {
                        //serverconfig
                        if(!remoteIp.equals("localhost")) {
                            copyFileToServer(experimentView.getPath(), new File(File.separator + File.separator + remoteIp + File.separator + upC + File.separator + exp.getUserName() + File.separator + exp.getName()), reg, tile, exp);
                        }
                        else {
                            //This is for localhost - Must have serverconfig.json
                            File serverConfigJson = new File("serverconfig.json");
                            if(serverConfigJson == null || !serverConfigJson.exists()) {
                                throw new IllegalStateException("serverconfig.json does not exist to be run on localhost. Please include this and set the uploader cache value in the json and try again!");
                            }
                            else {
                                Map<String, ServerConfig> serverVsConfig = loadServerConfigFromJson(serverConfigJson);
                                for (Map.Entry<String, ServerConfig> hm : serverVsConfig.entrySet()) {
                                    upC = expHelper.loadUploaderCacheFromJson(serverConfigJson, hm.getKey());
                                }
                                copyFileToServer(experimentView.getPath(), new File(upC + File.separator + exp.getUserName() + File.separator + exp.getName()), reg, tile, exp);
                            }
                        }
                        UploaderClient rnDiffta = new UploaderClient("http://" + remoteIp + ":4567", "runDriffta?user="+exp.getUserName()+"&exp="+exp.getName()+"&reg="+String.valueOf(reg)+"&tile="+String.valueOf(tile));
                        log(rnDiffta.getResponse());
                        log("Driffta done");
                        prg.setValue(currCnt++);
                        frmMain.this.repaint();
                    }
                }

                log("Creating montages");
                UploaderClient mkMontage = new UploaderClient("http://" + remoteIp + ":4567", "makeMontage?user="+exp.getUserName()+"&exp="+exp.getName()+"&fc=2");
                log(mkMontage.getResponse());
                log("Make Montage Done");

                log("All processes done!");

            } catch (Exception e) {
                System.out.println(new Error(e));
            }
          }
        });
        th.start();
        return th;
    }

    private void cmdStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdStartActionPerformed
        new Thread(() -> {
            cmdStop.setEnabled(false);
            cmdStart.setEnabled(true);
            prg.setValue(0);
            for(Process proc : allProcess) {
                if(proc != null) {
                    proc.destroy();
                }
            }
            log("All Processes stopped.");
            throw new IllegalStateException("Process stopped.");
        }).start();
    }

    public static void log(String s) {
        System.out.println(s);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Create and display the form
        EventQueue.invokeLater(() -> {
            try {
                frmMain frm = new frmMain();
                frm.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - frm.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frm.getHeight()) / 2, frm.getWidth(), frm.getHeight());
                frm.setVisible(true);
            } catch (Throwable e) {
                logger.showException(e);
            }
        });
    }

    /**
     * Copy the required config files to server
     * @param baseDir
     * @param dest
     */
    public void copyConfigFiles(String baseDir, File dest) {
        //Copy these files to the uploader_cache of server - chNames, exp.json, exposureTimes.txt
        //Copy channelNames.txt to processed folder.
        File chNames = new File(baseDir + File.separator + "channelNames.txt");
        copyFileFromSourceToDest(chNames, dest);

        //Copy Experiment.JSON to processed folder.
        File expJSON = new File(baseDir + File.separator + "experiment.json");
        if(expJSON != null) {
            copyFileFromSourceToDest(expJSON, dest);
        }

        //Copy exposure_times.txt to processed folder.
        File expTimesJSON = new File(baseDir + File.separator + "exposure_times.txt");
        if(expTimesJSON != null) {
            copyFileFromSourceToDest(expTimesJSON, dest);
        }
    }

    public ExperimentView getExperimentView() {
        return experimentView;
    }

    public void setExperimentView(ExperimentView experimentView) {
        this.experimentView = experimentView;
    }

    public JButton getCmdStart() {
        return cmdStart;
    }

    public void setCmdStart(JButton cmdStart) {
        this.cmdStart = cmdStart;
    }

}
