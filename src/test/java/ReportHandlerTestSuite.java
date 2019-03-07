import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReportHandlerTestSuite {
  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Test
  public void testPrintReportToConsole() throws SQLException {

    //Given
    ReportGenerator reportGenerator = new ReportGenerator();
    ReportHandler reportHandler = new ReportHandler();
    DbHandler dbHandler = DbHandler.getInstance();
    dbHandler.deleteTable();
    dbHandler.createTable();
    dbHandler.insert(new Request(1, 1, "Pączek", 1, 1));
    dbHandler.insert(new Request(1, 2, "Ciastko", 2, 2));
    dbHandler.insert(new Request(2, 3, "Kiełbasa", 3, 5));
    dbHandler.insert(new Request(2, 4, "Dynia", 4, 10));
    String separator = System.getProperty("line.separator");
    String s = "TOTAL_ORDERS_NUMBER" + separator + "4" + separator;
    String input = "1";

    //When
    reportHandler.printReportToConsole(reportGenerator.generateReport(input));

    //Then
    Assert.assertEquals(s, systemOutRule.getLog());

    //Clean up after the test
    dbHandler.deleteTable();
  }

  @Test
  public void saveReportToCsvFile() throws SQLException, IOException {

    //Given
    ReportGenerator reportGenerator = new ReportGenerator();
    ReportHandler reportHandler = new ReportHandler();
    DbHandler dbHandler = DbHandler.getInstance();
    dbHandler.deleteTable();
    dbHandler.createTable();
    dbHandler.insert(new Request(1, 1, "Pączek", 1, 1));
    dbHandler.insert(new Request(1, 2, "Ciastko", 2, 2));
    dbHandler.insert(new Request(2, 3, "Kiełbasa", 3, 5));
    dbHandler.insert(new Request(2, 4, "Dynia", 4, 10));
    String input = "1";
    ParserOpenCsv parserOpenCsv = new ParserOpenCsv();

    //When
    reportHandler.saveReportToCsvFile(reportGenerator.generateReport(input), input);

    //Then
    List list1 = parserOpenCsv.parseTestFiles("testing_file.csv");
    List list2 = parserOpenCsv.parseTestFiles("total orders number.csv");

    Assert.assertArrayEquals(list1.toArray(), list2.toArray());

    //Clean up after the test
    dbHandler.deleteTable();
//    Files.deleteIfExists(Paths.get("total orders number.csv"));
  }
}
