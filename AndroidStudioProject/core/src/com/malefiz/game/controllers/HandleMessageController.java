package controllers;

import java.util.ArrayList;

import models.Field;
import models.Mode;
import models.Rock;
import models.Team;
import models.Unit;
import network.MessageObject;
import network.MessageTypeEnum;
import screens.GameScreen;

public class HandleMessageController {
    GameController gameController;
    GameScreen gameScreen;
    MessageTypeEnum mEnum;

    public HandleMessageController (GameController gameController, GameScreen gameScreen) {
        this.gameController = gameController;
        this.gameScreen = gameScreen;
    }

    public void receiveMessage() {
            String msg = gameController.getClient().getReceivedMessage();
            if(!msg.isEmpty()) {
                MessageObject obj = MessageObject.MessageToMessageObject(msg);
                handleMessage(obj);
            }

    }


    public void handleMessage(MessageObject message) {
        Field field = null;
        Unit unit = null;
        Rock rock = null;

        switch (message.getMessageType()) {

            case SetUnit:
                // Info [unitId; fieldId]
                unit = getUnitById(Integer.parseInt(message.getInformation().get(0)));
                field = getFieldById(Integer.parseInt(message.getInformation().get(1)));
                unit.setPosition(field);
                gameController.setUnitImagePosition(unit);
                break;

            case SetFieldContent:
                // Info [fieldId; ["rock"||"unit"||"null"]; [unitId||rockId]]
                field = getFieldById(Integer.parseInt(message.getInformation().get(0)));
                if (message.getInformation().size() == 1) {
                    field.setUnit(null);
                    field.setRock(null);
                } else if (message.getInformation().get(1).equals("unit")) {
                    field.setUnit(getUnitById(Integer.parseInt(message.getInformation().get(2))));
                } else if (message.getInformation().get(1).equals("rock")) {
                    field.setRock(getRockById(Integer.parseInt(message.getInformation().get(2))));
                }
                break;

            case SetRock:
                // Info [rockId; newPositionId(field)]
                rock = getRockById(Integer.parseInt(message.getInformation().get(0)));
                field = getFieldById(Integer.parseInt(message.getInformation().get(1)));
                rock.setPosition(field);
                gameController.setRockImagePosition(rock);
                break;

            case RollDice:
                // Info [diceValue]
                gameScreen.setDiceDisplay(Integer.parseInt(message.getInformation().get(0)));
                gameScreen.setAnimationActive();
                gameScreen.resetElapsedTime();
                break;

            case NextPlayer:
                // Info []
                gameController.getNextPlayer();
                break;

            case SetWinner:
                // Info []
                gameScreen.getMainClass().setWinnerScreen(gameScreen.getBoard().getFieldByID(112).getUnit().getTeam());


        }

    }


    public Unit getUnitById(int id)
    {
        if(gameScreen == null)
        {
            System.out.println("Gamescreen = null");
        }
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
