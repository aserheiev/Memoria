package org.eelcorp;

import org.eelcorp.ui.GameButton;
import org.eelcorp.ui.SidebarPanel;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.List;

// TODO: 1. Implement a check that there are enough images
// TODO: 2. Switch to GridBagLayout
// TODO: 3. I think there's no more need for button icon swapping if I'm doing it via disabled icons. Clean it up
// TODO: 4. Look into fixes for the EDT game in progress shenanigans

public class Controller {
    private final GameModel gameModel;
    private final MainView mainView;
    private int previousGuess;
    private final File CATEGORY_FOLDER = new File("src/main/resources/categories");
    private boolean gameInProgress = false;
    private boolean poolNotEmpty;

    // this is real fucking JANK but for some reason if I define the delay runnable here, the event dispatcher thread throws an NPE
    public static boolean guessInProgress = false;

    public Controller(GameModel gameModel, MainView mainView) {
        this.gameModel = gameModel;
        this.mainView = mainView;
        initCheckboxes();
        initListeners();
    }

    private void initCheckboxes() {
        List<String> paths = new ArrayList<>();
        List<String> dirNames = new ArrayList<>();

        File[] files = CATEGORY_FOLDER.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    paths.add(file.getPath());
                    dirNames.add(file.getName());
                }
            }

            for (int i = 0; i < dirNames.size(); i++) {
                String dirname = dirNames.get(i);
                JCheckBox checkBox = new JCheckBox();

                if (i == 0) {
                    checkBox.setSelected(true);
                    CardPool.addSourcePath("src/main/resources/categories/" + dirname);
                    CardPool.assemblePool();
                }

                checkBox.setText(dirname);

                checkBox.addActionListener(e -> {
                    int checkedBoxes = mainView.getMainMenuPanel().getSettingsPanel().numberOfCheckedBoxes(checkBox);

                    if (checkedBoxes == 0) {
                        JOptionPane.showMessageDialog(checkBox, "At least one category must be selected!");
                        checkBox.setSelected(true);
                    } else {
                        if (checkBox.isSelected()) {
                            CardPool.addSourcePath("src/main/resources/categories/" + dirname);
                            CardPool.assemblePool();
                        } else {
                            CardPool.removeSourcePath("src/main/resources/categories/" + dirname);
                            poolNotEmpty = CardPool.assemblePool();

                            if (!poolNotEmpty) {
                                JOptionPane.showMessageDialog(checkBox, "The categories left don't have any images in them!");
                                checkBox.setSelected(true);
                                CardPool.addSourcePath("src/main/resources/categories/" + dirname);
                                poolNotEmpty = CardPool.assemblePool();
                            }
                        }
                    }

                });

                mainView.getMainMenuPanel().getSettingsPanel().addCheckbox(checkBox);
            }

        } else {
            // this gets triggered if there are no folders in the categories folder, or if it doesn't exist (?)
            mainView.getMainMenuPanel().getSettingsPanel().add(new JLabel("No categories found! Please add some folders" +
                    "with images to src/resources/categories!", new ImageIcon("src/main/resources/gameico/owo.png"), SwingConstants.CENTER));
        }
    }

    private void initListeners() {
        SidebarPanel sidebarPanel = mainView.getSidebarPanel();

        // home and settings button action listeners
        // both have confirmation dialogue to quit if a game is in progress
        sidebarPanel.getMainButton().addActionListener(e -> {
            if (gameInProgress) {
                int optionIndex = JOptionPane.showConfirmDialog(
                        null,
                        "Going back will reset your game. Are you sure you want to quit?",
                        "WARNING WARNING",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (optionIndex == JOptionPane.YES_OPTION) {
                    gameInProgress = false;
                    mainView.getMainMenuPanel().showCard1();
                    mainView.getMainMenuPanel().getGamePanel().removeAll();
                    mainView.getMainMenuPanel().getGamePanel().flushDelayedButtons();
                    gameModel.flushMemoryObjects();
                    mainView.getMainMenuPanel().getGamePanel().flushButtons();
                    mainView.getMainMenuPanel().getGamePanel().repaint();
                } else if (optionIndex == JOptionPane.NO_OPTION) {
                    // do nothing
                }
            } else {
                mainView.getMainMenuPanel().showCard1();
                mainView.getMainMenuPanel().getGamePanel().removeAll();
                mainView.getMainMenuPanel().getGamePanel().flushDelayedButtons();
                gameModel.flushMemoryObjects();
                mainView.getMainMenuPanel().getGamePanel().flushButtons();
                mainView.getMainMenuPanel().getGamePanel().repaint();
            }
        });

        sidebarPanel.getSettingsButton().addActionListener(e -> {
            if (gameInProgress) {
                int optionIndex = JOptionPane.showConfirmDialog(
                        null,
                        "Going back will reset your game. Are you sure you want to quit?",
                        "WARNING WARNING",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (optionIndex == JOptionPane.YES_OPTION) {
                    gameInProgress = false;
                    mainView.getMainMenuPanel().showCard2();
                    mainView.getMainMenuPanel().getGamePanel().removeAll();
                    mainView.getMainMenuPanel().getGamePanel().flushDelayedButtons();
                    gameModel.flushMemoryObjects();
                    mainView.getMainMenuPanel().getGamePanel().flushButtons();
                    mainView.getMainMenuPanel().getGamePanel().repaint();
                } else if (optionIndex == JOptionPane.NO_OPTION) {
                    // do nothing
                }
            } else {
                mainView.getMainMenuPanel().showCard2();
                mainView.getMainMenuPanel().getGamePanel().removeAll();
                mainView.getMainMenuPanel().getGamePanel().flushDelayedButtons();
                gameModel.flushMemoryObjects();
                mainView.getMainMenuPanel().getGamePanel().flushButtons();
                mainView.getMainMenuPanel().getGamePanel().repaint();
            }
        });

        // 2x3 start button event
        mainView.getMainMenuPanel().getStartPanel().getStart2x3().addActionListener(e -> {
            if (CardPool.getImages().size() >= 3) {
                gameModel.primeList(6);
                mainView.getMainMenuPanel().showGame();
                makeGameButtons(6);
                gameInProgress = true;
                updatePercentage();
            } else {
                JOptionPane.showMessageDialog(mainView, "There are not enough images in the selected categories!", "Good job", JOptionPane.ERROR_MESSAGE);
            }

        });

        // 3x4 start button event
        mainView.getMainMenuPanel().getStartPanel().getStart3x4().addActionListener(e -> {
            if (CardPool.getImages().size() >= 6) {
                gameModel.primeList(12);
                mainView.getMainMenuPanel().showGame();
                makeGameButtons(12);
                gameInProgress = true;
                updatePercentage();
            } else {
                JOptionPane.showMessageDialog(mainView, "There are not enough images in the selected categories!", "Good job", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 2x3 start button event
        mainView.getMainMenuPanel().getStartPanel().getStart4x5().addActionListener(e -> {
            if (CardPool.getImages().size() >= 10) {
                gameModel.primeList(20);
                mainView.getMainMenuPanel().showGame();
                makeGameButtons(20);
                gameInProgress = true;
                updatePercentage();
            } else {
                JOptionPane.showMessageDialog(mainView, "There are not enough images in the selected categories!", "Good job", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void makeGameButtons(int amount) {

        mainView.getMainMenuPanel().getGamePanel().changeLayout(amount);

        for (int i = 0; i < amount; i++) {
            GameButton newButton = mainView.getMainMenuPanel().getGamePanel().makeButton(i, gameModel.getImagePathByIndex(i));

            newButton.addActionListener(e -> {
                if (!guessInProgress) {
                    int actualNumber = Integer.parseInt(newButton.getName());
                    System.out.println("Button #" + actualNumber + " pressed!");

                    System.out.println("This would guess the number " + gameModel.getMemoryObjectListIDs()[actualNumber] + ".");

                    // we're receiving a response code from the game model that tells us the results,
                    // and take appropriate action depending on that

                    int guessResponseStatus = gameModel.makeGuess(actualNumber);


                    switch (guessResponseStatus) {
                        case 0:
                            System.out.println("First guess has been made");
                            previousGuess = actualNumber;
                            newButton.setEnabled(false);
                            newButton.swapIcon();
                            newButton.repaint();
                            primeDelayListeners(newButton);
                            break;
                        case 1:
                            System.out.println("Second guess, matching!");
                            mainView.getMainMenuPanel().getGamePanel().flushDelayedButtons();
                            newButton.setEnabled(false);
                            newButton.swapIcon();
                            newButton.repaint();
                            updatePercentage();
                            break;
                        case 2:
                            System.out.println("Second guess, non-matching.");

                            // todo: make both buttons hang on the icon for a second or two, then swap back and then become active again
                            // mainView.getMainMenuPanel().getGamePanel().getButtons().get(previousGuess).setEnabled(true);
                            // mainView.getMainMenuPanel().getGamePanel().getButtons().get(previousGuess).swapIcon();

                            // launches a runnable with a timer on a selected button that keeps it disabled for 2 seconds for consistency
                            guessInProgress = true;
                            /*
                            newButton.disableButtonWithTimer = () -> {
                                newButton.setEnabled(false);

                                // Create a timer that will re-enable the button after 3 seconds (3000 milliseconds)
                                Timer timer = new Timer(2000, event -> {
                                    newButton.setEnabled(true);
                                    newButton.swapIcon();
                                    guessInProgress = false;
                                });

                                // Ensure the timer only fires once
                                timer.setRepeats(false);
                                timer.start();
                            };

                             */

                            // I'm sure there's a better way to do this kind of call chain but my brain's too smooth
                            SwingUtilities.invokeLater(mainView.getMainMenuPanel().getGamePanel().getButtons().get(previousGuess).disableButtonWithTimer);

                            // getting rid of implanted delay listeners
                            mainView.getMainMenuPanel().getGamePanel().flushDelayedButtons();

                            break;
                        case 3:
                            newButton.setEnabled(false);
                            updatePercentage();
                            winDialogue();
                            gameModel.flushMemoryObjects();
                            mainView.getMainMenuPanel().getGamePanel().flushDelayedButtons();
                            mainView.getMainMenuPanel().getGamePanel().flushButtons();
                            mainView.getMainMenuPanel().getGamePanel().removeAll();
                            mainView.getMainMenuPanel().getGamePanel().repaint();
                            mainView.getMainMenuPanel().showWinner();
                            gameInProgress = false;
                            CardPool.assemblePool();
                            break;
                        default:
                            System.out.println("Something went wrong here");
                            break;
                    }
                }
            });
        }
    }

    // dialogue box when the game is over
    private void winDialogue() {
        mainView.getMainMenuPanel().getGamePanel().showGameOverBox();
    }

    // attaches delay event listeners to non-matching, non-guessed buttons
    private void primeDelayListeners(GameButton firstButton) {
        List<GameButton> gameButtons = mainView.getMainMenuPanel().getGamePanel().getButtons();
        int[] memoryIDs = gameModel.getMemoryObjectListIDs();
        boolean[] memoryGuessed = gameModel.getMemoryObjectStatus();

        // if guessed, do nothing
        // if matching, do nothing
        // if not guessed, and non-matching, attach the delay listeners that will disable the buttons for 2 seconds if clicked

        for (int i = 0; i < gameButtons.size(); i++) {
            GameButton gameButton = gameButtons.get(i);

            if (!memoryGuessed[i]) {
                // comparing the IDs of the currently pressed button and all the others in order to find a match and exclude it
                int currentMemoryID = memoryIDs[Integer.parseInt(gameButton.getName())];
                int firstButtonMemoryID = memoryIDs[Integer.parseInt(firstButton.getName())];

                if (currentMemoryID != firstButtonMemoryID) {
                    // todo: here we have only the non-guessed, non-matching buttons. enable the action listener
                    gameButton.addDelayListener();
                    System.out.println("Delay listener has been added?");
                    mainView.getMainMenuPanel().getGamePanel().addDelayedButton(gameButton);
                }
            }
        }
    }

    private void updatePercentage() {
        int totalMatches = gameModel.getMemoryObjectListIDs().length / 2;
        int matchesMade = totalMatches - gameModel.getMatchesLeft();
        float percentage = (float) matchesMade / (float) totalMatches;
        mainView.getSidebarPanel().adjustFGMPercentage(percentage);
    }
}
