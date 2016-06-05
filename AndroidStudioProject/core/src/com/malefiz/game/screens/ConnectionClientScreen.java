package screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

import network.MalefizClient;

public class ConnectionClientScreen implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    private Label labelMessage;
    private TextButton button;
    private TextArea textIPAddress;
    private TextArea textMessage;

    private MalefizClient client;

    // Pick a resolution that is 16:9 but not unreadibly small
    public float screenHeight = 960;
    public float screenWidth = 540;


    @Override
    public void show() {
        batch = new SpriteBatch();

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        // Load our UI skin from file.  Once again, I used the files included in the tests.
        // Make sure default.fnt, default.png, uiskin.[atlas/json/png] are all added to your assets
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        stage = new Stage();
        // Wire the stage to receive input, as we are using Scene2d in this example
        Gdx.input.setInputProcessor(stage);


        // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example
        // one per NIC, one per active wireless and the loopback
        // In this case we only care about IPv4 address ( x.x.x.x format )

        // Vertical group groups contents vertically.  I suppose that was probably pretty obvious
        VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();//.space(2).pad(5).fill();//.space(3).reverse().fill();
        // Set the bounds of the group to the entire virtual display
        vg.setBounds(0, 0, screenWidth, screenHeight);

        // Create our controls
        labelMessage = new Label("Hello world",skin);
        button = new TextButton("Send message",skin);
        textIPAddress = new TextArea("",skin);
        textMessage = new TextArea("",skin);

        // Add them to scene
        vg.addActor(labelMessage);
        vg.addActor(textIPAddress);
        vg.addActor(textMessage);
        vg.addActor(button);

        // Add scene to stage
        stage.addActor(vg);

        // Wire up a click listener to our button
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                if(client == null)
                    client = new MalefizClient(textIPAddress.getText());

                // When the button is clicked, get the message text or create a default string value
                String textToSend = new String();
                if(textMessage.getText().length() == 0)
                    textToSend = "Doesn't say much but likes clicking buttons\n";
                else
                    textToSend = textMessage.getText() + ("\n"); // Brute for a newline so readline gets a line

                client.sendMessage(textToSend);
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(client != null) {
            String msg = client.getReceivedMessage();
            if (!msg.isEmpty()) {
                labelMessage.setText(msg);
                client.clearReceivedMessage();
            }
        }
        stage.draw();
        batch.end();
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
}