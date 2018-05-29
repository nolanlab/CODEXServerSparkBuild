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
/*    */ public class SplitElement
/*    */   extends GateElement
/*    */ {
/*    */   protected SplitElement(Split gate)
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
/*    */   protected static Split parseElement(int id, int x, int y, int width, int height, int xChannel, int compensationID, int xBins, int xSize)
/*    */   {
/* 89 */     return new Split(id, x, y, width, height, xChannel, compensationID, xBins, xSize);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\SplitElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */