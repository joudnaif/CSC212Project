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
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Inverted_Index_BST {
    BST<Word> Inverted_Index_BST;
    LinkedList<String> stopWords;
    Inverted_Index_BST Inverted_BST;
    
    Scanner scanner;

    public Inverted_Index_BST() {
        Inverted_Index_BST = new BST<Word>();
        stopWords = new LinkedList<String>();
        Inverted_BST = new Inverted_Index_BST();
       
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
    
    public boolean searchStopWords(String word){
        
        if(stopWords==null||stopWords.empty())//wather the object doesn't exist or it doesn't have any elements
            return false;
        
            stopWords.findFirst();
            while(!stopWords.last()){
                if(stopWords.retrieve().equals(word))
                    return true;
            }// again for the last node
            stopWords.findFirst();
            if(stopWords.retrieve().equals(word))
                    return true;
            return false;
        
    }
    
    public void readStopWords(String filename){
        try{
            File f = new File(filename);
            scanner = new Scanner(f);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                stopWords.insert(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void readDocument(String filename){
        try{
            File f = new File(filename);
            scanner = new Scanner(f);
            scanner.nextLine(); //skip the header
            
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String idString = line.substring(0, line.indexOf(','));
                int id = Integer.parseInt(idString);
                String doc = line.substring(line.indexOf(',')+1).trim();
                
                doc = doc.toLowerCase().replaceAll("[^a-zA-Z0-9", "");
                String tokens[] = doc.split("\\s+");
                for(String w :tokens){
                    if(!searchStopWords(w)){// add only if doesn't exsist in stop word list
                        Inverted_BST.addWord(w, id);//add the word and its id 
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
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
