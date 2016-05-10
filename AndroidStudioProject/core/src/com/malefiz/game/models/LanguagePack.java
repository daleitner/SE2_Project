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
            languagePack.put("choosecharacter","Waehle deinen Charakter");
            languagePack.put("cancel","Abbrechen");
            languagePack.put("play","Spielen");
        }
        else if(lang.equals("eng"))
        {
            languagePack.put("startgame", "Start Game");
            languagePack.put("joingame", "Join Game");
            languagePack.put("rules","Rules");
            languagePack.put("leavegame","Leave Game");
            languagePack.put("choosecharacter","Choose your character");
            languagePack.put("cancel","Cancel");
            languagePack.put("play","Play");
        }
    }

    public String getText(String text)
    {
        return languagePack.get(text);
    }


}
