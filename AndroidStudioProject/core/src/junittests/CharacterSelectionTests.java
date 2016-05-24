
import junit.framework.Assert;

import controllers.CharacterSelectionController;
import models.Mode;


import org.junit.Test;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by MCLeite on 17.05.2016.
 */
public class CharacterSelectionTests {

    @Test
    public void AfterInitializingFourCharactersAreCreated() {
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 1);
        assertEquals(4, sut.getCharacters().size());
    }
    @Test
    public void AfterInitializingNoCharacterIsSelected() {
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 1);
        assertEquals(0, sut.getSelectedCharacters().size());
    }

    @Test
    public void WhenClickingFirstCharacter_SelectFirstCharacter() {
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 1);
        sut.selectCharacter(0);
        assertEquals(sut.getCharacters().get(0), sut.getSelectedCharacters().get(0));
    }

    @Test
    public void WhenClickingSecondCharacter_SelectSecondCharacter() {
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 1);
        sut.selectCharacter(1);
        assertEquals(sut.getCharacters().get(1), sut.getSelectedCharacters().get(0));
    }

    @Test
    public void WhenClickingThirdCharacter_SelectThirdCharacter() {
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 1);
        sut.selectCharacter(2);
        assertEquals(sut.getCharacters().get(2), sut.getSelectedCharacters().get(0));
    }

    @Test
    public void WhenClickingFourthCharacter_SelectFourthCharacter() {
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 1);
        sut.selectCharacter(3);
        assertEquals(sut.getCharacters().get(3), sut.getSelectedCharacters().get(0));
    }

    @Test
    public void CheckNumberOfPlayersTwoPlayer(){
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 2);
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
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 4);
        sut.selectCharacter(2);
        assertEquals(sut.isCharacterSelected(2), true);
        assertEquals(sut.isCharacterEnabled(2),true);
        sut.switchToNextScreen();
        sut.selectCharacter(1);
        sut.selectCharacter(3);
        assertEquals(sut.isCharacterSelected(1), false);
        assertEquals(sut.isCharacterSelected(3), true);
        assertEquals(sut.isCharacterEnabled(4),true);
        assertEquals(sut.isCharacterEnabled(3),true);
        assertEquals(sut.isCharacterEnabled(2),false);
        assertEquals(sut.isCharacterEnabled(1),true);
        sut.switchToNextScreen();
        sut.selectCharacter(1);
        sut.selectCharacter(4);
        assertEquals(sut.isCharacterSelected(1), false);
        assertEquals(sut.isCharacterSelected(4), true);
        assertEquals(sut.isCharacterEnabled(1),true);
        assertEquals(sut.isCharacterEnabled(2),false);
        assertEquals(sut.isCharacterEnabled(3),false);
        assertEquals(sut.isCharacterEnabled(4),true);
        sut.switchToNextScreen();
        sut.selectCharacter(1);
        assertEquals(sut.isCharacterSelected(1), true);
        assertEquals(sut.isCharacterEnabled(1),true);
        assertEquals(sut.isCharacterEnabled(2),false);
        assertEquals(sut.isCharacterEnabled(3),false);
        assertEquals(sut.isCharacterEnabled(4),false);

        assertEquals(sut.canExecutePlayButton(),true);


    }
    @Test
    public void UnFinishSelectionCheckNumberOfPlayersFourPlayer(){
        CharacterSelectionController sut = CharacterSelectionController.getInstance();
        sut.init(null, null, Mode.LOCAL, 4);
        sut.selectCharacter(2);

        //button next or play can be executed
        assertEquals(sut.canExecutePlayButton(),true);

        sut.switchToNextScreen();
        sut.selectCharacter(4);

        //button next or play can be executed
        assertEquals(sut.canExecutePlayButton(),true);

        sut.switchToNextScreen();
        sut.selectCharacter(1);

        //button next or play can be executed
        assertEquals(sut.canExecutePlayButton(),true);

        sut.switchToNextScreen();
        sut.selectCharacter(3);

        //button next or play can be executed
        assertEquals(sut.canExecutePlayButton(),true);

        assertEquals(sut.isCharacterSelected(3), true);
        assertEquals(sut.isCharacterSelected(2), false);
        assertEquals(sut.isCharacterSelected(1), false);
        assertEquals(sut.isCharacterSelected(4), false);
        assertEquals(sut.isCharacterEnabled(2),false);
        assertEquals(sut.isCharacterEnabled(3),true);//current selected character - is also enabled
        assertEquals(sut.isCharacterEnabled(1),false);

    }

}
