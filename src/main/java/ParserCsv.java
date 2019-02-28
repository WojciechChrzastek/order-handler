import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ParserCsv implements Parser {
  private List<Request> requestList = null;
  private Request request = null;

  List<Request> getRequestList() {
    return requestList;
  }

  @Override
  public List parse(String file) {
    String line;
    String[] requestArray;
    requestList = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      br.readLine();
      while ((line = br.readLine()) != null) {
        requestArray = line.split(",");

        request = new Request();

        request.setClientId(Integer.parseInt(requestArray[0]));
        request.setRequestId(Integer.parseInt(requestArray[1]));
        request.setName(requestArray[2]);
        request.setQuantity(Integer.parseInt(requestArray[3]));
        request.setPrice(Double.parseDouble(requestArray[4]));

        requestList.add(request);
      }
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    return requestList;
  }
}
