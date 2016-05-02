package com.malefiz.game.models;

/**
 * Created by MCLeite on 25.04.2016.
 */
public class Avatar {
    private String id;
    private boolean isEnabled;
    private String imageName;
    private String disabledImageName;
    private int xPos;
    private int yPos;

    public Avatar(String id, String imageName, String disabledImageName, int xPos, int yPos) {
        this.id = id;
        this.isEnabled = false;
        this.imageName = imageName;
        this.disabledImageName = disabledImageName;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getImageName() { return imageName; }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDisabledImageName() {
        return disabledImageName;
    }

    public void setDisabledImageName(String disabledImageName) {
        this.disabledImageName = disabledImageName;
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
}
