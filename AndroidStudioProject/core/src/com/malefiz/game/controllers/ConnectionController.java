package controllers;


import models.LanguagePack;
import models.Mode;
import network.MalefizServer;

public class ConnectionController  {
    private MyMalefiz mainClass;
    private LanguagePack lp;
    private MalefizServer server;
    private String playersString;

    public ConnectionController(MyMalefiz mainClass, LanguagePack lp) {
        this.mainClass = mainClass;
        this.lp = lp;
        this.server = new MalefizServer();
        this.playersString = "Players:";
    }

    public LanguagePack getLanguagePack() {
        return lp;
    }

    public void switchToNetworkMenuScreen() {
        this.server.disconnect();
        this.mainClass.setNetworkMenuScreen();
    }

    public void switchToCharacterSelectionScreen() {
        this.mainClass.setCharacterSelectionScreen(Mode.NETWORK, 2);
    }

    public String getIpAddresses() {
        return this.server.getIpAddresses();
    }

    public String getPlayersString() {
        String msg = this.server.getReceivedMessage();
        if(!msg.isEmpty())
            this.playersString += "\n" + msg;
        return this.playersString;
    }

    public void startWaitingForClients() {
        this.server.startWaitingForClients();
    }
}
