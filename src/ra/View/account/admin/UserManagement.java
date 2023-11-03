package ra.View.account.admin;

import ra.config.Validate;
import ra.model.RoleName;
import ra.model.Users;
import ra.service.users.IUserService;
import ra.service.users.UserServiceIMPL;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ra.config.Color.RESET;

public class UserManagement {
    IUserService userService = new UserServiceIMPL();

    public void menu() {
        do {
            System.out.println("\033[1;94m╔═══════════════════════ QUẢN LÝ NGƯỜI DÙNG ═════════════════════╗");
            System.out.println("\033[1;94m║                                                                ║");
            System.out.println("\033[1;94m║              \033[1;97m1. Hiển thị danh sách" + RESET + "\033[1;94m                             ║");
            System.out.println("\033[1;94m║              \033[1;97m2. Tìm kiếm" + RESET + "\033[1;94m                                       ║");
            System.out.println("\033[1;94m║              \033[1;97m3. Khóa/ Mở khóa người dùng" + RESET + "\033[1;94m                       ║");
            System.out.println("\033[1;94m║              \033[1;97m4. Thay đổi vai trò của người dùng" + RESET + "\033[1;94m                ║");
            System.out.println("\033[1;94m║              \033[1;97m0. Thoát" + RESET + "\033[1;94m                                          ║");
            System.out.println("\033[1;94m║                                                                ║");
            System.out.println("\033[1;94m╚════════════════════════════════════════════════════════════════╝" + RESET);
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
                        if (users.getUsername().toLowerCase().contains(inputName.toLowerCase())) {
                            System.out.println(users);
                            isFound = true;
//                            break;
                        }
                    }
                    System.out.println("Tìm kiếm thành công! ");
                    if (!isFound) {
                        System.out.println("Không có người dùng này! ");
                    }
                    break;
                case 3:
                    handleDisplay();
                    System.out.println("Nhập id bạn muốn block/unblock: ");
                    int number = Validate.validateInt();
                    for (Users users : userService.findAll()) {
                        if (users.getId() == number) {
                            System.out.println("Trạng thái hiện tại của " + users.getRole() + " là: " + (users.isStatus() ? "đang mở" : "bị khóa"));
                            System.out.println("Bạn có muốn " + (!users.isStatus() ? "mở khóa" : "khóa") + " tài khoản " + users.getRole() + " này không?");
                            System.out.println("1. Có");
                            System.out.println("2. Không");
                            switch (Validate.validateInt()) {
                                case 1:
                                    System.out.println("Có");
                                    users.setStatus(!users.isStatus());
                                    System.out.println("Trạng thái của tài khoản này được đổi thành: " + (users.isStatus() ? "đang mở" : "bị khóa"));
                                    userService.updateData();
                                    break;
                                case 2:
                                    System.out.println("Không");
                                    return;
                            }
                        }
                    }
                    break;
                case 4:
                    handleDisplay();
                    System.out.println("Nhập vào id người dùng bạn muốn đổi vai trò: ");
                    int idChangeRole = Validate.validateInt();
                    Users userEditRole = userService.findById(idChangeRole);
                    if (userEditRole == null) {
                        System.out.println("ID ban vừa nhập không tồn tại");
                    } else if (userEditRole.isStatus()) {
                        userEditRole.setRole(RoleName.ADMIN);
                        userService.save(userEditRole);
                        System.out.println("Đổi thành công !");
                    } else {
                        System.out.println("Tài khoản này đang bị khóa, không đổi được vai trò!");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    public void handleDisplay() {
        System.out.println("+-----------+--------------------+------------------------+------------------------+-------------------------+---------------------+--------------+-------------------+");
        System.out.println("                                                                          \033[1;94mDANH SÁCH NGƯỜI DÙNG" + RESET);
        System.out.println("+-----------+--------------------+------------------------+------------------------+-------------------------+---------------------+--------------+-------------------+");
        System.out.println("|  User ID  |        Name        |        Username        |        Password        |          Email          |        Status       |     Role     |       Phone       |");
        System.out.println("+-----------+--------------------+------------------------+------------------------+-------------------------+---------------------+--------------+-------------------+");
        userService.findAll().sort((u1, u2) -> u2.getId() - u1.getId());
        for (Users users : userService.findAll()) {
            System.out.println(users);
        }
        System.out.println("+-----------+--------------------+------------------------+------------------------+-------------------------+---------------------+--------------+-------------------+");

    }
}
