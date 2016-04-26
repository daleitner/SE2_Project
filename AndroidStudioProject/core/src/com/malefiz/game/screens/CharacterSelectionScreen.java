package com.malefiz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MCLeite on 25.04.2016.
 */
public class CharacterSelectionScreen implements Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;
    BitmapFont font;

    int screenHeight;
    int screenWidth;
    float img_width = 400.0f;
    float img_height = 400.0f;
    float selected_width = 450.0f;
    float selected_height = 450.0f;
    private List<Image> images = null;
    private MyMalefiz mainClass = null;

    private List<Avatar> characters;
    private Avatar selectedCharacter = null;

    public CharacterSelectionScreen(MyMalefiz mainClass) {
        this.mainClass = mainClass;
        this.images = new ArrayList<Image>();
        this.characters = new ArrayList<Avatar>();
        this.characters.add(new Avatar("avatar_red", "avatar_rot.png", "avatar_rot_disabled.png", 150, 960));
        this.characters.add(new Avatar("avatar_blue", "avatar_blau.png", "avatar_blau_disabled.png", 600, 960));
        this.characters.add(new Avatar("avatar_yellow", "avatar_gelb.png", "avatar_gelb_disabled.png", 150, 510));
        this.characters.add(new Avatar("avatar_green", "avatar_gruen.png", "avatar_gruen_disabled.png", 600, 510));
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

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(5.0f);

        for(int i= 0; i<this.characters.size(); i++) {
            skin.add(this.characters.get(i).getId(), new Texture(this.characters.get(i).getImageName()));
            Image img = new Image(skin.getDrawable(this.characters.get(i).getId()));
            img.setX(this.characters.get(i).getxPos());
            img.setY(this.characters.get(i).getyPos());
            img.setWidth(img_width);
            img.setHeight(img_height);
            this.images.add(img);
        }

        this.images.get(0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedCharacter = characters.get(0);
            }
        });

        this.images.get(1).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedCharacter = characters.get(1);
            }
        });

        this.images.get(2).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedCharacter = characters.get(2);
            }
        });

        this.images.get(3).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedCharacter = characters.get(3);
            }
        });

        for(int i =0; i<this.images.size(); i++) {
            stage.addActor(this.images.get(i));
        }

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
        batch.begin();
        font.draw(batch, "Wähle einen Charakter", 150, 1600);
        batch.end();
        if(this.selectedCharacter != null) {
            for (int i = 0; i < this.characters.size(); i++) {
                if (this.selectedCharacter == this.characters.get(i)) {
                    this.images.get(i).setWidth(this.selected_width);
                    this.images.get(i).setHeight(this.selected_height);
                } else {
                    this.images.get(i).setWidth(this.img_width);
                    this.images.get(i).setHeight(this.img_height);
                }
            }
        }
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
        font.dispose();
        batch.dispose();
    }

    public Avatar getSelectedAvatar() {
        return this.selectedCharacter;
    }
}
