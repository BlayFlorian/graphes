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
    float[] millieu = new float[]{0f,0f};
    float[] courbure = new float[] {0f, 0f};
    Path path;
    Paint mPaint;
    Paint rectPaint;

    RectF rectF = null;

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
        Log.e("mamène", "mamène");
        PathMeasure pm = new PathMeasure(path, false);
        float[] aCoordinates = new float[2];
        pm.getPosTan(pm.getLength() / 2 , aCoordinates, null);
        millieu[0] = aCoordinates[0];
        millieu[1] = aCoordinates[1];
        Log.e("millieu", ""+millieu[0]);
        return aCoordinates;
    }

    private float[] getBorder(int x) {
        PathMeasure pm = new PathMeasure(path, false);
        float[] aCoordinates = new float[2];
        //float size = 50 + to.
        pm.getPosTan(pm.getLength() - x , aCoordinates, null);
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

    private void drawArrow(float fromX, float fromY, float toX, float toY) {
        float deltaX =   toX-fromX;
        float deltaY =   toY-fromY;
        float frac = (float) 0.1;
        float point_x_1 = fromX + (float) ((1 - frac) * deltaX + frac * deltaY);
        float point_y_1 = fromY+ (float) ((1 - frac) * deltaY - frac * deltaX);
        float point_x_2 = toX;
        float point_y_2 = toY;
        float point_x_3 = fromX + (float) ((1 - frac) * deltaX - frac * deltaY);
        float point_y_3 = fromY+ (float) ((1 - frac) * deltaY + frac * deltaX);
        path.moveTo(point_x_1, point_y_1);
        path.lineTo(point_x_2, point_y_2);
        path.lineTo(point_x_3, point_y_3);
        path.lineTo(point_x_1, point_y_1);
        path.lineTo(point_x_1, point_y_1);
        //
    }
    public void draw(Dot d1, float endX, float endY) {
        path.reset();
        path.moveTo(d1.getX(), d1.getY());
        path.lineTo(endX, endY);
        drawArrow(d1.getX(), d1.getY(), endX, endY);
        getMiddleArc();
    }

    public void move(){
        Log.e("x", ""+ millieu[0]);
        path.reset();
        path.moveTo(from.getX(), from.getY());
        path.quadTo(millieu[0], millieu[1], to.getX() , to.getY());
        //getMiddleArc();
        setRectF(millieu[0], millieu[1]);
        float [] toBorder = getBorder(50);
        float [] fromBorder = getBorder(300);
        drawArrow(fromBorder[0], fromBorder[1], toBorder[0], toBorder[1]);
    }

    public void moveMiddle(float x, float y) {
        getMiddleArc();
        float y1 = (to.getX() - from.getX());
        float x1 = (to.getY() - from.getY());
        x = x - millieu[0] / 2;
        y = y - millieu[1] / 2;
        Log.e("x1", ""+x1);
        Log.e("y1", ""+y1);
        Log.e("x", ""+ x);
        Log.e("y", ""+ y);
        path.reset();
        path.moveTo(from.getX(), from.getY());
        path.rQuadTo(x, y/2, y1, x1);
        getMiddleArc();
        //path.quadTo(x, y, to.getX(), to.getY());
        getMiddleArc();
        //path.moveTo(position[0], position[1]);
        setRectF(millieu[0], millieu[1]);
        float [] toBorder = getBorder(50);
        float [] fromBorder = getBorder(200);
        drawArrow(fromBorder[0], fromBorder[1], toBorder[0], toBorder[1]);
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
        return millieu;
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
