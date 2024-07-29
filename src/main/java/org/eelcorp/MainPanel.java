package org.eelcorp;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private MainView mainView;
    private CardLayout cardLayout;
    private StartPanel startPanel;
    private SettingsPanel settingsPanel;
    private GamePanel gamePanel;


    public MainPanel(MainView mainView) {
        FlatLightLaf.setup();
        this.mainView = mainView;

        cardLayout = new CardLayout();

        setLayout(cardLayout);

        startPanel = new StartPanel();
        settingsPanel = new SettingsPanel();
        gamePanel = new GamePanel();

        JPanel winnerPanel = new JPanel() {
            ImageIcon winrar = new ImageIcon("src/main/resources/winner.jpg");

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(winrar.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        add("Start Panel", startPanel);
        add("Settings Panel", settingsPanel);
        add("Game Panel", gamePanel);
        add("Winner Panel", winnerPanel);
    }

    public void showCard1() {
        cardLayout.show(this, "Start Panel");
    }

    public void showCard2() {
        cardLayout.show(this, "Settings Panel");
    }

    public void showGame() {
        cardLayout.show(this, "Game Panel");
    }

    public void showWinner() {
        cardLayout.show(this, "Winner Panel");
    }

    public StartPanel getStartPanel() {
        return startPanel;
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

}
