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
    Paint mPaint;
    Paint rectPaint;

    RectF rectF = null;
    float[] position;

    public Arc () {
        this.from = null;
        this.to = null;
        path = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
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
    }
    public void draw(Dot d1, float endX, float endY) {
        path.reset();
        path.moveTo(d1.getX(), d1.getY());
        path.lineTo(endX, endY);
        drawArrow(endX, endY);
    }

    public void move(){
        path.reset();
        path.moveTo(from.getX(), from.getY());
        path.quadTo(from.getX(), to.getY(), to.getX() , to.getY());
        position = getMiddleArc();
        path.moveTo(position[0], position[1]);
        setRectF(position[0], position[1]);
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

    //----------------Paint--------------//
    public Paint getPaint() {
        return mPaint;
    }

    public void setColor(int i) {
        switch (i) {
            case 0: mPaint.setColor(Color.RED);
                break;
            case 1: mPaint.setColor(Color.GREEN);
                break;
            case 2: mPaint.setColor(Color.BLUE);
                break;
            case 3: mPaint.setColor(Color.YELLOW);
                break;
            case 4: mPaint.setColor(Color.CYAN);
                break;
            case 5: mPaint.setColor(Color.MAGENTA);
                break;
            default: mPaint.setColor(Color.BLACK);
                break;
        }
    }

    public void setWidthArc(int i) {
        mPaint.setStrokeWidth(i);
    }

    public void setText(String t){

    }
    public void setWidthText(int i){

    }
}
