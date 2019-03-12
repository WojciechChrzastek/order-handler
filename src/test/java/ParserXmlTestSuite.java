import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ParserXmlTestSuite {
  @Test
  public void testParse() {
    //Given
    ParserXml parserXml = new ParserXml();
    List<Request> requestsList;

    //When
    requestsList = parserXml.parse("requests.xml");

    //Then
    assertEquals(4, requestsList.size());
    assertEquals(1, requestsList.get(0).getRequestId());
    assertEquals("1", requestsList.get(1).getClientId());
    assertEquals("Bułka", requestsList.get(0).getName());
    assertEquals(1, requestsList.get(0).getQuantity());
    assertThat(new BigDecimal(10.00), Matchers.comparesEqualTo(requestsList.get(0).getPrice()));
  }

  @Test
  public void testGetRequestsList() {
    //Given
    ParserXml parserXml = new ParserXml();
    List<Request> requestsList1 = parserXml.parse("requests.xml");

    //When
    List<Request> requestList2 = parserXml.getRequestsList();

    //Then
    assertEquals(requestsList1, requestList2);
  }
}
