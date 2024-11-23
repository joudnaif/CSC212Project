
public class Index {
    class frequency
    {
        int docID = 0;
        int f = 0;
        String msg = "Document ";
    }

    class Document {
            int docID;
            LinkedList <String> index; 

            public Document() {
                docID = 0;
                index = new LinkedList <String>();
            }

            public void addWord (String word)
            {
                index.insert(word);
            }

           public boolean search(String word)
           {
               if (index.empty())
                   return false;

               index.findFirst();
               while ( ! index.last())
               {
                   if ( index.retrieve().compareToIgnoreCase(word) == 0)
                       return true;
                  index.findNext();
               }
                if ( index.retrieve().compareToIgnoreCase(word) == 0)
                    return true;
               return false;
           }
    }   
    
    Document [] indexes;
    frequency [] freqs;

    
    public Index() {
        freqs = new frequency [50];
        indexes = new Document [50];
        for ( int i = 0 ; i < indexes.length ; i++)
        {
            indexes [i] = new Document();
            indexes [i].docID = i;
        }
    }
        
    public void addDocument ( int docID, String data)
    {
            indexes[docID].addWord(data);
    }
    
    public void printDocment (int docID)
    {
        if ( indexes[docID].index.empty())
            System.out.println("Empty Document");
        else
        {
            indexes[docID].index.findFirst();
            for ( int i = 0; i< indexes[docID].index.size ; i++)
            {
                System.out.print (indexes[docID].index.retrieve() + " ");
                indexes[docID].index.findNext();
            }
        }
    }
public  boolean [] getDocs (String str)
{
    boolean [] result = new boolean [50];
    for (int i = 0 ; i < 50 ; i++)
        result[i] = false;
    
    for (int i = 0 ; i < 50 ; i++)
        if (indexes[i].search(str))
            result[i] = true;

    return result;
}

        public LinkedList<Integer> AND_OR_Query (String str )
        {
            if (! str.contains(" OR ") && ! str.contains(" AND "))
            {
                str = str.toLowerCase().trim();
                LinkedList<Integer> result = new LinkedList<Integer>();
                boolean [] docs = getDocs(str);
                for ( int i = 0 ; i < docs.length ; i++)
                    if (docs[i]) 
                        result.insert(i);
                return result;
            }
            
            else if (str.contains(" OR ") && str.contains(" AND "))
            {
                String [] AND_ORs = str.split(" OR ");
                LinkedList<Integer> result = AND_Function (AND_ORs[0]);
               
                for ( int i = 1 ; i < AND_ORs.length ; i++  )
                {   
                    LinkedList<Integer> r2 =AND_Function (AND_ORs[i]);

                    r2.findFirst();
                    for ( int j = 0 ; j < r2.size() ; j++)
                    {
                        boolean found = false;
                        result.findFirst();
                        while (! result.last())
                        {
                            if (result.retrieve().compareTo(r2.retrieve()) == 0 )
                                found = true;
                            result.findNext();
                        }
                        if (result.retrieve().compareTo(r2.retrieve()) == 0 )
                            found = true;

                        if (!found )
                            result.insert(r2.retrieve());

                        r2.findNext();
                    }
                }
                return result;
            }
            
            else  if (str.contains(" AND "))
                return ANDQuery (str);
            
            return ORQuery (str);
        }
        
        public LinkedList<Integer> ANDQuery (String str)
        {
            String [] ANDs = str.split(" AND ");
 
            LinkedList<Integer> result = new LinkedList<Integer>();
            boolean [] docs = getDocs(ANDs[0].toLowerCase().trim());
            for ( int i = 0 ; i < docs.length ; i++)
                if (docs[i]) 
                    result.insert(i);
                            
            for ( int i = 1 ; i< ANDs.length ; i++)
            {

                LinkedList<Integer> b1 = result;
                result = new LinkedList<Integer> ();

                docs = getDocs(ANDs[i].toLowerCase().trim());
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
            return result;
        }
        public LinkedList<Integer> ORQuery (String str)
        {
            String [] ORs = str.split(" OR ");

            LinkedList<Integer> result = new LinkedList<Integer> ();
            boolean [] docs = getDocs(ORs[0].toLowerCase().trim());
            for ( int i = 0 ; i < docs.length ; i++)
                if (docs[i]) 
                    result.insert(i);

            for ( int i = 1 ; i< ORs.length ; i++)
            {
                docs = getDocs(ORs[i].toLowerCase().trim());
                for ( int j = 0 ; j < 50 ; j++)
                {  
                    if (docs[j] )  {

                        result.findFirst();
                        boolean found =  false;

                        while (! result.last() )
                        {
                            if ( result.retrieve() == j)
                                found = true;
                            result.findNext();
                        }
                        if ( result.retrieve() == j)
                            found = true;

                        if (! found)
                            result.insert(j);
                    } 
                }
            }
            return result;
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
                freqs[i].f = 0;
                freqs[i].msg = "Document " + i + " : ";
            }
            
            for ( int docs = 0 ; docs <50 ; docs++)
            {
                for ( int i = 0 ; i < words.length ; i++)
                {
                    indexes[docs].index.findFirst();
                    int wordcount = 0;
                    for ( int x = 0 ; x < indexes[docs].index.size() ; x++ )
                    {
                        if (indexes[docs].index.retrieve().compareTo(words[i])==0)
                            wordcount ++;
                        indexes[docs].index.findNext();
                    }
                    freqs[docs].f += wordcount;
                    freqs[docs].msg +=" ( " + words[i] + ", " + wordcount + " ) +"; 
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
    
}
