/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D.Double;
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
/*     */ public abstract class Gate1D
/*     */   extends Gate
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   protected static final int COUNT_CHANNEL = -1;
/*  64 */   protected static final float[] dashArray = { 10.0F };
/*  65 */   protected static final BasicStroke dashed = new BasicStroke(1.0F, 0, 0, 10.0F, dashArray, 0.0F);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Gate1D() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Gate1D(int id, int x, int y, int width, int height, int xChannel, int compensationID, int xBins, int xSize)
/*     */   {
/* 104 */     super(id, x, y, width, height, xChannel, -1, compensationID, xBins, height, xSize, height);
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
/*     */   protected void draw(Graphics2D g, boolean selectedP, int lowerX, int upperX, Color normalColor, Color selectedColor)
/*     */   {
/* 132 */     if (g == null)
/*     */     {
/* 134 */       return;
/*     */     }
/*     */     
/* 137 */     if (selectedP)
/*     */     {
/*     */ 
/* 140 */       g.setColor(selectedColor);
/* 141 */       g.draw(new Line2D.Double(lowerX, this.y, upperX, this.y));
/*     */       
/*     */ 
/* 144 */       drawHandles(g);
/*     */     }
/*     */     else
/*     */     {
/* 148 */       g.setColor(normalColor);
/* 149 */       g.draw(new Line2D.Double(lowerX, this.y, upperX, this.y));
/*     */       
/*     */ 
/* 152 */       drawTickMarks(g);
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
/*     */   protected void drawHandles(Graphics2D g)
/*     */   {
/* 179 */     super.drawHandles(g);
/*     */     
/*     */ 
/* 182 */     drawBoundingLines(g);
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
/*     */   protected void drawBoundingLines(Graphics2D g)
/*     */   {
/* 202 */     if (g == null)
/*     */     {
/* 204 */       return;
/*     */     }
/*     */     
/*     */ 
/* 208 */     Point2D[] handles = getHandles();
/*     */     
/*     */ 
/* 211 */     Stroke saved = g.getStroke();
/*     */     
/*     */ 
/* 214 */     g.setColor(Color.black);
/*     */     
/*     */ 
/* 217 */     g.setStroke(dashed);
/*     */     
/*     */ 
/* 220 */     for (int i = 0; i < handles.length; i++)
/*     */     {
/* 222 */       g.draw(new Line2D.Double(handles[i].getX(), 0.0D, handles[i].getX(), this.height));
/*     */     }
/*     */     
/*     */ 
/* 226 */     g.setStroke(saved);
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
/*     */   protected void drawTickMarks(Graphics2D g)
/*     */   {
/* 246 */     if (g == null)
/*     */     {
/* 248 */       return;
/*     */     }
/*     */     
/*     */ 
/* 252 */     Point2D[] handles = getHandles();
/*     */     
/*     */ 
/* 255 */     for (int i = 0; i < handles.length; i++)
/*     */     {
/* 257 */       Gate.drawTickMark(g, handles[i]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Gate1D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */