import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ReportGenerator {
  private ResultSet rs = null;

  ResultSet generateReport(String input, Connection conn, int clientId) throws SQLException {
    int report = Integer.parseInt(input);

    if (report == 1 | (report != 5 && report != 6)) {
      if (report == 1) {
        rs = conn.createStatement().executeQuery(SqlQueries.RETURN_TOTAL_ORDERS_NUMBER);
      } else {
        switch (report) {
          case 2: {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.RETURN_TOTAL_ORDERS_NUMBER_OF_GIVEN_CLIENT_ID);
            ps.setString(1, "TOTAL_ORDERS_NUMBER_FOR_CLIENT_ID_NO_" + clientId);
            ps.setInt(2, clientId);
            rs = ps.executeQuery();
            break;
          }
          case 3: {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.RETURN_TOTAL_VALUE_OF_ORDERS);
            rs = ps.executeQuery();
            break;
          }
          case 4: {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.RETURN_TOTAL_VALUE_OF_ORDERS_OF_GIVEN_CLIENT_ID);
            ps.setString(1, "TOTAL_VALUE_OF_ORDERS_FOR_CLIENT_ID_NO_" + clientId);
            ps.setInt(2, clientId);
            rs = ps.executeQuery();
            break;
          }
          case 7: {
            rs = conn.createStatement().executeQuery(SqlQueries.RETURN_AVERAGE_ORDER_VALUE);
            break;
          }
          case 8: {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.RETURN_AVERAGE_ORDER_VALUE_OF_GIVEN_CLIENT_ID);
            ps.setString(1, "AVERAGE_ORDER_VALUE_FOR_CLIENT_ID_NO_" + clientId);
            ps.setInt(2, clientId);
            rs = ps.executeQuery();
            break;
          }
          default: {
            break;
          }
        }
      }
      rs.next();
    } else {
      if (report == 5) {
        rs = conn.createStatement().executeQuery(SqlQueries.RETURN_LIST_OF_ALL_ORDERS);
      } else {
        PreparedStatement ps = conn.prepareStatement(SqlQueries.RETURN_LIST_OF_ALL_ORDERS_OF_GIVEN_CLIENT_ID);
        ps.setInt(1, clientId);
        rs = ps.executeQuery();
      }
      rs.beforeFirst();
    }
    return rs;
  }
}
