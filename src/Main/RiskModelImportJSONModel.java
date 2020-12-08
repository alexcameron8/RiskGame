package Main;

import java.util.ArrayList;

public class RiskModelImportJSONModel {
    private ArrayList<RiskModelPlayer> players = new ArrayList<RiskModelPlayer>();
    private int activePlayerID;
    private String currentMap;

    public ArrayList<RiskModelPlayer> getPlayers(){
        return players;
    }

    public int getActivePlayerID(){
        return activePlayerID;
    }

    public String getCurrentMap(){return currentMap;}
}

class RiskModelPlayer{
    private boolean isAi;
    private ArrayList<RiskModelTerritory> listOfTerritories = new ArrayList<RiskModelTerritory>();
    private String name;
    private int reinforcements;
    private int playerColorValue;

    public boolean isAi() {
        return isAi;
    }

    public ArrayList<RiskModelTerritory> getListOfTerritories() {
        return listOfTerritories;
    }

    public String getName() {
        return name;
    }

    public int getReinforcements() {
        return reinforcements;
    }

    public int getPlayerColorValue() {
        return playerColorValue;
    }
}

class RiskModelTerritory{
    private String name;
    private int soldiers;

    public String getName() {
        return name;
    }

    public int getSoldiers() {
        return soldiers;
    }
}
