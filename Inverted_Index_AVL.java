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
    
    AVLTree <String, TermAVL> Inverted_Index_AVL;
    class frequency
            {
                int docID = 0;
                int f = 0;
                String msg = "Document ";
            }
        frequency [] freqs;
        



        public Inverted_Index_AVL() {
            Inverted_Index_AVL = new AVLTree <String, TermAVL>();
            freqs = new frequency [50];    
        }

        public boolean addWord(String word, int id){

             if (Inverted_Index_AVL.empty())// the list of words is empty
               {
                   TermAVL t = new TermAVL ();
                    t.setWord(word);
                    t.add_docID(id);
                    Inverted_Index_AVL.insert(word, t);
                    return true;
               }
               else
               {
                if (Inverted_Index_AVL.find(word))
                {
                    TermAVL t = Inverted_Index_AVL.retrieve();
                    t.add_docID(id);
                    Inverted_Index_AVL.update(t);
                    return false;

                }

               TermAVL t = new TermAVL ();
               t.setWord(word);
               t.add_docID(id);
               Inverted_Index_AVL.insert(word, t);
                return true;
            }}

        public boolean search(String word){

             return Inverted_Index_AVL.find(word);

        }


        public LinkedList<Integer> AndQuery(String Query) {
        LinkedList<Integer> result = new LinkedList<>();


        String[] words = Query.split("AND");
        if (words.length == 0) return result;

        // Handle the first word
        if (search(words[0].trim().toLowerCase())) {
           result = Inverted_Index_AVL.retrieve().documentIDs_ranked.getKeys();
        }

        // Process remaining words
        for (int i = 0; i < words.length; i++) {

                LinkedList<Integer> b1 = result;
                if (search(words[i].trim().toLowerCase())) {
                    LinkedList<Integer> docs = Inverted_Index_AVL.retrieve().documentIDs_ranked.getKeys();
                    docs.findFirst();

                for ( int j = 0 ; j < docs.size ; j++)
                    {  
                        b1.findFirst();
                        boolean found =  false;
                        while ( ! b1.last())
                        {
                            if ( b1.retrieve()==docs.retrieve()) 
                            {
                                found = true;
                                break;
                            }
                            b1.findNext();
                        }
                        if ( b1.retrieve()== docs.retrieve()) 
                            found = true;

                        if (found)
                            result.insert(docs.retrieve());

                        docs.findNext();
                    }
                }
            }
            return result;
    }

        public LinkedList<Integer> OrQuery(String Query){
            LinkedList<Integer> result = new LinkedList<Integer> ();

            String words[] = Query.split("OR");
            if(words.length==0)
                return result;

             if (search(words[0].trim().toLowerCase()))
                {
                    result = Inverted_Index_AVL.retrieve().documentIDs_ranked.getKeys();
                }
                for ( int i = 1 ; i< words.length ; i++)
                {
                    if (search(words[i].trim().toLowerCase()))
                    {
                    LinkedList<Integer> docs = Inverted_Index_AVL.retrieve().documentIDs_ranked.getKeys();
                    docs.findFirst();    
                    for ( int j = 0 ; j < docs.size ; j++)
                        {  
                             result.findFirst();
                            boolean found =  false;
                            while (! result.last())
                            {
                                if ( result.retrieve()== docs.retrieve())
                                {
                                    found = true;
                                    break;
                                }
                                result.findNext();
                            }
                            if ( result.retrieve() == docs.retrieve())
                                found = true;

                            if (! found)
                                result.insert(j);

                            docs.findNext(); 
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
                    
                    Query = Query.toLowerCase().trim();

                    if (this.search (Query))
                        result = Inverted_Index_AVL.retrieve().documentIDs_ranked.getKeys();
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
                freqs[i] = new frequency() ;
                freqs[i].docID = i;
                freqs[i].f = 0;
                freqs[i].msg = "Document " + i + " : ";
            }
            
            for ( int i = 0 ; i < words.length ; i++)
            {
                if (Inverted_Index_AVL.find(words[i]))
                {
                    LinkedList<Integer> docs = Inverted_Index_AVL.retrieve().getDocs();
                    LinkedList<Integer> rank = Inverted_Index_AVL.retrieve().getRanked();
                    
                    docs.findFirst();
                    rank.findFirst();
                    for ( int j = 0 ; j < docs.size() ; j ++)
                    {
                        int index = docs.retrieve();
                        freqs[index].docID = index;
                        freqs[index].f += rank.retrieve();
                        freqs[index].msg +=" ( " + words[i] + ", " + rank.retrieve() + " ) +"; 
                        docs.findNext();
                        rank.findNext();
                    }
                }
            }
            
            for ( int x = 0 ; x < freqs.length ; x ++)
            {
                freqs[x].msg = freqs[x].msg.substring(0, freqs[x].msg.length()-1);
                freqs[x].msg += " = " + freqs[x].f;
            }
            
            mergesort(freqs, 0, freqs.length-1 );
            
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
                return Inverted_Index_AVL.size();
            }
        
        public void printDocument()
        {
            Inverted_Index_AVL.Traverse();
        }

}
