package com.example.fblay.graphes;

import android.graphics.Path;

import java.util.ArrayList;

/**
 * Created by fblay on 04/10/2017.
 */

public class ArcList {
    ArrayList<Arc> arrayArc ;

    public ArcList() {
        arrayArc = new ArrayList<Arc>();
        arrayArc.add(new Arc());
    }

    public int getSize() {
        return arrayArc.size();
    }

    public Arc getCurrent() {
        return arrayArc.get(arrayArc.size() - 1);
    }
    public Arc getArc(int index) {
        return arrayArc.get(index);
    }

    public Path getIndex(int i) {
        return arrayArc.get(i).getPath();
    }

    public void add(){
        arrayArc.add(new Arc());
    }


}
