package ra.View.account.user;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Order;
import ra.model.Users;
import ra.service.order.IOrderService;
import ra.service.order.OrderServiceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import static ra.config.Color.RED;
import static ra.config.Color.RESET;

public class UserOrderHistory {
    IOrderService orderService = new OrderServiceIMPL();

    public void menu() {
        do {
            System.out.println("**********************MENU************************");
            System.out.println("1. Lịch sử đặt hàng");
            System.out.println("2. Hủy");
            System.out.println("0. Thoát");
            System.out.print("Mời lựa chọn (1/2/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    handleShowHistory();
                    break;
                case 2:
                    handleCancelOrder();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void handleCancelOrder() {
        System.out.println("Nhập mã đơn bạn muốn hủy: ");
        int cancelId = Validate.validateInt();
//        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
//        System.out.println("                                                                         \033[1;94mLỊCH SỬ ĐƠN HÀNG" + RESET);
//        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
//        System.out.println("| Mã đơn | Mã người đặt |   Tên người đặt   |    Số đện thoại    |       Địa chỉ       |   Tổng giá   | Mã sản phẩm | Số lượng |         Đặt lúc          |     Trạng thái    ");
//        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        for (Order order : orderService.findAll()) {
            for (Integer idPro : order.getOrderDetails().keySet()) {
                if (order.getOrderId() == cancelId) {
                    handleShowHistory();
                    System.out.println("Bạn có chắc chắn muốn hủy đơn không? ");
                    System.out.println("1. Có");
                    System.out.println("2. Không");
                    System.out.println("Mời lựa chọn (0/1/2): ");
                    switch (Validate.validateInt()) {
                        case 1:
                            handleShowHistory();
                            break;
                        case 2:
                            return;
                        default:
                            System.out.println("Không có lựa chọn này! ");
                            break;
                    }
                }else {
                    System.out.println(RED+"Không có lựa chọn này! "+RESET);
                    return;
                }
//                System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
            }
        }
    }

    public void handleShowHistory() {
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        System.out.println("                                                                         \033[1;94mLỊCH SỬ ĐƠN HÀNG" + RESET);
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        System.out.println("| Mã đơn | Mã người đặt |   Tên người đặt   |    Số đện thoại    |       Địa chỉ       |   Tổng giá   | Mã sản phẩm | Số lượng |         Đặt lúc          |     Trạng thái    ");
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        List<Order> orders = orderService.findAll().stream().filter(o -> o.getUserId() == new Config<Users>().readFile(Config.URL_USER_LOGIN).getId()).collect(Collectors.toList());
        for (Order order : orders) {
            for (Integer idPro : order.getOrderDetails().keySet()) {
                System.out.printf("    %-5d|      %-5d   |  %-10s       |     %-10s     |  %-10s         |    %-10s|      %-5s  |     %-5d| %-20s  |     %-14s\n",
                        order.getOrderId(), order.getUserId(), order.getName(), order.getPhoneNumber(), order.getAddress(), order.getTotal(), idPro, order.getOrderDetails().get(idPro), order.getOrderAt(), order.getOrderStatus().getVietnameseName());
            }
        }
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
    }
}
