import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class DbHandlerTestSuite {
  @Test
  public void testConnect() throws SQLException {
    //Given
    //When
    DbHandler dbHandler = DbHandler.getInstance();
    //Then
    Assert.assertNotNull(dbHandler.getConnection());
  }
}
