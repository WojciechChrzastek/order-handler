//import org.hamcrest.Matchers;
//import org.junit.Test;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//
//public class ParserCsvTestSuite {
//
//  @Test
//  public void testParse() {
//    //Given
//    ParserCsv parserCsv = new ParserCsv();
//    ParserCsv parserCsv2 = new ParserCsv();
//
//    //When
//    List<Request> requestsList = parserCsv.parse("out/test/resources/test.csv");
//    List<Request> requestsListMissing = parserCsv2.parse("out/test/resources/test_missing.csv");
//
//    //Then
//    assertEquals(4, requestsList.size());
//    assertEquals(1, requestsList.get(0).getRequestId());
//    assertEquals("1", requestsList.get(1).getClientId());
//    assertEquals("Bułka", requestsList.get(0).getName());
//    assertEquals(1, requestsList.get(0).getQuantity());
//    assertThat(new BigDecimal(10.00), Matchers.comparesEqualTo(requestsList.get(0).getPrice()));
//
//    assertEquals(3, requestsListMissing.size());
//    assertEquals(1, requestsListMissing.get(2).getRequestId());
//    assertEquals("2", requestsListMissing.get(2).getClientId());
//    assertEquals("Chleb", requestsListMissing.get(2).getName());
//    assertEquals(1, requestsListMissing.get(2).getQuantity());
//    assertThat(new BigDecimal(10.00), Matchers.comparesEqualTo(requestsListMissing.get(2).getPrice()));
//  }
//
//  @Test
//  public void testGetRequestsList() {
//    //Given
//    ParserCsv parserCsv = new ParserCsv();
//    List<Request> requestsList = parserCsv.parse("out/test/resources/test.csv");
//
//    //When
//    List<Request> requestList2 = parserCsv.getRequestsList();
//
//    //Then
//    assertEquals(requestsList, requestList2);
//  }
//}
