import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ParserCsvListReader implements Parser {
  private List<Request> requestsList = null;

  @Override
  public List parse(String file) {

    final CellProcessor[] allProcessors = new CellProcessor[] { new UniqueHashCode(), // customerNo (must be unique)
            new NotNull(), // firstName
            new NotNull(), // lastName
            new ParseDate("dd/MM/yyyy") }; // birthDate

    final CellProcessor[] noBirthDateProcessors = new CellProcessor[] { allProcessors[0], // customerNo
            allProcessors[1], // firstName
            allProcessors[2] }; // lastName

    ICsvListReader listReader = null;
    try {
      listReader = new CsvListReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);

      listReader.getHeader(true); // skip the header (can't be used with CsvListReader)

      while( (listReader.read()) != null ) {

        // use different processors depending on the number of columns
        final CellProcessor[] processors;
        if( listReader.length() == noBirthDateProcessors.length ) {
          processors = noBirthDateProcessors;
        } else {
          processors = allProcessors;
        }

        final List<Object> customerList = listReader.executeProcessors(processors);
        System.out.println(String.format("lineNo=%s, rowNo=%s, columns=%s, customerList=%s",
                listReader.getLineNumber(), listReader.getRowNumber(), customerList.size(), customerList));
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if( listReader != null ) {
        try {
          listReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }


    return null;
  }












  @Override
  public List<Request> getRequestsList() {
    return requestsList;
  }
}
