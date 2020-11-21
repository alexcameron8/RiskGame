package Main.ActionBar;

import Main.Map.MapEvent;
import Main.Map.MapTerritoryEvent;
import Main.Map.MapViewListener;
import Map.Territory;
import Player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is controls what happens when user interacts with the action bar GUI component and updates the view/model with corresponding methods.
 *
 * @Author Alex Cameron
 */
public class ActionBarController implements ActionListener, MapViewListener {
    private ActionBarView abv;
    private ActionBarModel abm;
    private int numOfTroops,numofAttackers;
    //Different states are used to determine what variables are updated when the user clicks the territories on the map
    private String state = "Default";
    private Territory territory, attackerTerritory,defenderTerritory;
    private Player defender;
    private boolean attackConfirm, defendConfirm,hasNumTroopsSelected,hasTerritorySelected;

    /**
     * The ActionBarController constructor passes the action bar view and model in which are being manipulated in Risk.
     * @param abv The action bar view component
     * @param abm The action bar model component
     */
    public ActionBarController(ActionBarView abv, ActionBarModel abm) {
        this.abv = abv;
        this.abm = abm;
    }

    /**
     * This method removes the message bar from the GUI.
     */
    public void removeMessageBar(){
        if(abv.getMessageFlag()){
            abv.removeMessageBar();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("place")) {
            state = "Place";
            //check if any previous message bars are on GUI and removes them if true
            removeMessageBar();
            //Clears all previous flags for attacking
            hasNumTroopsSelected = false;
            hasTerritorySelected = false;
            //Opens troop deployment info bar in GUI
            if(!abv.getPlaceTroopsFlag()) {
                //clears any other bars if there
                abv.removeAttackBar();
                abv.deployTroopsInfo();
            }
        } else if (e.getActionCommand().equals("attack")) { //If attack button is clicked then the Attack info bar appears on GUI
            state = "Attacker";
            removeMessageBar();
            if(!abv.getAttackFlag()) {
                abv.removeDeployTroopsBar();
                abv.attackInfo();
            }
        } else if (e.getActionCommand().equals("next")) { //Advances turn to next player
            removeMessageBar();

            //Clears all previous flags for attacking
            hasNumTroopsSelected = false;
            hasTerritorySelected = false;
            abm.nextTurn(abm.getRiskModel());
            //clears any bars on GUI
            abv.removeDeployTroopsBar();
            abv.removeAttackBar();
            //displays corresponding message if turn is complete or not
            if(!abm.getRiskModel().isTurnComplete()){
                abv.setMessage(abm.getRiskModel().getPlayers().get(abm.getRiskModel().getActivePlayerID()).getName() + " turn is not complete. There are " + abm.getRiskModel().getPlayers().get(abm.getRiskModel().getActivePlayerID()).getReinforcement() + " soldiers left to place.");
            }else{
                abv.initTroopMovement();
                abv.setMessage("Turn advanced. It is now " + abm.getRiskModel().getActivePlayer().getName() + "'s turn.");
            }
        } else if(e.getActionCommand().equals("numTroops")) { //Gets number of troops the player chooses to place
            if((Integer) abv.getNumberOfTroops().getSelectedItem()!=null) {
                numOfTroops = (Integer) abv.getNumberOfTroops().getSelectedItem();
                hasNumTroopsSelected = true;
            }else{
                abv.setMessage("Number of troops not selected.");
            }
        } else if (e.getActionCommand().equals("deploy")) { //This button will deploy the troops in the model and update the view correspondingly or deliver error messages
            removeMessageBar();
            if (territory != null) {
                if(hasTerritorySelected) {
                    if (hasNumTroopsSelected) {
                        abm.deployTroops(territory, numOfTroops);
                        abv.removeDeployTroopsBar();
                        abv.getRiskView().getTerritoryInfoView().setInfo(territory);
                        hasTerritorySelected = false;
                        hasNumTroopsSelected = false;
                        state = "Default";
                    } else {
                        abv.setMessage("Select number of reinforcements to deploy.");
                    }
                }else{
                    abv.setMessage("You must select a Territory before placing troops.");
                }
            } else {
                abv.setMessage("You must select a Territory before placing troops.");
            }
        }else if(e.getActionCommand().equals("confirmAttack")) { //confirms the attacker territory and changes state to defender territory
            if(attackerTerritory!=null) {
                attackConfirm = true;
                state = "Defender";
            }
        }else if(e.getActionCommand().equals("cancelDefend")){ //cancels the confirmation of the defender territory and changes state to defender
            defendConfirm = false;
            state = "Defender";
        }else if(e.getActionCommand().equals("cancelAttack")){ //cancels the confirmation of the attacker territory and changes state to attacker
            attackConfirm = false;
            state = "Attacker";
        }else if(e.getActionCommand().equals("attackTroops")){ //gets the number of troops to attack with from JComboBox
            if((Integer)abv.getAttackNumberOfTroops().getSelectedItem()!=null){
                numofAttackers = (Integer) abv.getAttackNumberOfTroops().getSelectedItem();
            }
        }else if(e.getActionCommand().equals("confirmDefend")){ //confirms the defender territory
            if(defenderTerritory!=null) {
                defendConfirm = true;
            }
        }else if(e.getActionCommand().equals("attackButton")){ //performs the attack with the attacker territory, defender territory and number of troops to send to battle
            removeMessageBar();
            //ensures that both defender and attacker territories are valid
            if(defendConfirm && attackConfirm) {
                if (attackerTerritory != null && defenderTerritory !=null) {
                    //performs attack
                    if (abm.attack(attackerTerritory, defender, defenderTerritory, numofAttackers)) {
                        //attack successful message
                        abv.setMessage(attackerTerritory.getOwner().getName() + ", conquered " + defender.getName() + "'s territory, " + defenderTerritory.getName() + ".");
                    } else {
                        //attack unsuccesful message
                        abv.setMessage(attackerTerritory.getOwner().getName() + ", failed to conquer " + defender.getName() + "'s territory, " + defenderTerritory.getName() + ".");
                    }
                    abv.removeAttackBar();
                    state = "Default";
                } else { //missing fields for attack
                    abv.setMessage("All of the attack information has not yet been confirmed.");
                }
            }
        }else if(e.getActionCommand().equals("backAttack")){ //exits the attack info bar
            state = "Default";
            removeMessageBar();
            abv.removeAttackBar();
        }else if(e.getActionCommand().equals("backDeploy")){ //exits the deploy troops info bar
            state = "Default";
            removeMessageBar();
            abv.removeDeployTroopsBar();
        }else if(e.getActionCommand().equals("fortify")) { //Changes ActionBar to fortify
            removeMessageBar();
            abv.fortifyTroops();
        }
    }
    /**
     *  When the map is updated this method determines what the territory that has been clicked does.
     */
    @Override
    public void handleMapUpdate(MapEvent e) {
        if (e instanceof MapTerritoryEvent) {
            removeMessageBar();
            if (state.equals("Place")) { //current state is for placing troops
                if (((MapTerritoryEvent) e).getMapTerritory().getOwner() == abm.getRiskModel().getActivePlayer()) { //checks that territory clicked is owned by the current turn player
                    hasTerritorySelected = true;
                    territory = ((MapTerritoryEvent) e).getMapTerritory(); //sets territory to clicked territory
                    if (abv.getPlaceTroopsFlag()) {
                        abv.setDeployInfo(); //changes GUI to see what territory was selected
                    }
                } else {
                    abv.setMessage(((MapTerritoryEvent) e).getMapTerritory().getName() + " does not belong to you.");
                }
            } else if (state.equals("Attacker")) {  //current state is Attacking
                if (((MapTerritoryEvent) e).getMapTerritory().getOwner() == abm.getRiskModel().getActivePlayer()) {
                    attackerTerritory = ((MapTerritoryEvent) e).getMapTerritory(); //sets attacker territory to territory that is clicked
                    abv.setAttackerInfo(); //sets Gui attacker territory info
                    abv.setAttackNumberRange();

                } else { //invalid territory selected
                    attackerTerritory = null;
                    abv.setAttackerInfo();
                    abv.setMessage(((MapTerritoryEvent) e).getMapTerritory().getName() + " does not belong to you. Choose a Territory you own occupied with at least 2 troops.");
                }
            } else if (state.equals("Defender")) { //current state is the defending territory for attack simulation
                if(attackerTerritory != null) {
                for (Territory ter : attackerTerritory.getNeighbours()) {
                    if (ter.equals(((MapTerritoryEvent) e).getMapTerritory())) {
                        if(!ter.getOwner().equals(abm.getRiskModel().getActivePlayer())) {
                            defenderTerritory = ((MapTerritoryEvent) e).getMapTerritory();
                            defender = defenderTerritory.getOwner();
                            abv.setDefenderInfo(); //sets attack defender info
                            return;
                        }else{ //invalid territory selected
                            defenderTerritory = null;
                            abv.setDefenderInfo();
                            abv.setMessage("You cannot attack your own Territory!");
                            return;
                        }
                    }
                }//invalid territory selected
                    defenderTerritory = null;
                    abv.setDefenderInfo();
                    abv.setMessage(((MapTerritoryEvent) e).getMapTerritory().getName() + " is not a neighbour of " + attackerTerritory);
                }else{
                    abv.setMessage("Choose the territory you want to attack with first!");
                }
            }
        }
    }

    /**
     * Method which gets the current territory selected
     * @return The territory selected by mouse click
     */
    public Territory getTerritory() {
        return territory;
    }

    /**
     * Gets The attacker territory clicked by user
     * @return the attacker territory
     */
    public Territory getAttackerTerritory() {
        return attackerTerritory;
    }

    /**
     * gets the defender territory
     * @return The defender territory
     */
    public Territory getDefenderTerritory(){
        return defenderTerritory;
    }
}