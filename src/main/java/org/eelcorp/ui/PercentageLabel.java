package org.eelcorp.ui;

import javax.swing.*;
import java.awt.*;

public class PercentageLabel extends JLabel {
    private float percentage = 0;
    private ImageIcon imageIcon;
    private static final int RECT_WIDTH = 33;
    private static final int RECT_HEIGHT = 94;
    private static final int IMAGE_WIDTH = 100;
    private static final int IMAGE_HEIGHT = 267;

    public PercentageLabel() {
        this.percentage = percentage;
        setPreferredSize(new Dimension(33, 94));
        imageIcon = new ImageIcon("src/main/resources/fgm.png");
        setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw the pic
        if (imageIcon != null) {
            imageIcon.paintIcon(this, g, 0, 0);
        }

        // don't ask me I googled this, but it sure is a graphics object (or a copy idk)
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        // height of the rectangle based on the percentage
        // -10 offset is here because the placement was WRONG and I don't know why
        int filledHeight = (int) (RECT_HEIGHT * percentage);
        int yOffset = IMAGE_HEIGHT - filledHeight - 11;

        // actually draw it
        g2d.fillRect((IMAGE_WIDTH - RECT_WIDTH) / 2, yOffset, RECT_WIDTH, filledHeight);

    }

    // update it when we progress
    public void setPercentage(float percentage) {
        System.out.println("NOW PAINTING PERCENTAGE: " + percentage);
        this.percentage = percentage;
        repaint();
    }

}