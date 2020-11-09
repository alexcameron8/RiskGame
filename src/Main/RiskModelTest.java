package Main;

import Map.Territory;
import Player.Player;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class RiskModelTest {

    RiskModel rm;

    @Test
    public void testCreateMap(){
        rm= new RiskModel();
        assertNotEquals(rm.getMap(),null);
    }

    @Test
    public void testAddPlayer(){
        rm= new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        assertNotNull(rm.getPlayerByName("Thomas"));
    }

    @Test
    public void testInitialTroops(){
        rm= new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        rm.addPlayer(new Player("Alex", Color.RED));
        rm.play();
        int numberOfSoldiers = 0;
        for(Territory terr : rm.getPlayerByName("Thomas").getListOfTerritories()){
            numberOfSoldiers += terr.getSoldiers();
        }
        assertEquals(40, numberOfSoldiers);
    }

    @Test
    public void testAssignTerritories(){
        rm= new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        rm.addPlayer(new Player("Alex", Color.RED));
        rm.play();
        assertEquals(rm.getMap().getTerritories().size()/2,rm.getPlayerByName("Thomas").getListOfTerritories().size());
    }
    @Test
    public void testStartingReinforcements(){
        rm= new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        rm.addPlayer(new Player("Alex", Color.RED));
        rm.play();
        assertNotEquals(rm.getActivePlayer().getReinforcements(),0);
    }
    @Test
    public void testPlaceReinforcements(){
        rm= new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        rm.addPlayer(new Player("Alex", Color.RED));
        rm.play();
        int previous = rm.getActivePlayer().getReinforcements();
        int previousTerr = rm.getActivePlayer().getListOfTerritories().get(0).getSoldiers();
        rm.getActivePlayer().placeReinforcement(rm.getActivePlayer().getListOfTerritories().get(0),previous-1);
        //Test the number of Reinforcements left
        assertEquals(1,rm.getActivePlayer().getReinforcements());
        //Check the number of placed Reinforcments
        assertEquals(previousTerr+previous-1,rm.getActivePlayer().getListOfTerritories().get(0).getSoldiers());
        //Check can't place more
        assertFalse(rm.getActivePlayer().canPlaceReinforcement(rm.getActivePlayer().getListOfTerritories().get(0),2));
    }

    @Test
    public void testAttack() {
        rm = new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        rm.addPlayer(new Player("Alex", Color.RED));
        rm.play();
        Territory attacking = null;
        Territory defending = null;
        for (Territory terr: rm.getActivePlayer().getListOfTerritories()){
            for(Territory neighbour: terr.getNeighbours()) {
                if (!rm.getActivePlayer().hasTerritory(neighbour)) {
                    attacking = terr;
                    defending = neighbour;
                    break;
                }
            }
        }
        //test too many soldiers
        assertFalse(rm.getActivePlayer().attack(attacking,defending.getOwner(),defending, attacking.getSoldiers()));
        //test successful attack
        int a = rm.getActivePlayer().getListOfTerritories().size();
        int d = defending.getOwner().getListOfTerritories().size();
        Player dP = defending.getOwner();
        if(rm.getActivePlayer().attack(attacking,defending.getOwner(),defending, attacking.getSoldiers()-1)){
            //test transfer of territories
            assertEquals(a+1,rm.getActivePlayer().getListOfTerritories().size());
            assertEquals(d-1,dP.getListOfTerritories().size());
        }
        else{
            //test transfer of territories
            assertEquals(a,rm.getActivePlayer().getListOfTerritories().size());
            assertEquals(d,defending.getOwner().getListOfTerritories().size());
        }
    }

    @Test
    public void testNextTurn(){
        rm= new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        rm.addPlayer(new Player("Alex", Color.RED));
        rm.play();
        rm.getActivePlayer().placeReinforcement(rm.getActivePlayer().getListOfTerritories().get(0),rm.getActivePlayer().getReinforcements());
        int previousActivePlayerId = rm.getActivePlayerID();
        rm.advanceTurn();
        assertNotEquals(previousActivePlayerId,rm.getActivePlayerID());
    }

    @Test
    public void testEliminatePlayer(){
        rm= new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        rm.addPlayer(new Player("Alex", Color.RED));
        rm.addPlayer(new Player("Ben", Color.BLUE));
        rm.play();
        rm.getActivePlayer().placeReinforcement(rm.getActivePlayer().getListOfTerritories().get(0),rm.getActivePlayer().getReinforcements());
        rm.getActivePlayer().transferAllTerritory(rm.getPlayers().get(rm.getActivePlayerID()+1==rm.getPlayers().size()? 0:rm.getActivePlayerID()+1));
        int previousNumberOfPlayers = rm.getPlayers().size();
        rm.advanceTurn();
        assertEquals(previousNumberOfPlayers-1,rm.getPlayers().size());
    }

    @Test
    public void testWinPlayer(){
        rm= new RiskModel();
        rm.addPlayer(new Player("Thomas", Color.BLACK));
        rm.addPlayer(new Player("Alex", Color.RED));
        rm.play();
        rm.getActivePlayer().placeReinforcement(rm.getActivePlayer().getListOfTerritories().get(0),rm.getActivePlayer().getReinforcements());
        rm.getActivePlayer().transferAllTerritory(rm.getPlayers().get(rm.getActivePlayerID()+1==rm.getPlayers().size()? 0:rm.getActivePlayerID()+1));
        int previousNumberOfPlayers = rm.getPlayers().size();
        rm.advanceTurn();
        assertEquals(previousNumberOfPlayers-1,rm.getPlayers().size());
    }
}