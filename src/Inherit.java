public class Inherit {
  private int n;
  public Inherit(int n) { this.n = n; }
  public static void main(String[] args) {
    Inherit i = new Inherit(343);
    System.out.format("%s%n", i.toString());
    System.out.format("%s%n", i);
    System.out.format("%s%n", new Inherit(343).toString());
  }
}
