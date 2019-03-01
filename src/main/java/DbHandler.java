import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbHandler {
  private Connection conn;
  private static DbHandler dbHandlerInstance;

  private DbHandler() throws SQLException {
    Properties connectionProps = new Properties();
    connectionProps.put("user", "???");
    connectionProps.put("password", "???");
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
}
