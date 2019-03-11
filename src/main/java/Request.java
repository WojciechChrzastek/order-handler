import java.math.BigDecimal;

class Request {
  private String clientId;
  private long requestId;
  private String name;
  private int quantity;
  private BigDecimal price;

  Request(String clientId, long requestId, String name, int quantity, BigDecimal price) {
    this.clientId = clientId;
    this.requestId = requestId;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

  public Request() {
  }

  String getClientId() {
    return clientId;
  }

  void setClientId(String clientId) {
    this.clientId = clientId;
  }

  long getRequestId() {
    return requestId;
  }

  void setRequestId(long requestId) {
    this.requestId = requestId;
  }

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  int getQuantity() {
    return quantity;
  }

  void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  BigDecimal getPrice() {
    return price;
  }

  void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Request request = (Request) o;

    if (requestId != request.requestId) return false;
    if (quantity != request.quantity) return false;
    if (clientId != null ? !clientId.equals(request.clientId) : request.clientId != null) return false;
    if (name != null ? !name.equals(request.name) : request.name != null) return false;
    return price != null ? price.equals(request.price) : request.price == null;
  }

  @Override
  public int hashCode() {
    int result = clientId != null ? clientId.hashCode() : 0;
    result = 31 * result + (int) (requestId ^ (requestId >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + quantity;
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Request{" +
            "clientId='" + clientId + '\'' +
            ", requestId=" + requestId +
            ", name='" + name + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            '}';
  }
}
