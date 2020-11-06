package Main;

import javax.swing.*;
import java.awt.*;

public class MenuBarView extends JMenuBar {
    private JMenu optionsMenu;
    private JMenu helpMenu;

    public MenuBarView(){
        super();
        this.setBackground(new Color(224,224,224));
        initMenu();
    }

    public void initMenu(){
        optionsMenu = new JMenu("Options");
        helpMenu = new JMenu("Help");
        this.add(optionsMenu);
        this.add(helpMenu);
        //Creating JMenuItems
        JMenuItem howToPlay = new JMenuItem("How To Play Risk");
        JMenuItem gameManual = new JMenuItem("Game Manual");
        JMenuItem newGame = new JMenuItem("New Game");
        optionsMenu.add(howToPlay);
        optionsMenu.add(newGame);
        helpMenu.add(gameManual);
    }
}
