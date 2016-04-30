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

    ArrayList<Unit> unit_fields = new ArrayList<Unit>();
    ArrayList<Unit> units = new ArrayList<Unit>();



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

        createUnits();


    }

    private void createUnits(){
        // hier muessen noch die startpositionen eingebaut werden
        for (int i = 1; i < 6; i++) {
            units.add(new Unit (Team.RED, fields.get(i)));
        }
        for (int i = 7; i < 12; i++) {
            units.add(new Unit (Team.YELLOW, fields.get(i)));
        }
        for (int i = 13; i < 18; i++) {
            units.add(new Unit (Team.GREEN, fields.get(i)));
        }
        for (int i = 20; i < 25; i++) {
            units.add(new Unit (Team.BLUE, fields.get(i)));
        }
    }

    public ArrayList getUnits() {
        return units;
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
