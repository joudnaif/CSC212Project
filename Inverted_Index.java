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

public class Inverted_Index {
    LinkedList<Word> Inverted_Index;//list of words
    LinkedList<String> stopWords;
    Inverted_Index Inverted;//object of type inverted index
    
    Scanner scanner;

    public Inverted_Index() {
        Inverted_Index = new LinkedList<Word>();
        stopWords = new LinkedList<String>();
        Inverted = new Inverted_Index();
       
    }
    
    public void addWord(String text, int id){
        
        if(search(text)){
            Inverted_Index.retrieve().doc_IDs.insert(id);
        }else{
            Word word = new Word(text);// since it's doesn't exist, create a new one
            word.doc_IDs.insert(id);// insert the id to the id list
            Inverted_Index.insert(word);// insert the word into the inverted index list
        }}
    
    public boolean search(String word){
        
        if(Inverted_Index==null||Inverted_Index.empty())//wather the object doesn't exist or it doesn't have any elements
            return false;
        
            Inverted_Index.findFirst();
            while(!Inverted_Index.last()){
                if(Inverted_Index.retrieve().text.equals(word))
                    return true;
            }// again for the last node
            Inverted_Index.findFirst();
            if(Inverted_Index.retrieve().text.equals(word))
                    return true;
            return false;
        
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
                        Inverted.addWord(w, id);//add the words and 
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void display(){
        if(Inverted_Index.empty()){
            System.out.println("The Inverted Index is Empty.");
            return;
        }
        if(Inverted_Index == null){
            System.out.println("The Inverted Index is Null.");
            return;
        }
        
        Inverted_Index.findFirst();
        while(!Inverted_Index.last()){
            Word word = Inverted_Index.retrieve();
            System.out.println(word.text+" ");
            word.doc_IDs.findFirst();
            while(!word.doc_IDs.last()){//ممكن تحط كلاس display
                System.out.println(word.doc_IDs.retrieve()+" ");
                word.doc_IDs.findNext();
            }
            Inverted_Index.findNext();
        }
        Word word = Inverted_Index.retrieve();
            System.out.println(word.text+" ");
            word.doc_IDs.findFirst();
            while(!word.doc_IDs.last()){
                System.out.println(word.doc_IDs.retrieve()+" ");
                word.doc_IDs.findNext();
            }
            Inverted_Index.findNext();
    }
    
    
}
