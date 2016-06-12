package models;

public class Player {
    private Team team;
    private int index;
    private Avatar avatar;
    private String nickName;

    public Player(String nickName) {
        this.nickName = nickName;
    }

    public Player(String nickName, Team team, int index, Avatar avatar)
    {
        this.nickName = nickName;
        this.team = team;
        this.index = index;
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
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

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
