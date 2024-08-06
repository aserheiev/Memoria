package org.eelcorp.ui;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    private final JButton mainButton;
    private final JButton settingsButton;
    private ImageIcon home_icon = new ImageIcon("C:\\Users\\user\\Desktop\\Memoria\\src\\main\\resources\\house.png");
    private ImageIcon settings_icon = new ImageIcon("C:\\Users\\user\\Desktop\\Memoria\\src\\main\\resources\\cogwheel.png");
    private final PercentageLabel infoLabel;
    // TODO: change to relative path

    public SidebarPanel() {
        setLayout(new GridLayout(3, 1));

        mainButton = new JButton();
        settingsButton = new JButton();

        // resizing of images
        Image home = home_icon.getImage();
        Image settings = settings_icon.getImage();
        Image newHome = home.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        Image newSettings = settings.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        home_icon = new ImageIcon(newHome);
        settings_icon = new ImageIcon(newSettings);

        mainButton.setIcon(new ImageIcon(newHome));
        settingsButton.setIcon(new ImageIcon(newSettings));

        JButton infoButton = new JButton();
        infoButton.setIcon(new ImageIcon("src/main/resources/fgm.png"));
        infoButton.setDisabledIcon(new ImageIcon("src/main/resources/fgm.png"));
        infoButton.setEnabled(false);

        infoLabel = new PercentageLabel();

        infoLabel.setPercentage(0.0f);


        add(mainButton);
        add(settingsButton);
        // add(infoButton);
        setVisible(true);
        add(infoLabel);
    }

    public JButton getMainButton() {
        return mainButton;
    }

    public void adjustFGMPercentage(float percentage) {
        infoLabel.setPercentage(percentage);
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }
}
