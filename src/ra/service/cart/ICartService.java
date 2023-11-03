package ra.service.cart;

import ra.model.Cart;
import ra.service.IGenericService;

public interface ICartService extends IGenericService<Cart> {
    Cart findCartByUserLogin();
}
