/*      */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*      */ 
/*      */ import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.EigenvalueDecomposition;
import org.nolanlab.CODEX.segm.segmserver.MatrixOp;

import java.awt.Shape;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Arrays;

/*      */ public class Ellipse
/*      */   extends Gate2D
/*      */ {
/*      */   private static final long serialVersionUID = 4649L;
/*      */   public static final String TYPE = "Ellipse";
/*      */   private static final double fitEllipseToPoints_default_tolerance = 1.0E-20D;
/*      */   private static final int fitEllipseToPoints_default_maxIterations = 100000;
/*      */   protected double doubleX;
/*      */   private double doubleY;
/*      */   private double major;
/*      */   private double minor;
/*      */   private double angle;
/*      */   private double leftMajorX;
/*      */   private double leftMajorY;
/*      */   private double rightMajorX;
/*      */   private double rightMajorY;
/*      */   private double bottomMinorX;
/*      */   private double bottomMinorY;
/*      */   private double topMinorX;
/*      */   private double topMinorY;
/*      */   private double movedX;
/*      */   private double movedY;
/*      */   private double oneoverasquared;
/*      */   private double oneoverbsquared;
/*      */   private double costheta;
/*      */   private double sintheta;
/*      */   
/*      */   public Ellipse() {}
/*      */   
/*      */   public Ellipse(int id, int bins, Point2D center, double major, double minor, double angle, int xChannel, int yChannel)
/*      */   {
/*  176 */     this(id, bins, center.getX(), center.getY(), major, minor, angle, xChannel, yChannel);
/*      */   }

/*      */ 
/*      */ 
/*      */   public Ellipse(int id, int bins, double doubleX, double doubleY, double major, double minor, double angle, int xChannel, int yChannel)
/*      */   {
/*  218 */     this(id, doubleX, doubleY, major, minor, angle, xChannel, yChannel, -1, bins, bins, bins, bins);
/*      */   }

/*      */   public Ellipse(int id, double doubleX, double doubleY, double major, double minor, double angle, int xChannel, int yChannel, int compID, int xBins, int yBins, int xSize, int ySize)
/*      */   {
/*  265 */     super(id, (int)doubleX, (int)doubleY, (int)major, (int)minor, xChannel, yChannel, -1, xBins, yBins, xSize, ySize);
/*      */     
/*      */ 
/*  268 */     this.doubleX = doubleX;
/*  269 */     this.doubleY = doubleY;
/*  270 */     this.major = major;
/*  271 */     this.minor = minor;
/*      */     
/*      */ 
/*  274 */     this.angle = angle;
/*      */     
/*      */ 
/*  277 */     this.labelX = ((int)this.doubleX);
/*  278 */     this.labelY = ((int)this.doubleY);
/*      */     
/*      */ 
/*  281 */     this.leftMajorX = 0.0D;
/*  282 */     this.leftMajorY = 0.0D;
/*  283 */     this.rightMajorX = 0.0D;
/*  284 */     this.rightMajorY = 0.0D;
/*  285 */     this.bottomMinorX = 0.0D;
/*  286 */     this.bottomMinorY = 0.0D;
/*  287 */     this.topMinorX = 0.0D;
/*  288 */     this.topMinorY = 0.0D;
/*  289 */     this.movedX = 0.0D;
/*  290 */     this.movedY = 0.0D;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  296 */     this.oneoverasquared = (4.0D / (this.major * this.major));
/*  297 */     this.oneoverbsquared = (4.0D / (this.minor * this.minor));
/*  298 */     this.costheta = Math.cos(angle);
/*  299 */     this.sintheta = Math.sin(angle);
/*      */   }
/*      */   
/*      */   public void setPosition(double doubleX, double doubleY, double major, double minor, double angle, int xChannel, int yChannel, int compensationID, int xBins, int yBins, int xSize, int ySize) {
/*  303 */     this.doubleX = doubleX;
/*  304 */     setDoubleY(doubleY);
/*  305 */     setMajor(major);
/*  306 */     setMinor(minor);
/*  307 */     setAngle(angle);
/*  308 */     this.xChannel = xChannel;
/*  309 */     this.yChannel = yChannel;
/*  310 */     this.compensationID = compensationID;
/*  311 */     this.xBins = xBins;
/*  312 */     this.yBins = yBins;
/*  313 */     this.xSize = xSize;
/*  314 */     this.ySize = ySize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDoubleX()
/*      */   {
/*  326 */     return this.doubleX;
/*      */   }
/*      */   
/*      */   public void setDoubleX(double doubleX) {
/*  330 */     this.doubleX = doubleX;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDoubleY()
/*      */   {
/*  342 */     return this.doubleY;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getMajor()
/*      */   {
/*  354 */     return this.major;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getMinor()
/*      */   {
/*  366 */     return this.minor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getAngle()
/*      */   {
/*  380 */     return this.angle;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private AffineTransform getTransform()
/*      */   {
/*  396 */     AffineTransform ellipseAT = AffineTransform.getTranslateInstance(this.doubleX, this.doubleY);
/*      */     
/*      */ 
/*  399 */     ellipseAT.rotate(this.angle);
/*      */     
/*      */ 
/*  402 */     return ellipseAT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getType()
/*      */   {
/*  418 */     return "Ellipse";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getHandleCount()
/*      */   {
/*  430 */     return 4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Point2D[] getHandles()
/*      */   {
/*  444 */     Point2D[] handles = new Point2D[getHandleCount()];
/*      */     
/*      */ 
/*  447 */     AffineTransform ellipseAT = getTransform();
/*      */     
/*      */ 
/*      */ 
/*  451 */     handles[0] = ellipseAT.transform(new Point2D.Double(-(this.major / 2.0D), 0.0D), null);
/*      */     
/*  453 */     handles[1] = ellipseAT.transform(new Point2D.Double(this.major / 2.0D, 0.0D), null);
/*      */     
/*  455 */     handles[2] = ellipseAT.transform(new Point2D.Double(0.0D, -(this.minor / 2.0D)), null);
/*      */     
/*  457 */     handles[3] = ellipseAT.transform(new Point2D.Double(0.0D, this.minor / 2.0D), null);
/*      */     
/*      */ 
/*  460 */     return handles;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Shape getShape()
/*      */   {
/*  477 */     AffineTransform ellipseAT = getTransform();
/*      */     
/*      */ 
/*  480 */     return ellipseAT.createTransformedShape(new Ellipse2D.Double(-(this.major / 2.0D), -(this.minor / 2.0D), this.major, this.minor));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void prepareForGating()
/*      */   {
/*  518 */     this.oneoverasquared = (4.0D / (this.major * this.major));
/*  519 */     this.oneoverbsquared = (4.0D / (this.minor * this.minor));
/*  520 */     this.costheta = Math.cos(this.angle);
/*  521 */     this.sintheta = Math.sin(this.angle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(double x, double y)
/*      */   {
/*  547 */     x -= this.doubleX;
/*  548 */     y -= this.doubleY;
/*      */     
/*      */ 
/*  551 */     double xr = x * this.costheta + y * this.sintheta;
/*  552 */     double yr = -x * this.sintheta + y * this.costheta;
/*      */     
/*      */ 
/*  555 */     return xr * xr * this.oneoverasquared + yr * yr * this.oneoverbsquared < 1.0D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void updateEllipse(Point2D center, double major, double minor, double angle)
/*      */   {
/*  580 */     if (center == null)
/*      */     {
/*  582 */       return;
/*      */     }
/*      */     
/*  585 */     if (isLocked())
/*      */     {
/*  587 */       return;
/*      */     }
/*      */     
/*      */ 
/*  591 */     this.doubleX = center.getX();
/*  592 */     setDoubleY(center.getY());
/*  593 */     setMajor(major);
/*  594 */     setMinor(minor);
/*      */     
/*      */ 
/*  597 */     this.x = ((int)this.doubleX);
/*  598 */     this.y = ((int)this.doubleY);
/*  599 */     this.width = ((int)this.major);
/*  600 */     this.height = ((int)this.minor);
/*      */     
/*      */ 
/*  603 */     setAngle(angle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int inHandle(Point2D point)
/*      */   {
/*  627 */     if (point == null)
/*      */     {
/*  629 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*  633 */     Point2D[] handles = getHandles();
/*      */     
/*      */ 
/*  636 */     this.leftMajorX = handles[0].getX();
/*  637 */     this.leftMajorY = handles[0].getY();
/*  638 */     this.rightMajorX = handles[1].getX();
/*  639 */     this.rightMajorY = handles[1].getY();
/*  640 */     this.bottomMinorX = handles[2].getX();
/*  641 */     this.bottomMinorY = handles[2].getY();
/*  642 */     this.topMinorX = handles[3].getX();
/*  643 */     this.topMinorY = handles[3].getY();
/*      */     
/*      */ 
/*  646 */     int handleIndex = super.inHandle(point);
/*      */     
/*      */ 
/*  649 */     if (handleIndex == 0)
/*      */     {
/*      */ 
/*  652 */       this.movedX = this.leftMajorX;
/*  653 */       this.movedY = this.leftMajorY;
/*  654 */     } else if (handleIndex == 1)
/*      */     {
/*      */ 
/*  657 */       this.movedX = this.rightMajorX;
/*  658 */       this.movedY = this.rightMajorY;
/*  659 */     } else if (handleIndex == 2)
/*      */     {
/*      */ 
/*  662 */       this.movedX = this.bottomMinorX;
/*  663 */       this.movedY = this.bottomMinorY;
/*  664 */     } else if (handleIndex == 3)
/*      */     {
/*      */ 
/*  667 */       this.movedX = this.topMinorX;
/*  668 */       this.movedY = this.topMinorY;
/*      */     }
/*      */     
/*      */ 
/*  672 */     return handleIndex;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void move(int dx, int dy)
/*      */   {
/*  687 */     if (isLocked())
/*      */     {
/*  689 */       return;
/*      */     }
/*      */     
/*      */ 
/*  693 */     this.doubleX += dx;
/*  694 */     setDoubleY(this.doubleY + dy);
/*      */     
/*      */ 
/*  697 */     super.move(dx, dy);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void move(int handleIndex, int dx, int dy)
/*      */   {
/*  716 */     if (isLocked())
/*      */     {
/*  718 */       return;
/*      */     }
/*      */     
/*  721 */     if (handleIndex == -2)
/*      */     {
/*      */ 
/*  724 */       moveLabel(dx, dy);
/*      */       
/*  726 */       return;
/*      */     }
/*      */     
/*      */ 
/*  730 */     this.movedX += dx;
/*  731 */     this.movedY += dy;
/*      */     
/*      */ 
/*  734 */     Point2D leftMajor = new Point2D.Double(this.leftMajorX, this.leftMajorY);
/*  735 */     Point2D rightMajor = new Point2D.Double(this.rightMajorX, this.rightMajorY);
/*  736 */     Point2D bottomMinor = new Point2D.Double(this.bottomMinorX, this.bottomMinorY);
/*  737 */     Point2D topMinor = new Point2D.Double(this.topMinorX, this.topMinorY);
/*  738 */     Point2D movedPoint = new Point2D.Double(this.movedX, this.movedY);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  743 */     if (handleIndex == 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  748 */       Point2D newCenter = new Point2D.Double((movedPoint.getX() + rightMajor.getX()) / 2.0D, (movedPoint.getY() + rightMajor.getY()) / 2.0D);
/*      */       
/*      */ 
/*  751 */       updateEllipse(newCenter, movedPoint.distance(rightMajor), topMinor.distance(bottomMinor), calculateAngle(movedPoint, rightMajor));
/*  752 */     } else if (handleIndex == 1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  757 */       Point2D newCenter = new Point2D.Double((leftMajor.getX() + movedPoint.getX()) / 2.0D, (leftMajor.getY() + movedPoint.getY()) / 2.0D);
/*      */       
/*      */ 
/*  760 */       updateEllipse(newCenter, leftMajor.distance(movedPoint), topMinor.distance(bottomMinor), calculateAngle(leftMajor, movedPoint));
/*  761 */     } else if (handleIndex == 2)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  766 */       Point2D newCenter = new Point2D.Double((topMinor.getX() + movedPoint.getX()) / 2.0D, (topMinor.getY() + movedPoint.getY()) / 2.0D);
/*      */       
/*      */ 
/*  769 */       double angle = calculateAngle(topMinor, movedPoint);
/*      */       
/*      */ 
/*      */ 
/*  773 */       angle = Math.min(angle - 1.5707963267948966D, angle + 1.5707963267948966D);
/*      */       
/*      */ 
/*  776 */       updateEllipse(newCenter, leftMajor.distance(rightMajor), topMinor.distance(movedPoint), angle);
/*  777 */     } else if (handleIndex == 3)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  782 */       Point2D newCenter = new Point2D.Double((movedPoint.getX() + bottomMinor.getX()) / 2.0D, (movedPoint.getY() + bottomMinor.getY()) / 2.0D);
/*      */       
/*      */ 
/*  785 */       double angle = calculateAngle(movedPoint, bottomMinor);
/*      */       
/*      */ 
/*      */ 
/*  789 */       angle = Math.min(angle - 1.5707963267948966D, angle + 1.5707963267948966D);
/*      */       
/*      */ 
/*  792 */       updateEllipse(newCenter, leftMajor.distance(rightMajor), movedPoint.distance(bottomMinor), angle);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static double calculateAngle(Point2D point1, Point2D point2)
/*      */   {
/*  817 */     if ((point1 == null) || (point2 == null))
/*      */     {
/*  819 */       return Double.NaN;
/*      */     }
/*      */     
/*  827 */     if (point1.getX() == point2.getX())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  833 */       return 1.5707963267948966D; }
/*  834 */     if (point1.getX() < point2.getX())
/*      */     {
/*      */ 
/*      */ 
/*  838 */       return Math.atan((point2.getY() - point1.getY()) / (point2.getX() - point1.getX()));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  843 */     return Math.atan((point1.getY() - point2.getY()) / (point1.getX() - point2.getX()));
/*      */   }
/*      */   
/*      */   public DoubleMatrix2D getCovarianceMatrix()
/*      */   {
/*  848 */     DoubleMatrix2D covInverse = new DenseDoubleMatrix2D(2, 2);
/*  849 */     double cosAngle = Math.cos(this.angle);
/*  850 */     double cosAngleSquare = cosAngle * cosAngle;
/*  851 */     double sinAngle = Math.sin(this.angle);
/*  852 */     double sinAngleSquare = sinAngle * sinAngle;
/*  853 */     double a = this.major / 2.0D;
/*  854 */     double b = this.minor / 2.0D;
/*  855 */     double aSquare = a * a;
/*  856 */     double bSquare = b * b;
/*      */     
/*  858 */     covInverse.set(0, 0, cosAngleSquare / aSquare + sinAngleSquare / bSquare);
/*  859 */     covInverse.set(1, 1, sinAngleSquare / aSquare + cosAngleSquare / bSquare);
/*      */     
/*  861 */     double nonDiagonalValue = sinAngle * cosAngle * (1.0D / aSquare - 1.0D / bSquare);
/*  862 */     covInverse.set(0, 1, nonDiagonalValue);
/*  863 */     covInverse.set(1, 0, nonDiagonalValue);
/*      */     
/*  865 */     return Algebra.DEFAULT.inverse(covInverse);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setDoubleY(double doubleY)
/*      */   {
/*  872 */     this.doubleY = doubleY;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setMajor(double major)
/*      */   {
/*  879 */     this.major = major;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setMinor(double minor)
/*      */   {
/*  886 */     this.minor = minor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setAngle(double angle)
/*      */   {
/*  893 */     this.angle = angle;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Point2D[] findRepresentativePoints()
/*      */   {
/*  902 */     Point2D[] bbPoints = findBoundingBoxPoints();
/*  903 */     Point2D[] handles = findHandlePoints();
/*      */     
/*  905 */     Point2D[] representatives = new Point2D[bbPoints.length + handles.length];
/*  906 */     System.arraycopy(bbPoints, 0, representatives, 0, bbPoints.length);
/*  907 */     System.arraycopy(handles, 0, representatives, bbPoints.length, handles.length);
/*      */     
/*  909 */     return representatives;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Point2D[] findHandlePoints()
/*      */   {
/*  917 */     AffineTransform ellipseAT = getTransform();
/*  918 */     Point2D[] handles = new Point2D[4];
/*  919 */     handles[0] = ellipseAT.transform(new Point2D.Double(-(this.major / 2.0D), 0.0D), null);
/*  920 */     handles[1] = ellipseAT.transform(new Point2D.Double(this.major / 2.0D, 0.0D), null);
/*  921 */     handles[2] = ellipseAT.transform(new Point2D.Double(0.0D, -(this.minor / 2.0D)), null);
/*  922 */     handles[3] = ellipseAT.transform(new Point2D.Double(0.0D, this.minor / 2.0D), null);
/*  923 */     return handles;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Point2D[] findBoundingBoxPoints()
/*      */   {
/*  934 */     Point2D[] result = new Point2D.Double[4];
/*  935 */     double a = this.major / 2.0D;
/*  936 */     double b = this.minor / 2.0D;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  941 */     double t = Math.atan(-b * Math.tan(this.angle) / a);
/*  942 */     double offx = a * Math.cos(t) * Math.cos(this.angle) - b * Math.sin(t) * Math.sin(this.angle);
/*  943 */     double offy = b * Math.sin(t) * Math.cos(this.angle) + a * Math.cos(t) * Math.sin(this.angle);
/*  944 */     double x = this.doubleX + offx;
/*  945 */     double y = this.doubleY + offy;
/*  946 */     result[0] = new Point2D.Double(x, y);
/*  947 */     x = this.doubleX - offx;
/*  948 */     y = this.doubleY - offy;
/*  949 */     result[1] = new Point2D.Double(x, y);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  954 */     t = Math.atan(b / (Math.tan(this.angle) * a));
/*  955 */     offx = a * Math.cos(t) * Math.cos(this.angle) - b * Math.sin(t) * Math.sin(this.angle);
/*  956 */     offy = b * Math.sin(t) * Math.cos(this.angle) + a * Math.cos(t) * Math.sin(this.angle);
/*  957 */     x = this.doubleX + offx;
/*  958 */     y = this.doubleY + offy;
/*  959 */     result[2] = new Point2D.Double(x, y);
/*  960 */     x = this.doubleX - offx;
/*  961 */     y = this.doubleY - offy;
/*  962 */     result[3] = new Point2D.Double(x, y);
/*      */     
/*  964 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fitEllipseToPoints(Point2D[] points)
/*      */   {
/*  973 */     fitEllipseToPoints(points, 1.0E-20D);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fitEllipseToPoints(Point2D[] points, double errTolerance)
/*      */   {
/*  985 */     fitEllipseToPoints(points, errTolerance, 100000);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fitEllipseToPoints(Point2D[] points, double errTolerance, int maxCount)
/*      */   {
/* 1000 */     int d = 2;
/*      */     
/* 1002 */     int N = points.length;
/*      */     
/* 1004 */     double[][] P = new double[d][N];
/* 1005 */     for (int i = 0; i < N; i++) {
/* 1006 */       P[0][i] = points[i].getX();
/* 1007 */       P[1][i] = points[i].getY();
/*      */     }
/* 1009 */     DoubleMatrix2D matrixP = new DenseDoubleMatrix2D(P);
/*      */     
/*      */ 
/* 1012 */     double[][] Q = new double[d + 1][N];
/* 1013 */     for (int i = 0; i < N; i++) {
/* 1014 */       Q[0][i] = points[i].getX();
/* 1015 */       Q[1][i] = points[i].getY();
/* 1016 */       Q[2][i] = 1.0D;
/*      */     }
/* 1018 */     DoubleMatrix2D matrixQ = new DenseDoubleMatrix2D(Q);
/*      */     
/*      */ 
/* 1021 */     DoubleMatrix2D matrixQt = Algebra.DEFAULT.transpose(matrixQ);
/*      */     
/*      */ 
/*      */ 
/* 1025 */     int count = 1;
/* 1026 */     double err = 1.0D;
/*      */     
/*      */ 
/* 1029 */     double[] u = new double[N];
/* 1030 */     for (int i = 0; i < N; i++) {
/* 1031 */       u[i] = (1.0D / N);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1037 */     while ((err > errTolerance) && (count < maxCount))
/*      */     {
/* 1039 */       DoubleMatrix2D matrixU =  DoubleFactory2D.dense.diagonal(new DenseDoubleMatrix1D(u));
/*      */
/* 1042 */       DoubleMatrix2D matrixX = Algebra.DEFAULT.mult(matrixQ, Algebra.DEFAULT.mult(matrixU, matrixQt));
/*      */       
/* 1047 */       double[] M = DoubleFactory2D.dense.diagonal(Algebra.DEFAULT.mult(matrixQt, Algebra.DEFAULT.mult(Algebra.DEFAULT.inverse(matrixX), matrixQ))).toArray();
/*      */
                 double maximum = Double.NEGATIVE_INFINITY;
                 int j = -1;

        for (int i = 0; i < M.length; i++) {
            if(M[i]>maximum){
               maximum = M[i];
                j = i;
            }
        }


/*      */ 
/* 1050 */

/*      */       
/*      */ 
/* 1055 */       double stepSize = (maximum - d - 1.0D) / ((d + 1) * (maximum - 1.0D));
/*      */       
/*      */ 
/*      */       double[] newU = Arrays.copyOf(u,u.length);
/* 1059 */       MatrixOp.mult(u, 1.0D - stepSize);
/*      */
/* 1062 */       newU[j] += stepSize;
/*      */       
/*      */        double[] diff = MatrixOp.diff(newU, u);
/* 1066 */       err = MatrixOp.lenght(diff);
/*      */       
/*      */ 
/* 1069 */       count++;
/* 1070 */       u = newU;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1076 */     DoubleMatrix2D matrixU = DoubleFactory2D.dense.diagonal(new DenseDoubleMatrix1D(u));
/*      */     
/*      */ 
/*      */ 
/* 1080 */     DoubleMatrix2D matrixPUPt = Algebra.DEFAULT.mult(matrixP, Algebra.DEFAULT.mult(matrixU, Algebra.DEFAULT.transpose(matrixP)));
/* 1081 */     DoubleMatrix2D matrixPu = Algebra.DEFAULT.mult(matrixP, matrixU);
/* 1082 */     DoubleMatrix2D matrixPut = Algebra.DEFAULT.transpose(matrixPu);
/* 1083 */     DoubleMatrix2D matrixPuPut = Algebra.DEFAULT.mult(matrixPu, matrixPut);

    DoubleDoubleFunction minus = new DoubleDoubleFunction() {
        public double apply(double a, double b) { return a-b; }
    };
                DoubleMatrix2D matrixPUPt_minus_PuPut = matrixPUPt.assign( matrixPuPut, minus);
/* 1085 */     DoubleMatrix2D matrixInv_PUPt_minus_PuPut = Algebra.DEFAULT.inverse(matrixPUPt_minus_PuPut);
/* 1086 */     DoubleMatrix2D matrixA = matrixInv_PUPt_minus_PuPut.assign(new DoubleFunction() {
        @Override
        public double apply(double v) {
            return v /d;
        }
    });
/*      */     
/*      */ 
/* 1089 */     DoubleMatrix2D matrixCenter = matrixPu;
/* 1090 */     this.doubleX = matrixCenter.get(0, 0);
/* 1091 */     this.doubleY = matrixCenter.get(1, 0);
/*      */     
/*      */ 
/* 1094 */     DoubleMatrix2D cov = Algebra.DEFAULT.inverse(matrixA);
/*      */     
/*      */     EigenvalueDecomposition ed = new EigenvalueDecomposition(cov);
/*      */ 
/* 1098 */     double[] lambdas = ed.getRealEigenvalues().toArray();
/* 1099 */     this.minor = (2.0D * Math.sqrt(lambdas[0]));
/* 1100 */     this.major = (2.0D * Math.sqrt(lambdas[1]));
/*      */

/* 1102 */     double[][] eigenVectors = ed.getV().toArray();
/* 1103 */     double[] eigenVector1 = eigenVectors[0];double[] eigenVector2 = null;
/* 1104 */     if (eigenVectors.length == 2)
/*      */     {
/* 1106 */       eigenVector2 = eigenVectors[1];
/* 1107 */       this.angle = Math.atan(eigenVector2[1] / eigenVector2[0]);
/*      */     } else {
/* 1109 */       this.angle = (-Math.atan(eigenVector1[0] / eigenVector1[1]));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\Ellipse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */