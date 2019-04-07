import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

class InMemoryDbHandler {
  private Connection conn;
  private QueryRunner qr = new QueryRunner();
  private static InMemoryDbHandler inMemoryDbHandlerInstance;

  private InMemoryDbHandler() throws SQLException, ManagedProcessException {
    DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
    config.setPort(0);
    config.addArg("--default-time-zone=+0:00");
    DB db = DB.newEmbeddedDB(config.build());
    db.start();
    String dbName = "sampleInMemoryDatabase";
    db.createDB(dbName);
    conn = DriverManager.getConnection(config.getURL(dbName), "root", "");
  }

  static InMemoryDbHandler getInstance() throws SQLException, ManagedProcessException {
    if (inMemoryDbHandlerInstance == null) {
      inMemoryDbHandlerInstance = new InMemoryDbHandler();
    }
    return inMemoryDbHandlerInstance;
  }

  Connection getConnection() {
    return conn;
  }

  void createTable() throws Exception {
    qr.update(conn, SqlQueries.CREATE_TABLE);
  }

  void deleteTable() throws SQLException, ManagedProcessException {
    InMemoryDbHandler inMemoryDbHandler = InMemoryDbHandler.getInstance();
    Statement statement = inMemoryDbHandler.getConnection().createStatement();
    statement.executeUpdate(SqlQueries.DELETE_TABLE);
  }

  static void insert(Request request) throws SQLException, ManagedProcessException {
    InMemoryDbHandler inMemoryDbHandler = InMemoryDbHandler.getInstance();

    String clientId = request.getClientId();
    long requestId = request.getRequestId();
    String name = request.getName();
    int quantity = request.getQuantity();
    BigDecimal price = request.getPrice();

    PreparedStatement ps = inMemoryDbHandler.getConnection().prepareStatement(SqlQueries.INSERT);

    ps.setString(1, clientId);
    ps.setLong(2, requestId);
    ps.setString(3, name);
    ps.setInt(4, quantity);
    ps.setBigDecimal(5, price);

    ps.executeUpdate();
  }

  void addRequestsListToDatabase(List<Request> requestsList) throws SQLException, ManagedProcessException {
    for (Request r : requestsList) {
      // ==>>> walidacja
      insert(r);
    }
  }

  void closeDb() {
    DbUtils.closeQuietly(conn);
  }

}
