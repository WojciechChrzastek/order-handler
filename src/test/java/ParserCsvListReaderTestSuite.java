import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ParserCsvListReaderTestSuite {
  private ParserCsvListReader parserCsvListReader = new ParserCsvListReader();

  @Test
  public void testParse() {
    //Given

    //When
    List<Request> requestsList = parserCsvListReader.parse("out/test/resources/test.csv");

    //Then
    assertEquals(4, requestsList.size());
    assertEquals("1", requestsList.get(0).getClientId());
    assertEquals(1, requestsList.get(0).getRequestId());
    assertEquals("Bułka", requestsList.get(0).getName());
    assertEquals(1, requestsList.get(0).getQuantity());
    assertThat(new BigDecimal(10.00), Matchers.comparesEqualTo(requestsList.get(0).getPrice()));
  }
}
