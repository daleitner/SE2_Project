package com.malefiz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.malefiz.game.MyMalefiz;
import com.malefiz.game.models.Avatar;

import java.util.List;

/**
 * Created by MCLeite on 25.04.2016.
 */
public class CharacterSelectionScreen implements Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;

    int screenHeight;
    int screenWidth;

    private Image img_red = null;
    private Image img_blue = null;
    private Image img_green = null;
    private Image img_yellow = null;

    private MyMalefiz mainClass = null;

    private List<Avatar> characters;

    public CharacterSelectionScreen(MyMalefiz mainClass) {
        this.mainClass = mainClass;
        show();
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        Gdx.input.setInputProcessor(stage);

        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        skin.add("avatar_red", new Texture("avatar_rot.png"));
        skin.add("avatar_blue", new Texture("avatar_blau.png"));
        skin.add("avatar_green", new Texture("avatar_gruen.png"));
        skin.add("avatar_yellow", new Texture("avatar_gelb.png"));

        float img_width = 400.0f;
        float img_height = 400.0f;

        this.img_red = new Image(skin.getDrawable("avatar_red"));
        this.img_red.setX(150);
        this.img_red.setY(910);
        this.img_red.setWidth(img_width);
        this.img_red.setHeight(img_height);
        stage.addActor(this.img_red);

        this.img_blue = new Image(skin.getDrawable("avatar_blue"));
        this.img_blue.setX(550);
        this.img_blue.setY(910);
        this.img_blue.setWidth(img_width);
        this.img_blue.setHeight(img_height);
        stage.addActor(this.img_blue);

        this.img_green = new Image(skin.getDrawable("avatar_green"));
        this.img_green.setX(150);
        this.img_green.setY(510);
        this.img_green.setWidth(img_width);
        this.img_green.setHeight(img_height);
        stage.addActor(this.img_green);

        this.img_yellow = new Image(skin.getDrawable("avatar_yellow"));
        this.img_yellow.setX(550);
        this.img_yellow.setY(510);
        this.img_yellow.setWidth(img_width);
        this.img_yellow.setHeight(img_height);
        stage.addActor(this.img_yellow);

        TextButton backBtn = new TextButton("Abbrechen", skin, "default");

        backBtn.setWidth(400);
        backBtn.setHeight(120);
        backBtn.setPosition(20, 20);
        backBtn.getLabel().setFontScale(5.0f);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mainClass.setMenuScreen();
            }
        });
        stage.addActor(backBtn);

       TextButton selectedBtn = new TextButton("Spielen", skin, "default");

        selectedBtn.setWidth(400);
        selectedBtn.setHeight(120);
        selectedBtn.setPosition(screenWidth - 420, 20);
        selectedBtn.getLabel().setFontScale(5.0f);
        selectedBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mainClass.setGameScreen();
            }
        });
        stage.addActor(selectedBtn);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 187 / 255f, 1 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        stage.setDebugAll(true);
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
}
