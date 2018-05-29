/*    */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuadrantElement
/*    */   extends GateElement
/*    */ {
/*    */   protected QuadrantElement(Quadrant gate)
/*    */   {
/* 56 */     super(gate, true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected static Quadrant parseElement(int id, int x, int y, int width, int height, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize)
/*    */   {
/* 98 */     return new Quadrant(id, x, y, width, height, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\QuadrantElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */