package controllers;

/**
 * Created by Dan on 20.05.2016.
 */
public class GameController {
    private static GameController instance;

    private GameController(){}

    public static GameController getInstance()
    {
        if(instance == null)
        {
            instance = new GameController();
        }
        return instance;
    }

    public void init()
    {

    }


}
