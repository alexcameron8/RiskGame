package Player;
import java.util.*;
import Map.*;

public class Player {
    private ArrayList<Territory> listOfTerritories;
    private ArrayList<String> listOfContinents;
    private String name;

    public Player(String name){
        this.name = name;
        this.listOfContinents = new ArrayList<>();
        this.listOfTerritories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public ArrayList<String> getListOfContinents() {
        return listOfContinents;
    }

    public ArrayList<Territory> getListOfTerritories() {
        return listOfTerritories;
    }

}
