package Command.Validators;

/**
 * Define valid commands while creating a game
 *
 * @author Benjamin Munro
 */
public class ValidCreateGameCommands extends ValidCommands{

    /**
     * New ValidCreateGameCommands
     */
    public ValidCreateGameCommands(){
        super();
        validCommands.put("home", 0);
        validCommands.put("two", 2);
        validCommands.put("three", 3);
        validCommands.put("four", 4);
        validCommands.put("five", 5);
        validCommands.put("six", 6);
        validCommands.put("help", 0);
        validCommands.put("quit", 0);
    }

}
