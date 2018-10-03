import java.util.*;

public class ForLoops {

  public static void main(String[] args) {

    Integer[] a = {2, 4, 6, 8};
    ArrayList<Integer> b = new ArrayList<Integer>();

    b.add(12);
    b.add(24);

    // for-loops work for arrays
    //
    for (int i : a)
      System.out.format("%d%n", i);

    // for-loops work for Collections
    //
    for (int i : b)
      System.out.format("%d%n", i);
  }
}
