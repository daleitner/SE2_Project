package com.malefiz.game.models;

import java.util.ArrayList;

/**
 * Created by Dan on 26.04.2016.
 */
public class Field {
    int ID;
    Color color;
    int coordX;
    int coordY;
    ArrayList<Integer> neighbouringFields;

    public Field(int id, Color color, int coordX, int coordY, ArrayList neighbouringFields)
    {
        this.ID = id;
        this.color = color;
        this.coordX = coordX;
        this.coordY = coordY;
        this.neighbouringFields = neighbouringFields;
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
}
