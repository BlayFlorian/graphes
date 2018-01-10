package com.example.fblay.graphes;

import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 *  @author Florian Blay & Lucile Floc
 */

public class ArcList implements Parcelable {


    ArrayList<Arc> arrayArc ;

    /**
     * Constructeur ArcList
     */
    public ArcList() {
        arrayArc = new ArrayList<Arc>();
        arrayArc.add(new Arc());
    }

    protected ArcList(Parcel in) {
    }

    public static final Creator<ArcList> CREATOR = new Creator<ArcList>() {
        @Override
        public ArcList createFromParcel(Parcel in) {
            return new ArcList(in);
        }

        @Override
        public ArcList[] newArray(int size) {
            return new ArcList[size];
        }
    };

    /**
     * @return la taille du tableau contenant les arcs. Soit le nombre d'arc
     */
    public int getSize() {
        return arrayArc.size();
    }

    /**
     * ?????????????
     * @return
     */
    public Arc getCurrent() {
        return arrayArc.get(arrayArc.size() - 1);
    }

    /**
     * @param index: index de l'arc que l'on cherche
     * @return l'arc à l'index passé en paramètre
     */
    public Arc getArc(int index) {
        return arrayArc.get(index);
    }


    /**
     * Ajouter un nouvel arc
     */
    public void add(){
        arrayArc.add(new Arc());
    }

    /**
     * Trouve le milieu d'un arc
     * @param x: Coordonnée abscisse de la position actuelle
     * @param y: Coordonnée ordonnée de la position actuelle
     * @return l'index de l'arc, -1 sinon
     */
    public int getMiddle(float x, float y) {
        for (int i = 0; i < arrayArc.size() - 1; i++) {
            float[] position = arrayArc.get(i).getPosition();
            if (position[0] < x + 50 && position[0] > x -50) {
                if (position[1] < y + 50 && position[1] > y - 50) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Suppression d'un arc
     * @param index: index de l'arc
     */
    public void deleteArc(int index) {
        arrayArc.remove(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
