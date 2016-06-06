package controllers;

import com.badlogic.gdx.Game;

import java.util.HashMap;

import interfaces.ActionResolver;
import models.Avatar;
import models.LanguagePack;
import models.Mode;
import models.Team;
import screens.CharacterSelectionScreen;
import screens.ConnectionClientScreen;
import screens.ConnectionScreen;
import screens.GameScreen;
import screens.LanguageScreen;
import screens.MenuScreen;
import screens.NetworkMenuScreen;
import screens.NumberOfPlayersSelectionScreen;
import screens.RuleScreen;
import screens.WinnerScreen;

public class MyMalefiz extends Game {
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private RuleScreen ruleScreen;
	private LanguageScreen languageScreen;
	private WinnerScreen winnerScreen;
	private CharacterSelectionController characterSelectionController;
	private NumberOfPlayersSelectionController numberOfPlayersSelectionController;
	private ConnectionController connectionController;
	private ConnectionClientController connectionClientController;
	public ActionResolver actionResolver;

	private LanguagePack lp = new LanguagePack("ger");

	public MyMalefiz(ActionResolver actionResolver)
	{
		this.actionResolver = actionResolver;
	}

	@Override
	public void create() {
		//AssetLoader.load(); //load all assets!!
		this.setLanguageScreen();
	}

	public void setMenuScreen()
	{
		this.menuScreen = new MenuScreen(this, lp);
		setScreen(this.menuScreen);
	}

	public void setGameScreen(Mode m, HashMap<Integer, Avatar> selectedCharacters)
	{
		this.gameScreen = new GameScreen(this, selectedCharacters.get(0), lp, m, actionResolver);
		setScreen(this.gameScreen);
	}

	public void setNumberOfPlayersSelectionScreen() {
		this.numberOfPlayersSelectionController = new NumberOfPlayersSelectionController(this, lp);
		setScreen(new NumberOfPlayersSelectionScreen(this.numberOfPlayersSelectionController));
	}

	public void setCharacterSelectionScreen(Mode m, int numberOfCharacters)
	{
		this. characterSelectionController = characterSelectionController.getInstance();
		this.characterSelectionController.init(this, lp, m, numberOfCharacters);
		setScreen(new CharacterSelectionScreen(this.characterSelectionController));
	}

	public void setRuleScreen()
	{
		this.ruleScreen = new RuleScreen(this, lp);
		setScreen(this.ruleScreen);
	}

	public void setLanguageScreen()
	{
		this.languageScreen = new LanguageScreen(this, lp);
		setScreen(this.languageScreen);
	}

	public void setWinnerScreen(Team t)
	{
		this.winnerScreen = new WinnerScreen(this, lp, t);
		setScreen(this.winnerScreen);
	}

	public void setConnectionScreen() {
		this.connectionController = new ConnectionController(this, lp);
		setScreen(new ConnectionScreen((this.connectionController)));
	}

	public void setNetworkMenuScreen() {
		setScreen(new NetworkMenuScreen(this, lp));
	}

	public void setConnectionClientScreen() {
		this.connectionClientController = new ConnectionClientController(this, lp);
		setScreen(new ConnectionClientScreen(this.connectionClientController));
	}

	public void setLanguagePack(LanguagePack lang)
	{
		this.lp = lang;
	}


}
