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
    dbHandler.createTable();

    //Then
    String sqlQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'order_handler' AND table_name = 'REQUESTS'";
    Statement statement = dbHandler.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();

    Assert.assertEquals(1, rs.getInt(1));

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
    DbHandler dbHandler = DbHandler.getInstance();
    String sqlQuery = "SELECT COUNT(*) FROM REQUESTS";
    Statement statement = dbHandler.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();
    int rowsNumberBeforeInsert = rs.getInt(1);
    System.out.println(rowsNumberBeforeInsert);

    Request r = new Request(5, 8, "John", 16, 26.14);

    //When
    dbHandler.insert(r);

    //Then
    ResultSet rs2 = statement.executeQuery(sqlQuery);
    rs2.next();
    int rowsNumberAfterInsert = rs2.getInt(1);
    System.out.println(rowsNumberAfterInsert);

    Assert.assertEquals(rowsNumberBeforeInsert + 1, rowsNumberAfterInsert);

    //Clean up after the test
    if (rowsNumberBeforeInsert + 1 == rowsNumberAfterInsert) {
      statement.executeUpdate("DELETE FROM REQUESTS ORDER BY ID DESC LIMIT 1");
    }
    statement.close();
    rs.close();
  }
}
