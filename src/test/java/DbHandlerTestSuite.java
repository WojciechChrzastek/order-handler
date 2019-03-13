import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DbHandlerTestSuite {
  private DbHandler dbHandler = DbHandler.getInstance();

  @Test
  public void testConnect() throws SQLException {
    //Given

    //When

    //Then
    assertNotNull(dbHandler.getConnection());
  }

  @Test
  public void testCreateTable() throws SQLException {
    //Given

    //When
    dbHandler.createTable();

    //Then
    String sqlQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'order_handler' AND table_name = 'REQUESTS'";
    Statement statement = dbHandler.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();

    assertEquals(1, rs.getInt(1));

    //Clean up after the test
    if (rs.getInt(1) == 1) {
      statement.executeUpdate("DROP TABLE REQUESTS");
    }
    statement.close();
    rs.close();
  }

  @Test
  public void testInsert() throws SQLException {
    //Given
    dbHandler.createTable();
    String sqlQuery = "SELECT COUNT(*) FROM REQUESTS";
    Statement statement = dbHandler.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();
    int rowsNumberBeforeInsert = rs.getInt(1);

    Request r = new Request("5", 8, "Roll", 16, new BigDecimal(26.14));

    //When
    dbHandler.insert(r);

    //Then
    ResultSet rs2 = statement.executeQuery(sqlQuery);
    rs2.next();
    int rowsNumberAfterInsert = rs2.getInt(1);

    assertEquals(rowsNumberBeforeInsert + 1, rowsNumberAfterInsert);

    //Clean up after the test
    if (rowsNumberBeforeInsert + 1 == rowsNumberAfterInsert) {
      statement.executeUpdate("DELETE FROM REQUESTS ORDER BY ID DESC LIMIT 1");
    }
    statement.executeUpdate("DROP TABLE REQUESTS");
    statement.close();
    rs.close();
  }

  public DbHandlerTestSuite() throws SQLException {
  }
}
