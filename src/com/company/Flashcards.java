package com.company;

import java.util.ArrayList;
import java.util.List;

public class Flashcards {
    private final int numFlashcards;
    private final List<Flashcard> flashcards;

    public Flashcards(int numFlashcards) {
        this.numFlashcards = numFlashcards;
        this.flashcards = new ArrayList<>();
    }

    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    public int getNumFlashcards() {
        return numFlashcards;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    @Override
    public String toString() {
        return "Flashcards{" +
                "numFlashcards=" + numFlashcards +
                ", flashcards=" + flashcards +
                '}';
    }
}
