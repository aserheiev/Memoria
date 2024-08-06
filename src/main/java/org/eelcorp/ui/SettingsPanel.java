package org.eelcorp.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsPanel extends JPanel {
    private ImageIcon background = new ImageIcon("src/main/resources/back2.jpeg");
    private List<JCheckBox> checkboxes = new ArrayList<>();
    GridBagConstraints constraints;

    public SettingsPanel() {
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.VERTICAL;

        JLabel owo = new JLabel(new ImageIcon("src/main/resources/cogwheel2.png"));

        constraints.ipady = 50;

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(owo, constraints);
        constraints.ipady = 20;

    }

    public void addCheckbox(JCheckBox checkbox) {
        checkboxes.add(checkbox);

        constraints.gridy++;

        add(checkbox, constraints);
    }

    public int numberOfCheckedBoxes(JCheckBox currentCheckbox) {
        int count = 0;

        for (JCheckBox checkbox : checkboxes) {
            if (!checkbox.equals(currentCheckbox)) {
                if (checkbox.isSelected()) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public List<JCheckBox> getCheckboxes() {
        return checkboxes;
    }
}
