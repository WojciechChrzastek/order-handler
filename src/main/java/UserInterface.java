import java.util.Scanner;

public class UserInterface {
  void run() {
    
  }
  void selectAction() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Which report do you want to generate?\n" +
            "1 - total orders number" +
            "2 - total orders number by client id" +
            "3 - total value of orders " +
            "4 - total value of orders by client id" +
            "5 - list of all orders" +
            "6 - list of all orders by client id" +
            "7 - average order value" +
            "8 - average order value by client id" +
            "x - go back to previous menu" +
            "Type chosen option and accept it using 'enter' button: ");
    scanner.nextInt();
    System.out.println("Select one of the following options\n" +
            "1 - print report to the console" +
            "2 - save report as a .csv file" +
            "x - go back to previous menu" +
            "Type chosen option and accept it using 'enter' button: ");
  }
}
