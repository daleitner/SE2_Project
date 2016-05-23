package models;


import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by friedemannzindler on 30/04/16.
 */
public class Unit {
    /** id **/
    private int id;
    /** related to team of player **/
    public final Team team;
    /** position current **/
    public int coordX;
    public int coordY;

    public Image unitImage;
    public Field currentFieldPosition;
    public Field startPosition;


    public Unit(Team team, Field startPos, int id) {
        this.startPosition = startPos;
        this.currentFieldPosition = startPos;
        setPosition(currentFieldPosition);
        this.team = team;
        this.id = id;
    }

    public void setPosition(Field newPosition){
        this.coordX = newPosition.getCoordX();
        this.coordY = newPosition.getCoordY();
        this.currentFieldPosition = newPosition;
    }


    public int getUnitCoordX() {
        return coordX;
    }

    public int getUnitCoordY() {
        return coordY;
    }

    public Team getTeam() {
        return team;
    }

    public Image getUnitImage() {
        return unitImage;
    }

    public void setUnitImage(Image unitImage) {
        this.unitImage = unitImage;
    }

    public int getId() {
        return this.id;
    }

    public Field getCurrentFieldPosition() {
        return this.currentFieldPosition;
    }




}
