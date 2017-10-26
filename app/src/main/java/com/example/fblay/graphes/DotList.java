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
    public DotList() {
        l = new ArrayList<Dot>();
    }

    public DotList (Context context, AttributeSet attrs){
        int xMax = 1024;
        int xMin = 50;
        int yMin = 50;
        int x;
        int y;
        l = new ArrayList<Dot>();
        for(int i = 0; i < 4; i++){
            x = xMax / (i + 2);
            for (int j = 0; j < i+1; j++){
                Dot d = new Dot(context, attrs);
                d.setX(x * (j+1));
                d.setY(250 * (i + 1));
                d.setText(String.valueOf(l.size()));
                l.add(d);
            }
        }
    }
    public int getSize() {
        return l.size();
    }
    public ArrayList<Dot> getList() {
        return l;
    }
    public void putDot(Dot d) {
        l.add(d);
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

    public Dot getDot(int index){
        return l.get(index);
    }

    public ArcList getArcList(int index) {
        return l.get(index).getArcList();
    }

    public boolean contain(Dot dot){
        for(int i = 0; i < l.size(); i++) {
            if (l.get(i).equals(dot)){
                return true;
            }
        }
        return false;
    }

    public DotList containsDot(Dot dot) {
        DotList dl = new DotList();
        for(int i = 0; i < l.size(); i++) {
            ArcList arcList = l.get(i).getArcList();
            for (int y = 0 ;y < arcList.getSize() - 1; y++) {
                if (!dl.contain(arcList.getArc(y).getFrom())) {
                    dl.putDot(arcList.getArc(y).getFrom());
                }
                if (!dl.contain(arcList.getArc(y).getTo()))
                    dl.putDot(arcList.getArc(y).getTo());
                }
            }
        return dl;
    }
    public void supDot(int index){
        Log.e("sup", "sup");
        l.remove(index);
    }

}
