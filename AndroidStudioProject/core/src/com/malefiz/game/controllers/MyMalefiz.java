package controllers;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.HashMap;

import interfaces.ActionResolver;
import models.Avatar;
import models.LanguagePack;
import models.Mode;
import models.Player;
import models.Team;
import network.MalefizClient;
import network.MalefizServer;
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
	public MalefizServer server;
	private MalefizClient gameclient;

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

	public void setGameScreen(Mode m, HashMap<Integer, Player> selectedCharacters)
	{
		this.gameScreen = new GameScreen(this, selectedCharacters, lp, m, actionResolver);
		setScreen(this.gameScreen);
	}

	public void setGameScreen(Mode m, HashMap<Integer, Player> selectedCharacters, MalefizClient client)
	{
		this.gameScreen = new GameScreen(this, selectedCharacters, lp, m, actionResolver);
		setScreen(this.gameScreen);
	}

	public void setNumberOfPlayersSelectionScreen() {
		this.numberOfPlayersSelectionController = new NumberOfPlayersSelectionController(this, lp);
		setScreen(new NumberOfPlayersSelectionScreen(this.numberOfPlayersSelectionController));
	}

	public void setLocalCharacterSelectionScreen(int numberOfCharacters) {
		HashMap<Integer, Player> players = new HashMap<Integer, Player>();
		for(int i = 0; i<numberOfCharacters; i++) {
			Player player = new Player("Spieler " + (i+1));
			players.put(i, player);
		}
		this.characterSelectionController = new CharacterSelectionController(this, lp, players);
		setScreen(new CharacterSelectionScreen(this.characterSelectionController));
	}

	public void setRemoteCharacterSelectionScreen(ArrayList<String> nickNames, MalefizClient client) {
		HashMap<Integer, Player> players = new HashMap<Integer, Player>();
		for(int i = 0; i<nickNames.size(); i++) {
			Player player = new Player(nickNames.get(i));
			players.put(i, player);
		}
		this.gameclient = client;
		this.characterSelectionController = new CharacterSelectionController(this, lp, players, client);
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
		this.server = new MalefizServer();
		this.connectionController = new ConnectionController(this, server, lp);
		setScreen(new ConnectionScreen(this.connectionController, lp));
	}

	public void setNetworkMenuScreen() {
		setScreen(new NetworkMenuScreen(this, lp));
	}

	public void setConnectionClientScreen() {
		this.connectionClientController = new ConnectionClientController(this, lp);
		setScreen(new ConnectionClientScreen(this.connectionClientController, lp));
	}

	public void setLanguagePack(LanguagePack lang)
	{
		this.lp = lang;
	}

	public MalefizClient getMalefizClient()
	{
		return this.gameclient;
	}
}
