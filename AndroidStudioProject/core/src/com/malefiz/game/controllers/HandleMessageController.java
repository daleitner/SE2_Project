package controllers;

import java.util.ArrayList;

import models.Field;
import models.Rock;
import models.Unit;
import screens.GameScreen;

public class HandleMessageController {
    GameController gameController;
    GameScreen gameScreen;

    public HandleMessageController (GameController gameController) {
        this.gameController = gameController;
        this.gameScreen = gameController.gameScreen;
    }

    public void handleMessage(String message) {
        String[] splitMessage = message.split(";");
        ArrayList<Unit> units = gameScreen.getUnits();
        ArrayList<Field> fields = gameScreen.getFields();
        ArrayList<Rock> rocks = gameScreen.getRocks();

        // "|command|unitID|newPositionID|"
        if (splitMessage[0].equals("setUnit"))
        {
        Unit u = getUnitById(Integer.parseInt(splitMessage[1]));
            for (Field f : fields) {
                if (f.getID() == Integer.parseInt(splitMessage[2])){
                    u.setPosition(f);
                    gameController.setUnitImagePosition(u);
                }
            }

        }

        // "|command|fieldId|Content["rock" || "unit" || "null"]|(RockId || UnitId)"
         else if (splitMessage[0].equals("setFieldContent"))
        {
            Field f = getFieldById(Integer.parseInt(splitMessage[1]));
                    if (splitMessage[2].equals("rock")) {
                        f.setRock(getRockById(Integer.parseInt(splitMessage[3])));
                    } else if (splitMessage[2].equals("unit")) {
                        f.setUnit(getUnitById(Integer.parseInt(splitMessage[3])));
                    } else if (splitMessage[2].equals("null")) {
                        f.setUnit(null);
                        f.setRock(null);
                    }
        }
        // "|command|rockID|newPositionID|"
        else if(splitMessage[0].equals("setRock"))
        {
            Rock r = getRockById(Integer.parseInt(splitMessage[1]));
            Field f = getFieldById(Integer.parseInt(splitMessage[2]));
            r.setPosition(f);
            gameController.setRockImagePosition(r);
        }
        //"|command|diceValue|"
        else if(splitMessage[0].equals("rollDice"))
        {
            gameScreen.setDiceDisplay(Integer.parseInt(splitMessage[1]));
            gameScreen.setAnimationActive();
            gameScreen.resetElapsedTime();
        }
        //""
        else if(splitMessage[0].equals("rollDice"))
        {

        }
    }

    public Unit getUnitById(int id)
    {
        ArrayList<Unit> units = gameScreen.getUnits();
        for (Unit u : units){
            if (u.getId() == id)
            {
                return u;
            }
        }

        return null;
    }

    public Rock getRockById(int id)
    {
        ArrayList<Rock> rocks = gameScreen.getRocks();
        for (Rock r : rocks){
            if (r.getId() == id)
            {
                return r;
            }
        }

        return null;
    }

    public Field getFieldById(int id)
    {
        ArrayList<Field> fields = gameScreen.getFields();
        for (Field f : fields){
            if (f.getID() == id)
            {
                return f;
            }
        }
        return null;
    }

}
