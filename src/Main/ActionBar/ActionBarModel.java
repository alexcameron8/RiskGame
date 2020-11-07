package Main.ActionBar;
import java.util.*;

import Main.RiskModel;
import Main.RiskView;
import Main.Turn;
import Player.Player;


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

    public void attack(){
        return;
    }
    public void deployTroops(){
        return;
    }

    public void nextTurn(){
        return;
    }
}
