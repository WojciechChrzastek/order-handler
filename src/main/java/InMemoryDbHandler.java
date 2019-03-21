import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

class InMemoryDbHandler {
  private Connection conn;
  private QueryRunner qr = new QueryRunner();

  private String sqlQuery;
  private ResultSet rs = null;

  Connection getConnection() {
    return conn;
  }

  void setDatabase() throws SQLException, ManagedProcessException {
    DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
    config.setPort(0);
    config.addArg("--default-time-zone=+0:00");
    DB db = DB.newEmbeddedDB(config.build());
    db.start();
    String dbName = "sampleInMemoryDatabase";
    db.createDB(dbName);
    conn = DriverManager.getConnection(config.getURL(dbName), "root", "");
  }

  void createTable() throws Exception {
    String createTableQuery = "CREATE TABLE IF NOT EXISTS REQUESTS " +
            "(" +
            "ID SERIAL PRIMARY KEY, " +
            "CLIENT_ID CHAR(6), " +
            "REQUEST_ID BIGINT UNSIGNED, " +
            "NAME VARCHAR(255) CHARSET utf8, " +
            "QUANTITY INT UNSIGNED, " +
            "PRICE DECIMAL (19,2) UNSIGNED " +
            ")";
    qr.update(conn, createTableQuery);
  }

  void insert(Request request) throws SQLException {

    String clientId = request.getClientId();
    long requestId = request.getRequestId();
    String name = request.getName();
    int quantity = request.getQuantity();
    BigDecimal price = request.getPrice();

    PreparedStatement ps = conn.prepareStatement(
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

  void addRequestsListToDatabase(List<Request> requestsList) throws SQLException {
    for (Request r : requestsList) {
      // ==>>> walidacja
      insert(r);
    }
  }


  void select() throws SQLException {
    List<String> results = qr.query(conn, "SELECT * FROM REQUESTS", new ColumnListHandler<>());

    System.out.println(results.size());
    System.out.println(results.get(0));

  }

  void closeDb() {
    DbUtils.closeQuietly(conn);
  }

  ResultSet returnListOfAllOrders() throws SQLException {
    sqlQuery = "SELECT * FROM REQUESTS";
    Statement statement = conn.createStatement();
    rs = statement.executeQuery(sqlQuery);
    rs.beforeFirst();
    return rs;
  }

  void showTables() throws SQLException {
    List<String> results = qr.query(conn, "SHOW TABLES", new ColumnListHandler<>());
    System.out.println(results.size());
    System.out.println(results.get(0));
  }
}
