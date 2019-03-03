import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ParserCsv implements Parser {
  private List<Request> requestsList = null;
  private Request request = null;

  public List<Request> getRequestsList() {
    return requestsList;
  }

  @Override
  public List parse(String file) {
    String line;
    String[] requestsArray;
    requestsList = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      br.readLine();
      while ((line = br.readLine()) != null) {
        requestsArray = line.split(",");

        request = new Request();

        request.setClientId(Integer.parseInt(requestsArray[0]));
        request.setRequestId(Integer.parseInt(requestsArray[1]));
        request.setName(requestsArray[2]);
        request.setQuantity(Integer.parseInt(requestsArray[3]));
        request.setPrice(Double.parseDouble(requestsArray[4]));

        requestsList.add(request);
      }
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    return requestsList;
  }
}
