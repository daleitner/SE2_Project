package com.malefiz.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.malefiz.game.screens.CharacterScreen;
import com.malefiz.game.screens.MenuScreen;
import com.badlogic.gdx.Game;

public class MyMalefiz extends Game {
	private StartScreen startScreen;
	private GameScreen gameScreen;
	private CharacterSelectionScreen characterSelectionScreen;

	@Override
	public void create() {
		//AssetLoader.load(); //load all assets!!
		this.setStartScreen();
	}

	public void setStartScreen()
	{
		this.startScreen = new StartScreen(this);
		setScreen(this.startScreen);
	}

	public void setGameScreen()
	{
		this.gameScreen = new GameScreen(this);
		setScreen(this.gameScreen);
	}

	public void setCharacterSelectionScreen()
	{
		this.characterSelectionScreen = new CharacterSelectionScreen(this);
		setScreen(this.characterSelectionScreen);
	}
}
