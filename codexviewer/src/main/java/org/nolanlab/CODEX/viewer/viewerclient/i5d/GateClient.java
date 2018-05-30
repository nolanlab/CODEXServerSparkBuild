package org.nolanlab.CODEX.viewer.viewerclient.i5d;

import org.nolanlab.CODEX.viewer.viewerclient.gate.Gate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GateClient {

    List<Gate> gates = new ArrayList<Gate>();

    public void addGate(Gate g){
        gates.add(g);
    }

    public void removeGate(Gate g){
        gates.remove(g);
    }
    public void showGate(Gate g){

    }

    String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public void selectGate(Gate g){

    }

    JPopupMenu popup;

    public JPopupMenu getPopupMenu() {
        return popup;
    }

    public  Gate[] getVisibleGates(){
        return null;
    }

    public  Gate[] getVisibleGates(int ch1, int ch2){
        return null;
    }

    public  Gate[] getSelectedGates(int ch1, int ch2){
        return null;
    }


    public void setDirtyGate(){

    }

    public void unselect(){

    }

    public void save(){

    }

    public void cut(){

    }
    public void copy(){

    }

    public void paste(){

    }

    public int getNextGateID(){
        return 1;
    }

    public int getAxisBins(){
        return 1;
    }

    public int getSelectedCount(){
        return 1;
    }

    public boolean isSelected(Gate g) {
        return true;
    }
}
