import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

class Parser {

  HashMap parse() {

    String fileCsv = "requests_test_file.csv";
    String line;
    String[] requestArray;
    HashMap<Integer, Request> requestMap = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(fileCsv))) {
      br.readLine();
      int n = 0;
      while ((line = br.readLine()) != null) {

        requestArray = line.split(",");

        Request request = new Request(Integer.parseInt(requestArray[0]),
                Integer.parseInt(requestArray[1]),
                requestArray[2],
                Integer.parseInt(requestArray[3]),
                Double.parseDouble(requestArray[4]));

        requestMap.put(n, request);
        n++;
      }
    } catch (IOException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    return requestMap;
  }
}
