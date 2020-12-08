package Player;
import java.awt.*;
import java.util.*;
import Main.RiskView;
import Map.*;
import Attack.*;

/**
 * Player models a player in risk.
 * The player controls territories and continents
 * The player can attack another player
 *
 * @author Alex Cameron and Thomas Dunnigan
 */
public class Player {
    //the territories controlled by the player
    private ArrayList<Territory> listOfTerritories;
    //the continents controlled by the player
    private ArrayList<Continent> listOfContinents;
    //the name of the player
    protected String name;
    //number of Reinforcements
    private int reinforcements;
    //player colour
    private Color playerColor;
    //risk view
    protected RiskView riskView;

    /**
     * Constructor for a player. Gives them
     * a name and create a list of territories
     * and continents controlled by the player
     *
     * @param name Player name.
     */
    public Player(String name, Color playerColor, RiskView riskView){
        this.name = name;
        this.listOfContinents = new ArrayList<>();
        this.listOfTerritories = new ArrayList<>();
        this.reinforcements = 0;
        this.playerColor = playerColor;
        this.riskView = riskView;
    }

    /**
     * get the player colour
     *
     * @return Color
     */
    public Color getPlayerColor(){
        return playerColor;
    }

    /**
     * Player attacks another player
     *
     * @param attackerTerritory Territory attacking.
     * @param defender Player defending attack
     * @param defenderTerritory Territory defending.
     * @param numOfAttackArmy Number of troops attacking
     */
    public boolean attack(Territory attackerTerritory, Player defender, Territory defenderTerritory, int numOfAttackArmy){
        Attack attack = new Attack(this, attackerTerritory, defender, defenderTerritory,  numOfAttackArmy);
        return attack.isSuccesfulAttack();
    }

    /**
     * adds territory to the player
     *
     * @param territory Territory to add to a player
     */
    public void addTerritory(Territory territory){
        listOfTerritories.add(territory);
        territory.setOwner(this);
        int sizeControlsContinent = 0;
        for(Territory terr : territory.getContinent().getTerritories()){
            if(hasTerritory(terr)){
                sizeControlsContinent+=1;
            }
        }
        if(sizeControlsContinent == territory.getContinent().getTerritories().size()) {
            addContinent(territory.getContinent());
        }
    }

    /**
     * removes a territory from the player
     *
     * @param name Name of territory to remove
     * @return the removed territory
     */
    public Territory removeTerritory(String name) {
        for (Territory ter : listOfTerritories) {
            if (ter.getName().equals(name)) {
                for(Continent continent : listOfContinents){
                    if(continent.isTerritory(ter.getName())) {
                        removeContinent(continent);
                        break;
                    }
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
     * @param territory Territory to remove.
     * @return the removed territory
     */
    public Territory removeTerritory(Territory territory) {
        return removeTerritory(territory.getName());
    }

    /**
     * removes a continent from the player
     *
     * @param name Name of continent to remove.
     * @return removed continent
     */
    public Continent removeContinent(String name){
        for (Continent cont : listOfContinents) {
            if (cont.getName().equals(name)) {
                listOfContinents.remove(cont);
                return cont;
            }
        }
        return null;
    }

    /**
     * removes a continent from the player
     *
     * @param continent Continent to remove.
     * @return removed continent
     */
    public Continent removeContinent(Continent continent) {
        return removeContinent(continent.getName());
    }

    /**
     * gives a territory to another player
     *
     * @param receiver Player to receive a territory.
     * @param territory Territory to transfer.
     */
    public void transferTerritory(Player receiver,Territory territory){
        receiver.addTerritory(territory);
        removeTerritory(territory.getName());
    }

    /**
     * Transfer all the territories owned by the player to another player
     *
     * @param receiver
     */
    public void transferAllTerritory(Player receiver){
        int firstTerritory = 0;
        int terrSize = listOfTerritories.size();
        for(int i = 0; i <  terrSize; i ++){
            transferTerritory(receiver, listOfTerritories.get(firstTerritory));
        }
    }

    /**
     * get the number of reinforcements left
     *
     * @return Number of reinforcements remaining
     */
    public int getRemainingReinforcements() {
        return reinforcements;
    }

    /**
     * get the number of reinforcements the player gets
     *
     * @return int number of reinforcements a player gets
     */
    public int getReinforcement(){
        if(reinforcements != 0){
            return reinforcements;
        }
        int numberOfReinforcement = 0;
        //the lowest amount of reinforcements a player can receive per turn is 3 armies.
        //A player receives 1 army for every 3 territories they own e.g. player has 23 countries, player receives 7 armies
        //if a player has less than 9 territories then they must receive at least 3 armies.
        int minNumTerritories = 9;
        int threeTerr = 3;
        if(listOfTerritories.size() <= minNumTerritories){
            numberOfReinforcement = 3;
        }
        else {
            numberOfReinforcement = listOfTerritories.size() / threeTerr;
        }
        for (Continent continent: listOfContinents) {
            numberOfReinforcement += continent.getNumberOfReinforcement();
        }
        reinforcements = numberOfReinforcement;
        return numberOfReinforcement;
    }


   /**
     * place a certain number of Reinforcements on a territory
     *
     * @param territory Territory to place troops
     * @param numberOfReinforcement Number of troops to place
    *
    * @return boolean Returns true if can place reinforcement and false otherwise.
     */
    public boolean canPlaceReinforcement(Territory territory, int numberOfReinforcement){
        int noReinforcements = 0; //no reinforcements for player to place
        if(numberOfReinforcement <= noReinforcements ){ //checks if player has any reinforcements left
            return false;
        }
        else if(numberOfReinforcement > getReinforcement() ){ //check if number of reinforcements to be placed are more than what player has
            return false;
        }
        else if(hasTerritory(territory)){ //check if player owns territory.
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * place a certain number of Reinforcements on a territory
     *
     * @param territory The territory adding troops to
     * @param numberOfReinforcement the number of troops adding to territory.
     *
     */
    public void placeReinforcement(Territory territory, int numberOfReinforcement) {
        if (hasTerritory(territory)) {
            territory.addSoldiers(numberOfReinforcement);
            reinforcements -= numberOfReinforcement;
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
     * @param continent Continent to add.
     */
    public void addContinent(Continent continent){
        listOfContinents.add(continent);
    }

    /**
     * check to see if player controls a continent
     *
     * @param continent Continent to check.
     * @return boolean
     */
    public boolean hasContinent(Continent continent){
        return hasContinent(continent.getName());
    }

    /**
     * check to see if player controls a continent
     *
     * @param name Name of continent.
     * @return boolean
     */
    public boolean hasContinent(String name){
        for (Continent cont : listOfContinents) {
            if (cont.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check to see if player controls a territory
     *
     * @param territory Territory to check.
     * @return boolean
     */
    public boolean hasTerritory(Territory territory){
        return hasTerritory(territory.getName());
    }

    /**
     * check to see if player controls a territory
     *
     * @param name Name of territory to check.
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
     * @param name Name of territory to retrieve.
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
     * @param territory Territory to retrieve
     * @return Territory
     */
    public Territory getTerritory(Territory territory){
        return getTerritory(territory.getName());
    }
    @Override
    public String toString(){
      return name;
    }

    /**
     * move troops from one territory to another territory
     *
     * @param giving Territory giving troops
     * @param receiving Territory receiving troops
     * @param numOfTroops Number of troops
     * @return boolean if moved was successful
     */
    public boolean moveTroops(Territory giving, Territory receiving, int  numOfTroops){
        if(giving.getSoldiers()>numOfTroops) {
            if (hasTerritory(giving) && hasTerritory(receiving)) {
                if (giving.isNeighbour(receiving.getName())) {
                    giving.removeSoldiers(numOfTroops);
                    receiving.addSoldiers(numOfTroops);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if all reinforcements have been placed and turn is complete.
     * @return true if turn is complete, false otherwise.
     */
    public boolean isTurnComplete(){
        if(reinforcements==0){
            return true;
        }else{
            return false;
        }
    }

    public void advanceTurn(){
    }
}
