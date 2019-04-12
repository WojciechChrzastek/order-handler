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

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
      lineNumber = 0;
      System.out.println(SoutMessages.PARSE_SUCCESS);
    } catch (IOException e) {
      System.out.println("\n" + SoutMessages.FILE_NOT_FOUND);
      System.out.println(SoutMessages.PARSE_FAIL);
      e.printStackTrace();
    }
    return requestsList;
  }

  List parseTestFiles(String file) {
    List<String[]> list = new ArrayList<>();
    String line;
    String[] array;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      br.readLine();
      while ((line = br.readLine()) != null) {
        array = line.split(",");
        list.add(array);
      }
    } catch (IOException e) {
      System.out.println("\n" + SoutMessages.FILE_NOT_FOUND);
      System.out.println(SoutMessages.PARSE_FAIL);
      e.printStackTrace();
    }
    return list;
  }
}
