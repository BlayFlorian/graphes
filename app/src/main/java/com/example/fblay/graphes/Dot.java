package com.example.fblay.graphes;

/**
 *  @author Florian Blay & Lucile Floc
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Classe Dot, pour les noeuds
 */
class Dot implements Parcelable {
    private float RADIUS = 50;
    private Paint circle;
    private Paint textPaint;
    ArcList arcList;
    private RectF rectF;
    private int x = 0;
    private int y = 0;
    private String text = "0";
    private DrawableGraph dg;

    /**
     * Constructeur de la classe Dot
     */
    public Dot(DrawableGraph dg) {
        this.dg = dg;
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

    protected Dot(Parcel in) {
        RADIUS = in.readFloat();
        rectF = in.readParcelable(RectF.class.getClassLoader());
        x = in.readInt();
        y = in.readInt();
        text = in.readString();
    }

    public void Draw(Canvas canvas) {
        String t = String.valueOf(text);
        int size = t.length();
        rectF.set(x + RADIUS + (size - 1) * 10, y + RADIUS, x - RADIUS - (size - 1) * 10, y-RADIUS);
        canvas.drawOval(rectF, circle);
        canvas.drawText(t, x, y, textPaint);
        dg.invalidate();
    }

    public void setX (int nx) {
        this.x = nx;
    }

    public void setY (int ny) {
        this.y = ny;
    }

    /**
     * Change la couleur du noeud
     * @param c: index du noeud
     */
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
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public float getRadius() { return RADIUS; }

    public ArcList getArcList(){
        return arcList;
    }

    public void setRadius(int i) {
        RADIUS = i;
    }

    /**
     * Change le texte du noeud
     * @param t: le nouveau texte
     */
    public void setTextPoint(String t) {
        this.text = t;
    }

    public String toString(){
        return this.text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(RADIUS);
        dest.writeParcelable(rectF, flags);
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeString(text);
    }

    public static final Parcelable.Creator<Dot> CREATOR = new Parcelable.Creator<Dot>()
    {
        @Override
        public Dot createFromParcel(Parcel source)
        {
            return new Dot(source);
        }

        @Override
        public Dot[] newArray(int size)
        {
            return new Dot[size];
        }
    };
}
