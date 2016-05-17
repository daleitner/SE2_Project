package com.malefiz.game;

import com.badlogic.gdx.Game;
import com.malefiz.game.models.LanguagePack;
import com.malefiz.game.screens.CharacterSelectionScreen;
import com.malefiz.game.screens.GameScreen;
import com.malefiz.game.screens.MenuScreen;
import com.malefiz.game.screens.RuleScreen;

public class MyMalefiz extends Game {
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private CharacterSelectionScreen characterSelectionScreen;
	private RuleScreen ruleScreen;

	private LanguagePack lp;

	@Override
	public void create() {
		//AssetLoader.load(); //load all assets!!
		this.lp = new LanguagePack("ger");

		this.setMenuScreen();
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
}
