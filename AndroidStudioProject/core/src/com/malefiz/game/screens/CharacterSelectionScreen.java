package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import controllers.CharacterSelectionController;
import controllers.MyMalefiz;
import models.Avatar;
import models.Grid;
import models.LanguagePack;

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
    private LanguagePack lp;

    private CharacterSelectionController controller;

    private Label head;

    Grid g = new Grid();

    public CharacterSelectionScreen(MyMalefiz mainClass, LanguagePack lp) {
        this.images = new ArrayList<Image>();
        this.controller = new CharacterSelectionController(mainClass, lp);
        this.lp = lp;
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();




        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));

        font = new BitmapFont();
        font.setColor(Color.BLACK);

        head = new Label(lp.getText("choosecharacter"), skin);
        head.setPosition(g.getUnitSize(), 15*g.getUnitSize()*g.getRatio());
        head.setWidth(18*g.getUnitSize());
        head.setHeight(4*g.getUnitSize()*g.getRatio());
        head.setFontScale(5);
        head.setAlignment(Align.center);


        stage.addActor(head);

        List<Avatar> characters = this.controller.getCharacters();
        for(int i= 0; i<characters.size(); i++) {
            skin.add(characters.get(i).getId(), new Texture(characters.get(i).getImageName()));
            Image img = new Image(skin.getDrawable(characters.get(i).getId()));
            img.setX(characters.get(i).getxPos()*g.getUnitSize());
            img.setY(characters.get(i).getyPos()*g.getUnitSize()*g.getRatio());
            img.setWidth(6*g.getUnitSize());
            img.setHeight(6*g.getUnitSize());
            this.images.add(img);
        }

        this.images.get(0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectCharacter(0);
            }
        });

        this.images.get(1).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectCharacter(1);
            }
        });

        this.images.get(2).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectCharacter(2);
            }
        });

        this.images.get(3).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectCharacter(3);
            }
        });

        for(int i =0; i<this.images.size(); i++) {
            stage.addActor(this.images.get(i));
        }

        TextButton backBtn = new TextButton(this.controller.getLanguagePack().getText("cancel"), skin, "default");

        backBtn.setWidth((int)(8.5f*g.getUnitSize()));
        backBtn.setHeight(2* g.getUnitSize()*g.getRatio());
        backBtn.setPosition(g.getUnitSize(), g.getUnitSize());
        backBtn.getLabel().setFontScale(3.0f);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.switchToMenuScreen();
            }
        });


       TextButton selectedBtn = new TextButton(this.controller.getLanguagePack().getText("play"), skin, "default");

        selectedBtn.setWidth((int)(8.5*g.getUnitSize()));
        selectedBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        selectedBtn.setPosition((int)(10.5*g.getUnitSize()), g.getUnitSize());
        selectedBtn.getLabel().setFontScale(3.0f);
        selectedBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.switchToGameScreen();
            }
        });
        stage.addActor(selectedBtn);
        stage.addActor(backBtn);



        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 187 / 255f, 1 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();
        if(this.controller.getSelectedCharacter() != null) {
            for (int i = 0; i < this.controller.getCharacters().size(); i++) {
                Avatar character = this.controller.getCharacters().get(i);
                if (i == this.controller.getSelectedIndex()) {
                    this.images.get(i).setX(character.getxPos()*g.getUnitSize()-g.getUnitSize());
                    this.images.get(i).setY(character.getyPos()*g.getUnitSize()*g.getRatio()-g.getUnitSize());
                    this.images.get(i).setWidth(8*g.getUnitSize());
                    this.images.get(i).setHeight(8*g.getUnitSize());
                } else {
                    this.images.get(i).setX(character.getxPos()*g.getUnitSize());
                    this.images.get(i).setY(character.getyPos()*g.getUnitSize()*g.getRatio());
                    this.images.get(i).setWidth(6*g.getUnitSize());
                    this.images.get(i).setHeight(6*g.getUnitSize());
                }
            }
        }
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        //stage.setDebugAll(true);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){this.controller.switchToMenuScreen();}
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
        return this.controller.getSelectedCharacter();
    }
}
