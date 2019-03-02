import java.sql.*;
import java.util.Properties;

public class DbHandler {
  private Connection conn;
  private static DbHandler dbHandlerInstance;

  private DbHandler() throws SQLException {
    Properties connectionProps = new Properties();
    connectionProps.put("user", "wojtek");
    connectionProps.put("password", "wojtek123");
    conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/order_handler?serverTimezone=Europe/Warsaw" +
                    "&useSSL=False", connectionProps);
  }

  public static DbHandler getInstance() throws SQLException {
    if (dbHandlerInstance == null) {
      dbHandlerInstance = new DbHandler();
    }
    return dbHandlerInstance;
  }

  public Connection getConnection() {
    return conn;
  }

  public void createTable() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();

    String sqlQuery = "CREATE TABLE IF NOT EXISTS REQUESTS " +
            "(" +
            "ID SERIAL PRIMARY KEY, " +
            "CLIENT_ID INT(20) UNSIGNED, " +
            "REQUEST_ID INT(20) UNSIGNED, " +
            "NAME VARCHAR(255) CHARSET utf8, " +
            "QUANTITY INT UNSIGNED, " +
            "PRICE DECIMAL (9,2) UNSIGNED " +
            ")";
    statement.executeUpdate(sqlQuery);
  }

  public void insert(Request request) throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();

    int clientId = request.getClientId();
    int requestId = request.getRequestId();
    String name = request.getName();
    int quantity = request.getQuantity();
    double price = request.getPrice();

    PreparedStatement ps = dbHandler.getConnection().prepareStatement(
            "INSERT INTO REQUESTS" +
                    " (CLIENT_ID, REQUEST_ID, NAME, QUANTITY, PRICE)" +
                    " VALUES (?, ?, ?, ?, ?)");

    ps.setInt(1, clientId);
    ps.setInt(2, requestId);
    ps.setString(3, name);
    ps.setInt(4, quantity);
    ps.setDouble(5, price);

    ps.executeUpdate();
  }
}
