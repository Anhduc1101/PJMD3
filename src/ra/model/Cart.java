package ra.model;

import java.io.Serializable;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private int cartId;
    private int userId;
    private Map<Integer,Integer> products;
    private boolean status;

    public Cart() {
    }

    public Cart(int cartId, int userId, Map<Integer, Integer> products,boolean status) {
        this.cartId = cartId;
        this.userId = userId;
        this.products = products;
        this.status=status;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", products=" + products +
                ", status=" + (status?"Đã thanh toán":"Chưa thanh toán") +
                '}';
    }
}
