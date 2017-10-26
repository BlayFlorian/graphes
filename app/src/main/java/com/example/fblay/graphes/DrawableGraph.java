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
	private Paint backgroundPaint;
	private Paint mPaint;
    private Event e;
    private int longClick;

    private Context context;
    private DotList dotList;
    private AttributeSet attrs;
    private int mode = -1;

	public DrawableGraph(Context c, AttributeSet attrs) {
		super(c, attrs);
		context = c;
		this.attrs = attrs;
        setLongClickable(true);
        setOnLongClickListener(this);
        dotList = new DotList(context, attrs);
        e = new Event(this);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.CYAN);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		canvas.drawRect(0, 0, width, height, backgroundPaint);
        ArrayList<Dot> l = dotList.getList();
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
	}

    @Override
	public boolean onTouchEvent(MotionEvent event) {
		e.setEvent(event.getX(), event.getY());
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

    public AttributeSet getAttrs() {
        return this.attrs;
    }

    public DotList getDotList() {
        return this.dotList;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
