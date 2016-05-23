package models;

/**
 * Created by Dan on 23.05.2016.
 */
public class Player {
    private Team team;
    private int index;

    public Player(Team team, int index)
    {
        this.team = team;
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public Team getTeam()
    {
        return this.team;
    }
}
