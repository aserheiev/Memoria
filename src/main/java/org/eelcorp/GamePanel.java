package org.eelcorp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private List<GameButton> buttons = new ArrayList<>();
    private List<GameButton> delayedButtons = new ArrayList<>();
    ImageIcon winner = new ImageIcon("src/main/resources/winner.jpg");
    GridLayout layout = new GridLayout();
    GridBagConstraints constraints;

    public GamePanel() {
        setLayout(layout);
        layout.setHgap(10);
        layout.setVgap(1);
    }

    public void changeLayout(int amount) {
        switch (amount) {
            case 6:
                layout.setRows(2);
                layout.setColumns(3);
                break;
            case 12:
                layout.setRows(3);
                layout.setColumns(4);
                break;
            case 20:
                layout.setRows(4);
                layout.setColumns(5);
                break;
            default:
                // LOL
                System.exit(0);
        }
    }

    public GameButton makeButton(int numby, String iconPath) {
        GameButton newButton = new GameButton(iconPath);
        newButton.setName(String.valueOf(numby));

        buttons.add(newButton);
        add(newButton);

        return newButton;
    }

    public List<GameButton> getButtons() {
        return buttons;
    }

    public List<GameButton> getDelayedButtons() {
        return delayedButtons;
    }

    public void flushDelayedButtons() {
        for (GameButton button : delayedButtons) {
            button.removeDelayListener();
        }
        delayedButtons.clear();
    }

    public void addDelayedButton(GameButton button) {
        delayedButtons.add(button);
    }

    public void showGameOverBox() {
        JOptionPane.showMessageDialog(this, "Game over man");
    }

    public void flushButtons() {
        buttons.clear();
    }


    // this just paints the background
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon background = new ImageIcon("src/main/resources/back2.jpeg");
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

}
