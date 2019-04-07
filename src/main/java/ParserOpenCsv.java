import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class ParserOpenCsv implements Parser {
  private List<Request> requestsList = null;

  @Override
  public boolean validate(String line, int lineNumber) {
    return FileValidator.validate(line, lineNumber);
  }

  @Override
  public List<Request> getRequestsList() {
    return requestsList;
  }

  @Override
  public List parse(String file) {
    requestsList = new ArrayList<>();

    try (CSVReader reader = new CSVReader(new FileReader(file))) {
      reader.readNext();
      String[] line;
      while ((line = reader.readNext()) != null) {
        Request request = new Request();
        request.setClientId(line[0]);
        request.setRequestId(Long.parseLong(line[1]));
        request.setName(line[2]);
        request.setQuantity(Integer.parseInt(line[3]));
        request.setPrice(new BigDecimal(line[4]));
        requestsList.add(request);
      }
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    return requestsList;
  }

  List parseTestFiles(String file) {
    List<String[]> list = new ArrayList<>();
    CSVReader reader;

    try {
      reader = new CSVReader(new FileReader(file));
      reader.readNext();
      String[] line;
      while ((line = reader.readNext()) != null) {
        list.add(line);
      }
      reader.close();
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    return list;
  }
}
