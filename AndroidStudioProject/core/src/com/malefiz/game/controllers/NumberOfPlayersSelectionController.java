package controllers;

import models.LanguagePack;
import models.Mode;

/**
 * Created by MCLeite on 22.05.2016.
 */
public class NumberOfPlayersSelectionController {
    private MyMalefiz mainClass;
    private LanguagePack lp;
    private int minPlayer = 2;
    private int maxPlayer = 4;
    private int selectedNumberOfPlayers = 4;
    public NumberOfPlayersSelectionController(MyMalefiz mainClass, LanguagePack lp) {
        this.mainClass = mainClass;
        this.lp = lp;
    }

    public LanguagePack getLanguagePack() {
        return lp;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getSelectedNumberOfPlayers() {
        return selectedNumberOfPlayers;
    }

    public void IncreaseNumberOfPlayers() {
        if(this.selectedNumberOfPlayers < this.maxPlayer)
            this.selectedNumberOfPlayers += 1;
    }

    public void DecreaseNumberOfPlayers() {
        if(this.selectedNumberOfPlayers > this.minPlayer) {
            this.selectedNumberOfPlayers -= 1;
        }
    }

    public void switchToMenuScreen() {
        this.mainClass.setMenuScreen();
    }

    public void switchToCharacterSelectionScreen() {
        this.mainClass.setCharacterSelectionScreen(Mode.LOCAL, this.selectedNumberOfPlayers);
    }
}
