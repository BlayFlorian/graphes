package com.example.fblay.graphes;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by fblay on 13/10/2017.
 */

public class Event {
    float eventX, eventY, startX, startY;
    int nodeIndex;
    DotList dotList;
    Integer TOLERANCE = 5;
    Context context;
    AttributeSet attrs;

    ArcList arcList;

    public Event(DotList dList, Context context, AttributeSet attrs) {
        this.context = context;
        this.attrs = attrs;
        this.eventX = 0;
        this.eventY = 0;
        this.dotList = dList;
    }
    // when ACTION_DOWN start touch according to the x,y values
    public void startTouch() {
        nodeIndex = this.dotList.searchDot(this.eventX, this.eventY);
        this.startY = eventY;
        this.startX = eventX;
        if(nodeIndex >= 0) {
            this.startX = this.dotList.getDot(nodeIndex).getX();
            this.startY = this.dotList.getDot(nodeIndex).getY();
            this.arcList = this.dotList.getDot(nodeIndex).getArcList();
        }
    }

    public void moveTouch() {
        if(this.nodeIndex >= 0) {
            arcList.getCurrent().draw(this.dotList.getDot(nodeIndex), eventX, eventY);
            arcList.getCurrent().path.reset();
            arcList.getCurrent().path.moveTo(startX, startY);
            arcList.getCurrent().path.lineTo(eventX, eventY);
        }
    }

    public void upTouch() {
        int res = dotList.searchDot(eventX, eventY);
        if(this.nodeIndex >= 0) {
            arcList.getCurrent().path.reset();
            if(res >= 0) {
                arcList.getCurrent().setArc(dotList.getDot(this.nodeIndex), dotList.getDot(res));
                arcList.add();
            }
        }
    }

    public boolean checkLongClick() {
        if(startY <= eventY + 25 && startY >= eventY - 25
                && startX <= eventX + 25 && startX >= eventX - 25) {
            if (this.nodeIndex >= 0) {
                return true;
            }
            else {
                dialog();
            }
        }
        return false;
    }

    public void moveNode(){
        dotList.getDot(nodeIndex).setY((int) eventY);
        dotList.getDot(nodeIndex).setX((int) eventX);
        DotList dl = dotList.containsDot(dotList.getDot(nodeIndex));
        for(int i = 0; i < dl.getSize(); i++ ) {
            ArcList arcList = dl.getDot(i).getArcList();
            for (int y = 0; y < arcList.getSize() - 1; y++) {
                arcList.getArc(y).move();
            }
        }
    }


    public void dialog() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialogue_name, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.edit1);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Dot d = new Dot(context,attrs);
                                d.setText(""+userInput.getText());
                                d.setX((int)eventX);
                                d.setY((int)eventY);
                                dotList.putDot(d);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

}
