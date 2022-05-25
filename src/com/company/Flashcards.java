package com.company;

import java.util.LinkedHashMap;
import java.util.Map;

public class Flashcards {
    private final Map<String, String> flashcards;

    public Flashcards() {
        this.flashcards = new LinkedHashMap<>();
    }

    public void addFlashcard(String term, String definition) {
        flashcards.put(term, definition);
    }

    public String removeFlashcard(String term) {
        return flashcards.remove(term);
    }

    public boolean isDuplicateTerm(String term) {
        return flashcards.containsKey(term);
    }

    public boolean isDuplicateDefinition(String definition) {
        return flashcards.containsValue(definition);
    }

    public int getFlashcardsCount() {
        return flashcards.size();
    }

    public Map<String, String> getFlashcards() {
        return flashcards;
    }
}
