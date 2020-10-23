package Player;
import java.util.*;
import Map.*;

public class Player {
    private ArrayList<Territory> listOfTerritories;
    private ArrayList<Continent> listOfContinents;
    private String name;

    public Player(String name){
        this.name = name;
        this.listOfContinents = new ArrayList<>();
        this.listOfTerritories = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public ArrayList<Continent> getListOfContinents() {
        return listOfContinents;
    }

    public ArrayList<Territory> getListOfTerritories() {
        return listOfTerritories;
    }

}
