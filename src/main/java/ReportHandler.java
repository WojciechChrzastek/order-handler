import java.util.List;

public class ReportHandler {
  public ReportHandler() {
  }

  void printReportToConsole(List<String> report) {
    for (String s : report) {
      System.out.println(s);
    }
  }

  void saveReportToCsvFile() {

  }

  @Override
  public String toString() {
    return "ReportHandler{}";
  }
}
