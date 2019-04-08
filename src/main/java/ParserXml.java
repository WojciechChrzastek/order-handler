import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class ParserXml extends DefaultHandler implements Parser {
  private List<Request> requestsList = null;
  private Request request = null;
  private StringBuilder data = null;
  private boolean clientId = false;
  private boolean requestId = false;
  private boolean name = false;
  private boolean quantity = false;
  private boolean price = false;

  public List<Request> getRequestsList() {
    return requestsList;
  }

  @Override
  public boolean validate(String line, int lineNumber) {
    return false;
  }

  @Override
  public List parse(String file) {

    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    requestsList = new ArrayList<>();
    try {
      SAXParser saxParser = saxParserFactory.newSAXParser();
      ParserXml handler = new ParserXml();
      saxParser.parse(new File(file), handler);
      requestsList = handler.getRequestsList();
    } catch (ParserConfigurationException | SAXException | IOException e) {
      System.out.println("\n" + SoutMessages.FILE_NOT_FOUND);
      System.out.println(SoutMessages.PARSE_FAIL);
      e.printStackTrace();
    }
    return requestsList;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    switch (qName.toLowerCase()) {
      case "request":
        request = new Request();
        if (requestsList == null) {
          requestsList = new ArrayList<>();
        }
        break;
      case "clientid":
        clientId = true;
        break;
      case "requestid":
        requestId = true;
        break;
      case "name":
        name = true;
        break;
      case "price":
        price = true;
        break;
      case "quantity":
        quantity = true;
        break;
      default:
        break;
    }
    data = new StringBuilder();
  }

  @Override
  public void endElement(String uri, String localName, String qName) {
    if (name) {
      request.setName(data.toString());
      name = false;
    } else if (clientId) {
      request.setClientId(data.toString());
      clientId = false;
    } else if (requestId) {
      request.setRequestId(Long.parseLong(data.toString()));
      requestId = false;
    } else if (quantity) {
      request.setQuantity(Integer.parseInt(data.toString()));
      quantity = false;
    } else if (price) {
      request.setPrice(new BigDecimal(data.toString()));
      price = false;
    }
    if (qName.equalsIgnoreCase("Request")) {
      requestsList.add(request);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    data.append(new String(ch, start, length));
  }
}
