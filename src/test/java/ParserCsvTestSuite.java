import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParserCsvTestSuite {
  @Test
  public void testParse() {
    //Given
    ParserCsv parserCsv = new ParserCsv();
    List<Request> requestsList;

    //When
    requestsList = parserCsv.parse("requests_test_file.csv");

    //Then
    Assert.assertEquals(4, requestsList.size());
    Assert.assertEquals(1, requestsList.get(0).getRequestId());
    Assert.assertEquals(1, requestsList.get(1).getClientId());
    Assert.assertEquals("Bułka", requestsList.get(0).getName());
    Assert.assertEquals(1, requestsList.get(0).getQuantity());
    Assert.assertEquals(10.00, requestsList.get(0).getPrice(), 0.001);
  }

  @Test
  public void testGetRequestsList() {
    //Given
    ParserCsv parserCsv = new ParserCsv();
    List<Request> requestsList1 = parserCsv.parse("requests_test_file.csv");

    //When
    List<Request> requestList2 = parserCsv.getRequestsList();

    //Then
    Assert.assertEquals(requestsList1, requestList2);
  }
}
