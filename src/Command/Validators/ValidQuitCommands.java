package Command.Validators;

/**
 * Define valid commands while prompting to quit a game
 *
 * @author Benjamin Munro
 */
public class ValidQuitCommands extends ValidCommands{
    public ValidQuitCommands(){
        super();
        validCommands.put("yes", 0);
        validCommands.put("no", 0);
        validCommands.put("help", 0);
    }

}
