package models;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;

public class Config {
    HashMap<String, String> languagePack = new HashMap<String, String>();

    public Config(String lang)
    {
        if(lang.equals("ger"))
        {
            languagePack.put("startgame", "Spiel starten");
            languagePack.put("joingame", "Spiel beitreten");
            languagePack.put("rules","Spielregeln");
            languagePack.put("leavegame","Spiel verlassen");
            languagePack.put("choosecharacter",",\r\nwähle deinen\r\nCharakter!");
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
            languagePack.put("ip", "IP-Adresse des Hosts:");
            languagePack.put("nick", "Dein Name:");
            languagePack.put("conplayers", "Verbundene Spieler");
            languagePack.put("p1", "Spieler 1");
            languagePack.put("p2", "Spieler 2");
            languagePack.put("rolldice", "Bitte würfeln!");
            languagePack.put("turn", ", du bist dran!");
            languagePack.put("newname", "Du musst noch deinen Namen eingeben!");
            languagePack.put("emptyname", "Das Namensfeld darf nicht leer sein!");

        }
        else if(lang.equals("eng"))
        {
            languagePack.put("startgame", "Start Game");
            languagePack.put("joingame", "Join Game");
            languagePack.put("rules","Rules");
            languagePack.put("leavegame","Leave Game");
            languagePack.put("choosecharacter",",\r\nchoose your\r\ncharacter!");
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
            languagePack.put("ip", "IP-adress of the host:");
            languagePack.put("nick", "Your name:");
            languagePack.put("conplayers", "Connected players");
            languagePack.put("p1", "Player 1");
            languagePack.put("p2", "Player 2");
            languagePack.put("rolldice", "Please roll the dice!");
            languagePack.put("turn", ", it's your turn!");
            languagePack.put("newname", "You should enter your name!");
            languagePack.put("emptyname", "The name field must not be empty!");
        }
        else if(lang.equals("ital"))
        {
            languagePack.put("startgame", "Avviare il gioco");
            languagePack.put("joingame", "Partecipare al gioco");
            languagePack.put("rules","Regole");
            languagePack.put("leavegame","Lasciare il gioco");
            languagePack.put("choosecharacter",",\r\nscegli il\r\ncarattere!");
            languagePack.put("cancel","Annullare");
            languagePack.put("play","Giocare");
            languagePack.put("tomenu","Menu principale");
            languagePack.put("winner","Vincitore!");
            languagePack.put("choosenumberofplayers", "Quanti giocatori?");
            languagePack.put("next", "Continuare");
            languagePack.put("back", "Dietro");
            languagePack.put("player", "Giocatore");
            languagePack.put("mplocal", "Multiplayer (locale)");
            languagePack.put("mpnetw", "Multiplayer (network)");
            languagePack.put("hostgame", "Creare un gioco");
            languagePack.put("join", "Aderire");
            languagePack.put("ip", "Indirizzo IP:");
            languagePack.put("nick", "Tuo nome:");
            languagePack.put("conplayers", "Giocatori collegati");
            languagePack.put("p1", "Giocatore 1");
            languagePack.put("p2", "Giocatore 2");
            languagePack.put("rolldice", "Tira i dadi!");
            languagePack.put("turn", ", tocca a te!");
            languagePack.put("newname", "Digita il nome!");
            languagePack.put("emptyname", "Il campo nome non può essere vuoto!");
        }
    }

    public String getText(String text)
    {
        return languagePack.get(text);
    }

    public float getScreenScaleFactor()
    {
        return Gdx.graphics.getWidth()/720f;
    }

}
