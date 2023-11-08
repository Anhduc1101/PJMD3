package ra.View.account.user;

import ra.config.Config;
import ra.config.Utils;
import ra.config.Validate;
import ra.model.*;
import ra.service.cart.CartServiceIMPL;
import ra.service.cart.ICartService;
import ra.service.order.IOrderService;
import ra.service.order.OrderServiceIMPL;
import ra.service.product.IProductService;
import ra.service.product.ProductServiceIMPL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ra.config.Color.*;

public class UserOrderHistory {
    IOrderService orderService = new OrderServiceIMPL();
    ICartService cartService = new CartServiceIMPL();
    IProductService productService = new ProductServiceIMPL();

    Cart cart = cartService.findCartByUserLogin();

    public void menu() {
        do {
            System.out.println("\033[1;94m╔══════════ LỊCH SỬ ĐƠN HÀNG  ═════════╗");
            System.out.println("\033[1;94m║" + RESET + "         " + Utils.getCurrentDateTime() + " \033[1;94m         ║");
            System.out.println("\033[1;94m║══════════════════════════════════════║" + RESET);
            System.out.println("\033[1;94m║         \033[1;97m1. Lịch sử đặt hàng" + RESET + "\033[1;94m          ║");
            System.out.println("\033[1;94m║         \033[1;97m2. Hủy đơn" + RESET + "\033[1;94m                   ║");
            System.out.println("\033[1;94m║         \033[1;97m0. Quay lại" + RESET + "\033[1;94m                  ║");
            System.out.println("\033[1;94m╚══════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (1/2/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    handleShowHistory();
                    break;
                case 2:
                    handleCancelOrder();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }


    private void handleCancelOrder() {
        System.out.println("Nhập mã đơn bạn muốn hủy: ");
        int cancelId = Validate.validateInt();
        boolean isExist = false;
        for (Order order : orderService.findAll()) {
            if (order.getOrderId() == cancelId) {
                handleShowHistory();
                isExist = true;
                System.out.println("Bạn có chắc chắn muốn hủy đơn không? ");
                System.out.println("1. Có");
                System.out.println("2. Không");
                System.out.println("Mời lựa chọn (1/2): ");
                switch (Validate.validateInt()) {
                    case 1:
                        if (order.getOrderStatus() == OrderStatus.WAITING) {
                            for (int idPro: order.getOrderDetails().keySet()){
                                Product product = productService.findById(idPro);
                                if (product.getProductId()==idPro){
                                    order.setOrderStatus(OrderStatus.CANCEL);
                                    product.setStock(product.getStock() + order.getOrderDetails().get(idPro));
                                    productService.save(product);
                                    orderService.save(order);
                                }
                            }
                            System.out.println(GREEN + "Hủy đơn thành công" + RESET);
                        } else {
                            System.out.println(RED + "Bạn không thể hủy đơn này " + RESET);
                        }
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                        break;
                }
            }
        }
        if (!isExist) {
            System.out.println(RED + "Không có mã đơn hàng này! " + RESET);
        }
    }

    public void handleShowHistory() {
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        System.out.println("                                                                         \033[1;94mLỊCH SỬ ĐƠN HÀNG" + RESET);
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        System.out.println("| Mã đơn | Mã người đặt |   Tên người đặt   |    Số đện thoại    |       Địa chỉ       |   Tổng giá   | Mã sản phẩm | Số lượng |         Đặt lúc          |     Trạng thái    ");
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
//        List<Order> orders = orderService.findAll().stream().filter(o -> o.getUserId() == new Config<Users>().readFile(Config.URL_USER_LOGIN).getId()).collect(Collectors.toList());
        for (Order order : orderService.findAll()) {
            if (order.getUserId() == new Config<Users>().readFile(Config.URL_USER_LOGIN).getId()) {
                for (Integer idPro : order.getOrderDetails().keySet()) {
                    System.out.printf("    %-5d|      %-5d   |  %-10s       |     %-10s     |  %-10s         |    %-10s|      %-5s  |     %-5d| %-20s  |     %-14s\n",
                            order.getOrderId(), order.getUserId(), order.getName(), order.getPhoneNumber(), order.getAddress(), order.getTotal(), idPro, order.getOrderDetails().get(idPro), order.getOrderAt(), order.getOrderStatus().getVietnameseName());
                }
            }
        }
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
    }
}
