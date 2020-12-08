package Main.IntializeFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Main.RiskModel.MAX_NUMBER_PLAYERS;
import static Main.RiskModel.MIN_NUMBER_PLAYERS;

/**
 * this class create the GUI for initializing the players
 * @author Thomas
 */
public class InitializeView extends JPanel implements InitializeViewListener {

    private JPanel[] playerConfigPanel;
    private JPanel playersConfigPanel;
    private final InitializeModel im;
    private final InitializeController ic;

    private static final String[] COLOURS = {null, "Red", "Green", "Blue", "Yellow", "Orange", "Purple"};
    public static final int PREFERRED_WIDTH = 400;
    public static final int PREFERRED_HEIGHT = 400;
    public static final String NUMBER_PLAYERS_NAME = "numPlayers";
    public static final String AI_CHECKBOX_NAME = "isAI";
    public static final String COLOUR_NAME = "colour";
    public static final String PLAYER_NAME = "name";

    /**
     * constructor for InitializeView that create teh GUI
     */
    public InitializeView(){
        super();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

        im = new InitializeModel();
        im.addInitializeView(this);
        ic = new InitializeController(im);

        createNumberOfPlayersRequest();

        createPlayersInfoRequest();
    }

    private void createPlayersInfoRequest(){
        JTextField[] nameOfPlayers = new JTextField[MAX_NUMBER_PLAYERS];

        playersConfigPanel = new JPanel();
        playersConfigPanel.setLayout(new BoxLayout(playersConfigPanel, BoxLayout.Y_AXIS));
        playersConfigPanel.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height*MAX_NUMBER_PLAYERS/(MAX_NUMBER_PLAYERS+1)));


        JComboBox[] playerColour = new JComboBox[MAX_NUMBER_PLAYERS];
        playerConfigPanel = new JPanel[MAX_NUMBER_PLAYERS];

        JCheckBox[] playerIsAI = new JCheckBox[MAX_NUMBER_PLAYERS];

        JLabel[] playerIsAILabel = new JLabel[MAX_NUMBER_PLAYERS];

        for (int i=0; i < MAX_NUMBER_PLAYERS; i++) {
            playerConfigPanel[i] = new JPanel();
            playerConfigPanel[i].setLayout(new GridBagLayout());
            playerConfigPanel[i].setBorder(BorderFactory.createTitledBorder("Player "+(i+1) + " setup:"));
            playerConfigPanel[i].setMaximumSize(
                    new Dimension(playersConfigPanel.getPreferredSize().width,
                            playersConfigPanel.getPreferredSize().height/(MAX_NUMBER_PLAYERS)));
            GridBagConstraints gridBagConstraints = new GridBagConstraints();

            nameOfPlayers[i]=new JTextField();
            nameOfPlayers[i].getDocument().addDocumentListener(ic);
            nameOfPlayers[i].getDocument().putProperty(PLAYER_NAME,(i+1));
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.8;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            playerConfigPanel[i].add(nameOfPlayers[i], gridBagConstraints);

            playerColour[i] = new JComboBox(COLOURS);
            playerColour[i].addActionListener(ic);
            playerColour[i].setActionCommand(COLOUR_NAME+" "+(i+1));
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.1;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            playerConfigPanel[i].add(playerColour[i], gridBagConstraints);

            playerIsAILabel[i] = new JLabel("AI: ");
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.05;
            gridBagConstraints.fill = GridBagConstraints.NONE;
            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            playerConfigPanel[i].add(playerIsAILabel[i], gridBagConstraints);

            playerIsAI[i] = new JCheckBox();
            playerIsAI[i].setActionCommand(AI_CHECKBOX_NAME +" "+ (i+1));
            playerIsAI[i].addActionListener(ic);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.05;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            playerConfigPanel[i].add(playerIsAI[i], gridBagConstraints);

        }
        for (int i=0; i < MIN_NUMBER_PLAYERS; i++) {
            playersConfigPanel.add(playerConfigPanel[i]);
        }
        this.add(playersConfigPanel, BorderLayout.CENTER);
    }

    private void createNumberOfPlayersRequest(){

        Integer[] numberOfPlayers = new Integer[MAX_NUMBER_PLAYERS - 1];
        for(int i = MIN_NUMBER_PLAYERS; i <= MAX_NUMBER_PLAYERS; i++){
            numberOfPlayers[i-MIN_NUMBER_PLAYERS]=i;
        }

        JComboBox numPlayers = new JComboBox(numberOfPlayers);
        numPlayers.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height/(MAX_NUMBER_PLAYERS+1)));
        numPlayers.setBorder(BorderFactory.createTitledBorder("Select number of Players:"));
        numPlayers.addActionListener(ic);
        numPlayers.setActionCommand(NUMBER_PLAYERS_NAME);
        this.add(numPlayers, BorderLayout.PAGE_START);
    }

    /**
     * display a number of player characteristics that is the number of players
     *
     * @param e
     */
    @Override
    public void handleInitializeUpdate(InitializeEvent e) {
        int numPlayers = e.getNumberOfPlayer();
        playersConfigPanel.removeAll();
        playersConfigPanel.revalidate();
        playersConfigPanel.repaint();
        for (int i=0; i < numPlayers; i++) {
            playersConfigPanel.add(playerConfigPanel[i]);
        }
        this.revalidate();
    }

    /**
     * get the final version of the player names
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getNameOfPlayers(){
        return im.getNamesOfPlayers();
    }

    /**
     * get the final version of the player colours
     *
     * @return ArrayList<Color>
     */
    public ArrayList<Color> getPlayersColour(){
        return im.getPlayersColours();
    }

    /**
     * get the final version of the player colours
     *
     * @return ArrayList<Color>
     */
    public ArrayList<Boolean> getIsPlayerAI(){
        return im.getPlayersIsAI();
    }


    /**
     * get the final number of players
     *
     * @return int
     */
    public int getNumberOfPlayers() {
        return im.getNumberOfPlayers();
    }
}
