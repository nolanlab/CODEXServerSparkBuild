/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
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
/*     */ public class Split
/*     */   extends Gate1D
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "Split";
/*     */   protected SplitGateSet gateSet;
/*     */   
/*     */   public Split()
/*     */   {
/*  66 */     this.gateSet = null;
/*     */     
/*     */ 
/*  69 */     this.labelX = (-this.x - this.width);
/*  70 */     this.labelY = (-this.y - this.height);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Split(int id, int bins, int x, int y, int width, int height, int xChannel)
/*     */   {
/* 105 */     this(id, x, y, width, height, xChannel, -1, bins, bins);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Split(int id, int x, int y, int width, int height, int xChannel, int compensationID, int xBins, int xSize)
/*     */   {
/* 137 */     super(id, x, y, width, height, xChannel, compensationID, xBins, xSize);
/*     */     
/*     */ 
/* 140 */     this.gateSet = null;
/*     */     
/*     */ 
/* 143 */     this.labelX = (-x - width);
/* 144 */     this.labelY = (-y - height);
/*     */   }
/*     */   
/*     */   public void setPosition(int x, int y, int width, int height, int xChannel, int compensationID, int xBins, int xSize) {
/* 148 */     this.x = x;
/* 149 */     this.y = y;
/* 150 */     this.width = width;
/* 151 */     this.height = height;
/* 152 */     this.xChannel = xChannel;
/* 153 */     this.compensationID = compensationID;
/* 154 */     this.xBins = xBins;
/* 155 */     this.xSize = xSize;
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
/* 168 */     if ((this.name != null) && (this.name.equals(name)))
/*     */     {
/*     */ 
/* 171 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 176 */     super.setName(name);
/*     */     
/* 178 */     if (this.gateSet != null)
/*     */     {
/*     */ 
/*     */ 
/* 182 */       this.gateSet.update();
/*     */     }
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
/*     */   public void setCompensationID(int id)
/*     */   {
/* 196 */     if (getCompensationID() != id)
/*     */     {
/*     */ 
/* 199 */       super.setCompensationID(id);
/*     */       
/* 201 */       if (this.gateSet != null)
/*     */       {
/* 203 */         this.gateSet.update();
/*     */       }
/*     */     }
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
/*     */   public void setXBins(int bins)
/*     */   {
/* 220 */     if (getXBins() != bins)
/*     */     {
/*     */ 
/* 223 */       super.setXBins(bins);
/*     */       
/* 225 */       if (this.gateSet != null)
/*     */       {
/* 227 */         this.gateSet.update();
/*     */       }
/*     */     }
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
/*     */   public void setXSize(int size)
/*     */   {
/* 244 */     if (getXSize() != size)
/*     */     {
/*     */ 
/* 247 */       super.setXSize(size);
/*     */       
/* 249 */       if (this.gateSet != null)
/*     */       {
/* 251 */         this.gateSet.update();
/*     */       }
/*     */     }
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
/*     */   public void setXScaleFlag(int scale)
/*     */   {
/* 270 */     if (getXScaleFlag() != scale)
/*     */     {
/*     */ 
/* 273 */       super.setXScaleFlag(scale);
/*     */       
/* 275 */       if (this.gateSet != null)
/*     */       {
/* 277 */         this.gateSet.update();
/*     */       }
/*     */     }
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
/*     */   public void setXScaleArgumentString(String xScaleArgument)
/*     */   {
/* 294 */     super.setXScaleArgumentString(xScaleArgument);
/*     */     
/* 296 */     if (this.gateSet != null)
/*     */     {
/* 298 */       this.gateSet.update();
/*     */     }
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
/*     */   public void setXMinimum(double min)
/*     */   {
/* 312 */     if (getXMinimum() != min)
/*     */     {
/*     */ 
/* 315 */       super.setXMinimum(min);
/*     */       
/* 317 */       if (this.gateSet != null)
/*     */       {
/* 319 */         this.gateSet.update();
/*     */       }
/*     */     }
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
/*     */   public void setXMaximum(double max)
/*     */   {
/* 334 */     if (getXMaximum() != max)
/*     */     {
/*     */ 
/* 337 */       super.setXMaximum(max);
/*     */       
/* 339 */       if (this.gateSet != null)
/*     */       {
/* 341 */         this.gateSet.update();
/*     */       }
/*     */     }
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
/*     */   public String getType()
/*     */   {
/* 359 */     return "Split";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getHandleCount()
/*     */   {
/* 371 */     return 1;
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
/*     */   public Point2D[] getHandles()
/*     */   {
/* 385 */     Point2D[] handles = new Point2D[getHandleCount()];
/*     */     
/*     */ 
/* 388 */     handles[0] = new Double(this.x, this.y);
/*     */     
/*     */ 
/* 391 */     return handles;
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
/*     */   public Shape getShape()
/*     */   {
/* 410 */     return null;
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
/*     */   public SplitGateSet getGateSet()
/*     */   {
/* 423 */     return this.gateSet;
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
/*     */   public void setGateSet(SplitGateSet gateSet)
/*     */   {
/* 437 */     this.gateSet = gateSet;
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
/*     */ 
/*     */   public boolean contains(int x)
/*     */   {
/* 464 */     return true;
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
/*     */   public boolean contains(double x)
/*     */   {
/* 487 */     return true;
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
/*     */   public boolean contains(int x, int y)
/*     */   {
/* 513 */     return true;
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
/*     */   public boolean contains(double x, double y)
/*     */   {
/* 534 */     return true;
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
/*     */   public boolean contains(Point2D point)
/*     */   {
/* 557 */     return inHandle(point) == 0;
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
/*     */   public void move(int dx, int dy)
/*     */   {
/* 576 */     super.move(dx, dy);
/*     */     
/* 578 */     if (this.gateSet != null)
/*     */     {
/* 580 */       this.gateSet.update();
/*     */     }
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
/*     */   public void move(int handleIndex, int dx, int dy)
/*     */   {
/* 605 */     if (isLocked())
/*     */     {
/* 607 */       return;
/*     */     }
/*     */     
/* 610 */     if (handleIndex == 0)
/*     */     {
/*     */ 
/* 613 */       move(dx, dy);
/*     */     }
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
/*     */   public void draw(Graphics2D g, boolean selectedP, Color normalColor, Color selectedColor, Color regionColor)
/*     */   {
/* 640 */     if (g == null)
/*     */     {
/* 642 */       return;
/*     */     }
/*     */     
/* 645 */     if (selectedP)
/*     */     {
/* 647 */       draw(g, selectedP, 0, this.width, normalColor, selectedColor);
/*     */     }
/*     */     else {
/* 650 */       drawTickMarks(g);
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawLabelHandle(Graphics2D g) {}
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Split.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */