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
 *  @author Florian Blay & Lucile Floc
 */

public class Arc {
    /**
     * to: point d'arrivé de l'arc
     * from: point de départ de l'arc
     */
    Dot from;
    Dot to;
    float[] millieu = new float[]{0f,0f};
    /**
     * path: chemin
     */
    Path path;
    /**
     * mPaint: dessin de l'arc
     * rectPaint: dessin du point
     */
    private Paint mPaint;
    private Paint rectPaint;
    private Paint textPaint;
    /**
     * rectF: point
     * position: coordonnées du point
     */
    RectF rectF = null;

    /**
     * Propriétés privées étiquette
     */
    private String text;
    private int size = 15;

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
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Création de l'arc
     * @param d1 : point de départ
     * @param d2 : point d'arrivée
     */
    public void setArc(Dot d1, Dot d2, String t) {
        this.from = d1;
        this.to = d2;
        this.text =t;
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
    private void setRectFMiddle(float x, float y) {
        this.rectPaint = new Paint();
        this.rectPaint.setColor(Color.WHITE);
        this.rectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.rectPaint.setAntiAlias(true);
        this.rectF = new RectF(x + size * text.length() , y + 10 * text.length(), x-size * text.length(), y -10 * text.length());

    }

    /**
     * Dessine le point
     * @param canvas
     */
    public void drawRect(Canvas canvas){
        millieu = getMiddleArc();
        canvas.drawRoundRect(this.rectF, 90, 90, this.rectPaint);
        canvas.drawText(text, millieu[0], millieu[1], textPaint);
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
        float frac = (float) 0.5;
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
        path.reset();
        path.moveTo(d1.getX(), d1.getY());
        path.lineTo(endX, endY);
        millieu = getMiddleArc();
    }

    /**
     *
     */
    public void move(){
        path.reset();
        if (from != null && to.equals(from)) {
            path.reset();
            path.moveTo(0,0);
            path.moveTo(from.getX(),from.getY());
            path.cubicTo(from.getX() - 250,from.getY() - 250,
                    from.getX() + 250, from.getY() - 250,
                    from.getX() , from.getY());
        } else {
            path.moveTo(from.getX(), from.getY());
            path.lineTo(to.getX(), to.getY());
            millieu = getMiddleArc();
        }
        setRectFMiddle(millieu[0], millieu[1]);
        float [] toBorder = getBorder(50);
        float [] fromBorder = getBorder(100);
        drawArrow(fromBorder[0], fromBorder[1], toBorder[0], toBorder[1]);
    }

    /**
     * Dessin de la courbure de l'arc par rapport au milieu
     * @param x: point d'abscisse du milieu de l'arc
     * @param y: point d'ordonnée du milieu de l'arc
     */
    public void moveMiddle(float x, float y) {
        millieu = getMiddleArc();
        path.reset();
        path.moveTo(from.getX(), from.getY());
        path.lineTo(x, y);
        float[] before = getBorder(200);
        path.reset();
        path.moveTo(to.getX(), to.getY());
        path.lineTo(x,y);
        float[] before1 = getBorder(200);
        path.reset();
        path.moveTo(from.getX(), from.getY());
        path.lineTo(before[0], before[1]);
        path.quadTo(x ,y, before1[0], before1[1]);
        path.lineTo(to.getX(), to.getY());
        millieu = getMiddleArc();
        setRectFMiddle(millieu[0], millieu[1]);
        float [] toBorder = getBorder(50);
        float [] fromBorder = getBorder(100);
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
        return millieu;
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

    /**
     * Texte de l'étiquette de l'arc
     * @param t: le texte
     */
    public void setTextArc(String t){
        this.text = t;
    }
    public void setWidthTextArc(int i){
        this.size = i;
    }
}
