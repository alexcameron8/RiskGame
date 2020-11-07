package Map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Continent models a continent in the game of risk.
 * A continent has a name, and a group of territories
 *
 * @version 1.9
 * Thomas Dunnigan
 * 101115382
 */

public class Continent {

    private String name;
    private String id;
    private HashMap<String, Territory> territories;
    private int numberOfReinforcement;
    private ArrayList<Integer> color = new ArrayList<>(3);

    /**
     * Constructor for Continent. Creates a Continent
     * with a name and a group of territories which is empty
     *
     * @param name Continent name
     */
    public Continent(String name, String id, int numberOfReinforcement, ArrayList<Integer> color){
        this.name = name;
        this.id = id;
        this.numberOfReinforcement = numberOfReinforcement;
        this.color = color;
        territories = new HashMap<>();
    }

    /**
     * Set the number of reinforcement gained when owning this continent
     *
     * @param numb Number of reinforcements to set.
     */
    public void setnumberOfReinforcement(int numb){
        numberOfReinforcement = numb;
    }

    /**
     * return the number of reinforcement gained when owning this continen
     *
     * @return Return reinforcements to gain.
     */
    public String getId(){
        return id;
    }

    /**
     * return the number of reinforcement gained when owning this continen
     *
     * @return Return reinforcements to gain.
     */
    public int getNumberOfReinforcement(){
        return numberOfReinforcement;
    }

    /**
     * adds a territory to the continent
     *
     * @param territory Territory to add.
     */
    public void addTerritory(Territory territory){
        territories.put(territory.getName(),territory);
    }

    /**
     * adds multiple territory to the continent
     *
     * @param args Territories to add
     */
    public void addTerritories(Territory... args){
        for(Territory arg : args) {
            addTerritory(arg);
        }
    }

    /**
     * get a territory from the continent
     * if not there return null
     *
     * @param territoryName Name of Territory to retrieve.
     * @return Territory Territory with territoryName
     */
    public Territory getTerritory(String territoryName) {
        return territories.get(territoryName);
    }

    /**
     * Get list of territories
     *
     * @return ArrayList of territories.
     */
    public ArrayList<Territory> getTerritories(){
        ArrayList<Territory> territories = new ArrayList<Territory>();
        for(Territory terr: this.territories.values()){
            territories.add(terr);
        }
        return territories;
    }

    /**
     * get the name of the continent
     *
     * @return name Name of Continent
     */
    public String getName(){
        return name;
    }

    /**
     * checks to see if territory is in the continent
     * returns null otherwise
     *
     * @param territoryName Name to check.
     * @return boolean true if territory is in continent, false otherwise
     */
    public boolean isTerritory(String territoryName){
        if(getTerritory(territoryName) == null)
            return false;
        return true;
    }

    public ArrayList<Integer> getColor(){
        return this.color;
    }


    @Override
    public String toString() {
        return name;
    }
}
