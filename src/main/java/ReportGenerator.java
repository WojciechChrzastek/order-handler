import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class ReportGenerator {
  private String sqlQuery;
  private Scanner scanner = new Scanner(System.in);
  private DbHandler dbHandler = DbHandler.getInstance();
  private Statement statement = dbHandler.getConnection().createStatement();
  private ResultSet rs = null;
  private int clientId;

  ReportGenerator() throws SQLException {
  }

  ResultSet generateReport(String input) throws SQLException {

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
      default: {
        System.out.println("Wrong input");
      }
    }
    return rs;
  }

  private ResultSet returnTotalOrdersNumber() throws SQLException {

    sqlQuery = "SELECT COUNT(*) AS TOTAL_ORDERS_NUMBER FROM REQUESTS";
    rs = statement.executeQuery(sqlQuery);
    rs.next();
    return rs;
  }

  private ResultSet returnTotalOrdersNumberOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    clientId = scanner.nextInt();

    sqlQuery = "SELECT COUNT(*) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = dbHandler.getConnection().prepareStatement(sqlQuery);
    ps.setString(1, "TOTAL_ORDERS_NUMBER_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);
    rs = ps.executeQuery();
    rs.next();
    return rs;
  }

  private ResultSet returnTotalValueOfOrders() throws SQLException {

    sqlQuery = "SELECT SUM(PRICE) AS TOTAL_VALUE_OF_ORDERS FROM REQUESTS;";
    rs = statement.executeQuery(sqlQuery);
    rs.next();
    return rs;
  }

  private ResultSet returnTotalValueOfOrdersOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    clientId = scanner.nextInt();

    sqlQuery = "SELECT SUM(PRICE) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = dbHandler.getConnection().prepareStatement(sqlQuery);
    ps.setString(1, "TOTAL_VALUE_OF_ORDERS_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);
    rs = ps.executeQuery();
    rs.next();
    return rs;
  }

  private ResultSet returnListOfAllOrders() throws SQLException {

    sqlQuery = "SELECT * FROM REQUESTS;";
    rs = statement.executeQuery(sqlQuery);
    return rs;
  }
}
