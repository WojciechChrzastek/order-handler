import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
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
    Assert.assertEquals("1", requestsList.get(1).getClientId());
    Assert.assertEquals("Bułka", requestsList.get(0).getName());
    Assert.assertEquals(1, requestsList.get(0).getQuantity());
    Assert.assertThat(new BigDecimal(10.00), Matchers.comparesEqualTo(requestsList.get(0).getPrice()));
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
