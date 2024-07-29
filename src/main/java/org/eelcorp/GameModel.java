package org.eelcorp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private int guessesMade;
    private int currentGuess;
    private int matchesLeft;
    private List<MemoryObject> memoryObjects = new ArrayList<>();

    // makes a list of memory objects based on the size of the grid
    public void primeList(int number) {
        List<MemoryObject> tempMemoryObjects = new ArrayList<>();
        matchesLeft = number / 2;
        guessesMade = 0;
        currentGuess = -1;
        Random random = new Random();

        List<String> images = new ArrayList<>(CardPool.getImages());

        for (int i = 0; i < number / 2; i++) {
            String imagePath = images.remove(random.nextInt(images.size()));

            MemoryObject memoryObject = new MemoryObject(i, imagePath);
            // adding the refs twice lol
            tempMemoryObjects.add(memoryObject);
            tempMemoryObjects.add(memoryObject);
        }

        // creating a shuffled list
        while (!tempMemoryObjects.isEmpty()) {
            System.out.println("SIZE: " + tempMemoryObjects.size());
            int randomIndex = random.nextInt(tempMemoryObjects.size());
            memoryObjects.add(tempMemoryObjects.remove(randomIndex));
            System.out.println("WE DID IT");
        }


        // just checking the order in the console
        System.out.println("DEBUG\nLIST PRIMED:\n");
        for (MemoryObject memoryObject : memoryObjects) {
            System.out.println(memoryObject.toString());
        }

    }

    // logic for making a guess that returns a response code based on the result
    public int makeGuess(int index) {
        switch (guessesMade) {
            case 0:
                currentGuess = index;
                guessesMade = 1;
                return 0; // status 0: first guess
            case 1:
                // Alright this requires a bit of explanation. We create a list of MemoryObject, but also buttons in the UI.
                // The UI buttons simply return an index, and then we check which MemoryObject we have at that index here.
                // They don't really know about each other but also they don't really need to
                // Except for one case where I check if the object at that index is guessed or not, which is why I have a method for that below.
                if (memoryObjects.get(index).equals(memoryObjects.get(currentGuess))) {
                    memoryObjects.get(index).setGuessed(true);
                    guessesMade = 0;
                    matchesLeft--;

                    if (matchesLeft == 0) {
                        return 3;  // status 3: correct guess, game over man
                    } else {
                        return 1;  // status 1: correct guess, game not over
                    }
                } else {
                    guessesMade = 0;
                    return 2; //status 2: non-matching
                }
            default:
                return -1;  // this should never happen
        }
    }

    // returning memory object IDs for index-based comparison
    public int[] getMemoryObjectListIDs() {
        int[] numbies = new int[memoryObjects.size()];

        for (int i = 0; i < memoryObjects.size(); i++) {
            MemoryObject memoryObject = memoryObjects.get(i);
            numbies[i] = memoryObject.getId();
        }

        return numbies;
    }

    public String getImagePathByIndex(int index) {
        return memoryObjects.get(index).getImagePath();
    }

    // returning the guessed status of memory objects for index-based checking
    public boolean[] getMemoryObjectStatus() {
        boolean[] status = new boolean[memoryObjects.size()];
        for (int i = 0; i < memoryObjects.size(); i++) {
            MemoryObject memoryObject = memoryObjects.get(i);
            status[i] = memoryObject.isGuessed();
        }
        return status;
    }

    // this exists purely for the glove percentage
    public int getMatchesLeft() {
        return matchesLeft;
    }

    public void flushMemoryObjects() {
        memoryObjects.clear();
    }

    // describes every card, nested class just for internal reference
    class MemoryObject {
        int id;
        int guessAttempt = 0;
        boolean guessed = false;
        String imageIconPath;

        public MemoryObject(int id, String imageIconPath) {
            this.id = id;
            this.imageIconPath = imageIconPath;
        }

        public int getId() {
            return id;
        }

        public boolean isGuessed() {
            return guessed;
        }

        public void setGuessed(boolean guessed) {
            this.guessed = guessed;
        }

        public String getImagePath() {
            return imageIconPath;
        }

        // getting info about each object for debugging
        @Override
        public String toString() {
            return "MemoryObject [id=" + id + ", guessAttempt=" + guessAttempt + "]";
        }

        // todo: maybe make a comparator? right now it's the Controller checking if they're equivalent, but also it wouldn't know this class anyway
    }
}
