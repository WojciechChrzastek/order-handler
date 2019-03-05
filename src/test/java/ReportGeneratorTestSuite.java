import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportGeneratorTestSuite {

  @Test
  public void testGenerateReport() throws SQLException {
    //Given
    ReportGenerator reportGenerator = new ReportGenerator();

    DbHandler dbHandler = DbHandler.getInstance();
    dbHandler.deleteTable();
    dbHandler.createTable();
    dbHandler.insert(new Request(1, 1, "Pączek", 1, 1));
    dbHandler.insert(new Request(1, 2, "Ciastko", 2, 2));
    dbHandler.insert(new Request(2, 3, "Kiełbasa", 3, 5));
    dbHandler.insert(new Request(2, 4, "Dynia", 4, 10));

    String input1 = "1";
//    String input2 = "2";
    String input3 = "3";
//    String input4 = "4";
    String input5 = "5";
//    String input6 = "6";
    String input7 = "7";
//    String input8 = "8";

    //When
    ResultSet rs1 = reportGenerator.generateReport(input1);
    ResultSet rs3 = reportGenerator.generateReport(input3);
//    ResultSet rs5 = reportGenerator.generateReport(input5);
    ResultSet rs7 = reportGenerator.generateReport(input7);

    //Then
    Assert.assertEquals(4, rs1.getInt(1));
    Assert.assertEquals(60, rs3.getInt(1));
//    Assert.assertEquals(1, rs5.getInt(1));
    Assert.assertEquals(15, rs7.getInt(1));

    //Clean up after the test
    dbHandler.deleteTable();
  }
}
