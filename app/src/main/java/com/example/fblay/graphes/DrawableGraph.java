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
    DotList dotList;
	AttributeSet attrs;
    int mode = -1;
    int longClick;
	private Paint mPaint;
    Event e;

	public DrawableGraph(Context c, AttributeSet attrs) {
		super(c, attrs);
		context = c;
		this.attrs = attrs;
        setLongClickable(true);
        setOnLongClickListener(this);
        dotList = new DotList(context, attrs);
        e = new Event(dotList, context, attrs, this);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.CYAN);
	}

    // override onDraw
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        Log.e("Mode", ""+mode);
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		canvas.drawRect(0, 0, width, height, backgroundPaint);
        ArrayList<Dot> l = dotList.getList();
		mPaint = new Paint();
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
	}

    @Override
	public boolean onTouchEvent(MotionEvent event) {
		e.eventX = event.getX();
		e.eventY = event.getY();
		e.mode = mode;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			e.startTouch();
            invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
		    if(longClick == 1 && mode == 2) {
                e.moveNode();
            } else if (longClick == 2 && mode == 2){
				e.moveMiddle();
			}else if (mode == 1) {
                e.moveTouch();
            }
            invalidate();
			break;
		//des que tu retire ton doigt
		case MotionEvent.ACTION_UP:
		    if(longClick == -1 && mode == 1) {
                e.upTouch();
            } else if(longClick == 1 && mode == 0) {
				e.menuNode();
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

}
