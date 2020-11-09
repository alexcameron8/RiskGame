package Main.Map;

import Main.RiskModel;
import Map.Territory;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for the MapView
 *
 * @author Benjamin Munro
 */
public class MapModel {
    private Territory activeTerritory;
    private RiskModel riskModel;
    private static PathParser pathParser = new PathParser();
    private static AWTPathProducer pathProducer = new AWTPathProducer();
    private ArrayList<Shape> waterCrossings;
    private Color backgroundColor;
    private boolean territoryNamesVisible;
    private boolean troopDotsVisible;
    private boolean troopCountVisible;
    private boolean playerTerritoryColorVisible;

    private List<MapViewListener> mapViewListenerList;

    /**
     * Create a new MapModel
     * @param riskModel Risk model containing shared game state.
     */
    MapModel(RiskModel riskModel){
        pathParser.setPathHandler(pathProducer);
        this.riskModel = riskModel;
        this.activeTerritory = null;
        this.mapViewListenerList = new ArrayList<>();
        this.backgroundColor = new Color(
                this.riskModel.getMap().getBackgroundColor().get(0),
                this.riskModel.getMap().getBackgroundColor().get(1),
                this.riskModel.getMap().getBackgroundColor().get(2)
        );

        this.waterCrossings = new ArrayList<>();
        for(String waterCrossingString: this.riskModel.getMap().getWaterCrossings()){
            pathParser.parse(waterCrossingString);
            this.waterCrossings.add(pathProducer.getShape());
        }
    }

    /**
     * Sets the active territory by searching through the list of territories to find a matching id
     * @param id ID representing the territory to set as active territory.
     */
    public void setActiveTerritoryByID(String id){
        for(Territory terr: this.riskModel.getMap().getTerritories()){
            if(terr.getId().equals(id)){
                activeTerritory = terr;
                updateMapListeners(new MapTerritoryEvent(this, terr));
                break;
            }
        }
    }

    /**
     * Return territory list
     * @return List of territories for the current map
     */
    public ArrayList<Territory> getTerritoryList(){
        return this.riskModel.getMap().getTerritories();
    }

    /**
     * Gets the active territory. May be null if no territory has been selected.
     * @return The active territory.
     */
    public Territory getActiveTerritory() {
        return this.activeTerritory;
    }

    /**
     * Gets the list of water crossing shapes from the map.
     * @return List of water crossing shapes.
     */
    public ArrayList<Shape> getWaterCrossings() { return this.waterCrossings; }

    /**
     * Gets the background color of the map.
     * @return Background color.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Adds a MVL to be updated with MapEvents. Events are triggered when a territory is clicked.
     * @param mvl Listener to update on map events.
     */
    public void addMapListener(MapViewListener mvl){
        mapViewListenerList.add(mvl);
    }

    public boolean isTerritoryNamesVisible() {
        return territoryNamesVisible;
    }

    public void setTerritoryNamesVisible(boolean territoryNamesVisible) {
        this.territoryNamesVisible = territoryNamesVisible;
        updateMapListeners(new MapRedrawEvent(this));
    }

    public boolean isTroopDotsVisible() {
        return troopDotsVisible;
    }

    public void setTroopDotsVisible(boolean troopDotsVisible) {
        this.troopDotsVisible = troopDotsVisible;
        updateMapListeners(new MapRedrawEvent(this));
    }

    public boolean isTroopCountVisible() {
        return troopCountVisible;
    }

    public void setTroopCountVisible(boolean troopCountVisible) {
        this.troopCountVisible = troopCountVisible;
        updateMapListeners(new MapRedrawEvent(this));
    }

    public boolean isPlayerTerritoryColorVisible() {
        return playerTerritoryColorVisible;
    }

    public void setPlayerTerritoryColorVisible(boolean playerTerritoryColorVisible) {
        this.playerTerritoryColorVisible = playerTerritoryColorVisible;
        updateMapListeners(new MapRedrawEvent(this));
    }

    /**
     * Update all registered listeners on map events.
     * @param mapEvent Event to send.
     */
    private void updateMapListeners(MapEvent mapEvent){
        for(MapViewListener mvl: mapViewListenerList){
            mvl.handleMapUpdate(mapEvent);
        }
    }
}
