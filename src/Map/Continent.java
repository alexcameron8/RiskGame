package Map;

import java.util.LinkedList;

public class Continent {

    private String name;
    private LinkedList<Territory> territories;

    public Continent(String name){
        this.name = name;
        territories = new LinkedList<>();
    }

    public void addTerritory(Territory territory){
        territories.add(territory);
    }

    public LinkedList<Territory> getTerritories() {
        return territories;
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

        System.out.println(australia.getTerritories());
    }
}
