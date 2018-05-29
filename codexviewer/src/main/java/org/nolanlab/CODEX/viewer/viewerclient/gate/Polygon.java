/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Polygon
/*     */   extends Gate2D
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "Polygon";
/*     */   protected int[] xcoords;
/*     */   protected int[] ycoords;
/*     */   private int npoints;
/*     */   private double[] slopes;
/*     */   
/*     */   public Polygon() {}
/*     */   
/*     */   public Polygon(int id, int bins, Point[] points, int xChannel, int yChannel)
/*     */   {
/* 111 */     this(id, bins, Arrays.asList(points), xChannel, yChannel);
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
/*     */   public Polygon(int id, int bins, List points, int xChannel, int yChannel)
/*     */   {
/* 150 */     this(id, points, xChannel, yChannel, -1, bins, bins, bins, bins);
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
/*     */ 
/*     */   public Polygon(int id, List points, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize)
/*     */   {
/* 192 */     super(id, 0, 0, 0, 0, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/*     */     
/* 194 */     this.labelX = 0;
/* 195 */     this.labelY = 0;
/*     */     
/* 197 */     if (points == null) {
/* 198 */       this.xcoords = new int[0];
/* 199 */       this.ycoords = new int[0];
/* 200 */       return;
/*     */     }
/*     */     
/* 203 */     this.xcoords = new int[points.size()];
/* 204 */     this.ycoords = new int[points.size()];
/*     */     
/*     */ 
/*     */ 
/* 208 */     Iterator iter = points.iterator();
/* 209 */     int index = 0;
/*     */     
/* 211 */     while (iter.hasNext()) {
/* 212 */       Point2D point = (Point2D)iter.next();
/* 213 */       this.xcoords[index] = ((int)point.getX());
/* 214 */       this.ycoords[index] = ((int)point.getY());
/*     */       
/* 216 */       this.labelX += this.xcoords[index];
/* 217 */       this.labelY += this.ycoords[index];
/*     */       
/* 219 */       index++;
/*     */     }
/*     */     
/* 222 */     if (points.size() > 1) {
/* 223 */       this.labelX /= points.size();
/* 224 */       this.labelY /= points.size();
/*     */     }
/*     */     
/* 227 */     this.npoints = getPointCount();
/* 228 */     this.slopes = new double[this.npoints];
/*     */   }
/*     */   
/*     */   public void setPosition(List points, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize) {
/* 232 */     this.xcoords = new int[points.size()];
/* 233 */     this.ycoords = new int[points.size()];
/*     */     
/*     */ 
/* 236 */     Iterator iter = points.iterator();
/* 237 */     int index = 0;
/*     */     
/* 239 */     while (iter.hasNext()) {
/* 240 */       Point2D point = (Point2D)iter.next();
/* 241 */       this.xcoords[index] = ((int)point.getX());
/* 242 */       this.ycoords[index] = ((int)point.getY());
/* 243 */       index++;
/*     */     }
/*     */     
/* 246 */     this.npoints = getPointCount();
/* 247 */     this.slopes = new double[this.npoints];
/*     */     
/* 249 */     this.xChannel = xChannel;
/* 250 */     this.yChannel = yChannel;
/* 251 */     this.compensationID = compensationID;
/* 252 */     this.xBins = xBins;
/* 253 */     this.yBins = yBins;
/* 254 */     this.xSize = xSize;
/* 255 */     this.ySize = ySize;
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
/* 271 */     return "Polygon";
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
/* 283 */     return getPointCount();
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
/*     */   public Point2D[] getHandles()
/*     */   {
/* 296 */     return getPoints();
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
/*     */   public Shape getShape()
/*     */   {
/* 312 */     return new java.awt.Polygon(this.xcoords, this.ycoords, this.xcoords.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getPointCount()
/*     */   {
/* 324 */     return this.xcoords.length;
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
/*     */   public Point[] getPoints()
/*     */   {
/* 337 */     Point[] points = new Point[getPointCount()];
/*     */     
/*     */ 
/* 340 */     for (int i = 0; i < points.length; i++)
/*     */     {
/* 342 */       points[i] = new Point(this.xcoords[i], this.ycoords[i]);
/*     */     }
/*     */     
/*     */ 
/* 346 */     return points;
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
/* 367 */     this.npoints = getPointCount();
/*     */     
/*     */ 
/* 370 */     this.slopes = new double[this.npoints];
/*     */     
/*     */ 
/* 373 */     int i = 0; for (int j = this.npoints - 1; i < this.npoints; j = i++) {
/* 374 */       if (this.ycoords[i] == this.ycoords[j])
/*     */       {
/*     */ 
/* 377 */         this.slopes[i] = 0.0D;
/*     */       }
/*     */       else
/*     */       {
/* 381 */         this.slopes[i] = ((this.xcoords[i] - this.xcoords[j]) / (this.ycoords[i] - this.ycoords[j]));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(double x, double y)
/*     */   {
/* 406 */     boolean in = false;
/*     */     
/* 408 */     int i = 0; for (int j = this.npoints - 1; i < this.npoints; j = i++)
/*     */     {
/*     */ 
/* 411 */       if ((this.xcoords[i] > x) || (this.xcoords[j] > x))
/*     */       {
/*     */ 
/* 414 */         if (((y > this.ycoords[i] ? 1 : 0) ^ (y > this.ycoords[j] ? 1 : 0)) != 0)
/*     */         {
/*     */ 
/* 417 */           if (this.xcoords[i] + (y - this.ycoords[i]) * this.slopes[i] > x) {
/* 418 */             in = !in;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 425 */     return in;
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
/*     */   public void move(int dx, int dy)
/*     */   {
/* 440 */     if (isLocked())
/*     */     {
/* 442 */       return;
/*     */     }
/*     */     
/*     */ 
/* 446 */     int numPoints = getPointCount();
/*     */     
/*     */ 
/* 449 */     for (int i = 0; i < numPoints; i++)
/*     */     {
/* 451 */       this.xcoords[i] += dx;
/* 452 */       this.ycoords[i] += dy;
/*     */     }
/*     */     
/*     */ 
/* 456 */     moveLabel(dx, dy);
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
/* 475 */     if (isLocked())
/*     */     {
/* 477 */       return;
/*     */     }
/*     */     
/* 480 */     if (handleIndex == -2)
/*     */     {
/*     */ 
/* 483 */       moveLabel(dx, dy);
/* 484 */     } else if ((handleIndex >= 0) && (handleIndex < getPointCount()))
/*     */     {
/*     */ 
/* 487 */       this.xcoords[handleIndex] += dx;
/* 488 */       this.ycoords[handleIndex] += dy;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPoints(Point[] points)
/*     */   {
/* 499 */     this.npoints = points.length;
/* 500 */     this.xcoords = new int[this.npoints];
/* 501 */     this.ycoords = new int[this.npoints];
/*     */     
/* 503 */     for (int i = 0; i < this.npoints; i++) {
/* 504 */       this.xcoords[i] = points[i].x;
/* 505 */       this.ycoords[i] = points[i].y;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPoints(Point2D[] points)
/*     */   {
/* 516 */     this.npoints = points.length;
/* 517 */     this.xcoords = new int[this.npoints];
/* 518 */     this.ycoords = new int[this.npoints];
/*     */     
/* 520 */     for (int i = 0; i < this.npoints; i++) {
/* 521 */       this.xcoords[i] = ((int)points[i].getX());
/* 522 */       this.ycoords[i] = ((int)points[i].getY());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Polygon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */