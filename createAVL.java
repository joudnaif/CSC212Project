/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212project;

/**
 *
 * @author joudnaif
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class createAVL {
    LinkedList<String> stopWords;
    Inverted_Index_AVL Inverted_AVL;
    Scanner scanner;

    public createAVL() {
        stopWords = new LinkedList<>();
        Inverted_AVL = new Inverted_Index_AVL();
    }

    public boolean searchStopWords(String word) {
        if (stopWords == null || stopWords.empty()) {
            return false;
        }

        stopWords.findFirst();
        while (!stopWords.last()) {
            if (stopWords.retrieve().equals(word)) {
                return true;
            }
            stopWords.findNext();
        }

        stopWords.findFirst();
        if (stopWords.retrieve().equals(word)) {
            return true;
        }

        return false;
    }

    public void readStopWords(String filename) {
        try {
            File file = new File(filename);
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stopWords.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDocument(String filename) {
        try {
            File file = new File(filename);
            scanner = new Scanner(file);
            scanner.nextLine(); // skip the header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String idString = line.substring(0, line.indexOf(','));
                int id = Integer.parseInt(idString);
                String doc = line.substring(line.indexOf(',') + 1).trim();

                doc = doc.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                String tokens[] = doc.split("\\s+");

                for (String w : tokens) {
                    if (!searchStopWords(w)) {
                        Inverted_AVL.addWord(w, id);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

