package Main;

import javax.swing.*;
import java.awt.*;

public class MenuBarView extends JMenuBar {
    private JMenu gameMenu;
    private JMenu helpMenu;
    private JMenu testMenu;
    private MenuBarController mbc;


    public MenuBarView(RiskModel riskModel, RiskView riskView){
        super();
        this.setBackground(new Color(224,224,224));
        mbc = new MenuBarController(riskModel,riskView);
        initMenu();
    }

    public void initMenu(){
        gameMenu = new JMenu("Game Options");
        helpMenu = new JMenu("Help");
        testMenu = new JMenu("Testing");
        this.add(gameMenu);
        this.add(helpMenu);
        this.add(testMenu);
        //Creating JMenuItems
        JMenuItem howToPlay = new JMenuItem("How To Play Risk");
        JMenuItem gameManual = new JMenuItem("Game Manual");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem transferAll = new JMenuItem("Transfer All");

        gameMenu.add(newGame);
        helpMenu.add(howToPlay);
        helpMenu.add(gameManual);
        testMenu.add(transferAll);

        howToPlay.addActionListener(mbc);
        howToPlay.setActionCommand("howtoplay");
        gameManual.addActionListener(mbc);
        gameManual.setActionCommand("manual");
        newGame.addActionListener(mbc);
        newGame.setActionCommand("newGame");
        transferAll.addActionListener(mbc);
        transferAll.setActionCommand("transferAll");

    }
}
