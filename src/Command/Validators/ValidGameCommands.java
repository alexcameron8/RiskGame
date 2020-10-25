package Command.Validators;

/**
 * Define valid commands while running a game
 *
 * @author Benjamin Munro
 */
public class ValidGameCommands extends ValidCommands{

    public ValidGameCommands(){
        super();
        validCommands.put("home", 0);
        validCommands.put("players", 0);
        validCommands.put("attack", 3);
        validCommands.put("turn", 0);
        validCommands.put("help", 0);
        validCommands.put("reinforcements", 0);
        validCommands.put("place", 2);
        validCommands.put("territories", 0);
        validCommands.put("map", 0);
        validCommands.put("neighbours", 1);
        validCommands.put("quit", 0);
    }

}
