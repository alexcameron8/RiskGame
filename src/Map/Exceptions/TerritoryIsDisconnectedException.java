package Map.Exceptions;

/**
 * This is an error that the territory is not connected
 *
 * @Ben Munro
 */
public class TerritoryIsDisconnectedException extends Exception{
    public TerritoryIsDisconnectedException(String errorMessage){
        super(errorMessage);
    }
}
