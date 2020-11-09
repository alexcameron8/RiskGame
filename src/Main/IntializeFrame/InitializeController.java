package Main.IntializeFrame;

import Main.RiskView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitializeController implements ActionListener, DocumentListener {

    private InitializeModel im;
    private InitializeView iv;

    public InitializeController(InitializeModel im, InitializeView iv) {
        this.im = im;
        this.iv = iv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("numPlayers")) {
            im.setPlayerNumbers((int) (((JComboBox) e.getSource()).getSelectedItem()));
        } else if (e.getActionCommand().split(" ")[0].equals("colour")) {
            im.setPlayerColour(Integer.parseInt(e.getActionCommand().split(" ")[1]), (String) ((JComboBox) e.getSource()).getSelectedItem());
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        addPlayerNames(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        addPlayerNames(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    private void addPlayerNames(DocumentEvent documentEvent) {
        Document source = documentEvent.getDocument();
        try {
            im.setPlayerName((int) source.getProperty("name"), source.getText(0, source.getLength()));
        } catch (BadLocationException badLocationException) {
            System.out.println("Contents: Unknown");
        }
    }
}
