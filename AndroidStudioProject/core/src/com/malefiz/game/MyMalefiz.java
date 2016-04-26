package com.malefiz.game;

import com.badlogic.gdx.Game;
import com.malefiz.game.screens.CharacterSelectionScreen;
import com.malefiz.game.screens.GameScreen;
import com.malefiz.game.screens.MenuScreen;

public class MyMalefiz extends Game {
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private CharacterSelectionScreen characterSelectionScreen;

	@Override
	public void create() {
		//AssetLoader.load(); //load all assets!!
		this.setMenuScreen();
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
		this.characterSelectionScreen = new CharacterSelectionScreen(this);
		setScreen(this.characterSelectionScreen);
	}
}
