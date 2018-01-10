package com.example.fblay.graphes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 *  @author Florian Blay & Lucile Floc
 */

/**
 * Classe DraableGraph
 */
public class DrawableGraph extends View implements View.OnLongClickListener {
	private Paint backgroundPaint;
    private Event e;
    private int longClick;

    private Context context;
    private DotList dotList;
    private AttributeSet attrs;
    private int mode = -1;

    /**
     * Constructeur
     * @param c
     * @param attrs
     */
	public DrawableGraph(Context c, AttributeSet attrs) {
		super(c, attrs);
		context = c;
		this.attrs = attrs;
        setLongClickable(true);
        setOnLongClickListener(this);
        this.dotList = new DotList(this);
        e = new Event(this);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.CYAN);
	}

    @Override
    public Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putParcelable("event", this.e); // ... save stuff
        bundle.putParcelable("dotList", this.dotList);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle) // implicit null check
        {
            Bundle bundle = (Bundle) state;
            this.e = bundle.getParcelable("event");
            this.dotList = bundle.getParcelable("dotList");
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		canvas.drawRect(0, 0, width, height, backgroundPaint);
        ArrayList<Dot> l = dotList.getDotList();
        for(int i = 0; i < l.size(); i++) {
            l.get(i).Draw(canvas);
            ArcList arcL = l.get(i).getArcList();
            for(int y = 0; y < arcL.getSize(); y++) {
                if(arcL.getArc(y).getPath() != null) {
                    Paint mPaint = arcL.getArc(y).getPaint();
                    canvas.drawPath(arcL.getArc(y).getPath(), mPaint);
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
			} else if(longClick == 2 && mode == 1) {
                e.menuArc();
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
