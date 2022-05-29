package com.company;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class UserInterface {
    private final Flashcards flashcards;
    private final Log log;
    private final File exportFile;

    public UserInterface(String mode, File file) {
        this.flashcards = new Flashcards();
        this.log = new Log();
        if ("-import".equals(mode)) {
            this.exportFile = null;
            importCards(String.valueOf(file));
        } else {
            this.exportFile = file;
        }
    }

    public UserInterface(File importfile, File exportFile) {
        this.flashcards = new Flashcards();
        this.log = new Log();
        importCards(String.valueOf(importfile));
        this.exportFile = exportFile;
    }

    public UserInterface() {
        this.flashcards = new Flashcards();
        this.log = new Log();
        this.exportFile = null;
    }

    public void startGame() {
        boolean quit = false;
        while (!quit) {
            switch (getAction()) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                    importCards();
                    break;
                case "export":
                    exportCards();
                    break;
                case "ask":
                    askQuestions();
                    break;
                case "exit":
                    quit = true;
                    if (null != exportFile) {
                        exportCards(String.valueOf(this.exportFile));
                    }
                    System.out.println("Bye bye!");
                    break;
                case "log":
                    saveLog();
                    break;
                case "hardest card":
                    hardestCard();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                default:
                    printOutput("Invalid input!\n");
                    break;
            }

        }
    }

    private void resetStats() {
        flashcards.restStats();
        printOutput("Card statistics have been reset.\n");
    }

    private void hardestCard() {
        String[] hardestCards = flashcards.getHardestCards();
        if (hardestCards[0].equals("0")) {
            printOutput("There are no cards with errors.\n");
        } else if (hardestCards.length == 2) {
            printOutput(String.format("The hardest card is \"%s\". You have %s errors answering it.%n",
                    hardestCards[1], hardestCards[0]));
        } else {
            StringBuilder stringBuilder = new StringBuilder("The hardest cards are ");
            for (int i = 1; i < hardestCards.length; i++) {
                stringBuilder.append(String.format("\"%s\"", hardestCards[i]));
                if (i < hardestCards.length - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(String.format(". You have %s errors answering them.%n", hardestCards[0]));
            printOutput(stringBuilder.toString());
        }
    }

    private void saveLog() {
        printOutput("File name:");
        log.saveLog(readInput());
        printOutput("The log has been saved.\n");
    }

    private String getAction() {
        printOutput("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        return readInput().trim().toLowerCase();
    }

    private void addCard() {
        printOutput("The card:");
        String term = readInput();
        if (flashcards.isDuplicateTerm(term)) {
            printOutput(String.format("The card \"%s\" already exists.%n%n", term));
            return;
        }
        printOutput("The definition of the card:");
        String definition = readInput();
        if (flashcards.isDuplicateDefinition(definition)) {
            printOutput(String.format("The definition \"%s\" already exists%n%n", definition));
            return;
        }
        flashcards.addFlashcard(term, definition);
        printOutput(String.format("The pair (\"%s\":\"%s\") has been added.%n", term, definition));
    }

    private void removeCard() {
        printOutput("Which card?");
        String term = readInput();
        if (Objects.equals(null, flashcards.removeFlashcard(term))) {
            printOutput(String.format("Can't remove \"%s\": there is no such card.%n%n", term));
        } else {
            printOutput("The card has been removed.\n");
        }
    }

    private void importCards() {
        printOutput("File name:");
        int numImported = flashcards.importCards(readInput());
        if (numImported >= 0) {
            printOutput(String.format("%d cards have been loaded.%n", numImported));
        } else {
            printOutput("File not found.\n");
        }
    }

    private void importCards(String file) {
        int numImported = flashcards.importCards(file);
        if (numImported >= 0) {
            printOutput(String.format("%d cards have been loaded.%n", numImported));
        } else {
            printOutput("File not found.\n");
        }
    }

    private void exportCards() {
        printOutput("File Name:");
        int numSaved = flashcards.exportCards(readInput());
        if (numSaved >= 0) {
            printOutput(String.format("%d cards have been saved.%n", numSaved));
        } else {
            printOutput("File not found.\n");
        }
    }

    private void exportCards(String file) {
        int numSaved = flashcards.exportCards(file);
        if (numSaved >= 0) {
            printOutput(String.format("%d cards have been saved.%n", numSaved));
        } else {
            printOutput("File not found.\n");
        }
    }

    private void askQuestions() {
        Map<String, String>  cards = flashcards.getFlashcards();
        printOutput("How many times to ask?");
        int numCards = Integer.parseInt(readInput());
        int index = 0;
        for (var card : cards.entrySet()) {
            printOutput(String.format("Print the definition of \"%s\":", card.getKey()));
            String answer = readInput();
            if (answer.equals(card.getValue())) {
                printOutput("Correct!");
            } else {
                flashcards.addWrongAnswer(card.getKey());
                StringBuilder stringBuilder = new StringBuilder(String.format("Wrong. The right answer is \"%s\"",
                        card.getValue()));
                if (cards.containsValue(answer)) {
                    String term = "";
                    for (var entry : cards.entrySet()) {
                        if (entry.getValue().equals(answer)) {
                            term = entry.getKey();
                        }
                    }
                    stringBuilder.append(String.format(", but your definition is correct for \"%s\" card",
                            term));
                }
                stringBuilder.append(".");
                printOutput(stringBuilder.toString());
            }
            index++;
            if (index >= numCards) {
                printOutput("");
                break;
            }
        }
    }

    public void printOutput(String output) {
        System.out.println(output);
        log.addLogEntry(output);
    }

    public String readInput() {
        String input = new Scanner(System.in).nextLine();
        log.addLogEntry(input);
        return input;
    }
}
