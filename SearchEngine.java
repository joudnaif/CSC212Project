/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212project;


import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/**
 *
 * @author joudnaif
 */
public class SearchEngine {
    LinkedList<String> stopWords;
    
    Index Index;
    Inverted_Index Inverted;//object of type inverted index- document
    Inverted_Index_AVL Inverted_AVL;
    Inverted_Index_BST Inverted_BST;
    
    int vocab = 0;
    int token = 0;
    Scanner scanner;
    
    public SearchEngine(){
        stopWords= new LinkedList<String>();
        Index = new Index();
        Inverted = new Inverted_Index();
        Inverted_BST = new Inverted_Index_BST();
        Inverted_AVL = new Inverted_Index_AVL();
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
                
                doc = doc.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                String tokens[] = doc.split("\\s+");
                for(String w :tokens){
                    if(!searchStopWords(w)){// add only if doesn't exsist in stop word list
                        Inverted.addWord(w, id);
                        Inverted_BST.addWord(w, id);
                        Inverted_AVL.addWord(w, id);
                        token++;// to calculate the number of tokens
                    }
                }
            }
            vocab = Inverted_AVL.size();
      
                System.out.println("tokens " + token);
                System.out.println("vocabs " + vocab);
                
                scanner.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
          
              public void LoadData (String stopFile, String fileName)
    {
            try{
                File stopfile = new File (stopFile);
                Scanner reader = new Scanner (stopfile).useDelimiter("\\Z");
                String stops = reader.next();
                
                stops = stops.replaceAll("\n", " ");
                
                File docsfile = new File(fileName);
                Scanner reader1 = new Scanner (docsfile);
                String line = reader1.nextLine();
                
                for ( int lineID = 0 ; lineID <50 ; lineID ++ ) 
                {
                    line = reader1.nextLine().toLowerCase();
                    
                    int pos = line.indexOf(',');
                    int docID = Integer.parseInt( line .substring(0, pos));

                    String data = line.substring(pos+1, line.length() - pos).trim();
                    data = data.substring(0, data.length() -1);

                    data = data.toLowerCase();
                    data =  data.replaceAll("[\']", " ");
                    data = data.replaceAll("[^a-zA-Z0-9]", " ").trim() ;

                    String [] words = data.split(" "); // --1

                    for (int i = 0; i < words.length ; i++)
                    {
                        String word = words[i].trim(); //--2
                
                        if ( word.compareToIgnoreCase("") != 0)
                            token ++;
                        
                        //this.test.addNew(docID, word);
                                
                        if ( ! stops.contains(word + " ")) //--3
                         {
                            this.Index.addDocument(docID, word);
                            this.Inverted.addWord(word,docID);
                            this.Inverted_AVL.addWord(word,docID);
                            this.Inverted_BST.addWord(word,docID);
                        }
                    }

                  
                }
                

                //vocab = test.size();
      
                System.out.println("tokens " + token);
                System.out.println("vocabs " + vocab);
                
                reader.close();
                reader1.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
    public LinkedList<Integer> Boolean_Retrieval(String str , int DSType)
    {
                LinkedList<Integer> docs = new LinkedList<Integer> ();
        switch (DSType)
        {
            case 1 :
                docs = Index.AND_OR_Query(str);
                break;
            case 2 :
                docs = Inverted.AND_OR_Query(str);
                break;
            case 3:
                docs = Inverted_BST.AND_OR_Query(str);
                break;
            case 4:
                docs = Inverted_AVL.AND_OR_Query(str);
                break;
            default :
                System.out.println("Invalid data structure");
                
        }
        return docs;
    
    }
    
       public void Ranked_Retrieval(String str , int DSType)
    {
         switch (DSType)
        {
            case 1 :
                Index.Ranking(str);
                break;
            case 2 :
                Inverted.Ranking(str);
                break;
            case 3:
                Inverted_BST.Ranking(str);
                break;
            case 4:
                Inverted_AVL.Ranking(str);
                break;
            default :
                System.out.println("Invalid data structure");
        }
    }
       
       public LinkedList<Integer> Term_Retrieval(String str , int DSType)
    {
        System.out.println(str + " " + DSType);
        LinkedList<Integer> docs = new LinkedList<Integer> ();
        switch (DSType)
        {
            case 1 :
            {
                boolean [] docs1 = Index.getDocs(str);
                for ( int i = 0 ; i < 50 ; i++)
                    if ( docs1[i] == true)
                        docs.insert(i);
            }
            break;
            case 2 :
                if (Inverted.search(str))
                {
                    boolean [] docs1 = Inverted.Inverted_Index.retrieve().getDocs();
                    for ( int i = 0 ; i < 50 ; i++)
                        if ( docs1[i] == true)
                            docs.insert(i);
                }
                break;
            case 3:
                if (Inverted_BST.search(str))
                    docs = Inverted_BST.Inverted_Index_BST.retrieve().getDocs();
                break;
            case 4:
                if (Inverted_AVL.search(str))
                    docs = Inverted_AVL.Inverted_Index_AVL.retrieve().getDocs();
                break;
            default :
                System.out.println("Bad data structure");
                
        }
        return docs;
    }
       
       public void Indexed_Documents()
    {
        System.out.println("All Documents with the number of words in them ");
        for ( int i = 0 ; i < 50 ; i++ )
        {
            int size = Index.indexes[i].index.size();
            System.out.println("Document# " + i +"  with size(" +  size + ")"  );
        }
        
    }
    
    public void Indexed_Tokens()
    {
        System.out.println("All tokens with the documents appear in it ");
        Inverted_BST.Inverted_Index_BST.Traverse();
    }
    
    }

