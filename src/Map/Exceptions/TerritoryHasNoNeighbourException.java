package Map.Exceptions;

public class TerritoryHasNoNeighbourException extends Exception{
    public TerritoryHasNoNeighbourException(String errorMessage){
        super(errorMessage);
    }
}
