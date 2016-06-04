package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;



import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Board{
    Logger logger = Logger.getLogger(Board.class.getName());

    ArrayList<Field> fields = new ArrayList<Field>();
    ArrayList<Field> redStartFields = new ArrayList<Field>();
    ArrayList<Field> greenStartFields = new ArrayList<Field>();
    ArrayList<Field> yellowStartFields = new ArrayList<Field>();
    ArrayList<Field> blueStartFields = new ArrayList<Field>();
    ArrayList<Integer[]> lines = new ArrayList<Integer[]>();
    FileHandle file;
    BufferedReader CSVFile;
    String dataRow;

    ArrayList<Unit> units = new ArrayList<Unit>();

    ArrayList<Rock> rocks = new ArrayList<Rock>();


    /**
     * Konstruktor
     */
    public Board()
    {
        parseFields();
        parseLines();
        createUnits();
        createRocks();
    }

    /**
     * Liest die Maps aus den Dateien und generiert daraus Spielfeldobjekt-Instanzen
     */
    private void parseFields()
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
                    color = Color.BLACK;
                }
                else if(line[1].equals("rd"))
                {
                    color = Color.RED;
                }
                else if(line[1].equals("yw"))
                {
                    color = Color.YELLOW;
                }
                else if(line[1].equals("bu"))
                {
                    color = Color.BLUE;
                }
                else if(line[1].equals("gr"))
                {
                    color = Color.GREEN;
                }
                else if(line[1].equals("wt"))
                {
                    color = Color.WHITE;
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
            CSVFile.close();
        }
        catch (Exception ex)
        {
            logger.severe("Fehler beim Parsen der Felder!\n" + ex);
        }
    }

    private void parseLines()
    {
        try {
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
        catch (Exception ex)
        {
            logger.severe("Fehler beim Parsen der Linien zwischen den Feldern");
        }
    }

    /**
     * Erstellt aus den Daten der Fieldmap die Spielfiguren
     * (i-3), da Index nicht mit Zeilennummer übereinstimmt
     */
    private void createUnits(){
        for (int i = 115; i < 120; i++) {
            units.add(new Unit (Team.RED, fields.get(i-3), i));
        }
        for (int i = 120; i < 125; i++) {
            units.add(new Unit (Team.GREEN, fields.get(i-3), i));
        }
        for (int i = 125; i < 130; i++) {
            units.add(new Unit (Team.YELLOW, fields.get(i-3), i));
        }
        for (int i = 130; i < 135; i++) {
            units.add(new Unit (Team.BLUE, fields.get(i-3), i));
        }
    }

    /**
     * Fügt auf allen roten Felder, ausgenommen der roten Startfelder, einen Stein hinzu
     */
    private void createRocks(){
        int counter = 1;
        Rock rock;
        for (Field field : fields) {
            if (field.getColor() == Color.RED && !redStartFields.contains(field)) {
                rock = new Rock (counter, field);
                rocks.add(rock);
                field.setRockId(counter);
                field.setRock(rock);
                counter++;
            }
        }
    }

    public ArrayList getUnits() {
        return units;
    }

    public ArrayList getFields()
    {
        return fields;
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }

    public ArrayList getLines()
    {
        return lines;
    }

    public ArrayList getRedStartFields() {return redStartFields;};

    public ArrayList getGreenStartFields() {return greenStartFields;};

    public ArrayList getYellowStartFields() {return yellowStartFields;};

    public ArrayList getBlueStartFields() {return blueStartFields;};

    public Field getFieldByID(int id)
    {
        for(Field f : fields)
        {
            if(f.getID() == id)
            {
                return f;
            }
        }
        return null;
    }
}
