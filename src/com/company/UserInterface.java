package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class UserInterface {
    private final Flashcards flashcards = new Flashcards();

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
                    System.out.println("Bye bye!");
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }

        }
    }

    private String getAction() {
        System.out.println("Input the action (add, remove, import, export, ask, exit):");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim().toLowerCase();
    }

    private void addCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The card:");
        String term = scanner.nextLine();
        if (flashcards.isDuplicateTerm(term)) {
            System.out.printf("The card \"%s\" already exists.%n%n", term);
            return;
        }
        System.out.println("The definition of the card:");
        String definition = scanner.nextLine();
        if (flashcards.isDuplicateDefinition(definition)) {
            System.out.printf("The definition \"%s\" already exists%n%n", definition);
            return;
        }
        flashcards.addFlashcard(term, definition);
        System.out.printf("The pair (\"%s\":\"%s\") has been added.%n%n", term, definition);
    }

    private void removeCard() {
        System.out.println("Which card?");
        Scanner scanner = new Scanner(System.in);
        String term = scanner.nextLine();
        if (Objects.equals(null, flashcards.removeFlashcard(term))) {
            System.out.printf("Can't remove \"%s\": there is no such card.%n%n", term);
        } else {
            System.out.println("The card has been removed.\n");
        }
    }

    private void importCards() {
        System.out.println("File name:");
        File file = new File(new Scanner(System.in).nextLine());
        int cardCount = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String term = scanner.nextLine();
                String definition = scanner.nextLine();
                flashcards.addFlashcard(term, definition);
                cardCount++;
            }
            System.out.printf("%d cards have been loaded.%n%n", cardCount);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.\n");
        }
    }

    private void exportCards() {
        System.out.println("File name:");
        File file = new File(new Scanner(System.in).nextLine());
        try (PrintWriter printWriter = new PrintWriter(file)){
            for (var card : flashcards.getFlashcards().entrySet()) {
                printWriter.println(card.getKey());
                printWriter.println(card.getValue());
            }
            System.out.printf("%d cards have been saved.%n%n", flashcards.getFlashcardsCount());
        } catch (FileNotFoundException e) {
            System.out.println("File not found.\n");
        }
    }

    private void askQuestions() {
        Map<String, String>  cards = flashcards.getFlashcards();
        System.out.println("How many times to ask?");
        int numCards = new Scanner(System.in).nextInt();
        int index = 0;
        for (var card : cards.entrySet()) {
            System.out.printf("Print the definition of \"%s\":%n", card.getKey());
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            if (answer.equals(card.getValue())) {
                System.out.println("Correct!");
            } else {
                System.out.printf("Wrong. The right answer is \"%s\"", card.getValue());
                if (cards.containsValue(answer)) {
                    String term = "";
                    for (var entry : cards.entrySet()) {
                        if (entry.getValue().equals(answer)) {
                            term = entry.getKey();
                        }
                    }
                    System.out.printf(", but your definition is correct for \"%s\"", term);
                }
                System.out.println(".");
            }
            index++;
            if (index >= numCards) {
                System.out.println();
                break;
            }
        }
    }
}
