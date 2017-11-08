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
    /**
     * to: point d'arrivé de l'arc
     * from: point de départ de l'arc
     */
    Dot from;
    Dot to;
    /**
     * path: chemin
     */
    Path path;
    /**
     * mPaint: dessin de l'arc
     * rectPaint: dessin du point
     */
    Paint mPaint;
    Paint rectPaint;
    /**
     * rectF: point
     * position: coordonnées du point
     */
    RectF rectF = null;
    float[] position;

    /**
     * Propriétés privées étiquette
     */
    private String text = "0";

    /**
     * Constructeur de la classe Arc
     */
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

    /**
     * Création de l'arc
     * @param d1 : point de départ
     * @param d2 : point d'arrivée
     */
    public void setArc(Dot d1, Dot d2) {
        this.from = d1;
        this.to = d2;
        move();
    }

    /**
     * Trouve le milieu de l'arc
     * @return les coordonnées du milieu de l'arc
     */
    private float[] getMiddleArc() {
        PathMeasure pm = new PathMeasure(path, false);
        float[] aCoordinates = new float[2];
        pm.getPosTan(pm.getLength() / 2 , aCoordinates, null);
        return aCoordinates;
    }

    /**
     * Trouve les coordonnées de la bordure d'un point
     * @param x: le point
     * @return les coordonnées de la bordure du point
     */
    private float[] getBorder(int x) {
        PathMeasure pm = new PathMeasure(path, false);
        float[] aCoordinates = new float[2];
        //float size = 50 + to.
        pm.getPosTan(pm.getLength() - x , aCoordinates, null);
        return aCoordinates;
    }

    /**
     *
     * @param x
     * @param y
     */
    private void setRectF(float x, float y) {
        this.rectPaint = new Paint();
        this.rectPaint.setColor(Color.WHITE);
        this.rectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.rectPaint.setAntiAlias(true);
        this.rectF = new RectF(x + 10, y + 10, x-10, y -10);
    }

    /**
     * Dessine le point
     * @param canvas
     */
    public void drawRect(Canvas canvas){
        canvas.drawRoundRect(this.rectF, 6, 6, this.rectPaint);
    }

    /**
     * Dessine l'arc
     * @param fromX: coordonnée de l'axe des abscisses du point de départ
     * @param fromY: coordonnée de l'axe des ordonnées du point de départ
     * @param toX: coordonnée de l'axe des abscisses du point d'arrivée
     * @param toY: coordonnée de l'axe des ordonnées du point d'arrivée
     */
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
    }

    /**
     * Dessin
     * @param d1
     * @param endX
     * @param endY
     */
    public void draw(Dot d1, float endX, float endY) {
        //String t = String.valueOf(text);
        //int size = t.length();

        path.reset();
        path.moveTo(d1.getX(), d1.getY());
        path.lineTo(endX, endY);
        drawArrow(d1.getX(), d1.getY(), endX, endY);

    }

    /**
     *
     */
    public void move(){
        path.reset();
        path.moveTo(from.getX(), from.getY());
        path.quadTo(from.getX(), from.getY(), to.getX() , to.getY());
        position = getMiddleArc();
        path.moveTo(position[0], position[1]);
        setRectF(position[0], position[1]);
        float [] toBorder = getBorder(50);
        float [] fromBorder = getBorder(300);
        drawArrow(fromBorder[0], fromBorder[1], toBorder[0], toBorder[1]);
    }

    /**
     * Dessin de la courbure de l'arc par rapport au milieu
     * @param x: point d'abscisse du milieu de l'arc
     * @param y: point d'ordonnée du milieu de l'arc
     */
    public void moveMiddle(float x, float y) {
        path.reset();
        path.moveTo(from.getX(), from.getY());
        position = getMiddleArc();
        path.quadTo(x, y, to.getX(), to.getY());
        position = getMiddleArc();
        path.moveTo(position[0], position[1]);
        setRectF(position[0], position[1]);
        float [] toBorder = getBorder(50);
        float [] fromBorder = getBorder(200);
        drawArrow(fromBorder[0], fromBorder[1], toBorder[0], toBorder[1]);
    }

    /**
     *
     * @return
     */
    public Path getPath() {
        return this.path;
    }

    /**
     * Fin de la flêche
     * @param d: point de la fin de l'arc
     */
    public void setFrom(Dot d) {
        this.from = d;
    }

    /**
     * Debut de la flêche
     * @param d: point du début de l'arc
     */
    public void setTo(Dot d) {
        this.to = d;
    }

    /**
     * @return le point de départ de l'arc
     */
    public Dot getFrom() {
        return from;
    }

    /**
     * @return le point d'arrivé de l'arc
     */
    public Dot getTo() {
        return to;
    }

    /**
     * Recupère les coordonnées de la position
     * @return les coordonnées de la position
     */
    public float[] getPosition () {
        return position;
    }

    //----------------Paint--------------//

    /**
     * @return mPaint
     */
    public Paint getPaint() {
        return mPaint;
    }

    /**
     * Change la couleur d'un arc
     * @param i: arc
     */
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

    /**
     * Change l'epaisseur de l'arc
     * @param i: arc
     */
    public void setWidthArc(int i) {
        mPaint.setStrokeWidth(i);
    }

    public void setText(String t){

    }
    public void setWidthText(int i){

    }
}
