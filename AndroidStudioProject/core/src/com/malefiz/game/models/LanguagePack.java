package com.malefiz.game.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dan on 09.05.2016.
 */
public class LanguagePack {
    HashMap<String, String> languagePack = new HashMap<String, String>();

    public LanguagePack(String lang)
    {
        if(lang.equals("ger"))
        {
            languagePack.put("startgame", "Spiel starten");
            languagePack.put("joingame", "Spiel beitreten");
            languagePack.put("rules","Spielregeln");
            languagePack.put("leavegame","Spiel verlassen");
            languagePack.put("choosecharacter","Waehle deinen\r\nCharakter!");
            languagePack.put("cancel","Abbrechen");
            languagePack.put("play","Spielen");
            languagePack.put("tomenu","Zum Hauptmenü");
            languagePack.put("winner","Gewinner!");
        }
        else if(lang.equals("eng"))
        {
            languagePack.put("startgame", "Start Game");
            languagePack.put("joingame", "Join Game");
            languagePack.put("rules","Rules");
            languagePack.put("leavegame","Leave Game");
            languagePack.put("choosecharacter","Choose your\r\ncharacter!");
            languagePack.put("cancel","Cancel");
            languagePack.put("play","Play");
            languagePack.put("tomenu","Back to menu");
            languagePack.put("winner","Winner!");
        }
    }

    public String getText(String text)
    {
        return languagePack.get(text);
    }


}
