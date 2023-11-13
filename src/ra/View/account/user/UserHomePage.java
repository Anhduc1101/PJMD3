package ra.View.account.user;

import ra.config.Utils;
import ra.config.Validate;
import ra.model.Category;
import ra.model.Product;
import ra.service.category.CategoryServiceIMPL;
import ra.service.category.ICategoryService;
import ra.service.product.IProductService;
import ra.service.product.ProductServiceIMPL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static ra.config.Color.*;

public class UserHomePage {
    ICategoryService categoryService = new CategoryServiceIMPL();
    IProductService productService = new ProductServiceIMPL();

    public void menu() {
        do {
            System.out.println("\033[1;94m╔══════════════════════════ TRANG CHỦ  ═══════════════════════════╗");
            System.out.println("\033[1;94m║" + RESET + "                      " + Utils.getCurrentDateTime() + " \033[1;94m                       ║");
            System.out.println("\033[1;94m║═════════════════════════════════════════════════════════════════║" + RESET);
            System.out.println("\033[1;94m║              \033[1;97m1. Tìm kiếm sản phẩm" + RESET + "\033[1;94m                               ║");
            System.out.println("\033[1;94m║              \033[1;97m2. Hiển thị sản phẩm nổi bật có giá rẻ nhất" + RESET + "\033[1;94m        ║");
            System.out.println("\033[1;94m║              \033[1;97m3. Hiển thị từng nhóm sản phẩm" + RESET + "\033[1;94m                     ║");
            System.out.println("\033[1;94m║              \033[1;97m4. Danh sách sản phẩm" + RESET + "\033[1;94m                              ║");
            System.out.println("\033[1;94m║              \033[1;97m5. Danh sách sắp xếp theo giá giảm dần" + RESET + "\033[1;94m             ║");
            System.out.println("\033[1;94m║              \033[1;97m0. Quay lại" + RESET + "\033[1;94m                                        ║");
            System.out.println("\033[1;94m╚═════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (1/2/3/4/5/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    handleFindProduct();
                    break;
                case 2:
                    handleDisplayTopTenProductByUnitPriceCheapest();
                    break;
                case 3:
                    handleDisplayProductByCategoryId();
                    break;
                case 4:
                    handleDisplayProduct();
                    break;
                case 5:
                    handleDisplayProductByUnitPriceDecent();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }


    private void handleDisplayProductByUnitPriceDecent() {
        System.out.println("Danh sách trước khi sắp xếp: ");
        System.out.println();
        handleDisplayProduct();
        System.out.println();
        System.out.println("Danh sách sau khi sắp xếp theo thứ tự giá tăng dần: ");
        System.out.println();
//        for (int i = 0; i < productService.findAll().size() - 1; i++) {
//            for (int j = 0; j < productService.findAll().size() - 1; j++) {
//                if (productService.findAll().get(j).getUnitPrice() < productService.findAll().get(j + 1).getUnitPrice()) {
//                    Product temp = productService.findAll().get(j);
//                    productService.findAll().set(j,productService.findAll().get(j+1));
//                    productService.findAll().set(j+1,temp);
//                }
//            }
//        }
        Collections.sort(productService.findAll());
        handleDisplayProduct();
        System.out.println(GREEN + "Đã sắp xếp xong!" + RESET);
    }

    private void handleDisplayProduct() {
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("                                                                  \033[1;94mDANH SÁCH SẢN PHẨM" + RESET);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("| Mã sản phẩm  |       Tên sản phẩm       |             Mô tả             |       Đơn giá       |   SL Kho  |      Danh mục       |       Trạng thái      |");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        for (Product product : productService.findAll()) {
            if (product.isStatus() && product.getCategory().isStatus()) {
                System.out.println(product);
            }
        }
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
    }

    private void handleDisplayProductByCategoryId() {
        for (Category cate : categoryService.findAll()) {
            if (cate.isStatus()) {
                System.out.println(cate);
            }
        }
        System.out.println("Nhập vào mã danh mục bạn muốn hiển thị: ");
        int cateId = Validate.validateInt();
        Category cat = categoryService.findById(cateId);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("                                                                  \033[1;94mDANH SÁCH SẢN PHẨM" + RESET);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("| Mã sản phẩm  |       Tên sản phẩm       |             Mô tả             |       Đơn giá       |   SL Kho  |      Danh mục       |       Trạng thái      |");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        if (cat==null){
            System.out.println(RED+"Không có danh mục này!"+RESET);
            return;
        }
        if (!cat.isStatus()){
            System.out.println(RED+"Không có danh mục này!"+RESET);
            return;
        }
        boolean isFound = false;
        for (int i = 0; i < productService.findAll().size(); i++) {
            if (productService.findAll().get(i).getCategory().getCategoryId()==cateId) {
                if (productService.findAll().get(i).isStatus()&&productService.findAll().get(i).getCategory().isStatus()){
                System.out.println(productService.findAll().get(i));
                isFound = true;
                }
            }
        }
        if (!isFound) {
            System.out.println(RED+"Không có nhóm sản phẩm nào"+RESET);
        }
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");

    }

    private void handleDisplayTopTenProductByUnitPriceCheapest() {
        System.out.println("Danh sách sản phẩm có giá rẻ nhất: ");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("                                                                  \033[1;94mDANH SÁCH SẢN PHẨM" + RESET);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("| Mã sản phẩm  |       Tên sản phẩm       |             Mô tả             |       Đơn giá       |   SL Kho  |      Danh mục       |       Trạng thái      |");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        for (int i = 0; i < productService.findAll().size() - 1; i++) {
            for (int j = 0; j < productService.findAll().size() - 1; j++) {
                if (productService.findAll().get(j).getUnitPrice() > productService.findAll().get(j + 1).getUnitPrice()) {
                    Product temp = productService.findAll().get(j);
                    productService.findAll().set(j, productService.findAll().get(j + 1));
                    productService.findAll().set(j + 1, temp);
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            if (productService.findAll().get(i) != null && productService.findAll().get(i).isStatus()) {
                System.out.println(productService.findAll().get(i));
            }
        }
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
    }


    private void handleFindProduct() {
        handleDisplayProduct();
        System.out.println("Nhập vào tên sản phẩm bạn muốn tìm kiếm: ");
        String findName = Validate.validateString();
        boolean isFound = false;
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("                                                                  \033[1;94mDANH SÁCH SẢN PHẨM" + RESET);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("| Mã sản phẩm  |       Tên sản phẩm       |             Mô tả             |       Đơn giá       |   SL Kho  |      Danh mục       |       Trạng thái      |");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        for (int i = 0; i < productService.findAll().size(); i++) {
            if (productService.findAll().get(i).getProductName().toLowerCase().contains(findName.toLowerCase())) {
                System.out.println(productService.findAll().get(i));
                isFound = true;
            }
        }
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println(GREEN + "Đã tìm xong !" + RESET);
        if (!isFound) {
            System.out.println(RED + "Không tìm thấy sản phẩm này!" + RESET);
        }
    }
}
