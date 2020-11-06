package Main;

import Map.*;
import Map.Territory;

import java.util.ArrayList;
import java.util.List;

public class MapModel {
    private Territory activeTerritory;
    private ArrayList<Territory> territoryList;

    private List<MapViewListener> mapViewListenerList;



    MapModel(){
        MapImport mapImport = new MapImport("src/Map/worldmap.json");
        this.territoryList = mapImport.getTerritories();
        this.activeTerritory = null;
        this.mapViewListenerList = new ArrayList<>();
    }

    public void setActiveTerritoryByID(String id){
        for(Territory terr: territoryList){
            if(terr.getId().equals(id)){
                System.out.println(terr.getId());
                activeTerritory = terr;
                updateMapListeners(terr);
                break;
            }
        }
    }

    public ArrayList<Territory> getTerritoryList(){
        return this.territoryList;
    }

    public Territory getActiveTerritory() {
        return this.activeTerritory;
    }

    public void addMapListener(MapViewListener mvl){
        mapViewListenerList.add(mvl);
    }

    private void updateMapListeners(Territory mapTerritory){
        for(MapViewListener mvl: mapViewListenerList){
            mvl.handleMapUpdate(new MapEvent(this, mapTerritory));
        }
    }
}
