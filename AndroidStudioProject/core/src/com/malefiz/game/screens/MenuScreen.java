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
import com.malefiz.game.models.Grid;
import com.malefiz.game.models.LanguagePack;

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
    private LanguagePack lp;
    Texture startBtn;
    Skin skin;

    Grid g = new Grid();

    public MenuScreen(MyMalefiz mz, LanguagePack lp)
    {
        mymalefiz = mz;
        this.lp = lp;
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

        TextButton startBtn = new TextButton(lp.getText("startgame"), skin, "default");

        startBtn.setWidth(18*g.getUnitSize());
        startBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        startBtn.setPosition(g.getUnitSize(), 8*g.getUnitSize()*g.getRatio());
        startBtn.getLabel().setFontScale(3.0f);
        startBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setCharacterSelectionScreen();
            }
        });

        TextButton connectBtn = new TextButton(lp.getText("joingame"), skin, "default");

        connectBtn.setWidth(18*g.getUnitSize());
        connectBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        connectBtn.setPosition(g.getUnitSize(), 11*g.getUnitSize()*g.getRatio()/2);
        connectBtn.getLabel().setFontScale(3.0f);
        connectBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setGameScreen();
            }
        });

        TextButton ruleBtn = new TextButton(lp.getText("rules"), skin, "default");

        ruleBtn.setWidth(18*g.getUnitSize());
        ruleBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        ruleBtn.setPosition(g.getUnitSize(), 3*g.getUnitSize()*g.getRatio());
        ruleBtn.getLabel().setFontScale(3.0f);
        ruleBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setRuleScreen();
            }
        });

        TextButton exitBtn = new TextButton(lp.getText("leavegame"), skin, "default");

        exitBtn.setWidth(18*g.getUnitSize());
        exitBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        exitBtn.setPosition(g.getUnitSize(), g.getUnitSize()*g.getRatio()/2);
        exitBtn.getLabel().setFontScale(3.0f);
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        stage.addActor(connectBtn);
        stage.addActor(exitBtn);
        stage.addActor(ruleBtn);
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
        batch.draw(logo, 2*g.getUnitSize(), 12*g.getUnitSize()*g.getRatio(), 16*g.getUnitSize(), 7*g.getUnitSize()*g.getRatio());
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
