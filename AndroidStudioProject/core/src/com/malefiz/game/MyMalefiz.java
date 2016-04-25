package com.malefiz.game;

import com.badlogic.gdx.ApplicationAdapter;
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
	MenuScreen ms;
	CharacterScreen cs;
	Screen sc;

	@Override
	public void create () {
		setMenuScreen();
	}

	public void setMenuScreen() {
		ms = new MenuScreen(this);
		sc = ms;
		ms.show();
		setScreen(ms);
	}

	public void setCharacterScreen() {
		cs = new CharacterScreen(this);
		sc.hide();
		sc = cs;
		cs.show();
		setScreen(cs);
	}

	@Override
	public void render () {
		sc.render(0.0001f);
	}
}
