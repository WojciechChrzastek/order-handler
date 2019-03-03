import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
  private List<String> report = new ArrayList<>();

  List<String> generateReport(String input) throws SQLException {
    //case input -> run method for desired report

    switch (input) {
      case "1": {
        report.add(String.valueOf(returnTotalOrdersNumber()));
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
