import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

class InMemoryDbHandler {
  private Connection conn;
  private QueryRunner qr = new QueryRunner();

  void setDatabase() throws SQLException, ManagedProcessException {
    DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
    config.setPort(0);
    config.addArg("--default-time-zone=+0:00");
    DB db = DB.newEmbeddedDB(config.build());
    db.start();
    String dbName = "sampleInMemoryDataBase";
    db.createDB(dbName);
    conn = DriverManager.getConnection(config.getURL(dbName), "root", "");
  }

//  Connection getConnection() {
//    return conn;
//  }

  void test() throws Exception {

    qr.update(conn, "CREATE TABLE hello(world VARCHAR(100))");
    qr.update(conn, "INSERT INTO hello VALUES ('Hello, world')");
    List<String> results = qr.query(conn, "SELECT * FROM hello", new ColumnListHandler<String>());

    System.out.println(results.size());
    System.out.println(results.get(0));

    DbUtils.closeQuietly(conn);
  }
}
