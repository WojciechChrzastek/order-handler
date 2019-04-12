import ch.vorburger.exec.ManagedProcessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class UserService {
  private String input;
  private Scanner scanner = new Scanner(System.in);
  private Scanner scannerInt = new Scanner(System.in);
  private DbHandler dbHandler = DbHandler.getInstance();
  private ReportGenerator reportGenerator = new ReportGenerator();
  private ReportHandler reportHandler = new ReportHandler();
  private Connection conn;
  private int clientId;
  private String[] inputAnotherReport = {"1", "2", "3", "q"};
  private String[] input12q = {"1", "2", "q"};

  void run() throws Exception {
    System.out.println(SoutMessages.WELCOME_HEADER);
    showDatabaseSelector();
  }

  private void showDatabaseSelector() throws Exception {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_DB);
      input = scanner.nextLine();
    } while (!input.equals("1") && !input.equals("2") && !input.equals("q"));
    if (input.equals("1")) {
      System.out.print(SoutMessages.LOCAL_DB);
      conn = dbHandler.getConnection();
      dbHandler.createTable();
      System.out.println(SoutMessages.SUCCESS_CREATE_TABLE);
      showMainMenu();
    } else if (input.equals("2")) {
      System.out.print(SoutMessages.IN_MEMORY_DB);
      InMemoryDbHandler inMemoryDbHandler = InMemoryDbHandler.getInstance();
      conn = inMemoryDbHandler.getConnection();
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
    } while (!Arrays.asList(input12q).contains(input));
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
    String[] validInput = {".csv", ".xml", "q"};
    do {
      System.out.print(SoutMessages.ASK_FOR_FILE_PATH);
      input = scanner.nextLine();
      if (input.contains(".csv")) {
        handleInput(new ParserCsv());
      } else if (input.contains(".xml")) {
        handleInput(new ParserXml());
      } else if (input.equals("q")) {
        showMainMenu();
      } else {
        System.out.println(SoutMessages.FILE_NOT_FOUND);
        System.out.println();
      }
    } while (!Arrays.asList(validInput).contains(input));
  }

  private void handleInput(Parser parser) throws SQLException, IOException, ManagedProcessException {
    List requestList = parser.parse(input);
    if (requestList.size() != 0) {
      if (conn == dbHandler.getConnection()) {
        dbHandler.addRequestsListToDatabase(requestList);
      } else {
        InMemoryDbHandler inMemoryDbHandler = InMemoryDbHandler.getInstance();
        inMemoryDbHandler.addRequestsListToDatabase(requestList);
      }
      System.out.println(SoutMessages.ADD_FILE_DATA_SUCCESS);
      showMainMenu();
    } else {
      System.out.println(SoutMessages.ADD_FILE_DATA_FAIL);
      showAddFileMenu();
    }
  }

  private void showGenerateReportMenu() throws SQLException, IOException, ManagedProcessException {
    System.out.println();
    do {
      System.out.print(SoutMessages.SELECT_REPORT_TYPE);
      input = scanner.nextLine();
    } while (!Arrays.asList(input12q).contains(input));
    if (input.equals("1")) {
      showSoutReportMenu();
    } else if (input.equals("2")) {
      showCsvReportMenu();
    } else {
      System.out.println("\n" + SoutMessages.WRONG_INPUT);
      showMainMenu();
    }
  }

  private void selectReport() {
    System.out.println();
    String[] validInput = {"1", "2", "3", "4", "5", "6", "5", "7", "8", "q"};
    String[] reportsThatNeedClientId = {"2", "4", "6", "8"};
    do {
      System.out.print(SoutMessages.SELECT_REPORT);
      input = scanner.nextLine();
    } while (!Arrays.asList(validInput).contains(input));
    if (Arrays.asList(reportsThatNeedClientId).contains(input)) {
      System.out.print("\n" + SoutMessages.GIVE_CLIENT_ID);
      while (!scannerInt.hasNextInt()) {
        System.out.print(SoutMessages.WRONG_INPUT + " " + SoutMessages.GIVE_CLIENT_ID);
        scannerInt.next();
      }
      clientId = scannerInt.nextInt();
      //// MUSI BYC TAKI ID W BAZIE^^^^^^^^^^^^^^^^^^
    }
  }

  private void showSoutReportMenu() throws SQLException, IOException, ManagedProcessException {
    selectReport();
    if (!input.equals("q")) {
      reportHandler.printReportToConsole(reportGenerator.generateReport(input, conn, clientId));
      do {
        System.out.println();
        System.out.print(SoutMessages.ASK_FOR_ANOTHER_SOUT_REPORT);
        input = scanner.nextLine();
      } while (!Arrays.asList(inputAnotherReport).contains(input));
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
      reportHandler.saveReportToCsvFile(reportGenerator.generateReport(input, conn, clientId), input);
      System.out.println(SoutMessages.CSV_REPORT_GENERATION_SUCCESS);

      do {
        System.out.println();
        System.out.print(SoutMessages.ASK_FOR_ANOTHER_CSV_REPORT);
        input = scanner.nextLine();
      } while (!Arrays.asList(inputAnotherReport).contains(input));
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

  private void exitApplication() throws SQLException, ManagedProcessException {
    System.out.println(SoutMessages.GOODBYE_FOOTER);
    if (conn == dbHandler.getConnection()) {
      dbHandler.deleteTable();
    } else {
      InMemoryDbHandler inMemoryDbHandler = InMemoryDbHandler.getInstance();
      inMemoryDbHandler.closeDb();
    }
    System.exit(0);
  }

  UserService() throws SQLException {
  }
}
