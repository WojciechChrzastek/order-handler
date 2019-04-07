import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Properties;

final class DbHandler {
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

  void createTable() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();
    statement.executeUpdate(SqlQueries.CREATE_TABLE);
  }

  void deleteTable() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();
    statement.executeUpdate(SqlQueries.DELETE_TABLE);
  }

  void insert(Request request) throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();

    String clientId = request.getClientId();
    long requestId = request.getRequestId();
    String name = request.getName();
    int quantity = request.getQuantity();
    BigDecimal price = request.getPrice();

    PreparedStatement ps = dbHandler.getConnection().prepareStatement(SqlQueries.INSERT);

    ps.setString(1, clientId);
    ps.setLong(2, requestId);
    ps.setString(3, name);
    ps.setInt(4, quantity);
    ps.setBigDecimal(5, price);

    ps.executeUpdate();
  }

  void addRequestsListToDatabase(List<Request> requestsList) throws SQLException {
    for (Request r : requestsList) {
      // ==>>> walidacja
      insert(r);
    }
  }
}
