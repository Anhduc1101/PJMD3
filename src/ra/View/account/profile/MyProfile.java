package ra.View.account.profile;

import ra.config.Config;
import ra.config.Utils;
import ra.config.Validate;
import ra.model.Users;
import ra.service.users.IUserService;
import ra.service.users.UserServiceIMPL;

import static ra.config.Color.*;

public class MyProfile {
    IUserService userService = new UserServiceIMPL();
    public static Users userLogin = (Users) new Config<>().readFile(Config.URL_USER_LOGIN);

    public void menu() {
        do {
            System.out.println("0. Thoát");
            System.out.println("\033[1;94m╔═══════════ TRANG THÔNG TIN CÁ NHÂN  ═══════════╗");
            System.out.println("\033[1;94m║"+RESET+"              "+ Utils.getCurrentDateTime() + " \033[1;94m              ║");
            System.out.println("\033[1;94m║════════════════════════════════════════════════║"+RESET);
            System.out.println("\033[1;94m║         \033[1;97m1. Hiển thị thông tin cá nhân" + RESET + "\033[1;94m          ║");
            System.out.println("\033[1;94m║         \033[1;97m2. Thay đổi thông tin cá nhân" + RESET + "\033[1;94m          ║");
            System.out.println("\033[1;94m║         \033[1;97m0. Quay lại" + RESET + "\033[1;94m                            ║");
            System.out.println("\033[1;94m╚════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (1/2/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    System.out.println("Thông tin người dùng: ");
                    System.out.println(userLogin);
                    break;
                case 2:
                    handleChangeInfo();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void handleChangeInfo() {
        System.out.println(userLogin);
        System.out.println("Bạn muốn thay đổi thông tin nào dưới đây? ");
        System.out.println("1. Tên");
        System.out.println("2. Tên đăng nhập");
        System.out.println("3. Mật khẩu");
        System.out.println("4. Email");
        System.out.println("5. Số điện thoại");
        System.out.println("0. Quay lại");
        System.out.println("Mời lựa chọn: ");
        switch (Validate.validateInt()) {
            case 1:
                System.out.println("Tên hiện tại là: " + userLogin.getName());
                System.out.println("Mời thay đổi: ");
                userLogin.setName(Validate.validateString());
                System.out.println(GREEN+"Đổi tên thành công !"+RESET);
                break;
            case 2:
                System.out.println("Tên đăng nhập hện tại là: " + userLogin.getUsername());
                System.out.println("Mời thay đổi: ");
                while (true) {
                    String newUsername = Validate.validateString();
                    if (userService.existUsername(newUsername)) {
                        System.out.println(RED+"Tên đăng nhập đã tồn tại. Mời nhập tên khác: "+RESET);
                    } else {
                        userLogin.setUsername(newUsername);
                        break;
                    }
                }
                System.out.println(GREEN+"Đổi tên đăng nhập thành công !"+RESET);
                break;
            case 3:
                System.out.println("Mật khẩu hiện tại là: " + userLogin.getPassword());
                System.out.println("Mời nhập mật khẩu mới: ");
                String newPass;
                while (true) {
                    newPass = Validate.validateString();
                    if (userLogin.getPassword().equals(newPass)) {
                        System.out.println(RED+"Mật khẩu mới không được trùng với mật khẩu cũ. Mời nhập lại: "+RESET);
                    } else {
                        break;
                    }
                }
                System.out.println("Mời xác nhận mật khẩu: ");
                while (true) {
                    String confirmNewPass = Validate.validateString();
                    if (!confirmNewPass.equals(newPass)) {
                        System.out.println(RED+"Mật khẩu không khớp. Mời nhập lại: "+RESET);
                    } else {
                        break;
                    }
                }
                System.out.println(GREEN+"Đổi mật khẩu thành công !"+RESET);
                break;
            case 4:
                System.out.println("Email hiện tại là: " + userLogin.getEmail());
                System.out.println("Mời nhập email mới: ");
                while (true) {
                    String newEmail = Validate.validateString();
                    if (userService.existEmail(newEmail)) {
                        System.out.println(RED+"Email đã tồn tại. Mời nhập email khác: "+RESET);
                    } else {
                        userLogin.setEmail(newEmail);
                        break;
                    }
                }
                System.out.println(GREEN+"Đổi email thành công !"+RESET);
                break;
            case 5:
                System.out.println("Số điện thoại hiện tại là: " + userLogin.getPhone());
                System.out.println("Mời nhập số điện thoại mới: ");
                String newPhone = Validate.validatePhoneNumber();
                userLogin.setPhone(newPhone);
                System.out.println(GREEN+"Đổi số điện thoại thành công !"+RESET);
                break;
            case 0:
                return;
            default:
                System.out.println("Không có lựa chọn này. Hãy chọn lại: ");
                break;
        }
        userService.save(userLogin);
        new Config<>().writeFile(Config.URL_USER_LOGIN,userLogin);
        System.out.println(userLogin);
    }
}
