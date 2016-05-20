package controllers;

import com.badlogic.gdx.Game;
import models.LanguagePack;
import models.Mode;
import models.Team;
import screens.CharacterSelectionScreen;
import screens.GameScreen;
import screens.LanguageScreen;
import screens.MenuScreen;
import screens.RuleScreen;
import screens.WinnerScreen;

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

	public void setGameScreen(Mode m)
	{
		this.gameScreen = new GameScreen(this, this.characterSelectionScreen.getSelectedAvatar(), lp, m);
		setScreen(this.gameScreen);
	}

	public void setCharacterSelectionScreen(Mode m)
	{
		this.characterSelectionScreen = new CharacterSelectionScreen(this, lp, m);
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
