import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserXml extends DefaultHandler implements Parser {
  private List<Request> requestList = null;
  private Request request = null;

  private StringBuilder data = null;

  private boolean clientId = false;
  private boolean requestId = false;
  private boolean name = false;
  private boolean quantity = false;
  private boolean price = false;

  public List<Request> getRequestList() {
    return requestList;
  }

  @Override
  public List parse(String file) {

    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    requestList = new ArrayList<>();
    try {
      SAXParser saxParser = saxParserFactory.newSAXParser();
      ParserXml handler = new ParserXml();
      saxParser.parse(new File(file), handler);

      requestList = handler.getRequestList();

    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return requestList;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (qName.equalsIgnoreCase("Request")) {
      request = new Request();
      if (requestList == null)
        requestList = new ArrayList<>();
    } else if (qName.equalsIgnoreCase("clientId")) {
      clientId = true;
    } else if (qName.equalsIgnoreCase("requestId")) {
      requestId = true;
    } else if (qName.equalsIgnoreCase("name")) {
      name = true;
    } else if (qName.equalsIgnoreCase("price")) {
      price = true;
    } else if (qName.equalsIgnoreCase("quantity")) {
      quantity = true;
    }
    data = new StringBuilder();
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (name) {
      request.setName(data.toString());
      name = false;
    } else if (clientId) {
      request.setClientId(Integer.parseInt(data.toString()));
      clientId = false;
    } else if (requestId) {
      request.setRequestId(Integer.parseInt(data.toString()));
      requestId = false;
    } else if (quantity) {
      request.setQuantity(Integer.parseInt(data.toString()));
      quantity = false;
    } else if (price) {
      request.setPrice(Double.parseDouble(data.toString()));
      price = false;
    }
    if (qName.equalsIgnoreCase("Request")) {
      requestList.add(request);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    data.append(new String(ch, start, length));
  }
}
