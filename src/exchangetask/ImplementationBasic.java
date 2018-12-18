package exchangetask;

import java.util.Comparator;
import java.util.LinkedList;

public class ImplementationBasic implements ExchangeInterface, QueryInterface, AdvancedExchangeInterface {

    LinkedList<Order> orders = new LinkedList<>();
    private Comparator<Order> byPrice = (o1, o2) -> o1.getPrice().compareTo(o2.getPrice());
    private Comparator<Order> bySize = (o1, o2) -> o1.getSize().compareTo(o2.getSize());


    @Override
    public boolean modify(long orderId, int newPrice, int newSize) {
        Order order=null;
        for (Order orderTemp : orders) {
            if(orderTemp.getId()==orderId){
                order=orderTemp;
            }
        }
        if(order==null||order.getSize()<=0){
            try {
                throw new RequestRejectedException();
            }catch (RequestRejectedException e){
                System.out.println(e);
                e.getMessage();
                return false;
            }
        }else{
            order.setPrice(newPrice);
            order.setSize(newSize);
            return true;
        }
    }

    @Override
    public void send(Order order) {

        if (order.getSize() <= 0 || order.getPrice() <= 0) {
            try {
                throw new RequestRejectedException();
            } catch (RequestRejectedException e) {
                System.out.println(e);
                e.getMessage();
            }
        } else {
            if (!orders.contains(order)) {
                orders.add(order);
            }

            if (order.isSideIsBuy()) {
                orders.sort(byPrice.thenComparing(bySize.reversed()));
                for (int i = 0; i < orders.size() && order.getSize() > 0; i++) {
                    if (!(orders.get(i).isSideIsBuy()) && orders.get(i).getPrice() <= order.getPrice()
                            && orders.get(i).getSize() >= order.getSize()) {
                        orders.get(i).setSize(orders.get(i).getSize() - order.getSize());
                        order.setSize(0);
                    } else if (!(orders.get(i).isSideIsBuy()) && orders.get(i).getPrice() <= order.getPrice()
                            && orders.get(i).getSize() <= order.getSize()) {
                        order.setSize(order.getSize() - orders.get(i).getSize());
                        orders.get(i).setSize(0);
                    }
                }

            } else {
                orders.sort(byPrice.reversed().thenComparing(bySize.reversed()));
                for (int i = 0; i < orders.size() && order.getSize() > 0; i++) {
                    if (orders.get(i).isSideIsBuy() && orders.get(i).getPrice() >= order.getPrice()
                            && orders.get(i).getSize() >= order.getSize()) {
                        orders.get(i).setSize(orders.get(i).getSize() - order.getSize());
                        order.setSize(0);
                    } else if (orders.get(i).isSideIsBuy() && orders.get(i).getPrice() >= order.getPrice()
                            && orders.get(i).getSize() <= order.getSize()) {
                        order.setSize(order.getSize() - orders.get(i).getSize());
                        orders.get(i).setSize(0);
                    }

                }

            }

        }
    }

    @Override
    public boolean cancel(long orderId) {
        Order order=null;
        for (Order orderTemp : orders) {
            if(orderTemp.getId()==orderId){
                order=orderTemp;
            }
        }
        if(order==null||order.getSize()<=0){
            try {
                throw new RequestRejectedException();
            }catch (RequestRejectedException e){
                System.out.println(e);
                e.getMessage();
                return false;
            }
        }else{
            orders.remove(order);
            return true;
        }
    }

    @Override
    public int getTotalSizeAtPrice(int price) {
        int sum=0;
        for (Order orderTemp : orders) {
            if(orderTemp.getPrice()==price){
                sum+=orderTemp.getSize();
            }
        }
        return sum;
    }

    public int getTotalSizeAtPrice(int price, boolean isBuy) {
        int sum=0;
        for (Order orderTemp : orders) {
            if(orderTemp.getPrice()==price&&orderTemp.isSideIsBuy()==isBuy){
                sum+=orderTemp.getSize();
            }
        }
        return sum;
    }

    @Override
    public int getHighestBuyPrice() {
        int result=0;
        orders.sort(byPrice.reversed());
        for (Order orderTemp : orders) {
            if(orderTemp.getSize()>0&&orderTemp.isSideIsBuy()){
                result=orderTemp.getPrice();
                return result;
            }
        }
        return result;
    }

    @Override
    public int getLowestSellPrice() {
        int result=0;
        orders.sort(byPrice);
        for (Order orderTemp : orders) {
            if(orderTemp.getSize()>0&&!orderTemp.isSideIsBuy()){
                result=orderTemp.getPrice();
                return result;
            }
        }
        return result;
    }
}
