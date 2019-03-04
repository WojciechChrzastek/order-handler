import java.sql.SQLException;

public class Main {
  public static void main(String[] args) {
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

    String input = "1";
    String input2 = "2";
    String input3 = "3";
    String input4 = "4";
    String input5 = "5";
    ReportGenerator reportGenerator = new ReportGenerator();
    ReportHandler reportHandler = new ReportHandler();

    try {
      reportHandler.printReportToConsole(reportGenerator.generateReport(input));
      reportHandler.printReportToConsole(reportGenerator.generateReport(input2));
      reportHandler.printReportToConsole(reportGenerator.generateReport(input3));
      reportHandler.printReportToConsole(reportGenerator.generateReport(input4));
      reportHandler.printReportToConsole(reportGenerator.generateReport(input5));

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
