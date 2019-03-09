import java.io.IOException;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws IOException, SQLException {
//    ParserCsv parserCsv = new ParserCsv();
//    parserCsv.parse("requests_test_file.csv");
//
//    DbHandler dbHandler = null;
//    try {
//      dbHandler = DbHandler.getInstance();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//
//    try {
//      if (dbHandler != null) {
//        dbHandler.createTable();
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//
//    for (Request r : parserCsv.getRequestsList()) {
//      try {
//        if (dbHandler != null) {
//          dbHandler.insert(r);
//        }
//      } catch (SQLException e) {
//        e.printStackTrace();
//      }
//    }
//
//    String input = "1";
//    String input2 = "2";
//    String input3 = "3";
//    String input4 = "4";
//    String input5 = "5";
//    String input6 = "6";
//    String input7 = "7";
//    String input8 = "8";
//    ReportGenerator reportGenerator = null;
//    try {
//      reportGenerator = new ReportGenerator();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    ReportHandler reportHandler = new ReportHandler();
//
//    try {
//      reportHandler.printReportToConsole(reportGenerator.generateReport(input));
//      reportHandler.printReportToConsole(reportGenerator.generateReport(input2));
//      reportHandler.printReportToConsole(reportGenerator.generateReport(input3));
//      reportHandler.printReportToConsole(reportGenerator.generateReport(input4));
//      reportHandler.printReportToConsole(reportGenerator.generateReport(input5));
//      reportHandler.printReportToConsole(reportGenerator.generateReport(input6));
//      reportHandler.printReportToConsole(reportGenerator.generateReport(input7));
//      reportHandler.printReportToConsole(reportGenerator.generateReport(input8));
//
//
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

//    String input = "4";
//
//    ReportGenerator reportGenerator = new ReportGenerator();
//    ReportHandler reportHandler = new ReportHandler();
//    reportHandler.printReportToConsole(reportGenerator.generateReport(input));
//    reportHandler.saveReportToCsvFile(reportGenerator.generateReport(input), input);

    UserInterface userInterface = new UserInterface();
    userInterface.run();
  }
}
