package Map;

import java.util.HashMap;

public class Continent {

    private String name;
    private HashMap<String, Territory> territories;

    public Continent(String name){
        this.name = name;
        territories = new HashMap<>();
    }

    public void addTerritory(Territory territory){
        territories.put(territory.getName(),territory);
    }

    public Territory getTerritory(String territoryName) {
        return territories.get(territoryName);
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        Continent australia = new Continent("Australia");

        Territory easternAustralia = new Territory("Eastern Australia");
        Territory indonesia  = new Territory("Indonesia");
        Territory newGuinea = new Territory("New Guinea");
        Territory westernAustralia = new Territory("Western Australia");
        Territory Siam = new Territory("Siam");


        easternAustralia.addNeighbours(westernAustralia);
        easternAustralia.addNeighbours(newGuinea);

        indonesia.addNeighbours(Siam);
        indonesia.addNeighbours(newGuinea);
        indonesia.addNeighbours(westernAustralia);

        newGuinea.addNeighbours(indonesia);
        newGuinea.addNeighbours(easternAustralia);
        newGuinea.addNeighbours(westernAustralia);

        westernAustralia.addNeighbours(indonesia);
        westernAustralia.addNeighbours(newGuinea);
        westernAustralia.addNeighbours(westernAustralia);

        australia.addTerritory(easternAustralia);
        australia.addTerritory(indonesia);
        australia.addTerritory(newGuinea);
        australia.addTerritory(westernAustralia);

        System.out.println(australia.getTerritory("Western Australia"));
    }
}
