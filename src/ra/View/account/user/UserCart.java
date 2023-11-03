package ra.View.account.user;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.Product;
import ra.model.Users;
import ra.service.cart.CartServiceIMPL;
import ra.service.cart.ICartService;
import ra.service.product.IProductService;
import ra.service.product.ProductServiceIMPL;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import static ra.config.Color.RESET;

public class UserCart {
    ICartService cartService = new CartServiceIMPL();
    IProductService productService = new ProductServiceIMPL();

    public void menu() {
        do {
            System.out.println("**********************MENU************************");
            System.out.println("1. Hiển thị danh sách sản phẩm ");
            System.out.println("2. Hiển thị giỏ hàng");
            System.out.println("3. Thay đổi số lượng");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Thêm vào giỏ hàng");
            System.out.println("0. Thoát");
            System.out.print("Mời lựa chọn (1/2/3/4/5/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    handleDisplayProductInfo();
                    break;
                case 2:
                    handleDisplayCart();
                    break;
                case 3:
                    handleChangeQuantityOfProduct();
                    break;
                case 4:
                    handleDeleteProductInCart();
                    break;
                case 5:
                    handleAddToCart();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }


    private void handleAddToCart() {
        for (Product pro : productService.findAll()) {
            if (pro.isStatus()) {
                System.out.println(pro);
            }
        }
//        đặt 1 cái
        System.out.println("Chọn mã sản phẩm cần đặt(chon 0 để thoát): ");
        int idBuy = Validate.validateInt();
        Product proBuy = productService.findById(idBuy);
        if (idBuy == 0) {
            return;
        }
        if (proBuy == null) {
            System.out.println("Không tồn tại theo ID vừa nhập: ");
        } else {
            int newStock = proBuy.getStock() - 1;
            proBuy.setStock(newStock);
            productService.save(proBuy);
            Users userLogin = new Config<Users>().readFile(Config.URL_USER_LOGIN);
            Cart cart = cartService.findCartByUserLogin();
            if (cart == null) {
                cart = new Cart(cartService.getNewId(), userLogin.getId(), new HashMap<>(), false);
            }
            if (cart.getProducts().containsKey(idBuy)) {
                cart.getProducts().put(idBuy, cart.getProducts().get(idBuy) + 1);
            } else {
                cart.getProducts().put(idBuy, 1);
            }
            cartService.save(cart);
            System.out.println(cart);
            System.out.println("Thêm sản phẩm vào giỏ hàng thành công");
        }
    }

    private void handleDeleteProductInCart() {
        handleDisplayCart();
        Cart cart = cartService.findCartByUserLogin();
        System.out.println("Nhập mã sản phẩm bạn muốn xóa: ");
        int deleteId = Validate.validateInt();
        Iterator<Integer> iterator = cart.getProducts().keySet().iterator();
        while (iterator.hasNext()) {
            int idPro = iterator.next();
            if (idPro == deleteId) {
                iterator.remove();
                handleDisplayCart();
                System.out.println("Xóa thành công");
                cartService.updateData();
                return; // Thoát khỏi phương thức sau khi xóa thành công
            }
        }
        cartService.save(cart);
        System.out.println("Không tìm thấy sản phẩm trong giỏ hàng");
    }

    private void handleChangeQuantityOfProduct() {
        handleDisplayCart();
        Cart cart = cartService.findCartByUserLogin();
        Map<Integer,Integer> products=cart.getProducts();
        System.out.println("Nhập vào mã sản phẩm bạn muốn thay đổi số lượng sản phẩm: ");
        int idChange = Validate.validateInt();
        Product pro =productService.findById(idChange);
        System.out.println("Số lượng sản phẩm hiện tại trong giỏ là: "+cart.getProducts().get(idChange));
        System.out.println("Nhập số lượng cần thay đổi: ");
        int changeNum=Validate.validateInt();
        if (products.containsKey(idChange)){
            if (changeNum>pro.getStock()){
                System.out.println("Số lượng không đủ, trong kho chỉ còn "+pro.getStock());
                return;
            }
            products.put(idChange,changeNum);
//            pro.setStock(pro.getStock()-changeNum);
            System.out.println("Số lượng sau khi thay đổi là: "+cart.getProducts().get(idChange));
            System.out.println("Thay đổi thành công! ");
            cartService.updateData();
        }else {
            System.out.println("Sản phẩm không tồn tại trong giỏ hàng!");
        }
    }

    private void handleDisplayCart() {
        Cart cart = cartService.findCartByUserLogin();
        if (cart == null) {
            System.out.println("Giỏ hàng trống");
            return;
        } else {
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
            }
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
            System.out.printf("|                                                       \033[1;97mTổng tiền:" + RESET + "                |       \033[0;32m%-10s" + RESET + "   |\n", currencyFormat.format(total));
            System.out.println("+---------------+-------------------------+------------------+--------------------+--------------------+");
        }
        System.out.println("Bạn có muốn đặt hàng không?: ");
        System.out.println("1. Có");
        System.out.println("2. Không");
        System.out.println("Mời lựa chọn: ");
        switch (Validate.validateInt()){
            case 1:
                if (cart.getProducts().isEmpty()){
                    System.out.println("Giỏ hàng trống, không được đặt!");
                    break;
                }else {
                System.out.println("Đặt hàng thành công");
                cart.getProducts().clear();
                }
//                cartService.save(cart);
                break;
             case 2:
                 return;
            default:
                System.out.println("Không có lựa chọn này! ");
                break;

        }
    }

    private void handleDisplayProductInfo() {
        System.out.println("Danh sách sản phẩm: ");
        for (Product pro : productService.findAll()) {
            if (pro.isStatus()) {
                System.out.println(pro);
            }
        }
    }
}
