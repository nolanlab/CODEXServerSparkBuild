/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Range
/*     */   extends Gate1D
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "Range";
/*     */   public static final int LABELY_OFFSET = 10;
/*     */   protected int movedX;
/*     */   protected int movedWidth;
/*     */   private int rightX;
/*     */   
/*     */   public Range() {}
/*     */   
/*     */   public Range(int id, int bins, int x, int y, int width, int height, int xChannel)
/*     */   {
/* 117 */     this(id, x, y, width, height, xChannel, -1, bins, bins);
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
/*     */   public Range(int id, int x, int y, int width, int height, int xChannel, int compensationID, int xBins, int xSize)
/*     */   {
/* 149 */     super(id, x, y, width, height, xChannel, compensationID, xBins, xSize);
/*     */     
/*     */ 
/*     */ 
/* 153 */     this.labelX = (x + width / 2);
/* 154 */     this.labelY = (y + 10);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */     this.rightX = getRightX();
/*     */   }
/*     */   
/*     */   public void setPosition(int x, int y, int width, int height, int xChannel, int compensationID, int xBins, int xSize) {
/* 164 */     this.x = x;
/* 165 */     this.y = y;
/* 166 */     this.width = width;
/* 167 */     this.height = height;
/* 168 */     this.xChannel = xChannel;
/* 169 */     this.compensationID = compensationID;
/* 170 */     this.xBins = xBins;
/* 171 */     this.xSize = xSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLeftX()
/*     */   {
/* 183 */     return this.x;
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
/*     */   public int getLowerX()
/*     */   {
/* 199 */     return getLeftX();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRightX()
/*     */   {
/* 211 */     return this.x + this.width;
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
/*     */   public int getUpperX()
/*     */   {
/* 227 */     return getRightX();
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
/* 243 */     return "Range";
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
/* 255 */     return 2;
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
/* 269 */     Point2D[] handles = new Point2D[getHandleCount()];
/*     */     
/*     */ 
/* 272 */     handles[0] = new Point2D.Double(getLeftX(), this.y);
/* 273 */     handles[1] = new Point2D.Double(getRightX(), this.y);
/*     */     
/*     */ 
/* 276 */     return handles;
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
/*     */   public Shape getShape()
/*     */   {
/* 299 */     return new Rectangle2D.Double(getLeftX(), getY() - 4, this.width, 7.0D);
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
/*     */   public void prepareForGating()
/*     */   {
/* 319 */     this.rightX = getRightX();
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
/*     */   public boolean contains(int x)
/*     */   {
/* 338 */     return contains(x);
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
/*     */   public boolean contains(double x)
/*     */   {
/* 363 */     return (this.x <= x) && (x < this.rightX);
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
/*     */   public boolean contains(int x, int y)
/*     */   {
/* 386 */     return contains(x);
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
/*     */   public boolean contains(double x, double y)
/*     */   {
/* 408 */     return contains(x);
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
/*     */   public int inHandle(Point2D point)
/*     */   {
/* 438 */     if (point == null)
/*     */     {
/* 440 */       return -1;
/*     */     }
/*     */     
/*     */ 
/* 444 */     this.movedX = this.x;
/* 445 */     this.movedWidth = this.width;
/*     */     
/*     */ 
/* 448 */     return super.inHandle(point);
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
/*     */   public void move(int handleIndex, int dx, int dy)
/*     */   {
/* 467 */     if (isLocked())
/*     */     {
/* 469 */       return;
/*     */     }
/*     */     
/* 472 */     if (handleIndex == -2)
/*     */     {
/*     */ 
/* 475 */       moveLabel(dx, dy);
/*     */       
/* 477 */       return; }
/* 478 */     if (handleIndex == 0)
/*     */     {
/*     */ 
/* 481 */       this.movedX += dx;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 486 */       this.movedWidth -= dx;
/* 487 */     } else if (handleIndex == 1)
/*     */     {
/*     */ 
/* 490 */       this.movedWidth += dx;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 498 */     this.x = Math.min(this.movedX, this.movedX + this.movedWidth);
/*     */     
/*     */ 
/*     */ 
/* 502 */     this.width = Math.abs(this.movedWidth);
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
/* 528 */     if (g == null)
/*     */     {
/* 530 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 535 */     draw(g, selectedP, getLeftX(), getRightX(), normalColor, selectedColor);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Range.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */