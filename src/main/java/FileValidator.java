import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileValidator {

  FileValidator() {
  }

  static boolean validate(String line, int lineNumber) {
    Pattern pattern = Pattern.compile("(.*,.*){4}");
    Matcher matcher = pattern.matcher(line);
    if (!matcher.matches()) {
      System.out.println("Missing data in line " + lineNumber + " of the file.");
    }
    return matcher.matches();
  }
}
