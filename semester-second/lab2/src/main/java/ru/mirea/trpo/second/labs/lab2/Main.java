package ru.mirea.trpo.second.labs.lab2;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file = new File(Main.class.getResource("/input/example_1.txt").getFile());
        ControlPanel panel = new ControlPanel(file);
        panel.go();
    }

}
