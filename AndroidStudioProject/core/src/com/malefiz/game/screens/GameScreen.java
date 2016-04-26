package com.malefiz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.malefiz.game.MyMalefiz;
import com.malefiz.game.models.Avatar;

/**
 * Created by MCLeite on 25.04.2016.
 */
public class GameScreen implements Screen {

    private MyMalefiz mainClass = null;

    Skin skin;
    Stage stage;
    SpriteBatch batch;

    int screenHeight;
    int screenWidth;
    private Avatar selectedAvatar;
    private Image img_field_red = null;
    private Image img_field_black = null;

    public GameScreen(MyMalefiz mainClass, Avatar selectedAvatar) {
        this.selectedAvatar = selectedAvatar;
        this.mainClass = mainClass;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        skin.add("field_red", new Texture("feld_rot.png"));
        skin.add("field_black", new Texture("feld_schwarz.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(34 / 255f, 187 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
