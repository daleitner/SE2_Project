package com.malefiz.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * Created by Dan on 26.04.2016.
 */
public class Board{
    ArrayList<Field> fields = new ArrayList<Field>();

    public Board()
    {
        try {
            BufferedReader CSVFile = new BufferedReader(new FileReader("Example.csv"));
            String dataRow = CSVFile.readLine();
            while(dataRow != null)
            {
                String[] line = dataRow.split(",");
                ArrayList<Integer> neighbouringFields = new ArrayList<Integer>();
                int id = Integer.parseInt(line[0]);
                int coordX = Integer.parseInt(line[2]);
                int coordY = Integer.parseInt(line[3]);
                neighbouringFields.add(Integer.parseInt(line[4]));
                neighbouringFields.add(Integer.parseInt(line[5]));
                try {
                    neighbouringFields.add(Integer.parseInt(line[6]));
                }catch (Exception ex)
                {

                }
                fields.add(new Field(id, line[1], coordX, coordY, neighbouringFields));
            }
        }catch (Exception ex)
        {

        }

    }

    public void draw()
    {

    }

    public ArrayList getFields()
    {
        return fields;
    }
}
