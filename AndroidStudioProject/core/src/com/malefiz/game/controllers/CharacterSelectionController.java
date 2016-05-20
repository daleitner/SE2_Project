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
    private Avatar selectedCharacter;
    private HashMap<Integer, Avatar> selectedCharacters;
    private Mode mode;

    public CharacterSelectionController(MyMalefiz mainClass, LanguagePack lp, Mode m) {
        this.mainClass = mainClass;
        this.lp = lp;
        this.mode = m;
        this.characters = new ArrayList<Avatar>();
        this.selectedCharacters = new HashMap<Integer, Avatar>();
        this.characters.add(new Avatar("avatar_red", "avatar_rot.png", "avatar_rot_disabled.png", 3, 5));
        this.characters.add(new Avatar("avatar_blue", "avatar_blau.png", "avatar_blau_disabled.png", 11, 5));
        this.characters.add(new Avatar("avatar_yellow", "avatar_gelb.png", "avatar_gelb_disabled.png", 3, 10));
        this.characters.add(new Avatar("avatar_green", "avatar_gruen.png", "avatar_gruen_disabled.png", 11, 10));
    }

    public List<Avatar> getCharacters() {
        return this.characters;
    }

    public void setCharacters(List<Avatar> characters) {
        this.characters = characters;
    }

    public Avatar getSelectedCharacter() {
        return this.selectedCharacter;
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    /**
     * Speichert den ausgewählten Charakter aus der Charakterauswahl
     * @param index     Index des Charakters
     */
    public void selectSingleCharacter(int index) {
        this.selectedCharacter = this.characters.get(index);
        this.selectedIndex = index;
    }

    /**
     * Fügt einen Charakter zur Auswahl hinzu
     * @param index     Index des Charakters
     */
    public void selectCharacter(int index)
    {
        selectedCharacters.put(index, characters.get(index));
    }

    /**
     * Löscht einen Charakter aus der Auswahl
     * @param index     Index des Charakters
     */
    public void deselectCharacter(int index)
    {
        selectedCharacters.remove(index);
    }

    /**
     * Überprüft, ob der Charakter schon ausgewählt wurde
     * @param index
     * @return
     */
    public boolean isCharacterSelected(int index)
    {
        return selectedCharacters.containsKey(index);
    }

    public void handleCharacter(int index)
    {
        if(mode == Mode.NETWORK)
        {
            selectSingleCharacter(index);
        }
        else
        {
            if(isCharacterSelected(index))
            {
                deselectCharacter(index);
            }
            else
            {
                selectCharacter(index);
            }
        }
    }

    public boolean isCharacterMapEmpty()
    {
        return selectedCharacters.isEmpty();
    }

    public LanguagePack getLanguagePack() {
        return this.lp;
    }

    public void switchToMenuScreen() {
        this.mainClass.setMenuScreen();
    }

    public void switchToGameScreen(Mode m) {
        this.mainClass.setGameScreen(m);
    }
}
