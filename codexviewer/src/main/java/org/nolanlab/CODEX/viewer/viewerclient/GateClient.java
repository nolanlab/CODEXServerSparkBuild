/*      */ package org.nolanlab.CODEX.viewer.viewerclient;
/*      */ 
/*      */ import facs.GateBean;
/*      */ import facs.Representation;
/*      */ import facs.Representation.Region;
/*      */ import facs.descriptor.CompensationDescriptor;
/*      */ import facs.descriptor.FlowFileDescriptor;
/*      */ import facs.descriptor.ScaleDescriptor;
/*      */ import facs.gate.CompensationGateSet;
/*      */ import facs.gate.Ellipse;
/*      */ import facs.gate.Gate;
/*      */ import facs.gate.GateSet;
/*      */ import facs.gate.Polygon;
/*      */ import facs.gate.Quad;
/*      */ import facs.gate.Quadrant;
/*      */ import facs.gate.QuadrantGateSet;
/*      */ import facs.gate.Range;
/*      */ import facs.gate.Rectangle;
/*      */ import facs.gate.SpecialGateSet;
/*      */ import facs.gate.Split;
/*      */ import facs.gate.SplitGateSet;
/*      */ import facs.gate.SplitRange;
import org.nolanlab.CODEX.viewer.viewerclient.gate.GateSet;
import org.nolanlab.CODEX.viewer.viewerclient.gate.Quadrant;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.KeyAdapter;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.net.URLEncoder;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JRadioButtonMenuItem;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSpinner;
/*      */ import javax.swing.JSpinner.NumberEditor;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JToggleButton;
/*      */ import javax.swing.JToolBar;
/*      */ import javax.swing.SpinnerModel;
/*      */ import javax.swing.SpinnerNumberModel;
/*      */ import javax.swing.SwingUtilities;
/*      */
/*      */ class GateClient
/*      */   extends JPanel
/*      */   implements ActionListener
/*      */ {
/*      */   static final String CUT_ACTION_COMMAND = "cut";
/*      */   static final String COPY_ACTION_COMMAND = "copy";
/*      */   static final String PASTE_ACTION_COMMAND = "paste";
/*      */   static final String DELETE_ACTION_COMMAND = "delete";
/*      */   static final String UNGATED = "Ungated";
/*      */   static final String USE_FILE_COMPENSATION = "File Compensation";
/*      */   static final String UNCOMPENSATED = "Uncompensated";
/*      */   static final String PANEL_DEFAULT_REPRESENTATION = "Panel Representation";
/*      */   static final int FILE_LIST_LENGTH = 47;
/*      */   static final int LIST_LENGTH = 17;
/*      */   static final String TRUNCATED_STRING = "...";
/*      */   static final int DEFAULT_AXIS_BINS = 256;
/*      */   static final int MAX_FILES_IN_FILE_LIST = 20;
/*      */   boolean debugP;
/*      */   GateApplet applet;
/*      */   GateBean bean;
/*      */   int experimentID;
/*      */   FlowFileDescriptor[] files;
/*      */   ArrayList<String> filenames;
/*      */   CompensationDescriptor[] compensations;
/*      */   ScaleDescriptor[] scales;
/*      */   int axisBins;
/*      */   GateSet gates;
/*      */   GateSet selectedGates;
/*      */   GateSet clipboardGates;
/*      */   ArrayList gateSets;
/*      */   ArrayList representations;
/*      */   FlowFileDescriptor file;
/*      */   Representation representation;
/*      */   GateSet activeGateSet;
/*      */   int activeCompensationID;
/*      */   boolean bypassP;
/*      */   URL plotURL;
/*      */   PlotThread plotThread;
/*      */   Image[][] plotCache;
/*      */   int plotCount;
/*      */   int lastPlotCount;
/*      */   boolean dirtyGateP;
/*      */   boolean dirtyGateSetP;
/*      */   boolean dirtyRepresentationP;
/*      */   JToggleButton ellipse;
/*      */   JToggleButton polygon;
/*      */   JToggleButton rectangle;
/*      */   JToggleButton quadrant;
/*      */   JToggleButton range;
/*      */   JToggleButton split;
/*      */   JToggleButton select;
/*      */   JButton save;
/*      */   JButton reset;
/*      */   JComboBox fileList;
/*      */   JButton firstFile;
/*      */   JButton prevFile;
/*      */   JButton nextFile;
/*      */   JButton lastFile;
/*      */   GateCanvas canvas;
/*      */   JComboBox xAxis;
/*      */   JLabel xAxisScaleLabel;
/*      */   JComboBox yAxis;
/*      */   JLabel yAxisScaleLabel;
/*      */   JLabel status;
/*      */   JList gatesList;
/*      */   GateListModel gatesListModel;
/*      */   GatePanel gatePanel;
/*      */   JComboBox populationList;
/*      */   JButton populationUp;
/*      */   JButton populationDown;
/*      */   JPanel populationPanel;
/*      */   JComboBox compensationList;
/*      */   JButton settings;
/*      */   JButton manage;
/*      */   JButton populationTreeButton;
/*      */   JButton gatingTools;
/*      */   JFrame autoGatingFrame;
/*      */   JButton autoQuadDetails;
/*      */   JButton autoBarcodeDetails;
/*      */   JFrame autoBarcodeFrame;
/*      */   JSpinner autoBarcodeXGroup;
/*      */   JSpinner autoBarcodeYGroup;
/*      */   JButton autoBarcodeOK;
/*      */   JButton autoBarcodeCancel;
/*      */   JFrame gateSetFrame;
/*      */   JTable gateSetTable;
/*      */   GateSetTableModel gateSetModel;
/*      */   JButton create;
/*      */   JButton copy;
/*      */   JButton delete;
/*      */   JButton makeActive;
/*      */   JButton ungate;
/*      */   JFrame plotSettingsFrame;
/*      */   JComboBox representationList;
/*      */   JButton saveRepresentationAs;
/*      */   RepresentationPanel representationPanel;
/*      */   JFrame selectFileFrame;
/*      */   JTable selectFileTable;
/*      */   SelectFileTableModel selectFileModel;
/*      */   JButton selectFileCreate;
/*      */   JButton selectFileCancel;
/*      */   JFrame selectPopulationFrame;
/*      */   JList selectPopulationList;
/*      */   PopulationListModel selectPopulationListModel;
/*      */   JFrame populationTreeFrame;
/*      */   PopulationTreeClient populationTree;
/*      */   
/*      */   GateClient()
/*      */   {
/*  469 */     super(new BorderLayout());
/*      */     
/*  471 */     this.debugP = false;
/*  472 */     this.applet = null;
/*  473 */     this.bean = null;
/*      */     
/*  475 */     this.experimentID = -1;
/*      */     
/*      */ 
/*  478 */     this.files = new FlowFileDescriptor[0];
/*  479 */     this.filenames = new ArrayList();
/*      */     
/*      */ 
/*  482 */     this.compensations = new CompensationDescriptor[0];
/*      */     
/*      */ 
/*  485 */     this.scales = new ScaleDescriptor[0];
/*      */     
/*      */ 
/*  488 */     this.axisBins = 256;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  496 */     this.gates = new GateSet(-1);
/*  497 */     this.gates.setName("List of gates in the client");
/*      */     
/*      */ 
/*  500 */     this.selectedGates = new GateSet(-1);
/*  501 */     this.selectedGates.setName("List of selected gates in the client");
/*      */     
/*      */ 
/*  504 */     this.clipboardGates = new GateSet(-1);
/*  505 */     this.clipboardGates.setName("List of gates on the clipboard in the client");
/*      */     
/*      */ 
/*  508 */     this.gateSets = new ArrayList();
/*      */     
/*      */ 
/*  511 */     this.representations = new ArrayList();
/*      */     
/*      */ 
/*  514 */     this.file = null;
/*      */     
/*      */ 
/*  517 */     this.representation = null;
/*      */     
/*      */ 
/*  520 */     this.activeGateSet = null;
/*      */     
/*      */ 
/*  523 */     this.activeCompensationID = -3;
/*      */     
/*      */ 
/*  526 */     this.bypassP = false;
/*      */     
/*      */ 
/*  529 */     this.plotURL = null;
/*      */     
/*      */ 
/*  532 */     this.plotThread = null;
/*      */     
/*      */ 
/*  535 */     this.plotCache = new Image[0][0];
/*      */     
/*      */ 
/*  538 */     this.plotCount = 0;
/*      */     
/*      */ 
/*  541 */     this.lastPlotCount = -1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  549 */     this.dirtyGateP = false;
/*      */     
/*      */ 
/*  552 */     this.dirtyGateSetP = false;
/*      */     
/*      */ 
/*  555 */     this.dirtyRepresentationP = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   GateClient(GateApplet applet, GateBean bean, URL plotURL)
/*      */   {
/*  566 */     this(applet, bean, plotURL, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   GateClient(GateApplet applet, GateBean bean, URL plotURL, boolean debugP)
/*      */   {
/*  578 */     this();
/*      */     
/*      */ 
/*  581 */     this.debugP = debugP;
/*      */     
/*      */ 
/*  584 */     this.applet = applet;
/*      */     
/*      */ 
/*  587 */     this.plotURL = plotURL;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  593 */     add(createToolbar(), "First");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  611 */     JPanel centerPanel = new JPanel(new BorderLayout());
/*  612 */     centerPanel.add(createFilePanel(), "First");
/*  613 */     centerPanel.add(createViewPanel(), "Before");
/*  614 */     centerPanel.add(createCanvasPanel(), "Center");
/*  615 */     centerPanel.add(createGatePanel(), "After");
/*      */     
/*      */ 
/*  618 */     add(centerPanel, "Center");
/*      */     
/*  620 */     createFrames();
/*      */     
/*  622 */     setBean(bean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JToolBar createToolbar()
/*      */   {
/*  640 */     JToolBar toolbar = new JToolBar("Toolbar");
/*      */     
/*      */ 
/*  643 */     toolbar.setFloatable(false);
/*  644 */     toolbar.setBackground(Color.white);
/*      */     
/*      */ 
/*  647 */     this.select = new JToggleButton("Select", false);
/*  648 */     this.select.setIcon(getImageIcon("select.gif"));
/*  649 */     this.select.setActionCommand("Select");
/*  650 */     this.select.setMnemonic(76);
/*  651 */     this.select.setToolTipText("Select a gate.");
/*  652 */     this.select.addActionListener(this);
/*  653 */     this.select.setFocusable(false);
/*      */     
/*      */ 
/*  656 */     this.ellipse = new JToggleButton("Ellipse", false);
/*  657 */     this.ellipse.setIcon(getImageIcon("ellipse.gif"));
/*  658 */     this.ellipse.setActionCommand("Ellipse");
/*  659 */     this.ellipse.setMnemonic(69);
/*  660 */     this.ellipse.setToolTipText("Draw an ellipse gate.");
/*  661 */     this.ellipse.addActionListener(this);
/*  662 */     this.ellipse.setFocusable(false);
/*      */     
/*      */ 
/*  665 */     this.polygon = new JToggleButton("Polygon", false);
/*  666 */     this.polygon.setIcon(getImageIcon("polygon.gif"));
/*  667 */     this.polygon.setActionCommand("Polygon");
/*  668 */     this.polygon.setMnemonic(80);
/*  669 */     this.polygon.setToolTipText("Draw a polygon gate.");
/*  670 */     this.polygon.addActionListener(this);
/*  671 */     this.polygon.setFocusable(false);
/*      */     
/*      */ 
/*  674 */     this.rectangle = new JToggleButton("Rectangle", false);
/*  675 */     this.rectangle.setIcon(getImageIcon("rect.gif"));
/*  676 */     this.rectangle.setActionCommand("Rectangle");
/*  677 */     this.rectangle.setMnemonic(82);
/*  678 */     this.rectangle.setToolTipText("Draw a rectangle gate.");
/*  679 */     this.rectangle.addActionListener(this);
/*  680 */     this.rectangle.setFocusable(false);
/*      */     
/*      */ 
/*  683 */     this.quadrant = new JToggleButton("Quadrant", false);
/*  684 */     this.quadrant.setIcon(getImageIcon("quadrant.gif"));
/*  685 */     this.quadrant.setActionCommand("Quadrant");
/*  686 */     this.quadrant.setMnemonic(81);
/*  687 */     this.quadrant.setToolTipText("Draw a quadrant gate.");
/*  688 */     this.quadrant.addActionListener(this);
/*  689 */     this.quadrant.setFocusable(false);
/*      */     
/*      */ 
/*  692 */     this.split = new JToggleButton("Split", false);
/*  693 */     this.split.setIcon(getImageIcon("split.gif"));
/*  694 */     this.split.setActionCommand("Split");
/*  695 */     this.split.setMnemonic(84);
/*  696 */     this.split.setToolTipText("Draw a split gate.");
/*  697 */     this.split.addActionListener(this);
/*  698 */     this.split.setFocusable(false);
/*      */     
/*      */ 
/*  701 */     this.range = new JToggleButton("Range", false);
/*  702 */     this.range.setIcon(getImageIcon("range.gif"));
/*  703 */     this.range.setActionCommand("Range");
/*  704 */     this.range.setMnemonic(65);
/*  705 */     this.range.setToolTipText("Draw a range gate.");
/*  706 */     this.range.addActionListener(this);
/*  707 */     this.range.setFocusable(false);
/*      */     
/*      */ 
/*  710 */     ButtonGroup gateButtonGroup = new ButtonGroup();
/*      */     
/*      */ 
/*  713 */     gateButtonGroup.add(this.select);
/*  714 */     gateButtonGroup.add(this.rectangle);
/*  715 */     gateButtonGroup.add(this.ellipse);
/*  716 */     gateButtonGroup.add(this.polygon);
/*  717 */     gateButtonGroup.add(this.quadrant);
/*  718 */     gateButtonGroup.add(this.split);
/*  719 */     gateButtonGroup.add(this.range);
/*      */     
/*      */ 
/*  722 */     this.select.setSelected(true);
/*      */     
/*      */ 
/*  725 */     this.save = new JButton("Save & Return");
/*  726 */     this.save.setIcon(getImageIcon("save.gif"));
/*  727 */     this.save.setActionCommand("Save");
/*  728 */     this.save.setMnemonic(83);
/*  729 */     this.save.setToolTipText("Save all the gates and populations.");
/*  730 */     this.save.addActionListener(this);
/*  731 */     this.save.setFocusable(false);
/*      */     
/*      */ 
/*  734 */     this.reset = new JButton("Reset");
/*  735 */     this.reset.setActionCommand("Reset");
/*  736 */     this.reset.setMnemonic(70);
/*  737 */     this.reset.setToolTipText("Reset all the gates and populations.");
/*  738 */     this.reset.addActionListener(this);
/*  739 */     this.reset.setFocusable(false);
/*      */     
/*      */ 
/*  742 */     toolbar.add(this.save);
/*  743 */     toolbar.addSeparator();
/*  744 */     toolbar.add(this.select);
/*  745 */     toolbar.addSeparator();
/*  746 */     toolbar.add(this.rectangle);
/*  747 */     toolbar.add(this.ellipse);
/*  748 */     toolbar.add(this.polygon);
/*  749 */     toolbar.add(this.quadrant);
/*  750 */     toolbar.addSeparator();
/*  751 */     toolbar.add(this.split);
/*  752 */     toolbar.add(this.range);
/*  753 */     toolbar.addSeparator();
/*  754 */     toolbar.add(this.reset);
/*      */     
/*      */ 
/*      */ 
/*  758 */     return toolbar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JPanel createFilePanel()
/*      */   {
/*  769 */     this.fileList = new JComboBox();
/*  770 */     this.fileList.setToolTipText("The file currently being shown.");
/*  771 */     this.fileList.addActionListener(this);
/*  772 */     this.fileList.setFocusable(false);
/*  773 */     this.fileList.setMaximumRowCount(20);
/*      */     
/*      */ 
/*  776 */     this.firstFile = new JButton("|<");
/*  777 */     this.firstFile.setActionCommand("firstFile");
/*      */     
/*  779 */     this.firstFile.setToolTipText("Show the first file in the list of files.");
/*  780 */     this.firstFile.addActionListener(this);
/*  781 */     this.firstFile.setFocusable(false);
/*      */     
/*      */ 
/*  784 */     this.prevFile = new JButton("<");
/*  785 */     this.prevFile.setActionCommand("prevFile");
/*      */     
/*  787 */     this.prevFile.setToolTipText("Show the previous file in the list of files.");
/*  788 */     this.prevFile.addActionListener(this);
/*  789 */     this.prevFile.setFocusable(false);
/*      */     
/*      */ 
/*  792 */     this.nextFile = new JButton(">");
/*  793 */     this.nextFile.setActionCommand("nextFile");
/*      */     
/*  795 */     this.nextFile.setToolTipText("Show the next file in the list of files.");
/*  796 */     this.nextFile.addActionListener(this);
/*  797 */     this.nextFile.setFocusable(false);
/*      */     
/*      */ 
/*  800 */     this.lastFile = new JButton(">|");
/*  801 */     this.lastFile.setActionCommand("lastFile");
/*      */     
/*  803 */     this.lastFile.setToolTipText("Show the last file in the list of files.");
/*  804 */     this.lastFile.addActionListener(this);
/*  805 */     this.lastFile.setFocusable(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  811 */     JPanel filePanel = new JPanel();
/*  812 */     filePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
/*  813 */     filePanel.setBackground(Color.white);
/*      */     
/*      */ 
/*  816 */     filePanel.add(this.firstFile);
/*      */     
/*      */ 
/*  819 */     filePanel.add(this.prevFile);
/*      */     
/*      */ 
/*  822 */     filePanel.add(new JLabel("File: "));
/*      */     
/*      */ 
/*  825 */     filePanel.add(this.fileList);
/*      */     
/*      */ 
/*  828 */     filePanel.add(this.nextFile);
/*      */     
/*      */ 
/*  831 */     filePanel.add(this.lastFile);
/*      */     
/*      */ 
/*  834 */     return filePanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JPanel createViewPanel()
/*      */   {
/*  845 */     this.populationList = new JComboBox();
/*  846 */     this.populationList.setToolTipText("The population currently being shown.");
/*  847 */     this.populationList.addActionListener(this);
/*  848 */     this.populationList.setFocusable(false);
/*      */     
/*      */ 
/*  851 */     this.populationUp = new JButton("Up");
/*  852 */     this.populationUp.setActionCommand("populationUp");
/*      */     
/*  854 */     this.populationUp.setToolTipText("Population Up.");
/*  855 */     this.populationUp.addActionListener(this);
/*  856 */     this.populationUp.setFocusable(false);
/*      */     
/*      */ 
/*  859 */     this.populationDown = new JButton("Down");
/*  860 */     this.populationDown.setActionCommand("populationDown");
/*      */     
/*  862 */     this.populationDown.setToolTipText("Population Down.");
/*  863 */     this.populationDown.addActionListener(this);
/*  864 */     this.populationDown.setFocusable(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  870 */     JPanel populationListPanel = new JPanel(new FlowLayout());
/*  871 */     populationListPanel.setBackground(Color.white);
/*  872 */     populationListPanel.add(this.populationList);
/*      */     
/*  874 */     JPanel populationListButtonPanel = new JPanel(new FlowLayout());
/*  875 */     populationListButtonPanel.setBackground(Color.white);
/*  876 */     populationListButtonPanel.add(this.populationUp);
/*  877 */     populationListButtonPanel.add(this.populationDown);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  883 */     this.compensationList = new JComboBox();
/*  884 */     this.compensationList.setToolTipText("The compensation currently being shown.");
/*  885 */     this.compensationList.addActionListener(this);
/*  886 */     this.compensationList.setFocusable(false);
/*      */     
/*  888 */     JPanel compensationListPanel = new JPanel(new FlowLayout());
/*  889 */     compensationListPanel.setBackground(Color.white);
/*  890 */     compensationListPanel.add(this.compensationList);
/*      */     
/*      */ 
/*      */ 
/*  894 */     this.settings = new JButton("Plot Settings");
/*  895 */     this.settings.setActionCommand("Settings");
/*  896 */     this.settings.setMnemonic(84);
/*  897 */     this.settings.setToolTipText("Change the plot settings.");
/*  898 */     this.settings.addActionListener(this);
/*  899 */     this.settings.setFocusable(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  907 */     this.manage = new JButton("Manage");
/*  908 */     this.manage.setActionCommand("Select active population");
/*  909 */     this.manage.setMnemonic(77);
/*  910 */     this.manage.setToolTipText("Manage populations.");
/*  911 */     this.manage.addActionListener(this);
/*  912 */     this.manage.setFocusable(false);
/*      */     
/*      */ 
/*  915 */     this.populationTreeButton = new JButton("View");
/*  916 */     this.populationTreeButton.setActionCommand("Population Tree");
/*  917 */     this.populationTreeButton.setMnemonic(86);
/*  918 */     this.populationTreeButton.setToolTipText("View the population tree.");
/*  919 */     this.populationTreeButton.addActionListener(this);
/*  920 */     this.populationTreeButton.setFocusable(false);
/*      */     
/*      */ 
/*  923 */     JPanel populationButtonPanel = new JPanel();
/*  924 */     populationButtonPanel.setBackground(Color.white);
/*  925 */     populationButtonPanel.add(this.manage);
/*  926 */     populationButtonPanel.add(this.populationTreeButton);
/*      */     
/*      */ 
/*  929 */     this.gatingTools = new JButton("Gating Tools");
/*  930 */     this.gatingTools.setActionCommand("Auto Gating Tools");
/*      */     
/*  932 */     this.gatingTools.setToolTipText("Open auto gating tools.");
/*  933 */     this.gatingTools.addActionListener(this);
/*  934 */     this.gatingTools.setFocusable(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  940 */     JPanel settingsPanel = new JPanel();
/*  941 */     settingsPanel.setLayout(new BoxLayout(settingsPanel, 3));
/*  942 */     settingsPanel.setBackground(Color.white);
/*  943 */     settingsPanel.setBorder(BorderFactory.createTitledBorder("View"));
/*      */     
/*  945 */     JPanel tempPanel = new JPanel();
/*  946 */     tempPanel.setBackground(Color.white);
/*  947 */     tempPanel.add(new JLabel("Active Population:"));
/*      */     
/*      */ 
/*  950 */     settingsPanel.add(tempPanel);
/*  951 */     settingsPanel.add(populationListPanel);
/*  952 */     settingsPanel.add(populationListButtonPanel);
/*      */     
/*  954 */     tempPanel = new JPanel();
/*  955 */     tempPanel.setBackground(Color.white);
/*  956 */     tempPanel.add(new JLabel("Active Compensation:"));
/*      */     
/*      */ 
/*  959 */     settingsPanel.add(tempPanel);
/*  960 */     settingsPanel.add(compensationListPanel);
/*      */     
/*  962 */     tempPanel = new JPanel();
/*  963 */     tempPanel.setBackground(Color.white);
/*  964 */     tempPanel.add(this.settings);
/*      */     
/*      */ 
/*  967 */     settingsPanel.add(tempPanel);
/*      */     
/*      */ 
/*  970 */     settingsPanel.add(Box.createRigidArea(new Dimension(175, 0)));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  976 */     JPanel populationPanel = new JPanel();
/*  977 */     populationPanel.setLayout(new BoxLayout(populationPanel, 3));
/*  978 */     populationPanel.setBorder(BorderFactory.createTitledBorder("Populations"));
/*  979 */     populationPanel.setBackground(Color.white);
/*      */     
/*      */ 
/*  982 */     populationPanel.add(populationButtonPanel);
/*      */     
/*      */ 
/*  985 */     populationPanel.add(Box.createRigidArea(new Dimension(175, 0)));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  991 */     JPanel viewPanel = new JPanel(new GridBagLayout());
/*  992 */     viewPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));
/*  993 */     viewPanel.setBackground(Color.white);
/*      */     
/*      */ 
/*  996 */     GridBagConstraints constraints = new GridBagConstraints();
/*      */     
/*      */ 
/*  999 */     constraints.anchor = 19;
/*      */     
/*      */ 
/* 1002 */     constraints.gridx = 0;
/* 1003 */     constraints.gridy = 0;
/* 1004 */     viewPanel.add(settingsPanel, constraints);
/*      */     
/*      */ 
/* 1007 */     constraints.gridx = 0;
/* 1008 */     constraints.gridy = 1;
/* 1009 */     viewPanel.add(populationPanel, constraints);
/*      */     
/* 1011 */     tempPanel = new JPanel();
/* 1012 */     tempPanel.add(this.gatingTools);
/*      */     
/*      */ 
/* 1015 */     constraints.gridx = 0;
/* 1016 */     constraints.gridy = 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1024 */     constraints.fill = 2;
/* 1025 */     viewPanel.add(Box.createRigidArea(new Dimension(0, 25)), constraints);
/*      */     
/*      */ 
/*      */ 
/* 1029 */     return viewPanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JPanel createCanvasPanel()
/*      */   {
/* 1040 */     this.canvas = new GateCanvas(this, GateCanvas.SELECT, getAxisBins(), getAxisBins());
/*      */     
/*      */ 
/* 1043 */     this.canvas.requestFocusInWindow();
/*      */     
/*      */ 
/* 1046 */     this.yAxis = new JComboBox();
/* 1047 */     this.yAxis.setToolTipText("The y channel.");
/* 1048 */     this.yAxis.addActionListener(this);
/* 1049 */     this.yAxis.setFocusable(false);
/*      */     
/*      */ 
/* 1052 */     this.yAxisScaleLabel = new JLabel("Linear");
/*      */     
/*      */ 
/* 1055 */     this.xAxis = new JComboBox();
/* 1056 */     this.xAxis.setToolTipText("The x channel.");
/* 1057 */     this.xAxis.addActionListener(this);
/* 1058 */     this.xAxis.setFocusable(false);
/*      */     
/*      */ 
/* 1061 */     this.xAxisScaleLabel = new JLabel("Linear");
/*      */     
/*      */ 
/* 1064 */     this.status = new JLabel();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1075 */     JPanel canvasPanel = new JPanel();
/* 1076 */     canvasPanel.setLayout(new BoxLayout(canvasPanel, 3));
/* 1077 */     canvasPanel.setBackground(Color.white);
/*      */     
/*      */ 
/* 1080 */     canvasPanel.add(this.canvas);
/*      */     
/*      */ 
/* 1083 */     canvasPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/*      */     
/*      */ 
/* 1086 */     JPanel yAxisPanel = new JPanel(new FlowLayout(1));
/* 1087 */     yAxisPanel.setBackground(Color.white);
/* 1088 */     yAxisPanel.add(Box.createRigidArea(new Dimension(50, 0)));
/* 1089 */     yAxisPanel.add(new JLabel("Y: "));
/* 1090 */     yAxisPanel.add(this.yAxis);
/* 1091 */     yAxisPanel.add(this.yAxisScaleLabel);
/*      */     
/*      */ 
/* 1094 */     canvasPanel.add(yAxisPanel);
/*      */     
/*      */ 
/* 1097 */     JPanel xAxisPanel = new JPanel(new FlowLayout(1));
/* 1098 */     xAxisPanel.setBackground(Color.white);
/* 1099 */     xAxisPanel.add(Box.createRigidArea(new Dimension(50, 0)));
/* 1100 */     xAxisPanel.add(new JLabel("X: "));
/* 1101 */     xAxisPanel.add(this.xAxis);
/* 1102 */     xAxisPanel.add(this.xAxisScaleLabel);
/*      */     
/*      */ 
/* 1105 */     canvasPanel.add(xAxisPanel);
/*      */     
/*      */ 
/* 1108 */     JPanel statusPanel = new JPanel();
/* 1109 */     statusPanel.setBackground(Color.white);
/* 1110 */     statusPanel.add(this.status);
/*      */     
/*      */ 
/* 1113 */     canvasPanel.add(statusPanel);
/*      */     
/*      */ 
/* 1116 */     canvasPanel.add(Box.createVerticalGlue());
/*      */     
/*      */ 
/*      */ 
/* 1120 */     return canvasPanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JPanel createGatePanel()
/*      */   {
/* 1133 */     JPanel gatePanel = new JPanel(new GridBagLayout());
/* 1134 */     gatePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
/* 1135 */     gatePanel.setBackground(Color.white);
/*      */     
/*      */ 
/* 1138 */     GridBagConstraints constraints = new GridBagConstraints();
/*      */     
/*      */ 
/* 1141 */     constraints.fill = 2;
/* 1142 */     constraints.anchor = 19;
/*      */     
/*      */ 
/* 1145 */     constraints.gridx = 0;
/* 1146 */     constraints.gridy = 0;
/* 1147 */     gatePanel.add(createGatesList(), constraints);
/*      */     
/*      */ 
/* 1150 */     constraints.gridx = 0;
/* 1151 */     constraints.gridy = 1;
/* 1152 */     gatePanel.add(createGateUI(), constraints);
/*      */     
/*      */ 
/*      */ 
/* 1156 */     return gatePanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JScrollPane createGatesList()
/*      */   {
/* 1170 */     final JButton fakeShowGateButton = new JButton("Fake Show Gate Button");
/* 1171 */     fakeShowGateButton.setActionCommand("Display Gate");
/* 1172 */     fakeShowGateButton.addActionListener(this);
/*      */     
/*      */ 
/* 1175 */     final JButton fakeDeleteGateButton = new JButton("Fake Delete Gate Button");
/* 1176 */     fakeDeleteGateButton.setActionCommand("Delete Gate");
/* 1177 */     fakeDeleteGateButton.addActionListener(this);
/*      */     
/*      */ 
/* 1180 */     this.gatesListModel = new GateListModel(this);
/*      */     
/*      */ 
/* 1183 */     this.gatesList = new JList(this.gatesListModel);
/* 1184 */     this.gatesList.setToolTipText("The list of gates.");
/* 1185 */     this.gatesList.setSelectionMode(0);
/* 1186 */     this.gatesList.setLayoutOrientation(0);
/*      */     
/*      */ 
/* 1189 */     this.gatesList.addMouseListener(new MouseAdapter() {
/*      */       public void mouseClicked(MouseEvent e) {
/* 1191 */         if (e.getClickCount() == 1)
/*      */         {
/* 1193 */           fakeShowGateButton.doClick();
/*      */         }
/*      */         
/*      */       }
/*      */       
/* 1198 */     });
/* 1199 */     this.gatesList.addKeyListener(new KeyAdapter() {
/*      */       public void keyPressed(KeyEvent e) {
/* 1201 */         if ((e.getKeyCode() == 8) || (e.getKeyCode() == 127))
/*      */         {
/* 1203 */           fakeDeleteGateButton.doClick();
/*      */         }
/*      */         
/*      */       }
/*      */       
/* 1208 */     });
/* 1209 */     JScrollPane gatesListScrollPane = new JScrollPane(this.gatesList);
/* 1210 */     gatesListScrollPane.setBorder(BorderFactory.createTitledBorder("List of gates:"));
/* 1211 */     gatesListScrollPane.setPreferredSize(new Dimension(150, 150));
/*      */     
/*      */ 
/*      */ 
/* 1215 */     return gatesListScrollPane;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JPanel createGateUI()
/*      */   {
/* 1225 */     this.gatePanel = new GatePanel(this, this.debugP);
/*      */     
/*      */ 
/* 1228 */     return this.gatePanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void createFrames()
/*      */   {
/* 1241 */     this.create = new JButton("Create Population");
/* 1242 */     this.create.setActionCommand("createPopulation");
/* 1243 */     this.create.setMnemonic(67);
/* 1244 */     this.create.setToolTipText("Create a new population.");
/* 1245 */     this.create.addActionListener(this);
/* 1246 */     this.create.setFocusable(false);
/*      */     
/*      */ 
/* 1249 */     this.copy = new JButton("Copy Population");
/* 1250 */     this.copy.setActionCommand("copyPopulation");
/*      */     
/* 1252 */     this.copy.setToolTipText("Copy the selected population.");
/* 1253 */     this.copy.addActionListener(this);
/* 1254 */     this.copy.setFocusable(false);
/*      */     
/*      */ 
/* 1257 */     this.delete = new JButton("Delete Population");
/* 1258 */     this.delete.setActionCommand("deletePopulation");
/* 1259 */     this.delete.setMnemonic(68);
/* 1260 */     this.delete.setToolTipText("Delete the selected population.");
/* 1261 */     this.delete.addActionListener(this);
/* 1262 */     this.delete.setFocusable(false);
/*      */     
/*      */ 
/* 1265 */     this.makeActive = new JButton("Make Active");
/* 1266 */     this.makeActive.setActionCommand("Make Active");
/* 1267 */     this.makeActive.setMnemonic(77);
/* 1268 */     this.makeActive.setToolTipText("Makes the selected population active.");
/* 1269 */     this.makeActive.addActionListener(this);
/* 1270 */     this.makeActive.setFocusable(false);
/*      */     
/*      */ 
/* 1273 */     this.ungate = new JButton("Ungate");
/* 1274 */     this.ungate.setActionCommand("Ungated");
/*      */     
/* 1276 */     this.ungate.setToolTipText("Resets the population gating.");
/* 1277 */     this.ungate.addActionListener(this);
/* 1278 */     this.ungate.setFocusable(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1284 */     this.gateSetFrame = new JFrame("Manage Populations");
/* 1285 */     this.gateSetFrame.setTitle("Manage Populations");
/* 1286 */     this.gateSetFrame.setDefaultCloseOperation(1);
/*      */     
/*      */ 
/* 1289 */     this.gateSetModel = new GateSetTableModel(this);
/* 1290 */     this.gateSetTable = new JTable(this.gateSetModel);
/* 1291 */     this.gateSetTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
/* 1292 */     this.gateSetTable.setSelectionMode(0);
/* 1293 */     this.gateSetTable.setAutoResizeMode(0);
/*      */     
/*      */ 
/* 1296 */     JScrollPane gateSetTableScrollPane = new JScrollPane(this.gateSetTable);
/* 1297 */     gateSetTableScrollPane.setAlignmentX(0.0F);
/*      */     
/*      */ 
/* 1300 */     JPanel gateSetSouthPanel = new JPanel();
/* 1301 */     gateSetSouthPanel.add(this.create);
/* 1302 */     gateSetSouthPanel.add(this.copy);
/* 1303 */     gateSetSouthPanel.add(this.delete);
/* 1304 */     gateSetSouthPanel.add(this.makeActive);
/* 1305 */     gateSetSouthPanel.add(this.ungate);
/*      */     
/*      */ 
/* 1308 */     this.gateSetFrame.getContentPane().add(gateSetTableScrollPane, "Center");
/*      */     
/*      */ 
/* 1311 */     this.gateSetFrame.getContentPane().add(gateSetSouthPanel, "Last");
/*      */     
/*      */ 
/* 1314 */     this.gateSetFrame.pack();
/*      */     
/*      */ 
/* 1317 */     this.gateSetFrame.setLocationRelativeTo(this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1323 */     this.representationList = new JComboBox();
/* 1324 */     this.representationList.setToolTipText("The representation");
/* 1325 */     this.representationList.addActionListener(this);
/* 1326 */     this.representationList.setFocusable(false);
/*      */     
/*      */ 
/* 1329 */     this.saveRepresentationAs = new JButton("Save As...");
/* 1330 */     this.saveRepresentationAs.setActionCommand("saveRepresentationAs");
/*      */     
/* 1332 */     this.saveRepresentationAs.setToolTipText("Saves the current settings as a representation.");
/* 1333 */     this.saveRepresentationAs.addActionListener(this);
/*      */     
/*      */ 
/* 1336 */     JPanel representationListPanel = new JPanel();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1344 */     this.representationPanel = new RepresentationPanel(this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1350 */     this.plotSettingsFrame = new JFrame("Plot Settings");
/* 1351 */     this.plotSettingsFrame.setTitle("Plot Settings");
/* 1352 */     this.plotSettingsFrame.setResizable(false);
/* 1353 */     this.plotSettingsFrame.setDefaultCloseOperation(1);
/*      */     
/*      */ 
/* 1356 */     JPanel settingPanel = new JPanel(new BorderLayout());
/* 1357 */     settingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/* 1358 */     settingPanel.add(representationListPanel, "First");
/* 1359 */     settingPanel.add(this.representationPanel, "Center");
/*      */     
/*      */ 
/* 1362 */     this.plotSettingsFrame.getContentPane().add(settingPanel, "Center");
/*      */     
/*      */ 
/* 1365 */     this.plotSettingsFrame.pack();
/*      */     
/*      */ 
/* 1368 */     this.plotSettingsFrame.setLocationRelativeTo(this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1376 */     this.selectFileCreate = new JButton("Create");
/* 1377 */     this.selectFileCreate.setActionCommand("Create spawned gates");
/*      */     
/* 1379 */     this.selectFileCreate.setToolTipText("Create spawned gates.");
/* 1380 */     this.selectFileCreate.addActionListener(this);
/* 1381 */     this.selectFileCreate.setFocusable(false);
/*      */     
/*      */ 
/* 1384 */     this.selectFileCancel = new JButton("Cancel");
/* 1385 */     this.selectFileCancel.setActionCommand("Cancel create spawned gates");
/*      */     
/* 1387 */     this.selectFileCancel.setToolTipText("Cancel.");
/* 1388 */     this.selectFileCancel.addActionListener(this);
/* 1389 */     this.selectFileCancel.setFocusable(false);
/*      */     
/*      */ 
/* 1392 */     this.selectFileFrame = new JFrame("Select Files");
/* 1393 */     this.selectFileFrame.setTitle("Select Files");
/* 1394 */     this.selectFileFrame.setDefaultCloseOperation(1);
/*      */     
/*      */ 
/* 1397 */     this.selectFileModel = new SelectFileTableModel();
/* 1398 */     this.selectFileTable = new JTable(this.selectFileModel);
/*      */     
/* 1400 */     this.selectFileTable.setPreferredScrollableViewportSize(new Dimension(300, 100));
/* 1401 */     this.selectFileTable.setSelectionMode(0);
/*      */     
/*      */ 
/* 1404 */     JScrollPane selectFileTableScrollPane = new JScrollPane(this.selectFileTable);
/* 1405 */     selectFileTableScrollPane.setAlignmentX(0.0F);
/*      */     
/*      */ 
/* 1408 */     JPanel selectFileSouthPanel = new JPanel();
/* 1409 */     selectFileSouthPanel.add(this.selectFileCreate);
/* 1410 */     selectFileSouthPanel.add(this.selectFileCancel);
/*      */     
/*      */ 
/* 1413 */     this.selectFileFrame.getContentPane().add(selectFileTableScrollPane, "Center");
/*      */     
/*      */ 
/* 1416 */     this.selectFileFrame.getContentPane().add(selectFileSouthPanel, "Last");
/*      */     
/* 1418 */     this.selectFileFrame.pack();
/* 1419 */     this.selectFileFrame.setLocationRelativeTo(this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1426 */     this.selectPopulationFrame = new JFrame("Select Population");
/* 1427 */     this.selectPopulationFrame.setTitle("Select Population");
/* 1428 */     this.selectPopulationFrame.setDefaultCloseOperation(1);
/*      */     
/*      */ 
/* 1431 */     final JButton fakeSelectPopulationButton = new JButton("Fake Select Population Button");
/* 1432 */     fakeSelectPopulationButton.setActionCommand("Select Population");
/* 1433 */     fakeSelectPopulationButton.addActionListener(this);
/*      */     
/*      */ 
/* 1436 */     this.selectPopulationListModel = new PopulationListModel();
/*      */     
/*      */ 
/* 1439 */     this.selectPopulationList = new JList(this.selectPopulationListModel);
/* 1440 */     this.selectPopulationList.setSelectionMode(0);
/* 1441 */     this.selectPopulationList.setLayoutOrientation(0);
/*      */     
/*      */ 
/* 1444 */     this.selectPopulationList.addMouseListener(new MouseAdapter() {
/*      */       public void mouseClicked(MouseEvent e) {
/* 1446 */         if (e.getClickCount() == 2) {
/* 1447 */           fakeSelectPopulationButton.doClick();
/*      */         }
/*      */         
/*      */       }
/*      */       
/* 1452 */     });
/* 1453 */     this.selectPopulationList.addKeyListener(new KeyAdapter() {
/*      */       public void keyPressed(KeyEvent e) {
/* 1455 */         if (e.getKeyCode() == 10) {
/* 1456 */           fakeSelectPopulationButton.doClick();
/*      */         }
/*      */         
/*      */       }
/*      */       
/* 1461 */     });
/* 1462 */     JScrollPane selectPopulationListScrollPane = new JScrollPane(this.selectPopulationList);
/* 1463 */     selectPopulationListScrollPane.setPreferredSize(new Dimension(150, 150));
/*      */     
/*      */ 
/* 1466 */     JPanel selectPopulationListPanel = new JPanel(new BorderLayout());
/* 1467 */     selectPopulationListPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
/*      */     
/*      */ 
/* 1470 */     selectPopulationListPanel.add(new JLabel("Select a population:"), "First");
/* 1471 */     selectPopulationListPanel.add(selectPopulationListScrollPane, "Center");
/*      */     
/*      */ 
/* 1474 */     this.selectPopulationFrame.getContentPane().add(selectPopulationListPanel, "Center");
/*      */     
/* 1476 */     this.selectPopulationFrame.pack();
/* 1477 */     this.selectPopulationFrame.setLocationRelativeTo(this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1483 */     this.autoGatingFrame = new JFrame("Auto Gating Tools");
/* 1484 */     this.autoGatingFrame.setTitle("Auto Gating Tools");
/* 1485 */     this.autoGatingFrame.setDefaultCloseOperation(1);
/*      */     
/*      */ 
/* 1488 */     this.autoQuadDetails = new JButton("Create Quadrant Gate");
/* 1489 */     this.autoQuadDetails.setActionCommand("Auto Quad");
/*      */     
/* 1491 */     this.autoQuadDetails.setToolTipText("Automatically creates a quadrant gate for the current plot.");
/* 1492 */     this.autoQuadDetails.addActionListener(this);
/* 1493 */     this.autoQuadDetails.setFocusable(false);
/*      */     
/*      */ 
/* 1496 */     this.autoBarcodeDetails = new JButton("Create Ellipse Gates");
/* 1497 */     this.autoBarcodeDetails.setActionCommand("Auto Barcode");
/*      */     
/* 1499 */     this.autoBarcodeDetails.setToolTipText("Automatically creates ellipse gates on the specified channels.");
/* 1500 */     this.autoBarcodeDetails.addActionListener(this);
/* 1501 */     this.autoBarcodeDetails.setFocusable(false);
/*      */     
/* 1503 */     JPanel autoPanel = new JPanel();
/* 1504 */     autoPanel.setLayout(new BoxLayout(autoPanel, 3));
/* 1505 */     autoPanel.setPreferredSize(new Dimension(250, 400));
/*      */     
/*      */ 
/* 1508 */     JPanel tempPanel = new JPanel();
/* 1509 */     tempPanel.setBorder(BorderFactory.createTitledBorder("Automatic Quadrant Gating"));
/* 1510 */     tempPanel.add(this.autoQuadDetails);
/* 1511 */     autoPanel.add(tempPanel);
/*      */     
/*      */ 
/* 1514 */     tempPanel = new JPanel();
/* 1515 */     tempPanel.setBorder(BorderFactory.createTitledBorder("Automatic Barcode Gating"));
/* 1516 */     tempPanel.add(this.autoBarcodeDetails);
/* 1517 */     autoPanel.add(tempPanel);
/*      */     
/*      */ 
/* 1520 */     this.autoGatingFrame.getContentPane().add(autoPanel, "Center");
/*      */     
/* 1522 */     this.autoGatingFrame.pack();
/* 1523 */     this.autoGatingFrame.setResizable(false);
/* 1524 */     this.autoGatingFrame.setLocationRelativeTo(this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1530 */     this.autoBarcodeFrame = new JFrame("Automatic Barcode Gating");
/* 1531 */     this.autoBarcodeFrame.setTitle("Automatic Barcode Gating");
/* 1532 */     this.autoBarcodeFrame.setResizable(false);
/* 1533 */     this.autoBarcodeFrame.setDefaultCloseOperation(1);
/*      */     
/*      */ 
/* 1536 */     SpinnerModel xModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
/*      */     
/*      */ 
/* 1539 */     this.autoBarcodeXGroup = new JSpinner(xModel);
/* 1540 */     this.autoBarcodeXGroup.setEditor(new NumberEditor(this.autoBarcodeXGroup));
/*      */     
/*      */ 
/* 1543 */     SpinnerModel yModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
/*      */     
/*      */ 
/* 1546 */     this.autoBarcodeYGroup = new JSpinner(yModel);
/* 1547 */     this.autoBarcodeYGroup.setEditor(new NumberEditor(this.autoBarcodeYGroup));
/*      */     
/*      */ 
/* 1550 */     this.autoBarcodeOK = new JButton("OK");
/* 1551 */     this.autoBarcodeOK.setActionCommand("autoBarcodeOK");
/*      */     
/* 1553 */     this.autoBarcodeOK.setToolTipText("Create the automatic barcode gates.");
/* 1554 */     this.autoBarcodeOK.addActionListener(this);
/* 1555 */     this.autoBarcodeOK.setFocusable(false);
/*      */     
/*      */ 
/* 1558 */     this.autoBarcodeCancel = new JButton("Cancel");
/* 1559 */     this.autoBarcodeCancel.setActionCommand("autoBarcodeCancel");
/*      */     
/* 1561 */     this.autoBarcodeCancel.setToolTipText("Cancel the automatic barcode.");
/* 1562 */     this.autoBarcodeCancel.addActionListener(this);
/* 1563 */     this.autoBarcodeCancel.setFocusable(false);
/*      */     
/*      */ 
/* 1566 */     JPanel autoBarcodePanel = new JPanel(new GridBagLayout());
/* 1567 */     autoBarcodePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*      */     
/*      */ 
/* 1570 */     GridBagConstraints constraints = new GridBagConstraints();
/*      */     
/*      */ 
/*      */ 
/* 1574 */     constraints.insets = new Insets(2, 5, 2, 5);
/* 1575 */     constraints.anchor = 21;
/*      */     
/*      */ 
/* 1578 */     constraints.gridx = 0;
/* 1579 */     constraints.gridy = 0;
/* 1580 */     autoBarcodePanel.add(new JLabel("Number of X Groups: "), constraints);
/*      */     
/*      */ 
/* 1583 */     constraints.gridx = 1;
/* 1584 */     constraints.gridy = 0;
/* 1585 */     autoBarcodePanel.add(this.autoBarcodeXGroup, constraints);
/*      */     
/*      */ 
/* 1588 */     constraints.gridx = 0;
/* 1589 */     constraints.gridy = 1;
/* 1590 */     autoBarcodePanel.add(new JLabel("Number of Y Groups: "), constraints);
/*      */     
/*      */ 
/* 1593 */     constraints.gridx = 1;
/* 1594 */     constraints.gridy = 1;
/* 1595 */     autoBarcodePanel.add(this.autoBarcodeYGroup, constraints);
/*      */     
/* 1597 */     tempPanel = new JPanel();
/* 1598 */     tempPanel.add(this.autoBarcodeOK);
/* 1599 */     tempPanel.add(this.autoBarcodeCancel);
/*      */     
/*      */ 
/* 1602 */     constraints.gridx = 0;
/* 1603 */     constraints.gridy = 2;
/* 1604 */     constraints.gridwidth = 2;
/* 1605 */     constraints.anchor = 10;
/* 1606 */     autoBarcodePanel.add(tempPanel, constraints);
/*      */     
/*      */ 
/* 1609 */     constraints.gridwidth = 1;
/*      */     
/*      */ 
/* 1612 */     this.autoBarcodeFrame.getContentPane().add(autoBarcodePanel, "Center");
/*      */     
/* 1614 */     this.autoBarcodeFrame.pack();
/* 1615 */     this.autoBarcodeFrame.setLocationRelativeTo(this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1621 */     this.populationTreeFrame = new JFrame("View Populations");
/* 1622 */     this.populationTreeFrame.setTitle("View Populations");
/* 1623 */     this.populationTreeFrame.setDefaultCloseOperation(1);
/*      */     
/*      */ 
/* 1626 */     this.populationTree = new PopulationTreeClient(this, null);
/*      */     
/*      */ 
/* 1629 */     this.populationTree.setPreferredSize(new Dimension(300, 400));
/*      */     
/*      */ 
/* 1632 */     this.populationTreeFrame.getContentPane().add(this.populationTree, "Center");
/*      */     
/* 1634 */     this.populationTreeFrame.pack();
/* 1635 */     this.populationTreeFrame.setLocationRelativeTo(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setBean(GateBean bean)
/*      */   {
/* 1649 */     if (bean == null)
/*      */     {
/* 1651 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1660 */     unselect();
/*      */     
/*      */ 
/* 1663 */     this.gates.clear();
/*      */     
/*      */ 
/* 1666 */     this.gateSets.clear();
/*      */     
/*      */ 
/* 1669 */     this.representations.clear();
/*      */     
/*      */ 
/* 1672 */     this.file = null;
/*      */     
/*      */ 
/* 1675 */     this.representation = null;
/*      */     
/*      */ 
/* 1678 */     this.activeGateSet = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1686 */     this.dirtyGateP = false;
/*      */     
/*      */ 
/* 1689 */     this.dirtyGateSetP = false;
/*      */     
/*      */ 
/* 1692 */     this.dirtyRepresentationP = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1700 */     this.bean = bean;
/*      */     
/*      */ 
/* 1703 */     this.experimentID = this.bean.getExperimentID();
/*      */     
/*      */ 
/* 1706 */     this.files = this.bean.getFiles();
/*      */     
/*      */ 
/* 1709 */     this.compensations = this.bean.getCompensations();
/*      */     
/*      */ 
/* 1712 */     this.scales = this.bean.getAvailableScales();
/*      */     
/*      */ 
/* 1715 */     populateFileList();
/*      */     
/*      */ 
/* 1718 */     populateCompensationList();
/*      */     
/* 1720 */     if (this.gatePanel != null) {
/* 1721 */       this.gatePanel.setCompensations(this.compensations);
/* 1722 */       this.gatePanel.setScales(this.scales);
/*      */     }
/*      */     
/* 1725 */     if ((this.files == null) || (this.files.length <= 0))
/*      */     {
/* 1727 */       this.plotCache = new Image[0][0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 1738 */       int maxChannelCount = this.files[0].getChannelCount();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1743 */       for (int i = 1; i < this.files.length; i++)
/*      */       {
/* 1745 */         int channelCount = this.files[i].getChannelCount();
/*      */         
/* 1747 */         if (channelCount > maxChannelCount)
/*      */         {
/* 1749 */           maxChannelCount = channelCount;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1754 */       if (this.plotCache.length < maxChannelCount)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1761 */         this.plotCache = new Image[maxChannelCount][maxChannelCount + 1];
/*      */         
/*      */ 
/* 1764 */         for (int i = 0; i < maxChannelCount; i++) {
/* 1765 */           for (int j = 0; j < maxChannelCount + 1; j++) {
/* 1766 */             this.plotCache[i][j] = null;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1778 */     Gate[] gateArray = this.bean.getGates();
/*      */     
/*      */ 
/* 1781 */     int count = 0;
/*      */     
/*      */ 
/* 1784 */     for (int i = 0; i < gateArray.length; i++)
/*      */     {
/* 1786 */       this.gates.add(gateArray[i]);
/*      */       
/* 1788 */       if ((!(gateArray[i] instanceof Quad)) && (!(gateArray[i] instanceof SplitRange)))
/*      */       {
/*      */ 
/* 1791 */         count++;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1796 */     this.gatesListModel.fireGateAdded(0, count);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1804 */     GateSet[] gateSetArray = this.bean.getGateSets();
/*      */     
/*      */ 
/* 1807 */     for (int i = 0; i < gateSetArray.length; i++) {
/* 1808 */       if (gateSetArray[i] != null)
/*      */       {
/* 1810 */         this.gateSets.add(gateSetArray[i]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1815 */     fireGateSetChanged();
/*      */     
/*      */ 
/* 1818 */     showGate(null);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1826 */     Representation[] reps = bean.getRepresentations();
/*      */     
/*      */ 
/* 1829 */     for (int i = 0; i < reps.length; i++) {
/* 1830 */       if (reps[i] != null)
/*      */       {
/* 1832 */         this.representations.add(reps[i]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1837 */     populateRepresentationList();
/*      */     
/*      */ 
/*      */ 
/* 1841 */     setFlowFile(0);
/*      */     
/* 1843 */     if (this.activeCompensationID < -2)
/*      */     {
/* 1845 */       if (this.representation != null)
/*      */       {
/* 1847 */         setCompensationID(this.representation.getCompensationID());
/*      */       }
/* 1849 */       else if (this.file != null)
/*      */       {
/* 1851 */         if (this.file.hasNonIdentityCompensationMatrix())
/*      */         {
/* 1853 */           setCompensationID(-2);
/*      */ 
/*      */ 
/*      */         }
/* 1857 */         else if ((this.compensations != null) && (this.compensations.length > 0) && (this.compensations[(this.compensations.length - 1)] != null))
/*      */         {
/* 1859 */           setCompensationID(this.compensations[(this.compensations.length - 1)].getID());
/*      */         }
/*      */         else
/*      */         {
/* 1863 */           setCompensationID(-1);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else {
/* 1869 */         setCompensationID(-1);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 1874 */     else if (!setCompensationID(this.activeCompensationID))
/*      */     {
/* 1876 */       setCompensationID(-1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1881 */     firePlotChanged();
/*      */   }
/*      */   
/*      */   FlowFileDescriptor[] getFiles() {
/* 1885 */     return this.files;
/*      */   }
/*      */   
/*      */   String[] getFilenames() {
/* 1889 */     String[] filenameList = new String[this.filenames.size()];
/* 1890 */     return (String[])this.filenames.toArray(filenameList);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void populateFileList()
/*      */   {
/* 1901 */     populateFileList(47);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void populateFileList(int maxLength)
/*      */   {
/* 1911 */     if (this.fileList == null) {
/* 1912 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1920 */     this.bypassP = true;
/*      */     
/* 1922 */     this.fileList.removeAllItems();
/*      */     
/* 1924 */     if (this.files != null)
/*      */     {
/*      */ 
/* 1927 */       for (int i = 0; i < this.files.length; i++) { String fullName;
/* 1928 */         String filename; String fullName; if (this.files[i] == null) {
/* 1929 */           String filename = "File " + (i + 1);
/* 1930 */           String sampleName = "";
/* 1931 */           fullName = filename;
/*      */         }
/*      */         else {
/* 1934 */           filename = this.files[i].getFilename();
/*      */           
/* 1936 */           if ((filename == null) || (filename.length() <= 0)) {
/* 1937 */             filename = "File " + (i + 1);
/*      */           }
/*      */           
/* 1940 */           String sampleName = this.files[i].getSampleName();
/* 1941 */           String tubeName = this.files[i].getTubeName();
/*      */           
/*      */           String fullName;
/* 1944 */           if ((tubeName != null) && (tubeName.length() > 0) && (!tubeName.equals("Untitled"))) {
/* 1945 */             fullName = filename + " - " + tubeName; } else { String fullName;
/* 1946 */             if ((sampleName != null) && (sampleName.length() > 0)) {
/* 1947 */               fullName = filename + " - " + sampleName;
/*      */             } else {
/* 1949 */               fullName = filename;
/*      */             }
/*      */           }
/*      */         }
/* 1953 */         if (fullName.length() > maxLength)
/*      */         {
/* 1955 */           if (filename.length() > maxLength)
/*      */           {
/* 1957 */             this.fileList.addItem(new ListItem(filename.substring(0, maxLength) + "..."));
/* 1958 */             this.filenames.add(i, filename);
/*      */           }
/*      */           else
/*      */           {
/* 1962 */             this.fileList.addItem(new ListItem(filename));
/* 1963 */             this.filenames.add(i, filename);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1968 */           this.fileList.addItem(new ListItem(fullName));
/* 1969 */           this.filenames.add(i, fullName);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1975 */     this.bypassP = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void populateCompensationList()
/*      */   {
/* 1983 */     if (this.compensationList == null)
/*      */     {
/* 1985 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1994 */     this.bypassP = true;
/*      */     
/*      */ 
/* 1997 */     this.compensationList.removeAllItems();
/*      */     
/*      */ 
/* 2000 */     this.compensationList.addItem(new ListItem("File Compensation"));
/*      */     
/*      */ 
/* 2003 */     this.compensationList.addItem(new ListItem("Uncompensated"));
/*      */     
/* 2005 */     if (this.compensations != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 2010 */       for (int i = 0; i < this.compensations.length; i++)
/*      */       {
/* 2012 */         String name = null;
/*      */         
/* 2014 */         if (this.compensations[i] != null)
/*      */         {
/* 2016 */           name = this.compensations[i].getName();
/*      */         }
/*      */         
/* 2019 */         if (name == null)
/*      */         {
/* 2021 */           this.compensationList.addItem(new ListItem("Comp " + (i + 1)));
/*      */         }
/* 2023 */         else if (name.length() >= 17)
/*      */         {
/* 2025 */           this.compensationList.addItem(new ListItem(name.substring(0, 17) + "..."));
/*      */         }
/*      */         else
/*      */         {
/* 2029 */           this.compensationList.addItem(new ListItem(name));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2035 */     this.bypassP = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void populatePopulationList()
/*      */   {
/* 2043 */     if (this.populationList == null)
/*      */     {
/* 2045 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2054 */     this.bypassP = true;
/*      */     
/*      */ 
/* 2057 */     this.populationList.removeAllItems();
/*      */     
/*      */ 
/* 2060 */     this.populationList.addItem(new ListItem("Ungated"));
/*      */     
/*      */ 
/* 2063 */     GateSet[] populations = getPopulations();
/*      */     
/*      */ 
/* 2066 */     int index = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2071 */     for (int i = 0; i < populations.length; i++)
/*      */     {
/* 2073 */       String name = null;
/*      */       
/* 2075 */       if (populations[i] != null)
/*      */       {
/* 2077 */         name = populations[i].getName();
/*      */       }
/*      */       
/* 2080 */       if (name == null)
/*      */       {
/* 2082 */         this.populationList.addItem(new ListItem("Population " + (i + 1)));
/*      */       }
/* 2084 */       else if (name.length() >= 17)
/*      */       {
/* 2086 */         this.populationList.addItem(new ListItem(name.substring(0, 17) + "..."));
/*      */       }
/*      */       else
/*      */       {
/* 2090 */         this.populationList.addItem(new ListItem(name));
/*      */       }
/*      */       
/* 2093 */       if ((this.activeGateSet != null) && (this.activeGateSet.equals(populations[i])))
/*      */       {
/*      */ 
/*      */ 
/* 2097 */         index = i + 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2102 */     this.populationList.setSelectedIndex(index);
/*      */     
/*      */ 
/* 2105 */     this.bypassP = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void populateRepresentationList()
/*      */   {
/* 2113 */     if (this.representationList == null)
/*      */     {
/* 2115 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2124 */     this.bypassP = true;
/*      */     
/*      */ 
/* 2127 */     this.representationList.removeAllItems();
/*      */     
/*      */ 
/* 2130 */     this.representationList.addItem("Panel Representation");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2136 */     int i = 0; for (int n = this.representations.size(); i < n; i++)
/*      */     {
/* 2138 */       String name = null;
/*      */       
/*      */ 
/* 2141 */       Representation rep = (Representation)this.representations.get(i);
/*      */       
/* 2143 */       if (rep != null)
/*      */       {
/* 2145 */         name = rep.getName();
/*      */       }
/*      */       
/* 2148 */       if (name == null)
/*      */       {
/* 2150 */         this.representationList.addItem("Representation " + (i + 1));
/*      */       }
/* 2152 */       else if (name.length() >= 17)
/*      */       {
/* 2154 */         this.representationList.addItem(name.substring(0, 17) + "...");
/*      */       }
/*      */       else
/*      */       {
/* 2158 */         this.representationList.addItem(name);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2163 */     this.bypassP = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void populateAxes()
/*      */   {
/* 2171 */     if ((this.xAxis == null) || (this.yAxis == null))
/*      */     {
/* 2173 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2177 */     this.bypassP = true;
/*      */     
/*      */ 
/* 2180 */     this.xAxis.removeAllItems();
/*      */     
/*      */ 
/* 2183 */     this.yAxis.removeAllItems();
/*      */     
/*      */ 
/* 2186 */     this.yAxis.addItem("Counts");
/*      */     
/* 2188 */     if (this.file != null)
/*      */     {
/*      */ 
/*      */ 
/* 2192 */       String[] channels = this.file.getChannels();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2197 */       for (int i = 0; i < channels.length; i++) { String channel;
/* 2198 */         String channel; if (channels[i] == null)
/*      */         {
/* 2200 */           channel = "Channel " + i;
/*      */         } else { String channel;
/* 2202 */           if (channels[i].length() >= 17)
/*      */           {
/* 2204 */             channel = channels[i].substring(0, 17) + "...";
/*      */           }
/*      */           else
/*      */           {
/* 2208 */             channel = channels[i];
/*      */           }
/*      */         }
/*      */         
/* 2212 */         this.xAxis.addItem(channel);
/*      */         
/*      */ 
/* 2215 */         this.yAxis.addItem(channel);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2220 */     this.bypassP = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getAxisBins()
/*      */   {
/* 2230 */     return this.axisBins;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getXChannel()
/*      */   {
/* 2239 */     if (this.xAxis == null)
/*      */     {
/* 2241 */       return -1;
/*      */     }
/*      */     
/*      */ 
/* 2245 */     return this.xAxis.getSelectedIndex();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setXChannel(int channel)
/*      */   {
/* 2256 */     if ((this.file == null) || (channel < 0) || (channel >= this.file.getChannelCount()))
/*      */     {
/* 2258 */       return;
/*      */     }
/*      */     
/* 2261 */     if ((this.xAxis != null) && (this.xAxis.getSelectedIndex() != channel) && (channel < this.xAxis.getItemCount()))
/*      */     {
/*      */ 
/*      */ 
/* 2265 */       this.bypassP = true;
/*      */       
/*      */ 
/* 2268 */       this.xAxis.setSelectedIndex(channel);
/*      */       
/*      */ 
/* 2271 */       this.bypassP = false;
/*      */     }
/*      */     
/* 2274 */     if (this.canvas != null)
/*      */     {
/* 2276 */       this.canvas.setXChannel(channel);
/*      */     }
/*      */     
/* 2279 */     if ((this.representation != null) && (this.representation.getXChannel() != channel))
/*      */     {
/* 2281 */       this.representation.setXChannel(channel);
/*      */       
/*      */ 
/* 2284 */       fireRepresentationChanged(false);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getYChannel()
/*      */   {
/* 2294 */     if (this.yAxis == null)
/*      */     {
/* 2296 */       return -2;
/*      */     }
/*      */     
/*      */ 
/* 2300 */     return this.yAxis.getSelectedIndex() - 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setYChannel(int channel)
/*      */   {
/* 2311 */     if ((this.file == null) || (channel < -1) || (channel >= this.file.getChannelCount()))
/*      */     {
/* 2313 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2317 */     int channelIndex = channel + 1;
/*      */     
/* 2319 */     if ((this.yAxis != null) && (this.yAxis.getSelectedIndex() != channelIndex) && (channelIndex < this.yAxis.getItemCount()))
/*      */     {
/*      */ 
/*      */ 
/* 2323 */       this.bypassP = true;
/*      */       
/*      */ 
/* 2326 */       this.yAxis.setSelectedIndex(channelIndex);
/*      */       
/*      */ 
/* 2329 */       this.bypassP = false;
/*      */     }
/*      */     
/* 2332 */     if (this.canvas != null)
/*      */     {
/* 2334 */       this.canvas.setYChannel(channel);
/*      */     }
/*      */     
/* 2337 */     if ((this.representation != null) && (this.representation.getYChannel() != channel))
/*      */     {
/* 2339 */       this.representation.setYChannel(channel);
/*      */       
/*      */ 
/* 2342 */       fireRepresentationChanged(false);
/*      */     }
/*      */     
/*      */ 
/* 2346 */     toggle1D(channel == -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setFlowFile(String filename)
/*      */   {
/* 2356 */     if (filename == null)
/*      */     {
/* 2358 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2362 */     if (this.files != null)
/*      */     {
/*      */ 
/*      */ 
/* 2366 */       for (int i = 0; i < this.files.length; i++) {
/* 2367 */         if (filename.equals(this.files[i].getFilename()))
/*      */         {
/* 2369 */           setFlowFile(i);
/*      */           
/*      */ 
/* 2372 */           firePlotChanged();
/*      */           
/* 2374 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setFlowFile(int index)
/*      */   {
/* 2394 */     if ((this.files == null) || (index < 0) || (index >= this.files.length))
/*      */     {
/* 2396 */       return;
/*      */     }
/*      */     
/* 2399 */     if ((this.file != null) && (this.file.equals(this.files[index])))
/*      */     {
/* 2401 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2405 */     int selectedGateIndex = this.gatesList.getSelectedIndex();
/* 2406 */     unselect();
/*      */     
/*      */ 
/* 2409 */     this.file = this.files[index];
/*      */     
/* 2411 */     if (this.file != null)
/*      */     {
/*      */ 
/*      */ 
/* 2415 */       populateAxes();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2437 */       int[] ids = this.file.getRepresentationIDs();
/*      */       
/*      */ 
/* 2440 */       boolean useDefaultP = true;
/*      */       
/* 2442 */       if (ids.length > 0)
/*      */       {
/* 2444 */         int repID = ids[0];
/*      */         
/* 2446 */         if ((this.representation != null) && (this.representation.getID() == repID))
/*      */         {
/*      */ 
/*      */ 
/* 2450 */           useDefaultP = false;
/*      */ 
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 2457 */           int i = 0; for (int n = this.representations.size(); i < n; i++)
/*      */           {
/* 2459 */             Representation rep = (Representation)this.representations.get(i);
/*      */             
/* 2461 */             if (rep.getID() == repID)
/*      */             {
/* 2463 */               if (this.representation != null)
/*      */               {
/*      */ 
/*      */ 
/* 2467 */                 rep.clearGateSetIDs();
/*      */                 
/*      */ 
/* 2470 */                 int[] gateSetIDs = this.representation.getGateSetIDs();
/*      */                 
/*      */ 
/* 2473 */                 for (int j = 0; j < gateSetIDs.length; j++) {
/* 2474 */                   rep.addGateSetID(gateSetIDs[j]);
/*      */                 }
/*      */                 
/* 2477 */                 rep.setXChannel(this.representation.getXChannel());
/* 2478 */                 rep.setYChannel(this.representation.getYChannel());
/* 2479 */                 rep.setPlotType(this.representation.getPlotType());
/* 2480 */                 rep.setStatisticType(this.representation.getStatisticType());
/* 2481 */                 rep.setColorSet(this.representation.getColorSet());
/* 2482 */                 rep.setBlackBackground(this.representation.useBlackBackground());
/* 2483 */                 rep.setAnnotation(this.representation.drawAnnotation());
/* 2484 */                 rep.setScaleLabel(this.representation.drawScaleLabel());
/* 2485 */                 rep.setScaleTick(this.representation.drawScaleTick());
/* 2486 */                 rep.setAxisLabel(this.representation.drawAxisLabel());
/* 2487 */                 rep.setLongLabel(this.representation.useLongLabel());
/* 2488 */                 rep.setSmoothing(this.representation.getSmoothing());
/* 2489 */                 rep.setAspectRatio(this.representation.getAspectRatio());
/* 2490 */                 rep.setContourPercent(this.representation.getContourPercent());
/* 2491 */                 rep.setContourStartPercent(this.representation.getContourStartPercent());
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2499 */                 rep.clearRegions();
/*      */                 
/*      */ 
/* 2502 */                 Representation.Region[] regions = this.representation.getRegions();
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 2507 */                 for (int j = 0; j < regions.length; j++)
/*      */                 {
/* 2509 */                   Representation.Region region = rep.addRegion(regions[j].getID());
/*      */                   
/*      */ 
/* 2512 */                   region.setShown(regions[j].isShown());
/* 2513 */                   region.setDrawn(regions[j].isDrawn());
/* 2514 */                   region.setLabelShown(regions[j].isLabelShown());
/* 2515 */                   region.setEventCountShown(regions[j].isEventCountShown());
/* 2516 */                   region.setMeanShown(regions[j].isMeanShown());
/* 2517 */                   region.setMedianShown(regions[j].isMedianShown());
/* 2518 */                   region.setPercentShown(regions[j].isPercentShown());
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 2523 */               this.representation = rep;
/*      */               
/*      */ 
/* 2526 */               useDefaultP = false;
/*      */               
/* 2528 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2534 */       if (useDefaultP)
/*      */       {
/* 2536 */         Representation defaultRepresentation = getDefaultRepresentation(-1, "Working Representation", this.file);
/*      */         
/* 2538 */         if (this.representation != null)
/*      */         {
/*      */ 
/*      */ 
/* 2542 */           int[] gateSetIDs = this.representation.getGateSetIDs();
/*      */           
/*      */ 
/* 2545 */           for (int i = 0; i < gateSetIDs.length; i++) {
/* 2546 */             defaultRepresentation.addGateSetID(gateSetIDs[i]);
/*      */           }
/*      */           
/* 2549 */           defaultRepresentation.setXChannel(this.representation.getXChannel());
/* 2550 */           defaultRepresentation.setYChannel(this.representation.getYChannel());
/* 2551 */           defaultRepresentation.setPlotType(this.representation.getPlotType());
/* 2552 */           defaultRepresentation.setStatisticType(this.representation.getStatisticType());
/* 2553 */           defaultRepresentation.setColorSet(this.representation.getColorSet());
/* 2554 */           defaultRepresentation.setBlackBackground(this.representation.useBlackBackground());
/* 2555 */           defaultRepresentation.setAnnotation(this.representation.drawAnnotation());
/* 2556 */           defaultRepresentation.setScaleLabel(this.representation.drawScaleLabel());
/* 2557 */           defaultRepresentation.setScaleTick(this.representation.drawScaleTick());
/* 2558 */           defaultRepresentation.setAxisLabel(this.representation.drawAxisLabel());
/* 2559 */           defaultRepresentation.setLongLabel(this.representation.useLongLabel());
/* 2560 */           defaultRepresentation.setSmoothing(this.representation.getSmoothing());
/* 2561 */           defaultRepresentation.setAspectRatio(this.representation.getAspectRatio());
/* 2562 */           defaultRepresentation.setContourPercent(this.representation.getContourPercent());
/* 2563 */           defaultRepresentation.setContourStartPercent(this.representation.getContourStartPercent());
/*      */         }
/*      */         
/*      */ 
/* 2567 */         this.representation = defaultRepresentation;
/*      */       }
/*      */       
/*      */ 
/* 2571 */       this.representationPanel.setRepresentation(this.file.getChannels(), this.representation);
/*      */     }
/*      */     
/*      */ 
/* 2575 */     if ((this.fileList != null) && (this.fileList.getSelectedIndex() != index) && (index < this.fileList.getItemCount()))
/*      */     {
/*      */ 
/*      */ 
/* 2579 */       this.bypassP = true;
/*      */       
/*      */ 
/* 2582 */       this.fileList.setSelectedIndex(index);
/*      */       
/*      */ 
/* 2585 */       this.bypassP = false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2592 */     this.firstFile.setEnabled(true);
/* 2593 */     this.prevFile.setEnabled(true);
/* 2594 */     this.nextFile.setEnabled(true);
/* 2595 */     this.lastFile.setEnabled(true);
/*      */     
/* 2597 */     if (index <= 0)
/*      */     {
/* 2599 */       this.firstFile.setEnabled(false);
/* 2600 */       this.prevFile.setEnabled(false);
/*      */     }
/*      */     
/* 2603 */     if (index >= this.files.length - 1)
/*      */     {
/* 2605 */       this.nextFile.setEnabled(false);
/* 2606 */       this.lastFile.setEnabled(false);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2614 */     if (this.representation != null)
/*      */     {
/* 2616 */       setXChannel(this.representation.getXChannel());
/* 2617 */       setYChannel(this.representation.getYChannel());
/*      */     }
/* 2619 */     else if (this.file != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2631 */       if (this.file.getChannelCount() > 1)
/*      */       {
/* 2633 */         setXChannel(1);
/* 2634 */         setYChannel(0);
/*      */       }
/*      */       else
/*      */       {
/* 2638 */         setXChannel(0);
/* 2639 */         setYChannel(-1);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2644 */       setXChannel(0);
/* 2645 */       setYChannel(-1);
/*      */     }
/*      */     
/* 2648 */     if (selectedGateIndex != -1) {
/* 2649 */       Gate[] gateArray = getMutableGates();
/* 2650 */       Gate selectedGate = gateArray[selectedGateIndex];
/* 2651 */       if (selectedGate != null) {
/* 2652 */         selectGate(selectedGate);
/* 2653 */         displayGate(selectedGate);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2658 */     this.gatesListModel.fireGateChanged();
/*      */     
/* 2660 */     if (this.representation == null)
/*      */     {
/* 2662 */       setActiveGateSet(null);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 2668 */       int[] ids = this.representation.getGateSetIDs();
/*      */       
/* 2670 */       if ((ids != null) && (ids.length > 0))
/*      */       {
/* 2672 */         setActiveGateSet(ids[0]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getCompensationID()
/*      */   {
/* 2688 */     return this.activeCompensationID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean setCompensationID(int id)
/*      */   {
/* 2698 */     if ((id < -2) || (this.activeCompensationID == id))
/*      */     {
/* 2700 */       return true;
/*      */     }
/*      */     
/*      */ 
/* 2704 */     if (id == -2)
/*      */     {
/* 2706 */       this.activeCompensationID = -2;
/*      */       
/* 2708 */       if ((this.compensationList != null) && (this.compensationList.getSelectedIndex() != 0) && (this.compensationList.getItemCount() > 0))
/*      */       {
/*      */ 
/*      */ 
/* 2712 */         this.bypassP = true;
/*      */         
/*      */ 
/* 2715 */         this.compensationList.setSelectedIndex(0);
/*      */         
/*      */ 
/* 2718 */         this.bypassP = false;
/*      */       }
/*      */       
/* 2721 */       if ((this.representation != null) && (this.representation.getCompensationID() != -2))
/*      */       {
/* 2723 */         this.representation.setCompensationID(-2);
/*      */         
/*      */ 
/* 2726 */         fireRepresentationChanged(false);
/*      */       }
/*      */       
/*      */ 
/* 2730 */       return true;
/*      */     }
/* 2732 */     if (id == -1)
/*      */     {
/* 2734 */       this.activeCompensationID = -1;
/*      */       
/* 2736 */       if ((this.compensationList != null) && (this.compensationList.getSelectedIndex() != 1) && (this.compensationList.getItemCount() > 1))
/*      */       {
/*      */ 
/*      */ 
/* 2740 */         this.bypassP = true;
/*      */         
/*      */ 
/* 2743 */         this.compensationList.setSelectedIndex(1);
/*      */         
/*      */ 
/* 2746 */         this.bypassP = false;
/*      */       }
/*      */       
/* 2749 */       if ((this.representation != null) && (this.representation.getCompensationID() != -1))
/*      */       {
/* 2751 */         this.representation.setCompensationID(-1);
/*      */         
/*      */ 
/* 2754 */         fireRepresentationChanged(false);
/*      */       }
/*      */       
/*      */ 
/* 2758 */       return true;
/*      */     }
/* 2760 */     if (id > 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2766 */       for (int i = 0; i < this.compensations.length; i++) {
/* 2767 */         if (this.compensations[i].getID() == id)
/*      */         {
/*      */ 
/*      */ 
/* 2771 */           this.activeCompensationID = id;
/*      */           
/*      */ 
/* 2774 */           int index = i + 2;
/*      */           
/* 2776 */           if ((this.compensationList != null) && (this.compensationList.getSelectedIndex() != index) && (index < this.compensationList.getItemCount()))
/*      */           {
/*      */ 
/*      */ 
/* 2780 */             this.bypassP = true;
/*      */             
/*      */ 
/* 2783 */             this.compensationList.setSelectedIndex(index);
/*      */             
/*      */ 
/* 2786 */             this.bypassP = false;
/*      */           }
/*      */           
/* 2789 */           if ((this.representation != null) && (this.representation.getCompensationID() != this.activeCompensationID))
/*      */           {
/* 2791 */             this.representation.setCompensationID(this.activeCompensationID);
/*      */             
/*      */ 
/* 2794 */             fireRepresentationChanged(false);
/*      */           }
/*      */           
/*      */ 
/* 2798 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2805 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int[] getAvailableScales()
/*      */   {
/* 2819 */     if (this.scales == null)
/*      */     {
/* 2821 */       return new int[0];
/*      */     }
/*      */     
/*      */ 
/* 2825 */     int[] scaleFlags = new int[this.scales.length];
/*      */     
/*      */ 
/* 2828 */     for (int i = 0; i < this.scales.length; i++) {
/* 2829 */       scaleFlags[i] = this.scales[i].getScaleFlag();
/*      */     }
/*      */     
/*      */ 
/* 2833 */     return scaleFlags;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   String getScaleLabel(int type)
/*      */   {
/* 2845 */     if (this.scales == null)
/*      */     {
/* 2847 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2851 */     for (int i = 0; i < this.scales.length; i++) {
/* 2852 */       if (this.scales[i].getScaleFlag() == type)
/*      */       {
/* 2854 */         return this.scales[i].getLabel();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2859 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   String getDefaultScaleArgument(int type)
/*      */   {
/* 2870 */     if (this.scales == null)
/*      */     {
/* 2872 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2876 */     for (int i = 0; i < this.scales.length; i++) {
/* 2877 */       if (this.scales[i].getScaleFlag() == type)
/*      */       {
/* 2879 */         return this.scales[i].getScaleArgumentString();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2884 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   double getDefaultScaleMinimum(int type)
/*      */   {
/* 2895 */     if (this.scales == null)
/*      */     {
/* 2897 */       return NaN.0D;
/*      */     }
/*      */     
/*      */ 
/* 2901 */     for (int i = 0; i < this.scales.length; i++) {
/* 2902 */       if (this.scales[i].getScaleFlag() == type)
/*      */       {
/* 2904 */         return this.scales[i].getChannelMinimum();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2909 */     return NaN.0D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   double getDefaultScaleMaximum(int type)
/*      */   {
/* 2920 */     if (this.scales == null)
/*      */     {
/* 2922 */       return NaN.0D;
/*      */     }
/*      */     
/*      */ 
/* 2926 */     for (int i = 0; i < this.scales.length; i++) {
/* 2927 */       if (this.scales[i].getScaleFlag() == type)
/*      */       {
/* 2929 */         return this.scales[i].getChannelMaximum();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2934 */     return NaN.0D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean addGate(Gate gate)
/*      */   {
/* 2958 */     return addGate(gate, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean addGate(Gate gate, boolean createGateSetP)
/*      */   {
/* 2978 */     if (gate == null)
/*      */     {
/* 2980 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2992 */     String name = JOptionPane.showInputDialog(this, "Please enter a name for the gate and its corresponding population:", gate.getName());
/*      */     
/* 2994 */     if (name == null)
/*      */     {
/* 2996 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 3000 */     gate.setName(name);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3005 */     boolean addP = this.gates.add(gate);
/*      */     
/* 3007 */     if (!addP)
/*      */     {
/* 3009 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3015 */     setDirtyGate();
/*      */     
/* 3017 */     if (this.file != null)
/*      */     {
/* 3019 */       gate.setFilename(this.file.getFilename());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3028 */     gate.setCompensationID(getCompensationID());
/*      */     
/*      */ 
/* 3031 */     int gateXChannel = gate.getXChannel();
/*      */     
/*      */ 
/* 3034 */     int gateYChannel = gate.getYChannel();
/*      */     
/* 3036 */     if (this.representation != null)
/*      */     {
/*      */ 
/*      */ 
/* 3040 */       gate.setXScaleFlag(this.representation.getScaleFlag(gateXChannel));
/*      */       
/*      */ 
/* 3043 */       gate.setYScaleFlag(this.representation.getScaleFlag(gateYChannel));
/*      */       
/*      */ 
/* 3046 */       gate.setXScaleArgumentString(this.representation.getScaleArgumentString(gateXChannel));
/*      */       
/*      */ 
/* 3049 */       gate.setYScaleArgumentString(this.representation.getScaleArgumentString(gateYChannel));
/*      */       
/*      */ 
/* 3052 */       gate.setXMinimum(this.representation.getMinimum(gateXChannel));
/*      */       
/*      */ 
/* 3055 */       gate.setXMaximum(this.representation.getMaximum(gateXChannel));
/*      */       
/*      */ 
/* 3058 */       gate.setYMinimum(this.representation.getMinimum(gateYChannel));
/*      */       
/*      */ 
/* 3061 */       gate.setYMaximum(this.representation.getMaximum(gateYChannel));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3070 */     this.select.setSelected(true);
/*      */     
/*      */ 
/* 3073 */     this.canvas.setMode(GateCanvas.SELECT);
/*      */     
/*      */ 
/*      */ 
/* 3077 */     selectGate(gate);
/*      */     
/*      */ 
/* 3080 */     this.gatesListModel.fireGateAdded();
/*      */     
/*      */ 
/* 3083 */     this.gateSetModel.fireTableStructureChanged();
/*      */     
/*      */ 
/* 3086 */     if (createGateSetP)
/*      */     {
/* 3088 */       addGateToGateSet(gate, this.activeGateSet);
/*      */     }
/*      */     
/* 3091 */     if (name.length() > 10) {
/* 3092 */       name = name.substring(0, 10) + "...";
/*      */     }
/* 3094 */     setStatus("<html>Population " + name + " was created.<br /> Double-click the gate to select it.</html>");
/*      */     
/*      */ 
/* 3097 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getGateCount()
/*      */   {
/* 3108 */     Gate[] gateArray = this.gates.toArray();
/*      */     
/*      */ 
/* 3111 */     int count = 0;
/*      */     
/*      */ 
/* 3114 */     for (int i = 0; i < gateArray.length; i++) {
/* 3115 */       if (!gateArray[i].isCompensation())
/*      */       {
/* 3117 */         count++;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3122 */     return count;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate[] getGates()
/*      */   {
/* 3133 */     Gate[] gateArray = this.gates.toArray();
/*      */     
/*      */ 
/* 3136 */     ArrayList gateList = new ArrayList(gateArray.length);
/*      */     
/*      */ 
/* 3139 */     for (int i = 0; i < gateArray.length; i++) {
/* 3140 */       if (!gateArray[i].isCompensation())
/*      */       {
/* 3142 */         gateList.add(gateArray[i]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3147 */     Gate[] gatesArray = new Gate[gateList.size()];
/*      */     
/*      */ 
/* 3150 */     gateList.toArray(gatesArray);
/*      */     
/*      */ 
/* 3153 */     return gatesArray;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate[] getVisibleGates(int xChannel, int yChannel)
/*      */   {
/* 3168 */     return getVisibleGates(xChannel, yChannel, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate[] getVisibleGates(int xChannel, int yChannel, boolean compensationP)
/*      */   {
/* 3183 */     if ((this.file == null) || (this.representation == null))
/*      */     {
/* 3185 */       return new Gate[0];
/*      */     }
/*      */     
/*      */ 
/* 3189 */     Gate[] gateArray = Gate.filter(this.gates.getGates(this.file.getFilename()), xChannel, yChannel);
/*      */     
/* 3191 */     if (gateArray.length <= 0)
/*      */     {
/* 3193 */       return new Gate[0];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3202 */     int compensationID = getCompensationID();
/*      */     
/*      */ 
/* 3205 */     int xScaleFlag = this.representation.getScaleFlag(xChannel);
/*      */     
/*      */ 
/* 3208 */     String xScaleArgument = this.representation.getScaleArgumentString(xChannel);
/*      */     
/*      */ 
/* 3211 */     double xMin = this.representation.getMinimum(xChannel);
/*      */     
/*      */ 
/* 3214 */     double xMax = this.representation.getMaximum(xChannel);
/*      */     
/*      */ 
/* 3217 */     int yScaleFlag = this.representation.getScaleFlag(yChannel);
/*      */     
/*      */ 
/* 3220 */     String yScaleArgument = this.representation.getScaleArgumentString(yChannel);
/*      */     
/*      */ 
/* 3223 */     double yMin = this.representation.getMinimum(yChannel);
/*      */     
/*      */ 
/* 3226 */     double yMax = this.representation.getMaximum(yChannel);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3234 */     ArrayList gateList = new ArrayList(gateArray.length);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3240 */     for (int i = 0; i < gateArray.length; i++)
/*      */     {
/* 3242 */       Gate gate = gateArray[i];
/*      */       
/* 3244 */       if (((!compensationP) || (gate.isCompensation())) && ((compensationP) || (!gate.isCompensation())))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3251 */         if (gate.getCompensationID() == compensationID)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3257 */           boolean flippedP = (gate.getXChannel() == yChannel) && (gate.getYChannel() == xChannel);
/*      */           
/* 3259 */           if (flippedP)
/*      */           {
/* 3261 */             if ((gate.getXScaleFlag() != yScaleFlag) || (gate.getYScaleFlag() != xScaleFlag)) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/* 3266 */             if (((xScaleArgument == null) && (gate.getYScaleArgumentString() != null)) || ((xScaleArgument != null) && (gate.getYScaleArgumentString() == null)) || ((xScaleArgument != null) && (!xScaleArgument.equals(gate.getYScaleArgumentString())))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 3273 */             if (((yScaleArgument == null) && (gate.getXScaleArgumentString() != null)) || ((yScaleArgument != null) && (gate.getXScaleArgumentString() == null)) || ((yScaleArgument != null) && (!yScaleArgument.equals(gate.getXScaleArgumentString())))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 3280 */             if ((gate.getYMinimum() != xMin) && (
/*      */             
/* 3282 */               (!Double.isNaN(gate.getYMinimum())) || (!Double.isNaN(xMin)))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3288 */             if ((gate.getYMaximum() != xMax) && (
/*      */             
/* 3290 */               (!Double.isNaN(gate.getYMaximum())) || (!Double.isNaN(xMax)))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3296 */             if ((gate.getXMinimum() != yMin) && (
/*      */             
/* 3298 */               (!Double.isNaN(gate.getXMinimum())) || (!Double.isNaN(yMin)))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3304 */             if (gate.getXMaximum() != yMax)
/*      */             {
/* 3306 */               if (!Double.isNaN(gate.getXMaximum())) continue; if (!Double.isNaN(yMax)) {
/*      */                 continue;
/*      */               }
/*      */               
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3314 */             if ((gate.getXScaleFlag() != xScaleFlag) || (gate.getYScaleFlag() != yScaleFlag)) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/* 3319 */             if (((xScaleArgument == null) && (gate.getXScaleArgumentString() != null)) || ((xScaleArgument != null) && (gate.getXScaleArgumentString() == null)) || ((xScaleArgument != null) && (!xScaleArgument.equals(gate.getXScaleArgumentString())))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 3326 */             if (((yScaleArgument == null) && (gate.getYScaleArgumentString() != null)) || ((yScaleArgument != null) && (gate.getYScaleArgumentString() == null)) || ((yScaleArgument != null) && (!yScaleArgument.equals(gate.getYScaleArgumentString())))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 3333 */             if ((gate.getXMinimum() != xMin) && (
/*      */             
/* 3335 */               (!Double.isNaN(gate.getXMinimum())) || (!Double.isNaN(xMin)))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3341 */             if ((gate.getXMaximum() != xMax) && (
/*      */             
/* 3343 */               (!Double.isNaN(gate.getXMaximum())) || (!Double.isNaN(xMax)))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3349 */             if ((gate.getYMinimum() != yMin) && (
/*      */             
/* 3351 */               (!Double.isNaN(gate.getYMinimum())) || (!Double.isNaN(yMin)))) {
/*      */               continue;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3357 */             if ((gate.getYMaximum() != yMax) && (
/*      */             
/* 3359 */               (!Double.isNaN(gate.getYMaximum())) || (!Double.isNaN(yMax)))) {
/*      */               continue;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 3367 */           gateList.add(gate);
/*      */         }
/*      */       }
/*      */     }
/* 3371 */     Gate[] gatesArray = new Gate[gateList.size()];
/* 3372 */     gateList.toArray(gatesArray);
/* 3373 */     return gatesArray;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   HashMap getGateMap()
/*      */   {
/* 3383 */     return this.gates.getGateMap();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate[] getSelectableGates()
/*      */   {
/* 3398 */     Gate[] gateArray = getGates();
/*      */     
/*      */ 
/* 3401 */     ArrayList gateList = new ArrayList(gateArray.length);
/*      */     
/*      */ 
/* 3404 */     boolean found = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3409 */     for (int i = 0; i < gateArray.length; i++) {
/* 3410 */       if ((!(gateArray[i] instanceof Quadrant)) && (!(gateArray[i] instanceof Split)))
/*      */       {
/*      */ 
/* 3413 */         if (gateArray[i].isStandAlone())
/*      */         {
/* 3415 */           found = false;
/*      */           
/*      */ 
/* 3418 */           int j = 0; for (int n = gateList.size(); j < n; j++)
/*      */           {
/* 3420 */             Gate gate = (Gate)gateList.get(j);
/*      */             
/* 3422 */             if (gateArray[i].isEquivalent(gate))
/*      */             {
/* 3424 */               found = true;
/*      */               
/* 3426 */               break;
/*      */             }
/*      */           }
/*      */           
/* 3430 */           if (!found)
/*      */           {
/* 3432 */             gateList.add(gateArray[i]);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 3437 */           gateList.add(gateArray[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3444 */     Gate[] gatesArray = new Gate[gateList.size()];
/*      */     
/*      */ 
/* 3447 */     gateList.toArray(gatesArray);
/*      */     
/*      */ 
/* 3450 */     return gatesArray;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate[] getMutableGates()
/*      */   {
/* 3462 */     if (this.file == null)
/*      */     {
/* 3464 */       return new Gate[0];
/*      */     }
/*      */     
/*      */ 
/* 3468 */     String filename = this.file.getFilename();
/*      */     
/*      */ 
/* 3471 */     Gate[] gateArray = getGates();
/*      */     
/*      */ 
/* 3474 */     ArrayList gateList = new ArrayList(gateArray.length);
/*      */     
/*      */ 
/* 3477 */     for (int i = 0; i < gateArray.length; i++) {
/* 3478 */       if ((!(gateArray[i] instanceof Quad)) && (!(gateArray[i] instanceof SplitRange)))
/*      */       {
/* 3480 */         if (gateArray[i].isGlobal())
/*      */         {
/* 3482 */           gateList.add(gateArray[i]);
/*      */         }
/* 3484 */         else if ((filename != null) && (gateArray[i].getFilename() != null) && (filename.equals(gateArray[i].getFilename())))
/*      */         {
/* 3486 */           gateList.add(gateArray[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3492 */     Gate[] gatesArray = new Gate[gateList.size()];
/* 3493 */     gateList.toArray(gatesArray);
/* 3494 */     return gatesArray;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void removeGate(Gate gate)
/*      */   {
/* 3504 */     removeGate(gate, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void removeGate(Gate gate, boolean spawnP)
/*      */   {
/* 3518 */     if (gate == null) {
/* 3519 */       return;
/*      */     }
/*      */     
/* 3522 */     if (isSelected(gate)) {
/* 3523 */       unselectGate(gate);
/* 3524 */       showGate(null);
/*      */     }
/*      */     
/*      */ 
/* 3528 */     boolean inActiveGateSetP = false;
/* 3529 */     if (this.activeGateSet != null) {
/* 3530 */       inActiveGateSetP = this.activeGateSet.contains(gate);
/*      */     }
/*      */     
/*      */ 
/* 3534 */     if (!spawnP) {
/* 3535 */       Gate[] gateArray = this.gates.toArray();
/* 3536 */       for (int i = 0; i < gateArray.length; i++) {
/* 3537 */         gateArray[i].removeSisterGate(gate);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3547 */     int removalIndex = -1;
/*      */     
/* 3549 */     for (int i = 0; i < this.gateSets.size(); i++) {
/* 3550 */       GateSet gateSet = (GateSet)this.gateSets.get(i);
/* 3551 */       gateSet.remove(gate);
/*      */       
/* 3553 */       if ((!spawnP) && (gateSet.getName().equals(gate.getName()))) {
/* 3554 */         removalIndex = i;
/*      */       }
/*      */       else {
/* 3557 */         for (int j = 0; j < this.gateSets.size(); j++) {
/* 3558 */           GateSet comparableGateSet = (GateSet)this.gateSets.get(j);
/* 3559 */           if ((i != j) && (gateSet.equals(comparableGateSet))) {
/* 3560 */             removalIndex = i;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3566 */     if (removalIndex != -1) {
/* 3567 */       removeGateSet((GateSet)this.gateSets.get(removalIndex));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3575 */     Gate[] gateArray = getMutableGates();
/* 3576 */     int index = -1;
/*      */     
/* 3578 */     if (!spawnP) {
/* 3579 */       for (int i = 0; i < gateArray.length; i++) {
/* 3580 */         if (gate.equals(gateArray[i])) {
/* 3581 */           index = i;
/* 3582 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3587 */     this.gates.remove(gate);
/* 3588 */     setDirtyGate();
/*      */     
/* 3590 */     if ((index >= 0) && (index < gateArray.length)) {
/* 3591 */       this.gatesListModel.fireGateRemoved(index);
/*      */     }
/* 3593 */     this.gateSetModel.fireTableStructureChanged();
/*      */     
/* 3595 */     if ((gate instanceof Quadrant)) {
/* 3596 */       removeQuadrantGateSet(((Quadrant)gate).getGateSet());
/* 3597 */     } else if ((gate instanceof Split)) {
/* 3598 */       removeSplitGateSet(((Split)gate).getGateSet());
/*      */     }
/*      */     
/* 3601 */     if (inActiveGateSetP) {
/* 3602 */       save();
/* 3603 */       firePlotChanged();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void select(Gate gate)
/*      */   {
/* 3614 */     if (gate == null) {
/* 3615 */       return;
/*      */     }
/*      */     
/*      */ 
/* 3619 */     if (this.gates.contains(gate))
/*      */     {
/* 3621 */       this.selectedGates.add(gate);
/*      */       
/* 3623 */       showGate(gate);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void selectGate(Gate gate)
/*      */   {
/* 3635 */     unselect();
/*      */     
/*      */ 
/* 3638 */     this.gatesList.setSelectedValue(gate.getName(), true);
/* 3639 */     select(gate);
/* 3640 */     setStatus("");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getSelectedCount()
/*      */   {
/* 3649 */     return this.selectedGates.size();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate[] getSelectedGates()
/*      */   {
/* 3658 */     return this.selectedGates.toArray();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate[] getSelectedGates(int xChannel, int yChannel)
/*      */   {
/* 3674 */     Gate[] gateArray = getVisibleGates(xChannel, yChannel);
/*      */     
/*      */ 
/* 3677 */     ArrayList gateList = new ArrayList(gateArray.length);
/*      */     
/*      */ 
/* 3680 */     for (int i = 0; i < gateArray.length; i++) {
/* 3681 */       if (isSelected(gateArray[i]))
/*      */       {
/* 3683 */         gateList.add(gateArray[i]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3688 */     Gate[] gatesArray = new Gate[gateList.size()];
/* 3689 */     gateList.toArray(gatesArray);
/* 3690 */     return gatesArray;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isSelected(Gate gate)
/*      */   {
/* 3701 */     return this.selectedGates.contains(gate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void unselectGate(Gate gate)
/*      */   {
/* 3710 */     this.selectedGates.remove(gate);
/* 3711 */     showGate(null);
/* 3712 */     this.gatesList.clearSelection();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void unselect()
/*      */   {
/* 3719 */     this.selectedGates.clear();
/* 3720 */     showGate(null);
/* 3721 */     this.gatesList.clearSelection();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   synchronized int getNextGateID()
/*      */   {
/* 3731 */     Gate[] gateArray = this.gates.toArray();
/* 3732 */     int newID = 0;
/*      */     
/* 3734 */     for (int i = 0; i < gateArray.length; i++) {
/* 3735 */       if (gateArray[i].getID() > newID)
/*      */       {
/* 3737 */         newID = gateArray[i].getID();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3742 */     newID++;
/*      */     
/* 3744 */     return newID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void makeGateGlobal(Gate gate)
/*      */   {
/* 3754 */     if (gate == null) {
/* 3755 */       return;
/*      */     }
/*      */     
/* 3758 */     if (gate.isGlobal()) {
/* 3759 */       return;
/*      */     }
/*      */     
/* 3762 */     if (gate.hasSisterGates()) {
/* 3763 */       int option = JOptionPane.showConfirmDialog(this, "Making the gate global will delete the gate's sister gates. Do you want to continue?", "Make the gate global?", 0, 2);
/*      */       
/* 3765 */       if (option == 0)
/*      */       {
/* 3767 */         removeSisters(gate);
/*      */         
/* 3769 */         gate.setGlobal(true);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 3774 */       gate.setGlobal(true);
/*      */     }
/*      */     
/* 3777 */     setDirtyGate();
/*      */     
/* 3779 */     showGate(gate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void makeGateStandAlone(Gate gate)
/*      */   {
/* 3788 */     if (gate == null) {
/* 3789 */       return;
/*      */     }
/*      */     
/* 3792 */     if (gate.isStandAlone()) {
/* 3793 */       return;
/*      */     }
/*      */     
/* 3796 */     gate.setStandAlone(true);
/* 3797 */     setDirtyGate();
/*      */     
/* 3799 */     if (this.file != null) {
/* 3800 */       gate.setFilename(this.file.getFilename());
/*      */     }
/*      */     
/*      */ 
/* 3804 */     spawnGates(gate);
/* 3805 */     Gate[] sisters = getSisterGates(gate);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3810 */     int i = 0; for (int n = this.gateSets.size(); i < n; i++)
/*      */     {
/* 3812 */       GateSet gateSet = (GateSet)this.gateSets.get(i);
/*      */       
/* 3814 */       if (gateSet.contains(gate))
/*      */       {
/* 3816 */         for (int j = 0; j < sisters.length; j++) {
/* 3817 */           gateSet.add(sisters[j]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3822 */     showGate(gate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void makeGateLocal(Gate gate)
/*      */   {
/* 3831 */     if (gate == null) {
/* 3832 */       return;
/*      */     }
/*      */     
/* 3835 */     if (gate.isLocal()) {
/* 3836 */       return;
/*      */     }
/*      */     
/* 3839 */     if (gate.hasSisterGates())
/*      */     {
/* 3841 */       int option = JOptionPane.showConfirmDialog(this, "Making the gate local will delete the gate's sister gates. Do you want to continue?", "Make the gate local?", 0, 2);
/*      */       
/* 3843 */       if (option == 0)
/*      */       {
/* 3845 */         removeSisters(gate);
/*      */         
/* 3847 */         gate.setLocal(true);
/*      */         
/* 3849 */         if (this.file != null) {
/* 3850 */           gate.setFilename(this.file.getFilename());
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 3856 */       gate.setLocal(true);
/*      */       
/* 3858 */       if (this.file != null)
/*      */       {
/* 3860 */         gate.setFilename(this.file.getFilename());
/*      */       }
/*      */     }
/*      */     
/* 3864 */     setDirtyGate();
/* 3865 */     showGate(gate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void applyGateToFiles(Gate gate, ArrayList<String> filenames)
/*      */   {
/* 3873 */     Gate[] sisterGates = getSisterGates(gate);
/*      */     
/*      */ 
/* 3876 */     for (String filename : filenames)
/*      */     {
/*      */ 
/*      */ 
/* 3880 */       int sampleIndex = filename.indexOf(" - ");
/* 3881 */       if (sampleIndex != -1) {
/* 3882 */         filename = filename.substring(0, sampleIndex);
/*      */       }
/*      */       
/* 3885 */       Gate[] matchingSisterGates = Gate.filter(sisterGates, filename);
/* 3886 */       if (matchingSisterGates.length > 0) {
/* 3887 */         moveGate(gate, matchingSisterGates[0]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void moveGate(Gate originalGate, Gate sisterGate) {
/* 3893 */     if ((originalGate == null) || (sisterGate == null)) {
/* 3894 */       return;
/*      */     }
/*      */     
/* 3897 */     if ((originalGate instanceof Ellipse)) {
/* 3898 */       Ellipse ellipse = (Ellipse)originalGate;
/* 3899 */       ((Ellipse)sisterGate).setPosition(ellipse.getDoubleX(), ellipse.getDoubleY(), ellipse.getMajor(), ellipse.getMinor(), ellipse.getAngle(), ellipse.getXChannel(), ellipse.getYChannel(), ellipse.getCompensationID(), ellipse.getXBins(), ellipse.getYBins(), ellipse.getXSize(), ellipse.getYSize());
/* 3900 */     } else if ((originalGate instanceof Polygon)) {
/* 3901 */       Polygon polygon = (Polygon)originalGate;
/* 3902 */       Point[] points = polygon.getPoints();
/* 3903 */       ((Polygon)sisterGate).setPosition(Arrays.asList(points), polygon.getXChannel(), polygon.getYChannel(), polygon.getCompensationID(), polygon.getXBins(), polygon.getYBins(), polygon.getXSize(), polygon.getYSize());
/* 3904 */     } else if ((originalGate instanceof Rectangle)) {
/* 3905 */       Rectangle rectangle = (Rectangle)originalGate;
/* 3906 */       ((Rectangle)sisterGate).setPosition(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), rectangle.getXChannel(), rectangle.getYChannel(), rectangle.getCompensationID(), rectangle.getXBins(), rectangle.getYBins(), rectangle.getXSize(), rectangle.getYSize());
/* 3907 */     } else if ((originalGate instanceof Quadrant)) {
/* 3908 */       Quadrant quadrant = (Quadrant)originalGate;
/* 3909 */       ((Quadrant)sisterGate).setPosition(quadrant.getX(), quadrant.getY(), quadrant.getWidth(), quadrant.getHeight(), quadrant.getXChannel(), quadrant.getYChannel(), quadrant.getCompensationID(), quadrant.getXBins(), quadrant.getYBins(), quadrant.getXSize(), quadrant.getYSize());
/* 3910 */     } else if ((originalGate instanceof Range)) {
/* 3911 */       Range range = (Range)originalGate;
/* 3912 */       ((Range)sisterGate).setPosition(range.getX(), range.getY(), range.getWidth(), range.getHeight(), range.getXChannel(), range.getCompensationID(), range.getXBins(), range.getXSize());
/* 3913 */     } else if ((originalGate instanceof Split)) {
/* 3914 */       Split split = (Split)originalGate;
/* 3915 */       ((Split)sisterGate).setPosition(split.getX(), split.getY(), split.getWidth(), split.getHeight(), split.getXChannel(), split.getCompensationID(), split.getXBins(), split.getXSize());
/*      */     }
/*      */     
/*      */ 
/* 3919 */     sisterGate.setXScaleFlag(originalGate.getXScaleFlag());
/* 3920 */     sisterGate.setYScaleFlag(originalGate.getYScaleFlag());
/* 3921 */     sisterGate.setXScaleArgumentString(originalGate.getXScaleArgumentString());
/* 3922 */     sisterGate.setYScaleArgumentString(originalGate.getYScaleArgumentString());
/* 3923 */     sisterGate.setXMinimum(originalGate.getXMinimum());
/* 3924 */     sisterGate.setXMaximum(originalGate.getXMaximum());
/* 3925 */     sisterGate.setYMinimum(originalGate.getYMinimum());
/* 3926 */     sisterGate.setYMaximum(originalGate.getYMaximum());
/*      */     
/*      */ 
/* 3929 */     sisterGate.setLabelX(originalGate.getLabelX());
/* 3930 */     sisterGate.setLabelY(originalGate.getLabelY());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate copyGate(int newID, Gate gate)
/*      */   {
/* 3942 */     if (gate == null) {
/* 3943 */       return null;
/*      */     }
/*      */     
/*      */     Gate copy;
/*      */     Gate copy;
/* 3948 */     if ((gate instanceof Ellipse)) {
/* 3949 */       Ellipse ellipse = (Ellipse)gate;
/* 3950 */       copy = new Ellipse(newID, ellipse.getDoubleX(), ellipse.getDoubleY(), ellipse.getMajor(), ellipse.getMinor(), ellipse.getAngle(), ellipse.getXChannel(), ellipse.getYChannel(), ellipse.getCompensationID(), ellipse.getXBins(), ellipse.getYBins(), ellipse.getXSize(), ellipse.getYSize()); } else { Gate copy;
/* 3951 */       if ((gate instanceof Polygon)) {
/* 3952 */         Polygon polygon = (Polygon)gate;
/* 3953 */         Point[] points = polygon.getPoints();
/* 3954 */         copy = new Polygon(newID, Arrays.asList(points), polygon.getXChannel(), polygon.getYChannel(), polygon.getCompensationID(), polygon.getXBins(), polygon.getYBins(), polygon.getXSize(), polygon.getYSize());
/*      */       } else { Gate copy;
/* 3956 */         if ((gate instanceof Rectangle)) {
/* 3957 */           Rectangle rectangle = (Rectangle)gate;
/* 3958 */           copy = new Rectangle(newID, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), rectangle.getXChannel(), rectangle.getYChannel(), rectangle.getCompensationID(), rectangle.getXBins(), rectangle.getYBins(), rectangle.getXSize(), rectangle.getYSize());
/*      */         } else { Gate copy;
/* 3960 */           if ((gate instanceof Quadrant)) {
/* 3961 */             Quadrant quadrant = (Quadrant)gate;
/* 3962 */             copy = new Quadrant(newID, quadrant.getX(), quadrant.getY(), quadrant.getWidth(), quadrant.getHeight(), quadrant.getXChannel(), quadrant.getYChannel(), quadrant.getCompensationID(), quadrant.getXBins(), quadrant.getYBins(), quadrant.getXSize(), quadrant.getYSize());
/*      */           } else { Gate copy;
/* 3964 */             if ((gate instanceof Range)) {
/* 3965 */               Range range = (Range)gate;
/* 3966 */               copy = new Range(newID, range.getX(), range.getY(), range.getWidth(), range.getHeight(), range.getXChannel(), range.getCompensationID(), range.getXBins(), range.getXSize());
/*      */             } else { Gate copy;
/* 3968 */               if ((gate instanceof Split)) {
/* 3969 */                 Split split = (Split)gate;
/* 3970 */                 copy = new Split(newID, split.getX(), split.getY(), split.getWidth(), split.getHeight(), split.getXChannel(), split.getCompensationID(), split.getXBins(), split.getXSize());
/*      */               }
/*      */               else {
/* 3973 */                 copy = null;
/*      */               }
/*      */             } } } } }
/* 3976 */     if (copy != null)
/*      */     {
/*      */ 
/* 3979 */       copy.setName(gate.getName());
/*      */       
/*      */ 
/* 3982 */       copy.setXScaleFlag(gate.getXScaleFlag());
/* 3983 */       copy.setYScaleFlag(gate.getYScaleFlag());
/* 3984 */       copy.setXScaleArgumentString(gate.getXScaleArgumentString());
/* 3985 */       copy.setYScaleArgumentString(gate.getYScaleArgumentString());
/* 3986 */       copy.setXMinimum(gate.getXMinimum());
/* 3987 */       copy.setXMaximum(gate.getXMaximum());
/* 3988 */       copy.setYMinimum(gate.getYMinimum());
/* 3989 */       copy.setYMaximum(gate.getYMaximum());
/*      */       
/*      */ 
/* 3992 */       copy.setLabelX(gate.getLabelX());
/* 3993 */       copy.setLabelY(gate.getLabelY());
/*      */     }
/*      */     
/* 3996 */     return copy;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate spawnGate(Gate gate, String filename)
/*      */   {
/* 4018 */     if (gate == null)
/*      */     {
/* 4020 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 4024 */     int newID = getNextGateID();
/*      */     
/*      */ 
/* 4027 */     Gate spawn = copyGate(newID, gate);
/*      */     
/* 4029 */     if (spawn != null)
/*      */     {
/*      */ 
/*      */ 
/* 4033 */       spawn.setFilename(filename);
/*      */       
/*      */ 
/* 4036 */       spawn.setGlobal(false);
/*      */       
/*      */ 
/* 4039 */       spawn.setStandAlone(true);
/*      */       
/*      */ 
/* 4042 */       spawn.setLocal(false);
/*      */       
/*      */ 
/* 4045 */       spawn.setLocked(gate.isLocked());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4058 */     return spawn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void spawnGatesNoOverwrite(Gate gate)
/*      */   {
/* 4069 */     if ((gate == null) || (this.bean == null)) {
/* 4070 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4078 */     Gate[] sisters = getSisterGates(gate);
/*      */     
/*      */ 
/* 4081 */     ArrayList gatedFiles = new ArrayList(sisters.length + 1);
/*      */     
/*      */ 
/* 4084 */     gatedFiles.add(gate.getFilename());
/*      */     
/*      */ 
/* 4087 */     for (int i = 0; i < sisters.length; i++)
/*      */     {
/* 4089 */       gatedFiles.add(sisters[i].getFilename());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4098 */     ArrayList spawnList = new ArrayList(this.files.length);
/*      */     
/*      */ 
/* 4101 */     for (int i = 0; i < this.files.length; i++) {
/* 4102 */       if (!gatedFiles.contains(this.files[i].getFilename()))
/*      */       {
/* 4104 */         spawnList.add(this.files[i].getFilename());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 4109 */     String[] spawnArray = new String[spawnList.size()];
/*      */     
/*      */ 
/* 4112 */     spawnList.toArray(spawnArray);
/*      */     
/*      */ 
/* 4115 */     spawnGates(gate, spawnArray);
/*      */     
/*      */ 
/* 4118 */     sisters = getSisterGates(gate);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4123 */     int i = 0; for (int n = this.gateSets.size(); i < n; i++)
/*      */     {
/* 4125 */       GateSet gateSet = (GateSet)this.gateSets.get(i);
/*      */       
/* 4127 */       if (gateSet.contains(gate))
/*      */       {
/* 4129 */         for (int j = 0; j < sisters.length; j++) {
/* 4130 */           if (!gateSet.contains(sisters[j]))
/*      */           {
/* 4132 */             gateSet.add(sisters[j]);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 4140 */       gateSet.print();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void spawnGatesChoose(Gate gate, ArrayList files)
/*      */   {
/* 4157 */     if ((gate == null) || (files == null) || (files.size() <= 0)) {
/* 4158 */       return;
/*      */     }
/*      */     
/* 4161 */     gate.setStandAlone(true);
/*      */     
/* 4163 */     if (this.file != null) {
/* 4164 */       gate.setFilename(this.file.getFilename());
/* 4165 */       files.remove(this.file.getFilename());
/*      */     }
/*      */     
/*      */ 
/* 4169 */     String[] filenames = new String[files.size()];
/* 4170 */     files.toArray(filenames);
/* 4171 */     spawnGates(gate, filenames);
/*      */     
/*      */ 
/* 4174 */     Gate[] sisters = getSisterGates(gate);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4179 */     int i = 0; for (int n = this.gateSets.size(); i < n; i++)
/*      */     {
/* 4181 */       GateSet gateSet = (GateSet)this.gateSets.get(i);
/*      */       
/* 4183 */       if (gateSet.contains(gate))
/*      */       {
/* 4185 */         for (int j = 0; j < sisters.length; j++) {
/* 4186 */           gateSet.add(sisters[j]);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4191 */       gateSet.print();
/*      */     }
/*      */     
/* 4194 */     fireGateSetChanged();
/* 4195 */     this.gatesListModel.fireGateChanged();
/* 4196 */     fireCanvasChanged();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void spawnGates(Gate gate)
/*      */   {
/* 4206 */     if ((gate == null) || (this.bean == null))
/*      */     {
/* 4208 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4217 */     ArrayList spawnList = new ArrayList(this.files.length);
/*      */     
/*      */ 
/* 4220 */     String filename = gate.getFilename();
/*      */     
/* 4222 */     if (filename != null)
/*      */     {
/*      */ 
/*      */ 
/* 4226 */       for (int i = 0; i < this.files.length; i++) {
/* 4227 */         if (!filename.equals(this.files[i].getFilename()))
/*      */         {
/* 4229 */           spawnList.add(this.files[i].getFilename());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 4235 */     String[] spawnArray = new String[spawnList.size()];
/*      */     
/*      */ 
/* 4238 */     spawnList.toArray(spawnArray);
/*      */     
/*      */ 
/* 4241 */     spawnGates(gate, spawnArray);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void spawnGates(Gate gate, String[] files)
/*      */   {
/* 4253 */     if ((gate == null) || (files == null) || (files.length <= 0)) {
/* 4254 */       return;
/*      */     }
/*      */     
/*      */ 
/* 4258 */     ArrayList spawnList = new ArrayList(files.length);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4263 */     for (int i = 0; i < files.length; i++)
/*      */     {
/* 4265 */       Gate spawn = spawnGate(gate, files[i]);
/*      */       
/*      */ 
/* 4268 */       spawnList.add(spawn);
/*      */       
/*      */ 
/* 4271 */       this.gates.add(spawn);
/*      */       
/*      */ 
/* 4274 */       setDirtyGate();
/*      */       
/*      */ 
/* 4277 */       gate.addSisterGate(spawn.getID());
/*      */       
/* 4279 */       if ((spawn instanceof Quadrant))
/*      */       {
/* 4281 */         addQuadrantGateSet((Quadrant)spawn, false);
/*      */       }
/* 4283 */       else if ((spawn instanceof Split))
/*      */       {
/* 4285 */         addSplitGateSet((Split)spawn, false);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4294 */     Gate[] sisters = getSisterGates(gate);
/*      */     
/*      */ 
/* 4297 */     int i = 0; for (int n = spawnList.size(); i < n; i++) {
/* 4298 */       Gate spawn = (Gate)spawnList.get(i);
/* 4299 */       spawn.addSisterGate(gate.getID());
/* 4300 */       for (int j = 0; j < sisters.length; j++) {
/* 4301 */         if (spawn.getID() != sisters[j].getID()) {
/* 4302 */           spawn.addSisterGate(sisters[j]);
/* 4303 */           sisters[j].addSisterGate(spawn);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 4309 */     this.gateSetModel.fireTableStructureChanged();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4315 */     System.out.println("Sister Gate IDs:");
/*      */     
/*      */ 
/* 4318 */     for (int i = 0; i < sisters.length; i++) {
/* 4319 */       System.out.println("\tGate ID: " + sisters[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Gate[] getSisterGates(Gate gate)
/*      */   {
/* 4332 */     if (gate == null) {
/* 4333 */       return new Gate[0];
/*      */     }
/*      */     
/* 4336 */     int[] sisters = gate.getSisterGates();
/*      */     
/*      */ 
/* 4339 */     ArrayList gateList = new ArrayList(sisters.length);
/*      */     
/*      */ 
/* 4342 */     HashMap gateMap = getGateMap();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4347 */     for (int i = 0; i < sisters.length; i++)
/*      */     {
/* 4349 */       Gate sister = (Gate)gateMap.get(new Integer(sisters[i]));
/*      */       
/* 4351 */       if (sister != null) {
/* 4352 */         gateList.add(sister);
/*      */       }
/*      */     }
/*      */     
/* 4356 */     Gate[] gateArray = new Gate[gateList.size()];
/* 4357 */     gateList.toArray(gateArray);
/*      */     
/* 4359 */     return gateArray;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void removeSisters(Gate gate)
/*      */   {
/* 4371 */     if (gate == null) {
/* 4372 */       return;
/*      */     }
/*      */     
/*      */ 
/* 4376 */     Gate[] sisters = getSisterGates(gate);
/*      */     
/* 4378 */     for (int i = 0; i < sisters.length; i++) {
/* 4379 */       removeGate(sisters[i], true);
/*      */       
/*      */ 
/* 4382 */       System.out.println("Removing sister gate ID: " + sisters[i].getID());
/*      */     }
/*      */     
/*      */ 
/* 4386 */     gate.clearSisterGates();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean addGateSet(GateSet set)
/*      */   {
/* 4402 */     return addGateSet(set, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean addGateSet(GateSet set, boolean promptP)
/*      */   {
/* 4414 */     if (set == null) {
/* 4415 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 4419 */     if (promptP)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4426 */       String name = JOptionPane.showInputDialog(this, "Please enter a name for the population:", set.getName());
/*      */       
/* 4428 */       if (name == null)
/*      */       {
/* 4430 */         return false;
/*      */       }
/*      */       
/*      */ 
/* 4434 */       set.setName(name);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4440 */     boolean addP = this.gateSets.add(set);
/*      */     
/* 4442 */     if (!addP)
/*      */     {
/* 4444 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4450 */     fireGateSetChanged();
/*      */     
/*      */ 
/* 4453 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void removeGateSet(GateSet set)
/*      */   {
/* 4463 */     if (set == null) {
/* 4464 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4470 */     this.gateSets.remove(set);
/*      */     
/*      */ 
/* 4473 */     fireGateSetChanged();
/*      */     
/* 4475 */     if (set.equals(this.activeGateSet))
/*      */     {
/* 4477 */       setActiveGateSet(null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void addQuadrantGateSet(Quadrant gate)
/*      */   {
/* 4489 */     addQuadrantGateSet(gate, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void addQuadrantGateSet(Quadrant gate, boolean addGateP)
/*      */   {
/* 4503 */     if (gate == null)
/*      */     {
/* 4505 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4510 */     int newID = getNextGateID();
/*      */     
/* 4512 */     if (newID <= gate.getID())
/*      */     {
/* 4514 */       newID = gate.getID() + 1;
/*      */     }
/*      */     
/*      */ 
/* 4518 */     QuadrantGateSet quadGateSet = new QuadrantGateSet(getNextGateSetID(), gate, newID, newID + 1, newID + 2, newID + 3);
/*      */     
/*      */ 
/* 4521 */     this.gateSets.add(quadGateSet);
/*      */     
/*      */ 
/* 4524 */     setDirtyGateSet();
/*      */     
/*      */ 
/* 4527 */     Gate[] quadGates = quadGateSet.toArray();
/*      */     
/*      */ 
/* 4530 */     for (int i = 0; i < quadGates.length; i++) {
/* 4531 */       this.gates.add(quadGates[i]);
/*      */     }
/*      */     
/* 4534 */     if (addGateP)
/*      */     {
/* 4536 */       if (!addGate(gate, false))
/*      */       {
/* 4538 */         for (int i = 0; i < quadGates.length; i++) {
/* 4539 */           this.gates.remove(quadGates[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 4545 */     this.gateSetModel.fireTableStructureChanged();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void removeQuadrantGateSet(QuadrantGateSet set)
/*      */   {
/* 4555 */     if (set == null)
/*      */     {
/* 4557 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4562 */     Gate[] quadGates = set.toArray();
/*      */     
/*      */ 
/* 4565 */     for (int i = 0; i < quadGates.length; i++) {
/* 4566 */       removeGate(quadGates[i]);
/*      */     }
/*      */     
/*      */ 
/* 4570 */     removeGateSet(set);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void addSplitGateSet(Split gate)
/*      */   {
/* 4581 */     addSplitGateSet(gate, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void addSplitGateSet(Split gate, boolean addGateP)
/*      */   {
/* 4595 */     if (gate == null)
/*      */     {
/* 4597 */       return;
/*      */     }
/*      */     
/*      */ 
/* 4601 */     int newID = getNextGateID();
/*      */     
/* 4603 */     if (newID <= gate.getID())
/*      */     {
/* 4605 */       newID = gate.getID() + 1;
/*      */     }
/*      */     
/*      */ 
/* 4609 */     SplitGateSet splitGateSet = new SplitGateSet(getNextGateSetID(), gate, newID, newID + 1);
/*      */     
/*      */ 
/* 4612 */     this.gateSets.add(splitGateSet);
/*      */     
/*      */ 
/* 4615 */     setDirtyGateSet();
/*      */     
/*      */ 
/* 4618 */     Gate[] splitGates = splitGateSet.toArray();
/*      */     
/*      */ 
/* 4621 */     for (int i = 0; i < splitGates.length; i++) {
/* 4622 */       this.gates.add(splitGates[i]);
/*      */     }
/*      */     
/* 4625 */     if (addGateP)
/*      */     {
/* 4627 */       if (!addGate(gate, false))
/*      */       {
/* 4629 */         for (int i = 0; i < splitGates.length; i++) {
/* 4630 */           this.gates.remove(splitGates[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 4636 */     this.gateSetModel.fireTableStructureChanged();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void removeSplitGateSet(SplitGateSet set)
/*      */   {
/* 4646 */     if (set == null)
/*      */     {
/* 4648 */       return;
/*      */     }
/*      */     
/*      */ 
/* 4652 */     Gate[] splitGates = set.toArray();
/*      */     
/*      */ 
/* 4655 */     for (int i = 0; i < splitGates.length; i++) {
/* 4656 */       removeGate(splitGates[i]);
/*      */     }
/*      */     
/*      */ 
/* 4660 */     removeGateSet(set);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   GateSet[] getPopulations()
/*      */   {
/* 4671 */     ArrayList populations = new ArrayList(this.gateSets.size());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4676 */     int i = 0; for (int n = this.gateSets.size(); i < n; i++) {
/* 4677 */       GateSet gateSet = (GateSet)this.gateSets.get(i);
/*      */       
/* 4679 */       if ((gateSet != null) && (!(gateSet instanceof SpecialGateSet)) && (!(gateSet instanceof CompensationGateSet)))
/*      */       {
/* 4681 */         populations.add(gateSet);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 4686 */     GateSet[] populationArray = new GateSet[populations.size()];
/* 4687 */     populations.toArray(populationArray);
/* 4688 */     return populationArray;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   synchronized int getNextGateSetID()
/*      */   {
/* 4698 */     int newID = 0;
/*      */     
/*      */ 
/*      */ 
/* 4702 */     int i = 0; for (int n = this.gateSets.size(); i < n; i++) {
/* 4703 */       GateSet gateSet = (GateSet)this.gateSets.get(i);
/*      */       
/* 4705 */       if (gateSet.getID() > newID)
/*      */       {
/* 4707 */         newID = gateSet.getID();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 4712 */     newID++;
/*      */     
/*      */ 
/* 4715 */     return newID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void populationAbove()
/*      */   {
/* 4724 */     if (this.activeGateSet == null)
/*      */     {
/* 4726 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4731 */     int targetCount = this.activeGateSet.size() - 1;
/*      */     
/* 4733 */     if (targetCount <= 0)
/*      */     {
/* 4735 */       setActiveGateSet(null);
/*      */       
/* 4737 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4743 */     GateSet[] populations = getPopulations();
/*      */     
/*      */ 
/* 4746 */     ArrayList populationList = new ArrayList();
/*      */     
/*      */ 
/* 4749 */     Gate[] gateArray = this.activeGateSet.toArray();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4754 */     for (int i = 0; i < populations.length; i++) {
/* 4755 */       if (populations[i].size() == targetCount)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 4760 */         int count = 0;
/*      */         
/*      */ 
/* 4763 */         for (int j = 0; j < gateArray.length; j++) {
/* 4764 */           if (populations[i].contains(gateArray[j]))
/*      */           {
/* 4766 */             count++;
/*      */           }
/*      */         }
/*      */         
/* 4770 */         if (count == targetCount)
/*      */         {
/* 4772 */           populationList.add(populations[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4777 */     if (populationList.size() == 1)
/*      */     {
/* 4779 */       setActiveGateSet((GateSet)populationList.get(0));
/*      */     }
/* 4781 */     else if (populationList.size() > 1)
/*      */     {
/*      */ 
/*      */ 
/* 4785 */       GateSet[] populationsArray = new GateSet[populationList.size()];
/*      */       
/*      */ 
/* 4788 */       populationList.toArray(populationsArray);
/*      */       
/*      */ 
/* 4791 */       this.selectPopulationListModel.setPopulations(populationsArray);
/*      */       
/*      */ 
/* 4794 */       this.selectPopulationFrame.setVisible(true);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void populationBelow()
/*      */   {
/* 4805 */     GateSet[] populations = getPopulations();
/*      */     
/* 4807 */     if ((populations == null) || (populations.length <= 0))
/*      */     {
/* 4809 */       return;
/*      */     }
/*      */     
/*      */ 
/* 4813 */     ArrayList populationList = new ArrayList(populations.length);
/*      */     
/*      */ 
/* 4816 */     Gate[] selectedGates = getSelectedGates();
/*      */     
/*      */ 
/*      */ 
/* 4820 */     if (this.activeGateSet == null)
/*      */     {
/*      */ 
/*      */ 
/* 4824 */       for (int i = 0; i < populations.length; i++) {
/* 4825 */         if (populations[i].size() == 1)
/*      */         {
/*      */ 
/*      */ 
/* 4829 */           boolean ignoreP = false;
/*      */           
/*      */ 
/* 4832 */           for (int j = 0; j < selectedGates.length; j++) {
/* 4833 */             if (!populations[i].contains(selectedGates[j]))
/*      */             {
/* 4835 */               ignoreP = true;
/*      */               
/* 4837 */               break;
/*      */             }
/*      */           }
/*      */           
/* 4841 */           if (!ignoreP)
/*      */           {
/* 4843 */             populationList.add(populations[i]);
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 4852 */       int numGates = this.activeGateSet.size();
/*      */       
/*      */ 
/*      */ 
/* 4856 */       int targetCount = numGates + 1;
/*      */       
/*      */ 
/* 4859 */       Gate[] gateArray = this.activeGateSet.toArray();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 4864 */       for (int i = 0; i < populations.length; i++) {
/* 4865 */         if (populations[i].size() == targetCount)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 4870 */           int count = 0;
/*      */           
/*      */ 
/* 4873 */           for (int j = 0; j < gateArray.length; j++) {
/* 4874 */             if (populations[i].contains(gateArray[j]))
/*      */             {
/* 4876 */               count++;
/*      */             }
/*      */           }
/*      */           
/* 4880 */           if (count == numGates)
/*      */           {
/*      */ 
/*      */ 
/* 4884 */             boolean ignoreP = false;
/*      */             
/*      */ 
/* 4887 */             for (int j = 0; j < selectedGates.length; j++) {
/* 4888 */               if (!populations[i].contains(selectedGates[j]))
/*      */               {
/* 4890 */                 ignoreP = true;
/*      */                 
/* 4892 */                 break;
/*      */               }
/*      */             }
/*      */             
/* 4896 */             if (!ignoreP)
/*      */             {
/* 4898 */               populationList.add(populations[i]);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4905 */     if (populationList.size() <= 0)
/*      */     {
/* 4907 */       if (selectedGates.length == 1)
/*      */       {
/* 4909 */         addGateToActiveGateSet(selectedGates[0]);
/*      */       }
/*      */     }
/* 4912 */     else if (populationList.size() == 1)
/*      */     {
/* 4914 */       setActiveGateSet((GateSet)populationList.get(0));
/*      */     }
/* 4916 */     else if (populationList.size() > 1)
/*      */     {
/*      */ 
/*      */ 
/* 4920 */       GateSet[] populationArray = new GateSet[populationList.size()];
/*      */       
/*      */ 
/* 4923 */       populationList.toArray(populationArray);
/*      */       
/*      */ 
/* 4926 */       this.selectPopulationListModel.setPopulations(populationArray);
/*      */       
/*      */ 
/* 4929 */       this.selectPopulationFrame.setVisible(true);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   GateSet addGateToGateSet(Gate gate, GateSet gateSet)
/*      */   {
/* 4944 */     if (gate == null)
/*      */     {
/* 4946 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4953 */     Gate[] gates = null;
/*      */     
/* 4955 */     if (gateSet != null)
/*      */     {
/* 4957 */       if (gateSet.contains(gate))
/*      */       {
/* 4959 */         return gateSet;
/*      */       }
/*      */       
/*      */ 
/* 4963 */       gates = gateSet.toArray();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4968 */     ArrayList gateList = new ArrayList(1);
/*      */     
/* 4970 */     if (gates != null)
/*      */     {
/* 4972 */       gateList.addAll(Arrays.asList(gates));
/*      */     }
/*      */     
/*      */ 
/* 4976 */     gateList.add(gate);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4984 */     GateSet[] populations = getPopulations();
/*      */     
/*      */ 
/* 4987 */     for (int i = 0; i < populations.length; i++) {
/* 4988 */       if (populations[i].containsOnly(gateList))
/*      */       {
/* 4990 */         System.out.println("Returning current population");
/* 4991 */         return populations[i];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5000 */     System.out.println("Making a new gate set....");
/*      */     
/*      */ 
/* 5003 */     GateSet newGateSet = new GateSet(getNextGateSetID());
/*      */     
/*      */ 
/* 5006 */     newGateSet.setName(gate.getName());
/*      */     
/*      */ 
/* 5009 */     int i = 0; for (int n = gateList.size(); i < n; i++)
/*      */     {
/* 5011 */       newGateSet.add((Gate)gateList.get(i));
/*      */     }
/*      */     
/*      */ 
/* 5015 */     if (addGateSet(newGateSet, false))
/*      */     {
/* 5017 */       return newGateSet;
/*      */     }
/*      */     
/*      */ 
/* 5021 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void addGateToActiveGateSet(Gate gate)
/*      */   {
/* 5032 */     if ((gate == null) || ((gate instanceof Quadrant)) || ((gate instanceof Split)))
/*      */     {
/* 5034 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 5040 */     Gate[] gates = null;
/*      */     
/* 5042 */     if (this.activeGateSet != null) {
/* 5043 */       if (this.activeGateSet.contains(gate)) {
/* 5044 */         return;
/*      */       }
/* 5046 */       gates = this.activeGateSet.toArray();
/*      */     }
/*      */     
/* 5049 */     ArrayList gateList = new ArrayList(1);
/* 5050 */     if (gates != null) {
/* 5051 */       gateList.addAll(Arrays.asList(gates));
/*      */     }
/* 5053 */     gateList.add(gate);
/*      */     
/* 5055 */     GateSet[] populations = getPopulations();
/* 5056 */     for (int i = 0; i < populations.length; i++) {
/* 5057 */       if (populations[i].containsOnly(gateList)) {
/* 5058 */         setActiveGateSet(populations[i]);
/* 5059 */         break;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setActiveGateSet(int id)
/*      */   {
/* 5077 */     if (id <= 0)
/*      */     {
/* 5079 */       setActiveGateSet(null);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 5085 */       GateSet[] populations = getPopulations();
/*      */       
/*      */ 
/* 5088 */       for (int i = 0; i < populations.length; i++) {
/* 5089 */         if (populations[i].getID() == id)
/*      */         {
/* 5091 */           setActiveGateSet(populations[i]);
/*      */           
/* 5093 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 5098 */       setActiveGateSet(null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setActiveGateSet(GateSet set)
/*      */   {
/* 5109 */     if (((this.activeGateSet == null) && (set == null)) || ((this.activeGateSet != null) && (this.activeGateSet.equals(set))))
/*      */     {
/*      */ 
/* 5112 */       return;
/*      */     }
/*      */     
/*      */ 
/* 5116 */     this.activeGateSet = set;
/*      */     
/*      */ 
/* 5119 */     int index = -1;
/*      */     
/* 5121 */     if (this.activeGateSet == null)
/*      */     {
/* 5123 */       if (this.representation != null)
/*      */       {
/* 5125 */         this.representation.clearGateSetIDs();
/*      */         
/*      */ 
/* 5128 */         fireRepresentationChanged(false);
/*      */       }
/*      */       
/*      */ 
/* 5132 */       index = 0;
/*      */     }
/*      */     else
/*      */     {
/* 5136 */       if (this.representation != null)
/*      */       {
/*      */ 
/*      */ 
/* 5140 */         this.representation.clearGateSetIDs();
/*      */         
/*      */ 
/* 5143 */         this.representation.addGateSetID(this.activeGateSet.getID());
/*      */         
/*      */ 
/* 5146 */         fireRepresentationChanged(false);
/*      */       }
/*      */       
/* 5149 */       if ((isGateDirty()) || (isGateSetDirty()))
/*      */       {
/* 5151 */         save();
/*      */       }
/*      */       
/*      */ 
/* 5155 */       GateSet[] populations = getPopulations();
/*      */       
/*      */ 
/* 5158 */       for (int i = 0; i < populations.length; i++) {
/* 5159 */         if (this.activeGateSet.equals(populations[i]))
/*      */         {
/* 5161 */           index = i + 1;
/*      */           
/* 5163 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5173 */     if ((this.populationList != null) && (this.populationList.getSelectedIndex() != index) && (index >= 0) && (index < this.populationList.getItemCount()))
/*      */     {
/*      */ 
/*      */ 
/* 5177 */       this.bypassP = true;
/*      */       
/*      */ 
/* 5180 */       this.populationList.setSelectedIndex(index);
/*      */       
/*      */ 
/* 5183 */       this.bypassP = false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 5188 */     firePlotChanged();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isActiveGateSet(GateSet set)
/*      */   {
/* 5198 */     if (this.activeGateSet == null)
/*      */     {
/* 5200 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 5204 */     return this.activeGateSet.equals(set);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   synchronized int getNextRepresentationID()
/*      */   {
/* 5220 */     int newID = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 5225 */     int i = 0; for (int n = this.representations.size(); i < n; i++)
/*      */     {
/* 5227 */       Representation rep = (Representation)this.representations.get(i);
/*      */       
/* 5229 */       if (rep.getID() > newID)
/*      */       {
/* 5231 */         newID = rep.getID();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 5236 */     newID++;
/*      */     
/*      */ 
/* 5239 */     return newID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean addRepresentation(Representation rep)
/*      */   {
/* 5250 */     if (rep == null)
/*      */     {
/* 5252 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 5256 */     boolean addP = this.representations.add(rep);
/*      */     
/* 5258 */     if (addP)
/*      */     {
/* 5260 */       setDirtyRepresentation();
/*      */     }
/*      */     
/*      */ 
/* 5264 */     return addP;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void displayGate(Gate gate)
/*      */   {
/* 5279 */     if (gate == null)
/*      */     {
/* 5281 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 5286 */     int xChannel = getXChannel();
/* 5287 */     int yChannel = getYChannel();
/*      */     
/*      */ 
/* 5290 */     int gateXChannel = gate.getXChannel();
/* 5291 */     int gateYChannel = gate.getYChannel();
/*      */     
/* 5293 */     if ((gateXChannel != xChannel) || (gateYChannel != yChannel))
/*      */     {
/*      */ 
/* 5296 */       if (gateXChannel != xChannel)
/*      */       {
/* 5298 */         setXChannel(gateXChannel);
/*      */       }
/*      */       
/* 5301 */       if (gateYChannel != yChannel)
/*      */       {
/* 5303 */         setYChannel(gateYChannel);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 5308 */     setCompensationID(gate.getCompensationID());
/*      */     
/* 5310 */     if (this.representation != null)
/*      */     {
/* 5312 */       this.representation.setScaleFlag(gateXChannel, gate.getXScaleFlag());
/* 5313 */       this.representation.setScaleFlag(gateYChannel, gate.getYScaleFlag());
/* 5314 */       this.representation.setScaleArgumentString(gateXChannel, gate.getXScaleArgumentString());
/* 5315 */       this.representation.setScaleArgumentString(gateYChannel, gate.getYScaleArgumentString());
/* 5316 */       this.representation.setMinimum(gateXChannel, gate.getXMinimum());
/* 5317 */       this.representation.setMinimum(gateYChannel, gate.getYMinimum());
/* 5318 */       this.representation.setMaximum(gateXChannel, gate.getXMaximum());
/* 5319 */       this.representation.setMaximum(gateYChannel, gate.getYMaximum());
/*      */       
/*      */ 
/* 5322 */       fireRepresentationChanged(false);
/*      */     }
/*      */     
/*      */ 
/* 5326 */     firePlotChanged();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void showGate(Gate gate)
/*      */   {
/* 5336 */     if (this.gatePanel != null)
/*      */     {
/* 5338 */       this.gatePanel.setGate(gate);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void checkGate()
/*      */   {
/* 5350 */     System.out.println("BUTTON CLICK: applet = " + this.applet + " rep = " + this.representation + "rep ID = " + this.representation.getID());
/* 5351 */     if ((this.applet != null) && (this.representation != null))
/*      */     {
/* 5353 */       if ((isGateDirty()) || (isRepresentationDirty()))
/*      */       {
/* 5355 */         save();
/*      */       }
/* 5357 */       System.out.println("In gateclient.checkgate..  calling applet.checkGate(" + this.representation.getID() + ")");
/*      */       
/* 5359 */       int populationID = 0;
/* 5360 */       if (this.activeGateSet != null) {
/* 5361 */         populationID = this.activeGateSet.getID();
/*      */       }
/* 5363 */       this.applet.checkGate(this.gatePanel.getGate().getID(), populationID, this.representation.getPlotType());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void fireGateNameChanged()
/*      */   {
/* 5378 */     setDirtyGate();
/*      */     
/*      */ 
/* 5381 */     this.gatesListModel.fireGateChanged();
/*      */     
/*      */ 
/* 5384 */     this.gateSetModel.fireTableStructureChanged();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void fireGateSetChanged()
/*      */   {
/* 5392 */     setDirtyGateSet();
/*      */     
/*      */ 
/* 5395 */     this.gateSetModel.fireTableStructureChanged();
/*      */     
/*      */ 
/* 5398 */     populatePopulationList();
/*      */     
/*      */ 
/* 5401 */     this.populationTree.updateTree();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void fireRepresentationChanged(boolean updatePlotP)
/*      */   {
/* 5411 */     if ((this.representation != null) && (this.representation.getID() > 0))
/*      */     {
/* 5413 */       setDirtyRepresentation();
/*      */     }
/*      */     
/* 5416 */     if (updatePlotP)
/*      */     {
/* 5418 */       firePlotChanged();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void fireCanvasChanged()
/*      */   {
/* 5426 */     if (this.canvas != null)
/*      */     {
/* 5428 */       this.canvas.repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void firePlotChanged()
/*      */   {
/* 5439 */     updatePlot();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   URL getPlotURL(URL plotURL, int xChannel, int yChannel, int plotCount)
/*      */   {
/* 5457 */     if ((this.representation == null) || (this.file == null) || (plotURL == null))
/*      */     {
/* 5459 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5472 */     int plotType = this.representation.getPlotType();
/*      */     
/*      */ 
/* 5475 */     boolean annotationP = this.representation.drawAnnotation();
/*      */     
/*      */ 
/* 5478 */     int axisBins = this.representation.getAxisBins();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5485 */     if (yChannel == -1)
/*      */     {
/* 5487 */       this.representation.setPlotType(1);
/*      */     }
/*      */     
/*      */ 
/* 5491 */     this.representation.setAnnotation(false);
/*      */     
/*      */ 
/* 5494 */     this.representation.setAxisBins(getAxisBins());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5500 */     int[] gateSetIDs = new int[0];
/*      */     
/* 5502 */     if (this.activeGateSet != null)
/*      */     {
/* 5504 */       gateSetIDs = new int[1];
/* 5505 */       gateSetIDs[0] = this.activeGateSet.getID();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5514 */     StringBuffer plotParams = new StringBuffer();
/*      */     
/*      */ 
/* 5517 */     plotParams.append("?");
/* 5518 */     plotParams.append(this.representation.encode(this.experimentID, this.file.getFilename(), getCompensationID(), gateSetIDs, xChannel, yChannel));
/*      */     
/*      */ 
/* 5521 */     plotParams.append("&plotCount=");
/* 5522 */     plotParams.append(plotCount);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5528 */     this.representation.setPlotType(plotType);
/* 5529 */     this.representation.setAnnotation(annotationP);
/* 5530 */     this.representation.setAxisBins(axisBins);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5536 */     URL url = null;
/*      */     
/*      */     try
/*      */     {
/* 5540 */       url = new URL(plotURL.toString() + plotParams.toString());
/*      */     }
/*      */     catch (MalformedURLException murle)
/*      */     {
/* 5544 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 5548 */     System.out.println(url.toString());
/*      */     
/*      */ 
/*      */ 
/* 5552 */     return url;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void updatePlot()
/*      */   {
/* 5560 */     System.out.println("Updating plot...");
/*      */     
/* 5562 */     if ((this.file == null) || (this.representation == null))
/*      */     {
/* 5564 */       return;
/*      */     }
/*      */     
/*      */ 
/* 5568 */     this.plotThread = null;
/*      */     
/*      */ 
/* 5571 */     int channelCount = this.file.getChannelCount();
/*      */     
/*      */ 
/* 5574 */     int xChannel = getXChannel();
/*      */     
/*      */ 
/* 5577 */     int yChannel = getYChannel();
/*      */     
/* 5579 */     if ((xChannel < 0) || (yChannel < -1) || (xChannel >= channelCount) || (yChannel >= channelCount))
/*      */     {
/* 5581 */       return;
/*      */     }
/*      */     
/* 5584 */     if (this.xAxisScaleLabel != null)
/*      */     {
/* 5586 */       this.xAxisScaleLabel.setText(getScaleLabel(this.representation.getScaleFlag(xChannel)));
/*      */     }
/*      */     
/* 5589 */     if (this.yAxisScaleLabel != null)
/*      */     {
/* 5591 */       this.yAxisScaleLabel.setText(getScaleLabel(this.representation.getScaleFlag(yChannel)));
/*      */     }
/*      */     
/*      */ 
/* 5595 */     setStatus("Getting plot...");
/*      */     
/*      */ 
/* 5598 */     setCursor(Cursor.getPredefinedCursor(3));
/*      */     
/* 5600 */     if ((this.plotCache != null) && (xChannel >= 0) && (xChannel < this.plotCache.length))
/*      */     {
/* 5602 */       int yIndex = yChannel + 1;
/*      */       
/* 5604 */       if ((yIndex >= 0) && (yIndex < this.plotCache[xChannel].length))
/*      */       {
/* 5606 */         this.canvas.setPlot(this.plotCache[xChannel][yIndex]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 5612 */     URL plotImageURL = getPlotURL(this.plotURL, xChannel, yChannel, this.plotCount);
/*      */     
/* 5614 */     if (plotImageURL == null)
/*      */     {
/* 5616 */       setPlot(null, xChannel, yChannel, this.plotCount);
/*      */       
/*      */ 
/* 5619 */       this.plotCount += 1;
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 5625 */       this.plotThread = new PlotThread("Plot Thread", this, plotImageURL, xChannel, yChannel, this.plotCount);
/*      */       
/*      */ 
/* 5628 */       this.plotCount += 1;
/*      */       
/*      */ 
/* 5631 */       this.plotThread.start();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   synchronized void setPlot(Image plot, int xChannel, int yChannel, int plotCount)
/*      */   {
/* 5645 */     if (plotCount < this.lastPlotCount)
/*      */     {
/* 5647 */       return;
/*      */     }
/*      */     
/*      */ 
/* 5651 */     this.lastPlotCount = plotCount;
/*      */     
/* 5653 */     if ((xChannel >= 0) && (xChannel < this.plotCache.length))
/*      */     {
/* 5655 */       int yIndex = yChannel + 1;
/*      */       
/* 5657 */       if ((yIndex >= 0) && (yIndex < this.plotCache[xChannel].length))
/*      */       {
/* 5659 */         this.plotCache[xChannel][yIndex] = plot;
/*      */       }
/*      */     }
/*      */     
/* 5663 */     if ((xChannel == getXChannel()) && (yChannel == getYChannel()))
/*      */     {
/* 5665 */       this.canvas.setPlot(plot);
/*      */     }
/*      */     
/*      */ 
/* 5669 */     setCursor(Cursor.getPredefinedCursor(0));
/*      */     
/*      */ 
/* 5672 */     setStatus("");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void toggle1D(boolean oneDimP)
/*      */   {
/* 5683 */     if (oneDimP)
/*      */     {
/* 5685 */       if ((this.ellipse.isEnabled()) || (this.polygon.isEnabled()) || (this.rectangle.isEnabled()) || (this.quadrant.isEnabled()) || (!this.range.isEnabled()) || (!this.split.isEnabled())) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 5692 */     else if ((this.ellipse.isEnabled()) && (this.polygon.isEnabled()) && (this.rectangle.isEnabled()) && (this.quadrant.isEnabled()) && (!this.range.isEnabled()) && (!this.split.isEnabled()))
/*      */     {
/* 5694 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5704 */     this.select.setSelected(true);
/*      */     
/*      */ 
/* 5707 */     this.canvas.setMode(GateCanvas.SELECT);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5717 */     if (this.ellipse != null)
/*      */     {
/* 5719 */       this.ellipse.setEnabled(!oneDimP);
/*      */     }
/*      */     
/* 5722 */     if (this.polygon != null)
/*      */     {
/* 5724 */       this.polygon.setEnabled(!oneDimP);
/*      */     }
/*      */     
/* 5727 */     if (this.rectangle != null)
/*      */     {
/* 5729 */       this.rectangle.setEnabled(!oneDimP);
/*      */     }
/*      */     
/* 5732 */     if (this.quadrant != null)
/*      */     {
/* 5734 */       this.quadrant.setEnabled(!oneDimP);
/*      */     }
/*      */     
/* 5737 */     if (this.range != null)
/*      */     {
/* 5739 */       this.range.setEnabled(oneDimP);
/*      */     }
/*      */     
/* 5742 */     if (this.split != null)
/*      */     {
/* 5744 */       this.split.setEnabled(oneDimP);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setStatus(String msg)
/*      */   {
/* 5754 */     if (this.status != null)
/*      */     {
/* 5756 */       this.status.setText(msg);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   JPopupMenu getPopupMenu()
/*      */   {
/* 5767 */     JPopupMenu popup = new JPopupMenu("Popup");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5775 */     boolean hasSelectedP = getSelectedCount() > 0;
/*      */     
/*      */ 
/* 5778 */     boolean hasClipboardP = false;
/*      */     
/* 5780 */     if ((this.clipboardGates != null) && (this.clipboardGates.size() > 0))
/*      */     {
/* 5782 */       hasClipboardP = true;
/*      */     }
/*      */     
/*      */ 
/* 5786 */     JMenuItem cutMenuItem = new JMenuItem("Cut");
/* 5787 */     cutMenuItem.setActionCommand("cut");
/* 5788 */     cutMenuItem.addActionListener(this);
/* 5789 */     cutMenuItem.setEnabled(hasSelectedP);
/* 5790 */     popup.add(cutMenuItem);
/*      */     
/*      */ 
/* 5793 */     JMenuItem copyMenuItem = new JMenuItem("Copy");
/* 5794 */     copyMenuItem.setActionCommand("copy");
/* 5795 */     copyMenuItem.addActionListener(this);
/* 5796 */     copyMenuItem.setEnabled(hasSelectedP);
/* 5797 */     popup.add(copyMenuItem);
/*      */     
/*      */ 
/* 5800 */     JMenuItem pasteMenuItem = new JMenuItem("Paste");
/* 5801 */     pasteMenuItem.setActionCommand("paste");
/* 5802 */     pasteMenuItem.addActionListener(this);
/* 5803 */     pasteMenuItem.setEnabled(hasClipboardP);
/* 5804 */     popup.add(pasteMenuItem);
/*      */     
/*      */ 
/* 5807 */     JMenuItem deleteMenuItem = new JMenuItem("Delete");
/* 5808 */     deleteMenuItem.setActionCommand("delete");
/* 5809 */     deleteMenuItem.addActionListener(this);
/* 5810 */     deleteMenuItem.setEnabled(hasSelectedP);
/* 5811 */     popup.add(deleteMenuItem);
/*      */     
/*      */ 
/* 5814 */     popup.addSeparator();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5821 */     if (getSelectedCount() > 0)
/*      */     {
/*      */ 
/*      */ 
/* 5825 */       JMenuItem overwriteMenuItem = new JMenuItem("Overwrite existing gates and apply to all files");
/* 5826 */       overwriteMenuItem.setActionCommand("Global");
/* 5827 */       overwriteMenuItem.addActionListener(this);
/* 5828 */       popup.add(overwriteMenuItem);
/*      */       
/*      */ 
/* 5831 */       popup.addSeparator();
/*      */     }
/*      */     
/*      */ 
/* 5835 */     JMenuItem assignMenuItem = new JMenuItem("Select active population");
/* 5836 */     assignMenuItem.setActionCommand("Select active population");
/* 5837 */     assignMenuItem.addActionListener(this);
/* 5838 */     popup.add(assignMenuItem);
/*      */     
/*      */ 
/* 5841 */     GateSet[] populations = getPopulations();
/*      */     
/* 5843 */     if (populations.length > 0)
/*      */     {
/* 5845 */       popup.addSeparator();
/*      */     }
/*      */     
/*      */ 
/* 5849 */     ButtonGroup group = new ButtonGroup();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5859 */     JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Ungated");
/*      */     
/*      */ 
/* 5862 */     rbMenuItem.setActionCommand("Ungated");
/*      */     
/*      */ 
/* 5865 */     rbMenuItem.setSelected(this.activeGateSet == null);
/*      */     
/*      */ 
/* 5868 */     rbMenuItem.addActionListener(this);
/*      */     
/*      */ 
/* 5871 */     group.add(rbMenuItem);
/*      */     
/*      */ 
/* 5874 */     popup.add(rbMenuItem);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5882 */     for (int i = 0; i < populations.length; i++)
/*      */     {
/* 5884 */       rbMenuItem = new JRadioButtonMenuItem(populations[i].getName());
/*      */       
/*      */ 
/* 5887 */       rbMenuItem.setActionCommand(populations[i].getName());
/*      */       
/*      */ 
/* 5890 */       rbMenuItem.setSelected(populations[i].equals(this.activeGateSet));
/*      */       
/*      */ 
/* 5893 */       rbMenuItem.addActionListener(this);
/*      */       
/*      */ 
/* 5896 */       group.add(rbMenuItem);
/*      */       
/*      */ 
/* 5899 */       popup.add(rbMenuItem);
/*      */     }
/*      */     
/*      */ 
/* 5903 */     return popup;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void copy()
/*      */   {
/* 5910 */     copyHelper(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void cut()
/*      */   {
/* 5918 */     copyHelper(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void copyHelper(boolean removeP)
/*      */   {
/* 5930 */     this.clipboardGates.clear();
/*      */     
/* 5932 */     if ((this.selectedGates == null) || (this.selectedGates.size() <= 0))
/*      */     {
/* 5934 */       return;
/*      */     }
/*      */     
/*      */ 
/* 5938 */     Gate[] gateArray = this.selectedGates.toArray();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 5943 */     for (int i = 0; i < gateArray.length; i++)
/*      */     {
/* 5945 */       Gate gate = gateArray[i];
/*      */       
/*      */ 
/* 5948 */       Gate copy = copyGate(gate.getID(), gate);
/*      */       
/* 5950 */       if (copy != null)
/*      */       {
/* 5952 */         this.clipboardGates.add(copy);
/*      */       }
/*      */       
/* 5955 */       if (removeP)
/*      */       {
/* 5957 */         removeGate(gate);
/*      */         
/*      */ 
/* 5960 */         fireCanvasChanged();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void paste()
/*      */   {
/* 5970 */     if ((this.clipboardGates == null) || (this.clipboardGates.size() <= 0) || (this.representation == null))
/*      */     {
/* 5972 */       return;
/*      */     }
/*      */     
/*      */ 
/* 5976 */     Gate[] gateArray = this.clipboardGates.toArray();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5984 */     int xChannel = getXChannel();
/*      */     
/*      */ 
/* 5987 */     int yChannel = getYChannel();
/*      */     
/*      */ 
/* 5990 */     int compensationID = getCompensationID();
/*      */     
/*      */ 
/* 5993 */     int xScaleFlag = this.representation.getScaleFlag(xChannel);
/*      */     
/*      */ 
/* 5996 */     int yScaleFlag = this.representation.getScaleFlag(yChannel);
/*      */     
/*      */ 
/* 5999 */     String xScaleArgument = this.representation.getScaleArgumentString(xChannel);
/*      */     
/*      */ 
/* 6002 */     String yScaleArgument = this.representation.getScaleArgumentString(yChannel);
/*      */     
/*      */ 
/* 6005 */     double xMin = this.representation.getMinimum(xChannel);
/*      */     
/*      */ 
/* 6008 */     double xMax = this.representation.getMaximum(xChannel);
/*      */     
/*      */ 
/* 6011 */     double yMin = this.representation.getMinimum(yChannel);
/*      */     
/*      */ 
/* 6014 */     double yMax = this.representation.getMaximum(yChannel);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6024 */     for (int i = 0; i < gateArray.length; i++)
/*      */     {
/* 6026 */       Gate gate = gateArray[i];
/*      */       
/*      */ 
/* 6029 */       Gate copy = copyGate(getNextGateID(), gate);
/*      */       
/* 6031 */       if (copy != null)
/*      */       {
/* 6033 */         copy.setXChannel(xChannel);
/*      */         
/*      */ 
/* 6036 */         copy.setYChannel(yChannel);
/*      */         
/*      */ 
/*      */ 
/* 6040 */         copy.setCompensationID(compensationID);
/* 6041 */         copy.setXScaleFlag(xScaleFlag);
/* 6042 */         copy.setYScaleFlag(yScaleFlag);
/* 6043 */         copy.setXScaleArgumentString(xScaleArgument);
/* 6044 */         copy.setYScaleArgumentString(yScaleArgument);
/* 6045 */         copy.setXMinimum(xMin);
/* 6046 */         copy.setXMaximum(xMax);
/* 6047 */         copy.setYMinimum(yMin);
/* 6048 */         copy.setYMaximum(yMax);
/*      */         
/* 6050 */         copy.setXBins(getAxisBins());
/* 6051 */         copy.setYBins(getAxisBins());
/* 6052 */         copy.setXSize(getAxisBins());
/* 6053 */         copy.setYSize(getAxisBins());
/*      */         
/*      */ 
/* 6056 */         copy.setName(gate.getName());
/*      */         
/*      */ 
/* 6059 */         copy.setLabelX(gate.getLabelX());
/* 6060 */         copy.setLabelY(gate.getLabelY());
/*      */         
/*      */ 
/* 6063 */         addGate(copy);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void save()
/*      */   {
/* 6074 */     this.dirtyGateP = false;
/* 6075 */     this.dirtyGateSetP = false;
/* 6076 */     this.dirtyRepresentationP = false;
/*      */     
/* 6078 */     if (this.applet != null) {
/* 6079 */       System.out.println("Saving...");
/*      */       
/*      */ 
/* 6082 */       this.bean.setGates(this.gates.toArray());
/*      */       
/*      */ 
/* 6085 */       GateSet[] gateSetArray = new GateSet[this.gateSets.size()];
/* 6086 */       this.gateSets.toArray(gateSetArray);
/* 6087 */       this.bean.setGateSets(gateSetArray);
/*      */       
/*      */ 
/* 6090 */       Representation[] representationArray = new Representation[this.representations.size()];
/* 6091 */       this.representations.toArray(representationArray);
/* 6092 */       this.bean.setRepresentations(representationArray);
/*      */       
/*      */ 
/* 6095 */       this.applet.setBean(this.bean);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void saveAndReturn()
/*      */   {
/* 6104 */     save();
/*      */     
/*      */ 
/* 6107 */     this.applet.returnToIllustration();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void reset()
/*      */   {
/* 6120 */     unselect();
/*      */     
/* 6122 */     Gate[] gateArray = getGates();
/* 6123 */     for (int i = 0; i < gateArray.length; i++) {
/* 6124 */       removeGate(gateArray[i]);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6136 */     GateSet[] gateSetArray = new GateSet[this.gateSets.size()];
/*      */     
/*      */ 
/* 6139 */     this.gateSets.toArray(gateSetArray);
/*      */     
/*      */ 
/* 6142 */     for (int i = 0; i < gateSetArray.length; i++) {
/* 6143 */       if (!(gateSetArray[i] instanceof CompensationGateSet))
/*      */       {
/* 6145 */         removeGateSet(gateSetArray[i]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6156 */     int i = 0; for (int n = this.representations.size(); i < n; i++)
/*      */     {
/* 6158 */       Representation rep = (Representation)this.representations.get(i);
/*      */       
/*      */ 
/* 6161 */       rep.clearGateSetIDs();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6168 */     setDirtyGate();
/* 6169 */     setDirtyGateSet();
/* 6170 */     setDirtyRepresentation();
/*      */     
/* 6172 */     fireGateSetChanged();
/* 6173 */     setActiveGateSet(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isDirty()
/*      */   {
/* 6187 */     if ((this.dirtyGateP) || (this.dirtyGateSetP) || (this.dirtyRepresentationP))
/*      */     {
/* 6189 */       return true;
/*      */     }
/*      */     
/*      */ 
/* 6193 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isGateDirty()
/*      */   {
/* 6203 */     return this.dirtyGateP;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isGateSetDirty()
/*      */   {
/* 6212 */     return this.dirtyGateSetP;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean isRepresentationDirty()
/*      */   {
/* 6221 */     return this.dirtyRepresentationP;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void setDirtyGate()
/*      */   {
/* 6228 */     this.dirtyGateP = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void setDirtyGateSet()
/*      */   {
/* 6235 */     this.dirtyGateSetP = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void setDirtyRepresentation()
/*      */   {
/* 6242 */     this.dirtyRepresentationP = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void actionPerformed(ActionEvent ae)
/*      */   {
/* 6259 */     if (ae == null)
/*      */     {
/* 6261 */       return;
/*      */     }
/*      */     
/*      */ 
/* 6265 */     if (this.bypassP)
/*      */     {
/* 6267 */       return;
/*      */     }
/*      */     
/*      */ 
/* 6271 */     String command = ae.getActionCommand();
/*      */     
/* 6273 */     if (command == null)
/*      */     {
/* 6275 */       return;
/*      */     }
/*      */     
/*      */ 
/* 6279 */     Object source = ae.getSource();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6286 */     if (command.equals("Display Gate"))
/*      */     {
/* 6288 */       int selectedIndex = this.gatesList.getSelectedIndex();
/* 6289 */       Gate[] gateArray = getMutableGates();
/*      */       
/* 6291 */       if ((selectedIndex >= 0) && (selectedIndex < gateArray.length)) {
/* 6292 */         Gate gate = gateArray[selectedIndex];
/* 6293 */         selectGate(gate);
/* 6294 */         displayGate(gate);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 6299 */     else if (command.equals("Delete Gate"))
/*      */     {
/* 6301 */       int selectedIndex = this.gatesList.getSelectedIndex();
/* 6302 */       Gate[] gateArray = getMutableGates();
/*      */       
/* 6304 */       if ((selectedIndex >= 0) && (selectedIndex < gateArray.length)) {
/* 6305 */         Gate gate = gateArray[selectedIndex];
/*      */         
/* 6307 */         if (gate.hasSisterGates()) {
/* 6308 */           removeSisters(gate);
/*      */         }
/*      */         
/* 6311 */         removeGate(gate);
/* 6312 */         fireCanvasChanged();
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 6317 */     else if (command.equals("Select Population"))
/*      */     {
/* 6319 */       this.selectPopulationFrame.setVisible(false);
/*      */       
/*      */ 
/* 6322 */       int selectedIndex = this.selectPopulationList.getSelectedIndex();
/*      */       
/*      */ 
/* 6325 */       GateSet selectedPopulation = this.selectPopulationListModel.getPopulation(selectedIndex);
/*      */       
/* 6327 */       if (selectedPopulation != null) {
/* 6328 */         setActiveGateSet(selectedPopulation);
/*      */       }
/*      */     }
/* 6331 */     else if (command.equals("Global"))
/*      */     {
/*      */ 
/*      */ 
/* 6335 */       Gate[] gateArray = this.selectedGates.toArray();
/*      */       
/*      */ 
/* 6338 */       for (int i = 0; i < gateArray.length; i++) {
/* 6339 */         makeGateGlobal(gateArray[i]);
/*      */       }
/*      */     }
/* 6342 */     else if (command.equals("cut"))
/*      */     {
/* 6344 */       cut();
/*      */     }
/* 6346 */     else if (command.equals("copy"))
/*      */     {
/* 6348 */       copy();
/*      */     }
/* 6350 */     else if (command.equals("paste"))
/*      */     {
/* 6352 */       paste();
/*      */     }
/* 6354 */     else if (command.equals("delete"))
/*      */     {
/* 6356 */       if (getSelectedCount() > 0)
/*      */       {
/* 6358 */         Gate[] gateArray = this.selectedGates.toArray();
/*      */         
/*      */ 
/* 6361 */         for (int i = 0; i < gateArray.length; i++) {
/* 6362 */           removeGate(gateArray[i]);
/*      */         }
/*      */         
/* 6365 */         fireCanvasChanged();
/*      */       }
/*      */     }
/* 6368 */     else if (source == this.ellipse)
/*      */     {
/* 6370 */       this.canvas.setMode(GateCanvas.ELLIPSE);
/*      */       
/*      */ 
/* 6373 */       unselect();
/*      */     }
/* 6375 */     else if (source == this.polygon)
/*      */     {
/* 6377 */       this.canvas.setMode(GateCanvas.POLYGON);
/*      */       
/*      */ 
/* 6380 */       unselect();
/*      */     }
/* 6382 */     else if (source == this.rectangle)
/*      */     {
/* 6384 */       this.canvas.setMode(GateCanvas.RECTANGLE);
/*      */       
/*      */ 
/* 6387 */       unselect();
/*      */     }
/* 6389 */     else if (source == this.quadrant)
/*      */     {
/* 6391 */       this.canvas.setMode(GateCanvas.QUADRANT);
/*      */       
/*      */ 
/* 6394 */       unselect();
/*      */     }
/* 6396 */     else if (source == this.range)
/*      */     {
/* 6398 */       this.canvas.setMode(GateCanvas.RANGE);
/*      */       
/*      */ 
/* 6401 */       unselect();
/*      */     }
/* 6403 */     else if (source == this.split)
/*      */     {
/* 6405 */       this.canvas.setMode(GateCanvas.SPLIT);
/*      */       
/*      */ 
/* 6408 */       unselect();
/*      */     }
/* 6410 */     else if (source == this.select)
/*      */     {
/* 6412 */       this.canvas.setMode(GateCanvas.SELECT);
/*      */       
/*      */ 
/* 6415 */       unselect();
/*      */     }
/* 6417 */     else if (source == this.save)
/*      */     {
/* 6419 */       saveAndReturn();
/*      */     }
/* 6421 */     else if (source == this.reset)
/*      */     {
/*      */ 
/*      */ 
/* 6425 */       int selectedIndex = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove all gates and all populations?", "Reset?", 0, 2);
/*      */       
/* 6427 */       if (selectedIndex == 0)
/*      */       {
/* 6429 */         reset();
/* 6430 */         save();
/*      */       }
/*      */     }
/* 6433 */     else if ((source == this.manage) || (command.equals("Select active population")))
/*      */     {
/* 6435 */       this.gateSetFrame.setVisible(true);
/*      */     }
/* 6437 */     else if (source == this.populationTreeButton)
/*      */     {
/* 6439 */       this.populationTreeFrame.setVisible(true);
/*      */     }
/* 6441 */     else if (source == this.create)
/*      */     {
/* 6443 */       addGateSet(new GateSet(getNextGateSetID()));
/*      */       
/*      */ 
/* 6446 */       this.gateSetFrame.setVisible(true);
/*      */     }
/* 6448 */     else if (source == this.copy)
/*      */     {
/*      */ 
/*      */ 
/* 6452 */       int selectedIndex = this.gateSetTable.getSelectedRow();
/*      */       
/*      */ 
/* 6455 */       GateSet[] populations = getPopulations();
/*      */       
/* 6457 */       if ((selectedIndex >= 0) && (selectedIndex < populations.length))
/*      */       {
/* 6459 */         if ((!(populations[selectedIndex] instanceof SpecialGateSet)) && (!(populations[selectedIndex] instanceof CompensationGateSet)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6467 */           this.gateSets.add(populations[selectedIndex].copy(getNextGateSetID()));
/*      */           
/*      */ 
/* 6470 */           fireGateSetChanged();
/*      */         }
/*      */       }
/*      */     }
/* 6474 */     else if (source == this.delete)
/*      */     {
/*      */ 
/*      */ 
/* 6478 */       int selectedIndex = this.gateSetTable.getSelectedRow();
/*      */       
/*      */ 
/* 6481 */       GateSet[] populations = getPopulations();
/*      */       
/* 6483 */       if ((selectedIndex >= 0) && (selectedIndex < populations.length))
/*      */       {
/* 6485 */         if ((populations[selectedIndex] instanceof QuadrantGateSet))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 6490 */           removeGate(((QuadrantGateSet)populations[selectedIndex]).getQuadrantGate());
/*      */         }
/* 6492 */         else if ((populations[selectedIndex] instanceof SplitGateSet))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 6497 */           removeGate(((SplitGateSet)populations[selectedIndex]).getSplitGate());
/*      */         }
/*      */         else
/*      */         {
/* 6501 */           removeGateSet(populations[selectedIndex]);
/*      */         }
/*      */       }
/*      */     }
/* 6505 */     else if (source == this.makeActive)
/*      */     {
/*      */ 
/*      */ 
/* 6509 */       int selectedIndex = this.gateSetTable.getSelectedRow();
/*      */       
/*      */ 
/* 6512 */       GateSet[] populations = getPopulations();
/*      */       
/* 6514 */       if ((selectedIndex >= 0) && (selectedIndex < populations.length))
/*      */       {
/* 6516 */         setActiveGateSet(populations[selectedIndex]);
/*      */       }
/*      */     }
/* 6519 */     else if (source == this.settings)
/*      */     {
/* 6521 */       this.plotSettingsFrame.setVisible(true);
/*      */     }
/* 6523 */     else if (source == this.xAxis)
/*      */     {
/* 6525 */       setXChannel(getXChannel());
/*      */       
/* 6527 */       if (this.gatePanel != null)
/*      */       {
/* 6529 */         this.gatePanel.fireChannelsChanged();
/*      */       }
/*      */       
/* 6532 */       firePlotChanged();
/*      */     }
/* 6534 */     else if (source == this.yAxis)
/*      */     {
/* 6536 */       setYChannel(getYChannel());
/*      */       
/* 6538 */       if (this.gatePanel != null)
/*      */       {
/* 6540 */         this.gatePanel.fireChannelsChanged();
/*      */       }
/*      */       
/*      */ 
/* 6544 */       firePlotChanged();
/*      */     }
/* 6546 */     else if (source == this.representationList)
/*      */     {
/*      */ 
/*      */ 
/* 6550 */       int selectedIndex = this.representationList.getSelectedIndex();
/*      */       
/* 6552 */       if ((selectedIndex >= 0) && (selectedIndex < this.representations.size()))
/*      */       {
/* 6554 */         this.representation = ((Representation)this.representations.get(selectedIndex));
/*      */         
/*      */ 
/* 6557 */         this.representationPanel.setRepresentation(this.file.getChannels(), this.representation);
/*      */         
/*      */ 
/* 6560 */         firePlotChanged();
/*      */       }
/*      */     }
/* 6563 */     else if (source == this.saveRepresentationAs)
/*      */     {
/* 6565 */       if (this.representation != null)
/*      */       {
/*      */ 
/*      */ 
/* 6569 */         int newID = getNextRepresentationID();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6580 */         String name = JOptionPane.showInputDialog(this, "Please enter a name for the representation:", "Representation " + newID);
/*      */         
/* 6582 */         if (name != null)
/*      */         {
/* 6584 */           Representation rep = Representation.getRepresentation(newID, name, this.representation.getChannelCount(), this.representation.getFilename(), this.representation.getCompensationID(), this.representation.getGateSetIDs(), this.representation.getXChannel(), this.representation.getYChannel(), this.representation.getPlotType(), this.representation.getStatisticType(), this.representation.getColorSet(), this.representation.useBlackBackground(), this.representation.drawAnnotation(), this.representation.drawScaleLabel(), this.representation.drawScaleTick(), this.representation.drawAxisLabel(), this.representation.useLongLabel(), this.representation.getAxisBins(), this.representation.getSmoothing(), this.representation.getAspectRatio(), this.representation.getContourPercent(), this.representation.getContourStartPercent(), this.representation.getPopulationType(), this.representation.getEventCount());
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6597 */           for (int i = 0; i < this.representation.getChannelCount(); i++) {
/* 6598 */             rep.setScaleFlag(i, this.representation.getScaleFlag(i));
/* 6599 */             rep.setScaleArgumentString(i, this.representation.getScaleArgumentString(i));
/* 6600 */             rep.setMinimum(i, this.representation.getMinimum(i));
/* 6601 */             rep.setMaximum(i, this.representation.getMaximum(i));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6610 */           Representation.Region[] regions = this.representation.getRegions();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 6615 */           for (int i = 0; i < regions.length; i++)
/*      */           {
/* 6617 */             Representation.Region region = rep.addRegion(regions[i].getID());
/*      */             
/*      */ 
/* 6620 */             region.setShown(regions[i].isShown());
/* 6621 */             region.setDrawn(regions[i].isDrawn());
/* 6622 */             region.setLabelShown(regions[i].isLabelShown());
/* 6623 */             region.setEventCountShown(regions[i].isEventCountShown());
/* 6624 */             region.setMeanShown(regions[i].isMeanShown());
/* 6625 */             region.setMedianShown(regions[i].isMedianShown());
/* 6626 */             region.setPercentShown(regions[i].isPercentShown());
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 6631 */           addRepresentation(rep);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 6636 */         this.plotSettingsFrame.setVisible(true);
/*      */       }
/*      */     }
/* 6639 */     else if (source == this.populationList)
/*      */     {
/*      */ 
/*      */ 
/* 6643 */       int selectedIndex = this.populationList.getSelectedIndex();
/*      */       
/* 6645 */       if (selectedIndex <= 0)
/*      */       {
/* 6647 */         setActiveGateSet(null);
/*      */       }
/* 6649 */       else if (selectedIndex > 0)
/*      */       {
/*      */ 
/*      */ 
/* 6653 */         GateSet[] populations = getPopulations();
/*      */         
/*      */ 
/* 6656 */         selectedIndex--;
/*      */         
/* 6658 */         if (selectedIndex < populations.length)
/*      */         {
/* 6660 */           setActiveGateSet(populations[selectedIndex]);
/*      */         }
/*      */       }
/*      */     }
/* 6664 */     else if (source == this.populationUp)
/*      */     {
/* 6666 */       if (this.activeGateSet != null)
/*      */       {
/* 6668 */         populationAbove();
/*      */       }
/*      */     }
/* 6671 */     else if (source == this.populationDown)
/*      */     {
/* 6673 */       populationBelow();
/*      */     }
/* 6675 */     else if (source == this.fileList)
/*      */     {
/*      */ 
/*      */ 
/* 6679 */       int selectedIndex = this.fileList.getSelectedIndex();
/*      */       
/* 6681 */       if ((this.files != null) && (selectedIndex >= 0) && (selectedIndex < this.files.length))
/*      */       {
/* 6683 */         setFlowFile(selectedIndex);
/*      */         
/*      */ 
/* 6686 */         firePlotChanged();
/*      */       }
/*      */     }
/* 6689 */     else if (source == this.firstFile)
/*      */     {
/* 6691 */       if ((this.files != null) && (this.files.length > 0))
/*      */       {
/* 6693 */         setFlowFile(0);
/*      */         
/*      */ 
/* 6696 */         firePlotChanged();
/*      */       }
/*      */     }
/* 6699 */     else if (source == this.prevFile)
/*      */     {
/*      */ 
/*      */ 
/* 6703 */       int selectedIndex = this.fileList.getSelectedIndex() - 1;
/*      */       
/* 6705 */       if ((this.files != null) && (selectedIndex >= 0) && (selectedIndex < this.files.length))
/*      */       {
/* 6707 */         setFlowFile(selectedIndex);
/*      */         
/*      */ 
/* 6710 */         firePlotChanged();
/*      */       }
/*      */     }
/* 6713 */     else if (source == this.nextFile)
/*      */     {
/*      */ 
/*      */ 
/* 6717 */       int selectedIndex = this.fileList.getSelectedIndex() + 1;
/*      */       
/* 6719 */       if ((this.files != null) && (selectedIndex >= 0) && (selectedIndex < this.files.length))
/*      */       {
/* 6721 */         setFlowFile(selectedIndex);
/*      */         
/*      */ 
/* 6724 */         firePlotChanged();
/*      */       }
/*      */     }
/* 6727 */     else if (source == this.lastFile)
/*      */     {
/* 6729 */       if ((this.files != null) && (this.files.length > 0))
/*      */       {
/* 6731 */         setFlowFile(this.files.length - 1);
/*      */         
/*      */ 
/* 6734 */         firePlotChanged();
/*      */       }
/*      */     }
/* 6737 */     else if (source == this.compensationList)
/*      */     {
/*      */ 
/*      */ 
/* 6741 */       int selectedIndex = this.compensationList.getSelectedIndex();
/*      */       
/*      */ 
/* 6744 */       int newCompensationID = -1;
/*      */       
/* 6746 */       if ((selectedIndex < 0) || (selectedIndex == 1))
/*      */       {
/* 6748 */         newCompensationID = -1;
/*      */       }
/* 6750 */       else if (selectedIndex == 0)
/*      */       {
/* 6752 */         newCompensationID = -2;
/*      */       }
/* 6754 */       else if (selectedIndex > 1)
/*      */       {
/* 6756 */         selectedIndex -= 2;
/*      */         
/* 6758 */         if ((selectedIndex >= 0) && (selectedIndex < this.compensations.length))
/*      */         {
/* 6760 */           newCompensationID = this.compensations[selectedIndex].getID();
/*      */         }
/*      */       }
/*      */       
/* 6764 */       if (setCompensationID(newCompensationID))
/*      */       {
/* 6766 */         firePlotChanged();
/*      */       }
/*      */     }
/* 6769 */     else if (source == this.gatingTools)
/*      */     {
/* 6771 */       this.autoGatingFrame.setVisible(true);
/*      */     }
/* 6773 */     else if (source == this.autoQuadDetails)
/*      */     {
/* 6775 */       this.autoGatingFrame.setVisible(false);
/*      */       
/* 6777 */       JOptionPane.showMessageDialog(this, "Auto Quad is not yet implemented.");
/*      */     }
/* 6779 */     else if (source == this.autoBarcodeDetails)
/*      */     {
/* 6781 */       this.autoGatingFrame.setVisible(false);
/*      */       
/*      */ 
/* 6784 */       this.autoBarcodeFrame.setVisible(true);
/*      */     }
/* 6786 */     else if (source == this.autoBarcodeOK)
/*      */     {
/*      */ 
/*      */ 
/* 6790 */       this.autoBarcodeFrame.setVisible(false);
/*      */       
/*      */ 
/* 6793 */       int yChannel = getYChannel();
/*      */       
/* 6795 */       if ((yChannel > -1) && (this.file != null) && (this.applet != null))
/*      */       {
/*      */ 
/*      */ 
/* 6799 */         save();
/*      */         
/*      */ 
/* 6802 */         int selectedIndex = 0;
/*      */         
/* 6804 */         if (this.files != null)
/*      */         {
/*      */ 
/*      */ 
/* 6808 */           for (int i = 0; i < this.files.length; i++) {
/* 6809 */             if (this.file.equals(this.files[i]))
/*      */             {
/* 6811 */               selectedIndex = i;
/*      */               
/* 6813 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6824 */         int gateSetID = -1;
/*      */         
/* 6826 */         if (this.activeGateSet != null)
/*      */         {
/* 6828 */           gateSetID = this.activeGateSet.getID();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 6833 */         String filename = this.file.getFilename();
/*      */         
/*      */ 
/* 6836 */         int xChannel = getXChannel();
/*      */         
/*      */ 
/* 6839 */         int numXGroups = ((SpinnerNumberModel)this.autoBarcodeXGroup.getModel()).getNumber().intValue();
/*      */         
/*      */ 
/* 6842 */         int numYGroups = ((SpinnerNumberModel)this.autoBarcodeYGroup.getModel()).getNumber().intValue();
/*      */         
/* 6844 */         System.out.println("Filename: " + filename);
/* 6845 */         System.out.println("x channel: " + xChannel);
/* 6846 */         System.out.println("y channel: " + yChannel);
/* 6847 */         System.out.println("Number of x groups: " + numXGroups);
/* 6848 */         System.out.println("Number of y groups: " + numYGroups);
/*      */         
/*      */ 
/* 6851 */         GateBean newBean = this.applet.autoBarcode(this.bean.getExperimentID(), filename, xChannel, yChannel, numXGroups, numYGroups);
/*      */         
/* 6853 */         if (newBean != null)
/*      */         {
/* 6855 */           setBean(newBean);
/*      */           
/*      */ 
/* 6858 */           setFlowFile(selectedIndex);
/*      */           
/*      */ 
/* 6861 */           setActiveGateSet(gateSetID);
/*      */         }
/*      */       }
/*      */     }
/* 6865 */     else if (source == this.autoBarcodeCancel)
/*      */     {
/* 6867 */       this.autoBarcodeFrame.setVisible(false);
/*      */     }
/* 6869 */     else if (command != null)
/*      */     {
/*      */ 
/* 6872 */       if (command.equals("Ungated"))
/*      */       {
/* 6874 */         setActiveGateSet(null);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 6880 */         GateSet[] populations = getPopulations();
/*      */         
/*      */ 
/* 6883 */         for (int i = 0; i < populations.length; i++) {
/* 6884 */           if (command.equals(populations[i].getName()))
/*      */           {
/* 6886 */             setActiveGateSet(populations[i]);
/*      */             
/* 6888 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static ImageIcon getImageIcon(String name)
/*      */   {
/* 6936 */     if (name == null)
/*      */     {
/* 6938 */       return new ImageIcon();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 6943 */     URL imgURL = GateClient.class.getResource(name);
/*      */     
/* 6945 */     if (imgURL == null)
/*      */     {
/* 6947 */       return new ImageIcon();
/*      */     }
/*      */     
/*      */ 
/* 6951 */     InputStream imgIS = null;
/*      */     
/*      */ 
/* 6954 */     ImageIcon imgIcon = null;
/*      */     
/*      */     try
/*      */     {
/* 6958 */       URLConnection imgConn = imgURL.openConnection();
/*      */       
/*      */ 
/* 6961 */       imgConn.setUseCaches(true);
/*      */       
/*      */ 
/* 6964 */       imgIS = imgConn.getInputStream();
/*      */       
/*      */ 
/* 6967 */       byte[] imgData = new byte[imgIS.available()];
/*      */       
/*      */ 
/* 6970 */       int imgLength = imgData.length - 1;
/*      */       
/*      */ 
/* 6973 */       for (int i = 0; i < imgLength; i++) {
/* 6974 */         imgData[i] = ((byte)imgIS.read());
/*      */       }
/*      */       
/*      */ 
/* 6978 */       imgIcon = new ImageIcon(imgData);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 6986 */         if (imgIS != null)
/*      */         {
/* 6988 */           imgIS.close();
/*      */         }
/*      */       }
/*      */       catch (IOException ioe2) {}
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 6996 */       if (imgIcon != null) {
/*      */         return imgIcon;
/*      */       }
/*      */     }
/*      */     catch (IOException ioe) {}finally
/*      */     {
/*      */       try
/*      */       {
/* 6986 */         if (imgIS != null)
/*      */         {
/* 6988 */           imgIS.close();
/*      */         }
/*      */       }
/*      */       catch (IOException ioe2) {}
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6998 */     imgIcon = new ImageIcon();
/*      */     
/*      */ 
/*      */ 
/* 7002 */     return imgIcon;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static String encode(String str)
/*      */   {
/* 7021 */     if (str == null)
/*      */     {
/* 7023 */       return null;
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 7028 */       return URLEncoder.encode(str, "UTF-8");
/*      */     }
/*      */     catch (UnsupportedEncodingException uee) {}
/*      */     
/* 7032 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static Representation getDefaultRepresentation(int id, String name, FlowFileDescriptor file)
/*      */   {
/* 7050 */     if (file == null)
/*      */     {
/* 7052 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7057 */     int channelCount = file.getChannelCount();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7063 */     int xChannel = -1;
/* 7064 */     int yChannel = -1;
/*      */     
/* 7066 */     if (channelCount > 2)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 7071 */       xChannel = 0;
/*      */     }
/* 7073 */     else if (channelCount > 0)
/*      */     {
/* 7075 */       xChannel = 0;
/*      */     }
/*      */     
/* 7078 */     if (channelCount > 6)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 7083 */       yChannel = 1;
/*      */     }
/* 7085 */     else if (channelCount > 1)
/*      */     {
/* 7087 */       yChannel = 1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7092 */     Representation rep = Representation.getRepresentation(id, name, channelCount, file.getFilename(), -1, new int[0], xChannel, yChannel, 12, -1, 1, false, true, true, true, true, false, 256, 1.0D, 1.0D, 10.0D, 10.0D, -1, 10000);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7102 */     for (int i = 0; i < channelCount; i++)
/*      */     {
/* 7104 */       rep.setScaleFlag(i, file.getScaleFlag(i));
/*      */       
/*      */ 
/* 7107 */       rep.setScaleArgumentString(i, file.getScaleArgumentString(i));
/*      */       
/*      */ 
/* 7110 */       rep.setMinimum(i, file.getMinimum(i));
/*      */       
/*      */ 
/* 7113 */       rep.setMaximum(i, file.getMaximum(i));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7118 */     return rep;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static GateBean getDummyBean()
/*      */   {
/* 7135 */     return GateBean.getDummyGateBean();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void buildGUI()
/*      */   {
/* 7145 */     URL url = null;
/*      */     
/*      */     try
/*      */     {
/* 7149 */       url = new URL("../Flow.png");
/*      */     }
/*      */     catch (MalformedURLException murle) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7157 */     GateBean bean = getDummyBean();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7165 */     JFrame.setDefaultLookAndFeelDecorated(false);
/*      */     
/*      */ 
/* 7168 */     JFrame f = new JFrame("Cytobank Gating Client");
/*      */     
/*      */ 
/* 7171 */     f.setTitle("Cytobank Gating Client");
/*      */     
/*      */ 
/* 7174 */     f.setDefaultCloseOperation(3);
/*      */     
/*      */ 
/* 7177 */     GateClient panel = new GateClient(null, bean, url, true);
/* 7178 */     f.getContentPane().add(panel, "Center");
/*      */     
/*      */ 
/* 7181 */     f.pack();
/* 7182 */     f.setVisible(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void main(String[] args)
/*      */   {
/* 7191 */     SwingUtilities.invokeLater(new Runnable()
/*      */     {
/*      */       public void run() {}
/*      */     });
/*      */   }
/*      */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\org\cytobank\applets\gating\GateClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */