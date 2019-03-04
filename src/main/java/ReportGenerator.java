import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportGenerator {
  private List<String> report = new ArrayList<>();
  Scanner scanner = new Scanner(System.in);

  List<String> generateReport(String input) throws SQLException {

    switch (input) {
      case "1": {
        report.clear();
        report.add(String.valueOf(returnTotalOrdersNumber()));
        break;
      }
      case "2": {
        report.clear();
        report.add(String.valueOf(returnTotalOrdersNumberOfGivenClientId()));
        break;
      }
      case "3": {
        report.clear();
        report.add(String.valueOf(returnTotalValueOfOrders()));
        break;
      }
      case "4": {
        report.clear();
        report.add(String.valueOf(returnTotalValueOfOrdersOfGivenClientId()));
        break;
      }
    }
    return report;
  }

  private int returnTotalOrdersNumber() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();

    String sqlQuery = "SELECT COUNT(*) FROM REQUESTS";
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();
    return rs.getInt(1);
  }

  private int returnTotalOrdersNumberOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    int clientId = scanner.nextInt();

    DbHandler dbHandler = DbHandler.getInstance();
    PreparedStatement ps = dbHandler.getConnection().prepareStatement("SELECT COUNT(*) FROM REQUESTS WHERE CLIENT_ID = ?");
    ps.setInt(1, clientId);
    ResultSet rs = ps.executeQuery();
    rs.next();
    return rs.getInt(1);
  }

  private int returnTotalValueOfOrders() throws SQLException {
    DbHandler dbHandler = DbHandler.getInstance();
    Statement statement = dbHandler.getConnection().createStatement();

    String sqlQuery = "SELECT SUM(PRICE) FROM REQUESTS;";
    ResultSet rs = statement.executeQuery(sqlQuery);
    rs.next();
    return rs.getInt(1);
  }

  private int returnTotalValueOfOrdersOfGivenClientId() throws SQLException {
    System.out.print("Give Client Id: ");
    int clientId = scanner.nextInt();

    DbHandler dbHandler = DbHandler.getInstance();
    PreparedStatement ps = dbHandler.getConnection().prepareStatement("SELECT SUM(PRICE) FROM REQUESTS WHERE CLIENT_ID = ?");
    ps.setInt(1, clientId);
    ResultSet rs = ps.executeQuery();
    rs.next();
    return rs.getInt(1);
  }

//  total value of orders by client id
//  list of all orders
//  list of all orders by client id
//  average order value
//  average order value by client id


  public List<String> getReport() {
    return report;
  }

  public void setReport(List<String> report) {
    this.report = report;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ReportGenerator that = (ReportGenerator) o;

    return report != null ? report.equals(that.report) : that.report == null;
  }

  @Override
  public int hashCode() {
    return report != null ? report.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ReportGenerator{" +
            "report=" + report +
            '}';
  }
}
