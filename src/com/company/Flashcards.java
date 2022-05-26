package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Flashcards {
    private final Map<String, String> flashcards;
    private final Map<String, Integer> stats;

    public Flashcards() {
        this.flashcards = new LinkedHashMap<>();
        this.stats = new HashMap<>();
    }

    public void addFlashcard(String term, String definition) {
        flashcards.put(term, definition);
        stats.put(term, 0);
    }

    public String removeFlashcard(String term) {
        stats.remove(term);
        return flashcards.remove(term);
    }

    public boolean isDuplicateTerm(String term) {
        return flashcards.containsKey(term);
    }

    public boolean isDuplicateDefinition(String definition) {
        return flashcards.containsValue(definition);
    }

    public Map<String, String> getFlashcards() {
        return flashcards;
    }

    public int exportCards(String fileName) {
        try (PrintWriter printWriter = new PrintWriter(fileName)){
            for (var card : flashcards.entrySet()) {
                printWriter.printf("%s:==:%s:==:%d%n", card.getKey(), card.getValue(), stats.get(card.getKey()));
            }
            return flashcards.size();
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    public int importCards(String fileName) {
        int cardCount = 0;
        File file = new File(fileName);
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] nextLine = fileScanner.nextLine().split(":==:");
                flashcards.put(nextLine[0], nextLine[1]);
                stats.put(nextLine[0], Integer.parseInt(nextLine[2]));
                cardCount++;
            }
            return cardCount;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    public void addWrongAnswer(String term) {
        stats.put(term, stats.get(term) + 1);
    }

    public String[] getHardestCards() {
        int highestNum = 0;
        for (var entry : stats.entrySet()) {
            if (entry.getValue() > highestNum) {
                highestNum = entry.getValue();
            }
        }
        List<String> hardestCards = new ArrayList<>();
        hardestCards.add(String.valueOf(highestNum));
        if (highestNum > 0) {
            for (var entry : stats.entrySet()) {
                if (entry.getValue() == highestNum) {
                    hardestCards.add(entry.getKey());
                }
            }
        }
        return hardestCards.toArray(new String[0]);
    }

    public void restStats() {
        for (var entry : stats.entrySet()) {
            entry.setValue(0);
        }
    }

}
