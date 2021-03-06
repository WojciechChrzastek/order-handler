class SqlQueries {
  static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS REQUESTS " +
          "(" +
          "ID SERIAL PRIMARY KEY, " +
          "CLIENT_ID CHAR(6), " +
          "REQUEST_ID BIGINT UNSIGNED, " +
          "NAME VARCHAR(255) CHARSET utf8, " +
          "QUANTITY INT UNSIGNED, " +
          "PRICE DECIMAL (19,2) UNSIGNED " +
          ")";
  static String DELETE_TABLE = "DROP TABLE IF EXISTS REQUESTS";
  static String INSERT = "INSERT INTO REQUESTS" +
          " (CLIENT_ID, REQUEST_ID, NAME, QUANTITY, PRICE)" +
          " VALUES (?, ?, ?, ?, ?)";

  static String RETURN_TOTAL_ORDERS_NUMBER =
          "SELECT COUNT(*) AS TOTAL_ORDERS_NUMBER FROM REQUESTS";
  static String RETURN_TOTAL_ORDERS_NUMBER_OF_GIVEN_CLIENT_ID =
          "SELECT COUNT(*) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
  static String RETURN_TOTAL_VALUE_OF_ORDERS =
          "SELECT SUM(PRICE*QUANTITY) AS TOTAL_VALUE_OF_ORDERS FROM REQUESTS";
  static String RETURN_TOTAL_VALUE_OF_ORDERS_OF_GIVEN_CLIENT_ID =
          "SELECT SUM(PRICE * QUANTITY) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
  static String RETURN_LIST_OF_ALL_ORDERS =
          "SELECT * FROM REQUESTS";
  static String RETURN_LIST_OF_ALL_ORDERS_OF_GIVEN_CLIENT_ID =
          "SELECT * FROM REQUESTS WHERE CLIENT_ID = ?";
  static String RETURN_AVERAGE_ORDER_VALUE =
          "SELECT AVG(QUANTITY * PRICE) AS AVERAGE_ORDER_VALUE FROM REQUESTS";
  static String RETURN_AVERAGE_ORDER_VALUE_OF_GIVEN_CLIENT_ID =
          "SELECT AVG(QUANTITY * PRICE) AS ? FROM REQUESTS WHERE CLIENT_ID = ?";
}
