package controllers;

import models.Avatar;
import models.LanguagePack;
import models.Mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MCLeite on 17.05.2016.
 */
public class CharacterSelectionController {
    private MyMalefiz mainClass;
    private LanguagePack lp;
    private int selectedIndex = -1;
    private List<Avatar> characters;
    private HashMap<Integer, Integer> selectedCharacterIndicess;
    private Mode mode;
    private int numberOfPlayers;
    private int actualPlayer;

    public CharacterSelectionController(MyMalefiz mainClass, LanguagePack lp, Mode m, int numberOfPlayers) {
        this.mainClass = mainClass;
        this.lp = lp;
        this.mode = m;
        this.numberOfPlayers = numberOfPlayers;
        this.actualPlayer = 1;
        this.characters = new ArrayList<Avatar>();
        this.selectedCharacterIndicess = new HashMap<Integer, Integer>();
        this.characters.add(new Avatar("avatar_red", "avatar_rot.png", "avatar_rot_disabled.png", 3, 5));
        this.characters.add(new Avatar("avatar_blue", "avatar_blau.png", "avatar_blau_disabled.png", 11, 5));
        this.characters.add(new Avatar("avatar_yellow", "avatar_gelb.png", "avatar_gelb_disabled.png", 3, 10));
        this.characters.add(new Avatar("avatar_green", "avatar_gruen.png", "avatar_gruen_disabled.png", 11, 10));
    }

    public List<Avatar> getCharacters() {
        return this.characters;
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public HashMap<Integer, Avatar> getSelectedCharacters() {
        HashMap<Integer, Avatar> ret = new HashMap<Integer, Avatar>();
        for(int i = 0; i<this.selectedCharacterIndicess.size(); i++) {
            ret.put(i, this.characters.get(this.selectedCharacterIndicess.get(i)));
        }
        return ret;
    }

    public Mode getMode() {
        return mode;
    }

    /**
     * Fügt einen Charakter zur Auswahl hinzu. Existiert bereits ein Eintrag für den aktuellen Spieler,
     * so wird der Eintrag entfernt und erneut eingefügt.
     * @param index     Index des Charakters
     */
    public void selectCharacter(int index) {
        if(this.selectedCharacterIndicess.containsKey(this.actualPlayer-1))
            this.selectedCharacterIndicess.remove(this.actualPlayer-1);
        selectedCharacterIndicess.put(this.actualPlayer-1, index);
    }

    public void deselectCharacter() {
        if(this.selectedCharacterIndicess.containsKey(this.actualPlayer-1))
            this.selectedCharacterIndicess.remove(this.actualPlayer-1);
    }

    /**
     * Überprüft, ob der Charakter schon ausgewählt wurde
     * @param index
     * @return
     */
    public boolean isCharacterSelected(int index)
    {
        return selectedCharacterIndicess.containsKey(this.actualPlayer-1) && selectedCharacterIndicess.get(this.actualPlayer-1) == index;
    }

    public void handleCharacter(int index) {
        if(mode == Mode.NETWORK) {
        }
        else {
            if(isCharacterEnabled(index)) {
                if(!isCharacterSelected(index))
                    selectCharacter(index);
                //else
                //    deselectCharacter();
            }
        }
    }

    public boolean isCharacterEnabled(int index) {
        for(int i = 0; i<this.selectedCharacterIndicess.size(); i++){
            if(i != this.actualPlayer-1 && this.selectedCharacterIndicess.get(i) == index)
                return false;
        }
        return true;
    }

    public String getHeaderText() {
        return lp.getText("player") + " " + this.actualPlayer + " " + lp.getText("choosecharacter");
    }

    public String getNextButtonText() {
        if(this.actualPlayer < this.numberOfPlayers)
            return lp.getText("next");
        return lp.getText("play");
    }

    public boolean canExecutePlayButton() {
        return this.actualPlayer < this.numberOfPlayers || this.selectedCharacterIndicess.size() == this.numberOfPlayers;
    }

    public LanguagePack getLanguagePack() {
        return this.lp;
    }

    public void switchToPreviousScreen() {
        if(this.actualPlayer > 1)
            this.actualPlayer -= 1;
        else
            this.mainClass.setNumberOfPlayersSelectionScreen();
    }

    public void switchToNextScreen() {
        if(this.actualPlayer < this.numberOfPlayers)
            this.actualPlayer += 1;
        else
            this.mainClass.setGameScreen(this.mode, getSelectedCharacters());
    }


}
