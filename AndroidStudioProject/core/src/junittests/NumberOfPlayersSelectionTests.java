
import controllers.CharacterSelectionController;
import controllers.NumberOfPlayersSelectionController;
import models.Mode;


import org.junit.Test;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Pezi on 24.05.2016.
 */

public class NumberOfPlayersSelectionTests {


    @Test
    public void AfterInitializingNoPayersAreSelected() {
        NumberOfPlayersSelectionController controller = new NumberOfPlayersSelectionController(null, null);
        assertEquals(4, controller.getSelectedNumberOfPlayers());
    }
    @Test
    public void FourPlayer(){
        NumberOfPlayersSelectionController controller = new NumberOfPlayersSelectionController(null, null);
        assertEquals(4, controller.getSelectedNumberOfPlayers());
    }
    @Test
    public void TwoPlayer(){
        NumberOfPlayersSelectionController controller = new NumberOfPlayersSelectionController(null, null);
        controller.DecreaseNumberOfPlayers();
        controller.DecreaseNumberOfPlayers();
        assertEquals(2, controller.getSelectedNumberOfPlayers());
    }
    @Test
    public void TreePlayer(){
        NumberOfPlayersSelectionController controller = new NumberOfPlayersSelectionController(null, null);
        controller.DecreaseNumberOfPlayers();
        assertEquals(3, controller.getSelectedNumberOfPlayers());
    }

    @Test
    public void MaxPlayer(){
        NumberOfPlayersSelectionController controller = new NumberOfPlayersSelectionController(null, null);
        controller.IncreaseNumberOfPlayers();
        assertEquals(4, controller.getSelectedNumberOfPlayers());
    }
    @Test
    public void MinPlayer(){
        NumberOfPlayersSelectionController controller = new NumberOfPlayersSelectionController(null, null);
        controller.DecreaseNumberOfPlayers();
        controller.DecreaseNumberOfPlayers();
        controller.DecreaseNumberOfPlayers();
        controller.DecreaseNumberOfPlayers();
        assertEquals(2, controller.getSelectedNumberOfPlayers());
    }
    @Test
    public void ThreePlayerWithIncrease(){
        NumberOfPlayersSelectionController controller = new NumberOfPlayersSelectionController(null, null);
        controller.DecreaseNumberOfPlayers();
        controller.DecreaseNumberOfPlayers();
        controller.DecreaseNumberOfPlayers();
        controller.IncreaseNumberOfPlayers();
        assertEquals(3, controller.getSelectedNumberOfPlayers());
    }






}
