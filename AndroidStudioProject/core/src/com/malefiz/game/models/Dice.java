package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

/**
 * Created by Artist on 03.05.2016.
 */
public class Dice {

    private String id;                  // Falls wir mehrere Würfel erstellen
    private Sprite dice_picture;
    private int range;                  // Falls wir einen Würfel machen wollen mit dem man höhere Zahlen würfelt
    private Random rng = new Random();
    private int value;                  // Der gewürfelte Wert
    private Texture texture;            // Das Bild des Würfel selbst
    private Sprite dice_idle;

    public Dice(int id, int range) {
        this.range = range;
        this.dice_idle = new Sprite(new Texture(Gdx.files.internal("dice_idle.png")));

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sprite getImage() {
        return dice_picture;
    }

    public Sprite setImage(int number) {
        switch (number) {
            case 1:
                texture = new Texture(Gdx.files.internal("dice_one.png"));
                break;
            case 2:
                texture = new Texture(Gdx.files.internal("dice_two.png"));
                break;
            case 3:
                texture = new Texture(Gdx.files.internal("dice_three.png"));
                break;
            case 4:
                texture = new Texture(Gdx.files.internal("dice_four.png"));
                break;
            case 5:
                texture = new Texture(Gdx.files.internal("dice_five.png"));
                break;
            case 6:
                texture = new Texture(Gdx.files.internal("dice_six.png"));
                break;
            default:
        }
        dice_picture = new Sprite(texture);
        return dice_picture;
    }

    // Um einen Würfel einfach mal darzustellen.
    public Sprite displayDice() {
        return dice_idle;
    }

    /*
    Es wird der Sprite mit dem jeweiligen Würfelbild returnt.
    Mit jedem Klick soll rollDice aufgerufen werden - bzw. um dann
    eine Animation zu machen, muss die Funktion so oft aufgerufen werden bis
    wieder ein Input kommt.
    */
    public Sprite rollDice() {
        int result = random();
        return setImage(result);
    }

    public Sprite rollDice(int dice)
    {
        return setImage(dice);
    }

    public int random() {
        value = rng.nextInt(range) + 1;
        return value;
    }

    public int getValue() {
        return value;
    }
}
