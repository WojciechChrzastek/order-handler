import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class ParserCsv implements Parser {
  private List<Request> requestsList = null;
  private int lineNumber = 0;

  public List<Request> getRequestsList() {
    return requestsList;
  }

  @Override
  public boolean validate(String line, int lineNumber) {
    return FileValidator.validate(line, lineNumber);
  }

  @Override
  public List parse(String file) {
    String line;
    String[] requestsArray;
    requestsList = new ArrayList<>();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
      br.readLine();
      while ((line = br.readLine()) != null) {
        lineNumber++;
        if (validate(line, lineNumber)) {
          requestsArray = line.split(",");
          Request request = new Request();
          request.setClientId(requestsArray[0]);
          request.setRequestId(Long.parseLong(requestsArray[1]));
          request.setName(requestsArray[2]);
          request.setQuantity(Integer.parseInt(requestsArray[3]));
          request.setPrice(new BigDecimal(requestsArray[4]));
          requestsList.add(request);
        }
      }
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          System.out.println("Object not found");
          e.printStackTrace();
        }
      }
    }
    return requestsList;
  }
}
