package com.malefiz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by MCLeite on 25.04.2016.
 */
public class CharacterSelectionScreen implements Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;

    private Image img_red = null;
    private Image img_blue = null;
    private Image img_green = null;
    private Image img_yellow = null;
    private MyMalefiz mainClass = null;
    public CharacterSelectionScreen(MyMalefiz mainClass) {
        this.mainClass = mainClass;
        show();
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();
        skin.add("avatar_red", new Texture("avatar_rot.png"));
        skin.add("avatar_blue", new Texture("avatar_blau.png"));
        skin.add("avatar_green", new Texture("avatar_gruen.png"));
        skin.add("avatar_yellow", new Texture("avatar_gelb.png"));

        float img_width = 400.0f;
        float img_height = 400.0f;

        this.img_red = new Image(skin.getDrawable("avatar_red"));
        this.img_red.setX(10);
        this.img_red.setY(10);
        this.img_red.setWidth(img_width);
        this.img_red.setHeight(img_height);
        stage.addActor(this.img_red);

        this.img_blue = new Image(skin.getDrawable("avatar_blue"));
        this.img_blue.setX(410);
        this.img_blue.setY(10);
        this.img_blue.setWidth(img_width);
        this.img_blue.setHeight(img_height);
        stage.addActor(this.img_blue);

        this.img_green = new Image(skin.getDrawable("avatar_green"));
        this.img_green.setX(10);
        this.img_green.setY(410);
        this.img_green.setWidth(img_width);
        this.img_green.setHeight(img_height);
        stage.addActor(this.img_green);

        this.img_yellow = new Image(skin.getDrawable("avatar_yellow"));
        this.img_yellow.setX(410);
        this.img_yellow.setY(410);
        this.img_yellow.setWidth(img_width);
        this.img_yellow.setHeight(img_height);
        stage.addActor(this.img_yellow);
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
