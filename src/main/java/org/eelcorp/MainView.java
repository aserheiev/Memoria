package org.eelcorp;

import com.formdev.flatlaf.FlatLightLaf;
import org.eelcorp.ui.MainPanel;
import org.eelcorp.ui.SidebarPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame {
    private GameModel gameModel;
    private Controller controller;
    private SidebarPanel sidebarPanel;
    private MainPanel mainPanel;
    private JLabel backgroundLabel;
    ImageIcon background = new ImageIcon("src/main/resources/back2.jpeg");

    public static void main(String[] args) throws Exception {
        Runnable initFrame = new Runnable() {
            @Override
            public void run() {
                new MainView();
            }
        };

        SwingUtilities.invokeAndWait(initFrame);
    }

    public MainView() {
        super("Memoria");
        FlatLightLaf.setup();

        try {
            setIconImage(ImageIO.read(new File("src/main/resources/memory.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setSize(1300, 1000);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        backgroundLabel = new JLabel();
        backgroundLabel.setIcon(background);
        add(backgroundLabel);

        gameModel = new GameModel();
        mainPanel = new MainPanel(this);

        sidebarPanel = new SidebarPanel();
        sidebarPanel.setBounds(0, 0, 100, 1000);
        add(sidebarPanel);

        mainPanel = new MainPanel(this);
        mainPanel.setBounds(100, 0, 1200, 1000);
        add(mainPanel);
        controller = new Controller(gameModel, this);
    }

    public MainPanel getMainMenuPanel() {
        return mainPanel;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

}
