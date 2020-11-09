package Main.ActionBar;
import java.util.*;

import Main.RiskModel;
import Main.RiskView;
import Main.Turn;
import Player.Player;
import Map.*;

public class ActionBarModel {

    private List<ActionBarView> actionBarViews;
    private RiskModel riskModel;
    private RiskView riskView;

    public ActionBarModel(RiskModel riskModel, RiskView riskView){
        this.riskModel = riskModel;
        this.riskView = riskView;
        actionBarViews = new ArrayList<>();
    }

    public RiskModel getRiskModel(){
        return riskModel;
    }

    public RiskView getRiskView() {
        return riskView;
    }

    public void addActionBarModelViews(ActionBarView abv){
        actionBarViews.add(abv);
    }

    public void removeActionBarModelViews(ActionBarView abv){
        actionBarViews.remove(abv);
    }

    public boolean attack(Territory attackerTerritory,Player defender, Territory defenderTerritory,int numOfAttack){
        return riskModel.getActivePlayer().attack(attackerTerritory, defender,defenderTerritory, numOfAttack);
    }
    public void deployTroops(Territory territory, int numOfTroops){
        for(ActionBarView actionBarView : actionBarViews){
            actionBarView.handleTroopDeployment(new ActionBarEvent(this,riskModel.getActivePlayer().getReinforcements()));
        }
        if(numOfTroops>0) {
            riskModel.getActivePlayer().placeReinforcement(territory, numOfTroops);
        }
    }

    public void nextTurn(RiskModel riskModel){
            this.riskModel.advanceTurn();
    }
}
