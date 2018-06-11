/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
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
/*     */ public class EllipseElement
/*     */   extends GateElement
/*     */ {
/*     */   protected static final String X_NAME = "x";
/*     */   protected static final String Y_NAME = "y";
/*     */   protected static final String MAJOR_NAME = "Major";
/*     */   protected static final String MINOR_NAME = "Minor";
/*     */   protected static final String ANGLE_NAME = "Angle";
/*     */   
/*     */   protected EllipseElement(Ellipse gate)
/*     */   {
/*  65 */     super(gate);
/*     */     
/*     */ 
/*  68 */     Element xElement = new Element("x");
/*  69 */     xElement.addContent(Double.toString(gate.getDoubleX()));
/*  70 */     this.element.addContent(xElement);
/*     */     
/*     */ 
/*  73 */     Element yElement = new Element("y");
/*  74 */     yElement.addContent(Double.toString(gate.getDoubleY()));
/*  75 */     this.element.addContent(yElement);
/*     */     
/*     */ 
/*  78 */     Element majorElement = new Element("Major");
/*  79 */     majorElement.addContent(Double.toString(gate.getMajor()));
/*  80 */     this.element.addContent(majorElement);
/*     */     
/*     */ 
/*  83 */     Element minorElement = new Element("Minor");
/*  84 */     minorElement.addContent(Double.toString(gate.getMinor()));
/*  85 */     this.element.addContent(minorElement);
/*     */     
/*     */ 
/*  88 */     Element angleElement = new Element("Angle");
/*  89 */     angleElement.addContent(Double.toString(gate.getAngle()));
/*  90 */     this.element.addContent(angleElement);
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
/*     */   protected static Ellipse parseElement(int id, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize, Element element)
/*     */   {
/* 129 */     double x = 0.0D;
/*     */     
/*     */ 
/* 132 */     Element xElement = element.getChild("x");
/*     */     
/* 134 */     if (xElement != null)
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/* 140 */         x = Double.parseDouble(xElement.getText());
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 144 */         x = 0.0D;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 151 */     double y = 0.0D;
/*     */     
/*     */ 
/* 154 */     Element yElement = element.getChild("y");
/*     */     
/* 156 */     if (yElement != null)
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/* 162 */         y = Double.parseDouble(yElement.getText());
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 166 */         y = 0.0D;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 173 */     double major = 0.0D;
/*     */     
/*     */ 
/* 176 */     Element majorElement = element.getChild("Major");
/*     */     
/* 178 */     if (majorElement != null)
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/* 184 */         major = Double.parseDouble(majorElement.getText());
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 188 */         major = 0.0D;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 195 */     double minor = 0.0D;
/*     */     
/*     */ 
/* 198 */     Element minorElement = element.getChild("Minor");
/*     */     
/* 200 */     if (minorElement != null)
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/* 206 */         minor = Double.parseDouble(minorElement.getText());
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 210 */         minor = 0.0D;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 217 */     double angle = 0.0D;
/*     */     
/*     */ 
/* 220 */     Element angleElement = element.getChild("Angle");
/*     */     
/* 222 */     if (angleElement != null)
/*     */     {
/*     */       try
/*     */       {
/* 226 */         angle = Double.parseDouble(angleElement.getText());
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 230 */         angle = 0.0D;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 235 */     return new Ellipse(id, x, y, major, minor, angle, xChannel, yChannel, -1, xBins, yBins, xSize, ySize);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\EllipseElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */