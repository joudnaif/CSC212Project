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
    
    class frequency
        {
            int docID = 0;
            int f = 0;
            String msg = "Document ";
        }
    frequency [] freqs;
    LinkedList<Term> Inverted_Index;//list of words
    
    

    public Inverted_Index() {
        Inverted_Index = new LinkedList<Term>();
        freqs = new frequency [50];    
    }
    
    public boolean addWord(String word, int id){
        
         if (Inverted_Index.empty())// the list of words is empty
           {
                Term t = new Term ();
                t.setWord(word);
                t.add_docID(id);
                Inverted_Index.insert(t);
                return true;
           }
           else
           {
                Inverted_Index.findFirst();
               while ( ! Inverted_Index.last())
                {
                    if ( Inverted_Index.retrieve().word.compareTo(word) == 0)
                    {
                        Term t = Inverted_Index.retrieve();
                        t.add_docID(id);
                        Inverted_Index.update(t);
                        return false;    
                    }
                   Inverted_Index.findNext();
                }
                if ( Inverted_Index.retrieve().word.compareTo(word) == 0)// for the last node
                {
                    Term t = Inverted_Index.retrieve();
                    t.add_docID(id);
                    Inverted_Index.update(t);
                    return false;    
                }
                Term t = new Term ();
                t.setWord(word);
                t.add_docID(id);
                Inverted_Index.insert(t);
            }
            return true;}
    
    public boolean search(String word){
        
          if (Inverted_Index.empty())
                   return false;

               Inverted_Index.findFirst();
               for ( int i = 0 ; i < Inverted_Index.size ; i++)
               {
                   if ( Inverted_Index.retrieve().word.compareTo(word) == 0)
                       return true;
                  Inverted_Index.findNext();
               }
               return false;
        
    }
    
    
    public LinkedList<Integer> AndQuery(String Query) {
    String [] ANDs = Query.split(" AND ");
 
            LinkedList<Integer> result = new LinkedList<Integer>();
            if (this.search (ANDs[0].toLowerCase().trim()))
            {
                boolean [] docs = Inverted_Index.retrieve().getDocs();
                for ( int i = 0 ; i < docs.length ; i++)
                    if (docs[i]) 
                        result.insert(i);
            }
            
            for ( int i = 1 ; i< ANDs.length ; i++)
            {
                LinkedList<Integer> b1 = result;
                result = new LinkedList<Integer> ();

                if (this.search (ANDs[i].toLowerCase().trim()))
                {
                    boolean [] docs = Inverted_Index.retrieve().getDocs();
                    for ( int j = 0 ; j < docs.length ; j++)
                    {  
                        if (docs[j] )  {
                            b1.findFirst();
                            boolean found =  false;
                            while ( ! b1.last())
                            {
                                if ( b1.retrieve()==j) 
                                {
                                    found = true;
                                    break;
                                }
                                b1.findNext();
                            }
                            if ( b1.retrieve()== j) 
                                found = true;
                            
                            
                            if (found)
                                result.insert(j);
                        }
                    }
                }
            }
            return result;
}
    
    public LinkedList<Integer> OrQuery(String Query){
        String [] ORs = Query.split(" OR ");

            LinkedList<Integer> result = new LinkedList<Integer> ();
            if (this.search (ORs[0].toLowerCase().trim()))
            {
                boolean [] docs = Inverted_Index.retrieve().getDocs();
                for ( int i = 0 ; i < docs.length ; i++)
                    if (docs[i]) 
                        result.insert(i);
            }
            for ( int i = 1 ; i< ORs.length ; i++)
            {
                if (this.search (ORs[i].toLowerCase().trim()))
                {
                    boolean [] docs = Inverted_Index.retrieve().getDocs();
                    for ( int j = 0 ; j < docs.length ; j++)
                    {  
                        if (docs[j] )  {
                            
                            result.findFirst();
                            boolean found =  false;
                            
                            while (! result.last() )
                            {
                                if ( result.retrieve() == j)
                                {
                                    found = true;
                                    break;
                                }
                                result.findNext();
                            }
                            if ( result.retrieve() == j)
                            {
                                found = true;
                                break;
                            }
                            
                            if (! found)
                                result.insert(j);
                        } 
                    }
                }
            }
            return result;

    }
    
     public LinkedList<Integer> AND_OR_Query(String Query){
        
        
        if(Query.length()==0)
            return null;
        
        if (! Query.contains(" OR ") && ! Query.contains(" AND "))
            {
                LinkedList<Integer> result = new LinkedList<Integer>();
                int [] r1 = new int[50];
                Query = Query.toLowerCase().trim();
                
                if (this.search (Query))
                {
                    boolean [] docs = Inverted_Index.retrieve().getDocs();
                    for ( int i = 0 ; i < docs.length ; i++)
                        if (docs[i]) 
                            result.insert(i);
                }
                return result;
            }
        else if (Query.contains(" OR ") && Query.contains(" AND "))
            {
                String orQuery[] = Query.split("OR");
                LinkedList<Integer> result = AndQuery (orQuery[0]);
               
                for ( int i = 1 ; i < orQuery.length ; i++  )
                {   
                    LinkedList<Integer> result2 =AndQuery (orQuery[i]);

                    result2.findFirst();
                    for ( int j = 0 ; j < result2.size() ; j++)
                    {
                        boolean found = false;
                        result.findFirst();
                        while (! result.last())
                        {
                            if (result.retrieve().compareTo(result2.retrieve()) == 0 )
                            {
                                found = true;
                                break;
                            }
                            result.findNext();
                        }
                        if (result.retrieve().compareTo(result2.retrieve()) == 0 )
                        {
                            found = true;
                            break;
                        }

                        if (!found )
                            result.insert(result2.retrieve());

                        result2.findNext();
                    }
                }
                return result;
            }
        
        else  if (Query.contains(" AND "))
                return AndQuery(Query);
            
            return OrQuery(Query);
       
     }

    public void Ranking(String str)
        {
            str = str.toLowerCase().trim();
            String [] words = str.split(" ");
            freqs = new frequency[50];
            for ( int i = 0 ; i < 50 ; i++ )
            {
                freqs[i] = new frequency();
                freqs[i].docID = i;
                freqs[i].f = 0;//rank =0
                freqs[i].msg = "Document " + i + " : ";
            }
            
            for ( int i = 0 ; i < words.length ; i++)
            {
                if (search(words[i]))
                {
                    boolean [] docs = Inverted_Index.retrieve().getDocs();
                    int [] rank = Inverted_Index.retrieve().getRanked();
                    
                    for ( int j = 0 ; j < docs.length ; j ++)
                    {
                        if (docs[j] == true)// if doc has a rank
                        {
                            int index = j;
                            freqs[index].docID = index;
                            freqs[index].f += rank[j];// add up the rank
                            freqs[index].msg +=" ( " + words[i] + ", " + rank[j] + " ) +"; 
                        }
                    }
                }
            }
            
            for ( int x = 0 ; x < freqs.length ; x ++)
            {
                freqs[x].msg = freqs[x].msg.substring(0, freqs[x].msg.length()-1);
                freqs[x].msg += " = " + freqs[x].f;
            }
            
            mergesort(freqs, 0, freqs.length-1 );// sort the list based on frequncy
            
            System.out.println("Results: ");
            
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)
                System.out.println(freqs[x].msg);
            
            System.out.println("\nDocIDt\tScore");
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
        }
    
     public static void mergesort ( frequency [] A , int l , int r ) 
    {
        if ( l >= r )
            return;
        int m = ( l + r ) / 2;
        mergesort (A , l , m ) ; 
        mergesort (A , m + 1 , r ) ; 
        merge (A , l , m , r ) ;
    }

    private static void merge ( frequency [] A , int l , int m , int r ) 
    {
        frequency [] B = new frequency [ r - l + 1];
        int i = l , j = m + 1 , k = 0;
    
        while ( i <= m && j <= r )
        {
            if ( A [ i ].f >= A [ j ].f)
                B [ k ++] = A [ i ++];
            else
                B [ k ++] = A [ j ++];
        }
        
        if ( i > m )
            while ( j <= r )
                B [ k ++] = A [ j ++];
        else
            while ( i <= m )
                B [ k ++] = A [ i ++];
        
        for ( k = 0; k < B . length ; k ++)
            A [ k + l ] = B [ k ];
    }

    
    public int size()
        {
            return Inverted_Index.size();
        }
    
    public void display() {
        if (this.Inverted_Index.empty())
                System.out.println("Empty Inverted Index");
        else
            {
                this.Inverted_Index.findFirst();
                while ( ! this.Inverted_Index.last())
                {
                    System.out.println(Inverted_Index.retrieve());
                    this.Inverted_Index.findNext();
                }
                System.out.println(Inverted_Index.retrieve());
            }
    }

    
    
}
