import java.util.*;

public class Iterate {

  public static void main(String[] args) {

    Integer[] a = {2, 4, 6, 8};
    ArrayList<Integer> b = new ArrayList<Integer>();

    b.add(12);
    b.add(24);

    Iterator<Integer> iter = b.iterator();

    while (iter.hasNext()) {
      System.out.format("%d%n", iter.next());
    }
  }
}
