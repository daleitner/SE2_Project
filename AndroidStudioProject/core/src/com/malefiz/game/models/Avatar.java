package models;

public class Avatar {
    private String id;
    private String imageName;
    private String disabledImageName;
    private int xPos;
    private int yPos;
    private int index;

    /**
     * Konstruktor
     * @param id
     *  Name des Avatars
     * @param imageName
     *  Dateiname des Standard-Avatarbildes
     * @param disabledImageName
     *  Dateiname des deaktivierten Avatarbildes
     * @param xPos
     *  x-Koordinate des Avatars im Grid
     * @param yPos
     *  y-Koordinate des Avatars im Grid
     * @param idx
     *  Index entsprechend des zugehörigen Teams
     */
    public Avatar(String id, String imageName, String disabledImageName, int xPos, int yPos, int idx) {
        this.id = id;
        this.imageName = imageName;
        this.disabledImageName = disabledImageName;
        this.xPos = xPos;
        this.yPos = yPos;
        this.index = idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() { return imageName; }

    public String getDisabledImageName() {
        return disabledImageName;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getIndex()
    {
        return index;
    }
}
