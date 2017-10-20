package com.example.fblay.graphes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class DrawableGraph extends View implements View.OnLongClickListener {
	Context context;
	private Paint backgroundPaint;
	private static final float TOLERANCE = 5;
    ArrayList<Dot> l;
    DotList dotList;
	AttributeSet attrs;

    int longClick;

	private Paint mPaint;

    Event e;

	public DrawableGraph(Context c, AttributeSet attrs) {
		super(c, attrs);
		context = c;
		this.attrs = attrs;
		//PM
        setLongClickable(true);
        setOnLongClickListener(this);
		//Nouveau list de Node
        dotList = new DotList(context, attrs);
		//Nouvel evenement
        e = new Event(dotList, context, attrs);

		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.BLUE);
		// and we set a new DrawGraph with the desired attributes

	}

    // override onDraw
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//taille canvas
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		canvas.drawRect(0, 0, width, height, backgroundPaint);
		// find node
		l = dotList.getList();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeWidth(4f);
        for(int i = 0; i < l.size(); i++) {
            l.get(i).Draw(canvas);
            ArcList arcL = l.get(i).getArcList();
            for(int y = 0; y < arcL.getSize(); y++) {
                if(arcL.getIndex(y) != null) {
                    canvas.drawPath(arcL.getIndex(y), mPaint);
					if(arcL.getArc(y).rectF != null) {
						arcL.getArc(y).drawRect(canvas);
					}
                }
            }
        }
		// draw the mPath with the mPaint on the canvas when onDraw
	}

    @Override
	public boolean onTouchEvent(MotionEvent event) {
		e.eventX = event.getX();
		e.eventY = event.getY();
		switch (event.getAction()) {
			//Quand tu pose ton doigt
		case MotionEvent.ACTION_DOWN:
			e.startTouch();
			invalidate();
			break;
		//Des que tu bouge ton doigt
		case MotionEvent.ACTION_MOVE:
		    if(longClick == 1) {
                e.moveNode();
            } else if (longClick == 2){
				e.moveMiddle();
			}else {
                e.moveTouch();
            }
            invalidate();
			break;
		//des que tu retire ton doigt
		case MotionEvent.ACTION_UP:
		    if(longClick == -1) {
                e.upTouch();
            }
            longClick = -1;
			invalidate();
			break;
		}
        return super.onTouchEvent(event);
	}

    @Override
    public boolean onLongClick(View view) {
        longClick = e.checkLongClick();
        return true;
    }

    public void clearCanvas() {
		Log.e("clear", "clear");
	}

}
