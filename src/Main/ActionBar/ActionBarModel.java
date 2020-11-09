package Main.ActionBar;
import java.util.*;

import Main.RiskModel;
import Main.RiskView;
import Player.Player;
import Map.*;

/**
 * This is the action bar model which calls methods from the current RiskModel
 */
public class ActionBarModel {

    private List<ActionBarView> actionBarViews;
    private RiskModel riskModel;
    private RiskView riskView;

    /**
     * Constructor gets the current RiskModel, RiskView classes and initializes a new ArrayList of listeners which are updated through events
     * @param riskModel The current RiskModel of the Risk game
     * @param riskView The current RiskView of the Risk game
     */
    public ActionBarModel(RiskModel riskModel, RiskView riskView){
        this.riskModel = riskModel;
        this.riskView = riskView;
        actionBarViews = new ArrayList<>();
    }

    /**
     * This method gets the RiskModel
     * @return The current risk model
     */
    public RiskModel getRiskModel(){
        return riskModel;
    }

    /**
     * Adds new listeners to the list of listeners called actionBarViews
     * @param abv The new view that is added to the listeners
     */
    public void addActionBarModelViews(ActionBarView abv){
        actionBarViews.add(abv);
    }

    /**
     * Removes a listener from the list of listeners called actionBarViews
     * @param abv the view being removed
     */
    public void removeActionBarModelViews(ActionBarView abv){
        actionBarViews.remove(abv);
    }

    /**
     * This class calls the attack method from the Player class on the active riskModel
     * @param attackerTerritory The attackers attacking territory
     * @param defender The defending Player
     * @param defenderTerritory The defending players territory
     * @param numOfAttack The number of troops attacking the other territory
     * @return A boolean value of true if the attack is successful, otherwise false.
     */
    public boolean attack(Territory attackerTerritory,Player defender, Territory defenderTerritory,int numOfAttack){
        return riskModel.getActivePlayer().attack(attackerTerritory, defender,defenderTerritory, numOfAttack);
    }

    /**
     *This method access' the current risk model and places the certain reinforcements in the specified territory
     * @param territory The territory which the troops are placed in
     * @param numOfTroops The number of troops placed in the territory
     */
    public void deployTroops(Territory territory, int numOfTroops){
        if(riskModel.getActivePlayer().canPlaceReinforcement(territory,numOfTroops)) {
            riskModel.getActivePlayer().placeReinforcement(territory, numOfTroops);
        }
    }

    /**
     * Advances to the next turn
     * @param riskModel The current risk model
     */
    public void nextTurn(RiskModel riskModel){
            riskModel.advanceTurn();
    }
}
