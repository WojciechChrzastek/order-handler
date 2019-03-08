import java.util.Scanner;

class UserInterface {
  private ParserCsv parserCsv = new ParserCsv();
  private ParserXml parserXml = new ParserXml();
  private String input;
  private Scanner scanner = new Scanner(System.in);

  void run() {
    System.out.println(SoutMessages.WELCOME_HEADER);
    mainMenu();
  }

  private void mainMenu() {
    do {
      System.out.print(SoutMessages.SELECT_ACTION);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("q"));
    if (input.equals("1")) {
      addFileMenu();
    } else if (input.equals("2")) {
      System.out.println("nice2");
    } else {
      System.exit(0);
      System.out.println(SoutMessages.GOODBYE_FOOTER);
    }
  }

  private void addFileMenu() {
    String filePath;
    do {
      System.out.print(SoutMessages.ASK_FOR_FILE_PATH);
      filePath = scanner.nextLine();
      if (filePath.contains(".csv")) {
        parserCsv.parse(filePath);
        System.out.println(SoutMessages.PARSE_SUCCESS);
      } else if (filePath.contains(".xml")) {
        parserXml.parse(filePath);
        System.out.println(SoutMessages.PARSE_SUCCESS);
      } else {
        System.out.println(SoutMessages.FILE_NOT_FOUND);
      }
    } while (!filePath.contains(".csv") || !filePath.contains("xml"));
  }

}
