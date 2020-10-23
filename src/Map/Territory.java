package Map;

import java.util.HashMap;

public class Territory {

    private String name;
    private HashMap<String, Territory> neighbours;
    private int soldiers;

    public Territory(String name){
        this.name = name;
        neighbours = new  HashMap<>();
        soldiers = 0;
    }

    public void addNeighbours(Territory neighbour){
        neighbours.put(neighbour.getName(), neighbour);
    }

    public String getName() {
        return name;
    }

    public Territory getNeighbour(String territoryName) {
        return neighbours.get(territoryName);
    }

    public int getSoldiers() {
        return soldiers;
    }

    public int addSoldiers(int add) {
        return soldiers + add;
    }

    public int removeSoldiers(int remove) {
        return soldiers - remove;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        Territory easternAustralia = new Territory("easternAustralia");
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
    }

}
