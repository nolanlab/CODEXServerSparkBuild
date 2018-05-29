/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompensationGateSet
/*     */   extends GateSet
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String TYPE = "CompensationGateSet";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CompensationGateSet(int id)
/*     */   {
/*  60 */     super(id);
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
/*     */   public CompensationGateSet(int id, String name)
/*     */   {
/*  76 */     super(id, name);
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
/*  88 */     return "CompensationGateSet";
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
/*     */   public boolean isNegative()
/*     */   {
/* 104 */     Gate[] gateArray = toArray();
/*     */     
/*     */ 
/* 107 */     for (int i = 0; i < gateArray.length; i++) {
/* 108 */       if (gateArray[i].isNegative())
/*     */       {
/*     */ 
/*     */ 
/* 112 */         return true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 118 */     return false;
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
/*     */   public boolean isPositive()
/*     */   {
/* 134 */     Gate[] gateArray = toArray();
/*     */     
/*     */ 
/* 137 */     for (int i = 0; i < gateArray.length; i++) {
/* 138 */       if (gateArray[i].isPositive())
/*     */       {
/*     */ 
/*     */ 
/* 142 */         return true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 148 */     return false;
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
/*     */   public boolean isCleanup()
/*     */   {
/* 172 */     Gate[] gateArray = toArray();
/*     */     
/*     */ 
/* 175 */     for (int i = 0; i < gateArray.length; i++) {
/* 176 */       if ((gateArray[i].isNegative()) || (gateArray[i].isPositive()))
/*     */       {
/*     */ 
/*     */ 
/* 180 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 186 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\CompensationGateSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */