package controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Exchanger;

import models.Avatar;
import models.Field;
import models.Mode;
import models.Player;
import models.Rock;
import models.Team;
import models.Unit;
import network.MalefizClient;
import network.MessageObject;
import network.MessageTypeEnum;
import screens.GameScreen;

public class GameController {
    GameScreen gameScreen;
    HashMap<Integer, Player> players = new HashMap<Integer, Player>();
    Player actualPlayer;
    private int diceTries = 3;
    private boolean diceRolled;
    private boolean unitMoved;
    private boolean playerAbleToMove;
    private boolean rockTaken;
    private MalefizClient client;
    private Mode mode;

    private HandleMessageController handleMessageController = null;


    public GameController(GameScreen gameScreen, HashMap<Integer, Player> _players, Mode mode){
        this.handleMessageController = new HandleMessageController(this, gameScreen);
        this.gameScreen = gameScreen;
        this.players = _players;
        this.actualPlayer = this.players.get(0);
        this.mode = mode;
        reset();
    }

    /**
     * GameScreen pingt den Controller an, um Fortschritt zu überprüfen
     */
    public void check()
    {
        if(((diceRolled && unitMoved) || (!playerAbleToMove && diceTries <= 0)) && !rockTaken && !gameScreen.isAnimationActive())
        {
            System.out.println("DiceRolled: " + diceRolled + " unitMoved: " + unitMoved + " PlayerAbleToMove: " + playerAbleToMove + " diceTries: " + diceTries + " Dicevalue: " + gameScreen.getRolledDiceValue());
            gameScreen.deleteDiceDisplay();

            getNextPlayer();
            if(this.mode == Mode.NETWORK) {
                MessageObject msg = new MessageObject(client.getNickName(), MessageTypeEnum.NextPlayer, null);
                client.sendMessage(msg);
            }
        }

        /*if(diceRolled && playerAbleToMove(getActualTeam()))
        {
            //ToDo: deaktivieren des Würfels
        }*/
    }

    /**
     * Setzt die Zustände nach jeder Runde (bei Aufruf von getNextPlayer()) in den Ursprungszustand zurück
     */
    public void reset()
    {
        diceRolled = false;
        unitMoved = false;
        rockTaken = false;
        playerAbleToMove = false;
    }



    /**

     * Beendet die Runde und aktiviert den nächsten Spieler
     */
    public void getNextPlayer()
    {
        int idx = actualPlayer.getIndex();
        reset();
        actualPlayer =  players.get((idx+1)%players.size());

        if(actualPlayer.getCheatCount() >= 3)
        {
            gameScreen.removeRiggedDice();
        }
        else
        {
            if(!gameScreen.isRiggedDiceVisible())
            {
                gameScreen.showRiggedDice();
            }
        }
        System.out.println("get next player team = " + actualPlayer.getTeam());
        diceTries = 3;
        gameScreen.drawAvatar();
        gameScreen.activateRandomDiceDisplay();
        for(Unit u : gameScreen.getUnits())
        {
            if(u.getTeam() == getActualTeam())
            {
                if(u.getCurrentFieldPosition() != u.getStartPosition())
                {
                    diceTries = 1;
                }
            }
        }
        gameScreen.getActionResolver().showToast("Spieler " + actualPlayer.getTeam() + " ist am Zug!");
        gameScreen.getActionResolver().showToast("Bitte würfeln!");
    }

    /**
     * Setzte das Bild einer ausgewaehlten Unit auf ihre akuelle Position (currentposition)
     * @param unit
     */
    public void setUnitImagePosition (Unit unit) {

        unit.getUnitImage().setX(gameScreen.getUnitSize() * unit.getCurrentFieldPosition().getCoordX() - (unit.getUnitImage().getImageWidth()/2));
        //unit.getUnitImage().setX(gameScreen.getUnitSize() * unit.getUnitCoordX() - gameScreen.getFieldSize() / 2);


        int y;
        if(unit.getUnitCoordY() == 6)
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * unit.getUnitCoordY() - 4 * gameScreen.getUnitSize() - gameScreen.getFieldSize() / 2 + (gameScreen.getG().getRatio()-1)*gameScreen.getUnitSize());
            unit.getUnitImage().setY(y);
        }
        else if(unit.getUnitCoordY() == 5)
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * unit.getUnitCoordY() - 4 * gameScreen.getUnitSize() - gameScreen.getFieldSize() / 2 + 2*(gameScreen.getG().getRatio()-1)*gameScreen.getUnitSize());
            unit.getUnitImage().setY(y);
        }
        else
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * unit.getUnitCoordY() - 4 * gameScreen.getUnitSize() - gameScreen.getFieldSize() / 2);
            unit.getUnitImage().setY(y);
        }

        unit.getUnitImage().setHeight(gameScreen.getFieldSize()*gameScreen.getG().getRatio()*1.2f);
        unit.getUnitImage().setWidth(gameScreen.getFieldSize()*2);
    }

    /**
     * Bewegt Unit
     * checked ob der Zug moeglich ist
     * checked ob ein stein aufgenommen oder eine unit geschlagen wird
     * @param nextPosition
     * @param unit
     */
    public void moveUnit (Field nextPosition, Unit unit) {

        ArrayList<String> info;
        MessageObject msg;

        if(gameScreen.getBoard().getFieldByID(112).equals(nextPosition))
        {
            gameScreen.getMainClass().setWinnerScreen(unit.getTeam());
            if(this.mode == Mode.NETWORK) {
                // send unit position "setUnitPosition;unitId;fieldId"
                msg = new MessageObject(client.getNickName(), MessageTypeEnum.SetWinner, null);
                client.sendMessage(msg);
            }
        }
        // todo check if next position is empty
        //if (checkPossibleMoves(rolledDiceNumber, unit.getCurrentFieldPosition(), nextPosition)) {
        if (gameScreen.getPossibleMoves().contains(nextPosition)) {
            Unit u = nextPosition.getUnit();
            Rock r = nextPosition.getRock();

            if (r != null)
            {
                setRockPosition(r, null);
                nextPosition.setRock(null);

                if(this.mode == Mode.NETWORK) {
                    // send rockOnField "|setFieldContent|nextposition.getId()|null]|null"
                    info = new ArrayList<String>();
                    info.add(String.valueOf(nextPosition.getID()));
                    msg = new MessageObject(client.getNickName(), MessageTypeEnum.SetFieldContent, info);
                    client.sendMessage(msg);
                }

                gameScreen.setSelectedRock(null);
                // send rock position "setRockPosition;rockId;fieldId"

            }

            if(u != null)
            {
                u.setPosition(u.getStartPosition());
                setUnitImagePosition(u);
                if(this.mode == Mode.NETWORK) {
                    // send unit position "setUnitPosition;unitId;fieldId"
                    info = new ArrayList<String>();
                    info.add(String.valueOf(u.getId()));
                    info.add(String.valueOf(u.getStartPosition().getID()));
                    msg = new MessageObject(client.getNickName(), MessageTypeEnum.SetUnit, info);
                    client.sendMessage(msg);
                }
            }
            unit.currentFieldPosition.setUnit(null);
            if(this.mode == Mode.NETWORK) {
                // "|setFieldContent|currentFieldPosition.getId()|null|null"
                info = new ArrayList<String>();
                info.add(String.valueOf(unit.currentFieldPosition.getID()));
                msg = new MessageObject(client.getNickName(), MessageTypeEnum.SetFieldContent, info);
                client.sendMessage(msg);
            }

            unit.setPosition(nextPosition);
            setUnitImagePosition(unit);
            if(this.mode == Mode.NETWORK) {
                // send unit position "setUnitPosition;unitId;fieldId"
                info = new ArrayList<String>();
                info.add(String.valueOf(unit.getId()));
                info.add(String.valueOf(nextPosition.getID()));
                msg = new MessageObject(client.getNickName(), MessageTypeEnum.SetUnit, info);
                client.sendMessage(msg);
            }

            nextPosition.setUnit(unit);

            if(this.mode == Mode.NETWORK) {
                // "|"setFieldContent"|nextPosition.getId()|"unit"|unit.getId"
                info = new ArrayList<String>();
                info.add(String.valueOf(nextPosition.getID()));
                info.add("unit");
                info.add(String.valueOf(unit.getId()));
                msg = new MessageObject(client.getNickName(), MessageTypeEnum.SetFieldContent, info);
                client.sendMessage(msg);
            }

            clearPossibleMoves();
            unitMoved = true;

            gameScreen.setSelectedUnit(null);
        }

    }

    /**
     * Gibt eine Liste mit allen Nachbarfeldern eines uebergebenen Felds(field) zurueck
     * @param field
     * @return neighbours
     */
    private ArrayList<Field> getNeighbourFieldsOfField (Field field) {
        ArrayList<Field> neighbours= new ArrayList<Field>();
        for (Integer id : field.getNeighbouringFields()) {
            for (Field f : gameScreen.getFields()) {
                if (f.getID() == id) {
                    neighbours.add(f);
                }
            }
        }
        return neighbours;
    }

    /**
     * Ermittelt alle moeglichen Zuege anhand einer ausgewaehlten Unit und des gewuerfelten Wertes
     * Vergroessert die Bilder aller moeglichen Felder zur besseren auswahl
     * return true: Die ausgewaehlte Unit hat mindestens einen moeglichen Zug
     * return false: Die ausgewaehlte Unit hat keinen moeglichen Zug
     * @param rolledDiceValue
     * @param unitPosition
     * @return
     */
    public boolean checkPossibleMoves(int rolledDiceValue, Field unitPosition) {

        ArrayList<Field> possibleFields = new ArrayList<Field>();
        ArrayList<Field> visitedFields = new ArrayList<Field>();
        visitedFields.add(unitPosition);

        ArrayList<Field> visitedTemp = new ArrayList<Field>();
        if (    gameScreen.getB().getRedStartFields().contains(unitPosition) ||
                gameScreen.getB().getBlueStartFields().contains(unitPosition) ||
                gameScreen.getB().getYellowStartFields().contains(unitPosition) ||
                gameScreen.getB().getGreenStartFields().contains(unitPosition) ) {

            if (rolledDiceValue != 6) {
                return false;
            }
        }


        visitedTemp.addAll(visitedFields);

        clearPossibleMoves();


        int size;

        /* steps to go */
        for (int i = 0; i < rolledDiceValue - 1; i++) {
            size = visitedFields.size();
            /* all reachable fields within the current step range
             * visitedtemp all former reached; visitedfields all newly reachend in this step */

            for (int j = 0; j < size; j++) {

                if (visitedFields.get(j).getRock() == null) {
                    visitedFields.addAll(getNeighbourFieldsOfField(visitedFields.get(j)));
                } else {
                    visitedTemp.add(visitedFields.get(j));
                }
            }

            for (Field f : visitedFields) {
                if (f.getRock() != null) {
                    visitedTemp.add(f);
                }
            }

            visitedFields.removeAll(visitedTemp);
            visitedTemp.addAll(visitedFields);
        }

        /* go one last step and include rock fields now */
        for (Field f : visitedFields) {
            possibleFields.addAll( getNeighbourFieldsOfField( f ));
        }

        possibleFields.removeAll(visitedTemp);
        visitedTemp.clear();
        for (Field field : possibleFields) {
            if (field.getUnit() != null) {
                if (field.getUnit().getTeam() == getActualTeam()) {
                    visitedTemp.add(field);
                }
            }
        }
        possibleFields.removeAll(visitedTemp);

        gameScreen.setPossibleMoves(possibleFields);

        for (Field f : gameScreen.getPossibleMoves()) {
            setFieldPosScal(f, 1, false);
        }

        if (gameScreen.getPossibleMoves().isEmpty()) {
            return false;
        } else {
            for (Field f : gameScreen.getPossibleMoves()) {
                f.getFieldImage().setHeight(gameScreen.getFieldSize()*2);
                f.getFieldImage().setWidth(gameScreen.getFieldSize()*2);
            }
            return true;
        }
        //todo if unit current pos == start pos
    }

    /**
     * Clear Arraylist possilbeMoves
     * setzte die groesse dieser Felder auf die normale groesse zurueck
     */
    public void clearPossibleMoves () {
        if (gameScreen.getPossibleMoves() != null) {
            for (Field f : gameScreen.getPossibleMoves()) {
                setFieldPosScal(f, 0, false);
            }
        }
        gameScreen.setPossibleMoves(null);
    }


    // 0 for standard field size; 1 for selected size
    public void setFieldPosScal(Field field, int selected, boolean rockPos) {
        int size;
        if (selected == 1) {
            size = gameScreen.getFieldSize() * 2;
            if(rockPos) size =  (int) (gameScreen.getFieldSize() * 1.5);
        } else {
            size = gameScreen.getFieldSize();
        }

        field.getFieldImage().setX(gameScreen.getUnitSize() * field.getCoordX() - size / 2);

        int y;
        if(field.getCoordY() == 6)
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * field.getCoordY() - 4 * gameScreen.getUnitSize() - size / 2 + (gameScreen.getG().getRatio()-1)*gameScreen.getUnitSize());
            field.getFieldImage().setY(y);
        }
        else if(field.getCoordY() == 5)
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * field.getCoordY() - 4 * gameScreen.getUnitSize() - size / 2 + 2*(gameScreen.getG().getRatio()-1)*gameScreen.getUnitSize());
            field.getFieldImage().setY(y);
        }
        else
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * field.getCoordY() - 4 * gameScreen.getUnitSize() - size / 2);
            field.getFieldImage().setY(y);
        }

        field.setRealCoordX(gameScreen.getUnitSize()*field.getCoordX());
        field.setRealCoordY(y);
        field.getFieldImage().setHeight(size);
        field.getFieldImage().setWidth(size);
    }

    /**
     * Vergroessert die alle freien Felder
     * Dies hilft bei der Auswahl der Felder beim Setzen eines Steines
     * @param show
     */
    public void showPossibleRockPosition(boolean show) {
        if (show)
        {
            for (Field field: gameScreen.getFields())
            {
                if (field.getUnit() == null && field.getRock() == null && field.getID() < 95)
                {
                    setFieldPosScal(field, 1, true);
                }
            }
        }
        else
        {
            for (Field field: gameScreen.getFields())
            {
                setFieldPosScal(field, 0, true);
            }
        }



    }

    public void setRockImagePosition (Rock rock) {
        rock.getRockImage().setX(gameScreen.getUnitSize() * rock.getCoordX() - gameScreen.getFieldSize() / 2);

        int y;
        if(rock.getCoordY() == 6)
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * rock.getCoordY() - 4 * gameScreen.getUnitSize() - gameScreen.getFieldSize() / 2 + (gameScreen.getG().getRatio()-1)*gameScreen.getUnitSize());
            rock.getRockImage().setY(y);
        }
        else if(rock.getCoordY() == 5)
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * rock.getCoordY() - 4 * gameScreen.getUnitSize() - gameScreen.getFieldSize() / 2 + 2*(gameScreen.getG().getRatio()-1)*gameScreen.getUnitSize());
            rock.getRockImage().setY(y);
        }
        else
        {
            y = (int) (gameScreen.getG().getRatio() * gameScreen.getUnitSize() * rock.getCoordY() - 4 * gameScreen.getUnitSize() - gameScreen.getFieldSize() / 2);
            rock.getRockImage().setY(y);
        }

        rock.getRockImage().setHeight(gameScreen.getFieldSize()*gameScreen.getG().getRatio());
        rock.getRockImage().setWidth(gameScreen.getFieldSize());
    }

    /**
     * Ueberprueft ein Spieler einen moeglichen Zug hat
     * @param team
     * @return
     */
    public boolean playerAbleToMove (Team team) {
        for (Unit unit : gameScreen.getUnits()) {
            if (getActualTeam() == unit.getTeam()) {
                if (!checkPossibleMoves(gameScreen.getRolledDiceValue(), unit.getCurrentFieldPosition())){
                    clearPossibleMoves();
                } else {
                    clearPossibleMoves();
                    return true;
                }
            }
        }
        return false;
    }

    public Team getActualTeam()
    {
        return actualPlayer.getTeam();
    }

    /**
     * Überprüft, ob sich ein Stein in der Ablage befindet
     * @return
     */
    public boolean isRockTaken()
    {
        for (Rock r : gameScreen.getRocks())
        {
            if(r.isTaken())
            {
                return true;
            }
        }
        return false;
    }

    public void setDiceRolled()
    {
        diceRolled = true;
        diceTries--;

    }

    public void isPlayerAbleToMove()
    {
        playerAbleToMove = playerAbleToMove(getActualTeam());
    }

    public boolean getPlayerAbleToMove()
    {
        return this.playerAbleToMove;
    }

    public Player getActualPlayer()
    {
        return this.actualPlayer;
    }

    public boolean isDiceRolled() {
        return diceRolled;
    }

    public void setRockPosition(Rock rock, Field field) {
        if (rock.isTaken) {
            System.out.println("rock small");
            rock.setPosition(field);
            setRockImagePosition(rock);
            if(this.mode == Mode.NETWORK) {
                // send rock position // Info [rockId; newPositionId(field)]
                ArrayList<String> info = new ArrayList<String>();
                info.add(String.valueOf(rock.getId()));
                info.add(String.valueOf(field.getID()));
                MessageObject msg = new MessageObject(client.getNickName(), MessageTypeEnum.SetRock, info);
                client.sendMessage(msg);
            }

            rock.setTaken(false);
            rockTaken = false;


        } else if (!rock.isTaken) {
            System.out.println("rock big");
            rock.getRockImage().setX(gameScreen.getUnitSize()*15);
            rock.getRockImage().setY(gameScreen.getUnitSize()/2);
            rock.getRockImage().setWidth(4*gameScreen.getUnitSize());
            rock.getRockImage().setHeight(4*gameScreen.getUnitSize());
            rock.setTaken(true);
            rockTaken = true;
        }

    }

    public boolean isUnitMoved() {
        return unitMoved;
    }

    public void unitInit() {

        //System.out.println("ich mach das jetzt");
        ArrayList<Unit> units = gameScreen.getUnits();
        for (Unit u : gameScreen.getUnits()) {
            u.setPosition(u.getStartPosition());
            setUnitImagePosition(u);
        }
    }

    public void setMalefizClient(MalefizClient client)
    {
        this.client = client;
    }

    public boolean roundActive()
    {
        if (mode == Mode.LOCAL) {
            return true;
        }
        System.out.println("Playername" + actualPlayer.getNickName());

        if (client!=null) {
            System.out.println("Clientname" + client.getNickName());

            if (client.getNickName().equals(actualPlayer.getNickName())) {
                return true;
            }
        }
        return false;
    }

    public void receiveMessage(){
        if(this.mode == Mode.NETWORK) {
            handleMessageController.receiveMessage();
        }
    }

    public MalefizClient getClient() {
        return client;
    }

    public Mode getMode() {
        return mode;
    }
}