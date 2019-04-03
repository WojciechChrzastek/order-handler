import com.opencsv.CSVWriter;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class ReportHandler {

  void printReportToConsole(ResultSet rs) throws SQLException {
    System.out.println();
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
        String client_id = rs.getString("client_id");
        long request_id = rs.getLong("request_id");
        String name = rs.getString("name");
        int quantity = rs.getInt("quantity");
        BigDecimal price = rs.getBigDecimal("price");

        System.out.format("%3s %9s %10s %8s %8s %6s\n", id, client_id, request_id, name, quantity, price);
      }
    } else {
      System.out.println(rsMetaData.getColumnLabel(1));
      System.out.println(rs.getInt(1));
    }
  }

  void saveReportToCsvFile(ResultSet rs, String input) throws SQLException {
    String path;
    switch (input) {
      case "5": {
        path = "LIST_OF_ALL_ORDERS";
        break;
      }
      case "6": {
        rs.next();
        path = "LIST_OF_ALL_ORDERS_BY_CLIENT_ID_NO_" + rs.getInt("CLIENT_ID");
        break;
      }
      default: {
        path = rs.getMetaData().getColumnName(1);
      }
    }
    String pathname = path + ".csv";
    File file = new File(pathname);
    CSVWriter csvWriter = null;
    try {
      csvWriter = new CSVWriter(
              new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8),
              ',', ' ',
              CSVWriter.DEFAULT_ESCAPE_CHARACTER,
              CSVWriter.DEFAULT_LINE_END
      );
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    rs.beforeFirst();
    try {
      if (csvWriter != null) {
        csvWriter.writeAll(rs, true);
      }
    } catch (IOException e) {
      System.out.println("Object not found");
      e.printStackTrace();
    } finally {
      if (csvWriter != null) {
        try {
          csvWriter.close();
        } catch (IOException e) {
          System.out.println("Object not found");
          e.printStackTrace();
        }
      }
    }
  }
}
