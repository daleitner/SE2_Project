package models;

import java.util.ArrayList;

public enum Team {
    RED(0, Color.RED),
    YELLOW(1, Color.YELLOW),
    GREEN(2, Color.BLUE),
    BLUE(3, Color.GREEN);

    /** the id of that team */
    public final int id;

    /** the color of that team */
    public final Color color;

    Team(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    /** get team by id **/
    public Team getById(int id) {
        switch (id) {
            case 0:
                return RED;
            case 1:
                return YELLOW;
            case 2:
                return GREEN;
            case 3:
                return BLUE;
            default:
                throw new RuntimeException("wrong team id");
        }
    }

    public int getId()
    {
        return this.id;
    }
}
