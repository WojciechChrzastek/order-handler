import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class ReportGenerator {
  private String sqlQuery;
  private Scanner scanner = new Scanner(System.in);
  private DbHandler dbHandler = DbHandler.getInstance();
  private InMemoryDbHandler inMemoryDbHandler = new InMemoryDbHandler();
  private ResultSet rs = null;
  private int clientId;

  ReportGenerator() throws SQLException {
  }

  ResultSet generateReport(String input, String database) throws SQLException {


//    Statement statement;
//    if (database.equals("local")) {
//      statement = dbHandler.getConnection().createStatement();
//    } else {
//      statement = inMemoryDbHandler.getConnection().createStatement();
//    }

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
        rs = returnListOfAllOrders(database);
        break;
      }
      case "6": {
        rs = returnListOfAllOrdersOfGivenClientId();
        break;
      }
      case "7": {
        rs = returnAverageOrderValue();
        break;
      }
      case "8": {
        rs = returnAverageOrderValueOfGivenClientId();
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
    Statement statement = dbHandler.getConnection().createStatement();
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
    sqlQuery = "SELECT SUM(PRICE*QUANTITY) AS TOTAL_VALUE_OF_ORDERS FROM REQUESTS";
    Statement statement = dbHandler.getConnection().createStatement();
    rs = statement.executeQuery(sqlQuery);
    rs.next();
    return rs;
  }

  private ResultSet returnTotalValueOfOrdersOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    clientId = scanner.nextInt();
    sqlQuery = "SELECT SUM(PRICE * QUANTITY) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = dbHandler.getConnection().prepareStatement(sqlQuery);
    ps.setString(1, "TOTAL_VALUE_OF_ORDERS_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);
    rs = ps.executeQuery();
    rs.next();
    return rs;
  }

  private ResultSet returnListOfAllOrders(String database) throws SQLException {
//    rs = inMemoryDbHandler.returnListOfAllOrders();
    inMemoryDbHandler.showTables();
    Statement statement;
    sqlQuery = "SELECT * FROM REQUESTS";
    if (database.equals("local")) {
      statement = dbHandler.getConnection().createStatement();
    } else {
      statement = inMemoryDbHandler.getConnection().createStatement();
    }
//    Statement s = statement;
//    Statement s = inMemoryDbHandler.getConnection().createStatement();
    rs = statement.executeQuery(sqlQuery);
    rs.beforeFirst();
    return rs;
  }

  private ResultSet returnListOfAllOrdersOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    clientId = scanner.nextInt();
    sqlQuery = "SELECT * FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = dbHandler.getConnection().prepareStatement(sqlQuery);
    ps.setInt(1, clientId);
    rs = ps.executeQuery();
    rs.beforeFirst();
    return rs;
  }

  private ResultSet returnAverageOrderValue() throws SQLException {
    sqlQuery = "SELECT AVG(QUANTITY * PRICE) AS AVERAGE_ORDER_VALUE FROM REQUESTS";
    Statement statement = dbHandler.getConnection().createStatement();
    rs = statement.executeQuery(sqlQuery);
    rs.next();
    return rs;
  }

  private ResultSet returnAverageOrderValueOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    clientId = scanner.nextInt();
    sqlQuery = "SELECT AVG(QUANTITY * PRICE) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
    PreparedStatement ps = dbHandler.getConnection().prepareStatement(sqlQuery);
    ps.setString(1, "AVERAGE_ORDER_VALUE_FOR_CLIENT_ID_NO_" + clientId);
    ps.setInt(2, clientId);
    rs = ps.executeQuery();
    rs.next();
    return rs;
  }
}
