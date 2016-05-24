package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import controllers.CharacterSelectionController;
import controllers.GameController;
import controllers.MyMalefiz;
import models.Avatar;
import models.Board;
import models.Color;
import models.Dice;
import models.Field;
import models.Grid;
import models.LanguagePack;
import models.Mode;
import models.Player;
import models.Rock;
import models.Team;
import models.Unit;


import java.util.ArrayList;
import java.util.concurrent.Exchanger;

//import javafx.scene.Camera; //Wirft einen Error.


public class GameScreen implements Screen {

    private MyMalefiz mainClass = null;
    private LanguagePack lp;
    private Player player;
    long startTime;

    Skin skin;
    Stage stage;
    SpriteBatch batch;

    int screenHeight = Gdx.graphics.getHeight();
    int screenWidth = Gdx.graphics.getWidth();

    private Avatar selectedAvatar;
    private Mode mode;

    int fieldSize = screenWidth/30;
    int unitSize = screenWidth/20;

    Board b = new Board();
    Grid g = new Grid();

    GameController gc;

    ArrayList<Field> fields;
    ArrayList<Integer[]> lines;
    ArrayList<Unit> units;
    ArrayList<Image> riggedDices = new ArrayList<Image>();

    ArrayList<Rock> rocks;

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
    ArrayList<Field> possibleMoves = new ArrayList<Field>();

    Rock selectedRock = null;

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public ArrayList<Rock> getRocks() {

        return rocks;
    }

    public void setRocks(ArrayList<Rock> rocks) {
        this.rocks = rocks;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public ArrayList<Field> getPossibleMoves() {

        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<Field> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public int getUnitSize() {

        return unitSize;
    }

    public void setUnitSize(int unitSize) {
        this.unitSize = unitSize;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public Board getB() {
        return b;
    }

    public void setB(Board b) {
        this.b = b;
    }

    public Grid getG() {
        return g;
    }

    public void setG(Grid g) {
        this.g = g;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public GameScreen(MyMalefiz mainClass, Avatar selectedAvatar, LanguagePack lp, Mode mode) {

        this.selectedAvatar = selectedAvatar;
        this.mainClass = mainClass;
        this.lp = lp;
        this.gc = GameController.getInstance();
        gc.setGameScreenInstance(this);
        gc.setSelectedPlayers(CharacterSelectionController.getInstance().getSelectedCharacters());
        gc.init();
        this.mode = mode;
        show();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        sr = new ShapeRenderer();


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

        skin.add("rock", new Texture("rock.png"));

        drawLines();
        drawFields();
        drawAvatar();
        drawDice();
        drawRiggedDice();
        drawUnits();
        drawRocks();
        startTime = 0;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 187 / 255f, 1 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        gc.check();
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
            gc.setUnitImagePosition(u);

            //todo move all listeners to another location
            /* Listener for unit movement and selection */
            u.getUnitImage().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (gc.isDiceRolled() && gc.getActualTeam() == u.getTeam() && !gc.isUnitMoved()) {
                        System.out.println("can i reach the statement?");

                        if(gc.playerAbleToMove(gc.getActualTeam())) {

                            System.out.println("selected unit works");
                            selectedUnit = u;
                            isUnitSelected = true;
                            System.out.println("selected unit get team = " + selectedUnit.getTeam() + "player gc controller get team = " + gc.getActualTeam() );
                            gc.checkPossibleMoves(rolledDiceValue, selectedUnit.getCurrentFieldPosition());
                        } else {
                            System.out.println("This team is not able to move a unit!");
                        }

                    }
                }
            });

        }
    }

    public int getRolledDiceValue() {
        return rolledDiceValue;
    }

    public void setRolledDiceValue(int rolledDiceValue) {
        this.rolledDiceValue = rolledDiceValue;
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
                f.setFieldImage(field_img);
            }
            else if(f.getColor() == Color.RED)
            {
                field_img = new Image(skin.getDrawable("field_red"));
                f.setFieldImage(field_img);
            }
            else if(f.getColor() == Color.YELLOW)
            {
                field_img = new Image(skin.getDrawable("field_yellow"));
                f.setFieldImage(field_img);
            }
            else if(f.getColor() == Color.BLUE)
            {
                field_img = new Image(skin.getDrawable("field_blue"));
                f.setFieldImage(field_img);
            }
            else if(f.getColor() == Color.GREEN)
            {
                field_img = new Image(skin.getDrawable("field_green"));
                f.setFieldImage(field_img);
            }

            field_img.setX(unitSize * f.getCoordX() - fieldSize / 2);

            gc.setFieldPosScal(f, 0);

            stage.addActor(field_img);

            //todo move all listeners to another location
            /* Field listener for unit movement */
            f.getFieldImage().addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    System.out.print("FIELD: i am listening and my id is: " + f.getID());
                    System.out.print("\n - - - - - - - - - - - - - - - - - \n");

                    if (selectedRock != null && f.getUnit() == null && f.getRock() == null && (f.getID() < 95 || f.getID() > 111) && (f.getID()<114)) {
                        gc.setRockPosition(selectedRock, f);
                        f.setRock(selectedRock);
                        selectedRock = null;
                    }

                    //System.out.println("selected unit get team = " + selectedUnit.getTeam() + "player gc controller get team = " + gc.getActualTeam() );
                    if (selectedUnit != null && !(gc.isUnitMoved()) && diceRolled && selectedUnit.getTeam() == gc.getActualTeam()) {
                        gc.moveUnit(f, selectedUnit);
                    }


                }
            });

        }
    }

    public void drawRocks() {
        rocks = b.getRocks();

        for (final Rock rock : rocks) {
            Image rock_image = new Image(skin.getDrawable("rock"));

            stage.addActor(rock_image);

            rock.setRockImage(rock_image);
            gc.setRockImagePosition(rock);

            rock.getRockImage().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (rock.isTaken) {
                        selectedRock = rock;
                        System.out.println("i was taken i am listing");
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
        selectedAvatar = gc.getActualPlayer().getAvatar();
        skin.add(selectedAvatar.getId(), new Texture(selectedAvatar.getImageName()));
        Image img = new Image(skin.getDrawable(selectedAvatar.getId()));
        img.setX(unitSize / 2);
        img.setY(unitSize / 2);
        img.setWidth(4 * unitSize);
        img.setHeight(4 * unitSize);
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
                if(!gc.getPlayerAbleToMove()) {
                    drawDice();
                    gc.setDiceRolled();
                    rolledDiceValue = normalDice.getValue();
                    gc.isPlayerAbleToMove();
                    System.out.println("rolled dice value is = " + rolledDiceValue);
                }
            }
        });
    }

    public void removeDices()
    {
        for(Image i : riggedDices)
        {
            i.remove();
        }
        riggedDices.clear();
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
                if(riggedDices.size() == 0)
                {
                    for (int i = 1; i < riggedOptions.length; i++)
                    {

                        Image temp = new Image(new Sprite(new Texture(Gdx.files.internal(riggedOptions[i]))));
                        temp.setX(unitSize * 10);
                        temp.setY(unitSize * 4 * i+50);
                        temp.setWidth(4 * unitSize);
                        temp.setHeight(4 * unitSize);
                        riggedDices.add(temp);
                        temp.addListener(new ClickListener(){
                            public void clicked(InputEvent ev, float x, float y)
                            {
                                //Implementierung der Würfelzahlübergabe

                                removeDices();
                            }
                        });
                        stage.addActor(temp);

                    }
                }
                else
                {
                    removeDices();
                }

            }
        });
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void setSelectedRock(Rock selectedRock) {
        this.selectedRock = selectedRock;
    }

    public void setPlayer(Player p)
    {
        this.player = p;
    }
}
