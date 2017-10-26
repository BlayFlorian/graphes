package com.example.fblay.graphes;

/**
 * Created by fblay on 30/09/2017.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

class Dot extends View {
    private float RADIUS = 50;
    private Paint circle;
    private Paint textPaint;

    ArcList arcList;

    private RectF rectF;

    private int x = 0;
    private int y = 0;
    private String text = "0";

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
        circle = new Paint();
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        rectF = new RectF();
        circle.setColor(Color.BLACK);
        circle.setAntiAlias(true);
        arcList = new ArcList();
    }

    public void Draw(Canvas canvas) {
        String t = String.valueOf(text);
        int size = t.length();
        rectF.set(x + RADIUS + (size - 1) * 10, y + RADIUS, x - RADIUS - (size - 1) * 10, y-RADIUS);
        canvas.drawOval(rectF, circle);
        canvas.drawText(t, x, y, textPaint);
    }

    public void setX (int nx) {
        this.x = nx;
        invalidate();
    }

    public void setY (int ny) {
        this.y = ny;
        invalidate();
    }

    public void setColor(int c) {
        switch (c) {
            case 0: circle.setColor(Color.RED);
                break;
            case 1: circle.setColor(Color.GREEN);
                break;
            case 2: circle.setColor(Color.BLUE);
                break;
            case 3: circle.setColor(Color.YELLOW);
                break;
            case 4: circle.setColor(Color.CYAN);
                break;
            case 5: circle.setColor(Color.MAGENTA);
                break;
            default: circle.setColor(Color.BLACK);
                break;
        }
        invalidate();
    }

    public void setRadius(int i) {
        RADIUS = i;
        invalidate();
    }
    public void setText(String t) {
        this.text = t;
        invalidate();
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public float getRadius() { return RADIUS; }


    public String getText() {
        return this.text;
    }


    public ArcList getArcList(){
        return arcList;
    }


}
