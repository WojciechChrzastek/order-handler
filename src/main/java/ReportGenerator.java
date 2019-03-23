import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class ReportGenerator {
  private String sqlQuery;
  private Scanner scanner = new Scanner(System.in);
  private ResultSet rs = null;
  private int clientId;

  ResultSet generateReport(String input, Connection conn) throws SQLException {

    switch (input) {
      case "1": {
        rs = returnTotalOrdersNumber(conn);
        break;
      }
      case "2": {
        rs = returnTotalOrdersNumberOfGivenClientId(conn);
        break;
      }
      case "3": {
        rs = returnTotalValueOfOrders(conn);
        break;
      }
      case "4": {
        rs = returnTotalValueOfOrdersOfGivenClientId(conn);
        break;
      }
      case "5": {
        rs = returnListOfAllOrders(conn);
        break;
      }
      case "6": {
        rs = returnListOfAllOrdersOfGivenClientId(conn);
        break;
      }
      case "7": {
        rs = returnAverageOrderValue(conn);
        break;
      }
      case "8": {
        rs = returnAverageOrderValueOfGivenClientId(conn);
        break;
      }
      default: {
        System.out.println("Wrong input");
      }
    }
    return rs;
  }

  private ResultSet returnTotalOrdersNumber(Connection conn) throws SQLException {
    sqlQuery = "SELECT COUNT(*) AS TOTAL_ORDERS_NUMBER FROM REQUESTS";
    rs = conn.createStatement().executeQuery(sqlQuery);
    rs.next();
    return rs;
  }

  private ResultSet returnTotalOrdersNumberOfGivenClientId(Connection conn) throws SQLException {
    System.out.println(SoutMessages.GIVE_CLIENT_ID);
    clientId = scanner.nextInt();
    sqlQuery = "SELECT COUNT(*) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = conn.prepareStatement(sqlQuery);
    ps.setString(1, "TOTAL_ORDERS_NUMBER_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);

    rs = ps.executeQuery();
    rs.next();
    return rs;
  }

  private ResultSet returnTotalValueOfOrders(Connection conn) throws SQLException {
    sqlQuery = "SELECT SUM(PRICE*QUANTITY) AS TOTAL_VALUE_OF_ORDERS FROM REQUESTS";

//    rs = conn.createStatement().executeQuery(sqlQuery);

    PreparedStatement ps = conn.prepareStatement(sqlQuery);

    rs = ps.executeQuery();
    rs.next();
    return rs;
  }

  private ResultSet returnTotalValueOfOrdersOfGivenClientId(Connection conn) throws SQLException {
    System.out.println(SoutMessages.GIVE_CLIENT_ID);
    clientId = scanner.nextInt();
    sqlQuery = "SELECT SUM(PRICE * QUANTITY) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = conn.prepareStatement(sqlQuery);
    ps.setString(1, "TOTAL_VALUE_OF_ORDERS_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);

    rs = ps.executeQuery();
    rs.next();
    return rs;
  }

  private ResultSet returnListOfAllOrders(Connection conn) throws SQLException {
    sqlQuery = "SELECT * FROM REQUESTS";
    rs = conn.createStatement().executeQuery(sqlQuery);
    rs.beforeFirst();
    return rs;
  }

  private ResultSet returnListOfAllOrdersOfGivenClientId(Connection conn) throws SQLException {
    System.out.println(SoutMessages.GIVE_CLIENT_ID);
    clientId = scanner.nextInt();
    sqlQuery = "SELECT * FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = conn.prepareStatement(sqlQuery);
    ps.setInt(1, clientId);
    rs = ps.executeQuery();
    rs.beforeFirst();
    return rs;
  }

  private ResultSet returnAverageOrderValue(Connection conn) throws SQLException {
    sqlQuery = "SELECT AVG(QUANTITY * PRICE) AS AVERAGE_ORDER_VALUE FROM REQUESTS";
    rs = conn.createStatement().executeQuery(sqlQuery);
    rs.next();
    return rs;
  }

  private ResultSet returnAverageOrderValueOfGivenClientId(Connection conn) throws SQLException {
    System.out.println(SoutMessages.GIVE_CLIENT_ID);
    clientId = scanner.nextInt();
    sqlQuery = "SELECT AVG(QUANTITY * PRICE) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = conn.prepareStatement(sqlQuery);
    ps.setString(1, "AVERAGE_ORDER_VALUE_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);

    rs = ps.executeQuery();
    rs.next();
    return rs;
  }
}
