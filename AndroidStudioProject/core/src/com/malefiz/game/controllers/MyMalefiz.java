package controllers;

import com.badlogic.gdx.Game;

import java.util.HashMap;

import models.Avatar;
import models.LanguagePack;
import models.Mode;
import models.Team;
import screens.CharacterSelectionScreen;
import screens.GameScreen;
import screens.LanguageScreen;
import screens.MenuScreen;
import screens.NumberOfPlayersSelectionScreen;
import screens.RuleScreen;
import screens.WinnerScreen;

public class MyMalefiz extends Game {
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private RuleScreen ruleScreen;
	private LanguageScreen languageScreen;
	private WinnerScreen winnerScreen;
	private CharacterSelectionController characterSelectionController;
	private NumberOfPlayersSelectionController numberOfPlayersSelectionController;

	private LanguagePack lp = new LanguagePack("ger");

	@Override
	public void create() {
		//AssetLoader.load(); //load all assets!!
		this.setLanguageScreen();
	}

	public void setMenuScreen()
	{
		this.menuScreen = new MenuScreen(this, lp);
		setScreen(this.menuScreen);
	}

	public void setGameScreen(Mode m, HashMap<Integer, Avatar> selectedCharacters)
	{
		this.gameScreen = new GameScreen(this, selectedCharacters.get(0), lp, m);
		setScreen(this.gameScreen);
	}

	public void setNumberOfPlayersSelectionScreen() {
		this.numberOfPlayersSelectionController = new NumberOfPlayersSelectionController(this, lp);
		setScreen(new NumberOfPlayersSelectionScreen(this.numberOfPlayersSelectionController));
	}

	public void setCharacterSelectionScreen(Mode m, int numberOfCharacters)
	{
		this. characterSelectionController = characterSelectionController.getInstance();
		this.characterSelectionController.init(this, lp, m, numberOfCharacters);
		setScreen(new CharacterSelectionScreen(this.characterSelectionController));
	}

	public void setRuleScreen()
	{
		this.ruleScreen = new RuleScreen(this, lp);
		setScreen(this.ruleScreen);
	}

	public void setLanguageScreen()
	{
		this.languageScreen = new LanguageScreen(this, lp);
		setScreen(this.languageScreen);
	}

	public void setWinnerScreen(Team t)
	{
		this.winnerScreen = new WinnerScreen(this, lp, t);
		setScreen(this.winnerScreen);
	}

	public void setLanguagePack(LanguagePack lang)
	{
		this.lp = lang;
	}


}
