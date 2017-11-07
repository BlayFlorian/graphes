package com.example.fblay.graphes;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fblay on 03/10/2017.
 */

public class DotList {
    ArrayList<Dot> l;
    ArrayList<Dot> restart;
    Context context;
    AttributeSet attrs;

    public DotList() {
        l = new ArrayList<Dot>();
    }

    public DotList (Context context, AttributeSet attrs){
        this.context = context;
        this.attrs = attrs;
        init();
    }

    private void init() {
        int xMax = 1024;
        l = new ArrayList<Dot>();
        int y = 0;
        for (int i = 0; i < 9; i++) {
            Dot d = new Dot(context, attrs);
            d.setX((xMax / 3) * (i % 3) + 200);
            if(i%3 == 0) { y ++; }
            d.setY((350 * y) + 50);
            d.setText(String.valueOf(l.size()));
            l.add(d);
        }
    }
    
    public int getSize() {
        return l.size();
    }

    public ArrayList<Dot> getDotList() {
        return l;
    }

    public ArcList getArcList(int index) {
        return l.get(index).getArcList();
    }

    public Dot getDot(int index){
        return l.get(index);
    }

    public void putDot(Dot d) {
        l.add(d);
    }

    // DotList contient ce Dot
    public boolean contain(Dot dot){
        for(int i = 0; i < l.size(); i++) {
            if (l.get(i).equals(dot)){
                return true;
            }
        }
        return false;
    }

    // list de noeud connecté a ce noeud
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

    public int[] searchMiddle(float x, float y) {
        for (int i = 0; i < l.size(); i++) {
            int res = l.get(i).getArcList().getMiddle(x,y);
            if(res != -1) {
                return new int[] {i,res};
            }
        }
        return new int[]{-1,-1};
    }


    public void supDot(int index){
        DotList d = containsDot(l.get(index));
        for(int i = 0; i < d.getSize(); i++) {
            Log.e("noeud", d.getDot(i).toString());
            ArcList arcList = d.getArcList(i);
            for(int y = 0; y < arcList.getSize() -1; y ++) {
                if (arcList.getArc(y).getTo().equals(l.get(index))){
                    arcList.deleteArc(y);
                }
            }
        }
        l.remove(index);
    }

}
