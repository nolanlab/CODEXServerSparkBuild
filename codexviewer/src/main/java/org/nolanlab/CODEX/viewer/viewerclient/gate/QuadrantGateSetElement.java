/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import org.jdom2.Element;

/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuadrantGateSetElement
/*     */   extends GateSetElement
/*     */ {
/*     */   protected static final String QUADRANT_NAME = "Quadrant";
/*     */   protected static final String UR_NAME = "UR";
/*     */   protected static final String UL_NAME = "UL";
/*     */   protected static final String LL_NAME = "LL";
/*     */   protected static final String LR_NAME = "LR";
/*     */   
/*     */   protected QuadrantGateSetElement(QuadrantGateSet gateSet)
/*     */   {
/*  68 */     super(gateSet);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  75 */     Quadrant quadrant = gateSet.getQuadrantGate();
/*     */     
/*  77 */     if (quadrant != null)
/*     */     {
/*     */ 
/*  80 */       Element quadrantElement = new Element("Quadrant");
/*  81 */       quadrantElement.addContent(Integer.toString(quadrant.getID()));
/*  82 */       this.element.addContent(quadrantElement);
/*     */     }
/*     */     
/*     */ 
/*  86 */     Gate[] gates = gateSet.toArray();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  92 */     if (gates.length > 0)
/*     */     {
/*     */ 
/*  95 */       Gate ur = gates[0];
/*     */       
/*  97 */       if (ur != null)
/*     */       {
/*     */ 
/* 100 */         Element urElement = new Element("UR");
/* 101 */         urElement.addContent(Integer.toString(ur.getID()));
/* 102 */         this.element.addContent(urElement);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 110 */     if (gates.length > 1)
/*     */     {
/*     */ 
/* 113 */       Gate ul = gates[1];
/*     */       
/* 115 */       if (ul != null)
/*     */       {
/*     */ 
/* 118 */         Element ulElement = new Element("UL");
/* 119 */         ulElement.addContent(Integer.toString(ul.getID()));
/* 120 */         this.element.addContent(ulElement);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 128 */     if (gates.length > 2)
/*     */     {
/*     */ 
/* 131 */       Gate ll = gates[2];
/*     */       
/* 133 */       if (ll != null)
/*     */       {
/*     */ 
/* 136 */         Element llElement = new Element("LL");
/* 137 */         llElement.addContent(Integer.toString(ll.getID()));
/* 138 */         this.element.addContent(llElement);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 146 */     if (gates.length > 3)
/*     */     {
/*     */ 
/* 149 */       Gate lr = gates[3];
/*     */       
/* 151 */       if (lr != null)
/*     */       {
/*     */ 
/* 154 */         Element lrElement = new Element("LR");
/* 155 */         lrElement.addContent(Integer.toString(lr.getID()));
/* 156 */         this.element.addContent(lrElement);
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
/*     */   protected static QuadrantGateSet parseElement(int id, Element element, HashMap<Integer, Gate> gateMap)
/*     */   {
/* 180 */     if ((element == null) || (gateMap == null))
/*     */     {
/* 182 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 190 */     Quadrant quadrant = null;
/*     */     
/*     */ 
/* 193 */     Element quadrantElement = element.getChild("Quadrant");
/*     */     
/* 195 */     if (quadrantElement != null)
/*     */     {
/*     */ 
/*     */ 
/* 199 */       Gate gate = null;
/*     */       
/*     */       try
/*     */       {
/* 203 */         gate = (Gate)gateMap.get(Integer.valueOf(quadrantElement.getText()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 207 */         gate = null;
/*     */       }
/*     */       
/* 210 */       if (gate == null)
/*     */       {
/* 212 */         quadrant = null;
/* 213 */       } else if ((gate instanceof Quadrant))
/*     */       {
/*     */ 
/* 216 */         quadrant = (Quadrant)gate;
/*     */       }
/*     */       else {
/* 219 */         quadrant = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 226 */     Quad ur = null;
/*     */     
/*     */ 
/* 229 */     Element urElement = element.getChild("UR");
/*     */     
/* 231 */     if (urElement != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 236 */       Gate gate = null;
/*     */       
/*     */       try
/*     */       {
/* 240 */         gate = (Gate)gateMap.get(Integer.valueOf(urElement.getText()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 244 */         gate = null;
/*     */       }
/*     */       
/* 247 */       if (gate == null)
/*     */       {
/*     */ 
/* 250 */         ur = null;
/* 251 */       } else if ((gate instanceof Quad))
/*     */       {
/*     */ 
/* 254 */         ur = (Quad)gate;
/*     */       }
/*     */       else {
/* 257 */         ur = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 264 */     Quad ul = null;
/*     */     
/*     */ 
/* 267 */     Element ulElement = element.getChild("UL");
/*     */     
/* 269 */     if (ulElement != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 274 */       Gate gate = null;
/*     */       
/*     */       try
/*     */       {
/* 278 */         gate = (Gate)gateMap.get(Integer.valueOf(ulElement.getText()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 282 */         gate = null;
/*     */       }
/*     */       
/* 285 */       if (gate == null)
/*     */       {
/*     */ 
/* 288 */         ul = null;
/* 289 */       } else if ((gate instanceof Quad))
/*     */       {
/*     */ 
/* 292 */         ul = (Quad)gate;
/*     */       }
/*     */       else {
/* 295 */         ul = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 302 */     Quad ll = null;
/*     */     
/*     */ 
/* 305 */     Element llElement = element.getChild("LL");
/*     */     
/* 307 */     if (llElement != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 312 */       Gate gate = null;
/*     */       
/*     */       try
/*     */       {
/* 316 */         gate = (Gate)gateMap.get(Integer.valueOf(llElement.getText()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 320 */         gate = null;
/*     */       }
/*     */       
/* 323 */       if (gate == null)
/*     */       {
/*     */ 
/* 326 */         ll = null;
/* 327 */       } else if ((gate instanceof Quad))
/*     */       {
/*     */ 
/* 330 */         ll = (Quad)gate;
/*     */       }
/*     */       else {
/* 333 */         ll = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 340 */     Quad lr = null;
/*     */     
/*     */ 
/* 343 */     Element lrElement = element.getChild("LR");
/*     */     
/* 345 */     if (lrElement != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 350 */       Gate gate = null;
/*     */       
/*     */       try
/*     */       {
/* 354 */         gate = (Gate)gateMap.get(Integer.valueOf(lrElement.getText()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 358 */         gate = null;
/*     */       }
/*     */       
/* 361 */       if (gate == null)
/*     */       {
/*     */ 
/* 364 */         lr = null;
/* 365 */       } else if ((gate instanceof Quad))
/*     */       {
/*     */ 
/* 368 */         lr = (Quad)gate;
/*     */       }
/*     */       else {
/* 371 */         lr = null;
/*     */       }
/*     */     }
/*     */     
/* 375 */     if ((quadrant != null) && (ur != null) && (ul != null) && (ll != null) && (lr != null))
/*     */     {
/*     */ 
/* 378 */       return new QuadrantGateSet(id, quadrant, ur, ul, ll, lr);
/*     */     }
/*     */     
/* 381 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\QuadrantGateSetElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */