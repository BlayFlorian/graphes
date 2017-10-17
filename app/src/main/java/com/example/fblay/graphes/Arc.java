package com.example.fblay.graphes;

import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.FloatProperty;
import android.util.Log;

/**
 * Created by fblay on 04/10/2017.
 */

public class Arc {
    Dot from;
    Dot to;
    Path path;

    public Arc () {
        this.from = null;
        this.to = null;
        path = new Path();
    }
    public Arc(Dot d1, Dot d2){
        from = d1;
        to = d2;
        path = new Path();
    }

    public void setArc(Dot d1, Dot d2) {
        this.from = d1;
        this.to = d2;
        if(d1.equals(d2)) {
            path.reset();
            path.moveTo(from.getX(), from.getY());
            path.addCircle(from.getX() + 80, from.getY(), 50, Path.Direction.CCW);
            path.close();
        } else {
            path.reset();
            path.moveTo(d1.getX(), d1.getY());
            //path.rQuadTo(d1.getX(), d1.getY(), d2.getX(), d2.getY());
            path.quadTo(d2.getX(), d1.getY(), d2.getX(), d2.getY());
            PathMeasure pm = new PathMeasure(path, true);
            Float f = pm.getLength();
            drawArrow(d2.getX(), d2.getY());
        }

    }

    private void drawArrow(float x, float y) {
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(x - 40f, y - 40f);
        path.lineTo(x - 60f, y - 40f);
        path.lineTo(x - 40f, y - 60f);
        path.lineTo(x - 40f, y - 40f);
        path.close();
    }
    public void draw(Dot d1, float endX, float endY) {
        path.reset();
        path.moveTo(d1.getX(), d1.getY());
        path.lineTo(endX, endY);
    }

    public void move(){
        if(from.equals(to)) {
            path.reset();
            path.moveTo(from.getX(), from.getY());
            path.addCircle(from.getX() + 80, from.getY(), 50, Path.Direction.CCW);
            path.close();
        } else {
            path.reset();
            path.moveTo(from.getX(), from.getY());
            path.quadTo(from.getX(), to.getY(), to.getX() , to.getY());
        }
    }

    public Path getPath() {
        return this.path;
    }

    public void setFrom(Dot d) {
        this.from = d;
    }

    public void setTo(Dot d) {
        this.to = d;
    }

    public Dot getFrom() {
        return from;
    }

    public Dot getTo() {
        return to;
    }


}
