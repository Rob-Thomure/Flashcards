package com.company;

public class Flashcard {
    private final String term;
    private final String definition;

    public Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return String.format("Flashcard: Term = %s, Definition = %s%n", term, definition);
    }
}
