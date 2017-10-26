package com.example.fblay.graphes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by fblay on 13/10/2017.
 */

public class Event {
    float eventX, eventY, startX, startY;
    int nodeIndex;
    int[] middle;
    DotList dotList;
    Integer TOLERANCE = 5;
    int mode;
    Context context;
    AttributeSet attrs;
    DrawableGraph thisDG;
    ArcList arcList;

    public Event(DotList dList, Context context, AttributeSet attrs, DrawableGraph thisDG) {
        this.context = context;
        this.attrs = attrs;
        this.eventX = 0;
        this.eventY = 0;
        this.dotList = dList;
        this.thisDG = thisDG;
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

    public int checkLongClick() {
        if(startY <= eventY + 25 && startY >= eventY - 25
                && startX <= eventX + 25 && startX >= eventX - 25) {
            middle = dotList.searchMiddle(startX, startY);
            if (this.nodeIndex >= 0) {
                return 1;
            } else if(middle[0] >= 0){
                return 2;
            } else if (mode == 0){
                textDialog(0);
            }
        }
        return -1;
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
    public void moveMiddle() {
        dotList.getDot(middle[0]).getArcList().getArc(middle[1]).moveMiddle(eventX, eventY);
    }


    //LES DIALOGUES
    //0 = ajout noeud
    //1 = modification Text noeud
    @SuppressLint("InflateParams") //C'est l'exception donc pas de Warning Linter
    public void textDialog(final int arg) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialogue_name, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.edit1);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if(arg == 0) {
                                    newNode(""+userInput.getText());
                                } else if(arg == 1){
                                    setTextNode(""+userInput.getText());
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void menuNode(){
        CharSequence choice[] = new CharSequence[] {"Modifier text", "Modifier taille", "Modifier couleur", "Supprimer Noeud"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Menu Noeud");
        builder.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    textDialog(1);
                } else if(which == 1) {
                    show();
                } else if(which == 2) {
                    colorPicker();
                } else if(which == 2) {
                    supNode();
                }
            }
        });
        builder.show();
    }
    public void colorPicker(){
        CharSequence choice[] = new CharSequence[] {"Rouge", "Vert", "Bleu", "Orange", "Cyan", "Magenta", "Noir"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Couleur");
        builder.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("color", which+"");
                dotList.getDot(nodeIndex).setColor(which);
            }
        });
        builder.show();
    }

    public void show()
    {
        LayoutInflater li = LayoutInflater.from(context);
        AlertDialog.Builder d = new AlertDialog.Builder(
                context);
        View promptsView = li.inflate(R.layout.dialogue_number, null);
        d.setView(promptsView);
        d.setTitle("NumberPicker");
        final NumberPicker np = (NumberPicker) promptsView
                .findViewById(R.id.numberPicker1);
        np.setMaxValue(100); // max value 100
        np.setMinValue(50);   // min value 0
        np.setWrapSelectorWheel(false);
        d.setPositiveButton("OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    setSize(np.getValue());
                }
            })
            .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });
        d.show();


    }

    public void newNode(String text){
        Dot d = new Dot(context,attrs);
        d.setText(text);
        d.setX((int)eventX);
        d.setY((int)eventY);
        dotList.putDot(d);
    }

    public void setTextNode(String text) {
        dotList.getDot(nodeIndex).setText(text);
    }

    public void setSize(int i){
        dotList.getDot(nodeIndex).setRadius(i);
        thisDG.invalidate();
    }
    public void supNode(){
        dotList.supDot(nodeIndex);
    }
}
