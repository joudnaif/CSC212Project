public class Term {
    String word;
    boolean [] documentIDs;
    int [] ranked;

    public Term() {
        word = "";
        documentIDs = new boolean [50];
        ranked = new int [50];
        for (int i = 0 ; i < documentIDs.length ; i++)
        {
            documentIDs [i] = false;
            ranked[i] = 0;
        }
    }

    public Term(String word, int [] rank)
    {
        this.word = word;
        documentIDs = new boolean [50];
        ranked = new int [50];
        for (int i = 0 ; i < rank.length ; i++)
            if (rank[i] != 0)
            {
                documentIDs [i] = true;
                ranked[i] = rank[i];
            }
    }
    
    public void add_docID ( int docID)
    {
        documentIDs[docID] = true;
        ranked[docID] ++;
    }

    public void setWord(String word)
    {
        this. word = word; 
    }
    
    public String getWord()
    {
         return word;
    }
    
    public int [] getRanked()
    {
        int[] test = new int [ranked.length];
        for ( int i = 0 ; i < test.length ; i++)
            test[i] = ranked[i];
        return test;
    }

    
    public boolean [] getDocs ()
    {
        boolean [] test = new boolean [ranked.length];
        
        for ( int i = 0 ; i < test.length ; i++)
            test[i] = documentIDs[i];
        
        return test;
    }

      
    public String toString() {
        String docs = "";
        for (int i = 0, j = 0 ; i < documentIDs.length; i++)
            if ( documentIDs[i] != false)
            {
                if ( j == 0)
                {
                    docs += " " + String.valueOf(i) ;
                    j++;
                }
                else
                {
                    docs += ", " + String.valueOf(i) ;
                    j++;
                }
            }
        return word + "[" + docs + ']';
    }
    
}