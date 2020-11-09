package Main.ActionBar;

import Main.*;
import Map.Territory;
import Player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionBarController implements ActionListener, MapViewListener {
    private ActionBarView abv;
    private ActionBarModel abm;
    private Territory territory;
    private int numOfTroops;
    private String state = "Default";
    private Territory attackerTerritory,defenderTerritory;
    private Player defender;
    private int numofAttackers;
    private boolean attackConfirm, defendConfirm,hasNumTroopsSelected,hasTerritorySelected;

    public ActionBarController(ActionBarView abv, ActionBarModel abm) {
        this.abv = abv;
        this.abm = abm;
    }

    public void removeMessageBar(){
        if(abv.getMessageFlag()){
            abv.removeMessageBar();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("place")) {
            state = "Place";
            //Open troop deployment info
            removeMessageBar();
            if(!abv.getPlaceTroopsFlag()) {
                abv.removeAttackBar();
                abv.deployTroopsInfo();
            }
            System.out.println("Test to see if place troops button works.");
        } else if (e.getActionCommand().equals("attack")) {
            state = "Attacker";
            //attack
            removeMessageBar();
            if(!abv.getAttackFlag()) {
                abv.removeDeployTroopsBar();
                abv.attackInfo();
            }
            System.out.println("attack");
        } else if (e.getActionCommand().equals("next")) {
            removeMessageBar();
            //advances turn
            hasNumTroopsSelected = false;
            hasTerritorySelected = false;
            abm.nextTurn(abm.getRiskModel());
            abv.removeDeployTroopsBar();
            abv.removeAttackBar();
            if(!abm.getRiskModel().isTurnComplete()){
                abv.setMessage(abm.getRiskModel().getPlayers().get(abm.getRiskModel().getActivePlayerID()).getName() + " turn is not complete. There are " + abm.getRiskModel().getPlayers().get(abm.getRiskModel().getActivePlayerID()).getReinforcement() + " soldiers left to place.");
            }else{
                abv.setMessage("Turn advanced. It is now " + abm.getRiskModel().getActivePlayer().getName() + "'s turn.");
            }
            System.out.println("next turn");
        } else if(e.getActionCommand().equals("numTroops")) {
            if((Integer) abv.getNumberOfTroops().getSelectedItem()!=null) {
                numOfTroops = (Integer) abv.getNumberOfTroops().getSelectedItem();
                System.out.println("Number of troops to deploy : " + numOfTroops);
                hasNumTroopsSelected = true;
            }else{
                abv.setMessage("Number of troops not selected.");
            }
        } else if (e.getActionCommand().equals("deploy")) {
            removeMessageBar();
            if (territory != null) {
                if(hasTerritorySelected) {
                    if (hasNumTroopsSelected) {
                        abm.deployTroops(territory, numOfTroops);
                        abv.removeDeployTroopsBar();
                        abv.updateUI();
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
        }else if(e.getActionCommand().equals("confirmAttack")) {
            attackConfirm = true;
            state = "Defender";
        }else if(e.getActionCommand().equals("cancelDefend")){
            defendConfirm = false;
            state = "Defender";
        }else if(e.getActionCommand().equals("cancelAttack")){
            attackConfirm = false;
            state = "Attacker";
        }else if(e.getActionCommand().equals("attackTroops")){
            if((Integer)abv.getAttackNumberOfTroops().getSelectedItem()!=null){
                numofAttackers = (Integer) abv.getAttackNumberOfTroops().getSelectedItem();
            }
        }else if(e.getActionCommand().equals("confirmDefend")){
            defendConfirm = true;
        }else if(e.getActionCommand().equals("attackButton")){
            removeMessageBar();
            if(defendConfirm && attackConfirm){
                if(abm.attack(attackerTerritory,defender,defenderTerritory, numofAttackers)){
                    abv.setMessage(attackerTerritory.getOwner().getName() + ", conquered " + defender.getName() + "'s territory, " + defenderTerritory.getName() + ".");
                }else{
                    abv.setMessage(attackerTerritory.getOwner().getName() + ", failed to conquer " + defender.getName() + "'s territory, " + defenderTerritory.getName() + ".");
                }
                    abv.removeAttackBar();
                state = "Default";
            }else{
                abv.setMessage("All of the attack information has not yet been confirmed.");
            }
        }else if(e.getActionCommand().equals("backAttack")){
            state = "Default";
            removeMessageBar();
            abv.removeAttackBar();
        }else if(e.getActionCommand().equals("backDeploy")){
            state = "Default";
            removeMessageBar();
            abv.removeDeployTroopsBar();
        }
    }

    @Override
    public void handleMapUpdate(MapEvent e) {
        if (e instanceof MapTerritoryEvent) {
            if (abv.getMessageFlag()) {
                abv.removeMessageBar();
            }
            if (state.equals("Place")) {
                if (((MapTerritoryEvent) e).getMapTerritory().getOwner() == abm.getRiskModel().getActivePlayer()) {
                    hasTerritorySelected = true;
                    territory = ((MapTerritoryEvent) e).getMapTerritory();

                    if (abv.getPlaceTroopsFlag()) {
                        abv.setDeployInfo();
                    }
                } else {
                    abv.setMessage(((MapTerritoryEvent) e).getMapTerritory().getName() + " does not belong to you.");
                }
            } else if (state.equals("Attacker")) {
                if (((MapTerritoryEvent) e).getMapTerritory().getOwner() == abm.getRiskModel().getActivePlayer()) {
                    abv.setAttackerInfo();
                    attackerTerritory = ((MapTerritoryEvent) e).getMapTerritory();
                    abv.setAttackNumberRange();

                } else {
                    abv.setMessage(((MapTerritoryEvent) e).getMapTerritory().getName() + " does not belong to you. Choose a Territory you own occupied with at least 2 troops.");
                }
            } else if (state.equals("Defender")) {
                if(attackerTerritory != null) {
                for (Territory ter : attackerTerritory.getNeighbours()) {
                    if (ter.equals(((MapTerritoryEvent) e).getMapTerritory())) {
                        if(!ter.getOwner().equals(abm.getRiskModel().getActivePlayer())) {
                            defenderTerritory = ((MapTerritoryEvent) e).getMapTerritory();
                            defender = defenderTerritory.getOwner();
                            abv.setDefenderInfo();
                            return;
                        }else{
                            abv.setMessage("You cannot attack your own Territory!");
                            return;
                        }
                    }
                }
                    abv.setMessage(((MapTerritoryEvent) e).getMapTerritory().getName() + " is not a neighbour of " + attackerTerritory);
                }else{
                    abv.setMessage("Choose the territory you want to attack with first!");
                }
            }
        }
    }
    public Territory getTerritory() {
        return territory;
    }
    public Territory getAttackerTerritory() {
        return attackerTerritory;
    }
    public Territory getDefenderTerritory(){
        return defenderTerritory;
    }
}