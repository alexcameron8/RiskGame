package Main;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates the MenuBar with options to start a new game and get help from the game manual and learn how to play.
 *
 * @Author Alex Cameron
 */
public class MenuBarView extends JMenuBar {
    private JMenu gameMenu;
    private JMenu helpMenu;
    private JMenu testMenu;
    private MenuBarController mbc;

    /**
     * Creates a new JMenuBar
     * @param riskModel current risk model
     * @param riskView current risk view
     */
    public MenuBarView(RiskModel riskModel, RiskView riskView){
        super();
        this.setBackground(new Color(224,224,224));
        mbc = new MenuBarController(riskModel,riskView);
        initMenu();
    }

    /**
     * Initializes the MenuItems and adds listeners to each item.
     */
    public void initMenu(){
        //create new JMenus
        gameMenu = new JMenu("Game Options");
        helpMenu = new JMenu("Help");
        testMenu = new JMenu("Testing");

        //Adding to JMenuBar
        this.add(gameMenu);
        this.add(helpMenu);
        this.add(testMenu);

        //Creating JMenuItems
        JMenuItem howToPlay = new JMenuItem("How To Play Risk");
        JMenuItem gameManual = new JMenuItem("Game Manual");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem transferAll = new JMenuItem("Transfer All");
        JMenuItem saveGame = new JMenuItem("Save");

        //Add JMenuItems to Menubar
        gameMenu.add(newGame);
        gameMenu.add(saveGame);
        helpMenu.add(howToPlay);
        helpMenu.add(gameManual);
        testMenu.add(transferAll);

        //Adding actionlisteners
        howToPlay.addActionListener(mbc);
        howToPlay.setActionCommand("howtoplay");
        gameManual.addActionListener(mbc);
        gameManual.setActionCommand("manual");
        newGame.addActionListener(mbc);
        newGame.setActionCommand("newGame");
        transferAll.addActionListener(mbc);
        transferAll.setActionCommand("transferAll");
        saveGame.addActionListener(mbc);
        saveGame.setActionCommand("saveGame");

    }
}
