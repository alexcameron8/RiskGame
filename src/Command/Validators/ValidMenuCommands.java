package Command.Validators;

/**
 * Define valid commands while on the main menu
 *
 * @author Benjamin Munro
 */
public class ValidMenuCommands extends ValidCommands{

    /**
     * Create new ValidMenuCommands
     */
    public ValidMenuCommands(){
        super();
        validCommands.put("start", 0);
        validCommands.put("help", 0);
        validCommands.put("quit", 0);
    }

}
