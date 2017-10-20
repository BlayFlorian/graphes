package com.example.fblay.graphes;

import android.graphics.Canvas;
import android.graphics.Color;
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
    Paint rectPaint;

    RectF rectF = null;
    float[] position;

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
        move();

    }

    private float[] getMiddleArc() {
        PathMeasure pm = new PathMeasure(path, false);
        float[] aCoordinates = new float[2];
        pm.getPosTan(pm.getLength() / 2 , aCoordinates, null);
        return aCoordinates;
    }

    private void setRectF(float x, float y) {
        this.rectPaint = new Paint();
        this.rectPaint.setColor(Color.WHITE);
        this.rectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.rectPaint.setAntiAlias(true);
        this.rectF = new RectF(x + 10, y + 10, x-10, y -10);
    }
    public void drawRect(Canvas canvas){
        canvas.drawRoundRect(this.rectF, 6, 6, this.rectPaint);
    }
    private void drawArrow(float x, float y) {
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(x - 40f, y - 40f);
        path.lineTo(x - 70f, y - 40f);
        path.lineTo(x - 40f, y - 70f);
        path.lineTo(x - 40f, y - 40f);
        path.close();
        Matrix mMatrix = new Matrix();
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        mMatrix.postRotate(90, bounds.centerX(), bounds.centerY());
    }
    public void draw(Dot d1, float endX, float endY) {
        path.reset();
        path.moveTo(d1.getX(), d1.getY());
        path.lineTo(endX, endY);
    }

    public void move(){
        path.reset();
        if(from.equals(to)) {
            path.moveTo(from.getX(), from.getY());
            path.addCircle(from.getX() + 80, from.getY(), 50, Path.Direction.CCW);
            path.close();
        } else {
            path.reset();
            path.moveTo(from.getX(), from.getY());
            path.quadTo(from.getX(), to.getY(), to.getX() , to.getY());
            position = getMiddleArc();
            path.moveTo(position[0], position[1]);
            setRectF(position[0], position[1]);
        }
    }

    public void moveMiddle(float x, float y) {
        path.reset();
        path.moveTo(from.getX(), from.getY());
        path.quadTo(x, y, to.getX(), to.getY());
        position = getMiddleArc();
        path.moveTo(position[0], position[1]);
        setRectF(position[0], position[1]);
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

    public float[] getPosition () {
        return position;
    }


}
