public interface MaxPriorityQueue<T extends Comparable<T>> {

  void insert(T item);
  T removeMax();
  T peek();
  String toString();
  boolean isEmpty();
  int size();

}
