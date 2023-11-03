package ra.View;

import ra.View.account.admin.MenuAdmin;
import ra.View.account.user.MenuUser;
import ra.config.Config;
import ra.config.Validate;
import ra.model.RoleName;
import ra.model.Users;
import ra.service.users.IUserService;
import ra.service.users.UserServiceIMPL;

import static ra.config.Color.RESET;


public class Home {
    IUserService userService = new UserServiceIMPL();

    public void menu() {
        do {
            for (Users users : userService.findAll()) {
                System.out.println(users);
            }
            System.out.println("\033[1;94m╔═══════════════ TRANG CHỦ ═══════════════╗");
            System.out.println("\033[1;94m║                                         ║");
            System.out.println("\033[1;94m║              \033[1;97m1. Đăng nhập"+RESET+"\033[1;94m               ║");
            System.out.println("\033[1;94m║              \033[1;97m2. Đăng ký"+RESET+"\033[1;94m                 ║");
            System.out.println("\033[1;94m║              \033[1;97m0. Đăng xuất"+RESET+"\033[1;94m               ║");
            System.out.println("\033[1;94m║                                         ║");
            System.out.println("\033[1;94m╚═════════════════════════════════════════╝"+RESET);
            System.out.print("Mời lựa chọn: ");
            switch (Validate.validateInt()) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    System.out.println("Cảm ơn đã sử dụng chương trình ! \uD83D\uDE47");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void register() {
        System.out.println(" *** FORM REGISTER *** ");
        Users users = new Users();
        users.setId(userService.getNewId());
        System.out.println("Nhập họ tên: ");
        users.setName(Validate.validateString());
        System.out.println("Nhập tên tài khoản: ");
        while (true) {
            String username = Validate.validateString();
            if (userService.existUsername(username)) {
                System.out.println("Tên đăng nhập đã tồn tại. Mời nhập lại: ");
            } else {
                users.setUsername(username);
                break;
            }
        }
        System.out.println("Nhập mật khẩu: ");
        users.setPassword(Validate.validateString());
        System.out.println("Xác nhận mật khẩu: ");
        while (true) {
            String repeatPass = Validate.validateString();
            if (users.getPassword().equals(repeatPass)) {
                break;
            } else {
                System.out.println("Nhập lại mật khẩu chưa đúng. Mời nhập lại: ");
            }
        }

        System.out.println("Nhập email: ");
        while (true) {
            String email = Validate.validateEmail();
            if (userService.existEmail(email)) {
                System.out.println("Email đã tồn tại!");
            } else {
                users.setEmail(email);
                break;
            }
        }

        System.out.println("Nhập số điện thoại: ");
        users.setPhone(Validate.validatePhoneNumber());
        userService.save(users);
        System.out.println("Tạo tài khoản thành công");
//        login();
    }

    private void login() {
        System.out.println(" *** FORM LOGIN *** ");
        System.out.println("Nhập tên tài khoản: ");
        String name = Validate.validateString();
        System.out.println("Nhập mật khẩu: ");
        String pass = Validate.validateString();
        Users users = userService.checkLogin(name, pass);
        if (users == null) {
            System.out.println("Tài khoản hoặc mật khẩu không đúng!");
        } else {
            checkRoleLogin(users);
        }
    }

    public void checkRoleLogin(Users users) {
        // đúng tên tài khoản
        if (users.getRole().equals(RoleName.ADMIN)) {
//                userLogin = users;
            new Config<Users>().writeFile(Config.URL_USER_LOGIN, users);// ghi đối tượng Users đang đăng nhập vào file
            // chuyển đến trang quản lý admin
            System.out.println("Đăng nhập thành công! ");
            new MenuAdmin().menuAdmin();
        } else {
            if (users.isStatus()) {
                // chuyển đến trang user
//                    userLogin = users;
                new Config<Users>().writeFile(Config.URL_USER_LOGIN, users);
                System.out.println("Đăng nhập thành công! ");
                new MenuUser().menuUser();
//                    user();
            } else {
                System.out.println("Tài khoản của bạn đang bị khóa, liên hệ admin để mở khóa !");
            }
        }
    }
}
