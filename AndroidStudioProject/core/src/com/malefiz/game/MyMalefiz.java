package com.malefiz.game;

import com.badlogic.gdx.Game;
import com.malefiz.game.screens.CharacterSelectionScreen;
import com.malefiz.game.screens.GameScreen;
import com.malefiz.game.screens.MenuScreen;
import com.malefiz.game.screens.RuleScreen;

public class MyMalefiz extends Game {
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private CharacterSelectionScreen characterSelectionScreen;
	private RuleScreen ruleScreen;

	@Override
	public void create() {
		//AssetLoader.load(); //load all assets!!
		this.setMenuScreen();
		this.characterSelectionScreen = new CharacterSelectionScreen(this);
	}

	public void setMenuScreen()
	{
		this.menuScreen = new MenuScreen(this);
		setScreen(this.menuScreen);
	}

	public void setGameScreen()
	{
		this.gameScreen = new GameScreen(this, this.characterSelectionScreen.getSelectedAvatar());
		setScreen(this.gameScreen);
	}

	public void setCharacterSelectionScreen()
	{

		setScreen(this.characterSelectionScreen);
	}

	public void setRuleScreen()
	{
		this.ruleScreen = new RuleScreen(this);
		setScreen(this.ruleScreen);
	}
}
