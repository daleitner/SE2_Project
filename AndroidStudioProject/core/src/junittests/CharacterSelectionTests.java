
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
        CharacterSelectionController sut = new CharacterSelectionController(null, null, Mode.LOCAL);
        assertEquals(4, sut.getCharacters().size());
    }
    @Test
    public void AfterInitializingNoCharacterIsSelected() {
        CharacterSelectionController sut = new CharacterSelectionController(null, null, Mode.LOCAL);
        assertNull(sut.getSelectedCharacter());
    }

    @Test
    public void WhenClickingFirstCharacter_SelectFirstCharacter() {
        CharacterSelectionController sut = new CharacterSelectionController(null, null, Mode.LOCAL);
        sut.selectSingleCharacter(0);
        assertEquals(sut.getCharacters().get(0), sut.getSelectedCharacter());
    }

    @Test
    public void WhenClickingSecondCharacter_SelectSecondCharacter() {
        CharacterSelectionController sut = new CharacterSelectionController(null, null, Mode.LOCAL);
        sut.selectSingleCharacter(1);
        assertEquals(sut.getCharacters().get(1), sut.getSelectedCharacter());
    }

    @Test
    public void WhenClickingThirdCharacter_SelectThirdCharacter() {
        CharacterSelectionController sut = new CharacterSelectionController(null, null, Mode.LOCAL);
        sut.selectSingleCharacter(2);
        assertEquals(sut.getCharacters().get(2), sut.getSelectedCharacter());
    }

    @Test
    public void WhenClickingFourthCharacter_SelectFourthCharacter() {
        CharacterSelectionController sut = new CharacterSelectionController(null, null, Mode.LOCAL);
        sut.selectSingleCharacter(3);
        assertEquals(sut.getCharacters().get(3), sut.getSelectedCharacter());
    }
}
