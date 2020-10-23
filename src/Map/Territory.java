package Map;

import java.util.HashMap;
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
        Territory argentina = new Territory("Argentina");
        Territory Peru = new Territory("Peru");
        Territory Brazil = new Territory("Brazil");

        argentina.addNeighbours(Peru);
        argentina.addNeighbours(Brazil);
        System.out.println(argentina.getNeighbours());

    }

}
