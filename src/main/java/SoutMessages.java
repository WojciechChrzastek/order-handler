final class SoutMessages {
  static String WELCOME_HEADER = "***** Welcome *****";
  static String SELECT_ACTION = "Select one of the following options:\n" +
          "1 - Add data from a file to the database\n" +
          "2 - Generate report\n" +
          "q - quit the program\n" +
          "Type chosen option and accept it using 'enter' button: ";

  static String GOODBYE_FOOTER = "***** Goodbye *****";
  static String ASK_FOR_FILE_PATH =
          "Please type a path to the file you want to parse and save its content to the database." +
                  "Accept the path using 'enter' button: ";
  static String PARSE_SUCCESS = ">>> Data successfully added to the database <<<";
  static String FILE_NOT_FOUND = "!!! Valid file has not been found. Please enter a valid file path. !!!";
  static String SELECT_REPORT = "Which report do you want to generate?\n" +
          "1 - total orders number\n" +
          "2 - total orders number by client id\n" +
          "3 - total value of orders\n" +
          "4 - total value of orders by client id\n" +
          "5 - list of all orders\n" +
          "6 - list of all orders by client id\n" +
          "7 - average order value\n" +
          "8 - average order value by client id\n" +
          "x - go back to previous menu\n" +
          "Type chosen option and accept it using 'enter' button: ";
  static String WRONG_INPUT = "Wrong input. Please type a valid input.";
  static String SELECT_REPORT_TYPE = "Select one of the following options\n" +
          "1 - print report to the console" +
          "2 - save report as a .csv file" +
          "x - go back to previous menu" +
          "Type chosen option and accept it using 'enter' button: ";

  private SoutMessages() {

  }
}
