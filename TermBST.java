public class TermBST {
   String word;
 
BST <Integer, Integer> documentIDs_ranked;
    
public TermBST() {
    word = "";
    documentIDs_ranked= new BST <Integer, Integer> (); }

public TermBST(String word){
    this.word = word;
    documentIDs_ranked= new BST <Integer, Integer> ();}
    
public void add_docID (int docID){
    if (documentIDs_ranked.empty())
        documentIDs_ranked.insert(docID, 1);
    else{
    if (documentIDs_ranked.find(docID)){
        int ranked = documentIDs_ranked.retrieve();
            ranked++;
            documentIDs_ranked.update(ranked); }   
    else
            documentIDs_ranked.insert(docID, 1); }
 }

public void setWord(String word) {
       this.word = word; }
    
public String getWord() {
        return word; }
    
public LinkedList<Integer> getDocs() {
        return documentIDs_ranked.getKeys(); }

public LinkedList<Integer> getRanked() {
       return this.documentIDs_ranked.getData(); }
    
public String toString() {
       String docs = "";
       LinkedList<Integer> IDs = getDocs();
       IDs.findFirst();
    for (int i = 0, j = 0 ; i < IDs.size(); i++) {
        if (i == 0)
         docs += " " + String.valueOf(i) ;
        else
         docs += ", " + String.valueOf(i) ;
         IDs.findNext(); }
         return word + "[" + docs + ']'; }
}