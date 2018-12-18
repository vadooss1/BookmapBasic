package exchangetask;

public class Order {
    private long orderId;
    private boolean sideIsBuy;
    private Integer price;
    private Integer size;

    public Order() {
        orderId = System.nanoTime();
    }

    public Order(boolean sideIsBuy, int price, int size) {
        orderId = System.nanoTime();
        this.sideIsBuy = sideIsBuy;
        this.price = price;
        this.size = size;
    }

    public long getId() {
        return orderId;
    }

    public void setId(long orderId) {
        this.orderId = orderId;
    }

    public boolean isSideIsBuy() {
        return sideIsBuy;
    }

    public void setSideIsBuy(boolean sideIsBuy) {
        this.sideIsBuy = sideIsBuy;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
