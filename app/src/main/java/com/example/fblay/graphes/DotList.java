package com.example.fblay.graphes;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian Blay & Lucile Floc
 */

/**
 * Classe DotList, liste des noeuds
 */
public class DotList implements Parcelable {
    ArrayList<Dot> l;
    ArrayList<Dot> restart;
    Context context;
    AttributeSet attrs;
    DrawableGraph dg;

    public DotList() {
        l = new ArrayList<Dot>();
    }

    /**
     * Constructeur de la classe DotList
     */
    public DotList (DrawableGraph dg){
        this.context = dg.getContext();
        this.attrs = dg.getAttrs();
        this.dg = dg;
        init();
    }

    protected DotList(Parcel in) {
        l = in.createTypedArrayList(Dot.CREATOR);
        restart = in.createTypedArrayList(Dot.CREATOR);
    }

    public static final Creator<DotList> CREATOR = new Creator<DotList>() {
        @Override
        public DotList createFromParcel(Parcel in) {
            return new DotList(in);
        }

        @Override
        public DotList[] newArray(int size) {
            return new DotList[size];
        }
    };

    private void init() {
        int xMax = 1024;
        l = new ArrayList<Dot>();
        int y = 0;
        for (int i = 0; i < 9; i++) {
            Dot d = new Dot(dg);
            d.setX((xMax / 3) * (i % 3) + 200);
            if(i%3 == 0) { y ++; }
            d.setY((350 * y) + 50);
            d.setTextPoint(String.valueOf(l.size()));
            l.add(d);
        }
    }

    /**
     * @return la taille du noeud
     */
    public int getSize() {
        return l.size();
    }

    /**
     * @return la liste de noeuds
     */
    public ArrayList<Dot> getDotList() {
        return l;
    }

    public ArcList getArcList(int index) {
        return l.get(index).getArcList();
    }

    public Dot getDot(int index){
        return l.get(index);
    }

    /**
     * Ajoute le noeud dans la liste
     * @param d
     */
    public void putDot(Dot d) {
        l.add(d);
    }

    /**
     * @param dot
     * @return true si le noeud est dans la liste, false sinon
     */
    public boolean contain(Dot dot){
        for(int i = 0; i < l.size(); i++) {
            if (l.get(i).equals(dot)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param dot le noeud
     * @return liste de noeud connecté a ce noeud par rapport aux arcs
     */
    public DotList containsDot(Dot dot) {
        DotList dl = new DotList();
        for(int i = 0; i < l.size(); i++) {
            ArcList arcList = l.get(i).getArcList();
            for (int y = 0 ;y < arcList.getSize() - 1; y++) {
                if(!dl.contain(arcList.getArc(y).getTo())) {
                    dl.putDot(arcList.getArc(y).getTo());
                }
                if(!dl.contain(arcList.getArc(y).getFrom())) {
                    dl.putDot(arcList.getArc(y).getFrom());
                }
            }
        }
        return dl;
    }

    /**
     * Cherche le noeud avec les coordonnées
     * @param x abscisse
     * @param y ordonnée
     * @return index du noeud
     */
    public int searchDot(float x, float y) {
        for(int i = 0; i < l.size(); i++ ) {
            float maxX = l.get(i).getX() + l.get(i).getRadius();
            float minX = l.get(i).getX() - l.get(i).getRadius();
            float maxY = l.get(i).getY() + l.get(i).getRadius();
            float minY = l.get(i).getY() - l.get(i).getRadius();
            if((x < maxX && x > minX) && (y < maxY && y > minY)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Cherche le milieu de l'arc
     * @param x abscisse
     * @param y ordonnée
     * @return tableau des milieux
     */
    public int[] searchMiddle(float x, float y) {
        for (int i = 0; i < l.size(); i++) {
            int res = l.get(i).getArcList().getMiddle(x,y);
            if(res != -1) {
                return new int[] {i,res};
            }
        }
        return new int[]{-1,-1};
    }

    /**
     * Supprime un noeud
     * @param index index du noeud
     */
    public void supDot(int index){
        DotList d = containsDot(l.get(index));
        for(int i = 0; i < d.getSize(); i++) {
            ArcList arcList = d.getArcList(i);
            for(int y = 0; y < arcList.getSize() -1; y ++) {
                if (arcList.getArc(y).getTo().equals(l.get(index))){
                    arcList.deleteArc(y);
                }
            }
        }
        l.remove(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(l);
        dest.writeTypedList(restart);
    }

}
