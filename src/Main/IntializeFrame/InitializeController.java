package Main.IntializeFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is controls what happens when user interacts with the Initialize frame GUI component and updates the view/model with corresponding methods.
 * @author Thomas
 */
public class InitializeController implements ActionListener, DocumentListener {

    private InitializeModel im;

    public InitializeController(InitializeModel im) {
        this.im = im;
    }

    /**
     * change the number of players or the colour of the player
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(InitializeView.NUMBER_PLAYERS_NAME)) {
            im.setPlayerNumbers((int) (((JComboBox) e.getSource()).getSelectedItem()));
        } else if (e.getActionCommand().split(" ")[0].equals(InitializeView.COLOUR_NAME)) {
            im.setPlayerColour(Integer.parseInt(e.getActionCommand().split(" ")[1]), (String) ((JComboBox) e.getSource()).getSelectedItem());
        } else if (e.getActionCommand().split(" ")[0].equals(InitializeView.AI_CHECKBOX_NAME) && e.getSource() instanceof JCheckBox) {
            im.setPlayerisAI(Integer.parseInt(e.getActionCommand().split(" ")[1]), ((JCheckBox) e.getSource()).isSelected());
        } else if (e.getActionCommand().equals(InitializeView.ACTION_MAP_SELECT) && e.getSource() instanceof JComboBox) {
            im.setMapPath((String)((JComboBox<String>) e.getSource()).getSelectedItem());
            im.setMapName((String)((JComboBox<String>) e.getSource()).getSelectedItem());
        }
    }

    /**
     * when a new character is inserted into the JTextfield change the player name
     *
     * @param e the event where a new character is inserted
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        addPlayerNames(e);
    }

    /**
     * when a character is removed into the JTextfield change the player name
     *
     * @param e the event where a character is removed
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        addPlayerNames(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    /**
     * Change the name of the players
     *
     * @param documentEvent the document to change players
     */
    private void addPlayerNames(DocumentEvent documentEvent) {
        Document source = documentEvent.getDocument();
        try {
            im.setPlayerName((int) source.getProperty("name"), source.getText(0, source.getLength()));
        } catch (BadLocationException badLocationException) {
            System.out.println("Contents: Unknown");
        }
    }
}
