package com.malefiz.game.models;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

/**
 * Created by Dan on 26.04.2016.
 */
public class Field {
    int ID;
    Color color;
    int coordX;
    int coordY;
    int RealCoordX;
    int RealCoordY;
    public boolean isTaken = false;
    ArrayList<Integer> neighbouringFields;
    public Image fieldImage;

    boolean empty = true;
    int rockId; // 0 == no rock
    int unitId = 0; // 0 == no unit

    public Field(int id, Color color, int coordX, int coordY, ArrayList neighbouringFields)
    {
        this.ID = id;
        this.color = color;
        this.coordX = coordX;
        this.coordY = coordY;
        this.neighbouringFields = neighbouringFields;
        this.rockId = 0;
    }

    public int getID()
    {
        return ID;
    }

    public Color getColor()
    {
        return color;
    }

    public int getCoordX()
    {
        return coordX;
    }

    public int getCoordY()
    {
        return coordY;
    }

    public void setRealCoordX(int RealCoordX)
    {
        this.RealCoordX = RealCoordX;
    }

    public void setRealCoordY(int RealCoordY)
    {
        this.RealCoordY = RealCoordY;
    }

    public int getRealCoordX(){return this.RealCoordX;}

    public int getRealCoordY(){return this.RealCoordY;}

    public boolean isTaken() {return isTaken;}

    public void setTaken(boolean taken){this.isTaken = taken;}

    public ArrayList<Integer> getNeighbouringFields() {
        return neighbouringFields;
    }

    public Image getFieldImage() {
        return fieldImage;
    }

    public void setFieldImage(Image fieldImage) {
        this.fieldImage = fieldImage;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setIsEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getRockId() {
        return rockId;
    }

    public void setRockId(int rockId) {
        this.rockId = rockId;
    }
}
