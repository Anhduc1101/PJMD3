package ra.View.account.admin;

import ra.config.Utils;
import ra.config.Validate;
import ra.model.RoleName;
import ra.model.Users;
import ra.service.users.IUserService;
import ra.service.users.UserServiceIMPL;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ra.config.Color.*;

public class UserManagement {
    IUserService userService = new UserServiceIMPL();

    public void menu() {
        do {
            System.out.println("\033[1;94m╔══════════════════ QUẢN LÝ NGƯỜI DÙNG ════════════════╗");
            System.out.println("\033[1;94m║" + RESET + "                   " + Utils.getCurrentDateTime() + " \033[1;94m               ║");
            System.out.println("\033[1;94m║══════════════════════════════════════════════════════║" + RESET);
            System.out.println("\033[1;94m║          \033[1;97m1. Hiển thị danh sách" + RESET + "\033[1;94m                       ║");
            System.out.println("\033[1;94m║          \033[1;97m2. Tìm kiếm" + RESET + "\033[1;94m                                 ║");
            System.out.println("\033[1;94m║          \033[1;97m3. Khóa/ Mở khóa người dùng" + RESET + "\033[1;94m                 ║");
            System.out.println("\033[1;94m║          \033[1;97m4. Thay đổi vai trò của người dùng" + RESET + "\033[1;94m          ║");
            System.out.println("\033[1;94m║          \033[1;97m0. Quay lại" + RESET + "\033[1;94m                                 ║");
            System.out.println("\033[1;94m╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (1/2/3/4/0): ");
            switch (Validate.validateInt()) {
                case 1:
                    List<Users> usersList = userService.findAll();
                    Collections.sort(usersList, new Comparator<Users>() {
                        @Override
                        public int compare(Users o1, Users o2) {
                            return o1.getUsername().compareToIgnoreCase(o2.getUsername());
                        }
                    });
                    handleDisplay();
                    break;
                case 2:
                    System.out.println("Nhập tên người dùng bạn muốn tìm kiếm: ");
                    String inputName = Validate.validateString();
                    boolean isFound = false;
                    for (Users users : userService.findAll()) {
                        if (users.getUsername().toLowerCase().contains(inputName.toLowerCase()) || users.getName().toLowerCase().contains(inputName.toLowerCase())) {
                            System.out.println("+-----------------+-------------------+-------------------+--------------------+-------------------------+--------------------+---------------+-------------------+");
                            System.out.println("                                                                          \033[1;94mDANH SÁCH NGƯỜI DÙNG" + RESET);
                            System.out.println("+-----------------+-------------------+-------------------+--------------------+-------------------------+--------------------+---------------+-------------------+");
                            System.out.println("|  Mã người dùng  |        Tên        |   Tên đăng nhập   |      Mật khẩu      |          Email          |     Trạng thái     |    Vai trò    |   Số điện thoại   |");
                            System.out.println("+-----------------+-------------------+-------------------+--------------------+-------------------------+--------------------+---------------+-------------------+");
                            System.out.println(users);
                            System.out.println("+-----------------+-------------------+-------------------+--------------------+-------------------------+--------------------+---------------+-------------------+");
                            isFound = true;
                        }
                    }
                    System.out.println(GREEN+"Tìm kiếm thành công! "+RESET);
                    if (!isFound) {
                        System.out.println(RED+"Không có người dùng này! "+RESET);
                    }
                    break;
                case 3:
                    handleDisplay();
                    System.out.println("Nhập id bạn muốn khóa/mở khóa: ");
                    int number = Validate.validateInt();
                    boolean findAdmin=true;
                    for (Users users : userService.findAll()) {
                        if (users.getId() == number && number > 0) {
                            System.out.println("Trạng thái hiện tại của " + users.getRole() + " là: " + (users.isStatus() ? "Đang hoạt động" : "Đã bị khóa"));
                            System.out.println("Bạn có muốn " + (!users.isStatus() ? "mở khóa" : "khóa") + " tài khoản " + users.getRole() + " này không?");
                            System.out.println("1. Có");
                            System.out.println("2. Không");
                            switch (Validate.validateInt()) {
                                case 1:
                                    System.out.println("Có");
                                    users.setStatus(!users.isStatus());
                                    System.out.println("Tài khoản này đã: " + (users.isStatus() ? GREEN+"được mở khóa"+RESET : RED+"bị khóa"+RESET));
                                    findAdmin=false;
                                    userService.updateData();
                                    break;
                                case 2:
                                    System.out.println("Không");
                                    return;
                                default:
                                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                                    break;
                            }
                        }
                    }
                    if(findAdmin){
                        System.out.println(RED+"Đây là tài khoản quản trị gốc, không thể bị khóa"+RESET);
                        return;
                    }
                    break;
                case 4:
                    handleDisplay();
                    System.out.println("Nhập vào id người dùng bạn muốn đổi vai trò: ");
                    int idChangeRole = Validate.validateInt();
                    Users userEditRole = userService.findById(idChangeRole);
                    if (userEditRole == null) {
                        System.out.println(RED+"ID ban vừa nhập không tồn tại"+RESET);
                    } else if (userEditRole.getId()==0) {
                        System.out.println(RED+"Đây là tài khoản gốc, không thể bị thay đổi"+RESET);
                        break;
                    } else if (userEditRole.isStatus() && userEditRole.getRole() == RoleName.USER) {
                        userEditRole.setRole(RoleName.ADMIN);
                        userService.save(userEditRole);
                        System.out.println(GREEN+"Đổi thành công !"+RESET);
                    } else if (userEditRole.isStatus() && userEditRole.getRole() == RoleName.ADMIN) {
                        userEditRole.setRole(RoleName.USER);
                        userService.save(userEditRole);
                        System.out.println(GREEN+"Đổi thành công !"+RESET);

                    } else {
                        System.out.println(RED+"Tài khoản này đang bị khóa, không đổi được vai trò!"+RESET);
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                    break;
            }
        } while (true);
    }

    public void handleDisplay() {
        System.out.println("+-----------------+-------------------+-------------------+--------------------+-------------------------+--------------------+---------------+-------------------+");
        System.out.println("                                                                          \033[1;94mDANH SÁCH NGƯỜI DÙNG" + RESET);
        System.out.println("+-----------------+-------------------+-------------------+--------------------+-------------------------+--------------------+---------------+-------------------+");
        System.out.println("|  Mã người dùng  |        Tên        |   Tên đăng nhập   |      Mật khẩu      |          Email          |     Trạng thái     |    Vai trò    |   Số điện thoại   |");
        System.out.println("+-----------------+-------------------+-------------------+--------------------+-------------------------+--------------------+---------------+-------------------+");
        userService.findAll().sort((u1, u2) -> u2.getId() - u1.getId());
        for (Users users : userService.findAll()) {
            System.out.println(users);
        }
        System.out.println("+-----------------+-------------------+-------------------+--------------------+-------------------------+--------------------+---------------+-------------------+");

    }
}
