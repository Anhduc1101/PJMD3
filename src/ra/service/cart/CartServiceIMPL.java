package ra.service.cart;

import ra.config.Config;
import ra.model.Cart;
import ra.model.Users;

import java.util.ArrayList;
import java.util.List;

public class CartServiceIMPL implements ICartService {
    static Config<List<Cart>> config = new Config<>();
    public static List<Cart> cartList;

    static {
        cartList = config.readFile(Config.URL_CART);
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
    }

    @Override
    public List<Cart> findAll() {
        return cartList;
    }

    @Override
    public void save(Cart cart) {
        if (findById(cart.getCartId()) == null) {
            cartList.add(cart);
            updateData();
        } else {
            cartList.set(cartList.indexOf(cart), cart);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        cartList.remove(findById(id));
    }

    @Override
    public Cart findById(int id) {
        for (Cart cart : cartList) {
            if (cart.getCartId() == id) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Cart cart : cartList) {
            if (cart.getCartId() > idMax) {
                idMax = cart.getCartId();
            }
        }
        return idMax + 1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CART, cartList);
    }

    @Override
    public Cart findCartByUserLogin() {
        Users userLogin = new Config<Users>().readFile(Config.URL_USER_LOGIN);
        for (Cart cart : cartList) {
            if (cart.getUserId() == userLogin.getId() && !cart.isStatus()) {
                return cart;
            }
        }
        return null;
    }
}
