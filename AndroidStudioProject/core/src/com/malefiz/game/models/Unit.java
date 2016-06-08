package models;


import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Unit {
    /** id **/
    private int id;
    /** related to team of player **/
    public final Team team;
    /** position current + coords **/
    public int coordX;
    public int coordY;
    public Field currentFieldPosition;

    public Image unitImage;
    public Field startPosition;


    public Unit(Team team, Field startPos, int id) {
        this.startPosition = startPos;
        this.currentFieldPosition = startPos;
        setPosition(currentFieldPosition);
        this.team = team;
        this.id = id;
    }

    /**
     * Versetzt diese Unit auf eine neue Position (Field)
     * @param newPosition
     */
    public void setPosition(Field newPosition){
        this.coordX = newPosition.getCoordX();
        this.coordY = newPosition.getCoordY();
        this.currentFieldPosition = newPosition;
    }

    public Field getStartPosition()
    {
        return this.startPosition;
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
