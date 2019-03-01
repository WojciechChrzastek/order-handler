public class Main {
  public static void main(String[] args) {
    ParserCsv parserCsv = new ParserCsv();
    ParserXml parserXml = new ParserXml();
    ParserOpenCsv parserOpenCsv = new ParserOpenCsv();

    parserCsv.parse("requests_test_file.csv");
    parserOpenCsv.parse("requests_test_file.csv");
    parserXml.parse("requests.xml");

    for (Request r : parserCsv.getRequestList())
      System.out.println(r);

    System.out.println();

    for (Request r : parserXml.getRequestList()) {
      System.out.println(r);
    }
    System.out.println();

    for (Request r : parserOpenCsv.getRequestList()) {
      System.out.println(r);
    }
  }
}
