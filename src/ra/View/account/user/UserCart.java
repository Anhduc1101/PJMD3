package ra.View.account.user;

import ra.config.Config;
import ra.config.Utils;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.Order;
import ra.model.Product;
import ra.model.Users;
import ra.service.cart.CartServiceIMPL;
import ra.service.cart.ICartService;
import ra.service.order.IOrderService;
import ra.service.order.OrderServiceIMPL;
import ra.service.product.IProductService;
import ra.service.product.ProductServiceIMPL;

import java.text.NumberFormat;
import java.util.*;

import static ra.config.Color.*;

public class UserCart {
    ICartService cartService = new CartServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    Cart cart = cartService.findCartByUserLogin();

    public void menu() {
        do {
            System.out.println("\033[1;94m╔════════════ TRANG GIỎ HÀNG  ════════════╗");
            System.out.println("\033[1;94m║" + RESET + "           " + Utils.getCurrentDateTime() + " \033[1;94m          ║");
            System.out.println("\033[1;94m║═════════════════════════════════════════║" + RESET);
            System.out.println("\033[1;94m║          \033[1;97m1. Danh sách sản phẩm" + RESET + "\033[1;94m          ║");
            System.out.println("\033[1;94m║          \033[1;97m2. Hiển thị giỏ hàng" + RESET + "\033[1;94m           ║");
            System.out.println("\033[1;94m║          \033[1;97m3. Thêm vào giỏ hàng" + RESET + "\033[1;94m           ║");
            System.out.println("\033[1;94m║          \033[1;97m4. Xóa sản phẩm" + RESET + "\033[1;94m                ║");
            System.out.println("\033[1;94m║          \033[1;97m5. Thay đổi số lượng" + RESET + "\033[1;94m           ║");
//            System.out.println("\033[1;94m║          \033[1;97m6. Đặt hàng" + RESET + "\033[1;94m                    ║");
            System.out.println("\033[1;94m║          \033[1;97m0. Quay lại" + RESET + "\033[1;94m                    ║");
            System.out.println("\033[1;94m╚═════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (1/2/3/4/5/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    handleDisplayProductInfo();
                    break;
                case 2:
                    handleDisplayCart();
                    break;
                case 3:
                    handleAddToCart();
                    break;
                case 4:
                    handleDeleteProductInCart();
                    break;
                case 5:
                    handleChangeQuantityOfProduct();
                    break;
//                case 6:
//                    handleCheckOut();
//                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void handleCheckOut() {
        handleDisplayCart();
        System.out.println("Bạn có muốn đặt hàng không?: ");
        System.out.println("1. Có");
        System.out.println("2. Không");
        System.out.println("Mời lựa chọn: ");
        switch (Validate.validateInt()) {
            case 1:
                if (cart.getProducts().isEmpty()) {
                    System.out.println(RED + "Giỏ hàng trống, không được đặt!" + RESET);
                    break;
                } else {
                    Order newOrder = new Order();
                    newOrder.setOrderId(orderService.getNewId());
                    newOrder.setUserId(cart.getUserId());
                    System.out.println("Nhập vào tên người nhận: ");
                    String inputName = Validate.validateString();
                    System.out.println("Nhập vào địa chỉ người nhận: ");
                    String inputAddress = Validate.validateString();
                    System.out.println("Nhập vào sô điện thoại của người nhận: ");
                    String inputPhone = Validate.validatePhoneNumber();
                    System.out.println(GREEN + "Đặt hàng thành công" + RESET);
                    cart.getProducts().clear();
                }
//                cartService.save(cart);
                break;
            case 2:
                break;
            default:
                System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                break;

        }

    }


    private void handleAddToCart() {
        handleDisplayProductInfo();
//        đặt 1 cái
        System.out.println("Chọn mã sản phẩm cần đặt(chon 0 để thoát): ");
        int idBuy = Validate.validateInt();
        if (idBuy == 0) {
            return;
        }
        if (productService.findById(idBuy) == null || !productService.findById(idBuy).isStatus() || !productService.findById(idBuy).getCategory().isStatus()) {
            System.out.println(RED + "Không tồn tại theo ID vừa nhập! " + RESET);
            return;
        }
//      int newStock = proBuy.getStock() - 1;
//            proBuy.setStock(newStock);
//            productService.save(proBuy);
        Users userLogin = new Config<Users>().readFile(Config.URL_USER_LOGIN);
        if (cart == null) {
            cart = new Cart(cartService.getNewId(), userLogin.getId(), new HashMap<>(), false);
        }
        if (cart.getProducts().containsKey(idBuy)) {
            cart.getProducts().put(idBuy, cart.getProducts().get(idBuy) + 1);
        } else {
            cart.getProducts().put(idBuy, 1);
        }
        cartService.save(cart);
//            System.out.println(cart);
        System.out.println(GREEN + "Thêm sản phẩm vào giỏ hàng thành công!" + RESET);
    }


    private void handleDeleteProductInCart() {
        handleDisplayCart();
        System.out.println("Nhập mã sản phẩm bạn muốn xóa: ");
        int deleteId = Validate.validateInt();
        Iterator<Integer> iterator = cart.getProducts().keySet().iterator();
        while (iterator.hasNext()) {
            int idPro = iterator.next();
            if (idPro == deleteId) {
                iterator.remove();
                handleDisplayCart();
                System.out.println(GREEN + "Xóa thành công" + RESET);
                cartService.updateData();
                return;
            }
        }
        cartService.save(cart);
        System.out.println(RED + "Không tìm thấy sản phẩm trong giỏ hàng" + RESET);
    }

    private void handleChangeQuantityOfProduct() {
//        double total = 0.0;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
        System.out.println("                              \033[1;94mDANH SÁCH CÁC SẢN PHẨM TRONG GIỎ HÀNG CỦA BẠN" + RESET);
        System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
        System.out.println("|  Mã sản phẩm  |      Tên sản phẩm       |     Số lượng     |       Đơn giá      |      Tạm tính      |");
        System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
        for (int idPro : cart.getProducts().keySet()) {
            Product pro = productService.findById(idPro);
            double subTotal = cart.getProducts().get(idPro) * pro.getUnitPrice();
            System.out.printf("|       %-5d   |  %-20s   |        %-5d     |       %-10s   |       %-10s   |\n",
                    idPro, pro.getProductName(), cart.getProducts().get(idPro),
                    currencyFormat.format(pro.getUnitPrice()), currencyFormat.format(subTotal));
//            total += subTotal;
        }
        System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
        System.out.printf("|                                                       \033[1;97mTổng tiền:" + RESET + "                |       \033[0;32m%-10s" + RESET + "   |\n", currencyFormat.format(totalBill()));
        System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");

        System.out.println("Nhập vào mã sản phẩm bạn muốn thay đổi số lượng sản phẩm: ");
        int idChange = Validate.validateInt();
        Product pro = productService.findById(idChange);
        if (cart.getProducts().containsKey(idChange)) {
            System.out.println("Số lượng sản phẩm hiện tại trong giỏ là: " + cart.getProducts().get(idChange));
            System.out.println("Nhập số lượng cần thay đổi: ");
            int changeNum = Validate.validateInt();
            if (changeNum > pro.getStock()) {
                System.out.println(RED + "Số lượng không đủ, trong kho chỉ còn " + RESET + pro.getStock());
                return;
            }
            cart.getProducts().put(idChange, changeNum);
//            pro.setStock(pro.getStock()-changeNum);
            System.out.println("Số lượng sau khi thay đổi là: " + cart.getProducts().get(idChange));
            System.out.println(GREEN + "Thay đổi thành công! " + RESET);
            cartService.updateData();
        } else {
            System.out.println(RED + "Sản phẩm không tồn tại trong giỏ hàng!" + RESET);
        }
    }

    public void handleDisplayCart() {
        if (cart == null || cart.getProducts().isEmpty()) {
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
            System.out.println("                              \033[1;94mDANH SÁCH CÁC SẢN PHẨM TRONG GIỎ HÀNG CỦA BẠN" + RESET);
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
            System.out.println("|  Mã sản phẩm  |      Tên sản phẩm       |     Số lượng     |       Đơn giá      |      Tạm tính      |");
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
            System.out.println(RED + "                                               Giỏ hàng trống" + RESET);
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");

            return;
        } else {
//            double total = 0.0;
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
            System.out.println("                              \033[1;94mDANH SÁCH CÁC SẢN PHẨM TRONG GIỎ HÀNG CỦA BẠN" + RESET);
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
            System.out.println("|  Mã sản phẩm  |      Tên sản phẩm       |     Số lượng     |       Đơn giá      |      Tạm tính      |");
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
            for (int idPro : cart.getProducts().keySet()) {
                Product pro = productService.findById(idPro);
                double subTotal = cart.getProducts().get(idPro) * pro.getUnitPrice();
                System.out.printf("|       %-5d   |  %-20s   |        %-5d     |       %-10s   |       %-10s   |\n",
                        idPro, pro.getProductName(), cart.getProducts().get(idPro),
                        currencyFormat.format(pro.getUnitPrice()), currencyFormat.format(subTotal));
//                total += subTotal;
            }
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
            System.out.printf("|                                                       \033[1;97mTổng tiền:" + RESET + "                |       \033[0;32m%-10s" + RESET + "   |\n", currencyFormat.format(totalBill()));
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
        }

    }

    private void handleDisplayProductInfo() {
        System.out.println("Danh sách sản phẩm: ");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("                                                                  \033[1;94mDANH SÁCH SẢN PHẨM" + RESET);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("| Mã sản phẩm  |       Tên sản phẩm       |             Mô tả             |       Đơn giá       |   SL Kho  |      Danh mục       |       Trạng thái      |");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        for (Product pro : productService.findAll()) {
            if (pro.isStatus() && pro.getCategory().isStatus()) {
                System.out.println(pro);
            }
        }
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");

    }

    public double totalBill() {
        double totalBill = 0;
        for (int proId : cartService.findCartByUserLogin().getProducts().keySet()) {
            totalBill += cartService.findCartByUserLogin().getProducts().get(proId) * productService.findById(proId).getUnitPrice();
        }
        return totalBill;
    }
}
