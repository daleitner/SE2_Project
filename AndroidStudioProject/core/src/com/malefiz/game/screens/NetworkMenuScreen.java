package screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import controllers.MyMalefiz;
import models.Color;
import models.Grid;
import models.LanguagePack;
import models.Mode;
import models.Team;

public class NetworkMenuScreen implements Screen {
    SpriteBatch batch;
    Texture logo;
    Texture backgroundTexture;
    int screenHeight;
    int screenWidth;
    Stage stage;
    private MyMalefiz mymalefiz;
    private LanguagePack lp;
    Skin skin;

    Grid g = new Grid();

    public NetworkMenuScreen(MyMalefiz mz, LanguagePack lp)
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

        TextButton startBtn = new TextButton("Spiel erstellen", skin, "default");

        startBtn.setWidth(18*g.getUnitSize());
        startBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        startBtn.setPosition(g.getUnitSize(), 11*g.getUnitSize()*g.getRatio()/2);
        startBtn.getLabel().setFontScale(3.0f);
        startBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setConnectionScreen();
            }
        });

        TextButton connectBtn = new TextButton("Spiel beitreten", skin, "default");

        connectBtn.setWidth(18*g.getUnitSize());
        connectBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        connectBtn.setPosition(g.getUnitSize(), 3*g.getUnitSize()*g.getRatio());
        connectBtn.getLabel().setFontScale(3.0f);
        connectBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setConnectionClientScreen();
            }
        });

        TextButton backBtn = new TextButton("Zurueck", skin, "default");

        backBtn.setWidth(18*g.getUnitSize());
        backBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        backBtn.setPosition(g.getUnitSize(), g.getUnitSize()*g.getRatio()/2);
        backBtn.getLabel().setFontScale(3.0f);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setMenuScreen();
            }
        });

        stage.addActor(connectBtn);
        stage.addActor(startBtn);
        stage.addActor(backBtn);

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
        batch.draw(logo, 2*g.getUnitSize(), 12*g.getUnitSize()*g.getRatio(), 16*g.getUnitSize(), 7*g.getUnitSize()*g.getRatio());
        batch.end();
        stage.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){mymalefiz.setMenuScreen();}
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
