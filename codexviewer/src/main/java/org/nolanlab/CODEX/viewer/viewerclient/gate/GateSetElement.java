/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GateSetElement
/*     */ {
/*     */   protected static final String GATESET_NAME = "GateSet";
/*     */   protected static final String ID_NAME = "ID";
/*     */   protected static final String NAME_NAME = "Name";
/*     */   protected static final String TYPE_NAME = "Type";
/*     */   protected static final String GATES_NAME = "Gates";
/*     */   protected static final String GATE_NAME = "Gate";
/*     */   protected Element element;
/*     */   
/*     */   protected GateSetElement(GateSet gateSet)
/*     */   {
/*  93 */     this.element = new Element("GateSet");
/*     */     
/*     */ 
/*  96 */     Element idElement = new Element("ID");
/*  97 */     idElement.addContent(Integer.toString(gateSet.getID()));
/*  98 */     this.element.addContent(idElement);
/*     */     
/*     */ 
/* 101 */     if (gateSet.getName() != null)
/*     */     {
/* 103 */       Element nameElement = new Element("Name");
/* 104 */       nameElement.addContent(gateSet.getName());
/* 105 */       this.element.addContent(nameElement);
/*     */     }
/*     */     
/*     */ 
/* 109 */     Element typeElement = new Element("Type");
/* 110 */     typeElement.addContent(gateSet.getType());
/* 111 */     this.element.addContent(typeElement);
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
/*     */   protected GateSetElement(GateSet gateSet, boolean gatesP)
/*     */   {
/* 136 */     this(gateSet);
/*     */     
/* 138 */     if (gatesP)
/*     */     {
/*     */ 
/*     */ 
/* 142 */       Gate[] gates = gateSet.toArray();
/*     */       
/*     */ 
/* 145 */       Element gatesElement = new Element("Gates");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 150 */       for (int i = 0; i < gates.length; i++)
/*     */       {
/* 152 */         Element gateElement = new Element("Gate");
/* 153 */         gateElement.addContent(Integer.toString(gates[i].getID()));
/* 154 */         gatesElement.addContent(gateElement);
/*     */       }
/*     */       
/*     */ 
/* 158 */       this.element.addContent(gatesElement);
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
/*     */   public Element getElement()
/*     */   {
/* 171 */     return this.element;
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
/*     */   public static GateSetElement getGateSetElement(GateSet gateSet)
/*     */   {
/* 188 */     if (gateSet == null)
/*     */     {
/* 190 */       return null;
/*     */     }
/*     */     
/* 193 */     if ((gateSet instanceof QuadrantGateSet))
/*     */     {
/*     */ 
/* 196 */       return new QuadrantGateSetElement((QuadrantGateSet)gateSet); }
/* 197 */     if ((gateSet instanceof SplitGateSet))
/*     */     {
/*     */ 
/* 200 */       return new SplitGateSetElement((SplitGateSet)gateSet);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 205 */     return new GateSetElement(gateSet, true);
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
/*     */   public static GateSet getGateSet(Element element, HashMap<Integer, Gate> gateMap)
/*     */   {
/* 240 */     if (element == null)
/*     */     {
/* 242 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 250 */     String type = GateElement.getElementType(element);
/*     */     
/* 252 */     if (type == null)
/*     */     {
/*     */ 
/* 255 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 261 */     int id = 0;
/*     */     
/*     */ 
/* 264 */     Element idElement = element.getChild("ID");
/*     */     
/* 266 */     if (idElement == null)
/*     */     {
/* 268 */       return null;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 273 */       id = Integer.parseInt(idElement.getText());
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 277 */       id = 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 283 */     String name = null;
/*     */     
/*     */ 
/* 286 */     Element nameElement = element.getChild("Name");
/*     */     
/* 288 */     if (nameElement != null)
/*     */     {
/* 290 */       name = nameElement.getText();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 297 */     if (type.equals("QuadrantGateSet"))
/*     */     {
/* 299 */       return QuadrantGateSetElement.parseElement(id, element, gateMap); }
/* 300 */     if (type.equals("SplitGateSet"))
/*     */     {
/* 302 */       return SplitGateSetElement.parseElement(id, element, gateMap); }
/* 303 */     if ((type.equals("GateSet")) || (type.equals("CompensationGateSet")))
/*     */     {
/*     */       GateSet gateSet;
/*     */       
/*     */       GateSet gateSet;
/* 308 */       if (type.equals("CompensationGateSet"))
/*     */       {
/*     */ 
/* 311 */         gateSet = new CompensationGateSet(id, name);
/*     */       }
/*     */       else
/*     */       {
/* 315 */         gateSet = new GateSet(id, name);
/*     */       }
/*     */       
/*     */ 
/* 319 */       Element gatesElement = element.getChild("Gates");
/*     */       
/* 321 */       if (gatesElement != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 326 */         List gatesList = gatesElement.getChildren();
/* 327 */         Iterator gatesIter = gatesList.iterator();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 332 */         while (gatesIter.hasNext())
/*     */         {
/* 334 */           Element gateElement = (Element)gatesIter.next();
/*     */           
/*     */           try
/*     */           {
/* 338 */             gateSet.add((Gate)gateMap.get(Integer.valueOf(gateElement.getText())));
/*     */           }
/*     */           catch (NumberFormatException nfe) {}
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 346 */       return gateSet;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 351 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\GateSetElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */