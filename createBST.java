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

public class createBST {
    
    LinkedList<String> stopWords;
    Inverted_Index_BST Inverted_BST;
    
    Scanner scanner;
    public createBST(){
        stopWords= new LinkedList<String>();
        Inverted_BST = new Inverted_Index_BST();
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

    
}}
