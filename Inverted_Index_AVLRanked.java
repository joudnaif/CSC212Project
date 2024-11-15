import java.util.function.Function;

public class Inverted_Index_AVLRanked {
            class frequency
            {
                int docID = 0;
                int f = 0;
            }
    
           AVLTree <Integer, AVLTree <String,Rank>> AVLrank; 
           frequency [] freqs = new frequency[50];
            
            
            
            public Inverted_Index_AVLRanked() {
                AVLrank = new AVLTree <Integer, AVLTree <String,Rank>>();
                
            }

            public boolean addNew (int docID, String word)
            {
               if (AVLrank.empty())
               {
                   AVLTree <String,Rank> miniRank= new AVLTree <String,Rank>();
                   miniRank.insert(word, new Rank (word,1));
                   
                   AVLrank.insert(docID, miniRank);
                   return true;
               }
               else
               {
                    if (AVLrank.find(docID))
                    {
                        AVLTree <String,Rank> miniRank= AVLrank.retrieve();
                        if (miniRank.find(word))
                        {
                            // document available , word avialble // rank ++
                            Rank rank = miniRank.retrieve();
                            rank.add_Rank();
                            miniRank.update(rank);
                            AVLrank.update(miniRank);
                            return false;
                        }
                        //  document available , word unavailable 
                        miniRank.insert(word, new Rank (word , 1));
                        AVLrank.update(miniRank);
                        return true;
                    }
                    // document unavailable 
                   AVLTree <String,Rank> miniRank= new AVLTree <String,Rank>();
                   miniRank.insert(word, new Rank (word,1));
                   
                   AVLrank.insert(docID, miniRank);
                   return true;
               }
        }

        public boolean found(int docID, String word)
        {
               if (AVLrank.find(docID) )
                  if (AVLrank.retrieve().find(word))
                      return true;
               return false;
        }
        
        public int getrank (int docID, String word)
        {
            int value = 0;
               if (AVLrank.find(docID) )
                  if (AVLrank.retrieve().find(word))
                      return AVLrank.retrieve().retrieve().getRank();
               return value;
            
        }
        public void printDocument()
        {
                AVLrank.TraverseT();
        }

        //=================================================================
        public void TF(String str)
        {
            str = str.toLowerCase().trim();
            String [] words = str.split(" ");
            
            int index = 0;
            for ( int docID = 0 ; docID < 50 ; docID++ )
            {
                int count = 0 ;
                for ( int j = 0 ;j < words.length ; j++ )
                    count += this.getrank(docID, words[j]);
                if (count > 0)
                {
                    freqs[index] = new frequency();
                    freqs[index].docID = docID;
                    freqs[index].f = count;
                    index ++;
                }
            }
            
            mergesort(freqs, 0, index-1 );
                
            for ( int x = 0 ; x < index ; x++)
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
        }

         //=================================================================
    public static void mergesort ( frequency [] A , int l , int r ) 
    {
        if ( l >= r )
            return;
        int m = ( l + r ) / 2;
        mergesort (A , l , m ) ;          // Sort first half
        mergesort (A , m + 1 , r ) ;    // Sort second half
        merge (A , l , m , r ) ;            // Merge
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