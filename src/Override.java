public class Override {
  private int n;
  public Override(int n) { this.n = n; }

  // This definition of toString overrides
  // the one inherited from class Object.
  //
  public String toString() {
    return String.format("%d", this.n);
  }
  
  public static void main(String[] args) {
    Override o = new Override(343);
    System.out.format("%s%n", o.toString());
  }
}
