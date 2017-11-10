package com.example.fblay.graphes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class DrawableGraph extends View implements View.OnLongClickListener, Parcelable {
	private Paint backgroundPaint;
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
		    Log.e("mode", ""+mode);
		    if(longClick == -1 && mode == 1) {
                e.upTouch();
            } else if(longClick == 1 && mode == 0) {
				e.menuNode();
			} else if(longClick == 2 && mode == 1) {
                Log.e("ici", "ici");
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
