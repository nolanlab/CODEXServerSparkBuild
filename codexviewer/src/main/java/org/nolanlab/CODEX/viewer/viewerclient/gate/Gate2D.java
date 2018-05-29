/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Gate2D
/*     */   extends Gate
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   
/*     */   public Gate2D() {}
/*     */   
/*     */   protected Gate2D(int id, int x, int y, int width, int height, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize)
/*     */   {
/*  89 */     super(id, x, y, width, height, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
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
/*     */   public void draw(Graphics2D g, boolean selectedP, Color normalColor, Color selectedColor, Color regionColor)
/*     */   {
/* 119 */     if (g == null)
/*     */     {
/* 121 */       return;
/*     */     }
/*     */     
/*     */ 
/* 125 */     Shape shape = getShape();
/*     */     
/* 127 */     if (shape == null)
/*     */     {
/* 129 */       return;
/*     */     }
/*     */     
/* 132 */     if (selectedP)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 137 */       g.setColor(regionColor);
/* 138 */       g.fill(shape);
/*     */       
/*     */ 
/* 141 */       g.setColor(selectedColor);
/* 142 */       g.draw(shape);
/*     */       
/*     */ 
/* 145 */       drawHandles(g);
/*     */     }
/*     */     else
/*     */     {
/* 149 */       g.setColor(normalColor);
/* 150 */       g.draw(shape);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Gate2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */