package Main;

/**
 * Enum to represent game state. Used to decided how to process commands
 * and when to run certain methods.
 */
public enum GameState {
    /**
     * Game is on the main menu.
     */
    MAIN_MENU,
    /**
     * A new game is being created.
     */
    NEW_GAME_SETTINGS,
    /**
     * A game is currently being played
     */
    IN_GAME,
    /**
     * User has asked to quit a game
     */
    QUIT,
    /**
     * A new game/map is being generated
     */
    GENERATE_GAME
}
