import ra.model.Product;
import ra.service.product.IProductService;
import ra.service.product.ProductServiceIMPL;

public class Main {
    static
    Product product=new Product();
    static IProductService productService=new ProductServiceIMPL();
    public static void main(String[] args) {
        for (Product pro: productService.findAll()) {
         pro.setStock(pro.getStock()+1000);
        }
        System.out.println();
    }
}
