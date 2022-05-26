package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Log {
    private final StringBuilder logEntries;

    public Log() {
        this.logEntries = new StringBuilder();
    }

    public void addLogEntry(String entry) {
        logEntries.append(entry).append("\n");
    }

    public void saveLog(String fileName) {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            printWriter.print(logEntries);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
