package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import org.w3c.dom.Text;

import controllers.NumberOfPlayersSelectionController;
import models.Grid;

/**
 * Created by MCLeite on 22.05.2016.
 */
public class NumberOfPlayersSelectionScreen implements Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;
    BitmapFont font;
    int screenHeight;
    int screenWidth;
    Grid g = new Grid();

    private NumberOfPlayersSelectionController controller;

    private Label head;
    private Label numberOfPlayers;
    private TextButton increaseNumberOfPlayersBtn;
    private TextButton decreaseNumberOfPlayersBtn;

    public NumberOfPlayersSelectionScreen(NumberOfPlayersSelectionController controller) {
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

        head = new Label(controller.getLanguagePack().getText("choosenumberofplayers"), skin);
        head.setPosition(g.getUnitSize(), 15*g.getUnitSize()*g.getRatio());
        head.setWidth(18*g.getUnitSize());
        head.setHeight(4*g.getUnitSize()*g.getRatio());
        head.setFontScale(5);
        head.setAlignment(Align.center);
        stage.addActor(head);

        numberOfPlayers = new Label(String.valueOf(controller.getSelectedNumberOfPlayers()), skin);
        numberOfPlayers.setPosition(g.getUnitSize(), 10*g.getUnitSize()*g.getRatio());
        numberOfPlayers.setWidth(18*g.getUnitSize());
        numberOfPlayers.setHeight(4*g.getUnitSize()*g.getRatio());
        numberOfPlayers.setFontScale(5);
        numberOfPlayers.setAlignment(Align.center);
        stage.addActor(numberOfPlayers);

        increaseNumberOfPlayersBtn = new TextButton("+", skin, "default");

        increaseNumberOfPlayersBtn.setWidth((4*g.getUnitSize()));
        increaseNumberOfPlayersBtn.setHeight(2* g.getUnitSize()*g.getRatio());
        increaseNumberOfPlayersBtn.setPosition(12 * g.getUnitSize(), 11 * g.getUnitSize()*g.getRatio());
        increaseNumberOfPlayersBtn.getLabel().setFontScale(3.0f);
        increaseNumberOfPlayersBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.IncreaseNumberOfPlayers();
            }
        });
        stage.addActor(increaseNumberOfPlayersBtn);

        decreaseNumberOfPlayersBtn = new TextButton("-", skin, "default");

        decreaseNumberOfPlayersBtn.setWidth((4*g.getUnitSize()));
        decreaseNumberOfPlayersBtn.setHeight(2* g.getUnitSize()*g.getRatio());
        decreaseNumberOfPlayersBtn.setPosition(4 * g.getUnitSize(), 11 * g.getUnitSize()*g.getRatio());
        decreaseNumberOfPlayersBtn.getLabel().setFontScale(3.0f);
        decreaseNumberOfPlayersBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.DecreaseNumberOfPlayers();
            }
        });
        stage.addActor(decreaseNumberOfPlayersBtn);

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
        stage.addActor(backBtn);

        TextButton selectedBtn = new TextButton(this.controller.getLanguagePack().getText("next"), skin, "default");

        selectedBtn.setWidth((int)(8.5*g.getUnitSize()));
        selectedBtn.setHeight(2*g.getUnitSize()*g.getRatio());
        selectedBtn.setPosition((int)(10.5*g.getUnitSize()), g.getUnitSize());
        selectedBtn.getLabel().setFontScale(3.0f);
        selectedBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.switchToCharacterSelectionScreen();
            }
        });
        stage.addActor(selectedBtn);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 187 / 255f, 1 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();
        numberOfPlayers.setText(String.valueOf(this.controller.getSelectedNumberOfPlayers()));
        if(this.controller.getSelectedNumberOfPlayers() == this.controller.getMaxPlayer()) {
            this.increaseNumberOfPlayersBtn.setTouchable(Touchable.disabled);
        } else {
            this.increaseNumberOfPlayersBtn.setTouchable(Touchable.enabled);
        }
        if(this.controller.getSelectedNumberOfPlayers() == this.controller.getMinPlayer()) {
            this.decreaseNumberOfPlayersBtn.setTouchable(Touchable.disabled);
        } else {
            this.decreaseNumberOfPlayersBtn.setTouchable(Touchable.enabled);
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
}
