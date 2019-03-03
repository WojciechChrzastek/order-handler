import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParserOpenCsvTestSuite {
  @Test
  public void testParse() {
    //Given
    ParserOpenCsv parserOpenCsv = new ParserOpenCsv();
    List<Request> requestsList;

    //When
    requestsList = parserOpenCsv.parse("requests_test_file.csv");

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
    ParserOpenCsv parserOpenCsv = new ParserOpenCsv();
    List<Request> requestsList1 = parserOpenCsv.parse("requests_test_file.csv");

    //When
    List<Request> requestList2 = parserOpenCsv.getRequestsList();

    //Then
    Assert.assertEquals(requestsList1, requestList2);
  }
}
