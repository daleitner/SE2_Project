package com.malefiz.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.malefiz.game.MyMalefiz;

/**
 * Created by Dan on 25.04.2016.
 */
public class MenuScreen implements Screen {
    SpriteBatch batch;
    Texture logo;
    Texture backgroundTexture;
    int screenHeight;
    int screenWidth;
    Stage stage;
    private MyMalefiz mymalefiz;
    Texture startBtn;
    Skin skin;

    public MenuScreen(MyMalefiz mz)
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

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));

        TextButton startBtn = new TextButton("Spiel starten", skin, "default");

        startBtn.setWidth(screenWidth/2);
        startBtn.setHeight(screenWidth/8);
        startBtn.setPosition(screenWidth/4, 8*screenHeight/24);
        startBtn.getLabel().setFontScale(5.0f);
        startBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setCharacterSelectionScreen();
            }
        });

        TextButton connectBtn = new TextButton("Spiel beitreten", skin, "default");

        connectBtn.setWidth(screenWidth/2);
        connectBtn.setHeight(screenWidth/8);
        connectBtn.setPosition(screenWidth/4, 6*screenHeight/24);
        connectBtn.getLabel().setFontScale(5.0f);
        connectBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setGameScreen();
            }
        });

        TextButton exitBtn = new TextButton("Spiel verlassen", skin, "default");

        exitBtn.setWidth(screenWidth/2);
        exitBtn.setHeight(screenWidth/8);
        exitBtn.setPosition(screenWidth/4, 4*screenHeight/24);
        exitBtn.getLabel().setFontScale(5.0f);
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        stage.addActor(connectBtn);
        stage.addActor(exitBtn);
        stage.addActor(startBtn);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        renderBackground();
        batch.draw(logo, 100, 2*screenHeight/3, Gdx.graphics.getWidth()-200, screenHeight/3-screenHeight/9);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);

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
        stage.dispose();
    }

    public void renderBackground()
    {
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
