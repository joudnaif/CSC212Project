
public class Index {
    
    class Document {
            int docID;
            LinkedList <String> index; 

            public Document() {
                docID = 0;
                index = new LinkedList <String>();
            }

            public void addNew (String word)
            {
                index.insert(word);
            }

           public boolean found(String word)
           {
               if (index.empty())
                   return false;

               index.findFirst();
               for ( int i = 0 ; i < index.size ; i++)
               {
                   if ( index.retrieve().compareTo(word) == 0)
                       return true;
                  index.findNext();
               }
               return false;
           }
    }   
    
    Document [] indexes;
    
    
    public Index() {
        indexes = new Document [50];
        for ( int i = 0 ; i < indexes.length ; i++)
        {
            indexes [i] = new Document();
            indexes [i].docID = i;
        }
    }
        
    public void addDocument ( int docID, String data)
    {
            indexes[docID].addNew(data);
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
}