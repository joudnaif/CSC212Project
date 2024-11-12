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


public class Inverted_Index_BST {
    BST<Word> Inverted_Index_BST;

    public Inverted_Index_BST() {
        Inverted_Index_BST = new BST<Word>();
       
    }
    
    public void addWord(String text, int id){
        
        if(Inverted_Index_BST.findkey(text)){
            Inverted_Index_BST.retrieve().doc_IDs.insert(id);
        }else{
            Word word = new Word(text);// since it's doesn't exist, create a new one
            word.doc_IDs.insert(id);// insert the id to the id list
            Inverted_Index_BST.insert(text, word);// insert the word into the inverted index tree
        }}
    
    public boolean search(String word){
            return Inverted_Index_BST.findkey(word);
    }
    
    
    
    public void display(){
        if(Inverted_Index_BST.empty()){
            System.out.println("The Inverted Index is Empty.");
            return;
        }
        if(Inverted_Index_BST == null){
            System.out.println("The Inverted Index is Null.");
            return;
        }
        
        Inverted_Index_BST.inOrder();
    
    
}}
