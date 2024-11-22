public interface BT <K extends Comparable<K>, T>{
 public boolean empty();
 public int size();
 public void Traverse();
 public void clear();
 public void TraverseT();
 public void update(T e);
 public boolean find(K key);
 public boolean insert(K key, T data) ;
 public boolean remove(K key);
 public T retrieve();
 public LinkedList <T> getData();
 public LinkedList <K> getKeys(); }