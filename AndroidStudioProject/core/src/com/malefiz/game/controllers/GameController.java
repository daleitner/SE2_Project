package controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import models.Field;
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

    private GameController(){}

    public static GameController getInstance()
    {
        if(instance == null)
        {
            instance = new GameController();
        }
        return instance;
    }

    public void init()
    {

    }

    /* set scaling and image position of unit */
    /* muss noch auf die units angepasst werden */
    // scaling muss noch ueberarbeitet werden momentan nur von field uebernommen
    public void setUnitImagePosition (Unit unit) {

        unit.getUnitImage().setX(gameScreen.getUnitSize() * unit.getUnitCoordX() - gameScreen.getFieldSize() / 2);

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

        unit.getUnitImage().setHeight(gameScreen.getFieldSize()*gameScreen.getG().getRatio()*2);
        unit.getUnitImage().setWidth(gameScreen.getFieldSize()*2);
    }

    public void moveUnit (Field nextPosition, Unit unit) {
        // todo check if next position is empty
        //if (checkPossibleMoves(rolledDiceNumber, unit.getCurrentFieldPosition(), nextPosition)) {
        if (gameScreen.getPossibleMoves().contains(nextPosition)) {
            unit.setPosition(nextPosition);
            setUnitImagePosition(unit);
            clearPossibleMoves();

            if (nextPosition.getRockId() > 0) {
                for (Rock r : gameScreen.getRocks()) {
                    if (r.getId() == nextPosition.getRockId()) {
                        // draw taken rock
                        int id = nextPosition.getRockId();
                        nextPosition.setRockId(0);
                        r.getRockImage().setX(gameScreen.getUnitSize()*15);
                        r.getRockImage().setY(gameScreen.getUnitSize()/2);
                        r.getRockImage().setWidth(4*gameScreen.getUnitSize());
                        r.getRockImage().setHeight(4*gameScreen.getUnitSize());
                        r.setTaken(true);
                        gameScreen.getStage().addActor(r.getRockImage());

                        r.getRockImage().addListener(new ClickListener(){
                            public void clicked(InputEvent ev, float x, float y)
                            {
                                System.out.println("I AM LISTING");

                            }
                        });

                        System.out.println("ROck taken new image");
                    }
                }
            }

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

                if (visitedFields.get(j).getRockId() == 0) {
                    visitedFields.addAll(getNeighbourFieldsOfField(visitedFields.get(j)));
                } else {
                    visitedTemp.add(visitedFields.get(j));
                }
            }

            for (Field f : visitedFields) {
                if (f.getRockId() != 0) {
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
            if (team == unit.getTeam()) {
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


}
