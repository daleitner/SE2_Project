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
import models.Rock;
import models.Team;
import models.Unit;


import java.util.ArrayList;
import java.util.concurrent.Exchanger;

//import javafx.scene.Camera; //Wirft einen Error.


public class GameScreen implements Screen {

    private MyMalefiz mainClass = null;
    private LanguagePack lp;

    Skin skin;
    Stage stage;
    SpriteBatch batch;

    int screenHeight;
    int screenWidth;

    private Avatar selectedAvatar;
    private Mode mode;


    int fieldSize;
    int unitSize;

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
    ArrayList<Field> possibleMoves;

    public GameScreen(MyMalefiz mainClass, Avatar selectedAvatar, LanguagePack lp, Mode mode) {
        this.selectedAvatar = selectedAvatar;
        this.mainClass = mainClass;
        this.lp = lp;
        this.gc = GameController.getInstance();
        this.mode = mode;
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

        skin.add("rock", new Texture("rock.png"));

        drawLines();
        drawFields();
        drawAvatar();
        drawDice();
        drawRiggedDice();
        drawUnits();
        drawRocks();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 187 / 255f, 1 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        //stage.setDebugAll(true);
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

                        if(playerAbleToMove(u.getTeam())) {

                            selectedUnit = u;
                            isUnitSelected = true;

                            checkPossibleMoves(rolledDiceValue, selectedUnit.getCurrentFieldPosition());
                        } else {
                            System.out.println("This team is not able to move a unit!");
                        }

                    }
                }
            });

        }
    }

    public boolean playerAbleToMove (Team team) {
        for (Unit unit : units) {
            if (team == unit.getTeam()) {
                if (!checkPossibleMoves(rolledDiceValue, unit.getCurrentFieldPosition())){
                    clearPossibleMoves();
                } else {
                    clearPossibleMoves();
                    return true;
                }
            }
        }
        return false;
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

            setFieldPosScal(f, 0);

            stage.addActor(field_img);

            //todo move all listeners to another location
            /* Field listener for unit movement */
            f.getFieldImage().addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    System.out.print("FIELD: i am listening and my id is: " + f.getID());
                    System.out.print("\n - - - - - - - - - - - - - - - - - \n");
                    if (isUnitSelected && diceRolled) {
                        moveUnit(f, selectedUnit);
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
            setRockImagePosition(rock);
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
        if(mode == Mode.NETWORK) {
            skin.add(selectedAvatar.getId(), new Texture(selectedAvatar.getImageName()));
            Image img = new Image(skin.getDrawable(selectedAvatar.getId()));
            img.setX(unitSize / 2);
            img.setY(unitSize / 2);
            img.setWidth(4 * unitSize);
            img.setHeight(4 * unitSize);
            stage.addActor(img);
        }
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

    public void moveUnit (Field nextPosition, Unit unit) {
        // todo check if next position is empty
        //if (checkPossibleMoves(rolledDiceNumber, unit.getCurrentFieldPosition(), nextPosition)) {
        if (possibleMoves.contains(nextPosition)) {
            unit.setPosition(nextPosition);
            setUnitImagePosition(unit);
            clearPossibleMoves();

            if (nextPosition.getRockId() > 0) {
                for (Rock r : rocks) {
                    if (r.getId() == nextPosition.getRockId()) {
                        // draw taken rock
                        int id = nextPosition.getRockId();
                        nextPosition.setRockId(0);
                        r.getRockImage().setX(unitSize*15);
                        r.getRockImage().setY(unitSize/2);
                        r.getRockImage().setWidth(4*unitSize);
                        r.getRockImage().setHeight(4*unitSize);
                        r.setTaken(true);
                        stage.addActor(r.getRockImage());

                        r.getRockImage().addListener(new ClickListener(){
                            public void clicked(InputEvent ev, float x, float y)
                            {
                                System.out.println("I AM LISTING");

                            }
                        });

                        System.out.println("ROck taken new image");
                    }
                }
            }

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

    public boolean checkPossibleMoves(int rolledDiceValue, Field unitPosition) {

        ArrayList<Field> possibleFields = new ArrayList<Field>();
        ArrayList<Field> visitedFields = new ArrayList<Field>();
        visitedFields.add(unitPosition);

        ArrayList<Field> visitedTemp = new ArrayList<Field>();
        if (    b.getRedStartFields().contains(unitPosition) ||
                b.getBlueStartFields().contains(unitPosition) ||
                b.getYellowStartFields().contains(unitPosition) ||
                b.getGreenStartFields().contains(unitPosition) ) {

            if (rolledDiceValue != 6) {
                return false;
            }
        }


        visitedTemp.addAll(visitedFields);

        clearPossibleMoves();


        int size;

        /* steps to go */
        for (int i = 0; i < rolledDiceValue - 1; i++) {
            size = visitedFields.size();
            /* all reachable fields within the current step range
             * visitedtemp all former reached; visitedfields all newly reachend in this step */

            for (int j = 0; j < size; j++) {

                if (visitedFields.get(j).getRockId() == 0) {
                    visitedFields.addAll(getNeighbourFieldsOfField(visitedFields.get(j)));
                } else {
                    visitedTemp.add(visitedFields.get(j));
                }
            }

            for (Field f : visitedFields) {
                if (f.getRockId() != 0) {
                    visitedTemp.add(f);
                }
            }

            visitedFields.removeAll(visitedTemp);
            visitedTemp.addAll(visitedFields);
        }

        /* go one last step and include rock fields now */
        for (Field f : visitedFields) {
            possibleFields.addAll( getNeighbourFieldsOfField( f ));
        }

        possibleFields.removeAll(visitedTemp);


        this.possibleMoves = possibleFields;

        for (Field f : possibleMoves) {
            setFieldPosScal(f, 1);
        }

        if (possibleMoves.isEmpty()) {
            return false;
        } else {
            for (Field f : possibleMoves) {
                f.getFieldImage().setHeight(fieldSize*2);
                f.getFieldImage().setWidth(fieldSize*2);
            }
            return true;
        }
        //todo if unit current pos == start pos
    }

    public void clearPossibleMoves () {
        if (this.possibleMoves != null) {
            for (Field f : possibleMoves) {
                setFieldPosScal(f, 0);
            }
        }
        this.possibleMoves = null;
    }

    // 0 for standard field size; 1 for selected size
    public void setFieldPosScal(Field field, int selected) {
        int size;
        if (selected == 1) {
            size = this.fieldSize * 2;
        } else {
            size = this.fieldSize;
        }

        field.getFieldImage().setX(unitSize * field.getCoordX() - size / 2);

        int y;
        if(field.getCoordY() == 6)
        {
            y = (int) (g.getRatio() * unitSize * field.getCoordY() - 4 * unitSize - size / 2 + (g.getRatio()-1)*unitSize);
            field.getFieldImage().setY(y);
        }
        else if(field.getCoordY() == 5)
        {
            y = (int) (g.getRatio() * unitSize * field.getCoordY() - 4 * unitSize - size / 2 + 2*(g.getRatio()-1)*unitSize);
            field.getFieldImage().setY(y);
        }
        else
        {
            y = (int) (g.getRatio() * unitSize * field.getCoordY() - 4 * unitSize - size / 2);
            field.getFieldImage().setY(y);
        }

        field.setRealCoordX(unitSize*field.getCoordX());
        field.setRealCoordY(y);
        field.getFieldImage().setHeight(size);
        field.getFieldImage().setWidth(size);
    }

    public void setRockImagePosition (Rock rock) {
        rock.getRockImage().setX(unitSize * rock.getCoordX() - fieldSize / 2);

        int y;
        if(rock.getCoordY() == 6)
        {
            y = (int) (g.getRatio() * unitSize * rock.getCoordY() - 4 * unitSize - fieldSize / 2 + (g.getRatio()-1)*unitSize);
            rock.getRockImage().setY(y);
        }
        else if(rock.getCoordY() == 5)
        {
            y = (int) (g.getRatio() * unitSize * rock.getCoordY() - 4 * unitSize - fieldSize / 2 + 2*(g.getRatio()-1)*unitSize);
            rock.getRockImage().setY(y);
        }
        else
        {
            y = (int) (g.getRatio() * unitSize * rock.getCoordY() - 4 * unitSize - fieldSize / 2);
            rock.getRockImage().setY(y);
        }

        rock.getRockImage().setHeight(fieldSize*g.getRatio());
        rock.getRockImage().setWidth(fieldSize);
    }

}
