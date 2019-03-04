import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class ReportGenerator {
  private Scanner scanner = new Scanner(System.in);

  ResultSet generateReport(String input) throws SQLException {
    ResultSet rs = null;

    switch (input) {
      case "1": {
        rs = returnTotalOrdersNumber();
        break;
      }
      case "2": {
        rs = returnTotalOrdersNumberOfGivenClientId();
        break;
      }
      case "3": {
        rs = returnTotalValueOfOrders();
        break;
      }
      case "4": {
        rs = returnTotalValueOfOrdersOfGivenClientId();
        break;
      }
      case "5": {
        rs = returnListOfAllOrders();
        break;
      }
    }
    return rs;
  }

  private ResultSet returnTotalOrdersNumber() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();

    String sqlQuery = "SELECT COUNT(*) AS TOTAL_ORDERS_NUMBER FROM REQUESTS";
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();
    return rs;
  }

  private ResultSet returnTotalOrdersNumberOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    int clientId = scanner.nextInt();

    DbHandler dbHandler = DbHandler.getInstance();
    PreparedStatement ps = dbHandler.getConnection().prepareStatement("SELECT COUNT(*) AS ? FROM REQUESTS WHERE CLIENT_ID = ?");
    ps.setString(1, "TOTAL_ORDERS_NUMBER_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);
    ResultSet rs = ps.executeQuery();
    rs.next();
    return rs;
  }

  private ResultSet returnTotalValueOfOrders() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();

    String sqlQuery = "SELECT SUM(PRICE) AS TOTAL_VALUE_OF_ORDERS FROM REQUESTS;";
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();
    return rs;
  }

  private ResultSet returnTotalValueOfOrdersOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    int clientId = scanner.nextInt();

    DbHandler dbHandler = DbHandler.getInstance();
    PreparedStatement ps = dbHandler.getConnection().prepareStatement("SELECT SUM(PRICE) AS ? FROM REQUESTS WHERE CLIENT_ID = ?");
    ps.setString(1, "TOTAL_VALUE_OF_ORDERS_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);
    ResultSet rs = ps.executeQuery();
    rs.next();
    return rs;
  }

  private ResultSet returnListOfAllOrders() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();

    String sqlQuery = "SELECT * FROM REQUESTS;";
    ResultSet rs = statement.executeQuery(sqlQuery);
    return rs;
  }
}
