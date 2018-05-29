/*     */ package org.nolanlab.CODEX.viewer.viewerclient.gate;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GateSet
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4649L;
/*     */   public static final String TYPE = "GateSet";
/*     */   protected int id;
/*     */   protected String name;
/*     */   protected LinkedHashSet gates;
/*     */   
/*     */   public GateSet(int id)
/*     */   {
/*  87 */     this.id = id;
/*     */     
/*     */ 
/*  90 */     this.name = ("Population " + id);
/*     */     
/*     */ 
/*  93 */     this.gates = new LinkedHashSet();
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
/*     */   public GateSet(int id, String name)
/*     */   {
/* 113 */     this(id);
/*     */     
/*     */ 
/* 116 */     setName(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getID()
/*     */   {
/* 127 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setID(int id)
/*     */   {
/* 139 */     this.id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 150 */     return this.name;
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
/*     */   public String toString()
/*     */   {
/* 167 */     return getName();
/*     */   }
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
/* 179 */     this.name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getType()
/*     */   {
/* 190 */     return "GateSet";
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
/*     */   public boolean equals(Object obj)
/*     */   {
/* 211 */     if (obj == null)
/*     */     {
/*     */ 
/* 214 */       return false;
/*     */     }
/*     */     
/* 217 */     if ((obj instanceof GateSet))
/*     */     {
/*     */ 
/* 220 */       GateSet gateSet = (GateSet)obj;
/*     */       
/*     */ 
/* 223 */       return (getID() == gateSet.getID()) && (getType().equals(gateSet.getType())) && (getName().equals(gateSet.getName())) && (size() == gateSet.size());
/*     */     }
/*     */     
/* 226 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 238 */     return getID();
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
/*     */   public GateSet copy(int id)
/*     */   {
/* 252 */     GateSet copy = new GateSet(id, getName());
/*     */     
/*     */ 
/* 255 */     Gate[] gateArray = toArray();
/*     */     
/*     */ 
/*     */ 
/* 259 */     for (int i = 0; i < gateArray.length; i++) {
/* 260 */       copy.add(gateArray[i]);
/*     */     }
/*     */     
/*     */ 
/* 264 */     return copy;
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
/*     */   public int size()
/*     */   {
/* 287 */     return this.gates.size();
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
/*     */   public boolean isEmpty()
/*     */   {
/* 302 */     return this.gates.isEmpty();
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
/*     */   public boolean contains(Gate gate)
/*     */   {
/* 322 */     if (gate == null)
/*     */     {
/* 324 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 328 */     return this.gates.contains(gate);
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
/*     */   public boolean add(Gate gate)
/*     */   {
/* 342 */     if (gate != null)
/*     */     {
/* 344 */       return this.gates.add(gate);
/*     */     }
/*     */     
/*     */ 
/* 348 */     return false;
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
/*     */   public boolean remove(Gate gate)
/*     */   {
/* 362 */     return this.gates.remove(gate);
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
/*     */   private Set getSet()
/*     */   {
/* 379 */     return this.gates;
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
/*     */   public boolean containsAll(GateSet set)
/*     */   {
/* 402 */     return this.gates.containsAll(set.getSet());
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
/*     */   public boolean containsAll(Collection gates)
/*     */   {
/* 417 */     return this.gates.containsAll(gates);
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
/*     */   public boolean containsOnly(Collection gates)
/*     */   {
/* 432 */     return (size() == gates.size()) && (containsAll(gates));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 441 */     this.gates.clear();
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
/*     */   public Gate[] toArray()
/*     */   {
/* 457 */     Gate[] gateArray = new Gate[this.gates.size()];
/*     */     
/*     */ 
/* 460 */     this.gates.toArray(gateArray);
/*     */     
/*     */ 
/* 463 */     return gateArray;
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
/*     */   public int compareTo(Object obj)
/*     */   {
/* 488 */     if (obj == null)
/*     */     {
/*     */ 
/* 491 */       return 1;
/*     */     }
/*     */     
/* 494 */     if ((obj instanceof GateSet))
/*     */     {
/* 496 */       GateSet gateSet = (GateSet)obj;
/*     */       
/*     */ 
/* 499 */       Integer thisSize = new Integer(size());
/*     */       
/*     */ 
/* 502 */       return thisSize.compareTo(new Integer(gateSet.size()));
/*     */     }
/*     */     
/* 505 */     return -1;
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
/*     */   public Gate[] getGates(String filename)
/*     */   {
/* 526 */     return Gate.filter(toArray(), filename);
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
/*     */   public HashMap getGateMap()
/*     */   {
/* 540 */     HashMap gateMap = new HashMap();
/*     */     
/*     */ 
/* 543 */     Gate[] gateArray = toArray();
/*     */     
/*     */ 
/* 546 */     for (int i = 0; i < gateArray.length; i++)
/*     */     {
/* 548 */       gateMap.put(new Integer(gateArray[i].getID()), gateArray[i]);
/*     */     }
/*     */     
/*     */ 
/* 552 */     return gateMap;
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
/*     */   public HashMap getGateMap(String filename)
/*     */   {
/* 571 */     HashMap gateMap = new HashMap();
/*     */     
/*     */ 
/*     */ 
/* 575 */     Gate[] gateArray = getGates(filename);
/*     */     
/*     */ 
/*     */ 
/* 579 */     for (int i = 0; i < gateArray.length; i++)
/*     */     {
/* 581 */       gateMap.put(new Integer(gateArray[i].getID()), gateArray[i]);
/*     */     }
/*     */     
/*     */ 
/* 585 */     return gateMap;
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
/*     */   public static DefaultMutableTreeNode getPopulationTree(GateSet[] gateSets)
/*     */   {
/* 615 */     DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ungated");
/*     */     
/* 617 */     if ((gateSets == null) || (gateSets.length <= 0))
/*     */     {
/*     */ 
/* 620 */       return root;
/*     */     }
/*     */     
/* 623 */     if (gateSets.length > 1)
/*     */     {
/*     */ 
/*     */ 
/* 627 */       Arrays.sort(gateSets);
/*     */     }
/*     */     
/*     */ 
/* 631 */     DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[gateSets.length];
/*     */     
/*     */ 
/*     */ 
/* 635 */     for (int i = 0; i < gateSets.length; i++) {
/* 636 */       nodes[i] = new DefaultMutableTreeNode(gateSets[i]);
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
/*     */ 
/*     */ 
/*     */ 
/* 650 */     for (int i = gateSets.length - 1; i >= 0; i--)
/*     */     {
/* 652 */       int targetSize = gateSets[i].size() - 1;
/*     */       
/* 654 */       if (targetSize <= 0) {
/*     */         break;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 662 */       boolean foundParentP = false;
/*     */       
/*     */ 
/* 665 */       for (int j = i; j >= 0; j--) {
/* 666 */         if ((gateSets[j].size() == targetSize) && (gateSets[i].containsAll(gateSets[j])))
/*     */         {
/*     */ 
/*     */ 
/* 670 */           nodes[j].add(nodes[i]);
/*     */           
/*     */ 
/* 673 */           foundParentP = true;
/* 674 */         } else { if (gateSets[j].size() < targetSize) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 682 */       if (foundParentP)
/*     */       {
/*     */ 
/*     */ 
/* 686 */         nodes[i] = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 697 */     for (int i = 0; i < gateSets.length; i++) {
/* 698 */       if (nodes[i] != null)
/*     */       {
/*     */ 
/* 701 */         root.add(nodes[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 706 */     return root;
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
/*     */   public void print()
/*     */   {
/* 723 */     System.out.println("GateSet ID: " + getID());
/* 724 */     System.out.println("GateSet Name: " + getName());
/* 725 */     System.out.println("---");
/*     */     
/*     */ 
/* 728 */     Gate[] gateArray = toArray();
/*     */     
/*     */ 
/* 731 */     for (int i = 0; i < gateArray.length; i++)
/*     */     {
/* 733 */       System.out.println("\t" + gateArray[i].getID());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void testStandAlone()
/*     */   {
/* 744 */     GateSet gateSet = new GateSet(1, "Test GateSet");
/*     */     
/* 746 */     String[] filenames = { "Filename1", "Filename2", "Filename3", "Filename4" };
/*     */     
/*     */ 
/* 749 */     Gate g1 = new Rectangle(1, 256, 10, 10, 100, 50, 1, 2);
/* 750 */     g1.setFilename(filenames[3]);
/* 751 */     g1.setStandAlone(true);
/* 752 */     gateSet.add(g1);
/*     */     
/*     */ 
/* 755 */     Gate g2 = new Rectangle(2, 256, 20, 20, 100, 50, 1, 2);
/* 756 */     g2.setFilename(filenames[2]);
/* 757 */     g2.setStandAlone(true);
/* 758 */     gateSet.add(g2);
/*     */     
/*     */ 
/* 761 */     Gate g3 = new Rectangle(3, 256, 30, 30, 100, 50, 1, 2);
/* 762 */     g3.setFilename(filenames[1]);
/* 763 */     g3.setStandAlone(true);
/* 764 */     gateSet.add(g3);
/*     */     
/*     */ 
/* 767 */     Gate g4 = new Rectangle(4, 256, 40, 40, 100, 50, 1, 2);
/* 768 */     g4.setFilename(filenames[0]);
/* 769 */     g4.setStandAlone(true);
/* 770 */     gateSet.add(g4);
/*     */     
/*     */ 
/* 773 */     Gate[] gates = gateSet.toArray();
/*     */     
/* 775 */     System.out.println("All the gates in the gate set");
/* 776 */     System.out.println("---");
/*     */     
/*     */ 
/* 779 */     for (int i = 0; i < gates.length; i++) {
/* 780 */       System.out.println("\tGate ID: " + gates[i].getID());
/* 781 */       System.out.println("\tx: " + gates[i].getX());
/* 782 */       System.out.println("\ty: " + gates[i].getY());
/*     */     }
/*     */     
/*     */ 
/* 786 */     for (int i = 0; i < filenames.length; i++)
/*     */     {
/* 788 */       gates = gateSet.getGates(filenames[i]);
/*     */       
/* 790 */       System.out.println("Gates for " + filenames[i]);
/* 791 */       System.out.println("---");
/*     */       
/*     */ 
/* 794 */       for (int j = 0; j < gates.length; j++) {
/* 795 */         System.out.println("\tGate ID: " + gates[j].getID());
/* 796 */         System.out.println("\tx: " + gates[j].getX());
/* 797 */         System.out.println("\ty: " + gates[j].getY());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              C:\Users\Nikolay\Downloads\gating.jar!\facs\gate\GateSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */