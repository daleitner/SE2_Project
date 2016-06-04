package controllers;


import models.LanguagePack;

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
