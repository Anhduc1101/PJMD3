package ra.View.account.admin;

import ra.View.Home;
import ra.View.account.profile.MyProfile;
import ra.config.Config;
import ra.config.Utils;
import ra.config.Validate;
import ra.model.Category;
import ra.model.Users;

import static ra.config.Color.*;

public class MenuAdmin {
    public void menuAdmin() {
        do {
//            System.out.println("Xin  chào admin: " + Home.userLogin.getName());
            System.out.println("Xin chào : " + RED + new Config<Users>().readFile(Config.URL_USER_LOGIN).getName() + RESET);
            System.out.println("\033[1;94m╔═════════════ TRANG QUẢN TRỊ ═════════════╗");
            System.out.println("\033[1;94m║"+RESET+"           "+ Utils.getCurrentDateTime() + " \033[1;94m           ║");
            System.out.println("\033[1;94m║══════════════════════════════════════════║"+RESET);
            System.out.println("\033[1;94m║           \033[1;97m1. Quản lý người dùng"+RESET+"\033[1;94m          ║");
            System.out.println("\033[1;94m║           \033[1;97m2. Quản lý danh mục"+RESET+"\033[1;94m            ║");
            System.out.println("\033[1;94m║           \033[1;97m3. quản lý sản phẩm"+RESET+"\033[1;94m            ║");
            System.out.println("\033[1;94m║           \033[1;97m4. Quản lý đơn hàng"+RESET+"\033[1;94m            ║");
            System.out.println("\033[1;94m║           \033[1;97m5. Thông tin cá nhân"+RESET+"\033[1;94m           ║");
            System.out.println("\033[1;94m║           \033[1;97m0. Thoát"+RESET+"\033[1;94m                       ║");
            System.out.println("\033[1;94m╚══════════════════════════════════════════╝"+RESET);
            System.out.print("Mời lựa chọn (1/2/3/4/5/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    new UserManagement().menu();
                    break;
                case 2:
                    new CategoryManagement().menu();
                    break;
                case 3:
                    new ProductManagement().menu();
                    break;
                case 4:
                    break;
                     case 5:
                         new MyProfile().menu();
                    break;
                case 0:
                    new Config<Users>().writeFile(Config.URL_USER_LOGIN, null);
                    new Home().menu();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }
}
