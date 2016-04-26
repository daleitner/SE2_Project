package com.malefiz.game.models;

import java.util.ArrayList;

/**
 * Created by Dan on 26.04.2016.
 */
public class Field {
    ArrayList<Field> neighbouringFields;

    public Field(ArrayList neighbouringFields)
    {
        this.neighbouringFields = neighbouringFields;
    }
}
