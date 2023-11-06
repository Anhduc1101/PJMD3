package ra.View.account.user;

import ra.View.Home;
import ra.View.account.profile.MyProfile;
import ra.config.Config;
import ra.config.Utils;
import ra.config.Validate;
import ra.model.Users;
import sun.util.calendar.LocalGregorianCalendar;

import java.util.Date;

import static ra.config.Color.RED;
import static ra.config.Color.RESET;

public class MenuUser {
    public void menuUser() {
        do {
            System.out.println("Xin chào : " + RED + new Config<Users>().readFile(Config.URL_USER_LOGIN).getName() + RESET);
            System.out.println("\033[1;94m╔══════════════ TRANG NGƯỜI DÙNG ════════════════╗");
            System.out.println("\033[1;94m║"+RESET+"             "+Utils.getCurrentDateTime() + " \033[1;94m               ║");
            System.out.println("\033[1;94m║════════════════════════════════════════════════║"+RESET);
            System.out.println("\033[1;94m║           \033[1;97m1. Trang chủ"+RESET+"\033[1;94m                         ║");
            System.out.println("\033[1;94m║           \033[1;97m2. Giỏ hàng"+RESET+"\033[1;94m                          ║");
            System.out.println("\033[1;94m║           \033[1;97m3. Đặt hàng"+RESET+"\033[1;94m                          ║");
            System.out.println("\033[1;94m║           \033[1;97m4. Thông tin cá nhân"+RESET+"\033[1;94m                 ║");
            System.out.println("\033[1;94m║           \033[1;97m5. Lịch sử đơn hàng"+RESET+"\033[1;94m                  ║");
            System.out.println("\033[1;94m║           \033[1;97m0. Đăng xuất"+RESET+"\033[1;94m                         ║");
            System.out.println("\033[1;94m╚════════════════════════════════════════════════╝"+RESET);
            System.out.print("Mời lựa chọn (1/2/3/4/5/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    new UserHomePage().menu();
                    break;
                case 2:
                    new UserCart().menu();
                    break;
                case 3:
                    new UserOrder().handleCheckOut();
                    break;
                case 4:
                    new MyProfile().menu();
                    break;
                case 5:
                    new UserOrderHistory().menu();
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
