package Main;

import Map.*;
import Map.Territory;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapModel {
    private Territory activeTerritory;
    private RiskModel riskModel;
    private static PathParser pathParser = new PathParser();
    private static AWTPathProducer pathProducer = new AWTPathProducer();
    private ArrayList<Shape> waterCrossings;
    private Color backgroundColor;

    private List<MapViewListener> mapViewListenerList;
    

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

    public void setActiveTerritoryByID(String id){
        for(Territory terr: this.riskModel.getMap().getTerritories()){
            if(terr.getId().equals(id)){
                System.out.println(terr.getId());
                activeTerritory = terr;
                updateMapListeners(terr);
                break;
            }
        }
    }

    public ArrayList<Territory> getTerritoryList(){
        return this.riskModel.getMap().getTerritories();
    }

    public Territory getActiveTerritory() {
        return this.activeTerritory;
    }

    public ArrayList<Shape> getWaterCrossings() {


        return this.waterCrossings;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
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
