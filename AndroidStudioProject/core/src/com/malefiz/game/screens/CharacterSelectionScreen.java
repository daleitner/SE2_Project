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
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
import models.Mode;

import java.util.ArrayList;
import java.util.List;

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

    private CharacterSelectionController controller;

    private Label head;
    private TextButton nextBtn;

    Grid g = new Grid();

    public CharacterSelectionScreen(CharacterSelectionController controller) {
        this.images = new ArrayList<Image>();
        this.controller = controller;
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

        head = new Label(controller.getHeaderText(), skin);
        head.setPosition(g.getUnitSize(), 15*g.getUnitSize()*g.getRatio());
        head.setWidth(18*g.getUnitSize());
        head.setHeight(4*g.getUnitSize()*g.getRatio());
        head.setFontScale(5);
        head.setAlignment(Align.center);


        stage.addActor(head);

        List<Avatar> characters = this.controller.getCharacters();
        for(int i= 0; i<characters.size(); i++) {
            skin.add(characters.get(i).getId(), new Texture(characters.get(i).getImageName()));
            skin.add(characters.get(i).getId() + "_disabled", new Texture(characters.get(i).getDisabledImageName()));
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
                controller.handleCharacter(0);
            }
        });

        this.images.get(1).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleCharacter(1);
            }
        });

        this.images.get(2).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleCharacter(2);
            }
        });

        this.images.get(3).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleCharacter(3);
            }
        });

        for(int i =0; i<this.images.size(); i++) {
            stage.addActor(this.images.get(i));
        }

        TextButton backBtn = new TextButton(this.controller.getLanguagePack().getText("back"), skin, "default");

        backBtn.setWidth((int)(8.5f*g.getUnitSize()));
        backBtn.setHeight(2* g.getUnitSize()*g.getRatio());
        backBtn.setPosition(g.getUnitSize(), g.getUnitSize());
        backBtn.getLabel().setFontScale(3.0f);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.switchToPreviousScreen();
            }
        });


        nextBtn = new TextButton(this.controller.getNextButtonText(), skin, "default");

        nextBtn.setWidth((int)(8.5*g.getUnitSize()));
        nextBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        nextBtn.setPosition((int)(10.5*g.getUnitSize()), g.getUnitSize());
        nextBtn.getLabel().setFontScale(3.0f);
        nextBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                    controller.switchToNextScreen();
            }
        });
        stage.addActor(nextBtn);
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

        this.head.setText(this.controller.getHeaderText());
        this.nextBtn.setText(this.controller.getNextButtonText());
        if(this.controller.canExecutePlayButton())
            this.nextBtn.setTouchable(Touchable.enabled);
        else
            this.nextBtn.setTouchable(Touchable.disabled);

        //if(this.controller.getSelectedCharacter() != null || !this.controller.isCharacterMapEmpty()) {
          /*  if(this.controller.getMode() == Mode.NETWORK)
            {
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
            else
            {*/
                for (int i = 0; i < this.controller.getCharacters().size(); i++) {
                    Avatar character = this.controller.getCharacters().get(i);
                    if(controller.isCharacterEnabled(i)) {
                        this.images.get(i).setDrawable(skin.getDrawable(this.controller.getCharacters().get(i).getId()));
                        if (controller.isCharacterSelected(i)) {
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
                    } else {
                        this.images.get(i).setDrawable(skin.getDrawable(this.controller.getCharacters().get(i).getId() + "_disabled"));
                        this.images.get(i).setX(character.getxPos()*g.getUnitSize());
                        this.images.get(i).setY(character.getyPos()*g.getUnitSize()*g.getRatio());
                        this.images.get(i).setWidth(6*g.getUnitSize());
                        this.images.get(i).setHeight(6*g.getUnitSize());
                    }

                }
           // }
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){this.controller.switchToPreviousScreen();}
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
}
