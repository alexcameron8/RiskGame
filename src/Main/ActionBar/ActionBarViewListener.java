package Main.ActionBar;

import Main.ActionBar.ActionBarEvent;
import Main.MapEvent;
import Main.MapViewListener;
import Main.RiskModel;
import Map.Territory;

public class ActionBarViewListener implements MapViewListener {
    private Territory territory;

    public ActionBarViewListener(RiskModel riskModel){
        //probs need to delete this class.
    }
    @Override
    public void handleMapUpdate(MapEvent e) {
        Territory territory = (e.getMapTerritory());

    }
    public Territory getTerritory(){
        return territory;
    }
}
