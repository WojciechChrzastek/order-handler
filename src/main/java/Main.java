public class Main {
  public static void main(String[] args) throws Exception {
//    UserService userService = new UserService();
//    userService.run();
    InMemoryDbHandler inMemoryDbHandler = new InMemoryDbHandler();
    inMemoryDbHandler.setDatabase();
    inMemoryDbHandler.createTable();
    inMemoryDbHandler.insert();

    ReportHandler reportHandler = new ReportHandler();
    reportHandler.printReportToConsole(inMemoryDbHandler.returnListOfAllOrders());

    inMemoryDbHandler.closeDb();
    System.exit(0);
  }
}
