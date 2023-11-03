package ra.service.product;

import ra.model.Product;
import ra.service.IGenericService;

public interface IProductService extends IGenericService<Product> {
    Product findByName(String name);
    boolean isExistProductName(String productName);
}
