package com.malefiz.game.models;

import java.util.ArrayList;

/**
 * Created by Dan on 26.04.2016.
 */
public class Field {
    int ID;
    String color;
    int coordX;
    int coordY;
    ArrayList<Integer> neighbouringFields;

    public Field(int id, String color, int coordX, int coordY, ArrayList neighbouringFields)
    {
        this.neighbouringFields = neighbouringFields;
    }
}
