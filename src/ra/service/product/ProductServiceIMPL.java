package ra.service.product;

import ra.config.Config;
import ra.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceIMPL implements IProductService {
    static Config<List<Product>> config = new Config<>();
    public static List<Product> productList;

    static {
        productList = config.readFile(Config.URL_PRODUCT);
        if (productList == null) {
            productList = new ArrayList<>();
        }
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        if (findById(product.getProductId()) == null) {
            productList.add(product);
            updateData();
        } else {
            productList.set(productList.indexOf(product), product);
            updateData();

        }

    }

    @Override
    public void delete(int id) {
        productList.remove(findById(id));
        updateData();
    }

    @Override
    public Product findById(int id) {
        for (Product pro : productList) {
            if (pro.getProductId() == id) {
                return pro;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Product pro : productList) {
            if (pro.getProductId() > idMax) {
                idMax = pro.getProductId();
            }
        }
        return idMax + 1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_PRODUCT, productList);
    }

    @Override
    public Product findByName(String name) {
        for (Product pro : productList) {
            if (pro.getProductName().toLowerCase().contains(name.toLowerCase())) {
                return pro;
            }
        }
        return null;
    }

    @Override
    public boolean isExistProductName(String productName) {
        for (Product pro : productList) {
            if (pro.getProductName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

}
