public class Main {
  public static void main(String[] args) {
    Parser parser = new Parser();

    parser.parse();

    for (Request request : parser.getRequestList())
      System.out.println(request);
  }
}
