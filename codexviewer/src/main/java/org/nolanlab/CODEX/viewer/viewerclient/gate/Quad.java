/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Line2D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Quad
/*     */   extends Rectangle
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "Quad";
/*     */   
/*     */   public Quad(int id, int bins, int x, int y, int width, int height, int xChannel, int yChannel)
/*     */   {
/*  96 */     this(id, x, y, width, height, xChannel, yChannel, -1, bins, bins, bins, bins);
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
/*     */   public Quad(int id, int x, int y, int width, int height, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize)
/*     */   {
/* 137 */     super(id, x, y, width, height, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/*     */     
/*     */ 
/* 140 */     super.setLocked(true);
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
/* 152 */     return "Quad";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLocked(boolean lockedP) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 253 */     if (point == null)
/*     */     {
/* 255 */       return -1;
/*     */     }
/*     */     
/* 258 */     if (inLabel(point))
/*     */     {
/*     */ 
/* 261 */       return -2;
/*     */     }
/*     */     
/* 264 */     return -1;
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
/*     */   public void move(int dx, int dy) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 308 */     if (handleIndex == -2)
/*     */     {
/*     */ 
/* 311 */       moveLabel(dx, dy);
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
/*     */   protected void drawBorders(Graphics2D g)
/*     */   {
/* 327 */     if (g == null)
/*     */     {
/* 329 */       return;
/*     */     }
/*     */     
/*     */ 
/* 333 */     Shape top = new Line2D.Double(this.x, this.y + this.height, this.x + this.width, this.y + this.height);
/* 334 */     Shape bottom = new Line2D.Double(this.x, this.y, this.x + this.width, this.y);
/* 335 */     Shape left = new Line2D.Double(this.x, this.y, this.x, this.y + this.height);
/* 336 */     Shape right = new Line2D.Double(this.x + this.width, this.y, this.x + this.width, this.y + this.height);
/*     */     
/* 338 */     if (this.x <= 0)
/*     */     {
/*     */ 
/* 341 */       if (this.y <= 0)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 346 */         g.draw(top);
/* 347 */         g.draw(right);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 353 */         g.draw(bottom);
/* 354 */         g.draw(right);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 359 */     else if (this.y <= 0)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 364 */       g.draw(top);
/* 365 */       g.draw(left);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 371 */       g.draw(bottom);
/* 372 */       g.draw(left);
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
/*     */   public void draw(Graphics2D g, boolean selectedP, Color normalColor, Color selectedColor, Color regionColor)
/*     */   {
/* 404 */     if (g == null)
/*     */     {
/* 406 */       return;
/*     */     }
/*     */     
/*     */ 
/* 410 */     Shape shape = getShape();
/*     */     
/* 412 */     if (shape == null)
/*     */     {
/* 414 */       return;
/*     */     }
/*     */     
/* 417 */     if (selectedP)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 422 */       g.setColor(regionColor);
/* 423 */       g.fill(shape);
/*     */       
/*     */ 
/* 426 */       g.setColor(selectedColor);
/* 427 */       drawBorders(g);
/*     */     }
/*     */     else
/*     */     {
/* 431 */       g.setColor(normalColor);
/* 432 */       drawBorders(g);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Quad.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */