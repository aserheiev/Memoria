package org.eelcorp.ui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    private final ImageIcon background = new ImageIcon("src/main/resources/back2.jpeg");
    private final JButton start2x3;
    private final JButton start3x4;
    private final JButton start4x5;

    public StartPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel logo = new JLabel(new ImageIcon("src/main/resources/startlogo.png"));

        start2x3 = new JButton(new ImageIcon("src/main/resources/start2x3.png"));
        c.fill = GridBagConstraints.VERTICAL;
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        add(logo, c);
        c.gridy++;
        add(start2x3, c);
        start2x3.setFocusPainted(false);

        start3x4 = new JButton(new ImageIcon("src/main/resources/start3x4.png"));
        // start3x4.setBorderPainted(false);
        // start3x4.setContentAreaFilled(false);
        c.gridy++;
        add(start3x4, c);

        start4x5 = new JButton(new ImageIcon("src/main/resources/start4x5.png"));
        c.gridy++;
        add(start4x5, c);
    }

    public JButton getStart2x3() {
        return start2x3;
    }

    public JButton getStart3x4() {
        return start3x4;
    }

    public JButton getStart4x5() {
        return start4x5;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
    }



}
