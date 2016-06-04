package models;

import com.badlogic.gdx.Gdx;

public class Grid {
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeigth = Gdx.graphics.getHeight();
    float ratio = 1f*screenHeigth/screenWidth;

    int unitSize = (int)screenWidth/20;
    int verticalUnits = screenHeigth/unitSize;

    public Grid()
    {

    }

    public float getRatio(){return ratio;}

    public int getScreenWidth(){return screenWidth;}

    public int getScreenHeigth(){return screenHeigth;}

    public int getUnitSize(){return unitSize;}

}
