import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ParserCsv {

  private List<Request> requestList = null;

  List<Request> getRequestList() {
    return requestList;
  }

  List parse() {

    String fileCsv = "requests_test_file.csv";
    String line;
    String[] requestArray;
    requestList = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(fileCsv))) {
      br.readLine();
      while ((line = br.readLine()) != null) {

        requestArray = line.split(",");

        Request request = new Request(Integer.parseInt(requestArray[0]),
                Integer.parseInt(requestArray[1]),
                requestArray[2],
                Integer.parseInt(requestArray[3]),
                Double.parseDouble(requestArray[4]));

        requestList.add(request);
      }
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    return requestList;
  }
}
