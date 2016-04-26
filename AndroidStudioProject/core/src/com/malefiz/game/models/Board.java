package com.malefiz.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sun.webkit.dom.CSSValueImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * Created by Dan on 26.04.2016.
 */
public class Board{
    ArrayList<Field> fields = new ArrayList<Field>();
    ArrayList<Integer[]> lines = new ArrayList<Integer[]>();
    FileHandle file;
    BufferedReader CSVFile;
    String dataRow;

    public Board()
    {
        try {
            //Felder aus CSV parsen und Liste mit Feld-Instanzen generieren
            file = Gdx.files.internal("field-map.csv");
            CSVFile = new BufferedReader(file.reader());
            dataRow = CSVFile.readLine();
            while(dataRow != null)
            {
                Color color = null;
                String[] line = dataRow.split(";");
                ArrayList<Integer> neighbouringFields = new ArrayList<Integer>();
                int id = Integer.parseInt(line[0]);
                int coordX = Integer.parseInt(line[2]);
                int coordY = Integer.parseInt(line[3]);
                neighbouringFields.add(Integer.parseInt(line[4]));
                neighbouringFields.add(Integer.parseInt(line[5]));
                if(line[1].equals("bl"))
                {
                    color = color.BLACK;
                }
                else
                {
                    color = color.RED;
                }
                try {
                    neighbouringFields.add(Integer.parseInt(line[6]));
                }catch (Exception ex)
                {

                }
                fields.add(new Field(id, color, coordX, coordY, neighbouringFields));
                dataRow = CSVFile.readLine();
            }
            CSVFile.close();

            //Linien aus CSV parsen und Liste generieren
            file = Gdx.files.internal("line-map.csv");
            CSVFile = new BufferedReader(file.reader());
            dataRow = CSVFile.readLine();
            while(dataRow != null)
            {
                System.out.println("Line");
                String[] line = dataRow.split(";");
                Integer[] lineCoords = {Integer.parseInt(line[0]),Integer.parseInt(line[1]),Integer.parseInt(line[2]),Integer.parseInt(line[3])};
                lines.add(lineCoords);
                dataRow = CSVFile.readLine();
            }
            CSVFile.close();

        }
        catch (Exception ex){}




    }

    public ArrayList getFields()
    {
        return fields;
    }

    public ArrayList getLines()
    {
        return lines;
    }
}
