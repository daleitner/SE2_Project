package controllers;


import java.util.ArrayList;

import models.LanguagePack;
import models.Mode;
import network.MalefizClient;
import network.MalefizServer;
import network.MessageObject;
import network.MessageTypeEnum;

public class ConnectionController  {
    private MyMalefiz mainClass;
    private LanguagePack lp;
    private MalefizServer server;
    private MalefizClient client;
    private String playersString;

    public ConnectionController(MyMalefiz mainClass, MalefizServer server, LanguagePack lp) {
        this.mainClass = mainClass;
        this.lp = lp;
        this.server = server;
        this.playersString = "Players:";
    }

    public LanguagePack getLanguagePack() {
        return lp;
    }

    public void addClient(String playerName) {
        this.client = new MalefizClient(playerName, this.server.getIpAddress());
        this.client.sendMessage(new MessageObject(playerName, MessageTypeEnum.Connect, null).getMessage());
    }

    public void switchToNetworkMenuScreen() {
        this.server.disconnect();
        this.mainClass.setNetworkMenuScreen();
    }

    public void switchToCharacterSelectionScreen() {
        int count = this.server.getConnectedPlayersCount();
        ArrayList<String>infos = new ArrayList<String>();
        infos.add(Integer.toString(count));
        this.server.sendMessage(new MessageObject(this.client.getNickName(), MessageTypeEnum.GoToCharacterSelection, infos).getMessage());
        this.mainClass.setCharacterSelectionScreen(Mode.NETWORK, count);
    }

    public String getIpAddresses() {
        return this.server.getIpAddress();
    }

    public String getPlayersString() {
        String msg = this.server.getConnectedPlayers();
        this.playersString = "Players:";
        if(!msg.isEmpty())
            this.playersString += "\n" + msg;
        return this.playersString;
    }

    public void startWaitingForClients() {
        this.server.startWaitingForClients();
    }
}
