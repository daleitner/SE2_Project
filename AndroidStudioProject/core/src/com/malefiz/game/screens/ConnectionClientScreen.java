package screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import controllers.ConnectionClientController;
import controllers.ConnectionController;
import models.Grid;
import network.MalefizClient;

public class ConnectionClientScreen implements Screen {
    SpriteBatch batch;
    Texture backgroundTexture;
    int screenHeight;
    int screenWidth;
    Stage stage;
    private ConnectionClientController controller;
    Skin skin;
    Grid g = new Grid();

    private Label labelJoinedPlayers;
    private TextArea textIPAddress;
    private TextButton playButton;
    private TextButton cancelButton;

    public ConnectionClientScreen(ConnectionClientController controller)
    {
        this.controller = controller;
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
        Label labelIPAddresses = new Label("IP-Adresse:",skin);
        /*this.labelIPAddresses.setPosition(g.getUnitSize(), 15*g.getUnitSize()*g.getRatio());
        this.labelIPAddresses.setWidth(18*g.getUnitSize());
        this.labelIPAddresses.setHeight(4*g.getUnitSize()*g.getRatio());
        this.labelIPAddresses.setFontScale(5);
        this.labelIPAddresses.setAlignment(Align.center);*/
        //stage.addActor(this.labelIPAddresses);
        vg.addActor(labelIPAddresses);
        this.textIPAddress = new TextArea("192.168.1.1", skin);
        vg.addActor(this.textIPAddress);
        this.labelJoinedPlayers = new Label(this.controller.getPlayersString(),skin);
        /*this.labelJoinedPlayers.setPosition(g.getUnitSize(), 15*g.getUnitSize()*g.getRatio());
        this.labelJoinedPlayers.setWidth(18*g.getUnitSize());
        this.labelJoinedPlayers.setHeight(4*g.getUnitSize()*g.getRatio());
        this.labelJoinedPlayers.setFontScale(5);
        this.labelJoinedPlayers.setAlignment(Align.center);*/
        //stage.addActor(this.labelJoinedPlayers);
        vg.addActor(this.labelJoinedPlayers);
        stage.addActor(vg);

        this.cancelButton =  new TextButton(this.controller.getLanguagePack().getText("cancel"), skin, "default");

        this.cancelButton.setWidth((int)(8.5f*g.getUnitSize()));
        this.cancelButton.setHeight(2* g.getUnitSize()*g.getRatio());
        this.cancelButton.setPosition(g.getUnitSize(), g.getUnitSize());
        this.cancelButton.getLabel().setFontScale(3.0f);
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
        this.playButton.getLabel().setFontScale(3.0f);
        this.playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                controller.connect();
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
        this.controller.setServerIPAddress(this.textIPAddress.getText());
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