public class Main {
  public static void main(String[] args) {
    ParserCsv parserCsv = new ParserCsv();
    ParserXml parserXml = new ParserXml();

    parserCsv.parse();
    parserXml.parse();

    for (Request r : parserCsv.getRequestList())
      System.out.println(r);

    System.out.println();

    for (Request r : parserXml.getRequestList()) {
      System.out.println(r);
    }
  }
}
