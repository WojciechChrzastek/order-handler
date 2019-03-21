import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ReportGeneratorTestSuite {

  @Test
  public void testGenerateReport() throws SQLException {
    //Given
    ReportGenerator reportGenerator = new ReportGenerator();
    DbHandler dbHandler = DbHandler.getInstance();
    dbHandler.deleteTable();
    dbHandler.createTable();
    dbHandler.insert(new Request("1", 1, "Pączek", 1, new BigDecimal(1)));
    dbHandler.insert(new Request("1", 1, "Ciastko", 1, new BigDecimal(2)));
    dbHandler.insert(new Request("2", 1, "Kiełbasa", 1, new BigDecimal(5)));
    dbHandler.insert(new Request("2", 1, "Dynia", 1, new BigDecimal(10)));

    //When
    ResultSet rs1 = reportGenerator.generateReport("1", "local");
    ResultSet rs3 = reportGenerator.generateReport("3", "local");
    ResultSet rs5 = reportGenerator.generateReport("5", "local");
    ResultSet rs7 = reportGenerator.generateReport("7", "local");

    //Then
    assertEquals(4, rs1.getInt(1));
    assertEquals(18, rs3.getInt(1));

    List<Request> requestList = new ArrayList<>();
    rs5.beforeFirst();
    while (rs5.next()) {
      String clientId = rs5.getString("CLIENT_ID");
      long requestId = rs5.getLong("REQUEST_ID");
      String name = rs5.getString("NAME");
      int quantity = rs5.getInt("QUANTITY");
      BigDecimal price = rs5.getBigDecimal("PRICE");
      Request request = new Request(clientId, requestId, name, quantity, price);
      requestList.add(request);
    }

    assertThat(requestList, containsInAnyOrder(
            new Request("1", 1, "Pączek", 1, new BigDecimal(1.00).setScale(2, RoundingMode.HALF_UP)),
            new Request("1", 1, "Ciastko", 1,  new BigDecimal(2.00).setScale(2, RoundingMode.HALF_UP)),
            new Request("2", 1, "Kiełbasa", 1,  new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP)),
            new Request("2", 1, "Dynia", 1, new BigDecimal(10.00).setScale(2, RoundingMode.HALF_UP))
    ));

    assertEquals(4, rs7.getInt(1));

    //Clean up after the test
    dbHandler.deleteTable();
  }
}
