package controllers;

import models.LanguagePack;
import models.Mode;
import network.MalefizClient;

public class ConnectionClientController {
    private MyMalefiz mainClass;
    private LanguagePack lp;
    private MalefizClient client;
    private String playersString;
    private String serverIPAddress;

    public ConnectionClientController(MyMalefiz mainClass, LanguagePack lp) {
        this.mainClass = mainClass;
        this.lp = lp;
        //this.client = new MalefizClient();
        this.playersString = "Players:";
    }

    public LanguagePack getLanguagePack() {
        return lp;
    }

    public void switchToNetworkMenuScreen() {
        if(this.client != null)
            this.client.disconnect();
        this.mainClass.setNetworkMenuScreen();
    }

    public void switchToCharacterSelectionScreen() {
        this.mainClass.setCharacterSelectionScreen(Mode.NETWORK, 2);
    }

    public String getServerIPAddress() {
        return serverIPAddress;
    }

    public void setServerIPAddress(String serverIPAddress) {
        this.serverIPAddress = serverIPAddress;
    }

    public String getPlayersString() {
        if(this.client == null)
            return this.playersString;

        String msg = this.client.getReceivedMessage();
        if(!msg.isEmpty())
            this.playersString += "\n" + msg;
        return this.playersString;
    }

    public void connect() {
        if(this.client == null)
            this.client = new MalefizClient(this.serverIPAddress);
        client.sendMessage("connect\n");
    }
}
