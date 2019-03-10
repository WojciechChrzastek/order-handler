import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

class UserInterface {
  private ParserCsv parserCsv = new ParserCsv();
  private ParserXml parserXml = new ParserXml();
  private String input;
  private Scanner scanner = new Scanner(System.in);
  private DbHandler dbHandler = DbHandler.getInstance();
  private ReportGenerator reportGenerator = new ReportGenerator();
  private ReportHandler reportHandler = new ReportHandler();

  void run() throws SQLException, IOException {
    System.out.println(SoutMessages.WELCOME_HEADER);
    dbHandler.createTable();
    showMainMenu();
  }

  private void showMainMenu() throws SQLException, IOException {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_ACTION_MAIN);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("q"));
    if (input.equals("1")) {
      showAddFileMenu();
    } else if (input.equals("2")) {
      showGenerateReportMenu();
    } else {
      System.out.println(SoutMessages.GOODBYE_FOOTER);
      System.exit(0);
    }
  }

  private void showAddFileMenu() throws SQLException, IOException {
    System.out.println();
    do {
      System.out.print(SoutMessages.ASK_FOR_FILE_PATH);
      input = scanner.nextLine();
      if (input.contains(".csv")) {
        parseAndAddToDatabase(parserCsv.parse(input));
      } else if (input.contains(".xml")) {
        parseAndAddToDatabase(parserXml.parse(input));
      } else if (input.equals("q")) {
        showMainMenu();
      } else {
        System.out.println(SoutMessages.FILE_NOT_FOUND);
        System.out.println();
      }
    } while (!input.contains(".csv") && !input.contains("xml") && !input.equals("q"));
  }

  private void parseAndAddToDatabase(List<Request> requestList) throws SQLException, IOException {
    System.out.println(SoutMessages.PARSE_SUCCESS);
    for (Request r : requestList) {
      dbHandler.insert(r);
    }
    System.out.println(SoutMessages.ADD_FILE_DATA_SUCCESS);
    showMainMenu();
  }

  private void showGenerateReportMenu() throws SQLException, IOException {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_REPORT_TYPE);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("q"));
    if (input.equals("1")) {
      showSoutReportMenu();
    } else if (input.equals("2")) {
      showCsvReportMenu();
    } else {
      showMainMenu();
    }
  }

  private void showSoutReportMenu() throws SQLException, IOException {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_SOUT_REPORT);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("3") &&
            !input.equals("4") && !input.equals("5") && !input.equals("6") &&
            !input.equals("7") && !input.equals("8") && !input.equals("q"));
    if (!input.equals("q")) {
      reportHandler.printReportToConsole(reportGenerator.generateReport(input));
      do {
        System.out.println();
        System.out.print(SoutMessages.ASK_FOR_ANOTHER_SOUT_REPORT);
        input = scanner.nextLine();
      } while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("q"));
      switch (input) {
        case "1":
          showSoutReportMenu();
          break;
        case "2":
          showGenerateReportMenu();
          break;
        case "3":
          showMainMenu();
          break;
        default:
          System.out.println(SoutMessages.GOODBYE_FOOTER);
          System.exit(0);
      }
    } else {
      showGenerateReportMenu();
    }
  }

  private void showCsvReportMenu() throws SQLException, IOException {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_CSV_REPORT);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("3") &&
            !input.equals("4") && !input.equals("5") && !input.equals("6") &&
            !input.equals("7") && !input.equals("8") && !input.equals("q"));
    if (!input.equals("q")) {
      reportHandler.saveReportToCsvFile(reportGenerator.generateReport(input), input);
      System.out.println(SoutMessages.CSV_REPORT_GENERATION_SUCCESS);
      do {
        System.out.println();
        System.out.print(SoutMessages.ASK_FOR_ANOTHER_CSV_REPORT);
        input = scanner.nextLine();
      } while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("q"));
      switch (input) {
        case "1":
          showCsvReportMenu();
          break;
        case "2":
          showGenerateReportMenu();
          break;
        case "3":
          showMainMenu();
          break;
        default:
          System.out.println(SoutMessages.GOODBYE_FOOTER);
          System.exit(0);
      }
    } else {
      showGenerateReportMenu();
    }
  }

  UserInterface() throws SQLException {
  }
}
