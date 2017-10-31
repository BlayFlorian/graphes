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

    public int getMiddle(float x, float y) {
        for (int i = 0; i < arrayArc.size() - 1; i++) {
            float[] position = arrayArc.get(i).getPosition();
            if (position[0] < x + 50 && position[0] > x -50) {
                if (position[1] < y + 50 && position[1] > y - 50) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void deleteArc(int index) {
        arrayArc.remove(index);
    }

}
