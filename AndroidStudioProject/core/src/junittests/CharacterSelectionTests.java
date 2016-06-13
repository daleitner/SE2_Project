
import junit.framework.Assert;

import controllers.CharacterSelectionController;
import models.Mode;
import models.Player;


import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by MCLeite on 17.05.2016.
 */
public class CharacterSelectionTests {

    @Test
    public void AfterInitializingFourCharactersAreCreated() {
        CharacterSelectionController sut = new CharacterSelectionController(null, null, null);
        assertEquals(4, sut.getCharacters().size());
    }

    @Test
    public void WhenClickingFirstCharacter_SelectFirstCharacter() {
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for(int i = 0; i<4; i++) {
            Player player = new Player("Player" + String.valueOf(i));
            players.put(i, player);
        }
        CharacterSelectionController sut = new CharacterSelectionController(null, null,players);
        sut.selectCharacter(0);
        assertEquals(sut.getCharacters().get(0), sut.getSelectedCharacters().get(0).getAvatar());
    }

    @Test
    public void WhenClickingSecondCharacter_SelectSecondCharacter() {
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for(int i = 0; i<4; i++) {
            Player player = new Player("Player" + String.valueOf(i));
            players.put(i, player);
        }
        CharacterSelectionController sut = new CharacterSelectionController(null, null, players);
        sut.selectCharacter(1);
        assertEquals(sut.getCharacters().get(1), sut.getSelectedCharacters().get(0).getAvatar());
    }

    @Test
    public void WhenClickingThirdCharacter_SelectThirdCharacter() {
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for(int i = 0; i<4; i++) {
            Player player = new Player("Player" + String.valueOf(i));
            players.put(i, player);
        }
        CharacterSelectionController sut = new CharacterSelectionController(null, null, players);
        sut.selectCharacter(2);
        assertEquals(sut.getCharacters().get(2), sut.getSelectedCharacters().get(0).getAvatar());
    }

    @Test
    public void WhenClickingFourthCharacter_SelectFourthCharacter() {
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for(int i = 0; i<4; i++) {
            Player player = new Player("Player" + String.valueOf(i));
            players.put(i, player);
        }
        CharacterSelectionController sut = new CharacterSelectionController(null, null, players);
        sut.selectCharacter(3);
        assertEquals(sut.getCharacters().get(3), sut.getSelectedCharacters().get(0).getAvatar());
    }

    @Test
    public void CheckNumberOfPlayersTwoPlayer(){
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for(int i = 0; i<2; i++) {
            Player player = new Player("Player" + String.valueOf(i));
            players.put(i, player);
        }
        CharacterSelectionController sut = new CharacterSelectionController(null, null, players);
        sut.selectCharacter(3);
        assertEquals(sut.isCharacterSelected(3), true);
        assertEquals(sut.isCharacterEnabled(3),true);

        assertEquals(sut.isCharacterSelected(1), false);
        assertEquals(sut.isCharacterEnabled(1),true);

        sut.switchToNextScreen();
        sut.selectCharacter(2);

        assertEquals(sut.isCharacterSelected(2), true);
        assertEquals(sut.isCharacterEnabled(2),true);
        assertEquals(sut.isCharacterEnabled(3),false);
        assertEquals(sut.isCharacterEnabled(1),true);

        assertEquals(sut.canExecutePlayButton(),true);
    }
    @Test
    public void CheckNumberOfPlayersFourPlayer(){
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for(int i = 0; i<4; i++) {
            Player player = new Player("Player" + String.valueOf(i));
            players.put(i, player);
        }
        CharacterSelectionController sut = new CharacterSelectionController(null, null, players);
        sut.selectCharacter(2);
        assertEquals(sut.isCharacterSelected(2), true);
        assertEquals(sut.isCharacterEnabled(2),true);
        sut.switchToNextScreen();
        sut.selectCharacter(1);
        sut.selectCharacter(3);
        assertEquals(sut.isCharacterSelected(1), false);
        assertEquals(sut.isCharacterSelected(3), true);
        assertEquals(sut.isCharacterEnabled(3),true);
        assertEquals(sut.isCharacterEnabled(2),false);
        assertEquals(sut.isCharacterEnabled(1),true);
        assertEquals(sut.isCharacterEnabled(0),true);
        sut.switchToNextScreen();
        sut.selectCharacter(0);
        sut.selectCharacter(1);
        assertEquals(sut.isCharacterSelected(0), false);
        assertEquals(sut.isCharacterSelected(1), true);
        assertEquals(sut.isCharacterEnabled(0),true);
        assertEquals(sut.isCharacterEnabled(2),false);
        assertEquals(sut.isCharacterEnabled(3),false);
        assertEquals(sut.isCharacterEnabled(1),true);
        sut.switchToNextScreen();
        sut.selectCharacter(0);
        assertEquals(sut.isCharacterSelected(0), true);
        assertEquals(sut.isCharacterEnabled(0),true);
        assertEquals(sut.isCharacterEnabled(2),false);
        assertEquals(sut.isCharacterEnabled(3),false);
        assertEquals(sut.isCharacterEnabled(1),false);

        assertEquals(sut.canExecutePlayButton(),true);


    }
    @Test
    public void UnFinishSelectionCheckNumberOfPlayersFourPlayer(){
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for(int i = 0; i<4; i++) {
            Player player = new Player("Player" + String.valueOf(i));
            players.put(i, player);
        }
        CharacterSelectionController sut = new CharacterSelectionController(null, null, players);
        sut.selectCharacter(1);

        //button next or play can be executed
        assertEquals(sut.canExecutePlayButton(),true);

        sut.switchToNextScreen();
        sut.selectCharacter(3);

        //button next or play can be executed
        assertEquals(sut.canExecutePlayButton(),true);

        sut.switchToNextScreen();
        sut.selectCharacter(0);

        //button next or play can be executed
        assertEquals(sut.canExecutePlayButton(),true);

        sut.switchToNextScreen();
        sut.selectCharacter(2);

        //button next or play can be executed
        assertEquals(sut.canExecutePlayButton(),true);

        assertEquals(sut.isCharacterSelected(2), true);
        assertEquals(sut.isCharacterSelected(1), false);
        assertEquals(sut.isCharacterSelected(0), false);
        assertEquals(sut.isCharacterSelected(3), false);
        assertEquals(sut.isCharacterEnabled(1),false);
        assertEquals(sut.isCharacterEnabled(2),true);//current selected character - is also enabled
        assertEquals(sut.isCharacterEnabled(0),false);

    }

}
