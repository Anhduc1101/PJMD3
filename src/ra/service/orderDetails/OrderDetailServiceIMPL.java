package ra.service.orderDetails;

import ra.config.Config;
import ra.model.OrderDetails;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailServiceIMPL implements IOderDetailService {
    static Config<List<OrderDetails>> config = new Config<>();
    public static List<OrderDetails> orderDetailsList;

    static {
        orderDetailsList = config.readFile(Config.URL_ORDERDETAILS);
        if (orderDetailsList == null) {
            orderDetailsList = new ArrayList<>();
        }
    }

    @Override
    public List findAll() {
        return orderDetailsList;
    }

    @Override
    public void save(OrderDetails orderDetails) {

    }


    @Override
    public void delete(int id) {
        orderDetailsList.remove(findById(id));
    }

    @Override
    public OrderDetails findById(int id) {
        for (OrderDetails orderDetails : orderDetailsList) {
            if (orderDetails.getOrderId() == id) {
                return orderDetails;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (OrderDetails orderDetails : orderDetailsList) {
            if (orderDetails.getOrderId() > idMax) {
                idMax = orderDetails.getOrderId();
            }
        }
        return idMax + 1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_ORDERDETAILS, orderDetailsList);
    }
}
