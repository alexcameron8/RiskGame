package Main;

import java.util.ArrayList;
import java.util.List;

public class MapModel {
    private MapTerritory activeTerritory;
    private ArrayList<MapTerritory> territoryList;

    private List<MapViewListener> mapViewListenerList;



    MapModel(){
        MapImport mapImport = new MapImport("src/Main/worldmap.json");
        this.territoryList = mapImport.getTerritories();
        this.activeTerritory = null;
        this.mapViewListenerList = new ArrayList<>();
    }

    public void setActiveTerritoryByID(String id){
        for(MapTerritory terr: territoryList){
            if(terr.getId().equals(id)){
                System.out.println(terr.getId());
                activeTerritory = terr;
                break;
            }
        }
        updateMapListeners();
    }

    public ArrayList<MapTerritory> getTerritoryList(){
        return this.territoryList;
    }

    public MapTerritory getActiveTerritory() {
        return this.activeTerritory;
    }

    public void addMapListener(MapViewListener mvl){
        mapViewListenerList.add(mvl);
    }

    private void updateMapListeners(){
        for(MapViewListener mvl: mapViewListenerList){
            mvl.handleMapUpdate(new MapEvent(this));
        }
    }
}
