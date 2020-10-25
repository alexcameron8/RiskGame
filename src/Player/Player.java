package Player;
import java.util.*;
import Map.*;
import Attack.*;

/**
 * Player models a player in risk.
 * The player controls territories and continents
 * The player can attack another player
 *
 * Alex Cameron and Thomas Dunnigan
 */
public class Player {
    //the territories controlled by the player
    private ArrayList<Territory> listOfTerritories;
    //the continents controlled by the player
    private ArrayList<Continent> listOfContinents;
    //the name of the player
    private String name;

    /**
     * Constructor for a player. Gives them
     * a name and create a list of territories
     * and continents controlled by the player
     *
     * @param name
     */
    public Player(String name){
        this.name = name;
        this.listOfContinents = new ArrayList<>();
        this.listOfTerritories = new ArrayList<>();
    }

    /**
     * Player attacks another player
     *
     * @param attackerTerritory
     * @param defender
     * @param defenderTerritory
     * @param numOfAttackArmy
     */
    public void attack(Territory attackerTerritory, Player defender, Territory defenderTerritory, int numOfAttackArmy){
        Attack attack = new Attack(this, attackerTerritory, defender, defenderTerritory,  numOfAttackArmy);
    }

    /**
     * adds territory to the player
     *
     * @param territory
     */
    public void addTerritory(Territory territory){
        listOfTerritories.add(territory);
        WorldMap map = new WorldMap();
        for(Continent continent : map.getWorldMap().getContinents()){
            int sizeControlsContinent = 0;
            for(Territory terr : continent.getTerritories()){
                if(hasTerritory(terr)){
                    sizeControlsContinent+=1;
                }
            }
            if(sizeControlsContinent == continent.getTerritories().size())
            addContinent(continent);
        }
    }

    /**
     * removes a territory from the player
     *
     * @param name
     * @return the removed territory
     */
    public Territory removeTerritory(String name) {
        for (Territory ter : listOfTerritories) {
            if (ter.getName() == name) {
                for(Continent continent : listOfContinents){
                    if(continent.isTerritory(ter.getName()));
                        removeContinent(continent);
                        break;
                }
                listOfTerritories.remove(ter);
                return ter;
            }
        }
        return null;
    }

    /**
     * removes a territory from the player
     *
     * @param territory
     * @returnthe removed territory
     */
    public Territory removeTerritory(Territory territory) {
        return removeTerritory(territory.getName());
    }

    /**
     * removes a continent from the player
     *
     * @param name
     * @return removed continent
     */
    public Continent removeContinent(String name){
        for (Continent cont : listOfContinents) {
            if (cont.getName() == name) {
                listOfContinents.remove(cont);
                return cont;
            }
        }
        return null;
    }

    /**
     * removes a continent from the player
     *
     * @param continent
     * @return removed continent
     */
    public Continent removeContinent(Continent continent) {
        return removeContinent(continent.getName());
    }

    /**
     * gives a territory to another player
     *
     * @param receiver
     * @param territory
     */
    public void transferTerritory(Player receiver,Territory territory){
        receiver.addTerritory(territory);
        removeTerritory(territory.getName());
    }



    /**
     * get the number of reinforcements the player gets
     *
     * @return int
     */
    public int getReinforcement(){
        int numberOfReinforcement = 0;
        if(listOfTerritories.size() <= 9){
            numberOfReinforcement = 3;
        }
        else {
            numberOfReinforcement = (int) listOfTerritories.size() / 3;
        }
        for (Continent continent: listOfContinents) {
            numberOfReinforcement += continent.getNumberOfReinforcement();
        }
        return numberOfReinforcement;
    }


   /**
     * place a certain number of Reinforcements on a territory
     *
     * @param territory
     * @param numberOfReinforcement
    *
    * @return boolean
     */
    public boolean canPlaceReinforcement(Territory territory, int numberOfReinforcement){
        if(numberOfReinforcement <=0 ){
            System.out.println("Cannot place 0 or less reinforcements.");
            return false;
        }
        else if(numberOfReinforcement > getReinforcement() ){
            System.out.println("Cannot place more than "+ getReinforcement() + " reinforcements.");
            return false;
        }
        else if(hasTerritory(territory)){
            return true;
        }
        else{
            System.out.println("You do not own this territory");
            return false;
        }
    }

    /**
     * place a certain number of Reinforcements on a territory
     *
     * @param territory The territory adding troops to
     * @param numberOfReinforcement the number of troops adding to territory.
     *
     * @return boolean
     */
    public void placeReinforcement(Territory territory, int numberOfReinforcement) {
        if (hasTerritory(territory)) {
            territory.addSoldiers(numberOfReinforcement);
        }
    }

    /**
     * return the name of the player
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * return a list of all continents controlled by a player
     *
     * @return listOfContinents
     */
    public ArrayList<Continent> getListOfContinents() {
        return listOfContinents;
    }

    /**
     * return a list of all territories controlled by a player
     *
     * @return listOfTerritories
     */
    public ArrayList<Territory> getListOfTerritories() {
        return listOfTerritories;
    }

    /**
     * the player controls a new continent
     *
     * @param continent
     */
    public void addContinent(Continent continent){
        if(!hasContinent(continent)){
            listOfContinents.add(continent);
        }
    }

    /**
     * check to see if player controls a continent
     *
     * @param continent
     * @return boolean
     */
    public boolean hasContinent(Continent continent){
        return hasContinent(continent.getName());
    }

    /**
     * check to see if player controls a continent
     *
     * @param name
     * @return boolean
     */
    public boolean hasContinent(String name){
        for (Continent cont : listOfContinents) {
            if (cont.getName() == name) {
                return true;
            }
        }
        return false;
    }

    /**
     * check to see if player controls a territory
     *
     * @param territory
     * @return boolean
     */
    public boolean hasTerritory(Territory territory){
        return hasTerritory(territory.getName());
    }

    /**
     * check to see if player controls a territory
     *
     * @param name
     * @return boolean
     */
    public boolean hasTerritory(String name){
        for (Territory ter : listOfTerritories) {
            if (ter.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get a territory from the player
     * if they don't have it return null
     *
     * @param name
     * @return Territory
     */
    public Territory getTerritory(String name){
        for (Territory ter : listOfTerritories) {
            if (ter.getName().equals(name)) {
                return ter;
            }
        }
        return null;
    }
    /**
     * get a territory from the player
     * if they don't have it return null
     *
     * @param territory
     * @return Territory
     */
    public Territory getTerritory(Territory territory){
        return getTerritory(territory.getName());
    }

}
