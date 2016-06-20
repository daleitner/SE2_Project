package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import controllers.MyMalefiz;
import models.Grid;
import models.Config;

public class RuleScreen implements Screen{

    SpriteBatch batch;
    Texture logo;
    Texture backgroundTexture;
    int screenHeight;
    int screenWidth;
    Stage stage;
    private MyMalefiz mymalefiz;
    private Config lp;
    Texture startBtn;
    Skin skin;
    int indexRule = 1;
    Texture ruleDisplay;
    Image arrowLeft = new Image(new Sprite(new Texture(Gdx.files.internal("arrow_left.png"))));
    Image arrowRight = new Image(new Sprite(new Texture(Gdx.files.internal("arrow_right.png"))));
    int unitSize = screenWidth/20;
    Label head;
    Texture rule1;
    Texture rule2;
    Texture rule3;
    Texture rule4;

    Grid g = new Grid();

    public RuleScreen(MyMalefiz mz, Config lp)
    {
        mymalefiz = mz;
        this.lp = lp;
        create();
    }

    public void create()
    {
        batch = new SpriteBatch();
        logo = new Texture("malefiz-logo.png");
        backgroundTexture = new Texture("bg-startscreen.jpg");
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));

        Gdx.input.setInputProcessor(stage);

        head = new Label("Spielregeln", skin);
        head.setPosition(g.getUnitSize(), 15*g.getUnitSize()*g.getRatio());
        head.setWidth(18*g.getUnitSize());
        head.setHeight(4*g.getUnitSize()*g.getRatio());
        head.setFontScale(3*lp.getScreenScaleFactor());
        head.setAlignment(Align.center);

        rule1 = new Texture(Gdx.files.internal("rules1.png"));
        rule2 = new Texture(Gdx.files.internal("rules2.png"));
        rule3 = new Texture(Gdx.files.internal("rules3.png"));
        rule4 = new Texture(Gdx.files.internal("rules4.png"));
    /*
        String text = "Lorem ipsum dolor sit amet, \r\nconsetetur sadipscing elitr, sed\r\n diam nonumy eirmod tempor invidunt \r\nut labore et dolore magna aliquyam \r\nerat, sed diam voluptua. At vero \r\neos et accusam et justo duo dolores\r\n et ea rebum. Stet clita kasd gubergren, no \r\nsea takimata sanctus est Lorem ipsum \r\ndolor sit amet. Lorem ipsum \r\ndolor sit amet, consetetur sadipscing elitr, \r\nsed diam nonumy eirmod tempor invidunt \r\nut labore et dolore magna aliquyam erat, \\r\\nsed diam voluptua. At vero eos et accusam \\r\\net justo duo dolores et ea rebum. Stet clita kasd \\r\\ngubergren, no sea takimata sanctus est Lorem ipsum dolor\\r\\n sit amet. Lorem ipsum dolor sit amet, \\r\\nconsetetur sadipscing elitr, sed diam \\r\\nnonumy eirmod tempor invidunt ut labore et \\r\\ndolore magna aliquyam erat, sed diam \\r\\nvoluptua. At vero eos et accusam et justo duo dolores\\r\\n et ea rebum. Stet clita kasd gubergren,\\r\\n no sea takimata sanctus est Lorem ipsum dolor sit amet.\\r\\n\n" +
                "\n" +
                "Duis autem vel eum iriure dolor in\r\n hendrerit in vulputate velit esse molestie \r\nconsequat, vel illum dolore eu feugiat nulla facilisis\r\n at vero eros et accumsan et iusto odio dignissim qui\r\n blandit praesent luptatum zzril delenit\r\n augue duis dolore te feugait nulla facilisi. Lorem \r\nipsum dolor sit amet, consectetuer adipiscing elit, \r\nsed diam nonummy nibh euismod tincidunt ut \r\nlaoreet dolore magna aliquam erat volutpat.  \r\n" +
                "\n" +
                "Ut wisi enim ad minim veniam, quis\r\n nostrud exerci tation ullamcorper suscipit \r\\nlobortis nisl ut aliquip ex ea commodo consequat.\r\n Duis autem vel eum iriure dolor in hendrerit in vulputate \r\nvelit esse molestie consequat, vel illum \r\ndolore eu feugiat nulla facilisis at vero eros et \r\naccumsan et iusto odio dignissim qui blandit praesent \r\nluptatum zzril delenit augue duis dolore te feugait nulla facilisi.\r\n\n" +
                "\n" +
                "Nam liber tempor cum soluta nobis \r\neleifend option congue nihil imperdiet\r\n doming id quod mazim placerat facerA";


        Label label = new Label(text, skin);

        label.setWidth(18*g.getUnitSize());
        label.setHeight(14*g.getUnitSize()*g.getRatio());
        label.setWrap(true);
        label.pack();
        label.setFillParent(true);
        label.setFontScale(1.5f);
        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.setBounds(g.getUnitSize(), g.getUnitSize(), 18*g.getUnitSize()*g.getRatio(), 15*g.getUnitSize()*g.getRatio()); //This should be the bounds of the scroller and the scrollable content need to be inside this
        scrollPane.layout();
        scrollPane.setTouchable(Touchable.enabled);

        Gdx.input.setCatchBackKey(true);
*/
        //stage.addActor(scrollPane);
        stage.addActor(head);


    }

    @Override
    public void show() {
        renderArrows();
    }

    @Override
    public void render(float delta) {
        stage.act();
        batch.begin();
        renderBackground();
        renderRule(this.indexRule);
        batch.end();
        stage.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){mymalefiz.setMenuScreen();}
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
        stage.dispose();
    }

    public void renderBackground()
    {
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    public void renderRule(int val)
    {

        switch(indexRule) {
            case 1:
                ruleDisplay = rule1;
                batch.draw(ruleDisplay, g.getUnitSize()*2, g.getUnitSize(), 16*g.getUnitSize(), 14*g.getUnitSize()*g.getRatio());
                break;
            case 2:
                ruleDisplay = rule2;
                batch.draw(ruleDisplay, g.getUnitSize()*2, g.getUnitSize(), 16*g.getUnitSize(), 14*g.getUnitSize()*g.getRatio());
                break;
            case 3:
                ruleDisplay = rule3;
                batch.draw(ruleDisplay, g.getUnitSize()*2, g.getUnitSize(), 16*g.getUnitSize(), 14*g.getUnitSize()*g.getRatio());
                break;
            case 4:
                ruleDisplay = rule4;
                batch.draw(ruleDisplay, g.getUnitSize()*2, g.getUnitSize(), 16*g.getUnitSize(), 14*g.getUnitSize()*g.getRatio());
                break;
        }
    }
    public void renderArrows() {
        arrowLeft.setX(g.getUnitSize());
        arrowLeft.setY(g.getUnitSize()*28);
        arrowLeft.setWidth(2*g.getUnitSize());
        arrowLeft.setHeight(2*g.getUnitSize());
        arrowRight.setX(g.getUnitSize()*17);
        arrowRight.setY(g.getUnitSize()*28);
        arrowRight.setWidth(2*g.getUnitSize());
        arrowRight.setHeight(2*g.getUnitSize());
        stage.addActor(arrowLeft);
        stage.addActor(arrowRight);

        arrowLeft.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(indexRule >= 2) {
                    indexRule--;
                }
            }
        });
        arrowRight.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(indexRule <= 3) {
                    indexRule++;
                }
            }
        });

    }





}

