package Map;

import java.util.LinkedList;

public class Territory {

    private String name;
    private LinkedList<Territory> neighbours;
    private int soldiers;

    public Territory(String name){
        this.name = name;
        neighbours = new LinkedList<>();
        soldiers = 0;
    }

    public void addNeighbours(Territory neighbour){
        neighbours.add(neighbour);
    }

    public String getName() {
        return name;
    }

    public LinkedList<Territory> getNeighbours() {
        return neighbours;
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

        System.out.println(easternAustralia.getNeighbours());
    }

}
