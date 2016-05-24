package controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import models.Avatar;
import models.Field;
import models.Player;
import models.Rock;
import models.Team;
import models.Unit;
import screens.GameScreen;

/**
 * Created by Dan on 20.05.2016.
 */
public class GameController {
    private static GameController instance;
    GameScreen gameScreen;
    HashMap<Integer, Player> players = new HashMap<Integer, Player>();
    Player actualPlayer;
    private int diceTries = 3;
    private boolean diceRolled;
    private boolean unitMoved;
    private boolean playerAbleToMove;
    private boolean rockTaken;


    private GameController(){}

    /**
     * Liefert die Instanz des GameControllers zurück
     * @return GameController-Instanz
     */
    public static GameController getInstance()
    {
        if(instance == null)
        {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Initialisiert die erste Runde
     */
    public void init()
    {
        actualPlayer = players.get(0);
        reset();
    }

    /**
     * GameScreen pingt den Controller an, um Fortschritt zu überprüfen
     */
    public void check()
    {
        if(((diceRolled && unitMoved) || (!playerAbleToMove && diceTries <= 0)) && !rockTaken)
        {
            System.out.println("DiceRolled: " + diceRolled + " unitMoved: " + unitMoved + " PlayerAbleToMove: " + playerAbleToMove + " diceTries: " + diceTries + " Dicevalue: " + gameScreen.getRolledDiceValue());
            gameScreen.deleteDiceDisplay();
            getNextPlayer();
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
    private void getNextPlayer()
    {

        int idx = actualPlayer.getIndex();
        reset();
        actualPlayer =  players.get((idx+1)%players.size());
        System.out.println("get next player team = " + actualPlayer.getTeam());
        diceTries = 3;
        gameScreen.drawAvatar();
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
    }

    /* set scaling and image position of unit */
    /* muss noch auf die units angepasst werden */
    // scaling muss noch ueberarbeitet werden momentan nur von field uebernommen
    public void setUnitImagePosition (Unit unit) {

        unit.getUnitImage().setX(gameScreen.getUnitSize() * unit.getUnitCoordX() - unit.getUnitImage().getImageWidth()/2);

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

        unit.getUnitImage().setHeight(gameScreen.getFieldSize()*gameScreen.getG().getRatio()*1.5f);
        unit.getUnitImage().setWidth(gameScreen.getFieldSize()*2);
    }

    public void moveUnit (Field nextPosition, Unit unit) {
        // todo check if next position is empty
        //if (checkPossibleMoves(rolledDiceNumber, unit.getCurrentFieldPosition(), nextPosition)) {
        if (gameScreen.getPossibleMoves().contains(nextPosition)) {
            Unit u = nextPosition.getUnit();
            Rock r = nextPosition.getRock();

            if (r != null)
            {
                setRockPosition(r, null);
                nextPosition.setRock(null);
                gameScreen.setSelectedRock(null);
            }

            if(u != null)
            {
                u.setPosition(u.getStartPosition());
                setUnitImagePosition(u);
            }
            unit.currentFieldPosition.setUnit(null);

            unit.setPosition(nextPosition);
            setUnitImagePosition(unit);
            nextPosition.setUnit(unit);
            clearPossibleMoves();
            unitMoved = true;

            gameScreen.setSelectedUnit(null);
        }

    }

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
            setFieldPosScal(f, 1);
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

    public void clearPossibleMoves () {
        if (gameScreen.getPossibleMoves() != null) {
            for (Field f : gameScreen.getPossibleMoves()) {
                setFieldPosScal(f, 0);
            }
        }
        gameScreen.setPossibleMoves(null);
    }

    // 0 for standard field size; 1 for selected size
    public void setFieldPosScal(Field field, int selected) {
        int size;
        if (selected == 1) {
            size = gameScreen.getFieldSize() * 2;
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

    public void setGameScreenInstance(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

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

    public void setSelectedPlayers(HashMap<Integer, Avatar> players)
    {
        for(int i = 0; i < players.size(); i++)
        {
            System.out.println(Team.BLUE.getById(players.get(i).getIndex())+ "  i" + i);
            this.players.put(i, new Player(Team.BLUE.getById(players.get(i).getIndex()), i, players.get(i)));

        }
    }

    public Team getActualTeam()
    {
        return actualPlayer.getTeam();
    }

    /**
     * Überprüft, ob sich ein Stein in der Ablge befindet
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
}
