import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ParserCsvTestSuite {
  @Test
  public void testParse() {
    //Given
    ParserCsv parserCsv = new ParserCsv();
    List<Request> requestsList;

    //When
    requestsList = parserCsv.parse("requests_test_file.csv");

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
    ParserCsv parserCsv = new ParserCsv();
    List<Request> requestsList1 = parserCsv.parse("requests_test_file.csv");

    //When
    List<Request> requestList2 = parserCsv.getRequestsList();

    //Then
    assertEquals(requestsList1, requestList2);
  }
}
