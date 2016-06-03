package controllers;


import models.LanguagePack;

/**
 * Created by MCLeite on 03.06.2016.
 */
public class ConnectionController  {
    private MyMalefiz mainClass;
    private LanguagePack lp;


    public ConnectionController(MyMalefiz mainClass, LanguagePack lp) {
        this.mainClass = mainClass;
        this.lp = lp;
    }

    public LanguagePack getLanguagePack() {
        return lp;
    }

    public void switchToMenuScreen() {
        this.mainClass.setMenuScreen();
    }

}
