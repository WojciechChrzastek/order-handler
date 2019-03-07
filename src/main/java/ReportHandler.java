import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class ReportHandler {

  void printReportToConsole(ResultSet rs) throws SQLException {
    ResultSetMetaData rsMetaData = rs.getMetaData();
    if (rs.getMetaData().getColumnCount() == 6) {
      System.out.format("%3s %9s %10s %8s %8s %6s",
              rsMetaData.getColumnLabel(1),
              rsMetaData.getColumnLabel(2),
              rsMetaData.getColumnLabel(3),
              rsMetaData.getColumnLabel(4),
              rsMetaData.getColumnLabel(5),
              rsMetaData.getColumnLabel(6));

      System.out.println();

      while (rs.next()) {
        int id = rs.getInt("id");
        int client_id = rs.getInt("client_id");
        int request_id = rs.getInt("request_id");
        String name = rs.getString("name");
        int quantity = rs.getInt("quantity");
        double price = rs.getInt("price");

        System.out.format("%3s %9s %10s %8s %8s %6s\n", id, client_id, request_id, name, quantity, price);
      }
    } else {
      System.out.println(rsMetaData.getColumnLabel(1));
      System.out.println(rs.getInt(1));
    }
  }

  void saveReportToCsvFile(ResultSet rs, String input) throws IOException, SQLException {

    switch (input) {
      case "1": {
        input = "total orders number";
        break;
      }
      default: {
        input = ("report");
      }
    }

    String pathname = input + ".csv";
    File file = new File(pathname);
    CSVWriter csvWriter = new CSVWriter(
            new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8),
            ',', ' ',
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END
    );
    rs.beforeFirst();
    csvWriter.writeAll(rs, true);
    csvWriter.close();
  }
}
