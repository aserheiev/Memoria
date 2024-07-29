package org.eelcorp;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardPool {
    private static final List<String> sourcePaths = new ArrayList<String>();
    private static final Set<String> images = new HashSet<>();

    // makes a list of images of all selected categories
    public static boolean assemblePool() {
        images.clear();
        for (String path : sourcePaths) {
            File currentDirectory = new File(path);
            File[] files = currentDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")) {
                            System.out.println("Adding image: " + file.getAbsolutePath());
                            images.add(file.getAbsolutePath());
                        }
                    }
                }
            }
        }

        System.out.println("The pool has been reassembled!");
        System.out.println("Current paths: ");
        sourcePaths.forEach(System.out::println);
        System.out.println("Number of images in the pool: " + images.size());

        return !images.isEmpty();
    }

    public static Set<String> getImages() {
        return images;
    }

    public static void addSourcePath(String path) {
        sourcePaths.add(path);
    }

    public static void removeSourcePath(String path) {
        sourcePaths.remove(path);
        assemblePool();
    }
}
