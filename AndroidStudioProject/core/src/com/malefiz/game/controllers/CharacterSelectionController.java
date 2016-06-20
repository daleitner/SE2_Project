package controllers;

import models.Avatar;
import models.Config;
import models.Mode;
import models.Player;
import models.Team;
import network.MalefizClient;
import network.MessageObject;
import network.MessageTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CharacterSelectionController {
    private MyMalefiz mainClass;
    private Config lp;
    private int selectedIndex = -1;
    private List<Avatar> characters;
    private HashMap<Integer, Player> selectedPlayers;
    private Mode mode;
    private int numberOfPlayers;
    private int actualPlayer;
    private MalefizClient client;

    public CharacterSelectionController(MyMalefiz mainClass, Config lp, HashMap<Integer, Player> players) {
        this.mainClass = mainClass;
        this.lp = lp;
        this.mode = Mode.LOCAL;
        this.numberOfPlayers = 0;
        if(players != null)
            this.numberOfPlayers = players.size();
        this.selectedPlayers = players;
        this.actualPlayer = 1;
        this.characters = new ArrayList<Avatar>();
        this.characters.add(new Avatar("avatar_red", "avatar_rot.png", "avatar_rot_disabled.png", 3, 5, 0));
        this.characters.add(new Avatar("avatar_yellow", "avatar_gelb.png", "avatar_gelb_disabled.png", 3, 10, 1));
        this.characters.add(new Avatar("avatar_green", "avatar_gruen.png", "avatar_gruen_disabled.png", 11, 10, 2));
        this.characters.add(new Avatar("avatar_blue", "avatar_blau.png", "avatar_blau_disabled.png", 11, 5, 3));
    }

    public CharacterSelectionController(MyMalefiz mainClass, Config lp, HashMap<Integer, Player> players, MalefizClient client) {
        this(mainClass, lp, players);
        this.mode = Mode.NETWORK;
        this.client = client;
    }

    public List<Avatar> getCharacters() {
        return this.characters;
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public HashMap<Integer, Player> getSelectedCharacters() {
        return this.selectedPlayers;
    }

    public Mode getMode() {
        return mode;
    }

    /**
     * Fügt einen Charakter zur Auswahl hinzu. Existiert bereits ein Eintrag für den aktuellen Spieler,
     * so wird der Eintrag entfernt und erneut eingefügt.
     * @param index     Index des Charakters
     */
    public void selectCharacter(int index) {
        String nickName = this.selectedPlayers.get(this.actualPlayer-1).getNickName();
        this.selectedPlayers.remove(this.actualPlayer-1);
        this.selectedPlayers.put(this.actualPlayer-1, new Player(nickName, Team.BLUE.getById(this.characters.get(index).getIndex()), this.actualPlayer-1, this.characters.get(index)));
    }

    /*public void deselectCharacter() {
        if(this.selectedCharacterIndicess.containsKey(this.actualPlayer-1))
            this.selectedCharacterIndicess.remove(this.actualPlayer-1);
    }*/

    /**
     * Überprüft, ob der Charakter schon ausgewählt wurde
     * @param index
     * @return
     */
    public boolean isCharacterSelected(int index)
    {
        return this.selectedPlayers.get(this.actualPlayer-1).getAvatar() == this.characters.get(index);
        //return selectedCharacterIndicess.containsKey(this.actualPlayer-1) && selectedCharacterIndicess.get(this.actualPlayer-1) == index;
    }

    public void handleCharacter(int index) {
        if(mode == Mode.NETWORK ) {
            if(this.selectedPlayers.get(this.actualPlayer-1).getNickName().equals(this.client.getNickName())) {
                selectCharacterIfPossible(index);
            }
        }
        else {
            selectCharacterIfPossible(index);
        }
    }

    private void selectCharacterIfPossible(int index) {
        if(isCharacterEnabled(index) && !isCharacterSelected(index))
            selectCharacter(index);
    }

    public boolean isCharacterEnabled(int index) {
        for(int i = 0; i<this.selectedPlayers.size(); i++) {
            if(i != this.actualPlayer-1 && this.selectedPlayers.get(i).getAvatar() == this.characters.get(index))
                return false;
        }
        return true;
    }

    public String getHeaderText() {
        return this.selectedPlayers.get(this.actualPlayer-1).getNickName() + lp.getText("choosecharacter");
    }

    public String getNextButtonText() {
        if(this.actualPlayer < this.numberOfPlayers)
            return lp.getText("next");
        return lp.getText("play");
    }

    public boolean canExecutePlayButton() {
        if(mode == Mode.NETWORK && !(this.selectedPlayers.get(this.actualPlayer-1).getNickName().equals(this.client.getNickName())))
            return false;
        boolean check = true;
        for(int i = 0; i<this.selectedPlayers.size() && check; i++) {
            if(this.selectedPlayers.get(i).getAvatar() == null)
                check = false;
        }
        return this.actualPlayer < this.numberOfPlayers || check;
    }

    public boolean canExecutePreviousButton() {
        return mode == Mode.LOCAL;
    }

    public Config getLanguagePack() {
        return this.lp;
    }

    public void switchToPreviousScreen() {
        if(this.actualPlayer > 1)
            this.actualPlayer -= 1;
        else
            this.mainClass.setNumberOfPlayersSelectionScreen();
    }

    public void switchToNextScreen() {
        if(this.actualPlayer < this.numberOfPlayers) {
            this.actualPlayer += 1;
        }
        else{
            if(mode == Mode.LOCAL)
            {
                this.mainClass.setGameScreen(this.mode, getSelectedCharacters());
            }
            else
            {
                this.mainClass.setGameScreen(this.mode, getSelectedCharacters(), client);
            }

            System.out.println(getSelectedCharacters().size());
        }

    }

    public void playButtonClicked() {
        if(this.mode == Mode.NETWORK) {
            ArrayList<String> info = new ArrayList<String>();
            info.add(String.valueOf(this.selectedPlayers.get(this.actualPlayer-1).getAvatar().getIndex()));
            MessageObject obj = new MessageObject(this.client.getNickName(), MessageTypeEnum.CharacterSelected, info);
            this.client.sendMessage(obj);
        }
        switchToNextScreen();
    }

    public void receiveMessage(){
        if(this.mode == Mode.NETWORK) {
            String msg = this.client.getReceivedMessage();
            if(!msg.isEmpty()) {
                MessageObject obj = MessageObject.MessageToMessageObject(msg);
                if(obj.getMessageType() == MessageTypeEnum.CharacterSelected) {
                    selectCharacterIfPossible(Integer.parseInt(obj.getInformation().get(0)));
                    switchToNextScreen();
                }
            }
        }
    }
}
