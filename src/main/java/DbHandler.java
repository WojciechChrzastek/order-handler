import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;

class DbHandler {
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

  static DbHandler getInstance() throws SQLException {
    if (dbHandlerInstance == null) {
      dbHandlerInstance = new DbHandler();
    }
    return dbHandlerInstance;
  }

  Connection getConnection() {
    return conn;
  }

  void deleteTable() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();

    String sqlQuery = "DROP TABLE IF EXISTS REQUESTS";
    statement.executeUpdate(sqlQuery);
  }

  void createTable() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();

    String sqlQuery = "CREATE TABLE IF NOT EXISTS REQUESTS " +
            "(" +
            "ID SERIAL PRIMARY KEY, " +
            "CLIENT_ID CHAR(6), " +
            "REQUEST_ID BIGINT UNSIGNED, " +
            "NAME VARCHAR(255) CHARSET utf8, " +
            "QUANTITY INT UNSIGNED, " +
            "PRICE DECIMAL (19,2) UNSIGNED " +
            ")";
    statement.executeUpdate(sqlQuery);
  }

  void insert(Request request) throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();

    String clientId = request.getClientId();
    long requestId = request.getRequestId();
    String name = request.getName();
    int quantity = request.getQuantity();
    BigDecimal price = request.getPrice();

    PreparedStatement ps = dbHandler.getConnection().prepareStatement(
            "INSERT INTO REQUESTS" +
                    " (CLIENT_ID, REQUEST_ID, NAME, QUANTITY, PRICE)" +
                    " VALUES (?, ?, ?, ?, ?)");

    ps.setString(1, clientId);
    ps.setLong(2, requestId);
    ps.setString(3, name);
    ps.setInt(4, quantity);
    ps.setBigDecimal(5, price);

    ps.executeUpdate();
  }
}
