import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParserXmlTestSuite {
  @Test
  public void testParse() {
    //Given
    ParserXml parserXml = new ParserXml();
    List<Request> requestsList;

    //When
    requestsList = parserXml.parse("requests.xml");

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
    ParserXml parserXml = new ParserXml();
    List<Request> requestsList1 = parserXml.parse("requests.xml");

    //When
    List<Request> requestList2 = parserXml.getRequestsList();

    //Then
    Assert.assertEquals(requestsList1, requestList2);
  }
}
