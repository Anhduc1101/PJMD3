package ra.View.account.user;

import ra.config.Validate;
import ra.model.Cart;
import ra.model.Order;
import ra.model.OrderStatus;
import ra.model.Product;
import ra.service.cart.CartServiceIMPL;
import ra.service.cart.ICartService;
import ra.service.order.IOrderService;
import ra.service.order.OrderServiceIMPL;
import ra.service.product.IProductService;
import ra.service.product.ProductServiceIMPL;


import java.time.LocalDateTime;

import static ra.config.Color.*;

public class UserOrder {
    ICartService cartService = new CartServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    Cart cart = cartService.findCartByUserLogin();

    public void handleCheckOut() {
        new UserCart().handleDisplayCart();
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
//                    newOrder.setOrderId(cart.getCartId());
                    for (int idPro : cart.getProducts().keySet()) {
                        Product product = productService.findById(idPro);
                        if (product.getProductId() == idPro && product.getStock() >= cart.getProducts().get(idPro)) {
                            product.setStock(product.getStock() - cart.getProducts().get(idPro));
                        } else if (product.getStock() - cart.getProducts().get(idPro) == 0) {
                            product.setStatus(!product.isStatus());
                            System.out.println(RED+"Sản phẩm đã hết hàng"+RESET);
                        } else {
                            System.out.println(RED+"Số lượng trong kho không đủ!"+RESET);
                            return;
                        }
                    }
                    newOrder.setOrderId(orderService.getNewId());
                    newOrder.setUserId(cart.getUserId());
                    System.out.println("Nhập vào tên người nhận: ");
                    newOrder.setName(Validate.validateString());
                    System.out.println("Nhập vào địa chỉ người nhận: ");
                    newOrder.setAddress(Validate.validateString());
                    System.out.println("Nhập vào sô điện thoại của người nhận: ");
                    newOrder.setPhoneNumber(Validate.validatePhoneNumber());
                    newOrder.setTotal(new UserCart().totalBill());
                    newOrder.setOrderStatus(OrderStatus.WAITING);
                    newOrder.setOrderDetails(cart.getProducts());
                    newOrder.setOrderAt(LocalDateTime.now());
//                    newOrder.setDeliverAt(LocalDateTime.now().plusMinutes(3));
                    orderService.save(newOrder);
                    System.out.println(GREEN + "Đặt hàng thành công" + RESET);
                    cart.getProducts().clear();
                    cartService.save(cart);
                }
                break;
            case 2:
                break;
            default:
                System.out.println("Không có lựa chọn này! ");
                break;
        }

    }

}
