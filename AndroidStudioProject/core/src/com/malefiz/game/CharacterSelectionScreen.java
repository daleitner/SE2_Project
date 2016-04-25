package com.malefiz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by MCLeite on 25.04.2016.
 */
public class CharacterSelectionScreen implements Screen {
    private MyMalefiz mainClass = null;
    public CharacterSelectionScreen(MyMalefiz mainClass) {
        this.mainClass = mainClass;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 187 / 255f, 1 / 255f, 1);

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
