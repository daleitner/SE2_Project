package controllers;

import models.Avatar;
import models.LanguagePack;

import java.util.ArrayList;
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

    public CharacterSelectionController(MyMalefiz mainClass, LanguagePack lp) {
        this.mainClass = mainClass;
        this.lp = lp;
        this.characters = new ArrayList<Avatar>();
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

    public void selectCharacter(int index) {
        this.selectedCharacter = this.characters.get(index);
        this.selectedIndex = index;
    }

    public LanguagePack getLanguagePack() {
        return this.lp;
    }

    public void switchToMenuScreen() {
        this.mainClass.setMenuScreen();
    }

    public void switchToGameScreen() {
        this.mainClass.setGameScreen();
    }
}
