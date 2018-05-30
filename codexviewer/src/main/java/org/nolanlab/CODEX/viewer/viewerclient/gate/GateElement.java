/*      */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jdom2.Element;
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
/*      */ public abstract class GateElement
/*      */ {
/*      */   protected static final String GATE_NAME = "Gate";
/*      */   protected static final String ID_NAME = "ID";
/*      */   protected static final String NAME_NAME = "Name";
/*      */   protected static final String TYPE_NAME = "Type";
/*      */   protected static final String XCHANNEL_NAME = "xChannel";
/*      */   protected static final String YCHANNEL_NAME = "yChannel";
/*      */   protected static final String COMPENSATIONID_NAME = "CompensationID";
/*      */   protected static final String XSCALE_NAME = "xScale";
/*      */   protected static final String YSCALE_NAME = "yScale";
/*      */   protected static final String XSCALEARGUMENT_NAME = "xScaleArgument";
/*      */   protected static final String YSCALEARGUMENT_NAME = "yScaleArgument";
/*      */   protected static final String XMIN_NAME = "xMin";
/*      */   protected static final String XMAX_NAME = "xMax";
/*      */   protected static final String YMIN_NAME = "yMin";
/*      */   protected static final String YMAX_NAME = "yMax";
/*      */   protected static final String XBINS_NAME = "xBins";
/*      */   protected static final String YBINS_NAME = "yBins";
/*      */   protected static final String XSIZE_NAME = "xSize";
/*      */   protected static final String YSIZE_NAME = "ySize";
/*      */   protected static final String GLOBAL_NAME = "Global";
/*      */   protected static final String STANDALONE_NAME = "StandAlone";
/*      */   protected static final String LOCAL_NAME = "Local";
/*      */   protected static final String FILENAME_NAME = "Filename";
/*      */   protected static final String LOCKED_NAME = "Locked";
/*      */   protected static final String COMPENSATION_NAME = "Compensation";
/*      */   protected static final String POSITIVE_NAME = "Positive";
/*      */   protected static final String NEGATIVE_NAME = "Negative";
/*      */   protected static final String LABELX_NAME = "LabelX";
/*      */   protected static final String LABELY_NAME = "LabelY";
/*      */   protected static final String SISTERS_NAME = "Sisters";
/*      */   protected static final String X_NAME = "x";
/*      */   protected static final String Y_NAME = "y";
/*      */   protected static final String WIDTH_NAME = "Width";
/*      */   protected static final String HEIGHT_NAME = "Height";
/*      */   protected Element element;
/*      */   
/*      */   protected GateElement(Gate gate)
/*      */   {
/*  124 */     this.element = new Element("Gate");
/*      */     
/*      */ 
/*  127 */     Element idElement = new Element("ID");
/*  128 */     idElement.addContent(Integer.toString(gate.getID()));
/*  129 */     this.element.addContent(idElement);
/*      */     
/*      */ 
/*  132 */     if (gate.getName() != null)
/*      */     {
/*  134 */       Element nameElement = new Element("Name");
/*  135 */       nameElement.addContent(gate.getName());
/*  136 */       this.element.addContent(nameElement);
/*      */     }
/*      */     
/*      */ 
/*  140 */     Element typeElement = new Element("Type");
/*  141 */     typeElement.addContent(gate.getType());
/*  142 */     this.element.addContent(typeElement);
/*      */     
/*      */ 
/*  145 */     Element xChannelElement = new Element("xChannel");
/*  146 */     xChannelElement.addContent(Integer.toString(gate.getXChannel()));
/*  147 */     this.element.addContent(xChannelElement);
/*      */     
/*      */ 
/*  150 */     Element yChannelElement = new Element("yChannel");
/*  151 */     yChannelElement.addContent(Integer.toString(gate.getYChannel()));
/*  152 */     this.element.addContent(yChannelElement);
/*      */     
/*      */ 
/*  155 */     Element compensationIDElement = new Element("CompensationID");
/*  156 */     compensationIDElement.addContent(Integer.toString(gate.getCompensationID()));
/*  157 */     this.element.addContent(compensationIDElement);
/*      */     
/*      */ 
/*  160 */     Element xScaleElement = new Element("xScale");
/*  161 */     xScaleElement.addContent(Integer.toString(gate.getXScaleFlag()));
/*  162 */     this.element.addContent(xScaleElement);
/*      */     
/*      */ 
/*  165 */     Element yScaleElement = new Element("yScale");
/*  166 */     yScaleElement.addContent(Integer.toString(gate.getYScaleFlag()));
/*  167 */     this.element.addContent(yScaleElement);
/*      */     
/*      */ 
/*      */ 
/*  171 */     if (gate.getXScaleArgumentString() != null)
/*      */     {
/*  173 */       Element xScaleArgumentElement = new Element("xScaleArgument");
/*  174 */       xScaleArgumentElement.addContent(gate.getXScaleArgumentString());
/*  175 */       this.element.addContent(xScaleArgumentElement);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  180 */     if (gate.getYScaleArgumentString() != null)
/*      */     {
/*  182 */       Element yScaleArgumentElement = new Element("yScaleArgument");
/*  183 */       yScaleArgumentElement.addContent(gate.getYScaleArgumentString());
/*  184 */       this.element.addContent(yScaleArgumentElement);
/*      */     }
/*      */     
/*      */ 
/*  188 */     if (!Double.isNaN(gate.getXMinimum()))
/*      */     {
/*  190 */       Element xMinElement = new Element("xMin");
/*  191 */       xMinElement.addContent(Double.toString(gate.getXMinimum()));
/*  192 */       this.element.addContent(xMinElement);
/*      */     }
/*      */     
/*      */ 
/*  196 */     if (!Double.isNaN(gate.getXMaximum()))
/*      */     {
/*  198 */       Element xMaxElement = new Element("xMax");
/*  199 */       xMaxElement.addContent(Double.toString(gate.getXMaximum()));
/*  200 */       this.element.addContent(xMaxElement);
/*      */     }
/*      */     
/*      */ 
/*  204 */     if (!Double.isNaN(gate.getYMinimum()))
/*      */     {
/*  206 */       Element yMinElement = new Element("yMin");
/*  207 */       yMinElement.addContent(Double.toString(gate.getYMinimum()));
/*  208 */       this.element.addContent(yMinElement);
/*      */     }
/*      */     
/*      */ 
/*  212 */     if (!Double.isNaN(gate.getYMaximum()))
/*      */     {
/*  214 */       Element yMaxElement = new Element("yMax");
/*  215 */       yMaxElement.addContent(Double.toString(gate.getYMaximum()));
/*  216 */       this.element.addContent(yMaxElement);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  221 */     Element xBinsElement = new Element("xBins");
/*  222 */     xBinsElement.addContent(Integer.toString(gate.getXBins()));
/*  223 */     this.element.addContent(xBinsElement);
/*      */     
/*      */ 
/*      */ 
/*  227 */     Element yBinsElement = new Element("yBins");
/*  228 */     yBinsElement.addContent(Integer.toString(gate.getYBins()));
/*  229 */     this.element.addContent(yBinsElement);
/*      */     
/*      */ 
/*  232 */     Element xSizeElement = new Element("xSize");
/*  233 */     xSizeElement.addContent(Integer.toString(gate.getXSize()));
/*  234 */     this.element.addContent(xSizeElement);
/*      */     
/*      */ 
/*  237 */     Element ySizeElement = new Element("ySize");
/*  238 */     ySizeElement.addContent(Integer.toString(gate.getYSize()));
/*  239 */     this.element.addContent(ySizeElement);
/*      */     
/*      */ 
/*  242 */     Element globalElement = new Element("Global");
/*  243 */     globalElement.addContent(Boolean.toString(gate.isGlobal()));
/*  244 */     this.element.addContent(globalElement);
/*      */     
/*      */ 
/*  247 */     Element standAloneElement = new Element("StandAlone");
/*  248 */     standAloneElement.addContent(Boolean.toString(gate.isStandAlone()));
/*  249 */     this.element.addContent(standAloneElement);
/*      */     
/*      */ 
/*  252 */     Element localElement = new Element("Local");
/*  253 */     localElement.addContent(Boolean.toString(gate.isLocal()));
/*  254 */     this.element.addContent(localElement);
/*      */     
/*  256 */     if (gate.getFilename() != null)
/*      */     {
/*      */ 
/*  259 */       Element filenameElement = new Element("Filename");
/*  260 */       filenameElement.addContent(gate.getFilename());
/*  261 */       this.element.addContent(filenameElement);
/*      */     }
/*      */     
/*      */ 
/*  265 */     Element lockedElement = new Element("Locked");
/*  266 */     lockedElement.addContent(Boolean.toString(gate.isLocked()));
/*  267 */     this.element.addContent(lockedElement);
/*      */     
/*      */ 
/*  270 */     Element compensationElement = new Element("Compensation");
/*  271 */     compensationElement.addContent(Boolean.toString(gate.isCompensation()));
/*  272 */     this.element.addContent(compensationElement);
/*      */     
/*      */ 
/*  275 */     Element positiveElement = new Element("Positive");
/*  276 */     positiveElement.addContent(Boolean.toString(gate.isPositive()));
/*  277 */     this.element.addContent(positiveElement);
/*      */     
/*      */ 
/*  280 */     Element negativeElement = new Element("Negative");
/*  281 */     negativeElement.addContent(Boolean.toString(gate.isNegative()));
/*  282 */     this.element.addContent(negativeElement);
/*      */     
/*      */ 
/*  285 */     Element labelXElement = new Element("LabelX");
/*  286 */     labelXElement.addContent(Integer.toString(gate.getLabelX()));
/*  287 */     this.element.addContent(labelXElement);
/*      */     
/*      */ 
/*  290 */     Element labelYElement = new Element("LabelY");
/*  291 */     labelYElement.addContent(Integer.toString(gate.getLabelY()));
/*  292 */     this.element.addContent(labelYElement);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  299 */     int[] sisters = gate.getSisterGates();
/*      */     
/*      */ 
/*  302 */     Element sistersElement = new Element("Sisters");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  307 */     for (int i = 0; i < sisters.length; i++) {
/*  308 */       Element sisterElement = new Element("Gate");
/*  309 */       sisterElement.addContent(Integer.toString(sisters[i]));
/*  310 */       sistersElement.addContent(sisterElement);
/*      */     }
/*      */     
/*      */ 
/*  314 */     this.element.addContent(sistersElement);
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
/*      */   protected GateElement(Gate gate, boolean dimP)
/*      */   {
/*  342 */     this(gate);
/*      */     
/*  344 */     if (dimP)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  349 */       Element xElement = new Element("x");
/*  350 */       xElement.addContent(Integer.toString(gate.getX()));
/*  351 */       this.element.addContent(xElement);
/*      */       
/*      */ 
/*  354 */       Element yElement = new Element("y");
/*  355 */       yElement.addContent(Integer.toString(gate.getY()));
/*  356 */       this.element.addContent(yElement);
/*      */       
/*      */ 
/*  359 */       Element widthElement = new Element("Width");
/*  360 */       widthElement.addContent(Integer.toString(gate.getWidth()));
/*  361 */       this.element.addContent(widthElement);
/*      */       
/*      */ 
/*  364 */       Element heightElement = new Element("Height");
/*  365 */       heightElement.addContent(Integer.toString(gate.getHeight()));
/*  366 */       this.element.addContent(heightElement);
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
/*      */   public Element getElement()
/*      */   {
/*  379 */     return this.element;
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
/*      */   public static GateElement getGateElement(Gate gate)
/*      */   {
/*  397 */     if (gate == null)
/*      */     {
/*  399 */       return null;
/*      */     }
/*      */     
/*  402 */     if ((gate instanceof Ellipse))
/*      */     {
/*  404 */       return new EllipseElement((Ellipse)gate); }
/*  405 */     if ((gate instanceof Polygon))
/*      */     {
/*  407 */       return new PolygonElement((Polygon)gate); }
/*  408 */     if ((gate instanceof Quad))
/*      */     {
/*  410 */       return new QuadElement((Quad)gate); }
/*  411 */     if ((gate instanceof Quadrant))
/*      */     {
/*  413 */       return new QuadrantElement((Quadrant)gate); }
/*  414 */     if ((gate instanceof Range))
/*      */     {
/*  416 */       return new RangeElement((Range)gate); }
/*  417 */     if ((gate instanceof Rectangle))
/*      */     {
/*      */ 
/*  420 */       return new RectangleElement((Rectangle)gate); }
/*  421 */     if ((gate instanceof Split))
/*      */     {
/*  423 */       return new SplitElement((Split)gate); }
/*  424 */     if ((gate instanceof SplitRange))
/*      */     {
/*      */ 
/*  427 */       return new SplitRangeElement((SplitRange)gate);
/*      */     }
/*      */     
/*      */ 
/*  431 */     return null;
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
/*      */   public static Gate parseElement(Element element)
/*      */   {
/*  463 */     if (element == null)
/*      */     {
/*  465 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  473 */     String type = getElementType(element);
/*      */     
/*  475 */     if (type == null)
/*      */     {
/*      */ 
/*  478 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  484 */     int id = 0;
/*      */     
/*      */ 
/*  487 */     Element idElement = element.getChild("ID");
/*      */     
/*  489 */     if (idElement == null)
/*      */     {
/*  491 */       return null;
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  496 */       id = Integer.parseInt(idElement.getText());
/*      */     }
/*      */     catch (NumberFormatException nfe)
/*      */     {
/*  500 */       id = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  506 */     String name = null;
/*      */     
/*      */ 
/*  509 */     Element nameElement = element.getChild("Name");
/*      */     
/*  511 */     if (nameElement != null)
/*      */     {
/*  513 */       name = nameElement.getText();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  519 */     int xChannel = 1;
/*      */     
/*      */ 
/*  522 */     Element xChannelElement = element.getChild("xChannel");
/*      */     
/*  524 */     if (xChannelElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  529 */         xChannel = Integer.parseInt(xChannelElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  533 */         xChannel = 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  540 */     int yChannel = 0;
/*      */     
/*      */ 
/*  543 */     Element yChannelElement = element.getChild("yChannel");
/*      */     
/*  545 */     if (yChannelElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  550 */         yChannel = Integer.parseInt(yChannelElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  554 */         yChannel = 0;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  562 */     int xBins = 256;
/*      */     
/*      */ 
/*  565 */     Element xBinsElement = element.getChild("xBins");
/*      */     
/*  567 */     if (xBinsElement != null)
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*      */ 
/*  573 */         xBins = Integer.parseInt(xBinsElement.getText());
/*      */ 
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  578 */         xBins = 256;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  586 */     int yBins = 256;
/*      */     
/*      */ 
/*  589 */     Element yBinsElement = element.getChild("yBins");
/*      */     
/*  591 */     if (yBinsElement != null)
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*      */ 
/*  597 */         yBins = Integer.parseInt(yBinsElement.getText());
/*      */ 
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  602 */         yBins = 256;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  610 */     int xSize = 256;
/*      */     
/*      */ 
/*  613 */     Element xSizeElement = element.getChild("xSize");
/*      */     
/*  615 */     if (xSizeElement != null)
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*      */ 
/*  621 */         xSize = Integer.parseInt(xSizeElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  625 */         xSize = 256;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  633 */     int ySize = 256;
/*      */     
/*      */ 
/*  636 */     Element ySizeElement = element.getChild("ySize");
/*      */     
/*  638 */     if (ySizeElement != null)
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*      */ 
/*  644 */         ySize = Integer.parseInt(ySizeElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  648 */         ySize = 256;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  655 */     int compensationID = -1;
/*      */     
/*      */ 
/*  658 */     Element compensationIDElement = element.getChild("CompensationID");
/*      */     
/*  660 */     if (compensationIDElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  665 */         compensationID = Integer.parseInt(compensationIDElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  669 */         compensationID = -1;
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
/*  680 */     int xScale = 1;
/*      */     
/*      */ 
/*  683 */     Element xScaleElement = element.getChild("xScale");
/*      */     
/*  685 */     if (xScaleElement != null)
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*      */ 
/*  691 */         xScale = Integer.parseInt(xScaleElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  695 */         xScale = 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  702 */     int yScale = 1;
/*      */     
/*      */ 
/*  705 */     Element yScaleElement = element.getChild("yScale");
/*      */     
/*  707 */     if (yScaleElement != null)
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*      */ 
/*  713 */         yScale = Integer.parseInt(yScaleElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  717 */         yScale = 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  725 */     String xScaleArgument = null;
/*      */     
/*      */ 
/*  728 */     Element xScaleArgumentElement = element.getChild("xScaleArgument");
/*      */     
/*  730 */     if (xScaleArgumentElement != null)
/*      */     {
/*      */ 
/*      */ 
/*  734 */       xScaleArgument = xScaleArgumentElement.getText();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  741 */     String yScaleArgument = null;
/*      */     
/*      */ 
/*  744 */     Element yScaleArgumentElement = element.getChild("yScaleArgument");
/*      */     
/*  746 */     if (yScaleArgumentElement != null)
/*      */     {
/*      */ 
/*      */ 
/*  750 */       yScaleArgument = yScaleArgumentElement.getText();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  756 */     double xMin = Double.POSITIVE_INFINITY;
/*      */     
/*      */ 
/*  759 */     Element xMinElement = element.getChild("xMin");
/*      */     
/*  761 */     if (xMinElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  766 */         xMin = Double.parseDouble(xMinElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  770 */         xMin = Double.POSITIVE_INFINITY;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  777 */     double xMax =Double.NEGATIVE_INFINITY;
/*      */     
/*      */ 
/*  780 */     Element xMaxElement = element.getChild("xMax");
/*      */     
/*  782 */     if (xMaxElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  787 */         xMax = Double.parseDouble(xMaxElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  791 */         xMax = Double.NEGATIVE_INFINITY;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  798 */     double yMin = Double.POSITIVE_INFINITY;
/*      */     
/*      */ 
/*  801 */     Element yMinElement = element.getChild("yMin");
/*      */     
/*  803 */     if (yMinElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  808 */         yMin = Double.parseDouble(yMinElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  812 */         yMin = Double.POSITIVE_INFINITY;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  819 */     double yMax = Double.NEGATIVE_INFINITY;
/*      */     
/*      */ 
/*  822 */     Element yMaxElement = element.getChild("yMax");
/*      */     
/*  824 */     if (yMaxElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  829 */         yMax = Double.parseDouble(yMaxElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  833 */         yMax  = Double.NEGATIVE_INFINITY;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  840 */     boolean globalP = false;
/*      */     
/*      */ 
/*  843 */     Element globalElement = element.getChild("Global");
/*      */     
/*  845 */     if (globalElement != null)
/*      */     {
/*      */ 
/*  848 */       globalP = Boolean.parseBoolean(globalElement.getText());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  854 */     boolean standAloneP = false;
/*      */     
/*      */ 
/*  857 */     Element standAloneElement = element.getChild("StandAlone");
/*      */     
/*  859 */     if (standAloneElement != null)
/*      */     {
/*      */ 
/*  862 */       standAloneP = Boolean.parseBoolean(standAloneElement.getText());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  868 */     boolean localP = false;
/*      */     
/*      */ 
/*  871 */     Element localElement = element.getChild("Local");
/*      */     
/*  873 */     if (localElement != null)
/*      */     {
/*      */ 
/*  876 */       localP = Boolean.parseBoolean(localElement.getText());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  882 */     String filename = null;
/*      */     
/*      */ 
/*  885 */     Element filenameElement = element.getChild("Filename");
/*      */     
/*  887 */     if (filenameElement != null)
/*      */     {
/*      */ 
/*  890 */       filename = filenameElement.getText();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  896 */     boolean lockedP = false;
/*      */     
/*      */ 
/*  899 */     Element lockedElement = element.getChild("Locked");
/*      */     
/*  901 */     if (lockedElement != null)
/*      */     {
/*      */ 
/*  904 */       lockedP = Boolean.parseBoolean(lockedElement.getText());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  914 */     boolean compensationP = false;
/*      */     
/*      */ 
/*  917 */     Element compensationElement = element.getChild("Compensation");
/*      */     
/*  919 */     if (compensationElement != null)
/*      */     {
/*      */ 
/*  922 */       compensationP = Boolean.parseBoolean(compensationElement.getText());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  928 */     boolean positiveP = false;
/*      */     
/*      */ 
/*  931 */     Element positiveElement = element.getChild("Positive");
/*      */     
/*  933 */     if (positiveElement != null)
/*      */     {
/*      */ 
/*  936 */       positiveP = Boolean.parseBoolean(positiveElement.getText());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  942 */     boolean negativeP = false;
/*      */     
/*      */ 
/*  945 */     Element negativeElement = element.getChild("Negative");
/*      */     
/*  947 */     if (negativeElement != null)
/*      */     {
/*      */ 
/*  950 */       negativeP = Boolean.parseBoolean(negativeElement.getText());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  960 */     int labelX = 0;
/*      */     
/*      */ 
/*  963 */     Element labelXElement = element.getChild("LabelX");
/*      */     
/*  965 */     if (labelXElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  970 */         labelX = Integer.parseInt(labelXElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  974 */         labelX = 0;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  981 */     int labelY = 0;
/*      */     
/*      */ 
/*  984 */     Element labelYElement = element.getChild("LabelY");
/*      */     
/*  986 */     if (labelYElement != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/*  991 */         labelY = Integer.parseInt(labelYElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  995 */         labelY = 0;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1004 */     ArrayList<Integer> sisters = new ArrayList();
/*      */     
/*      */ 
/* 1007 */     Element sistersElement = element.getChild("Sisters");
/*      */     
/*      */ 
/*      */ 
/* 1011 */     if (sistersElement != null)
/*      */     {
/*      */ 
/* 1014 */       List sistersList = sistersElement.getChildren();
/* 1015 */       Iterator sistersIter = sistersList.iterator();
/*      */       
/*      */ 
/* 1018 */       while (sistersIter.hasNext())
/*      */       {
/* 1020 */         Element sisterElement = (Element)sistersIter.next();
/*      */         
/*      */         try
/*      */         {
/* 1024 */           sisters.add(new Integer(sisterElement.getText()));
/*      */         }
/*      */         catch (NumberFormatException nfe) {}
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1034 */     int x = 0;
/*      */     
/*      */ 
/* 1037 */     Element xElement = element.getChild("x");
/*      */     
/* 1039 */     if (xElement != null)
/*      */     {
/*      */       try
/*      */       {
/* 1043 */         x = Integer.parseInt(xElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/* 1047 */         x = 0;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1054 */     int y = 0;
/*      */     
/*      */ 
/* 1057 */     Element yElement = element.getChild("y");
/*      */     
/* 1059 */     if (yElement != null)
/*      */     {
/*      */       try
/*      */       {
/* 1063 */         y = Integer.parseInt(yElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/* 1067 */         y = 0;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1074 */     int width = 0;
/*      */     
/*      */ 
/* 1077 */     Element widthElement = element.getChild("Width");
/*      */     
/* 1079 */     if (widthElement != null)
/*      */     {
/*      */       try
/*      */       {
/* 1083 */         width = Integer.parseInt(widthElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe) {
/* 1086 */         width = 0;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1093 */     int height = 0;
/*      */     
/*      */ 
/* 1096 */     Element heightElement = element.getChild("Height");
/*      */     
/* 1098 */     if (heightElement != null)
/*      */     {
/*      */       try
/*      */       {
/* 1102 */         height = Integer.parseInt(heightElement.getText());
/*      */       }
/*      */       catch (NumberFormatException nfe) {
/* 1105 */         height = 0;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1112 */     Gate gate = null;
/*      */     
/* 1114 */     if (type.equals("Ellipse"))
/*      */     {
/* 1116 */       gate = EllipseElement.parseElement(id, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize, element);
/* 1117 */     } else if (type.equals("Polygon"))
/*      */     {
/* 1119 */       gate = PolygonElement.parseElement(id, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize, element);
/* 1120 */     } else if (type.equals("Quad"))
/*      */     {
/* 1122 */       gate = QuadElement.parseElement(id, x, y, width, height, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/* 1123 */     } else if (type.equals("Quadrant"))
/*      */     {
/* 1125 */       gate = QuadrantElement.parseElement(id, x, y, width, height, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/* 1126 */     } else if (type.equals("Range"))
/*      */     {
/* 1128 */       gate = RangeElement.parseElement(id, x, y, width, height, xChannel, compensationID, xBins, xSize);
/* 1129 */     } else if (type.equals("Rectangle"))
/*      */     {
/* 1131 */       gate = RectangleElement.parseElement(id, x, y, width, height, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/* 1132 */     } else if (type.equals("Split"))
/*      */     {
/* 1134 */       gate = SplitElement.parseElement(id, x, y, width, height, xChannel, compensationID, xBins, xSize);
/* 1135 */     } else if (type.equals("SplitRange"))
/*      */     {
/* 1137 */       gate = SplitRangeElement.parseElement(id, x, y, width, height, xChannel, compensationID, xBins, xSize);
/*      */     }
/*      */     
/* 1140 */     if (gate != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 1145 */       gate.setName(name);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1152 */       gate.setXScaleFlag(xScale);
/*      */       
/*      */ 
/* 1155 */       gate.setYScaleFlag(yScale);
/*      */       
/* 1157 */       if (xScaleArgument != null)
/*      */       {
/*      */ 
/* 1160 */         gate.setXScaleArgumentString(xScaleArgument);
/*      */       }
/*      */       
/* 1163 */       if (yScaleArgument != null)
/*      */       {
/*      */ 
/* 1166 */         gate.setYScaleArgumentString(yScaleArgument);
/*      */       }
/*      */       
/* 1169 */       if (!Double.isNaN(xMin))
/*      */       {
/*      */ 
/* 1172 */         gate.setXMinimum(xMin);
/*      */       }
/*      */       
/* 1175 */       if (!Double.isNaN(xMax))
/*      */       {
/*      */ 
/* 1178 */         gate.setXMaximum(xMax);
/*      */       }
/*      */       
/* 1181 */       if (!Double.isNaN(yMin))
/*      */       {
/*      */ 
/* 1184 */         gate.setYMinimum(yMin);
/*      */       }
/*      */       
/* 1187 */       if (!Double.isNaN(yMax))
/*      */       {
/*      */ 
/* 1190 */         gate.setYMaximum(yMax);
/*      */       }
/*      */       
/*      */ 
/* 1194 */       gate.setGlobal(globalP);
/*      */       
/*      */ 
/* 1197 */       gate.setStandAlone(standAloneP);
/*      */       
/*      */ 
/* 1200 */       gate.setLocal(localP);
/*      */       
/*      */ 
/* 1203 */       gate.setFilename(filename);
/*      */       
/*      */ 
/* 1206 */       gate.setLocked(lockedP);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1213 */       gate.setCompensation(compensationP);
/*      */       
/* 1215 */       if (positiveP)
/*      */       {
/*      */ 
/* 1218 */         gate.setPositive(positiveP);
/*      */       }
/*      */       
/* 1221 */       if (negativeP)
/*      */       {
/*      */ 
/* 1224 */         gate.setNegative(negativeP);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1232 */       gate.setLabelX(labelX);
/*      */       
/*      */ 
/* 1235 */       gate.setLabelY(labelY);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1242 */       for (int i = 0; i < sisters.size(); i++) {
/* 1243 */         gate.addSisterGate(((Integer)sisters.get(i)).intValue());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1248 */     return gate;
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
/*      */   public static String getElementType(Element element)
/*      */   {
/* 1268 */     if (element == null)
/*      */     {
/* 1270 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1274 */     Element typeElement = element.getChild("Type");
/*      */     
/* 1276 */     if (typeElement == null)
/*      */     {
/* 1278 */       return null;
/*      */     }
/*      */     
/* 1281 */     return typeElement.getText();
/*      */   }
/*      */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\GateElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */