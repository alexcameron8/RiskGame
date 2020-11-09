package Main.ActionBar;
import jdk.jfr.Event;

import java.util.EventObject;

/**
 * This class is an event class that gets the events of the action bar model class.
 *
 * @Author Alex Cameron
 */
public class ActionBarEvent extends EventObject {

    private int reinforcements;

    /**
     * Constructor which gets the number of reinforcements from the action bar model
     * @param actionBarModel The action bar model
     * @param reinforcements The number of reinforcements being deployed
     */
    public ActionBarEvent(ActionBarModel actionBarModel,int reinforcements ) {
        super(actionBarModel);
        this.reinforcements = reinforcements;
    }

    /**
     * Getter method for the number of reinforcements being deployed
     * @return Number of reinforcements deployed
     */
    public int getReinforcements(){
        return reinforcements;
    }
}

