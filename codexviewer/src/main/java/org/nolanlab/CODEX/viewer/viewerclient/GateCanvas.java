/*      */ package org.nolanlab.CODEX.viewer.viewerclient;
/*      */ 
/*      */ import org.nolanlab.CODEX.viewer.viewerclient.gate.*;
import org.nolanlab.CODEX.viewer.viewerclient.i5d.GateClient;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.NoninvertibleTransformException;
/*      */ import java.awt.geom.Point2D;
            import java.awt.geom.Line2D;
/*      */ import java.util.ArrayList;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.event.MouseInputListener;

/*      */ class GateCanvas extends JPanel implements FocusListener, MouseInputListener, KeyListener
/*      */ {

            private final GateClient client;
/*   75 */   static final CanvasMode SELECT = new CanvasMode("Select");

/*   80 */   static final CanvasMode RECTANGLE = new CanvasMode("Rectangle");

/*   85 */   static final CanvasMode ELLIPSE = new CanvasMode("Ellipse");

/*   90 */   static final CanvasMode POLYGON = new CanvasMode("Polygon");

/*   95 */   static final CanvasMode QUADRANT = new CanvasMode("Quadrant");

/*  100 */   static final CanvasMode SPLIT = new CanvasMode("Split");

/*  105 */   static final CanvasMode RANGE = new CanvasMode("Range");
/*      */   private static final int DEFAULT_WIDTH = 256;
/*      */   private static final int DEFAULT_HEIGHT = 256;
/*      */   private static final int BORDER_SIZE = 2;
/*      */   //private final GateClient client;
/*      */   private final Dimension preferredSize;
/*      */   private CanvasMode mode;
/*      */   private int xChannel;
/*      */   private int yChannel;
/*      */   private Image plot;
/*      */   private final AffineTransform cartesian;
/*      */   private final AffineTransform inverse;
/*      */   private final AffineTransform flipped;
/*      */   private boolean gridP;
/*      */   private Point2D point;
/*      */   private Point2D point2;
/*      */   private ArrayList points;
/*      */   private int handleIndex;
/*      */   private Gate current;
/*      */   
/*      */   static final class CanvasMode {
/*  126 */     private CanvasMode(String name) { this.name = name; }
/*      */     /*      */
/*      */     public String toString()
/*      */     {
/*  136 */       return this.name;
/*      */     }
/*      */
/*      */     private final String name;
/*      */   }
/*      */   GateCanvas(GateClient client, CanvasMode mode)
/*      */   {
/*  261 */     this(client, mode, 256, 256);
/*      */   }
/*      */   GateCanvas(GateClient client, CanvasMode mode, int width, int height)
/*      */   {
/*  279 */     this.client = client;
/*      */     
/*      */ 
/*  282 */     this.preferredSize = new Dimension(width + 2 + 2, height + 2 + 2);

/*  285 */     this.mode = mode;
/*      */     
/*      */ 
/*  288 */     this.xChannel = 0;
/*  289 */     this.yChannel = 0;
/*  292 */     this.plot = null;
/*      */     
/*      */
/*  304 */     this.cartesian = getCoordinateTransform(this.preferredSize.getHeight());
/*      */     
/*  306 */     if (this.cartesian == null)
/*      */     {
/*  308 */       this.inverse = null;
/*      */     }
/*      */     else
/*      */     {
/*      */       AffineTransform temp;
/*      */       
/*      */       try
/*      */       {
/*  316 */         temp = this.cartesian.createInverse();
/*      */       }
/*      */       catch (NoninvertibleTransformException nte)
/*      */       {
/*  320 */         temp = null;
/*      */       }
/*      */       
/*      */ 
/*  324 */       this.inverse = temp;
/*      */     }
/*      */     
/*      */ 
/*  328 */     this.flipped = getFlippedTransform(this.preferredSize.getHeight());
/*      */     
/*      */ 
/*      */ 
/*  332 */     this.gridP = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  338 */     this.point = null;
/*  339 */     this.point2 = null;
/*      */     
/*      */ 
/*  342 */     this.points = new ArrayList();
/*      */     
/*      */ 
/*  345 */     this.handleIndex = -1;
/*      */     
/*      */ 
/*  348 */     this.current = null;
/*      */
/*  356 */     setOpaque(true);
/*      */     
/*      */ 
/*  359 */     setBackground(Color.white);
/*      */     
/*      */ 
/*  362 */     setFocusable(true);
/*      */     
/*      */ 
/*  365 */     setFocusTraversalKeysEnabled(false);
/*      */     
/*      */ 
/*  368 */     addFocusListener(this);
/*      */     
/*      */ 
/*  371 */     addMouseListener(this);
/*  372 */     addMouseMotionListener(this);
/*      */     
/*      */ 
/*  375 */     addKeyListener(this);
/*      */   }
/*      */

/*      */   private static AffineTransform getCoordinateTransform(double height)
/*      */   {
/*  410 */     AffineTransform transform = AffineTransform.getTranslateInstance(2.0D, height - 3.0D);
/*      */     
/*      */ 
/*  413 */     transform.scale(1.0D, -1.0D);
/*      */     
/*      */ 
/*  416 */     return transform;
/*      */   }
/*      */   private static AffineTransform getFlippedTransform(double height)
/*      */   {
/*  447 */     AffineTransform transform = AffineTransform.getTranslateInstance(2.0D, height - 3.0D);
/*      */     
/*      */ 
/*  450 */     transform.rotate(-1.5707963267948966D);
/*      */     
/*      */ 
/*  453 */     return transform;
/*      */   }
/*      */
/*      */   private Point2D getMousePoint(double x, double y)
/*      */   {
/*  465 */     if (this.inverse == null)
/*      */     {
/*  467 */       return new Point2D.Double(x - 2.0D, getHeight() - 1.0D - y - 2.0D);
/*      */     }
/*      */     
/*      */ 
/*  471 */     return this.inverse.transform(new Point2D.Double(x, y), null);
/*      */   }
/*      */
/*      */ 
/*      */   public Dimension getPreferredSize()
/*      */   {
/*  496 */     return this.preferredSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Dimension getMinimumSize()
/*      */   {
/*  508 */     return getPreferredSize();
/*      */   }
/*      */   
/*      */
/*      */   public Dimension getMaximumSize()
/*      */   {
/*  520 */     return getPreferredSize();
/*      */   }
/*      */
/*      */   void setMode(CanvasMode mode)
/*      */   {
/*  530 */     if ((mode == null) || (this.mode == mode))
/*      */     {
/*  532 */       return;
/*      */     }
/*      */     
/*      */ 
/*  536 */     this.mode = mode;
/*      */     
/*      */ 
/*  539 */     clear();
/*      */     
/*      */ 
/*  542 */     repaint();
/*      */   }
/*      */
/*      */   private void clear()
/*      */   {
/*  551 */     this.point = null;
/*  552 */     this.point2 = null;
/*      */     
/*      */ 
/*  555 */     this.points.clear();
/*      */     
/*      */ 
/*  558 */     this.handleIndex = -1;
/*      */     
/*      */ 
/*  561 */     this.current = null;
/*      */   }
/*      */
/*      */   int getXChannel()
/*      */   {
/*  571 */     return this.xChannel;
/*      */   }
/*      */
/*      */   void setXChannel(int channel)
/*      */   {
/*  584 */     if (channel < 0)
/*      */     {
/*  586 */       return;
/*      */     }

/*  591 */     this.xChannel = channel;
/*      */     
/*      */ 
/*  594 */     clear();
/*      */   }

/*      */   int getYChannel()
/*      */   {
/*  603 */     return this.yChannel;
/*      */   }

/*      */   void setYChannel(int channel)
/*      */   {
/*  616 */     if (channel < -1)
/*      */     {
/*  618 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  623 */     this.yChannel = channel;
/*      */     
/*      */ 
/*  626 */     clear();
/*      */   }

/*      */   void setPlot(Image plot)
/*      */   {
/*  637 */     this.plot = plot;
/*      */     
/*      */ 
/*  640 */     repaint();
/*      */   }

/*      */   private Ellipse createEllipseGate(Point2D point1, Point2D point2)
/*      */   {
/*  657 */     if ((this.client == null) || (point1 == null) || (point2 == null))
/*      */     {
/*  659 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  664 */     double major = point1.distance(point2);
/*      */     
/*      */ 
/*  667 */     double minor = major / 2.0D;
/*      */     
/*      */ 
/*  670 */     Point2D point = new Point2D.Double((point1.getX() + point2.getX()) / 2.0D, (point1.getY() + point2.getY()) / 2.0D);
/*      */

/*  678 */     double angle = Ellipse.calculateAngle(point1, point2);
/*      */     
/*      */ 
/*  681 */     int axisBins = this.client.getAxisBins();
/*      */     
/*      */ 
/*  684 */     return new Ellipse(this.client.getNextGateID(), point.getX(), point.getY(), (int)major, (int)minor, angle, getXChannel(), getYChannel(),  axisBins, axisBins, axisBins, axisBins);
/*      */   }

/*      */ 
/*      */   private Rectangle createRectangleGate(Point2D point1, Point2D point2)
/*      */   {
/*  697 */     if ((this.client == null) || (point1 == null) || (point2 == null))
/*      */     {
/*  699 */       return null;
/*      */     }

/*  704 */     double leftX = Math.min(point1.getX(), point2.getX());
/*      */     
/*      */ 
/*  707 */     double bottomY = Math.min(point1.getY(), point2.getY());
/*      */     
/*      */ 
/*  710 */     double width = Math.abs(point2.getX() - point1.getX());
/*      */     
/*      */ 
/*  713 */     double height = Math.abs(point2.getY() - point1.getY());
/*      */     
/*      */ 
/*  716 */     int axisBins = this.client.getAxisBins();
/*      */     
/*      */ 
/*  719 */     return new Rectangle(this.client.getNextGateID(), (int)leftX, (int)bottomY, (int)width, (int)height, getXChannel(), getYChannel(), -1, axisBins, axisBins, axisBins, axisBins);
/*      */   }


/*      */   protected void paintComponent(Graphics g)
/*      */   {
/*  766 */     if (g == null)
/*      */     {
/*  768 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  773 */     g.setColor(Color.white);
/*  774 */     g.fillRect(0, 0, getWidth(), getHeight());

/*  781 */     if (this.plot != null)
/*      */     {
/*      */ 
/*      */ 
/*  785 */       int plotWidth = this.plot.getWidth(this);
/*  786 */       int plotHeight = this.plot.getHeight(this);
/*      */       
/*      */ 
/*  789 */       g.drawImage(this.plot, 2, getHeight() - plotHeight - 2, plotWidth, plotHeight, this);
/*      */     }
/*      */     
/*      */ 
/*  793 */     g.setColor(Color.BLACK);
/*  794 */     g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
/*      */     
/*      */ 
/*  797 */     if ((this.cartesian == null) || (this.flipped == null))
/*      */     {
/*  799 */       return;
/*      */     }

/*  806 */     if (!(g instanceof Graphics2D))
/*      */     {
/*  808 */       return;
/*      */     }
/*      */     
/*      */ 
/*  812 */     Graphics2D g2 = (Graphics2D)g;

/*  818 */     AffineTransform saved = g2.getTransform();

/*  825 */     if (this.gridP)
/*      */     {
/*      */ 
/*      */ 
/*  829 */       g2.transform(this.cartesian);
/*      */       
/*      */ 
/*  832 */       g2.setColor(Color.black);
/*      */       
/*      */ 
/*  835 */       double width = getWidth() - 2.0D - 2.0D - 1.0D;
/*      */       
/*      */ 
/*  838 */       double height = getHeight() - 2.0D - 2.0D - 1.0D;
/*      */       
/*      */ 
/*  841 */       g2.draw(new Line2D.Double(0.0D, 0.0D, width, 0.0D));
/*      */       
/*      */ 
/*  844 */       g2.draw(new Line2D.Double(0.0D, -10.0D, 0.0D, height));
/*      */       
/*      */ 
/*  847 */       g2.setColor(Color.lightGray);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  852 */       for (int i = 0; i <= 25; i++)
/*      */       {
/*  854 */         double currentDecade = i * 10.0D;
/*      */         
/*      */ 
/*  857 */         g2.draw(new Line2D.Double(0.0D, currentDecade, width, currentDecade));
/*      */         
/*      */ 
/*  860 */         g2.draw(new Line2D.Double(currentDecade, 0.0D, currentDecade, height));
/*      */       }
/*      */       
/*      */ 
/*  864 */       g2.setTransform(saved);
/*      */     }
/*      */     
/*      */ 
/*      */     Gate[] gates;
/*      */     
/*      */ 
/*      */
/*  873 */     if (this.client == null)
/*      */     {
/*  875 */       gates = new Gate[0];
/*      */     }
/*      */     else
/*      */     {
/*  879 */       gates = this.client.getVisibleGates(this.xChannel, this.yChannel);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  886 */     for (int i = 0; i < gates.length; i++)
/*      */     {
/*  888 */       Gate gate = gates[i];
/*      */       
/*  890 */       if ((gate.getXChannel() == this.xChannel) && (gate.getYChannel() == this.yChannel))
/*      */       {
/*  892 */         g2.transform(this.cartesian);
/*      */       } else {
/*  894 */         if ((gate.getXChannel() != this.yChannel) || (gate.getYChannel() != this.xChannel))
/*      */           continue;
/*  896 */         g2.transform(this.flipped);
/*      */       }

/*  905 */       boolean selectedP = false;
/*      */       
/*  907 */       if (this.client != null)
/*      */       {
/*  909 */         selectedP = this.client.isSelected(gate);
/*      */       }
/*      */       
/*      */ 
/*  913 */       gate.draw(g2, selectedP);
/*      */       
/*  915 */       if (selectedP)
/*      */       {
/*  917 */         gate.drawLabelHandle(g2);
/*      */       }
/*      */       
/*      */ 
/*  921 */       g2.setTransform(saved);
/*      */     }

/*  930 */     g2.transform(this.cartesian);
/*      */     
/*  932 */     if (this.current != null)
/*      */     {
/*  934 */       this.current.draw(g2, true);
/*      */     }
/*      */     
/*  937 */     if ((this.mode == POLYGON) && (this.points != null))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  942 */       for (int i = 1; i < this.points.size(); i++)
/*      */       {
/*  944 */         Point2D p1 = (Point2D)this.points.get(i - 1);
/*  945 */         Point2D p2 = (Point2D)this.points.get(i);
/*      */         
/*      */ 
/*  948 */         g2.setColor(Gate.SELECTED_COLOR);
/*  949 */         g2.draw(new Line2D.Double(p1, p2));
/*      */         
/*      */ 
/*  952 */         Gate.drawHandle(g2, p1);
/*  953 */         Gate.drawHandle(g2, p2);
/*      */       }
/*      */       
/*  956 */       if ((this.points.size() > 0) && (this.point != null))
/*      */       {
/*      */ 
/*      */ 
/*  960 */         Point2D lastPoint = (Point2D)this.points.get(this.points.size() - 1);
/*      */         
/*      */ 
/*  963 */         g2.setColor(Gate.SELECTED_COLOR);
/*  964 */         g2.draw(new Line2D.Double(lastPoint.getX(), lastPoint.getY(), this.point.getX(), this.point.getY()));
/*      */         
/*      */ 
/*  967 */         Gate.drawHandle(g2, lastPoint);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  972 */     g2.setTransform(saved);
/*      */   }

/*      */   public void focusGained(FocusEvent e) {}

/*      */   public void focusLost(FocusEvent e) {}

/*      */   public void mouseClicked(MouseEvent e)
/*      */   {
/* 1013 */     if (e.getButton() == 1)
/*      */     {
/* 1015 */       if (this.client == null)
/*      */       {
/* 1017 */         return;
/*      */       }

/* 1024 */       Point2D p = getMousePoint(e.getX(), e.getY());
/*      */       
/*      */ 
/*      */ 
/* 1028 */       int axisBins = 256;
/*      */       
/* 1030 */       if ((this.mode == SELECT) && (e.getClickCount() == 2))
/*      */       {
/*      */ 
/*      */ 
/* 1034 */         Gate[] gates = this.client.getVisibleGates(this.xChannel, this.yChannel);
/*      */         
/*      */ 
/* 1037 */         Point2D flippedPoint = new Point2D.Double(p.getY(), p.getX());
/*      */         
/*      */ 
/*      */ 
/* 1041 */         for (int i = gates.length - 1; i >= 0; i--) {
/* 1042 */           if (((gates[i].getXChannel() == this.xChannel) && (gates[i].getYChannel() == this.yChannel) && (gates[i].contains(p))) || ((gates[i].getXChannel() == this.yChannel) && (gates[i].getYChannel() == this.xChannel) && (gates[i].contains(flippedPoint))))
/*      */           {
/*      */ 
/*      */
/* 1047 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1051 */
/* 1059 */       else if (this.mode == POLYGON)
/*      */       {
/* 1061 */         if ((e.getClickCount() == 2) || ((this.points.size() > 0) && (p.distance((Point2D)this.points.get(0)) < 7.0D)))
/*      */         {
/*      */ 
/* 1064 */           this.client.addGate(new Polygon(this.client.getNextGateID(), this.points, this.xChannel, this.yChannel, -1, axisBins, axisBins, axisBins, axisBins));
/*      */           
/*      */ 
/* 1067 */           clear();
/*      */         }
/*      */         else
/*      */         {
/* 1071 */           this.points.add(p);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1076 */       this.current = null;
/*      */       
/*      */ 
/* 1079 */       repaint();
/*      */     }
/* 1081 */     else if ((this.mode == POLYGON) && (e.getButton() == 3))
/*      */     {
/* 1083 */       clear();
/*      */       
/*      */ 
/* 1086 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mouseDragged(MouseEvent e)
/*      */   {
/* 1096 */     if (this.client == null)
/*      */     {
/* 1098 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1103 */     if (this.point == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */
/*      */     
/*      */ 
/*      */     Point2D oldPoint;
/*      */     
/*      */ 
/* 1114 */     if (this.point2 == null)
/*      */     {
/* 1116 */       oldPoint = new Point2D.Double(this.point.getX(), this.point.getY());
/*      */     }
/*      */     else
/*      */     {
/* 1120 */       oldPoint = new Point2D.Double(this.point2.getX(), this.point2.getY());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */     this.point2 = getMousePoint(e.getX(), e.getY());
/*      */     
/*      */ 
/* 1130 */     if ((this.point != null) && ((this.mode == ELLIPSE) || (this.mode == RECTANGLE) || (this.mode == RANGE)))
/*      */     {
/* 1132 */       if (this.mode == ELLIPSE)
/*      */       {
/* 1134 */         this.current = createEllipseGate(this.point, this.point2);
/*      */       }
/* 1136 */       else if (this.mode == RECTANGLE)
/*      */       {
/* 1138 */         this.current = createRectangleGate(this.point, this.point2);
/*      */       }
/*      */       
/*      */ 
/* 1146 */       repaint();
/*      */     }
/* 1148 */     else if ((this.mode == SELECT) && (this.client.getSelectedCount() > 0))
/*      */     {
/*      */ 
/*      */ 
/* 1152 */       int dx = (int)this.point2.getX() - (int)oldPoint.getX();
/* 1153 */       int dy = (int)this.point2.getY() - (int)oldPoint.getY();
/*      */       
/*      */ 
/* 1156 */       Gate[] gates = this.client.getSelectedGates(this.xChannel, this.yChannel);
/*      */       
/* 1158 */       if (gates.length > 1)
/*      */       {
/* 1160 */         for (int i = 0; i < gates.length; i++) {
/* 1161 */           if ((gates[i].getXChannel() == this.xChannel) && (gates[i].getYChannel() == this.yChannel))
/*      */           {
/* 1163 */             gates[i].move(dx, dy);
/*      */           }
/* 1165 */           else if ((gates[i].getXChannel() == this.yChannel) && (gates[i].getYChannel() == this.xChannel))
/*      */           {
/* 1167 */             gates[i].move(dy, dx);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1172 */         this.client.setDirtyGate();
/*      */       }
/* 1174 */       else if (gates.length == 1)
/*      */       {
/*      */ 
/*      */ 
/* 1178 */         Gate gate = gates[0];
/*      */         
/* 1180 */         if (this.handleIndex == -1)
/*      */         {
/* 1182 */           if ((gate.getXChannel() == this.xChannel) && (gate.getYChannel() == this.yChannel))
/*      */           {
/* 1184 */             gate.move(dx, dy);
/*      */           }
/* 1186 */           else if ((gate.getXChannel() == this.yChannel) && (gate.getYChannel() == this.xChannel))
/*      */           {
/* 1188 */             gate.move(dy, dx);
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 1193 */         else if ((gate.getXChannel() == this.xChannel) && (gate.getYChannel() == this.yChannel))
/*      */         {
/* 1195 */           gate.move(this.handleIndex, dx, dy);
/*      */         }
/* 1197 */         else if ((gate.getXChannel() == this.yChannel) && (gate.getYChannel() == this.xChannel))
/*      */         {
/* 1199 */           gate.move(this.handleIndex, dy, dx);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1204 */         this.client.setDirtyGate();
/*      */         
/*      */ 
/* 1207 */         this.client.showGate(gate);
/*      */       }
/*      */       
/*      */ 
/* 1211 */       repaint();
/*      */     }
/*      */   }

/*      */   public void mouseEntered(MouseEvent e)
/*      */   {
/* 1226 */     requestFocusInWindow();
/*      */   }

/*      */   public void mouseExited(MouseEvent e) {}

/*      */   public void mouseMoved(MouseEvent e)
/*      */   {
/* 1249 */     if (this.client == null)
/*      */     {
/* 1251 */       return;
/*      */     }

/* 1260 */     Point2D mouse = getMousePoint(e.getX(), e.getY());
/*      */     
/*      */ 
/*      */ 
/* 1264 */     int axisBins = this.client.getAxisBins();
/*      */     
/* 1266 */     if (this.mode == QUADRANT)
/*      */     {
/* 1268 */       this.current = new Quadrant(this.client.getNextGateID(), (int)mouse.getX(), (int)mouse.getY(), getWidth(), getHeight(), getXChannel(), getYChannel(), -1, axisBins, axisBins, axisBins, axisBins);
/*      */       
/*      */ 
/* 1271 */       repaint();
/*      */     }
/* 1273 */     else if (this.mode == SPLIT)
/*      */     {
/* 1275 */       this.current = new Split(this.client.getNextGateID(), (int)mouse.getX(), (int)mouse.getY(), getWidth(), getHeight(), getXChannel(), -1, axisBins, axisBins);
/*      */       
/*      */ 
/* 1278 */       repaint();
/*      */     }
/* 1280 */     else if (this.mode == POLYGON)
/*      */     {
/*      */ 
/*      */ 
/* 1284 */       this.point = mouse;
/*      */       
/*      */ 
/* 1287 */       repaint();
/*      */     }
/*      */   }

/*      */   public void mousePressed(MouseEvent e)
/*      */   {
/* 1299 */     if (this.client == null)
/*      */     {
/* 1301 */       return;
/*      */     }

/* 1307 */     this.point = null;
/*      */     
/* 1309 */     if ((this.mode == SELECT) && (e.isPopupTrigger()))
/*      */     {
/* 1311 */       JPopupMenu popup = this.client.getPopupMenu();
/*      */       
/*      */ 
/* 1314 */       popup.show(e.getComponent(), e.getX(), e.getY());
/*      */     }
/* 1316 */     else if (e.getButton() == 1)
/*      */     {

/* 1323 */       this.point = getMousePoint(e.getX(), e.getY());
/*      */       
/*      */ 
/* 1326 */       if (this.mode == SELECT)
/*      */       {
/* 1330 */         this.handleIndex = -1;
/*      */         
/*      */ 
/* 1333 */         Point2D flippedPoint = new Point2D.Double(this.point.getY(), this.point.getX());
/*      */         
/*      */ 
/* 1336 */         Gate[] gates = this.client.getSelectedGates(this.xChannel, this.yChannel);
/*      */         
/*      */ 
/*      */ 
/* 1340 */         for (int i = gates.length - 1; i >= 0; i--)
/*      */         {
/* 1342 */           if ((gates[i].getXChannel() == this.xChannel) && (gates[i].getYChannel() == this.yChannel) && (gates[i].inHandle(this.point) != -1))
/*      */           {
/* 1344 */             this.handleIndex = gates[i].inHandle(this.point);
/*      */             
/* 1346 */             break;
/*      */           }
/* 1348 */           if ((gates[i].getXChannel() == this.yChannel) && (gates[i].getYChannel() == this.xChannel) && (gates[i].inHandle(flippedPoint) != -1))
/*      */           {
/* 1350 */             this.handleIndex = gates[i].inHandle(flippedPoint);
/*      */             
/* 1352 */             break;
/*      */           }
/*      */         }
/*      */         
/* 1356 */         if (this.handleIndex == -1)
/*      */         {
/*      */ 
/*      */ 
/* 1360 */           gates = this.client.getVisibleGates(this.xChannel, this.yChannel);
/*      */           
/*      */ 
/* 1363 */           boolean foundGateP = false;
/*      */           
/*      */ 
/*      */ 
/* 1367 */           for (int i = gates.length - 1; i >= 0; i--) {
/* 1368 */             if (((gates[i].getXChannel() == this.xChannel) && (gates[i].getYChannel() == this.yChannel) && (gates[i].contains(this.point))) || ((gates[i].getXChannel() == this.yChannel) && (gates[i].getYChannel() == this.xChannel) && (gates[i].contains(flippedPoint))))
/*      */             {
/*      */ 
/* 1371 */               this.client.selectGate(gates[i]);
/*      */               
/*      */ 
/* 1374 */               foundGateP = true;
/*      */               
/* 1376 */               break;
/*      */             }
/*      */           }
/*      */           
/* 1380 */           if (!foundGateP)
/*      */           {
/* 1382 */             this.client.unselect();
/* 1383 */             this.client.setStatus("");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1388 */         repaint();
/*      */       }
/*      */     }
/*      */   }

/*      */   public void mouseReleased(MouseEvent e)
/*      */   {
/* 1402 */     if (this.client == null)
/*      */     {
/* 1404 */       return;
/*      */     }

/* 1410 */     this.point2 = null;
/*      */     
/* 1412 */     if ((this.mode == SELECT) && (e.isPopupTrigger()))
/*      */     {
/* 1414 */       JPopupMenu popup = this.client.getPopupMenu();
/*      */       
/*      */ 
/* 1417 */       popup.show(e.getComponent(), e.getX(), e.getY());
/*      */     }
/* 1419 */     else if (e.getButton() == 1)
/*      */     {

/* 1426 */       this.point2 = getMousePoint(e.getX(), e.getY());
/*      */       
/* 1428 */       if ((this.point != null) && (this.point2 != null) && (this.point.distance(this.point2) > 7.0D))
/*      */       {

/* 1432 */         Gate gate = null;
/*      */         
/* 1434 */         if (this.mode == ELLIPSE)
/*      */         {
/* 1436 */           gate = createEllipseGate(this.point, this.point2);
/*      */         }

/* 1442 */         else if (this.mode == RECTANGLE)
/*      */         {
/* 1444 */           gate = createRectangleGate(this.point, this.point2);
/*      */         }
/*      */         
/* 1447 */         if (gate != null)
/*      */         {
/* 1449 */           this.client.addGate(gate);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1454 */       this.current = null;
/*      */       
/*      */ 
/* 1457 */       this.point2 = null;
/*      */       
/*      */ 
/* 1460 */       repaint();
/*      */     }
/*      */   }

/*      */   public void keyTyped(KeyEvent e) {}

/*      */   public void keyPressed(KeyEvent e)
/*      */   {
/* 1491 */     int keyCode = e.getKeyCode();
/*      */     
/* 1493 */     if (e.isControlDown())
/*      */     {
/* 1495 */       if (keyCode == 82)
/*      */       {
/* 1497 */         repaint();
/*      */       }
/* 1499 */       else if (keyCode == 71)
/*      */       {
/* 1501 */         this.gridP = (!this.gridP);
/*      */         
/*      */ 
/* 1504 */         repaint();
/*      */       }
/* 1506 */       else if (this.client != null)
/*      */       {
/* 1508 */         if (keyCode == 83)
/*      */         {
/* 1510 */           this.client.save();
/*      */         }
/* 1512 */         else if (keyCode == 88)
/*      */         {
/* 1514 */           this.client.cut();
/*      */         }
/* 1516 */         else if (keyCode == 67)
/*      */         {
/* 1518 */           this.client.copy();
/*      */         }
/* 1520 */         else if (keyCode == 86)
/*      */         {
/* 1522 */           this.client.paste();
/*      */         }
/*      */       }
/*      */     }
/* 1526 */     else if (this.mode == SELECT)
/*      */     {
/*      */ 
/* 1529 */       if (this.client == null)
/*      */       {
/* 1531 */         return;
/*      */       }
/*      */       
/*      */ 
/* 1535 */       Gate[] gates = this.client.getVisibleGates(this.xChannel, this.yChannel);
/*      */       
/* 1537 */       if ((gates == null) || (gates.length <= 0))
/*      */       {
/* 1539 */         return;
/*      */       }
/*      */       
/*      */ 
/* 1543 */       boolean[] selectedGates = new boolean[gates.length];
/*      */       
/*      */ 
/* 1546 */       int numSelected = 0;
/*      */       
/*      */ 
/* 1549 */       int firstSelected = -1;
/*      */       
/*      */ 
/* 1552 */       for (int i = 0; i < gates.length; i++)
/*      */       {
/* 1554 */         selectedGates[i] = this.client.isSelected(gates[i]);
/*      */         
/* 1556 */         if (selectedGates[i])
/*      */         {
/* 1558 */           if (numSelected <= 0)
/*      */           {
/* 1560 */             firstSelected = i;
/*      */           }
/*      */           
/*      */ 
/* 1564 */           numSelected++;
/*      */         }
/*      */       }
/*      */       
/* 1568 */       if (keyCode == 9)
/*      */       {
/*      */ 
/* 1571 */         if (gates.length == 1)
/*      */         {
/* 1573 */           this.client.selectGate(gates[0]);
/*      */         }
/* 1575 */         else if (e.isShiftDown())
/*      */         {
/* 1577 */           if (numSelected <= 0)
/*      */           {
/* 1579 */             this.client.selectGate(gates[(gates.length - 1)]);
/*      */ 
/*      */ 
/*      */           }
/* 1583 */           else if (firstSelected > 0)
/*      */           {
/* 1585 */             this.client.selectGate(gates[(firstSelected - 1)]);
/*      */           }
/*      */           else
/*      */           {
/* 1589 */             this.client.selectGate(gates[(gates.length - 1)]);
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 1595 */         else if (numSelected <= 0)
/*      */         {
/* 1597 */           this.client.selectGate(gates[0]);
/*      */ 
/*      */ 
/*      */         }
/* 1601 */         else if (firstSelected < gates.length - 1)
/*      */         {
/* 1603 */           this.client.selectGate(gates[(firstSelected + 1)]);
/*      */         }
/*      */         else
/*      */         {
/* 1607 */           this.client.selectGate(gates[0]);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 1612 */       else if (numSelected > 0)
/*      */       {
/* 1614 */         if ((keyCode == 8) || (keyCode == 127))
/*      */         {

/* 1618 */           for (int i = 0; i < gates.length; i++) {
/* 1619 */             if ((!(gates[i] instanceof Quad)) && (!(gates[i] instanceof SplitRange)))
/*      */             {

/* 1624 */               if (selectedGates[i])
/*      */               {
/* 1626 */                 this.client.removeGate(gates[i]);
/*      */               }
/*      */             }
/*      */           }
/* 1630 */         } else if ((keyCode == 38) || (keyCode == 104) || (keyCode == 40) || (keyCode == 98) || (keyCode == 37) || (keyCode == 100) || (keyCode == 39) || (keyCode == 102))
/*      */         {

/* 1637 */           int xIncrement = 0;
/* 1638 */           int yIncrement = 0;
/*      */           
/* 1640 */           if ((keyCode == 38) || (keyCode == 104))
/*      */           {
/* 1642 */             xIncrement = 0;
/* 1643 */             yIncrement = 1;
/*      */           }
/* 1645 */           else if ((keyCode == 40) || (keyCode == 98))
/*      */           {
/* 1647 */             xIncrement = 0;
/* 1648 */             yIncrement = -1;
/*      */           }
/* 1650 */           else if ((keyCode == 37) || (keyCode == 100))
/*      */           {
/* 1652 */             xIncrement = -1;
/* 1653 */             yIncrement = 0;
/*      */           }
/* 1655 */           else if ((keyCode == 39) || (keyCode == 102))
/*      */           {
/* 1657 */             xIncrement = 1;
/* 1658 */             yIncrement = 0;
/*      */           }
/*      */           
/*      */ 
/* 1662 */           for (int i = 0; i < gates.length; i++) {
/* 1663 */             if (selectedGates[i])
/*      */             {
/* 1665 */               if ((gates[i].getXChannel() == this.xChannel) && (gates[i].getYChannel() == this.yChannel))
/*      */               {
/* 1667 */                 gates[i].move(xIncrement, yIncrement);
/*      */               }
/* 1669 */               else if ((gates[i].getXChannel() == this.yChannel) && (gates[i].getYChannel() == this.xChannel))
/*      */               {
/* 1671 */                 gates[i].move(yIncrement, xIncrement);
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1677 */           this.client.setDirtyGate();
/*      */           
/*      */ 
/* 1680 */           this.client.showGate(gates[firstSelected]);
/*      */         }
/*      */       }

/* 1685 */       repaint();
/*      */     }
/* 1687 */     else if ((keyCode == 8) || (keyCode == 27))
/*      */     {
/* 1689 */       clear();
/*      */       
/*      */ 
/* 1692 */       repaint();
/*      */     }
/* 1694 */     else if (this.client != null)
/*      */     {
/* 1696 */       if (keyCode == 123)
/*      */       {
/* 1698 */         this.client.save();
/*      */       }
/* 1700 */       else if (keyCode == 65489)
/*      */       {
/* 1702 */         this.client.cut();
/*      */       }
/* 1704 */       else if (keyCode == 65485)
/*      */       {
/* 1706 */         this.client.copy();
/*      */       }
/* 1708 */       else if (keyCode == 65487)
/*      */       {
/* 1710 */         this.client.paste();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void keyReleased(KeyEvent e) {}
/*      */ }
