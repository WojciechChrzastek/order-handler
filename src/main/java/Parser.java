import java.util.List;

public interface Parser {
  List parse(String file);

  List<Request> getRequestsList();

  boolean validate(String line, int lineNumber);
}
