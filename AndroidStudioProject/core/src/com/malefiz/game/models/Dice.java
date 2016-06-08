package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class Dice {

    private String id;                  // Falls wir mehrere Würfel erstellen
    private Sprite dice_picture;
    private int range;                  // Falls wir einen Würfel machen wollen mit dem man höhere Zahlen würfelt
    private Random rng = new Random();
    private int value;                  // Der gewürfelte Wert
    private Texture texture;            // Das Bild des Würfel selbst
    private Sprite dice_idle;

    Texture text1;
    Texture text2;
    Texture text3;
    Texture text4;
    Texture text5;
    Texture text6;

    public Dice(int id, int range) {
        this.range = range;
        this.dice_idle = new Sprite(new Texture(Gdx.files.internal("dice_idle.png")));

        this.text1 = new Texture(Gdx.files.internal("dice_one.png"));
        this.text2 = new Texture(Gdx.files.internal("dice_two.png"));
        this.text3 = new Texture(Gdx.files.internal("dice_three.png"));
        this.text4 = new Texture(Gdx.files.internal("dice_four.png"));
        this.text5 = new Texture(Gdx.files.internal("dice_five.png"));
        this.text6 = new Texture(Gdx.files.internal("dice_six.png"));

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
                texture = text1;
                break;
            case 2:
                texture = text2;
                break;
            case 3:
                texture = text3;
                break;
            case 4:
                texture = text4;
                break;
            case 5:
                texture = text5;
                break;
            case 6:
                texture = text6;
                break;
            default:
                System.out.println("Falscher Dicevalue uebergeben!");
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
        int result = (int)(Math.random()*6+1);
        value = result;
        return setImage(result);
    }

    public Sprite rollDice(int dice)
    {
        value = dice;
        return setImage(dice);
    }


    public Animation createAnimation() {
        TextureRegion tex1 = new TextureRegion(new Texture(Gdx.files.internal("dice_one.png")));
        TextureRegion tex2 = new TextureRegion(new Texture(Gdx.files.internal("dice_two.png")));
        TextureRegion tex3 = new TextureRegion(new Texture(Gdx.files.internal("dice_three.png")));
        TextureRegion tex4 = new TextureRegion(new Texture(Gdx.files.internal("dice_four.png")));
        TextureRegion tex5 = new TextureRegion(new Texture(Gdx.files.internal("dice_five.png")));
        TextureRegion tex6 = new TextureRegion(new Texture(Gdx.files.internal("dice_six.png")));

        Animation diceAnimation = new Animation(0.15f, tex1, tex2, tex3, tex4, tex5, tex6);
        return diceAnimation;
    }

    public int getValue() {
        return value;
    }
}
