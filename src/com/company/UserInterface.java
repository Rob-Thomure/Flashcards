package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    Flashcards flashcards;

    public void startGame() {
        inputNumFlashcards();
        createCards();
        testTheUser();
    }

    private void inputNumFlashcards() {
        while (true) {
            try {
                System.out.println("Input the number of cards:");
                Scanner scanner = new Scanner(System.in);
                flashcards = new Flashcards(scanner.nextInt());
                return;
            } catch (InputMismatchException e) {
                System.out.println("Error: enter a number only.");
            }
        }
    }

    private void createCards() {
        for (int i = 1; i <= flashcards.getNumFlashcards(); i++) {
            System.out.printf("Card #%d:%n", i);
            Scanner scanner = new Scanner(System.in);
            String term = scanner.nextLine();
            System.out.printf("The definition for card #%d:%n", i);
            String definition = scanner.nextLine();
            Flashcard flashcard = new Flashcard(term, definition);
            flashcards.addFlashcard(flashcard);
        }
    }

    private void testTheUser() {
        for (Flashcard flashcard : flashcards.getFlashcards()) {
            System.out.printf("Print the definition of \"%s\":%n", flashcard.getTerm());
            Scanner scanner = new Scanner(System.in);
            System.out.printf(scanner.nextLine().equals(flashcard.getDefinition()) ? "Correct!%n" :
                    "Wrong. The right answer is \"%s\".%n", flashcard.getDefinition()) ;
        }
    }
}
