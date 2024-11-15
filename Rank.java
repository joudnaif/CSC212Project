public class Rank {
    Vocabulary word;
    int rank ;

    public Rank() {
        rank = 0;
        word = new Vocabulary("");
    }

    public Rank(String word, int rank) {
        this.word = new Vocabulary(word);
        this.rank = rank ;
    }
    
    public int add_Rank()
    {
        return ++rank;
    }

    public void setVocabulary(Vocabulary word)
    {
        this. word= word; 
    }
    
    
    public int getRank()
    {
        return this.rank;
    }
    
    public Vocabulary getVocabulary()
    {
         return word;
    }
    
    public String toString() {
        return "(" + word + ", " + rank + ")" ;
    }
    
    
}