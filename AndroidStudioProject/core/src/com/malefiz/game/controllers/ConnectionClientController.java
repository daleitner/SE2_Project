package controllers;

import java.util.ArrayList;
import java.util.Arrays;

import models.Config;
import network.MalefizClient;
import network.MessageObject;
import network.MessageTypeEnum;

public class ConnectionClientController {
    private MyMalefiz mainClass;
    private Config lp;
    private MalefizClient client;
    private String playersString;
    private String serverIPAddress;
    private String nickName;

    public ConnectionClientController(MyMalefiz mainClass, Config lp) {
        this.mainClass = mainClass;
        this.lp = lp;
        this.playersString = "";
    }

    public Config getLanguagePack() {
        return lp;
    }

    public void switchToNetworkMenuScreen() {
        if(this.client != null)
            this.client.disconnect();
        this.mainClass.setNetworkMenuScreen();
    }

    public void switchToCharacterSelectionScreen(int playerCount) {
        ArrayList<String> nickNames = new ArrayList<String>(Arrays.asList(this.playersString.split("\n")));
        this.mainClass.setRemoteCharacterSelectionScreen(nickNames, this.client);
    }

    public String getServerIPAddress() {
        return serverIPAddress;
    }

    public void setServerIPAddress(String serverIPAddress) {
        this.serverIPAddress = serverIPAddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void receiveMessage() {
        if(this.client == null)
            return;

        String msg = this.client.getReceivedMessage();
        if(!msg.isEmpty()) {
            MessageObject obj = MessageObject.MessageToMessageObject(msg);
            switch(obj.getMessageType()) {
                case Connect:
                    this.playersString = "";
                    for(String str:obj.getInformation())
                        this.playersString += str + "\n";
                    this.playersString = this.playersString.substring(0, this.playersString.length()-1);
                    break;
                case GoToCharacterSelection:
                    switchToCharacterSelectionScreen(Integer.parseInt(obj.getInformation().get(0)));
                    break;
            }
        }
    }

    public String getPlayersString() {
        return this.playersString;
    }

    public void connect() {
        if(this.client == null)
            this.client = new MalefizClient(this.nickName, this.serverIPAddress);
        client.sendMessage(new MessageObject(this.nickName, MessageTypeEnum.Connect, null));
    }
}
