package org.eelcorp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButton extends JButton {
    private final String DEFAULT_ICON_PATH = "src/main/resources/blankcard.png";
    private final String CHANGED_ICON_PATH;
    private ActionListener delayListener;
    private int currentIcon = 0; // 0 = default, 1 = changed


    public GameButton(String path) {
        CHANGED_ICON_PATH = path;
        setPreferredSize(new Dimension(166, 256));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);

        setIcon(new ImageIcon(DEFAULT_ICON_PATH));
        setDisabledIcon(new ImageIcon(CHANGED_ICON_PATH));
    }

    public void swapIcon() {
        if (currentIcon == 0) {
            setIcon(new ImageIcon(CHANGED_ICON_PATH));
            currentIcon = 1;
        } else {
            setIcon(new ImageIcon(DEFAULT_ICON_PATH));
            currentIcon = 0;
        }
        repaint();
        System.out.println("Icon was supposed to be swapped. Current icon: " + currentIcon + " ," + getIcon().toString());
    }

    public void addDelayListener() {
        // sets a timer, when clicked, button is disabled
        int delay = 2000;
        GameButton button = this;

        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Timer is firing btw");
                button.setEnabled(true);
                button.swapIcon();
                button.repaint();
            }
        });

        timer.setRepeats(false);

        delayListener = e -> {
            System.out.println("Delay is firing btw");
            button.swapIcon();
            button.repaint();
            button.setEnabled(false);
            timer.start();
        };

        this.addActionListener(delayListener);

    }

    public void removeDelayListener() {
        removeActionListener(delayListener);
        delayListener = null;
    }


    Runnable disableButtonWithTimer = () -> {
        setEnabled(false);

        // Create a timer that will re-enable the button after 3 seconds (3000 milliseconds)
        Timer timer = new Timer(2000, event -> {
            setEnabled(true);
            swapIcon();
            Controller.guessInProgress = false;
        });

        // Ensure the timer only fires once
        timer.setRepeats(false);
        timer.start();
    };

}
