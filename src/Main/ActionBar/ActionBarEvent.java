package Main.ActionBar;

import java.util.EventObject;

/**
 * This class is an event class that gets the events of the action bar model class.
 *
 * @Author Alex Cameron
 */
public class ActionBarEvent extends EventObject {

    private boolean isTurnComplete;

    /**
     * Constructor which gets the number of reinforcements from the action bar model
     * @param actionBarModel The action bar model
     *
     */
    public ActionBarEvent(ActionBarModel actionBarModel, boolean isTurnComplete) {
        super(actionBarModel);
        this.isTurnComplete = isTurnComplete;
    }

    /**
     * Checks if player is done placing troops
     * @return true if placing troops is complete, false otherwise.
     */
    public boolean isTurnComplete(){
        return isTurnComplete;
    }
}

