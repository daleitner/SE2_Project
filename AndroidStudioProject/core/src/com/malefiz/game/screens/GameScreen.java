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
import com.malefiz.game.models.Team;
import com.malefiz.game.models.Unit;

import java.util.ArrayList;

//import javafx.scene.Camera; //Wirft einen Error.


public class GameScreen implements Screen {

    private MyMalefiz mainClass = null;

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

    /* dice */
    Dice normalDice = new Dice(1,6);
    Image normalDiceDisplay = null;
    Image riggedDiceDisplay = null;
    String[] riggedOptions = new String[]{"","dice_one.png","dice_two.png","dice_three.png", "dice_four.png", "dice_five.png", "dice_six.png"};
    boolean diceRolled = true; // for testing true; has to be reseted after each turn
    int rolledDiceValue = 0;   // for testing fixed value; not sure if this var is necessary

    /* unit movement */
    boolean isUnitSelected = false;
    Unit selectedUnit = null;

    public GameScreen(MyMalefiz mainClass, Avatar selectedAvatar) {
        this.selectedAvatar = selectedAvatar;
        this.mainClass = mainClass;
        show();
        moveUnitWithId116(); // just for testing can be removed anytime
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

        for (final Unit u : units) {
            Image unit_image;
            if (u.getTeam() == Team.RED) {
                unit_image = new Image(skin.getDrawable("unit_red"));
                u.setUnitImage(unit_image);
            } else if (u.getTeam() == Team.YELLOW) {
                unit_image = new Image(skin.getDrawable("unit_yellow"));
                u.setUnitImage(unit_image);
            } else if (u.getTeam() == Team.BLUE) {
                unit_image = new Image(skin.getDrawable("unit_blue"));
                u.setUnitImage(unit_image);
            } else if (u.getTeam() == Team.GREEN){
                unit_image = new Image(skin.getDrawable("unit_green"));
                u.setUnitImage(unit_image);
            } else {
                throw new RuntimeException("Error in game screen draw units");
            }
            stage.addActor(unit_image);
            setUnitImagePosition(u);

            //todo move all listeners to another location
            /* Listener for unit movement and selection */
            u.getUnitImage().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (diceRolled) {
                        selectedUnit = u;
                        isUnitSelected = true;
                        System.out.println("Selected Unit is = " + selectedUnit.getId());
                    }

                }
            });

        }
    }


    public void drawFields()
    {
        fields = b.getFields();

        for(final Field f : fields)
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

            //todo move all listeners to another location
            /* Field listener for unit movement */
            field_img.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    System.out.print("FIELD: i am listening and my id is: " + f.getID());
                    System.out.print("\n - - - - - - - - - - - - - - - - - \n");
                    if (isUnitSelected && diceRolled) {
                        moveUnit(rolledDiceValue, f, selectedUnit);

                    }
                }
            });

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
                    rolledDiceValue = normalDice.getValue();
                    System.out.println("rolled dice value is = " +  rolledDiceValue);
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

    /* unit movement test */
    /* starting fields are not connected to the rest yet so to test movement one unit is moved inside the field */
    public void moveUnitWithId116() {
        for (Unit x : units) {
            if (x.getId() == 116) {
                for (Field f : fields) {
                    if (f.getID() == 36) {
                        x.setPosition(f);
                        setUnitImagePosition(x);
                    }
                }
            }
        }
    }

    /* set scaling and image position of unit */
    /* muss noch auf die units angepasst werden */
    // scaling muss noch ueberarbeitet werden momentan nur von field uebernommen
    public void setUnitImagePosition (Unit unit) {

        unit.getUnitImage().setX(unitSize * unit.getUnitCoordX() - fieldSize / 2);

        int y;
        if(unit.getUnitCoordY() == 6)
        {
            y = (int) (g.getRatio() * unitSize * unit.getUnitCoordY() - 4 * unitSize - fieldSize / 2 + (g.getRatio()-1)*unitSize);
            unit.getUnitImage().setY(y);
        }
        else if(unit.getUnitCoordY() == 5)
        {
            y = (int) (g.getRatio() * unitSize * unit.getUnitCoordY() - 4 * unitSize - fieldSize / 2 + 2*(g.getRatio()-1)*unitSize);
            unit.getUnitImage().setY(y);
        }
        else
        {
            y = (int) (g.getRatio() * unitSize * unit.getUnitCoordY() - 4 * unitSize - fieldSize / 2);
            unit.getUnitImage().setY(y);
        }

        unit.getUnitImage().setHeight(fieldSize*g.getRatio()*2);
        unit.getUnitImage().setWidth(fieldSize*2);
    }

    public void moveUnit (int rolledDiceNumber, Field nextPosition, Unit unit) {
        // todo check if next position is empty
        if (checkPossibleMoves(rolledDiceNumber, unit.getCurrentFieldPosition(), nextPosition)) {
            unit.setPosition(nextPosition);
            setUnitImagePosition(unit);
        }
    }

    private ArrayList<Field> getNeighbourFieldsOfField (Field field) {
        ArrayList<Field> neighbours= new ArrayList<Field>();
        for (Integer id : field.getNeighbouringFields()) {
            for (Field f : fields) {
                if (f.getID() == id) {
                    neighbours.add(f);
                }
            }
        }
        return neighbours;
    }

    public boolean checkPossibleMoves(int rolledDiceValue, Field unitPosition, Field nextPosition) {
        ArrayList<Field> visitedFields = new ArrayList<Field>();
        visitedFields.add(unitPosition);
        ArrayList<Field> possibleFields = new ArrayList<Field>();
        int size;

        for (int i = 0; i < rolledDiceValue - 1; i++) {
            size = visitedFields.size();
            for (int j = 0; j < size; j++) {
                visitedFields.addAll( getNeighbourFieldsOfField( visitedFields.get(j) ) );
            }
        }

        for (Field f : visitedFields) {
            possibleFields.addAll( getNeighbourFieldsOfField( f ));
        }

        possibleFields.removeAll(visitedFields);

        if (possibleFields.contains(nextPosition)) {
            return true;
        } else {
            System.out.println("This move is not possible");
            return false;
        }

        //todo if unit current pos == start pos

    }
}
