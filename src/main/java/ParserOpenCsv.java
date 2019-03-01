import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ParserOpenCsv implements Parser {
  private List<Request> requestList = null;
  private Request request = null;

  @Override
  public List<Request> getRequestList() {
    return requestList;
  }

  @Override
  public List parse(String file) {
    requestList = new ArrayList<>();
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
        requestList.add(request);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return requestList;
  }
}
