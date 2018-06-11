/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ public class Rectangle
/*     */   extends Gate2D
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "Rectangle";
/*     */   protected int movedX;
/*     */   protected int movedY;
/*     */   protected int movedWidth;
/*     */   protected int movedHeight;
/*     */   protected int rightX;
/*     */   protected int topY;
/*     */   
/*     */   public Rectangle() {}
/*     */   
/*     */   public Rectangle(int id, int bins, int x, int y, int width, int height, int xChannel, int yChannel)
/*     */   {
/* 119 */     this(id, x, y, width, height, xChannel, yChannel, -1, bins, bins, bins, bins);
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
/*     */   public Rectangle(int id, int x, int y, int width, int height, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize)
/*     */   {
/* 160 */     super(id, x, y, width, height, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/*     */     
/*     */ 
/* 163 */     this.labelX = (x + width / 2);
/* 164 */     this.labelY = (y + height / 2);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 170 */     this.rightX = (x + width);
/* 171 */     this.topY = (y + height);
/*     */   }
/*     */   
/*     */   public void setPosition(int x, int y, int width, int height, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize) {
/* 175 */     this.x = x;
/* 176 */     this.y = y;
/* 177 */     this.width = width;
/* 178 */     this.height = height;
/* 179 */     this.xChannel = xChannel;
/* 180 */     this.yChannel = yChannel;
/* 181 */     this.compensationID = compensationID;
/* 182 */     this.xBins = xBins;
/* 183 */     this.yBins = yBins;
/* 184 */     this.xSize = xSize;
/* 185 */     this.ySize = ySize;
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
/* 201 */     return "Rectangle";
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
/* 213 */     return 4;
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
/* 227 */     Point2D[] handles = new Point2D[getHandleCount()];
/*     */     
/*     */ 
/*     */ 
/* 231 */     handles[0] = new Point2D.Double(this.x, this.y);
/*     */     
/* 233 */     handles[1] = new Point2D.Double(this.x + this.width, this.y);
/*     */     
/* 235 */     handles[2] = new Point2D.Double(this.x, this.y + this.height);
/*     */     
/* 237 */     handles[3] = new Point2D.Double(this.x + this.width, this.y + this.height);
/*     */     
/*     */ 
/* 240 */     return handles;
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
/*     */   public Shape getShape()
/*     */   {
/* 257 */     return new Rectangle2D.Double(this.x, this.y, this.width, this.height);
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
/*     */   public void prepareForGating()
/*     */   {
/* 278 */     this.rightX = (this.x + this.width);
/* 279 */     this.topY = (this.y + this.height);
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
/* 300 */     return (this.x <= x) && (x < this.rightX) && (this.y <= y) && (y < this.topY);
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
/* 330 */     if (point == null)
/*     */     {
/* 332 */       return -1;
/*     */     }
/*     */     
/*     */ 
/* 336 */     this.movedX = this.x;
/* 337 */     this.movedY = this.y;
/* 338 */     this.movedWidth = this.width;
/* 339 */     this.movedHeight = this.height;
/*     */     
/*     */ 
/* 342 */     return super.inHandle(point);
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
/* 361 */     if (isLocked())
/*     */     {
/* 363 */       return;
/*     */     }
/*     */     
/* 366 */     if (handleIndex == -2)
/*     */     {
/*     */ 
/* 369 */       moveLabel(dx, dy);
/*     */       
/* 371 */       return; }
/* 372 */     if (handleIndex == 0)
/*     */     {
/* 374 */       this.movedX += dx;
/* 375 */       this.movedY += dy;
/* 376 */       this.movedWidth -= dx;
/* 377 */       this.movedHeight -= dy;
/* 378 */     } else if (handleIndex == 1)
/*     */     {
/* 380 */       this.movedY += dy;
/* 381 */       this.movedWidth += dx;
/* 382 */       this.movedHeight -= dy;
/* 383 */     } else if (handleIndex == 2)
/*     */     {
/* 385 */       this.movedX += dx;
/* 386 */       this.movedWidth -= dx;
/* 387 */       this.movedHeight += dy;
/* 388 */     } else if (handleIndex == 3)
/*     */     {
/*     */ 
/* 391 */       this.movedWidth += dx;
/* 392 */       this.movedHeight += dy;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 401 */     this.x = Math.min(this.movedX, this.movedX + this.movedWidth);
/*     */     
/*     */ 
/* 404 */     this.y = Math.min(this.movedY, this.movedY + this.movedHeight);
/*     */     
/*     */ 
/*     */ 
/* 408 */     this.width = Math.abs(this.movedWidth);
/*     */     
/*     */ 
/*     */ 
/* 412 */     this.height = Math.abs(this.movedHeight);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Rectangle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */