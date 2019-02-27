public class Request {
  private int clientId;
  private int requestId;
  private String name;
  private int quantity;
  private double price;

  Request(int clientId, int requestId, String name, int quantity, double price) {
    this.clientId = clientId;
    this.requestId = requestId;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

  public Request() {
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public int getRequestId() {
    return requestId;
  }

  public void setRequestId(int requestId) {
    this.requestId = requestId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Request request = (Request) o;

    if (clientId != request.clientId) return false;
    if (requestId != request.requestId) return false;
    if (quantity != request.quantity) return false;
    if (Double.compare(request.price, price) != 0) return false;
    return name != null ? name.equals(request.name) : request.name == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = clientId;
    result = 31 * result + requestId;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + quantity;
    temp = Double.doubleToLongBits(price);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "Request{" +
            "clientId=" + clientId +
            ", requestId=" + requestId +
            ", name='" + name + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            '}';
  }
}
