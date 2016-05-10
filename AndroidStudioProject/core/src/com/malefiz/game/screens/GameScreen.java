package com.malefiz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.malefiz.game.MyMalefiz;
import com.malefiz.game.models.Avatar;
import com.malefiz.game.models.Board;
import com.malefiz.game.models.Color;
import com.malefiz.game.models.Dice;
import com.malefiz.game.models.Field;
import com.malefiz.game.models.Grid;
import com.malefiz.game.models.LanguagePack;
import com.malefiz.game.models.Team;
import com.malefiz.game.models.Unit;

import java.util.ArrayList;

//import javafx.scene.Camera; //Wirft einen Error.

/**
 * Created by MCLeite on 25.04.2016.
 */
public class GameScreen implements Screen {

    private MyMalefiz mainClass = null;
    private LanguagePack lp;

    Skin skin;
    Stage stage;
    SpriteBatch batch;

    int screenHeight;
    int screenWidth;

    private Avatar selectedAvatar;


    int fieldSize;
    int unitSize;

    Board b = new Board();
    Grid g = new Grid();

    ArrayList<Field> fields;
    ArrayList<Integer[]> lines;
    ArrayList<Unit> units;


    Texture pixelBlack;

    ShapeRenderer sr;

    //Würfel
    Dice normalDice = new Dice(1,6);
    Image normalDiceDisplay = null;
    Image riggedDiceDisplay = null;
    String[] riggedOptions = new String[]{"","dice_one.png","dice_two.png","dice_three.png", "dice_four.png", "dice_five.png", "dice_six.png"};

    public GameScreen(MyMalefiz mainClass, Avatar selectedAvatar, LanguagePack lp) {
        this.selectedAvatar = selectedAvatar;
        this.mainClass = mainClass;
        this.lp = lp;
        show();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        sr = new ShapeRenderer();

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        fieldSize = screenWidth/30;
        unitSize = screenWidth/20;
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        skin.add("field_red", new Texture("feld_rot.png"));
        skin.add("field_blue", new Texture("feld_blau.png"));
        skin.add("field_green", new Texture("feld_gruen.png"));
        skin.add("field_yellow", new Texture("feld_gelb.png"));
        skin.add("field_black", new Texture("feld_schwarz.png"));
        skin.add("pixel_black", new Texture("pixel_black_1x1.png"));

        // probably own skin for units
        skin.add("unit_yellow", new Texture("unit_yellow.png"));
        skin.add("unit_red", new Texture("unit_red.png"));
        skin.add("unit_blue", new Texture("unit_blue.png"));
        skin.add("unit_green", new Texture("unit_green.png"));



        drawLines();
        drawFields();
        drawAvatar();
        drawDice();
        drawRiggedDice();
        drawUnits();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 187 / 255f, 1 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }

    public void drawUnits() {

        units = b.getUnits();

        for (Unit u : units) {
            Image unit_image;
            if (u.getTeam() == Team.RED) {
                unit_image = new Image(skin.getDrawable("unit_red"));
            } else if (u.getTeam() == Team.YELLOW) {
                unit_image = new Image(skin.getDrawable("unit_yellow"));
            } else if (u.getTeam() == Team.BLUE) {
                unit_image = new Image(skin.getDrawable("unit_blue"));
            } else if (u.getTeam() == Team.GREEN){
                unit_image = new Image(skin.getDrawable("unit_green"));
            } else {
                throw new RuntimeException("Error in game screen draw units");
            }
            // scaling muss noch ueberarbeitet werden momentan nur von field uebernommen

            unit_image.setX(unitSize * u.getUnitCoordX() - fieldSize / 2);

            int y;
            if(u.getUnitCoordY() == 6)
            {
                y = (int) (g.getRatio() * unitSize * u.getUnitCoordY() - 4 * unitSize - fieldSize / 2 + (g.getRatio()-1)*unitSize);
                unit_image.setY(y);
            }
            else if(u.getUnitCoordY() == 5)
            {
                y = (int) (g.getRatio() * unitSize * u.getUnitCoordY() - 4 * unitSize - fieldSize / 2 + 2*(g.getRatio()-1)*unitSize);
                unit_image.setY(y);
            }
            else
            {
                y = (int) (g.getRatio() * unitSize * u.getUnitCoordY() - 4 * unitSize - fieldSize / 2);
                unit_image.setY(y);
            }

            unit_image.setHeight(fieldSize*g.getRatio());
            unit_image.setWidth(fieldSize);
            stage.addActor(unit_image);
        }
    }


    public void drawFields()
    {
        fields = b.getFields();

        for(Field f : fields)
        {
            Image field_img = null;
            if(f.getColor() == Color.BLACK)
            {
                field_img = new Image(skin.getDrawable("field_black"));
            }
            else if(f.getColor() == Color.RED)
            {
                field_img = new Image(skin.getDrawable("field_red"));
            }
            else if(f.getColor() == Color.YELLOW)
            {
                field_img = new Image(skin.getDrawable("field_yellow"));
            }
            else if(f.getColor() == Color.BLUE)
            {
                field_img = new Image(skin.getDrawable("field_blue"));
            }
            else if(f.getColor() == Color.GREEN)
            {
                field_img = new Image(skin.getDrawable("field_green"));
            }

            field_img.setX(unitSize * f.getCoordX() - fieldSize / 2);

            int y;
            if(f.getCoordY() == 6)
            {
                y = (int) (g.getRatio() * unitSize * f.getCoordY() - 4 * unitSize - fieldSize / 2 + (g.getRatio()-1)*unitSize);
                field_img.setY(y);
            }
            else if(f.getCoordY() == 5)
            {
                y = (int) (g.getRatio() * unitSize * f.getCoordY() - 4 * unitSize - fieldSize / 2 + 2*(g.getRatio()-1)*unitSize);
                field_img.setY(y);
            }
            else
            {
                y = (int) (g.getRatio() * unitSize * f.getCoordY() - 4 * unitSize - fieldSize / 2);
                field_img.setY(y);
            }

            f.setRealCoordX(unitSize*f.getCoordX());
            f.setRealCoordY(y);
            field_img.setHeight(fieldSize);
            field_img.setWidth(fieldSize);
            stage.addActor(field_img);
        }
    }

    public void drawLines()
    {
        lines = b.getLines();

        for (Integer[] lineCoord : lines)
        {
            Image line = new Image(skin.getDrawable("pixel_black"));
            int coordX1 = unitSize*lineCoord[0]-2;
            int coordY1 = (int)(g.getRatio()*unitSize*lineCoord[1]-2-4*unitSize);

            int height;
            int width;

            if (lineCoord[0] == lineCoord[2])
            {
                height = (int)(g.getRatio()*(unitSize*(lineCoord[3]-lineCoord[1])));
                width = 5;
            }
            else
            {
                width = (int)(unitSize*(lineCoord[2]-lineCoord[0]));
                height = 5;
            }

            line.setX(coordX1);
            line.setY(coordY1);
            line.setHeight(height);
            line.setWidth(width);
            stage.addActor(line);
        }
        sr.end();
    }

    public void drawAvatar()
    {
        skin.add(selectedAvatar.getId(), new Texture(selectedAvatar.getImageName()));
        Image img = new Image(skin.getDrawable(selectedAvatar.getId()));
        img.setX(unitSize/2);
        img.setY(unitSize/2);
        img.setWidth(4*unitSize);
        img.setHeight(4*unitSize);
        stage.addActor(img);
    }
    public void drawDice() {

        normalDiceDisplay = new Image(normalDice.rollDice());
        normalDiceDisplay.setX(unitSize*5);
        normalDiceDisplay.setY(unitSize/2);
        normalDiceDisplay.setWidth(4*unitSize);
        normalDiceDisplay.setHeight(4*unitSize);
        stage.addActor(normalDiceDisplay);
        normalDiceDisplay.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                    drawDice();

            }
        });
    }
    public void drawRiggedDice() {
        boolean visible = false;
        riggedDiceDisplay = new Image(new Sprite(new Texture(Gdx.files.internal("dice_idle.png"))));
        riggedDiceDisplay.setX(unitSize*10);
        riggedDiceDisplay.setY(unitSize/2);
        riggedDiceDisplay.setWidth(4*unitSize);
        riggedDiceDisplay.setHeight(4*unitSize);
        stage.addActor(riggedDiceDisplay);
        riggedDiceDisplay.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                    for (int i = 1; i < riggedOptions.length; i++) {
                        Image temp = new Image(new Sprite(new Texture(Gdx.files.internal(riggedOptions[i]))));
                        temp.setX(unitSize * 10);
                        temp.setY(unitSize * 4 * i+50);
                        temp.setWidth(4 * unitSize);
                        temp.setHeight(4 * unitSize);
                        stage.addActor(temp);
                }
            }
        });
    }



}
