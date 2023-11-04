package ra.View.account.admin;

import com.sun.org.apache.regexp.internal.RE;
import ra.config.Utils;
import ra.config.Validate;
import ra.model.Category;
import ra.model.Product;
import ra.service.category.CategoryServiceIMPL;
import ra.service.category.ICategoryService;
import ra.service.product.IProductService;
import ra.service.product.ProductServiceIMPL;

import java.util.ArrayList;
import java.util.List;

import static ra.config.Color.*;

public class ProductManagement {
    IProductService productService = new ProductServiceIMPL();
    ICategoryService categoryService = new CategoryServiceIMPL();

    public void menu() {
        do {
            System.out.println("\033[1;94m╔════════════════ QUẢN LÝ SẢN PHẨM ═════════════════╗");
            System.out.println("\033[1;94m║" + RESET + "               " + Utils.getCurrentDateTime() + " \033[1;94m                ║");
            System.out.println("\033[1;94m║═══════════════════════════════════════════════════║" + RESET);
            System.out.println("\033[1;94m║         \033[1;97m1. Hiển thị danh sách sản phẩm" + RESET + "\033[1;94m            ║");
            System.out.println("\033[1;94m║         \033[1;97m2. Thêm mới 1 hoặc nhiều sản phẩm " + RESET + "\033[1;94m        ║");
            System.out.println("\033[1;94m║         \033[1;97m3. Sửa thông tin sản phẩm" + RESET + "\033[1;94m                 ║");
            System.out.println("\033[1;94m║         \033[1;97m4. Tìm kiếm sản phẩm theo tên" + RESET + "\033[1;94m             ║");
            System.out.println("\033[1;94m║         \033[1;97m5. Ẩn sản phẩm theo mã sản phẩm" + RESET + "\033[1;94m           ║");
            System.out.println("\033[1;94m║         \033[1;97m0. Quay lại" + RESET + "\033[1;94m                               ║");
            System.out.println("\033[1;94m╚═══════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (1/2/3/4/5/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    handleDisplayProducts();
                    break;
                case 2:
                    handleAddNewProduct();
                    break;
                case 3:
                    handleEditProduct();
                    break;
                case 4:
                    handleFindProductByProductName();
                    break;
                case 5:
                    handleHiddenProductByProductId();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void handleHiddenProductByProductId() {
        handleDisplayProducts();
        System.out.println("Nhập mã sản phẩm bạn muốn ẩn: ");
        int hiddenNum = Validate.validateInt();
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("                                                                  \033[1;94mDANH SÁCH SẢN PHẨM" + RESET);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("| Mã sản phẩm  |       Tên sản phẩm       |             Mô tả             |       Đơn giá       |   SL Kho  |      Danh mục       |       Trạng thái      |");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        for (int i = 0; i < productService.findAll().size(); i++) {
            if (productService.findAll().get(i).getProductId() == hiddenNum) {
                System.out.println("Bạn đã chọn sản phẩm này: " + productService.findAll().get(i));
                System.out.println("Bạn có muốn ẩn nó đi không? ");
                System.out.println("1. Có");
                System.out.println("2. Không");
                System.out.println("Mời chọn: ");
                switch (Validate.validateInt()) {
                    case 1:
                        productService.findAll().get(i).setStatus(!productService.findAll().get(i).isStatus());
                        System.out.println(GREEN + "Đổi thành công " + RESET);
                        productService.updateData();
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Không có lựa chọn này");
                        break;
                }
            }
        }
//        updateCategory();
    }

    private void handleFindProductByProductName() {
        handleDisplayProducts();
        System.out.println("Nhập tên sản phẩm bạn muốn tìm: ");
        String findName = Validate.validateString();
        boolean isFound = false;
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("                                                                  \033[1;94mDANH SÁCH SẢN PHẨM" + RESET);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("| Mã sản phẩm  |       Tên sản phẩm       |             Mô tả             |       Đơn giá       |   SL Kho  |      Danh mục       |       Trạng thái      |");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        for (Product pro : productService.findAll()) {
            if (pro.getProductName().toLowerCase().contains(findName.toLowerCase())) {
                System.out.println(pro);
                isFound = true;
            }
        }
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println(GREEN + "Đã tìm xong !" + RESET);
        if (!isFound) {
            System.out.println(RED + "Không có sản phẩm này!" + RESET);
        }
    }

    private void handleEditProduct() {
//        handleDisplayProducts();
        System.out.println("Nhập id sản phẩm bạn muốn chỉnh sửa thông tin: ");
        int idEdit = Validate.validateInt();
        List<Product> productsToEdit = new ArrayList<>();
        for (Product pro : productService.findAll()) {
            if (pro.getProductId() == idEdit) {
                System.out.println(pro);
                boolean isDone = false;
                while (!isDone) {
                    System.out.println("Chọn thông tin sản phẩm bạn muốn sửa: ");
                    System.out.println("1. Tên sản phẩm: ");
                    System.out.println("2. Id danh mục: ");
                    System.out.println("3. Mô tả: ");
                    System.out.println("4. Đơn giá: ");
                    System.out.println("5. Số lượng trong kho: ");
                    System.out.println("6. Trạng thái: ");
                    System.out.println("0. Quay lại ");
                    switch (Validate.validateInt()) {
                        case 1:
                            System.out.println("Mời nhập lại tên sản phẩm: ");
                            pro.setProductName(Validate.validateString());
                            productService.updateData();
                            break;
                        case 2:
                            System.out.println("Mời nhập lại danh mục theo số: ");
                            int n = Validate.validateInt();
                            for (int i = 0; i < categoryService.findAll().size(); i++) {
                                System.out.println((i + 1) + ". " + categoryService.findAll().get(i).getCategoryName());
                            }
                            pro.setCategory(categoryService.findAll().get(n - 1));
                            productService.updateData();
                            break;
                        case 3:
                            System.out.println("Mời nhập lại mô tả: ");
                            pro.setDescription(Validate.validateString());
                            productService.updateData();
                            break;
                        case 4:
                            System.out.println("Mời nhập lại giá: ");
                            pro.setUnitPrice(Validate.validateInt());
                            productService.updateData();
                            break;
                        case 5:
                            System.out.println("Mời nhập lại số lượng trong kho: ");
                            pro.setStock(Validate.validateInt());
                            productService.updateData();
                            break;
                        case 6:
                            System.out.println("Trạng thái hiện tại của sản phẩm là: " + (pro.isStatus() ? "đang bán" : "hết hàng"));
                            System.out.println("Bạn có muốn thay đổi trạng thái của sản phẩm? ");
                            System.out.println("1. Có");
                            System.out.println("2. Không");
                            switch (Validate.validateInt()) {
                                case 1:
                                    pro.setStatus(!pro.isStatus());
                                    System.out.println(GREEN + "Đổi thành công" + RESET);
                                    productService.updateData();
//                                    if (pro.getCategory().isStatus()){
//                                    }else {
//                                        System.out.println(RED+"Danh mục của sản phẩm này hện đang ngừng hoạt động, hãy đổi trạng thái danh mục trước"+RESET);
//                                        return;
//                                    }
                                    break;
                                case 2:
                                    break;
                            }
                            break;
                        case 0:
                            isDone = true;
                            return;
                        default:
                            System.out.println("Không có lựa chọn này!");
                            break;
                    }
                }
//                updateCategory(pro.getCategory());
                productService.save(pro);
            }
        }
        System.out.println(GREEN + "Thay đổi thành công!" + RESET);
    }

    private void handleAddNewProduct() {
        System.out.println("Nhập số lượng sản phẩm cần thêm: ");
        int n = Validate.validateInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Nhập sản phẩm thứ: " + (i + 1));
            Product product = new Product();
            product.setProductId(productService.getNewId());
            System.out.println("Nhập tên sản phẩm: ");
            while (true) {
                String proName = Validate.validateString();
                if (productService.isExistProductName(proName)) {
                    System.out.println(RED + "Sản phẩm này đã tồn tại. Mời nhập lại: " + RESET);
                } else {
                    product.setProductName(proName);
                    break;
                }
            }
            System.out.println("Nhập danh mục cho sản phẩm: ");
            System.out.println("Danh sách các danh mục");
            for (int j = 0; j < categoryService.findAll().size(); j++) {
                if (categoryService.findAll().get(j).isStatus()) {
                    System.out.println((j + 1) + ". " + categoryService.findAll().get(j).getCategoryName());
                }
            }
            System.out.println("Chọn danh mục theo số: ");
            while (true) {
                int choiceNum = Validate.validateInt();
                if (choiceNum > 0 && choiceNum <= categoryService.findAll().size() && categoryService.findAll().get(choiceNum - 1).isStatus()) {
                    product.setCategory(categoryService.findAll().get(choiceNum - 1));
                    break;
                } else {
                    System.out.println(RED + "Không có danh mục này này, mời nhập lại: " + RESET);
                }
            }
            System.out.println("Nhập mô tả: ");
            product.setDescription(Validate.validateString());
            System.out.println("Nhập đơn giá: ");
            product.setUnitPrice(Validate.validateInt());
            System.out.println("Nhập số lượng trong kho: ");
            product.setStock(Validate.validateInt());
            productService.save(product);
            System.out.println(GREEN + "Thêm mới sản phẩm thành công !" + RESET);
        }
    }

    private void handleDisplayProducts() {
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("                                                                  \033[1;94mDANH SÁCH SẢN PHẨM" + RESET);
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        System.out.println("| Mã sản phẩm  |       Tên sản phẩm       |             Mô tả             |       Đơn giá       |   SL Kho  |      Danh mục       |       Trạng thái      |");
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
        productService.findAll().sort((p1, p2) -> p2.getProductId() - p1.getProductId());
        for (Product pro : productService.findAll()) {
//            if (!pro.getCategory().isStatus()) {
//                pro.setStatus(!pro.isStatus());
//            }
                System.out.println(pro);
        }
        System.out.println("+--------------+--------------------------+-------------------------------+---------------------+-----------+---------------------+-----------------------+");
    }

//    private void updateCategory(Category category) {
//        List<Product> productsToUpdate = new ArrayList<>();
//
//        for (Product pro : productService.findAll()) {
//            if (pro.getCategory().getCategoryId() == category.getCategoryId()) {
//                pro.setCategory(category);
//                productsToUpdate.add(pro);
//            }
//        }
//        for (Product product : productsToUpdate) {
//            productService.save(product);
//        }
//    }
}
