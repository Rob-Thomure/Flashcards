package com.company;

public class Flashcard {
    private final String Term;
    private final String definition;

    public Flashcard(String term, String definition) {
        Term = term;
        this.definition = definition;
    }

    public String getTerm() {
        return Term;
    }

    public String getDefinition() {
        return definition;
    }
}
