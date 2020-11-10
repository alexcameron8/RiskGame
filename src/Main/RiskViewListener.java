package Main;

/**
 * Interface required to handle RiskEvents
 */
public interface RiskViewListener {
    /**
     * method to handle events
     * @param e event to handle
     */
    void handleTurnUpdate(RiskEvent e);
}
