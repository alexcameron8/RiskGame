package Main.ActionBar;
import java.util.*;

import Main.Turn;
import Player.Player;


public class ActionBarModel {

    private Player currPlayer;
    private List<ActionBarView> actionBarViews;

    public ActionBarModel(){
        actionBarViews = new ArrayList<>();
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
        new Turn(currPlayer);
    }
}
