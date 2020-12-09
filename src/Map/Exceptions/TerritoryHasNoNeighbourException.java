package Map.Exceptions;

/**
 * This is an error that the territory does not have a neighbour
 *
 * @Ben Munro
 */
public class TerritoryHasNoNeighbourException extends Exception{
    public TerritoryHasNoNeighbourException(String errorMessage){
        super(errorMessage);
    }
}
