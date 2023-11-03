package ra.View.account.user;

import ra.model.Cart;
import ra.model.Product;
import ra.service.cart.CartServiceIMPL;
import ra.service.cart.ICartService;
import ra.service.product.IProductService;
import ra.service.product.ProductServiceIMPL;

import java.text.NumberFormat;
import java.util.Locale;

import static ra.config.Color.RESET;

public class UserOrderHistory {
    ICartService cartService = new CartServiceIMPL();
    IProductService productService=new ProductServiceIMPL();

    public void showHistory() {
        Cart cart=cartService.findCartByUserLogin();
        double total = 0.0;
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
            total += subTotal;
        System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
        System.out.printf("|  %-5s %-20s   |       %-30s  |       %-10s   |\n","Trạng thái   : ",(cart.isStatus()?"Được xác nhận":(cart.isStatus()?"Bị hủy":"đã bị hủy")),"       Tổng tiền",total);
//        System.out.printf("|  Trạng thái:     "+(cart.isStatus()?"Được xác nhận":(cart.isStatus()?"Bị hủy":"đã thành công"))+"                 |             \033[1;97mTổng tiền:" + RESET + "                |       \033[0;32m%-10s" + RESET + "   |\n", currencyFormat.format(total));
        System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
        }


//        System.out.println(cart);
//        for (Cart cart:cartService.findAll()) {
//            System.out.println(cart);
//        }
    }
}
