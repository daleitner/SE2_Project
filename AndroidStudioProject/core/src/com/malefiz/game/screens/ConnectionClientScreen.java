package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import controllers.ConnectionClientController;
import models.Grid;
import models.Config;

public class ConnectionClientScreen implements Screen {
    SpriteBatch batch;
    Texture backgroundTexture;
    int screenHeight;
    int screenWidth;
    Stage stage;
    private ConnectionClientController controller;
    Skin skin;
    Grid g = new Grid();
    private Config lp;

    private Label labelJoinedPlayers;
    private TextField textIPAddress;
    private TextField textNickName;
    private TextButton playButton;
    private TextButton cancelButton;

    public ConnectionClientScreen(ConnectionClientController controller, Config lp)
    {
        this.controller = controller;
        this.lp = lp;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("bg-startscreen.jpg");
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));

        Label labelIPAddresses = new Label(lp.getText("ip"),skin);
        labelIPAddresses.setHeight(g.getUnitSize());
        labelIPAddresses.setWidth(g.getUnitSize()*18);
        labelIPAddresses.setPosition(g.getUnitSize(), g.getUnitSize()*18*g.getRatio());
        labelIPAddresses.setFontScale(2f*lp.getScreenScaleFactor());
        stage.addActor(labelIPAddresses);

        this.textIPAddress = new TextField("192.168.1.1", skin);
        this.textIPAddress.setAlignment(1);
        this.textIPAddress.setHeight(g.getUnitSize()*2);
        this.textIPAddress.setWidth(g.getUnitSize()*18);
        this.textIPAddress.setPosition(g.getUnitSize(), g.getUnitSize()*16.5f*g.getRatio());
        this.textIPAddress.scaleBy(2);
        stage.addActor(textIPAddress);

        Label labelNickName = new Label(lp.getText("nick"), skin);
        labelNickName.setHeight(g.getUnitSize());
        labelNickName.setWidth(g.getUnitSize()*18);
        labelNickName.setPosition(g.getUnitSize(), g.getUnitSize()*15*g.getRatio());
        labelNickName.setFontScale(2f*lp.getScreenScaleFactor());
        stage.addActor(labelNickName);

        this.textNickName = new TextField(lp.getText("p2"), skin);
        this.textNickName.setAlignment(1);
        this.textNickName.setHeight(g.getUnitSize()*2);
        this.textNickName.setWidth(g.getUnitSize()*18);
        this.textNickName.setPosition(g.getUnitSize(), g.getUnitSize()*13.5f*g.getRatio());
        stage.addActor(textNickName);

        Label labelPlayers = new Label(lp.getText("conplayers")+":", skin);
        labelPlayers.setHeight(g.getUnitSize());
        labelPlayers.setWidth(g.getUnitSize()*18);
        labelPlayers.setPosition(g.getUnitSize(), g.getUnitSize()*11*g.getRatio());
        labelPlayers.setFontScale(2f*lp.getScreenScaleFactor());
        stage.addActor(labelPlayers);

        this.labelJoinedPlayers = new Label(this.controller.getPlayersString(), skin);
        labelJoinedPlayers.setHeight(g.getUnitSize());
        labelJoinedPlayers.setWidth(g.getUnitSize()*18);
        labelJoinedPlayers.setPosition(g.getUnitSize(), g.getUnitSize()*10*g.getRatio());
        labelJoinedPlayers.setFontScale(1.5f*lp.getScreenScaleFactor());
        stage.addActor(labelJoinedPlayers);

        this.cancelButton =  new TextButton(this.controller.getLanguagePack().getText("cancel"), skin, "default");

        this.cancelButton.setWidth((int)(8.5f*g.getUnitSize()));
        this.cancelButton.setHeight(2* g.getUnitSize()*g.getRatio());
        this.cancelButton.setPosition(g.getUnitSize(), g.getUnitSize());
        this.cancelButton.getLabel().setFontScale(2.0f*lp.getScreenScaleFactor());
        this.cancelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.switchToNetworkMenuScreen();
            }
        });
        stage.addActor(this.cancelButton);

        this.playButton = new TextButton("Beitreten", skin, "default");

        this.playButton.setWidth((int)(8.5*g.getUnitSize()));
        this.playButton.setHeight(2*g.getUnitSize()*g.getRatio());
        this.playButton.setPosition((int)(10.5*g.getUnitSize()), g.getUnitSize());
        this.playButton.getLabel().setFontScale(2.0f*lp.getScreenScaleFactor());
        this.playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.connect();
                playButton.setTouchable(Touchable.disabled);
            }
        });
        stage.addActor(this.playButton);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
        this.controller.receiveMessage();
        this.controller.setServerIPAddress(this.textIPAddress.getText());
        this.controller.setNickName(this.textNickName.getText());
        this.labelJoinedPlayers.setText(this.controller.getPlayersString());
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){controller.switchToNetworkMenuScreen();}
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
}