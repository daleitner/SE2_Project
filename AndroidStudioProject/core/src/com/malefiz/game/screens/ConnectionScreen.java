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
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import controllers.ConnectionController;
import interfaces.ActionResolver;
import models.Grid;
import models.Config;

public class ConnectionScreen implements Screen {
    SpriteBatch batch;
    Texture backgroundTexture;
    int screenHeight;
    int screenWidth;
    Stage stage;
    private ConnectionController controller;
    Skin skin;
    Grid g = new Grid();
    private Config lp;
    private ActionResolver ar;

    private Label labelIPAddresses;
    private Label labelJoinedPlayers;
    private Label labelName;
    private TextField textName;
    private TextButton addButton;
    private TextButton playButton;
    private TextButton cancelButton;

    public ConnectionScreen(ConnectionController controller, Config lp, ActionResolver ar)
    {
        this.controller = controller;
        this.lp = lp;
        this.ar = ar;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("bg-startscreen.jpg");
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();//.space(2).pad(5).fill();//.space(3).reverse().fill();
        // Set the bounds of the group to the entire virtual display
        vg.setBounds(0, screenHeight/2, screenWidth, screenHeight/2);
        String ipAddress = this.controller.getIpAddresses();
        this.labelIPAddresses = new Label(lp.getText("ip")+" "+ipAddress,skin);
        labelIPAddresses.setHeight(g.getUnitSize());
        labelIPAddresses.setWidth(g.getUnitSize()*18);
        labelIPAddresses.setPosition(g.getUnitSize(), g.getUnitSize()*18*g.getRatio());
        labelIPAddresses.setFontScale(1.5f*lp.getScreenScaleFactor());
        stage.addActor(this.labelIPAddresses);


        this.labelName = new Label(lp.getText("nick"), skin);
        labelName.setHeight(g.getUnitSize());
        labelName.setWidth(g.getUnitSize()*18);
        labelName.setPosition(g.getUnitSize(), g.getUnitSize()*15*g.getRatio());
        labelName.setFontScale(2f*lp.getScreenScaleFactor());
        stage.addActor(labelName);

        this.textName = new TextField(lp.getText("p1"), skin);
        textName.setHeight(g.getUnitSize()*g.getRatio());
        textName.setWidth(g.getUnitSize()*8.5f);
        textName.setPosition(g.getUnitSize(), g.getUnitSize()*g.getRatio()*13.5f);
        textName.setAlignment(1);
        stage.addActor(textName);

        this.addButton =  new TextButton(lp.getText("join"), skin, "default");
        this.addButton.setWidth(8.5f*g.getUnitSize());
        this.addButton.setHeight(g.getUnitSize()*g.getRatio());
        this.addButton.setPosition(10.5f*g.getUnitSize(), g.getUnitSize()*g.getRatio()*13.5f);
        this.addButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(textName.getText().equals(lp.getText("p1")))
                {
                    ar.showToast(lp.getText("newname"));
                }
                else if(textName.getText().isEmpty())
                {
                    ar.showToast(lp.getText("emptyname"));
                }
                else {
                    controller.addClient(textName.getText());
                    addButton.setTouchable(Touchable.disabled);
                }
            }
        });
        stage.addActor(addButton);

        Label labelPlayers = new Label(lp.getText("conplayers")+":", skin);
        labelPlayers.setHeight(g.getUnitSize());
        labelPlayers.setWidth(g.getUnitSize()*18);
        labelPlayers.setPosition(g.getUnitSize(), g.getUnitSize()*11*g.getRatio());
        labelPlayers.setFontScale(2f*lp.getScreenScaleFactor());
        stage.addActor(labelPlayers);

        this.labelJoinedPlayers = new Label(this.controller.getPlayersString(),skin);
        labelJoinedPlayers.setHeight(g.getUnitSize()*6*g.getRatio());
        labelJoinedPlayers.setWidth(g.getUnitSize()*18);
        labelJoinedPlayers.setPosition(g.getUnitSize(), g.getUnitSize()*4*g.getRatio());
        labelJoinedPlayers.setFontScale(1.5f*lp.getScreenScaleFactor());
        labelJoinedPlayers.setAlignment(Align.topLeft);
        stage.addActor(this.labelJoinedPlayers);


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

        this.playButton = new TextButton(this.controller.getLanguagePack().getText("next"), skin, "default");

        this.playButton.setWidth((int)(8.5*g.getUnitSize()));
        this.playButton.setHeight(2*g.getUnitSize()*g.getRatio());
        this.playButton.setPosition((int)(10.5*g.getUnitSize()), g.getUnitSize());
        this.playButton.getLabel().setFontScale(2.0f*lp.getScreenScaleFactor());
        this.playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.switchToCharacterSelectionScreen();
            }
        });
        stage.addActor(this.playButton);

        this.controller.startWaitingForClients();
        Gdx.input.setInputProcessor(stage);

        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();

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