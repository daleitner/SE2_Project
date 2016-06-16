package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;

import controllers.CharacterSelectionController;
import controllers.MyMalefiz;
import models.Grid;
import models.LanguagePack;

public class LanguageScreen implements Screen{
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

    public LanguageScreen(MyMalefiz mz, LanguagePack lp)
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
        skin.add("german", new Texture("flag_germany.png"));
        skin.add("english", new Texture("flag_uk.png"));
        skin.add("italiano", new Texture("flag_italy.png"));

        TextButton startBtn = new TextButton("Deutsch", skin, "default");

        startBtn.setWidth(12*g.getUnitSize());
        startBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        startBtn.setPosition(7*g.getUnitSize(), 8*g.getUnitSize()*g.getRatio());
        startBtn.getLabel().setFontScale(3.0f);
        startBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setLanguagePack(new LanguagePack("ger"));
                mymalefiz.setMenuScreen();
            }
        });

        TextButton connectBtn = new TextButton("English", skin, "default");

        connectBtn.setWidth(12*g.getUnitSize());
        connectBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        connectBtn.setPosition(7*g.getUnitSize(), 11*g.getUnitSize()*g.getRatio()/2);
        connectBtn.getLabel().setFontScale(3.0f);
        connectBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setLanguagePack(new LanguagePack("eng"));
                mymalefiz.setMenuScreen();
            }
        });

        TextButton ruleBtn = new TextButton("Italiano", skin, "default");

        ruleBtn.setWidth(12*g.getUnitSize());
        ruleBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        ruleBtn.setPosition(7*g.getUnitSize(), 3*g.getUnitSize()*g.getRatio());
        ruleBtn.getLabel().setFontScale(3.0f);
        ruleBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mymalefiz.setLanguagePack(new LanguagePack("ger"));
                mymalefiz.setMenuScreen();
            }
        });

        TextButton exitBtn = new TextButton("Exit", skin, "default");

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


        /**
         * Hinzufügen der Flaggen
         */
        Image gerFlag = new Image(skin.getDrawable("german"));
        gerFlag.setWidth(5*g.getUnitSize());
        gerFlag.setHeight(2*g.getUnitSize()*g.getRatio());
        gerFlag.setPosition(g.getUnitSize(), 8*g.getUnitSize()*g.getRatio());

        Image ukFlag = new Image(skin.getDrawable("english"));
        ukFlag.setWidth(5*g.getUnitSize());
        ukFlag.setHeight(2*g.getUnitSize()*g.getRatio());
        ukFlag.setPosition(g.getUnitSize(), 11*g.getUnitSize()*g.getRatio()/2);

        Image italFlag = new Image(skin.getDrawable("italiano"));
        italFlag.setWidth(5*g.getUnitSize());
        italFlag.setHeight(2*g.getUnitSize()*g.getRatio());
        italFlag.setPosition(g.getUnitSize(), 3*g.getUnitSize()*g.getRatio());

        stage.addActor(connectBtn);
        stage.addActor(exitBtn);
        stage.addActor(ruleBtn);
        stage.addActor(startBtn);
        stage.addActor(gerFlag);
        stage.addActor(ukFlag);
        stage.addActor(italFlag);

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
