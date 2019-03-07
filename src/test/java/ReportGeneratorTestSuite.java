import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class ReportGeneratorTestSuite {

  @Test
  public void testGenerateReport() throws SQLException {
    //Given
    ReportGenerator reportGenerator = new ReportGenerator();
    DbHandler dbHandler = DbHandler.getInstance();
    dbHandler.deleteTable();
    dbHandler.createTable();
    dbHandler.insert(new Request(1, 1, "Pączek", 1, 1));
    dbHandler.insert(new Request(1, 2, "Ciastko", 2, 2));
    dbHandler.insert(new Request(2, 3, "Kiełbasa", 3, 5));
    dbHandler.insert(new Request(2, 4, "Dynia", 4, 10));

    //When
    ResultSet rs1 = reportGenerator.generateReport("1");
    ResultSet rs3 = reportGenerator.generateReport("3");
    ResultSet rs5 = reportGenerator.generateReport("5");
    ResultSet rs7 = reportGenerator.generateReport("7");

    //Then
    Assert.assertEquals(4, rs1.getInt(1));
    Assert.assertEquals(60, rs3.getInt(1));

    List<Request> requestList = new ArrayList<>();
    rs5.beforeFirst();
    while (rs5.next()) {
      int clientId = rs5.getInt("CLIENT_ID");
      int requestId = rs5.getInt("REQUEST_ID");
      String name = rs5.getString("NAME");
      int quantity = rs5.getInt("QUANTITY");
      double price = rs5.getDouble("PRICE");
      Request request = new Request(clientId, requestId, name, quantity, price);
      requestList.add(request);
    }
    Assert.assertThat(requestList, containsInAnyOrder(
            new Request(1, 1, "Pączek", 1, 1),
            new Request(1, 2, "Ciastko", 2, 2),
            new Request(2, 3, "Kiełbasa", 3, 5),
            new Request(2, 4, "Dynia", 4, 10)
    ));

    Assert.assertEquals(15, rs7.getInt(1));

    //Clean up after the test
    dbHandler.deleteTable();
  }
}
