package com.malefiz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.malefiz.game.MyMalefiz;
import com.malefiz.game.models.Grid;
import com.malefiz.game.models.LanguagePack;
import com.malefiz.game.models.Team;

import java.nio.charset.Charset;

import sun.nio.cs.ISO_8859_2;
import sun.nio.cs.ext.ISO_8859_11;

/**
 * Created by Dan on 18.05.2016.
 */
public class WinnerScreen implements Screen{
    SpriteBatch batch;
    Texture logo;
    Texture backgroundTexture;
    int screenHeight;
    int screenWidth;
    Stage stage;
    private MyMalefiz mymalefiz;
    private LanguagePack lp;
    Skin skin;
    Team winnerTeam;
    private Label head;

    Grid g = new Grid();

    public WinnerScreen(MyMalefiz mz, LanguagePack lp, Team t)
    {
        mymalefiz = mz;
        this.lp = lp;
        winnerTeam = t;
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
        skin.add("winning_blue", new Texture("winning_blue.png"));
        skin.add("winning_red", new Texture("winning_red.png"));
        skin.add("winning_yellow", new Texture("winning_yellow.png"));
        skin.add("winning_green", new Texture("winning_green.png"));

        head = new Label(lp.getText("winner"), skin);
        head.setPosition(g.getUnitSize(), 15*g.getUnitSize()*g.getRatio());
        head.setWidth(18*g.getUnitSize());
        head.setHeight(4*g.getUnitSize()*g.getRatio());
        head.setFontScale(5);
        head.setAlignment(Align.center);



        TextButton backToMenu = new TextButton(lp.getText("tomenu"), skin, "default");

        backToMenu.setWidth(18*g.getUnitSize());
        backToMenu.setHeight(2*g.getUnitSize()*g.getRatio());
        backToMenu.setPosition(g.getUnitSize(), g.getUnitSize()*g.getRatio()/2);
        backToMenu.getLabel().setFontScale(3.0f);
        backToMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setMenuScreen();
            }
        });

        Image winnerImage;
        switch (winnerTeam.getById(winnerTeam.id))
        {
            case RED:
                winnerImage = new Image(skin.getDrawable("winning_red"));
                break;
            case BLUE:
                winnerImage = new Image(skin.getDrawable("winning_blue"));
                break;
            case GREEN:
                winnerImage = new Image(skin.getDrawable("winning_green"));
                break;
            case YELLOW:
                winnerImage = new Image(skin.getDrawable("winning_yellow"));
                break;
            default: throw new IllegalArgumentException("Kein gültiges Gewinnerteam!");
        }


        winnerImage.setWidth(18*g.getUnitSize());
        winnerImage.setHeight(8*g.getUnitSize()*g.getRatio());
        winnerImage.setPosition(g.getUnitSize(), 5*g.getUnitSize()*g.getRatio());

        stage.addActor(backToMenu);
        stage.addActor(winnerImage);
        stage.addActor(head);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        renderBackground();
        batch.end();
        stage.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){Gdx.app.exit();}

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
