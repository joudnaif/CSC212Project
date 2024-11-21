import java.io.File;
import java.util.Scanner;

public class Search_Engine {
    int tokens = 0;
    int vocab = 0;
    
    Index index;
    Inverted_Index invertedindex;
    Inverted_Index_AVL invertedindexAVL;
    
   
    public Search_Engine ()
    {
        index = new Index();
        invertedindex = new Inverted_Index();
        invertedindexAVL = new Inverted_Index_AVL();
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
                    data = data.substring(0, data.length() -2);

                    data = data.toLowerCase();
                    data =  data.replaceAll("[\']", " ");
                    data = data.replaceAll("[^a-zA-Z0-9]", " ").trim() ;

                    String [] words = data.split(" "); 

                    for (int i = 0; i < words.length ; i++)
                    {
                        String word = words[i].trim(); 
                
                        if ( word.compareToIgnoreCase("") != 0)
                            tokens ++;

                        if ( ! stops.contains(word + " ")) 
                         {
                            { 
                                this.index.addDocument(docID, word);
                                this.invertedindex.addNew(docID, word);
                                this.invertedindexAVL.addNew(docID, word);
                            }
                        }
                    }

                }

                vocab = invertedindexAVL.invertedindexAVL.size();
      
                System.out.println("tokens " + tokens);
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
                docs = this.invertedindex.AND_OR_Function(str);
                break;
            case 2:
                docs = this.invertedindexAVL.AND_OR_Function(str);
                break;
            default :
                System.out.println("Bad data structure");
                
        }
        return docs;
    }
        
    public void Ranked_Retrieval(String str)
    {
        this.invertedindexAVL.TF(str);
    }
    

}