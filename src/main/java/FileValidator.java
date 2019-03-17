import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileValidator {

  FileValidator() {
  }

  static boolean validate(String line, int lineNumber) {
    String regex = "(.*,.*){4}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);
    boolean isMatched = matcher.matches();
    if (!isMatched) {
      System.out.println("Missing data in line " + lineNumber + " of the file.");
    }
    return isMatched;
  }
}
