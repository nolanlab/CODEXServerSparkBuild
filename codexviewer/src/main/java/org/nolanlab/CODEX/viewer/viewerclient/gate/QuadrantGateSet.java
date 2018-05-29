/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.util.LinkedHashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuadrantGateSet
/*     */   extends SpecialGateSet
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "QuadrantGateSet";
/*     */   protected static final String UR_NAME = " UR";
/*     */   protected static final String UL_NAME = " UL";
/*     */   protected static final String LL_NAME = " LL";
/*     */   protected static final String LR_NAME = " LR";
/*     */   protected static final int NUM_GATES = 4;
/*     */   protected static final int OFFSET = 20;
/*     */   protected Quadrant quadrant;
/*     */   
/*     */   public QuadrantGateSet(int id, Quadrant quadrant, int urID, int ulID, int llID, int lrID)
/*     */   {
/* 112 */     super(id);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 119 */     this.quadrant = quadrant;
/*     */     
/* 121 */     if (this.quadrant == null)
/*     */     {
/* 123 */       return;
/*     */     }
/*     */     
/*     */ 
/* 127 */     this.quadrant.setGateSet(this);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 134 */     int xChannel = this.quadrant.getXChannel();
/* 135 */     int yChannel = this.quadrant.getYChannel();
/*     */     
/*     */ 
/* 138 */     int compensationID = this.quadrant.getCompensationID();
/*     */     
/*     */ 
/* 141 */     int xBins = this.quadrant.getXBins();
/*     */     
/*     */ 
/* 144 */     int yBins = this.quadrant.getYBins();
/*     */     
/*     */ 
/* 147 */     int xSize = this.quadrant.getXSize();
/*     */     
/*     */ 
/* 150 */     int ySize = this.quadrant.getYSize();
/*     */     
/*     */ 
/* 153 */     int x = this.quadrant.getX();
/* 154 */     int y = this.quadrant.getY();
/* 155 */     int width = this.quadrant.getWidth();
/* 156 */     int height = this.quadrant.getHeight();
/*     */     
/*     */ 
/* 159 */     Quad ur = new Quad(urID, x, y, width - x, height - y, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/* 160 */     ur.setLabelX(width - 20);
/* 161 */     ur.setLabelY(height - 20);
/*     */     
/*     */ 
/* 164 */     Quad ul = new Quad(ulID, 0, y, x, height - y, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/* 165 */     ul.setLabelX(20);
/* 166 */     ul.setLabelY(height - 20);
/*     */     
/*     */ 
/* 169 */     Quad ll = new Quad(llID, 0, 0, x, y, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/* 170 */     ll.setLabelX(20);
/* 171 */     ll.setLabelY(20);
/*     */     
/*     */ 
/* 174 */     Quad lr = new Quad(lrID, x, 0, width - x, y, xChannel, yChannel, compensationID, xBins, yBins, xSize, ySize);
/* 175 */     lr.setLabelX(width - 20);
/* 176 */     lr.setLabelY(20);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 186 */     this.gates.add(ur);
/*     */     
/*     */ 
/* 189 */     this.gates.add(ul);
/*     */     
/*     */ 
/* 192 */     this.gates.add(ll);
/*     */     
/*     */ 
/* 195 */     this.gates.add(lr);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 200 */     update();
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
/*     */   protected QuadrantGateSet(int id, Quadrant quadrant, Quad ur, Quad ul, Quad ll, Quad lr)
/*     */   {
/* 231 */     super(id);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 238 */     this.quadrant = quadrant;
/*     */     
/* 240 */     if (this.quadrant != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 245 */       this.quadrant.setGateSet(this);
/*     */       
/*     */ 
/* 248 */       super.setName(this.quadrant.getName());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 259 */     this.gates.add(ur);
/*     */     
/*     */ 
/* 262 */     this.gates.add(ul);
/*     */     
/*     */ 
/* 265 */     this.gates.add(ll);
/*     */     
/*     */ 
/* 268 */     this.gates.add(lr);
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
/* 280 */     return "QuadrantGateSet";
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
/*     */   public Quadrant getQuadrantGate()
/*     */   {
/* 293 */     return this.quadrant;
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
/*     */   public void setName(String name)
/*     */   {
/* 306 */     super.setName(name);
/*     */     
/* 308 */     if (this.quadrant != null)
/*     */     {
/*     */ 
/* 311 */       this.quadrant.setName(name);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void update()
/*     */   {
/* 323 */     if (this.quadrant == null)
/*     */     {
/* 325 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 333 */     String name = this.quadrant.getName();
/*     */     
/*     */ 
/* 336 */     super.setName(name);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 343 */     Gate[] gatesArray = toArray();
/*     */     
/* 345 */     if (gatesArray.length != 4)
/*     */     {
/*     */ 
/* 348 */       return;
/*     */     }
/*     */     
/*     */ 
/* 352 */     int compensationID = this.quadrant.getCompensationID();
/*     */     
/*     */ 
/* 355 */     int xBins = this.quadrant.getXBins();
/*     */     
/*     */ 
/* 358 */     int yBins = this.quadrant.getYBins();
/*     */     
/*     */ 
/* 361 */     int xSize = this.quadrant.getXSize();
/*     */     
/*     */ 
/* 364 */     int ySize = this.quadrant.getYSize();
/*     */     
/*     */ 
/*     */ 
/* 368 */     int xScale = this.quadrant.getXScaleFlag();
/*     */     
/*     */ 
/*     */ 
/* 372 */     int yScale = this.quadrant.getYScaleFlag();
/*     */     
/*     */ 
/*     */ 
/* 376 */     String xScaleArgument = this.quadrant.getXScaleArgumentString();
/*     */     
/*     */ 
/*     */ 
/* 380 */     String yScaleArgument = this.quadrant.getYScaleArgumentString();
/*     */     
/*     */ 
/* 383 */     double xMin = this.quadrant.getXMinimum();
/*     */     
/*     */ 
/* 386 */     double xMax = this.quadrant.getXMaximum();
/*     */     
/*     */ 
/* 389 */     double yMin = this.quadrant.getYMinimum();
/*     */     
/*     */ 
/* 392 */     double yMax = this.quadrant.getYMaximum();
/*     */     
/*     */ 
/* 395 */     int x = this.quadrant.getX();
/* 396 */     int y = this.quadrant.getY();
/* 397 */     int width = this.quadrant.getWidth();
/* 398 */     int height = this.quadrant.getHeight();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 403 */     if ((gatesArray[0] instanceof Quad))
/*     */     {
/*     */ 
/* 406 */       Quad quad = (Quad)gatesArray[0];
/*     */       
/*     */ 
/* 409 */       quad.setCompensationID(compensationID);
/* 410 */       quad.setXBins(xBins);
/* 411 */       quad.setYBins(yBins);
/* 412 */       quad.setXSize(xSize);
/* 413 */       quad.setYSize(ySize);
/* 414 */       quad.setXScaleFlag(xScale);
/* 415 */       quad.setYScaleFlag(yScale);
/* 416 */       quad.setXScaleArgumentString(xScaleArgument);
/* 417 */       quad.setYScaleArgumentString(yScaleArgument);
/* 418 */       quad.setXMinimum(xMin);
/* 419 */       quad.setXMaximum(xMax);
/* 420 */       quad.setYMinimum(yMin);
/* 421 */       quad.setYMaximum(yMax);
/* 422 */       quad.setX(x);
/* 423 */       quad.setY(y);
/* 424 */       quad.setWidth(width - x);
/* 425 */       quad.setHeight(height - y);
/* 426 */       quad.setName(name + " UR");
/*     */     }
/*     */     
/*     */ 
/* 430 */     if ((gatesArray[1] instanceof Quad))
/*     */     {
/*     */ 
/* 433 */       Quad quad = (Quad)gatesArray[1];
/*     */       
/*     */ 
/* 436 */       quad.setCompensationID(compensationID);
/* 437 */       quad.setXBins(xBins);
/* 438 */       quad.setYBins(yBins);
/* 439 */       quad.setXSize(xSize);
/* 440 */       quad.setYSize(ySize);
/* 441 */       quad.setXScaleFlag(xScale);
/* 442 */       quad.setYScaleFlag(yScale);
/* 443 */       quad.setXScaleArgumentString(xScaleArgument);
/* 444 */       quad.setYScaleArgumentString(yScaleArgument);
/* 445 */       quad.setXMinimum(xMin);
/* 446 */       quad.setXMaximum(xMax);
/* 447 */       quad.setYMinimum(yMin);
/* 448 */       quad.setYMaximum(yMax);
/* 449 */       quad.setX(0);
/* 450 */       quad.setY(y);
/* 451 */       quad.setWidth(x);
/* 452 */       quad.setHeight(height - y);
/* 453 */       quad.setName(name + " UL");
/*     */     }
/*     */     
/*     */ 
/* 457 */     if ((gatesArray[2] instanceof Quad))
/*     */     {
/*     */ 
/* 460 */       Quad quad = (Quad)gatesArray[2];
/*     */       
/*     */ 
/* 463 */       quad.setCompensationID(compensationID);
/* 464 */       quad.setXBins(xBins);
/* 465 */       quad.setYBins(yBins);
/* 466 */       quad.setXSize(xSize);
/* 467 */       quad.setYSize(ySize);
/* 468 */       quad.setXScaleFlag(xScale);
/* 469 */       quad.setYScaleFlag(yScale);
/* 470 */       quad.setXScaleArgumentString(xScaleArgument);
/* 471 */       quad.setYScaleArgumentString(yScaleArgument);
/* 472 */       quad.setXMinimum(xMin);
/* 473 */       quad.setXMaximum(xMax);
/* 474 */       quad.setYMinimum(yMin);
/* 475 */       quad.setYMaximum(yMax);
/* 476 */       quad.setX(0);
/* 477 */       quad.setY(0);
/* 478 */       quad.setWidth(x);
/* 479 */       quad.setHeight(y);
/* 480 */       quad.setName(name + " LL");
/*     */     }
/*     */     
/*     */ 
/* 484 */     if ((gatesArray[3] instanceof Quad))
/*     */     {
/*     */ 
/* 487 */       Quad quad = (Quad)gatesArray[3];
/*     */       
/*     */ 
/* 490 */       quad.setCompensationID(compensationID);
/* 491 */       quad.setXBins(xBins);
/* 492 */       quad.setYBins(yBins);
/* 493 */       quad.setXSize(xSize);
/* 494 */       quad.setYSize(ySize);
/* 495 */       quad.setXScaleFlag(xScale);
/* 496 */       quad.setYScaleFlag(yScale);
/* 497 */       quad.setXScaleArgumentString(xScaleArgument);
/* 498 */       quad.setYScaleArgumentString(yScaleArgument);
/* 499 */       quad.setXMinimum(xMin);
/* 500 */       quad.setXMaximum(xMax);
/* 501 */       quad.setYMinimum(yMin);
/* 502 */       quad.setYMaximum(yMax);
/* 503 */       quad.setX(x);
/* 504 */       quad.setY(0);
/* 505 */       quad.setWidth(width - x);
/* 506 */       quad.setHeight(y);
/* 507 */       quad.setName(name + " LR");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\QuadrantGateSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */