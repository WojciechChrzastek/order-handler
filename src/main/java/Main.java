import java.sql.SQLException;

public class Main {
  public static void main(String[] args) {
    ParserCsv parserCsv = new ParserCsv();
    parserCsv.parse("requests_test_file.csv");

    DbHandler dbHandler = null;
    try {
      dbHandler = DbHandler.getInstance();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (dbHandler != null) {
        dbHandler.createTable();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    for (Request r : parserCsv.getRequestList()) {
      try {
        if (dbHandler != null) {
          dbHandler.insert(r);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
