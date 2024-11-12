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
public class Inverted_Index_AVL {
    AVL<Word> Inverted_Index_AVL;

    public Inverted_Index_AVL() {
        Inverted_Index_AVL = new AVL<>();
    }

    public void addWord(String text, int id) {
        Word word = new Word(text);

        if (Inverted_Index_AVL.findkey(word)) {
            Word existingWord = Inverted_Index_AVL.get(word); // Use the get method
            if (existingWord != null) {
                existingWord.doc_IDs.insert(id);
            }
        } else {
            word.doc_IDs.insert(id);
            Inverted_Index_AVL.insert(word);
        }
    }

    public boolean search(String word) {
        return Inverted_Index_AVL.findkey(new Word(word));
    }

    public void display() {
        if (Inverted_Index_AVL.empty()) {
            System.out.println("The Inverted Index is Empty.");
            return;
        }
        Inverted_Index_AVL.inOrder();
    }
}
