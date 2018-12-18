package exchangetask;

public interface ExchangeInterface {
    public void send(Order order);
    public boolean cancel(long orderId) throws RequestRejectedException;
}
