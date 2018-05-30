/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jdom2.Element;
/*     */ public class PolygonElement
/*     */   extends GateElement
/*     */ {
/*     */   protected static final String POINTS_NAME = "Points";
/*     */   protected static final String POINT_NAME = "Point";
/*     */   protected static final String X_NAME = "x";
/*     */   protected static final String Y_NAME = "y";
/*     */   
/*     */   protected PolygonElement(Polygon gate)
/*     */   {
/*  69 */     super(gate);
/*     */     
/*     */ 
/*  72 */     Point[] points = gate.getPoints();
/*     */     
/*     */ 
/*  75 */     Element pointsElement = new Element("Points");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  80 */     for (int i = 0; i < points.length; i++)
/*     */     {
/*  82 */       Element pointElement = new Element("Point");
/*     */       
/*     */ 
/*  85 */       Element xElement = new Element("x");
/*  86 */       xElement.addContent(Integer.toString(points[i].x));
/*  87 */       pointElement.addContent(xElement);
/*     */       
/*     */ 
/*  90 */       Element yElement = new Element("y");
/*  91 */       yElement.addContent(Integer.toString(points[i].y));
/*  92 */       pointElement.addContent(yElement);
/*     */       
/*     */ 
/*  95 */       pointsElement.addContent(pointElement);
/*     */     }
/*     */     
/*     */ 
/*  99 */     this.element.addContent(pointsElement);
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
/*     */   protected static Polygon parseElement(int id, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize, Element element)
/*     */   {
/* 136 */     ArrayList<Point> points = new ArrayList();
/*     */     
/*     */ 
/* 139 */     Element pointsElement = element.getChild("Points");
/*     */     
/* 141 */     if (pointsElement != null)
/*     */     {
/*     */ 
/*     */ 
/* 145 */       List pointsList = pointsElement.getChildren();
/* 146 */       Iterator pointsIter = pointsList.iterator();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 153 */       while (pointsIter.hasNext())
/*     */       {
/* 155 */         Element pointElement = (Element)pointsIter.next();
/*     */         
/*     */ 
/*     */ 
/* 159 */         boolean hasXP = false;
/* 160 */         boolean hasYP = false;
/*     */         
/*     */ 
/* 163 */         int x = 0;
/* 164 */         int y = 0;
/*     */         
/*     */ 
/* 167 */         Element xElement = pointElement.getChild("x");
/*     */         
/* 169 */         if (xElement != null)
/*     */         {
/*     */ 
/*     */ 
/* 173 */           hasXP = true;
/*     */           
/*     */           try
/*     */           {
/* 177 */             x = Integer.parseInt(xElement.getText());
/*     */           }
/*     */           catch (NumberFormatException nfe)
/*     */           {
/* 181 */             x = 0;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 186 */         Element yElement = pointElement.getChild("y");
/*     */         
/* 188 */         if (yElement != null)
/*     */         {
/*     */ 
/*     */ 
/* 192 */           hasYP = true;
/*     */           
/*     */           try
/*     */           {
/* 196 */             y = Integer.parseInt(yElement.getText());
/*     */           }
/*     */           catch (NumberFormatException nfe)
/*     */           {
/* 200 */             y = 0;
/*     */           }
/*     */         }
/*     */         
/* 204 */         if ((hasXP) && (hasYP))
/*     */         {
/*     */ 
/* 207 */           points.add(new Point(x, y));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 213 */     return new Polygon(id, points, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\PolygonElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */