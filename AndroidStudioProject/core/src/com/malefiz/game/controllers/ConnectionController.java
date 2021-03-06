package controllers;


import java.util.ArrayList;
import java.util.Arrays;

import models.Config;
import network.MalefizClient;
import network.MalefizServer;
import network.MessageObject;
import network.MessageTypeEnum;

public class ConnectionController  {
    private MyMalefiz mainClass;
    private Config lp;
    private MalefizServer server;
    private MalefizClient client;
    private String playersString;

    public ConnectionController(MyMalefiz mainClass, MalefizServer server, Config lp) {
        this.mainClass = mainClass;
        this.lp = lp;
        this.server = server;
        this.playersString = "Players:";
    }

    public Config getLanguagePack() {
        return lp;
    }

    public void addClient(String playerName) {
        this.client = new MalefizClient(playerName, this.server.getIpAddress());
        this.client.sendMessage(new MessageObject(playerName, MessageTypeEnum.Connect, null));
    }

    public void switchToNetworkMenuScreen() {
        this.server.disconnect();
        this.mainClass.setNetworkMenuScreen();
    }

    public void switchToCharacterSelectionScreen() {
        this.server.stopWaitingForClients();
        int count = this.server.getConnectedPlayersCount();
        ArrayList<String>infos = new ArrayList<String>();
        infos.add(Integer.toString(count));
        this.server.sendMessage(new MessageObject(this.client.getNickName(), MessageTypeEnum.GoToCharacterSelection, infos));
        ArrayList<String> nickNames = new ArrayList<String>(Arrays.asList(this.server.getConnectedPlayers().split("\n")));
        this.mainClass.setRemoteCharacterSelectionScreen(nickNames, this.client);
    }

    public String getIpAddresses() {
        return this.server.getIpAddress();
    }

    public String getPlayersString() {
        String msg = this.server.getConnectedPlayers();
        this.playersString = "";
        if(!msg.isEmpty())
            this.playersString += msg + "\n";
        return this.playersString;
    }

    public void startWaitingForClients() {
        this.server.startWaitingForClients();
    }
}
