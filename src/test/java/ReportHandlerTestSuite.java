import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ReportHandlerTestSuite {
  private ReportGenerator reportGenerator = new ReportGenerator();
  private ReportHandler reportHandler = new ReportHandler();
  private DbHandler dbHandler = DbHandler.getInstance();

  @Before
  public void init() throws SQLException {
    dbHandler.deleteTable();
    dbHandler.createTable();
    dbHandler.insert(new Request("1", 1, "Pączek", 1, new BigDecimal(1)));
    dbHandler.insert(new Request("1", 2, "Ciastko", 2, new BigDecimal(2)));
    dbHandler.insert(new Request("2", 3, "Kiełbasa", 3, new BigDecimal(5)));
    dbHandler.insert(new Request("2", 4, "Dynia", 4, new BigDecimal(10)));
  }

  @After
  public void after() throws SQLException {
    dbHandler.deleteTable();
  }

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Test
  public void testPrintReportToConsole() throws SQLException {
    //Given
    String separator = System.getProperty("line.separator");
    String s = separator + "TOTAL_ORDERS_NUMBER" + separator + "4" + separator;
    String input = "1";


    //When
    reportHandler.printReportToConsole(reportGenerator.generateReport(input, dbHandler.getConnection(), 1));

    //Then
    assertEquals(s, systemOutRule.getLog());

    //Clean up after the test
  }

  @Test
  public void saveReportToCsvFile() throws SQLException, IOException {
    //Given
    String input = "1";
    ParserOpenCsv parserOpenCsv = new ParserOpenCsv();

    //When
    reportHandler.saveReportToCsvFile(reportGenerator.generateReport(input, dbHandler.getConnection(), 1), input);

    //Then
    List list1 = parserOpenCsv.parseTestFiles("out/test/resources/test_TOTAL_ORDERS_NUMBER.csv");
    List list2 = parserOpenCsv.parseTestFiles("TOTAL_ORDERS_NUMBER.csv");

    assertArrayEquals(list1.toArray(), list2.toArray());

    //Clean up after the test
    Files.deleteIfExists(Paths.get("TOTAL_ORDERS_NUMBER.csv"));
  }

  public ReportHandlerTestSuite() throws SQLException {
  }
}
