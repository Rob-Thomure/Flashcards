package com.company;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of(args);
        UserInterface userInterface;
        if (args.length == 2 && ("-import".equals(args[0]) || "-export".equals(args[0]))) {
            userInterface = new UserInterface(args[0], new File(args[1]));
        } else if (args.length == 4 && list.contains("-import") && list.contains("-export")) {
            File importFile = new File(list.get(list.indexOf("-import") + 1));
            File exportFile = new File(list.get(list.indexOf("-export") + 1));
            userInterface = new UserInterface(importFile, exportFile);
        }
        else {
            userInterface = new UserInterface();
        }
        userInterface.startGame();
    }
}
