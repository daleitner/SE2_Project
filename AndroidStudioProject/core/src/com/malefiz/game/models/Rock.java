package com.malefiz.game.models;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by worm on 12.05.16.
 */
public class Rock {
    /** id **/
    private int id;
    /** position current **/
    public int coordX;
    public int coordY;

    public Image rockImage;

    public Field startPosition;
    public Field currentFieldPosition;

    public Rock(int id, Field startPos) {
        this.startPosition = startPos;
        this.currentFieldPosition = startPos;
        setPosition(currentFieldPosition);
        this.id = id;
    }


    public void setPosition(Field newPosition){
        this.coordX = newPosition.getCoordX();
        this.coordY = newPosition.getCoordY();
        this.currentFieldPosition = newPosition;
    }



    public Image getRockImage() {
        return rockImage;
    }

    public void setRockImage(Image rockImage) {
        this.rockImage = rockImage;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getCoordX() {
        return coordX;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
