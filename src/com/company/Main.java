package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Flashcard flashcard = new Flashcard(scanner.nextLine(), scanner.nextLine());
        System.out.println(scanner.nextLine().equals(flashcard.getDefinition()) ?
                "Your answer is right" : "Your answer is wrong");
    }
}
