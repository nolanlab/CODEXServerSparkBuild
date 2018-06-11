/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.util.HashMap;
import org.jdom2.Element;

/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SplitGateSetElement
/*     */   extends GateSetElement
/*     */ {
/*     */   protected static final String SPLIT_NAME = "Split";
/*     */   protected static final String LOW_NAME = "Low";
/*     */   protected static final String HIGH_NAME = "High";
/*     */   
/*     */   protected SplitGateSetElement(SplitGateSet gateSet)
/*     */   {
/*  66 */     super(gateSet);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  73 */     Split split = gateSet.getSplitGate();
/*     */     
/*  75 */     if (split != null)
/*     */     {
/*     */ 
/*  78 */       Element splitElement = new Element("Split");
/*  79 */       splitElement.addContent(Integer.toString(split.getID()));
/*  80 */       this.element.addContent(splitElement);
/*     */     }
/*     */     
/*     */ 
/*  84 */     Gate[] gates = gateSet.toArray();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  90 */     if (gates.length > 0)
/*     */     {
/*     */ 
/*  93 */       Gate low = gates[0];
/*     */       
/*  95 */       if (low != null)
/*     */       {
/*     */ 
/*  98 */         Element lowElement = new Element("Low");
/*  99 */         lowElement.addContent(Integer.toString(low.getID()));
/* 100 */         this.element.addContent(lowElement);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 108 */     if (gates.length > 1)
/*     */     {
/*     */ 
/* 111 */       Gate high = gates[1];
/*     */       
/* 113 */       if (high != null)
/*     */       {
/*     */ 
/* 116 */         Element highElement = new Element("High");
/* 117 */         highElement.addContent(Integer.toString(high.getID()));
/* 118 */         this.element.addContent(highElement);
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
/*     */   protected static SplitGateSet parseElement(int id, Element element, HashMap<Integer, Gate> gateMap)
/*     */   {
/* 142 */     if ((element == null) || (gateMap == null))
/*     */     {
/* 144 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 152 */     Split split = null;
/*     */     
/*     */ 
/* 155 */     Element splitElement = element.getChild("Split");
/*     */     
/* 157 */     if (splitElement != null)
/*     */     {
/*     */ 
/*     */ 
/* 161 */       Gate gate = null;
/*     */       
/*     */       try
/*     */       {
/* 165 */         gate = (Gate)gateMap.get(Integer.valueOf(splitElement.getText()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 169 */         gate = null;
/*     */       }
/*     */       
/* 172 */       if (gate == null)
/*     */       {
/* 174 */         split = null;
/* 175 */       } else if ((gate instanceof Split))
/*     */       {
/*     */ 
/* 178 */         split = (Split)gate;
/*     */       }
/*     */       else {
/* 181 */         split = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 188 */     SplitRange low = null;
/*     */     
/*     */ 
/* 191 */     Element lowElement = element.getChild("Low");
/*     */     
/* 193 */     if (lowElement != null)
/*     */     {
/*     */ 
/*     */ 
/* 197 */       Gate gate = null;
/*     */       
/*     */       try
/*     */       {
/* 201 */         gate = (Gate)gateMap.get(Integer.valueOf(lowElement.getText()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 205 */         gate = null;
/*     */       }
/*     */       
/* 208 */       if (gate == null)
/*     */       {
/*     */ 
/* 211 */         low = null;
/* 212 */       } else if ((gate instanceof SplitRange))
/*     */       {
/*     */ 
/* 215 */         low = (SplitRange)gate;
/*     */       }
/*     */       else {
/* 218 */         low = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 225 */     SplitRange high = null;
/*     */     
/*     */ 
/* 228 */     Element highElement = element.getChild("High");
/*     */     
/* 230 */     if (highElement != null)
/*     */     {
/*     */ 
/*     */ 
/* 234 */       Gate gate = null;
/*     */       
/*     */       try
/*     */       {
/* 238 */         gate = (Gate)gateMap.get(Integer.valueOf(highElement.getText()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 242 */         gate = null;
/*     */       }
/*     */       
/* 245 */       if (gate == null)
/*     */       {
/*     */ 
/* 248 */         high = null;
/* 249 */       } else if ((gate instanceof SplitRange))
/*     */       {
/*     */ 
/* 252 */         high = (SplitRange)gate;
/*     */       }
/*     */       else {
/* 255 */         high = null;
/*     */       }
/*     */     }
/*     */     
/* 259 */     if ((split != null) && (low != null) && (high != null))
/*     */     {
/*     */ 
/* 262 */       return new SplitGateSet(id, split, low, high);
/*     */     }
/*     */     
/* 265 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\SplitGateSetElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */