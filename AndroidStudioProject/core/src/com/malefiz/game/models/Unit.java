package com.malefiz.game.models;

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
    public boolean isSelected;


    public Unit(Team team, Field startPos) {
        setPosition(startPos);
        this.team = team;
    }

    public void setPosition(Field position){
        this.coordX = position.getCoordX();
        this.coordY = position.getCoordY();
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
    /** TODO */
    /** method for moving the unit */

    /** TODO */
    /** reset to start position */

    /** TODO */
    /** selection set selected true */


}
