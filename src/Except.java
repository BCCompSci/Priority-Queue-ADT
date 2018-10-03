import java.io.IOException;
import java.util.*;

 class Except {

  static int h(int x) throws IOException {
    System.out.format("Entered h%n");
    int answer = x * 2;
    if (true) throw new IOException();
    System.out.format("Exiting h%n");
    return answer;
  }

  static int g(int x)  {
    int answer = 33;
    System.out.format("Entered g%n");
    try {
    answer = h(x * 2);
    }
    catch (Exception e) {
      System.out.format("In g, caught any exception!");
    }
    /* catch (IOException e) {
      System.out.format("in g caught IO Exn");
    }
    */
    System.out.format("Exiting g%n");
    return answer;
  }

  static int f(int x) {
    int answer = 3;
    System.out.format("Entered f%n");
    try {
    answer = g(x * 2);
    }
    catch (RuntimeException e) {
      System.out.format("In f caught exception!");
    }
    System.out.format("Exiting f%n");
    return answer;
  }

  public static void main(String[] args) {
    System.out.format("%d%n", f(2));
  }
}
