package com.malefiz.game;

import com.badlogic.gdx.Game;
import com.malefiz.game.models.LanguagePack;
import com.malefiz.game.models.Team;
import com.malefiz.game.screens.CharacterSelectionScreen;
import com.malefiz.game.screens.GameScreen;
import com.malefiz.game.screens.LanguageScreen;
import com.malefiz.game.screens.MenuScreen;
import com.malefiz.game.screens.RuleScreen;
import com.malefiz.game.screens.WinnerScreen;

public class MyMalefiz extends Game {
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private CharacterSelectionScreen characterSelectionScreen;
	private RuleScreen ruleScreen;
	private LanguageScreen languageScreen;
	private WinnerScreen winnerScreen;

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

	public void setGameScreen()
	{
		this.gameScreen = new GameScreen(this, this.characterSelectionScreen.getSelectedAvatar(), lp);
		setScreen(this.gameScreen);
	}

	public void setCharacterSelectionScreen()
	{
		this.characterSelectionScreen = new CharacterSelectionScreen(this, lp);
		setScreen(this.characterSelectionScreen);
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
