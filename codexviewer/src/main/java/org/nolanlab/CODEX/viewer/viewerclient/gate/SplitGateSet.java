/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.util.LinkedHashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SplitGateSet
/*     */   extends SpecialGateSet
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "SplitGateSet";
/*     */   protected static final String LOW_NAME = " low";
/*     */   protected static final String HI_NAME = " hi";
/*     */   protected static final int NUM_GATES = 2;
/*     */   protected Split split;
/*     */   
/*     */   public SplitGateSet(int id, Split split, int lowID, int hiID)
/*     */   {
/*  92 */     super(id);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */     this.split = split;
/*     */     
/* 101 */     if (this.split == null)
/*     */     {
/* 103 */       return;
/*     */     }
/*     */     
/*     */ 
/* 107 */     this.split.setGateSet(this);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */     int xChannel = this.split.getXChannel();
/*     */     
/*     */ 
/* 117 */     int compensationID = this.split.getCompensationID();
/*     */     
/*     */ 
/* 120 */     int xBins = this.split.getXBins();
/*     */     
/*     */ 
/* 123 */     int xSize = this.split.getXSize();
/*     */     
/*     */ 
/* 126 */     int x = this.split.getX();
/* 127 */     int y = this.split.getY();
/* 128 */     int width = this.split.getWidth();
/* 129 */     int height = this.split.getHeight();
/*     */     
/*     */ 
/* 132 */     SplitRange low = new SplitRange(lowID, 0, y, x, height, xChannel, compensationID, xBins, xSize);
/*     */     
/*     */ 
/* 135 */     SplitRange high = new SplitRange(hiID, x, y, width - x, height, xChannel, compensationID, xBins, xSize);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 145 */     this.gates.add(low);
/*     */     
/*     */ 
/* 148 */     this.gates.add(high);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 153 */     update();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SplitGateSet(int id, Split split, SplitRange low, SplitRange high)
/*     */   {
/* 179 */     super(id);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 186 */     this.split = split;
/*     */     
/* 188 */     if (this.split != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 193 */       this.split.setGateSet(this);
/*     */       
/*     */ 
/* 196 */       super.setName(this.split.getName());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 207 */     this.gates.add(low);
/*     */     
/*     */ 
/* 210 */     this.gates.add(high);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getType()
/*     */   {
/* 222 */     return "SplitGateSet";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Split getSplitGate()
/*     */   {
/* 235 */     return this.split;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 248 */     super.setName(name);
/*     */     
/* 250 */     if (this.split != null)
/*     */     {
/*     */ 
/* 253 */       this.split.setName(name);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void update()
/*     */   {
/* 265 */     if (this.split == null)
/*     */     {
/* 267 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 275 */     String name = this.split.getName();
/*     */     
/*     */ 
/* 278 */     super.setName(name);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 285 */     Gate[] gatesArray = toArray();
/*     */     
/* 287 */     if (gatesArray.length != 2)
/*     */     {
/*     */ 
/* 290 */       return;
/*     */     }
/*     */     
/*     */ 
/* 294 */     int compensationID = this.split.getCompensationID();
/*     */     
/*     */ 
/*     */ 
/* 298 */     int xScale = this.split.getXScaleFlag();
/*     */     
/*     */ 
/*     */ 
/* 302 */     String xScaleArgument = this.split.getXScaleArgumentString();
/*     */     
/*     */ 
/* 305 */     double xMin = this.split.getXMinimum();
/*     */     
/*     */ 
/* 308 */     double xMax = this.split.getXMaximum();
/*     */     
/*     */ 
/* 311 */     int xBins = this.split.getXBins();
/*     */     
/*     */ 
/* 314 */     int xSize = this.split.getXSize();
/*     */     
/*     */ 
/* 317 */     int x = this.split.getX();
/* 318 */     int y = this.split.getY();
/* 319 */     int width = this.split.getWidth();
/*     */     
/*     */ 
/* 322 */     if ((gatesArray[0] instanceof SplitRange))
/*     */     {
/*     */ 
/* 325 */       SplitRange low = (SplitRange)gatesArray[0];
/*     */       
/*     */ 
/* 328 */       low.setCompensationID(compensationID);
/* 329 */       low.setXBins(xBins);
/* 330 */       low.setXSize(xSize);
/* 331 */       low.setXScaleFlag(xScale);
/* 332 */       low.setXScaleArgumentString(xScaleArgument);
/* 333 */       low.setXMinimum(xMin);
/* 334 */       low.setXMaximum(xMax);
/* 335 */       low.setX(0);
/* 336 */       low.setY(y);
/* 337 */       low.setWidth(x);
/* 338 */       low.setName(name + " low");
/*     */     }
/*     */     
/*     */ 
/* 342 */     if ((gatesArray[1] instanceof SplitRange))
/*     */     {
/*     */ 
/* 345 */       SplitRange high = (SplitRange)gatesArray[1];
/*     */       
/*     */ 
/* 348 */       high.setCompensationID(compensationID);
/* 349 */       high.setXBins(xBins);
/* 350 */       high.setXSize(xSize);
/* 351 */       high.setXScaleFlag(xScale);
/* 352 */       high.setXScaleArgumentString(xScaleArgument);
/* 353 */       high.setXMinimum(xMin);
/* 354 */       high.setXMaximum(xMax);
/* 355 */       high.setX(x);
/* 356 */       high.setY(y);
/* 357 */       high.setWidth(width - x);
/* 358 */       high.setName(name + " hi");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\SplitGateSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */