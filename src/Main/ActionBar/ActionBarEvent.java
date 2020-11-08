package Main.ActionBar;
import jdk.jfr.Event;

import java.util.EventObject;

public class ActionBarEvent extends EventObject {

    private int reinforcements;

    public ActionBarEvent(ActionBarModel actionBarModel,int reinforcements ) {
        super(actionBarModel);
        this.reinforcements = reinforcements;
    }
    public int getReinforcements(){
        return reinforcements;
    }
}

