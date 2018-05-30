/*      */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
/*      */ import java.awt.geom.Point2D;
/*      */
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Gate
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 4649L;
/*      */   public static final int HANDLE_SIZE = 7;
/*   95 */   public static final Color NORMAL_COLOR = Color.blue;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  100 */   public static final Color SELECTED_COLOR = Color.red;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  105 */   public static final Color REGION_COLOR = new Color(0.0F, 0.0F, 1.0F, 0.2F);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  110 */   public static final Color FLAG_COLOR = Color.red;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int id;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String name;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int x;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int y;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int width;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int height;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int xChannel;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int yChannel;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int compensationID;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int xBins;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int yBins;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int xSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int ySize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int xScale;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int yScale;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String xScaleArgument;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String yScaleArgument;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected double xMin;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected double xMax;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected double yMin;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected double yMax;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean globalP;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean standAloneP;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean localP;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String filename;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean lockedP;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean compensationP;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean positiveP;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean negativeP;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int labelX;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int labelY;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ArrayList sisterGates;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Gate()
/*      */   {
/*  295 */     this.xChannel = -1;
/*  296 */     this.yChannel = -1;
/*  297 */     this.compensationID = -1;
/*  298 */     this.xBins = 256;
/*  299 */     this.yBins = 256;
/*  300 */     this.xSize = 256;
/*  301 */     this.ySize = 256;
/*  302 */     this.xScale = 1;
/*  303 */     this.yScale = 1;
/*  304 */     this.xScaleArgument = "";
/*  305 */     this.yScaleArgument = "";
/*  306 */     this.xMin = java.lang.Double.POSITIVE_INFINITY;
/*  307 */     this.xMax = java.lang.Double.NEGATIVE_INFINITY;
/*  308 */     this.yMin = java.lang.Double.POSITIVE_INFINITY;
/*  309 */     this.yMax = java.lang.Double.NEGATIVE_INFINITY;
/*  310 */     this.globalP = true;
/*  311 */     this.standAloneP = false;
/*  312 */     this.localP = false;
/*  313 */     this.filename = null;
/*  314 */     this.lockedP = false;
/*  315 */     this.compensationP = false;
/*  316 */     this.positiveP = false;
/*  317 */     this.negativeP = false;
/*  318 */     this.labelX = 0;
/*  319 */     this.labelY = 0;
/*  320 */     this.x = 0;
/*  321 */     this.y = 0;
/*  322 */     this.height = 0;
/*  323 */     this.width = 0;
/*  324 */     this.sisterGates = new ArrayList();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Gate(int id, int x, int y, int width, int height, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize)
/*      */   {
/*  374 */     this.id = id;
/*      */     
/*      */ 
/*  377 */     this.name = ("Gate " + id);
/*      */     
/*      */ 
/*  380 */     this.x = x;
/*  381 */     this.y = y;
/*  382 */     this.width = width;
/*  383 */     this.height = height;
/*      */     
/*      */ 
/*  386 */     this.xChannel = xChannel;
/*  387 */     this.yChannel = yChannel;
/*      */     
/*      */ 
/*  390 */     this.compensationID = compensationID;
/*      */     
/*      */ 
/*  393 */     this.xBins = xBins;
/*  394 */     this.yBins = yBins;
/*  395 */     this.xSize = xSize;
/*  396 */     this.ySize = ySize;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  401 */     this.xScale = 1;
/*  402 */     this.yScale = 1;
/*  403 */     this.xScaleArgument = null;
/*  404 */     this.yScaleArgument = null;
/*  405 */     this.xMin = java.lang.Double.POSITIVE_INFINITY;
/*  406 */     this.xMax = java.lang.Double.NEGATIVE_INFINITY;
/*  407 */     this.yMin = java.lang.Double.POSITIVE_INFINITY;
/*  408 */     this.yMax = java.lang.Double.NEGATIVE_INFINITY;
/*      */     
/*      */ 
/*  411 */     this.globalP = true;
/*      */     
/*      */ 
/*  414 */     this.standAloneP = false;
/*      */     
/*      */ 
/*  417 */     this.localP = false;
/*      */     
/*      */ 
/*  420 */     this.filename = null;
/*      */     
/*      */ 
/*  423 */     this.lockedP = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  430 */     this.compensationP = false;
/*      */     
/*      */ 
/*  433 */     this.positiveP = false;
/*      */     
/*      */ 
/*  436 */     this.negativeP = false;
/*      */     
/*      */ 
/*  439 */     this.labelX = x;
/*  440 */     this.labelY = y;
/*      */     
/*      */ 
/*  443 */     this.sisterGates = new ArrayList();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getID()
/*      */   {
/*  455 */     return this.id;
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
/*      */   public void setID(int id)
/*      */   {
/*  468 */     this.id = id;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  480 */     return this.name;
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
/*      */   public void setName(String name)
/*      */   {
/*  493 */     this.name = name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getXChannel()
/*      */   {
/*  505 */     return this.xChannel;
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
/*      */   public void setXChannel(int channel)
/*      */   {
/*  518 */     this.xChannel = channel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getYChannel()
/*      */   {
/*  530 */     return this.yChannel;
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
/*      */   public void setYChannel(int channel)
/*      */   {
/*  543 */     this.yChannel = channel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getCompensationID()
/*      */   {
/*  555 */     return this.compensationID;
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
/*      */   public void setCompensationID(int id)
/*      */   {
/*  568 */     this.compensationID = id;
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
/*      */   public int getAxisBins()
/*      */   {
/*  589 */     return this.xBins;
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
/*      */   public int getXBins()
/*      */   {
/*  603 */     return this.xBins;
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
/*      */   public void setXBins(int bins)
/*      */   {
/*  618 */     this.xBins = bins;
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
/*      */   public int getYBins()
/*      */   {
/*  632 */     return this.yBins;
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
/*      */   public void setYBins(int bins)
/*      */   {
/*  647 */     this.yBins = bins;
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
/*      */   public int getXSize()
/*      */   {
/*  661 */     return this.xSize;
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
/*      */   public void setXSize(int size)
/*      */   {
/*  676 */     this.xSize = size;
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
/*      */   public int getYSize()
/*      */   {
/*  690 */     return this.ySize;
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
/*      */   public void setYSize(int size)
/*      */   {
/*  705 */     this.ySize = size;
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
/*      */   public int getXScaleFlag()
/*      */   {
/*  721 */     return this.xScale;
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
/*      */   public void setXScaleFlag(int scale)
/*      */   {
/*  734 */     this.xScale = scale;
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
/*      */   public String getXScaleArgumentString()
/*      */   {
/*  748 */     return this.xScaleArgument;
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
/*      */   public void setXScaleArgumentString(String xScaleArgument)
/*      */   {
/*  763 */     if ((xScaleArgument == null) || (xScaleArgument.length() <= 0))
/*      */     {
/*      */ 
/*  766 */       this.xScaleArgument = null;
/*      */     }
/*      */     else
/*      */     {
/*  770 */       this.xScaleArgument = xScaleArgument;
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
/*      */   public int getYScaleFlag()
/*      */   {
/*  783 */     return this.yScale;
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
/*      */   public void setYScaleFlag(int scale)
/*      */   {
/*  796 */     this.yScale = scale;
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
/*      */   public String getYScaleArgumentString()
/*      */   {
/*  810 */     return this.yScaleArgument;
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
/*      */   public void setYScaleArgumentString(String yScaleArgument)
/*      */   {
/*  825 */     if ((yScaleArgument == null) || (yScaleArgument.length() <= 0))
/*      */     {
/*      */ 
/*  828 */       this.yScaleArgument = null;
/*      */     }
/*      */     else
/*      */     {
/*  832 */       this.yScaleArgument = yScaleArgument;
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
/*      */   public double getXMinimum()
/*      */   {
/*  845 */     return this.xMin;
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
/*      */   public void setXMinimum(double min)
/*      */   {
/*  858 */     this.xMin = min;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getXMaximum()
/*      */   {
/*  870 */     return this.xMax;
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
/*      */   public void setXMaximum(double max)
/*      */   {
/*  883 */     this.xMax = max;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getYMinimum()
/*      */   {
/*  895 */     return this.yMin;
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
/*      */   public void setYMinimum(double min)
/*      */   {
/*  908 */     this.yMin = min;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getYMaximum()
/*      */   {
/*  920 */     return this.yMax;
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
/*      */   public void setYMaximum(double max)
/*      */   {
/*  933 */     this.yMax = max;
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
/*      */   public int getLabelX()
/*      */   {
/*  949 */     return this.labelX;
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
/*      */   public void setLabelX(int x)
/*      */   {
/*  962 */     this.labelX = x;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getLabelY()
/*      */   {
/*  974 */     return this.labelY;
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
/*      */   public void setLabelY(int y)
/*      */   {
/*  987 */     this.labelY = y;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getX()
/*      */   {
/*  999 */     return this.x;
/*      */   }
/*      */   
/*      */   public void setX(int x) {
/* 1003 */     this.x = x;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getY()
/*      */   {
/* 1015 */     return this.y;
/*      */   }
/*      */   
/*      */   public void setY(int y) {
/* 1019 */     this.y = y;
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
/*      */   public int getWidth()
/*      */   {
/* 1032 */     return this.width;
/*      */   }
/*      */   
/*      */   public void setWidth(int width) {
/* 1036 */     this.width = width;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getHeight()
/*      */   {
/* 1048 */     return this.height;
/*      */   }
/*      */   
/*      */   public void setHeight(int height) {
/* 1052 */     this.height = height;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isGlobal()
/*      */   {
/* 1064 */     return this.globalP;
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
/*      */   public void setGlobal(boolean globalP)
/*      */   {
/* 1081 */     this.globalP = globalP;
/*      */     
/* 1083 */     if (this.globalP)
/*      */     {
/*      */ 
/* 1086 */       this.standAloneP = false;
/* 1087 */       this.localP = false;
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
/*      */   public boolean isStandAlone()
/*      */   {
/* 1100 */     return this.standAloneP;
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
/*      */   public void setStandAlone(boolean standAloneP)
/*      */   {
/* 1117 */     this.standAloneP = standAloneP;
/*      */     
/* 1119 */     if (this.standAloneP)
/*      */     {
/*      */ 
/* 1122 */       this.globalP = false;
/* 1123 */       this.localP = false;
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
/*      */   public boolean isLocal()
/*      */   {
/* 1136 */     return this.localP;
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
/*      */   public void setLocal(boolean localP)
/*      */   {
/* 1153 */     this.localP = localP;
/*      */     
/* 1155 */     if (this.localP)
/*      */     {
/*      */ 
/* 1158 */       this.globalP = false;
/* 1159 */       this.standAloneP = false;
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
/*      */   public String getFilename()
/*      */   {
/* 1173 */     return this.filename;
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
/*      */   public void setFilename(String filename)
/*      */   {
/* 1188 */     this.filename = filename;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLocked()
/*      */   {
/* 1200 */     return this.lockedP;
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
/*      */   public void setLocked(boolean lockedP)
/*      */   {
/* 1213 */     this.lockedP = lockedP;
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
/*      */   public boolean isCompensation()
/*      */   {
/* 1229 */     return this.compensationP;
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
/*      */   public void setCompensation(boolean compensationP)
/*      */   {
/* 1243 */     this.compensationP = compensationP;
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
/*      */   public boolean isPositive()
/*      */   {
/* 1256 */     return this.positiveP;
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
/*      */   public void setPositive(boolean positiveP)
/*      */   {
/* 1270 */     this.positiveP = positiveP;
/*      */     
/* 1272 */     if (this.positiveP)
/*      */     {
/*      */ 
/* 1275 */       this.negativeP = false;
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
/*      */   public boolean isNegative()
/*      */   {
/* 1289 */     return this.negativeP;
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
/*      */   public void setNegative(boolean negativeP)
/*      */   {
/* 1303 */     this.negativeP = negativeP;
/*      */     
/* 1305 */     if (this.negativeP)
/*      */     {
/*      */ 
/* 1308 */       this.positiveP = false;
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
/*      */   public void addSisterGate(int id)
/*      */   {
/* 1326 */     if ((id > 0) && (!hasSisterGate(id)))
/*      */     {
/*      */ 
/* 1329 */       this.sisterGates.add(new Integer(id));
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
/*      */   public void addSisterGate(Gate gate)
/*      */   {
/* 1345 */     if (gate != null)
/*      */     {
/*      */ 
/* 1348 */       addSisterGate(gate.getID());
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
/*      */   public boolean hasSisterGates()
/*      */   {
/* 1361 */     return this.sisterGates.size() > 0;
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
/*      */   public boolean hasSisterGate(int id)
/*      */   {
/* 1376 */     if ((id > 0) && (hasSisterGates()))
/*      */     {
/*      */ 
/* 1379 */       return this.sisterGates.contains(new Integer(id));
/*      */     }
/*      */     
/* 1382 */     return false;
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
/*      */   public boolean hasSisterGate(Gate gate)
/*      */   {
/* 1399 */     if ((gate != null) && (hasSisterGates()))
/*      */     {
/*      */ 
/* 1402 */       return hasSisterGate(gate.getID());
/*      */     }
/*      */     
/* 1405 */     return false;
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
/*      */   public int[] getSisterGates()
/*      */   {
/* 1418 */     if (this.sisterGates.size() > 1)
/*      */     {
/*      */ 
/* 1421 */       Collections.sort(this.sisterGates);
/*      */     }
/*      */     
/*      */ 
/* 1425 */     int[] idArray = new int[this.sisterGates.size()];
/*      */     
/*      */ 
/* 1428 */     for (int i = 0; i < this.sisterGates.size(); i++) {
/* 1429 */       idArray[i] = ((Integer)this.sisterGates.get(i)).intValue();
/*      */     }
/*      */     
/*      */ 
/* 1433 */     return idArray;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clearSisterGates()
/*      */   {
/* 1443 */     this.sisterGates.clear();
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
/*      */   public void removeSisterGate(int id)
/*      */   {
/* 1456 */     if ((id > 0) && (hasSisterGates()))
/*      */     {
/*      */ 
/* 1459 */       this.sisterGates.remove(new Integer(id));
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
/*      */   public void removeSisterGate(Gate gate)
/*      */   {
/* 1475 */     if ((gate != null) && (hasSisterGates()))
/*      */     {
/*      */ 
/* 1478 */       removeSisterGate(gate.getID());
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
/*      */   public boolean equals(Object obj)
/*      */   {
/* 1501 */     if (obj == null)
/*      */     {
/*      */ 
/* 1504 */       return false;
/*      */     }
/*      */     
/* 1507 */     if ((obj instanceof Gate))
/*      */     {
/*      */ 
/* 1510 */       Gate gate = (Gate)obj;
/*      */       
/*      */ 
/* 1513 */       return (getID() == gate.getID()) && (getClass() == gate.getClass()) && (getType().equals(gate.getType()));
/*      */     }
/*      */     
/* 1516 */     return false;
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
/*      */   public int hashCode()
/*      */   {
/* 1529 */     return getID();
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
/*      */   public boolean isEquivalent(Gate gate)
/*      */   {
/* 1550 */     if (gate == null)
/*      */     {
/* 1552 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1556 */     return (getClass() == gate.getClass()) && (getType().equals(gate.getType())) && (hasSisterGate(gate));
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
/*      */   public abstract String getType();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract int getHandleCount();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Point2D[] getHandles();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Shape getShape();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void prepareForGating() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(int x)
/*      */   {
/* 1646 */     return false;
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
/*      */   public boolean contains(double x)
/*      */   {
/* 1665 */     return false;
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
/*      */   public boolean contains(int x, int y)
/*      */   {
/* 1687 */     return contains(x, y);
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
/*      */   public boolean contains(double x, double y)
/*      */   {
/* 1705 */     Shape shape = getShape();
/*      */     
/* 1707 */     if (shape != null)
/*      */     {
/*      */ 
/* 1710 */       return shape.contains(x, y);
/*      */     }
/*      */     
/* 1713 */     return false;
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
/*      */   public boolean contains(Point2D point)
/*      */   {
/* 1731 */     if (point == null)
/*      */     {
/* 1733 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1737 */     Shape shape = getShape();
/*      */     
/* 1739 */     if (shape == null)
/*      */     {
/* 1741 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1745 */     return shape.contains(point);
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
/*      */   public int inHandle(Point2D point)
/*      */   {
/* 1770 */     if (point == null)
/*      */     {
/* 1772 */       return -1;
/*      */     }
/*      */     
/* 1775 */     if (inLabel(point))
/*      */     {
/*      */ 
/* 1778 */       return -2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1785 */     Point2D[] handles = getHandles();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1790 */     for (int i = 0; i < handles.length; i++)
/*      */     {
/* 1792 */       Rectangle2D handle = getHandle(handles[i]);
/*      */       
/* 1794 */       if (handle.contains(point))
/*      */       {
/*      */ 
/* 1797 */         return i;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1803 */     return -1;
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
/*      */   public boolean inLabel(Point2D point)
/*      */   {
/* 1821 */     if (point == null)
/*      */     {
/* 1823 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1827 */     Rectangle2D label = getHandle(new Point2D.Double(this.labelX, this.labelY));
/*      */     
/*      */ 
/* 1830 */     return label.contains(point);
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
/*      */   public void move(int dx, int dy)
/*      */   {
/* 1845 */     if (isLocked())
/*      */     {
/* 1847 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1851 */     this.x += dx;
/* 1852 */     this.y += dy;
/*      */     
/*      */ 
/* 1855 */     moveLabel(dx, dy);
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
/*      */   public abstract void move(int paramInt1, int paramInt2, int paramInt3);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void moveLabel(int dx, int dy)
/*      */   {
/* 1887 */     this.labelX += dx;
/* 1888 */     this.labelY += dy;
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
/*      */   public void draw(Graphics2D g)
/*      */   {
/* 1904 */     draw(g, false);
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
/*      */   public void draw(Graphics2D g, boolean selected)
/*      */   {
/* 1922 */     draw(g, selected, NORMAL_COLOR, SELECTED_COLOR, REGION_COLOR);
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
/*      */   public abstract void draw(Graphics2D paramGraphics2D, boolean paramBoolean, Color paramColor1, Color paramColor2, Color paramColor3);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void drawLabelHandle(Graphics2D g)
/*      */   {
/* 1961 */     if (g == null)
/*      */     {
/* 1963 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1967 */     Rectangle2D handle = getHandle(new Point2D.Double(this.labelX, this.labelY));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1973 */     g.setColor(Color.white);
/* 1974 */     g.fill(handle);
/*      */     
/* 1976 */     g.setColor(Color.black);
/* 1977 */     g.draw(handle);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1982 */     drawFlag(g, this.labelX, this.labelY);
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
/*      */   protected void drawHandles(Graphics2D g)
/*      */   {
/* 2002 */     if (g == null)
/*      */     {
/* 2004 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2008 */     Point2D[] handles = getHandles();
/*      */     
/*      */ 
/* 2011 */     for (int i = 0; i < handles.length; i++) {
/* 2012 */       drawHandle(g, handles[i]);
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
/*      */   public static void drawHandle(Graphics2D g, Point2D point)
/*      */   {
/* 2036 */     if ((g == null) || (point == null))
/*      */     {
/* 2038 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2042 */     Rectangle2D handle = getHandle(point);
/*      */     
/* 2044 */     if (handle != null)
/*      */     {
/*      */ 
/* 2047 */       g.setColor(Color.black);
/* 2048 */       g.fill(handle);
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
/*      */   public static void drawTickMark(Graphics2D g, Point2D point)
/*      */   {
/* 2073 */     if ((g == null) || (point == null))
/*      */     {
/* 2075 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2079 */     g.setColor(Color.black);
/* 2080 */     g.draw(new Line2D.Double(point.getX(), point.getY() - 5.0D, point.getX(), point.getY() + 5.0D));
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
/*      */   public static Rectangle2D getHandle(Point2D point)
/*      */   {
/* 2104 */     if (point == null)
/*      */     {
/* 2106 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2110 */     return new Rectangle2D.Double(point.getX() - Math.floor(3.5D), point.getY() - Math.floor(3.5D), 7.0D, 7.0D);
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
/*      */   private static void drawFlag(Graphics2D g, int x, int y)
/*      */   {
/* 2132 */     if (g == null)
/*      */     {
/* 2134 */       return;
/*      */     }
/*      */     
/*      */ 
/* 2138 */     Rectangle2D flag = new Rectangle2D.Double(x, y + 3 + 1, Math.ceil(3.5D) + 1.0D, Math.floor(3.5D));
/*      */     
/*      */ 
/* 2141 */     g.setColor(Color.black);
/* 2142 */     g.draw(new Line2D.Double(x, y, x, y + 7));
/*      */     
/*      */ 
/* 2145 */     g.setColor(FLAG_COLOR);
/* 2146 */     g.fill(flag);
/*      */     
/*      */ 
/* 2149 */     g.setColor(Color.black);
/* 2150 */     g.draw(flag);
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
/*      */   public static Gate[] filter(Gate[] gates, int xChannel, int yChannel)
/*      */   {
/* 2174 */     if ((gates == null) || (gates.length <= 0))
/*      */     {
/*      */ 
/* 2177 */       return new Gate[0];
/*      */     }
/*      */     
/*      */ 
/* 2181 */     ArrayList gateList = new ArrayList(gates.length);
/*      */     
/*      */ 
/* 2184 */     for (int i = 0; i < gates.length; i++) {
/* 2185 */       if (((gates[i].getXChannel() == xChannel) && (gates[i].getYChannel() == yChannel)) || ((gates[i].getXChannel() == yChannel) && (gates[i].getYChannel() == xChannel)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 2190 */         gateList.add(gates[i]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2195 */     Gate[] gateArray = new Gate[gateList.size()];
/*      */     
/*      */ 
/* 2198 */     gateList.toArray(gateArray);
/*      */     
/*      */ 
/* 2201 */     return gateArray;
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
/*      */   public static Gate[] filter(Gate[] gates, String filename)
/*      */   {
/* 2220 */     if ((gates == null) || (gates.length <= 0))
/*      */     {
/*      */ 
/* 2223 */       return new Gate[0];
/*      */     }
/*      */     
/*      */ 
/* 2227 */     ArrayList gateList = new ArrayList(gates.length);
/*      */     
/*      */ 
/* 2230 */     for (int i = 0; i < gates.length; i++) {
/* 2231 */       if (gates[i].isGlobal())
/*      */       {
/* 2233 */         gateList.add(gates[i]);
/* 2234 */       } else if ((filename != null) && (gates[i].getFilename() != null) && (filename.equals(gates[i].getFilename())))
/*      */       {
/*      */ 
/*      */ 
/* 2238 */         gateList.add(gates[i]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2243 */     Gate[] gateArray = new Gate[gateList.size()];
/*      */     
/*      */ 
/* 2246 */     gateList.toArray(gateArray);
/*      */     
/*      */ 
/* 2249 */     return gateArray;
/*      */   }
/*      */
/*      */   
/*      */   public String toString() {
/* 2311 */     StringBuffer sb = new StringBuffer();
/* 2312 */     sb.append("<Gate id=");
/* 2313 */     sb.append(this.id);
/* 2314 */     sb.append(" name=\"");
/* 2315 */     sb.append(this.name);
/* 2316 */     sb.append("\">");
/* 2317 */     return sb.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Gate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */