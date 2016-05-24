package models;

/**
 * Created by Dan on 23.05.2016.
 */
public class Player {
    private Team team;
    private int index;
    private Avatar avatar;

    public Player(Team team, int index, Avatar avatar)
    {
        this.team = team;
        this.index = index;
        this.avatar = avatar;
    }

    public int getIndex()
    {
        return index;
    }

    public Team getTeam()
    {
        return this.team;
    }

    public Avatar getAvatar()
    {
        return this.avatar;
    }
}
