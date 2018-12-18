package exchangetask;

import com.google.gson.Gson;

public class Runner {

    public static void main(String[] args) {
        Gson json = new Gson();
        ImplementationBasic perform = new ImplementationBasic();

        Order orderBuy1 = new Order(false, 50, 100);
        Order orderBuy2 = new Order(false, 80, 120);
        Order orderBuy3 = new Order(false, 70, 100);

        perform.send(orderBuy1);
        perform.send(orderBuy3);
        perform.send(orderBuy2);


        System.out.println(json.toJson(perform.orders));
        System.out.println(perform.getTotalSizeAtPrice(50,true));
        System.out.println(perform.modify(orderBuy3.getId(),100, 100));
        System.out.println(perform.cancel(orderBuy2.getId()));
        System.out.println(json.toJson(perform.orders));
        System.out.println(perform.getLowestSellPrice());

    }
}
