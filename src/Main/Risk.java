package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Risk extends JFrame{
    CardLayout cardLayout;

    WelcomeScreen welcomeScreen;
    GameSetupScreen gameSetupScreen;
    GameScreen gameScreen;

    Risk(){
        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        this.welcomeScreen = new WelcomeScreen();
        this.gameSetupScreen = new GameSetupScreen();
        this.gameScreen = new GameScreen();

        this.add(welcomeScreen, "welcomeScreen");
        this.add(gameSetupScreen, "gameSetupScreen");
        this.add(gameScreen, "gameScreen");

        this.setSize(new Dimension(300, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setView(String view){
        cardLayout.show(this.getContentPane(), view);
    }

    private class WelcomeScreen extends JPanel{
        WelcomeScreen(){
            this.setBackground(Color.GREEN);
            this.add(new JLabel("Welcome"));

            JButton next = new JButton("Next");
            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Risk.this.setView("gameSetupScreen");
                }
            });
            this.add(next);
        }
    }

    private class GameSetupScreen extends JPanel{
        GameSetupScreen(){
            this.setBackground(Color.BLUE);
            this.add(new JLabel("Setup"));

            JButton next = new JButton("Next");
            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Risk.this.setView("gameScreen");
                }
            });
            this.add(next);
        }
    }

    private class GameScreen extends JPanel{
        GameScreen(){
            this.setBackground(Color.RED);
            this.add(new JLabel("Game"));

            JButton next = new JButton("Next");
            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Risk.this.setView("welcomeScreen");
                }
            });
            this.add(next);
        }
    }

    public static void main(String[] args) {
        new Risk();
    }
}