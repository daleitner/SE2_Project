package models;

import java.util.ArrayList;
import java.util.HashMap;

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
            languagePack.put("choosenumberofplayers", "Wie viele Spieler?");
            languagePack.put("next", "Weiter");
            languagePack.put("back", "Zurück");
            languagePack.put("player", "Spieler");
            languagePack.put("mplocal", "Multiplayer (Lokal)");
            languagePack.put("mpnetw", "Multiplayer (Netzwerk)");
            languagePack.put("hostgame", "Spiel erstellen");
            languagePack.put("join", "Beitreten");
            languagePack.put("ip", "IP-Adresse des Hosts");
            languagePack.put("nick", "Dein Name");
            languagePack.put("conplayers", "Verbundene Spieler");
            languagePack.put("p1", "Spieler 1");
            languagePack.put("p2", "Spieler 2");

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
            languagePack.put("choosenumberofplayers", "Number of Players?");
            languagePack.put("next", "Next");
            languagePack.put("back", "Back");
            languagePack.put("player", "Player");
            languagePack.put("mplocal", "Multiplayer (local)");
            languagePack.put("mpnetw", "Multiplayer (network)");
            languagePack.put("hostgame", "Host Game");
            languagePack.put("join", "Join");
            languagePack.put("ip", "IP-adress of the host");
            languagePack.put("nick", "Your name");
            languagePack.put("conplayers", "Connected players");
            languagePack.put("p1", "Player 1");
            languagePack.put("p2", "Player 2");

        }
    }

    public String getText(String text)
    {
        return languagePack.get(text);
    }


}
