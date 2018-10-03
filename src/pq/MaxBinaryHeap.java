import java.util.*;

@SuppressWarnings("unchecked")

public class MaxBinaryHeap<T extends Comparable<T>> implements MaxPriorityQueue<T> {

  private T[] pq; // 1 .. N
  private int N;

  public MaxBinaryHeap() {
    pq = (T[]) new Object[2];
    N = 0;
  }

  private void resize(int capacity) {
    T[] temp = (T[]) new Object[capacity];
    for (int i = 1; i <= N; i++)
      temp[i] = pq[i];
    pq = temp;
  }

  private boolean less(int i, int j) {
    return pq[i].compareTo(pq[j]) < 0;
  }

  private void exchange(int i, int j) {
    T swap = pq[i];
    pq[i] = pq[j];
    pq[j] = swap;
  }

  private void swim(int k) {
    while(k > 1 && less(k / 2, k)) {
      exchange(k, k / 2);
      k = k / 2;
    }
  }

  private void sink(int k) {
    while (2 * k <= N) {
      int j = 2 * k;
      if (j < N && less(j, j+1)) j++;
      if (!less(k, j)) break;
      exchange(k, j);
      k = j;
    }
  }

  public void insert(T item) {

    // double size of array if necessary
    if (N == pq.length - 1) resize(2 * pq.length);

    // add item, and percolate it up to maintain heap invariant
    pq[++N] = item;
    swim(N);
  }

  public T removeMax() {
    if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
    T max = pq[1];
    exchange(1, N--);
    sink(1);
    pq[N+1] = null;     // to avoid loiteing and help with garbage collection
    if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
    return max;
   }

  public T peek() {
    if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
    return pq[1];
  }

  public String toString() { return "Not implemented"; }
  public boolean isEmpty() { return N == 0; }
  public int size() { return N; }
}
