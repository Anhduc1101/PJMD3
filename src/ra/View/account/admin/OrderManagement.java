package ra.View.account.admin;

import ra.View.account.user.UserOrderHistory;
import ra.config.Config;
import ra.config.Utils;
import ra.config.Validate;
import ra.model.Order;
import ra.model.OrderStatus;
import ra.service.order.IOrderService;
import ra.service.order.OrderServiceIMPL;
import java.util.List;
import static ra.config.Color.*;

public class OrderManagement {
    IOrderService orderService = new OrderServiceIMPL();
    public void menu() {
                do {
                    System.out.println("\033[1;94m╔════════════════ QUẢN LÝ ĐƠN HÀNG ═════════════════╗");
                    System.out.println("\033[1;94m║" + RESET + "               " + Utils.getCurrentDateTime() + " \033[1;94m                ║");
                    System.out.println("\033[1;94m║═══════════════════════════════════════════════════║" + RESET);
                    System.out.println("\033[1;94m║         \033[1;97m1. Hiển thị danh sách đơn hàng" + RESET + "\033[1;94m            ║");
                    System.out.println("\033[1;94m║         \033[1;97m2. Thay đổi trạng thái đơn hàng " + RESET + "\033[1;94m          ║");
                    System.out.println("\033[1;94m║         \033[1;97m3. Tìm kiếm đơn hàng" + RESET + "\033[1;94m                      ║");
                    System.out.println("\033[1;94m║         \033[1;97m4. Lọc đơn hàng" + RESET + "\033[1;94m                           ║");
                    System.out.println("\033[1;94m║         \033[1;97m0. Quay lại" + RESET + "\033[1;94m                               ║");
                    System.out.println("\033[1;94m╚═══════════════════════════════════════════════════╝" + RESET);
                    System.out.print("Mời lựa chọn (1/2/3/4/0): ");
                    switch (Validate.validateInt()) {
                        case 1:
                            handleDisplay();
                            break;
                        case 2:
                            handleOrderDetails();
                            break;
                        case 3:
                            handleFindOrderDetailsById();
                            break;
                        case 4:
                            handleFilterOrderDetailsByOrderStartus();
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                            break;
                    }
                } while (true);
            }

    private void handleFilterOrderDetailsByOrderStartus() {
        System.out.println("Mời chọn trạng thái đơn hàng để lọc: ");
        System.out.println("1. Đang chờ xử lý ");
        System.out.println("2. Đã xác nhận ");
        System.out.println("3. Đã bị hủy ");
        System.out.println("0. Quay lại ");
        System.out.println("Mời lựa chọn (0/1/2/3):");
        switch (Validate.validateInt()){
            case 1:
                System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                System.out.println("                                                                         \033[1;94mLỊCH SỬ ĐƠN HÀNG" + RESET);
                System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                System.out.println("| Mã đơn | Mã người đặt |   Tên người đặt   |    Số đện thoại    |       Địa chỉ       |   Tổng giá   | Mã sản phẩm | Số lượng |         Đặt lúc          |     Trạng thái    ");
                System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                for (Order order:orderService.findAll()) {
                    for (Integer idPro:order.getOrderDetails().keySet()) {
                        if (order.getOrderStatus()==OrderStatus.WAITING){
                            System.out.printf("    %-5d|      %-5d   |  %-10s       |     %-10s     |  %-10s         |    %-10s|      %-5s  |     %-5d| %-20s  |     %-14s\n",
                                    order.getOrderId(), order.getUserId(), order.getName(),order.getPhoneNumber(),order.getAddress(),order.getTotal(),idPro,order.getOrderDetails().get(idPro),order.getOrderAt(),order.getOrderStatus().getVietnameseName());
                        }
                    }
                }
                System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                break;
                case 2:
                    System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                    System.out.println("                                                                         \033[1;94mLỊCH SỬ ĐƠN HÀNG" + RESET);
                    System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                    System.out.println("| Mã đơn | Mã người đặt |   Tên người đặt   |    Số đện thoại    |       Địa chỉ       |   Tổng giá   | Mã sản phẩm | Số lượng |         Đặt lúc          |     Trạng thái    ");
                    System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                    for (Order order:orderService.findAll()) {
                        for (Integer idPro:order.getOrderDetails().keySet()) {
                            if (order.getOrderStatus()==OrderStatus.SUCCESS){
                                System.out.printf("    %-5d|      %-5d   |  %-10s       |     %-10s     |  %-10s         |    %-10s|      %-5s  |     %-5d| %-20s  |     %-14s\n",
                                        order.getOrderId(), order.getUserId(), order.getName(),order.getPhoneNumber(),order.getAddress(),order.getTotal(),idPro,order.getOrderDetails().get(idPro),order.getOrderAt(),order.getOrderStatus().getVietnameseName());
                            }
                        }
                    }
                    System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");

                    break;
                case 3:
                    System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                    System.out.println("                                                                         \033[1;94mLỊCH SỬ ĐƠN HÀNG" + RESET);
                    System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                    System.out.println("| Mã đơn | Mã người đặt |   Tên người đặt   |    Số đện thoại    |       Địa chỉ       |   Tổng giá   | Mã sản phẩm | Số lượng |         Đặt lúc          |     Trạng thái    ");
                    System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                    for (Order order:orderService.findAll()) {
                        for (Integer idPro:order.getOrderDetails().keySet()) {
                            if (order.getOrderStatus()==OrderStatus.CANCEL){
                                System.out.printf("    %-5d|      %-5d   |  %-10s       |     %-10s     |  %-10s         |    %-10s|      %-5s  |     %-5d| %-20s  |     %-14s\n",
                                        order.getOrderId(), order.getUserId(), order.getName(),order.getPhoneNumber(),order.getAddress(),order.getTotal(),idPro,order.getOrderDetails().get(idPro),order.getOrderAt(),order.getOrderStatus().getVietnameseName());
                            }
                        }
                    }
                    System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
                    break;
                case 0:
                return;
            default:
                System.out.println("Không có lựa chọn này!");
                break;
        }
    }

    private void handleFindOrderDetailsById() {
        handleDisplay();
        System.out.println("Nhập vào mã đơn hàng bạn muốn tìm kiếm: ");
        int findId=Validate.validateInt();
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        System.out.println("                                                                         \033[1;94mLỊCH SỬ ĐƠN HÀNG" + RESET);
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        System.out.println("| Mã đơn | Mã người đặt |   Tên người đặt   |    Số đện thoại    |       Địa chỉ       |   Tổng giá   | Mã sản phẩm | Số lượng |         Đặt lúc          |     Trạng thái    ");
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        for (Order order:orderService.findAll()) {
            for (Integer idPro:order.getOrderDetails().keySet()) {
                if (order.getOrderId()==findId){
                System.out.printf("    %-5d|      %-5d   |  %-10s       |     %-10s     |  %-10s         |    %-10s|      %-5s  |     %-5d| %-20s  |     %-14s\n",
                        order.getOrderId(), order.getUserId(), order.getName(),order.getPhoneNumber(),order.getAddress(),order.getTotal(),idPro,order.getOrderDetails().get(idPro),order.getOrderAt(),order.getOrderStatus().getVietnameseName());
                }
            }
        }
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
    }

    public void handleOrderDetails() {
        orderService.findAll().sort((o1, o2) -> o2.getOrderAt().compareTo(o1.getOrderAt()));
        for (Order o : orderService.findAll()) {
            if (o.getOrderStatus() == OrderStatus.WAITING) {
                System.out.println(o);
            }
        }
        System.out.println("Chọn mã đơn hàng bạn muốn xác nhận: ");
        int idChange = Validate.validateInt();
        Order orderById = orderService.findById(idChange);
        if (orderById == null) {
            System.out.println(RED + "Không có đơn hàng này!" + RESET);
            return;
        } else if (orderById.getOrderStatus() != OrderStatus.WAITING) {
            System.out.println("Đơn hàng đã " + orderById.getOrderStatus().getVietnameseName());
            return;
        } else {
            System.out.println("MÃ bạn chọn là: " + orderById);
            System.out.println("Bạn có muốn thay đổi trạng thái đơn hàng này không? ");
            System.out.println("1. Xác nhận đơn hàng");
            System.out.println("2. Hủy đơn hàng");
            System.out.println("0. Quay lại");
            System.out.println("Mời lựa chọn: ");
            switch (Validate.validateInt()) {
                case 1:
                    System.out.println("Trạng thái đơn hàng hiện tại là: " + orderById.getOrderStatus().getVietnameseName());
                    orderById.setOrderStatus(OrderStatus.CONFIRM);
                    System.out.println( "Trạng thái đơn hàng đã được chuyển thành " +orderById.getOrderStatus().getVietnameseName() );
                    orderService.updateData();
                    break;
                case 2:
                    System.out.println("Trạng thái đơn hàng hiện tại là: " + orderById.getOrderStatus().getVietnameseName());
                    orderById.setOrderStatus(OrderStatus.CANCEL);
                    System.out.println("Trạng thái đơn hàng đã được chuyển thành " +orderById.getOrderStatus().getVietnameseName() );
                    orderService.updateData();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không có lựa chọn này! ");
                    break;
            }
        }
    }
    public void handleDisplay(){
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        System.out.println("                                                                         \033[1;94mLỊCH SỬ ĐƠN HÀNG" + RESET);
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        System.out.println("| Mã đơn | Mã người đặt |   Tên người đặt   |    Số đện thoại    |       Địa chỉ       |   Tổng giá   | Mã sản phẩm | Số lượng |         Đặt lúc          |     Trạng thái    ");
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
        List<Order> orders= orderService.findAll();
        for (Order order:orders) {
            for (Integer idPro:order.getOrderDetails().keySet()) {
                System.out.printf("    %-5d|      %-5d   |  %-10s       |     %-10s     |  %-10s         |    %-10s|      %-5s  |     %-5d| %-20s  |     %-14s\n",
                        order.getOrderId(), order.getUserId(), order.getName(),order.getPhoneNumber(),order.getAddress(),order.getTotal(),idPro,order.getOrderDetails().get(idPro),order.getOrderAt(),order.getOrderStatus().getVietnameseName());
            }
        }
        System.out.println("+--------+--------------+-------------------+--------------------+---------------------+--------------+-------------+----------+--------------------------+-------------------+");
    }
}
