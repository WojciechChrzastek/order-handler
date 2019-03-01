import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHandlerTestSuite {
  @Test
  public void testConnect() throws SQLException {
    //Given
    //When
    DbHandler dbHandler = DbHandler.getInstance();
    //Then
    Assert.assertNotNull(dbHandler.getConnection());
  }

  @Test
  public void testCreateTable() throws SQLException {
    //Given
    DbHandler dbHandler = DbHandler.getInstance();

    //When
    String sqlQuery = "CREATE TABLE TEST " +
            "(" +
            "ID SERIAL PRIMARY KEY, " +
            "CLIENT_ID INTEGER UNSIGNED, " +
            "REQUEST_ID INTEGER UNSIGNED, " +
            "NAME CHAR CHARSET utf8, " +
            "QUANTITY INTEGER UNSIGNED, " +
            "PRICE DECIMAL (9,2) UNSIGNED " +
            ")";
    Statement statement = dbHandler.getConnection().createStatement();
    statement.executeUpdate(sqlQuery);

    //Then
    sqlQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'order_handler' AND table_name = 'TEST'";
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();

    Assert.assertEquals(1, rs.getInt(1));

    //Clean up after the test
    if (rs.getInt(1) == 1) {
      statement.executeUpdate("DROP TABLE TEST");
    }
    statement.close();
    rs.close();
  }
}
