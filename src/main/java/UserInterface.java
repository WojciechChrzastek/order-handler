import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

class UserInterface {
  private ParserCsv parserCsv = new ParserCsv();
  private ParserXml parserXml = new ParserXml();
  private String input;
  private Scanner scanner = new Scanner(System.in);
  private DbHandler dbHandler = DbHandler.getInstance();

  void run() throws SQLException {
    System.out.println(SoutMessages.WELCOME_HEADER);
    dbHandler.createTable();
    mainMenu();
  }

  private void mainMenu() throws SQLException {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_ACTION_MAIN);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("q"));
    if (input.equals("1")) {
      addFileMenu();
    } else if (input.equals("2")) {
//      generateReportMenu();
    } else {
      System.exit(0);
      System.out.println(SoutMessages.GOODBYE_FOOTER);
    }
  }

  private void addFileMenu() throws SQLException {
    System.out.println();
    do {
      System.out.print(SoutMessages.ASK_FOR_FILE_PATH);
      input = scanner.nextLine();
      if (input.contains(".csv")) {
        parseAndAddToDatabase(parserCsv.parse(input));
      } else if (input.contains(".xml")) {
        parseAndAddToDatabase(parserXml.parse(input));
      } else if (input.contains("q")) {
        mainMenu();
      } else {
        System.out.println(SoutMessages.FILE_NOT_FOUND);
        System.out.println();
      }
    } while (!input.contains(".csv") || !input.contains("xml") || !input.contains("q"));
  }

  private void parseAndAddToDatabase(List<Request> requestList) throws SQLException {
    System.out.println(SoutMessages.PARSE_SUCCESS);
    for (Request r : requestList) {
      dbHandler.insert(r);
    }
    System.out.println(SoutMessages.ADD_FILE_DATA_SUCCESS);
    mainMenu();
  }

  UserInterface() throws SQLException {
  }
}
