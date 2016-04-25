package com.malefiz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.malefiz.game.MyMalefiz;

/**
 * Created by Dan on 25.04.2016.
 */
public class CharacterScreen implements Screen {

    MyMalefiz mymalefiz;
    SpriteBatch batch;
    Texture logo;
    Texture backgroundTexture;
    int screenHeight;
    int screenWidth;

    public CharacterScreen(MyMalefiz mz)
    {
        mymalefiz = mz;
        create();
    }

    public void create()
    {
        batch = new SpriteBatch();
        logo = new Texture("malefiz-logo.png");
        backgroundTexture = new Texture("bg-startscreen.jpg");
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        renderBackground();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void renderBackground()
    {
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
