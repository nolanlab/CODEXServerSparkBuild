/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Quadrant
/*     */   extends Gate2D
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "Quadrant";
/*     */   protected QuadrantGateSet gateSet;
/*     */   
/*     */   public Quadrant()
/*     */   {
/*  63 */     this.gateSet = null;
/*     */     
/*     */ 
/*  66 */     this.labelX = (-this.x - this.width);
/*  67 */     this.labelY = (-this.y - this.height);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public Quadrant(int id, int bins, int x, int y, int width, int height, int xChannel, int yChannel)
/*     */   {
/* 105 */     this(id, x, y, width, height, xChannel, yChannel, -1, bins, bins, bins, bins);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Quadrant(int id, int x, int y, int width, int height, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize)
/*     */   {
/* 146 */     super(id, x, y, width, height, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/*     */     
/*     */ 
/* 149 */     this.gateSet = null;
/*     */     
/*     */ 
/* 152 */     this.labelX = (-x - width);
/* 153 */     this.labelY = (-y - height);
/*     */   }
/*     */   
/*     */   public void setPosition(int x, int y, int width, int height, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize) {
/* 157 */     this.x = x;
/* 158 */     this.y = y;
/* 159 */     this.width = width;
/* 160 */     this.height = height;
/* 161 */     this.xChannel = xChannel;
/* 162 */     this.yChannel = yChannel;
/* 163 */     this.compensationID = compensationID;
/* 164 */     this.xBins = xBins;
/* 165 */     this.yBins = yBins;
/* 166 */     this.xSize = xSize;
/* 167 */     this.ySize = ySize;
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
/* 180 */     if ((this.name != null) && (this.name.equals(name)))
/*     */     {
/*     */ 
/* 183 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 188 */     super.setName(name);
/*     */     
/* 190 */     if (this.gateSet != null)
/*     */     {
/*     */ 
/*     */ 
/* 194 */       this.gateSet.update();
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
/* 208 */     if (getCompensationID() != id)
/*     */     {
/*     */ 
/* 211 */       super.setCompensationID(id);
/*     */       
/* 213 */       if (this.gateSet != null)
/*     */       {
/* 215 */         this.gateSet.update();
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
/* 232 */     if (getXBins() != bins)
/*     */     {
/*     */ 
/* 235 */       super.setXBins(bins);
/*     */       
/* 237 */       if (this.gateSet != null)
/*     */       {
/* 239 */         this.gateSet.update();
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
/*     */   public void setYBins(int bins)
/*     */   {
/* 256 */     if (getYBins() != bins)
/*     */     {
/*     */ 
/* 259 */       super.setYBins(bins);
/*     */       
/* 261 */       if (this.gateSet != null)
/*     */       {
/* 263 */         this.gateSet.update();
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
/* 280 */     if (getXSize() != size)
/*     */     {
/*     */ 
/* 283 */       super.setXSize(size);
/*     */       
/* 285 */       if (this.gateSet != null)
/*     */       {
/* 287 */         this.gateSet.update();
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
/*     */   public void setYSize(int size)
/*     */   {
/* 304 */     if (getYSize() != size)
/*     */     {
/*     */ 
/* 307 */       super.setYSize(size);
/*     */       
/* 309 */       if (this.gateSet != null)
/*     */       {
/* 311 */         this.gateSet.update();
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
/* 330 */     if (getXScaleFlag() != scale)
/*     */     {
/*     */ 
/* 333 */       super.setXScaleFlag(scale);
/*     */       
/* 335 */       if (this.gateSet != null)
/*     */       {
/* 337 */         this.gateSet.update();
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
/* 354 */     super.setXScaleArgumentString(xScaleArgument);
/*     */     
/* 356 */     if (this.gateSet != null)
/*     */     {
/* 358 */       this.gateSet.update();
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
/*     */   public void setYScaleFlag(int scale)
/*     */   {
/* 372 */     if (getYScaleFlag() != scale)
/*     */     {
/*     */ 
/* 375 */       super.setYScaleFlag(scale);
/*     */       
/* 377 */       if (this.gateSet != null)
/*     */       {
/* 379 */         this.gateSet.update();
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
/*     */   public void setYScaleArgumentString(String yScaleArgument)
/*     */   {
/* 396 */     super.setYScaleArgumentString(yScaleArgument);
/*     */     
/* 398 */     if (this.gateSet != null)
/*     */     {
/* 400 */       this.gateSet.update();
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
/* 414 */     if (getXMinimum() != min)
/*     */     {
/*     */ 
/* 417 */       super.setXMinimum(min);
/*     */       
/* 419 */       if (this.gateSet != null)
/*     */       {
/* 421 */         this.gateSet.update();
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
/* 436 */     if (getXMaximum() != max)
/*     */     {
/*     */ 
/* 439 */       super.setXMaximum(max);
/*     */       
/* 441 */       if (this.gateSet != null)
/*     */       {
/* 443 */         this.gateSet.update();
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
/*     */   public void setYMinimum(double min)
/*     */   {
/* 458 */     if (getYMinimum() != min)
/*     */     {
/*     */ 
/* 461 */       super.setYMinimum(min);
/*     */       
/* 463 */       if (this.gateSet != null)
/*     */       {
/* 465 */         this.gateSet.update();
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
/*     */   public void setYMaximum(double max)
/*     */   {
/* 480 */     if (getYMaximum() != max)
/*     */     {
/*     */ 
/* 483 */       super.setYMaximum(max);
/*     */       
/* 485 */       if (this.gateSet != null)
/*     */       {
/* 487 */         this.gateSet.update();
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
/* 505 */     return "Quadrant";
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
/* 517 */     return 1;
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
/* 531 */     Point2D[] handles = new Point2D[getHandleCount()];
/*     */     
/*     */ 
/* 534 */     handles[0] = new Point2D.Double(this.x, this.y);
/*     */     
/*     */ 
/* 537 */     return handles;
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
/* 556 */     return null;
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
/*     */   public QuadrantGateSet getGateSet()
/*     */   {
/* 569 */     return this.gateSet;
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
/*     */   public void setGateSet(QuadrantGateSet gateSet)
/*     */   {
/* 583 */     this.gateSet = gateSet;
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
/* 610 */     return true;
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
/* 633 */     return true;
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
/* 659 */     return true;
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
/* 680 */     return true;
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
/* 703 */     return inHandle(point) == 0;
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
/* 722 */     super.move(dx, dy);
/*     */     
/* 724 */     if (this.gateSet != null)
/*     */     {
/* 726 */       this.gateSet.update();
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
/* 751 */     if (isLocked())
/*     */     {
/* 753 */       return;
/*     */     }
/*     */     
/* 756 */     if (handleIndex == 0)
/*     */     {
/*     */ 
/* 759 */       move(dx, dy);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void draw(Graphics2D g, boolean selectedP, Color normalColor, Color selectedColor, Color regionColor)
/*     */   {
/* 792 */     if (g == null)
/*     */     {
/* 794 */       return;
/*     */     }
/*     */     
/*     */ 
/* 798 */     Shape horizontal = new Line2D.Double(0.0D, this.y, this.width, this.y);
/* 799 */     Shape vertical = new Line2D.Double(this.x, 0.0D, this.x, this.height);
/*     */     
/* 801 */     if (selectedP)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 806 */       g.setColor(selectedColor);
/* 807 */       g.draw(horizontal);
/* 808 */       g.draw(vertical);
/*     */       
/*     */ 
/* 811 */       drawHandles(g);
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawLabelHandle(Graphics2D g) {}
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Quadrant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */