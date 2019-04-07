import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ParserCsvListReader implements Parser {
  private List<Request> requestsList = null;

  @Override
  public List parse(String file) {
    requestsList = new ArrayList<>();

    CellProcessor[] allProcessors = new CellProcessor[]{
            new Optional(), //clientIt
            new Optional(new ParseLong()), // requestId
            new Optional(), // name
            new Optional(new ParseInt()), // quantity
            new Optional(new ParseBigDecimal())}; // price

    CellProcessor[] noRequestIdProcessors = new CellProcessor[]{
            allProcessors[0], // clientIt
            allProcessors[2], // name
            allProcessors[3], // quantity
            allProcessors[4]}; // price

    ICsvListReader listReader = null;
    try {
      listReader = new CsvListReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);

      listReader.getHeader(true); // skip the header (can't be used with CsvListReader)

      while ((listReader.read()) != null) {

        // use different processors depending on the number of columns
        CellProcessor[] processors;
        if (listReader.length() == noRequestIdProcessors.length) {
          processors = noRequestIdProcessors;
        } else {
          processors = allProcessors;
        }

        List<Object> objectList = listReader.executeProcessors(processors);

        Request request = new Request();
        request.setClientId(objectList.get(0).toString());
        request.setRequestId(Long.parseLong(objectList.get(1).toString()));
        request.setName(objectList.get(2).toString());
        request.setQuantity(Integer.parseInt(objectList.get(3).toString()));
        request.setPrice(new BigDecimal(objectList.get(4).toString()));
        requestsList.add(request);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (listReader != null) {
        try {
          listReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return requestsList;
  }

  @Override
  public List<Request> getRequestsList() {
    return requestsList;
  }

  @Override
  public boolean validate(String line, int lineNumber) {
    return false;
  }
}
