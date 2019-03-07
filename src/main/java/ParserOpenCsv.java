import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ParserOpenCsv implements Parser {
  private List<Request> requestsList = null;
  private Request request = null;

  @Override
  public List<Request> getRequestsList() {
    return requestsList;
  }

  @Override
  public List parse(String file) {
    requestsList = new ArrayList<>();
    CSVReader reader = null;

    try {
      reader = new CSVReader(new FileReader(file));
      reader.readNext();
      String[] line;
      while ((line = reader.readNext()) != null) {

        request = new Request();
        request.setClientId(Integer.parseInt(line[0]));
        request.setRequestId(Integer.parseInt(line[1]));
        request.setName(line[2]);
        request.setQuantity(Integer.parseInt(line[3]));
        request.setPrice(Double.parseDouble(line[4]));
        requestsList.add(request);

      }
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    return requestsList;
  }

  public List parseTestFiles(String file) {
    List<String[]> list = new ArrayList<>();
    CSVReader reader = null;

    try {
      reader = new CSVReader(new FileReader(file));
      reader.readNext();
      String[] line;
      while ((line = reader.readNext()) != null) {
        list.add(line);
      }
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    return list;
  }
}
