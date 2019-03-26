import ch.vorburger.exec.ManagedProcessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

class UserService {
  private ParserCsv parserCsv = new ParserCsv();
  private ParserXml parserXml = new ParserXml();
  private String input;
  private Scanner scanner = new Scanner(System.in);
  private DbHandler dbHandler = DbHandler.getInstance();
  private InMemoryDbHandler inMemoryDbHandler = InMemoryDbHandler.getInstance();
  private ReportGenerator reportGenerator = new ReportGenerator();
  private ReportHandler reportHandler = new ReportHandler();
  private Connection conn;

  void run() throws Exception {
    System.out.println(SoutMessages.WELCOME_HEADER);
    showDatabaseSelector();
  }

  private void exitApplication() throws SQLException {
    System.out.println(SoutMessages.GOODBYE_FOOTER);
    dbHandler.deleteTable();
    inMemoryDbHandler.closeDb();
    System.exit(0);
  }

  private void showDatabaseSelector() throws Exception {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_DB);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("q"));
    if (input.equals("1")) {
      conn = dbHandler.getConnection();
      System.out.print(SoutMessages.LOCAL_DB);
      dbHandler.createTable();
      System.out.println(SoutMessages.SUCCESS_CREATE_TABLE);
      showMainMenu();
    } else if (input.equals("2")) {
      conn = inMemoryDbHandler.getConnection();
      System.out.print(SoutMessages.IN_MEMORY_DB);
      inMemoryDbHandler.createTable();
      System.out.println(SoutMessages.SUCCESS_CREATE_TABLE);
      showMainMenu();
    } else {
      exitApplication();
    }
  }

  private void showMainMenu() throws SQLException, IOException, ManagedProcessException {
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
      exitApplication();
    }
  }

  private void showAddFileMenu() throws SQLException, IOException, ManagedProcessException {
    System.out.println();
    do {
      System.out.print(SoutMessages.ASK_FOR_FILE_PATH);
      input = scanner.nextLine();
      if (input.contains(".csv")) {
        handleInput(parserCsv.parse(input));
      } else if (input.contains(".xml")) {
        handleInput(parserXml.parse(input));
      } else if (input.equals("q")) {
        showMainMenu();
      } else {
        System.out.println(SoutMessages.FILE_NOT_FOUND);
        System.out.println();
      }
    } while (!input.contains(".csv") && !input.contains(".xml") && !input.equals("q"));
  }

  private void handleInput(List requestsList) throws SQLException, IOException, ManagedProcessException {
    System.out.println(SoutMessages.PARSE_SUCCESS);
    if (conn == dbHandler.getConnection()) {
      dbHandler.addRequestsListToDatabase(requestsList);
    } else {
      inMemoryDbHandler.addRequestsListToDatabase(requestsList);
    }
    System.out.println(SoutMessages.ADD_FILE_DATA_SUCCESS);
    showMainMenu();
  }

  private void showGenerateReportMenu() throws SQLException, IOException, ManagedProcessException {
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

  private void selectReport() {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_REPORT);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("3") &&
            !input.equals("4") && !input.equals("5") && !input.equals("6") &&
            !input.equals("7") && !input.equals("8") && !input.equals("q"));
    //if input.equals(tam gdzie jest zapytanie o clientId)
    //->zapytaj o clientId
    // zapisz client id i przejdź dalej?
  }

  private void showSoutReportMenu() throws SQLException, IOException, ManagedProcessException {
    selectReport();
    if (!input.equals("q")) {
      reportHandler.printReportToConsole(reportGenerator.generateReport(input, conn));
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
          exitApplication();
      }
    } else {
      showGenerateReportMenu();
    }
  }

  private void showCsvReportMenu() throws SQLException, IOException, ManagedProcessException {
    selectReport();
    if (!input.equals("q")) {
      reportHandler.saveReportToCsvFile(reportGenerator.generateReport(input, conn), input);
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
          exitApplication();
      }
    } else {
      showGenerateReportMenu();
    }
  }

  UserService() throws SQLException, ManagedProcessException {
  }
}
