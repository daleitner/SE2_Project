package com.malefiz.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


import java.io.BufferedReader;
import java.util.ArrayList;


/**
 * Created by Dan on 26.04.2016.
 */
public class Board{
    ArrayList<Field> fields = new ArrayList<Field>();
    ArrayList<Field> redStartFields = new ArrayList<Field>();
    ArrayList<Field> greenStartFields = new ArrayList<Field>();
    ArrayList<Field> yellowStartFields = new ArrayList<Field>();
    ArrayList<Field> blueStartFields = new ArrayList<Field>();
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

                if(line[1].equals("bl"))
                {
                    color = color.BLACK;
                }
                else if(line[1].equals("rd"))
                {
                    color = color.RED;
                }
                else if(line[1].equals("yw"))
                {
                    color = color.YELLOW;
                }
                else if(line[1].equals("bu"))
                {
                    color = color.BLUE;
                }
                else if(line[1].equals("gr"))
                {
                    color = color.GREEN;
                }
                try {
                    neighbouringFields.add(Integer.parseInt(line[4]));
                    neighbouringFields.add(Integer.parseInt(line[5]));
                    neighbouringFields.add(Integer.parseInt(line[6]));
                }catch (Exception ex)
                {

                }
                Field f = new Field(id, color, coordX, coordY, neighbouringFields);
                fields.add(f);
                if(130 <= Integer.parseInt(line[0]))
                {
                    blueStartFields.add(f);
                }
                else if(125 <= Integer.parseInt(line[0]))
                {
                    yellowStartFields.add(f);
                }
                else if(120 <= Integer.parseInt(line[0]))
                {
                    greenStartFields.add(f);
                }
                else if(115 <= Integer.parseInt(line[0]))
                {
                    redStartFields.add(f);
                }
                dataRow = CSVFile.readLine();
            }
            System.out.println(redStartFields.size());
            System.out.println(greenStartFields.size());
            System.out.println(yellowStartFields.size());
            System.out.println(blueStartFields.size());
            CSVFile.close();

            //Linien aus CSV parsen und Liste generieren
            file = Gdx.files.internal("line-map.csv");
            CSVFile = new BufferedReader(file.reader());
            dataRow = CSVFile.readLine();
            while(dataRow != null)
            {
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
        for (int i = 115; i < 120; i++) {
            units.add(new Unit (Team.RED, fields.get(i-3)));
        }
        for (int i = 120; i < 125; i++) {
            units.add(new Unit (Team.GREEN, fields.get(i-3)));
        }
        for (int i = 125; i < 130; i++) {
            units.add(new Unit (Team.YELLOW, fields.get(i-3)));
        }
        for (int i = 130; i < 135; i++) {
            units.add(new Unit (Team.BLUE, fields.get(i-3)));
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

    public ArrayList getRedStartFields() {return redStartFields;};

    public ArrayList getGreenStartFields() {return greenStartFields;};

    public ArrayList getYellowStartFields() {return yellowStartFields;};

    public ArrayList getBlueStartFields() {return blueStartFields;};
}
