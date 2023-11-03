package ra.service.order;

import ra.config.Config;
import ra.model.Order;

import java.util.List;

public class OrderServiceIMPL implements IOrderService{
    static Config<List<Order>> config=new Config<>();
    public static List<Order> orderList;
    static {
        orderList=config.readFile(Config.URL_ORDER);
    }
    @Override
    public List<Order> findAll() {
        return orderList;
    }

    @Override
    public void save(Order order) {
if (findById(order.getOrderId())==null){
    orderList.add(order);
    updateData();
}else {
    orderList.set(orderList.indexOf(order),order);
    updateData();
}
    }

    @Override
    public void delete(int id) {
orderList.remove(findById(id));
updateData();
    }

    @Override
    public Order findById(int id) {
        for (Order order:orderList) {
            if (order.getOrderId()==id){
                return order;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax=0;
        for (Order order:orderList) {
            if (order.getOrderId()>idMax){
                idMax=order.getOrderId();
            }
        }
        return idMax+1;
    }

    @Override
    public void updateData() {
config.writeFile(Config.URL_ORDER,orderList);
    }
}
