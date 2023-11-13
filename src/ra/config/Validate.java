package ra.config;

import static ra.config.Color.RED;
import static ra.config.Color.RESET;

public class Validate {
    public static int validateInt() {
        int n;
        while (true) {
            try {
                n = Integer.parseInt(Config.scanner().nextLine());
                if (n >= 0) { // Kiểm tra số không âm
                    break;
                } else {
                    System.out.println(RED+"Số không được âm. Mời nhập lại: "+RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED+"Sai định dạng. Mời nhập lại: "+RESET);
            }
        }
        return n;
    }

    public static String validateEmail() {
        String email;
        while (true) {
            email = Config.scanner().nextLine();
            if (email.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}")) {
                break;
            } else {
                System.out.println(RED+"Email không đúng định dạng mời nhập lại: "+RESET);
            }
        }
        return email;
    }

    public static String validateString() {
        String s;
        while (true) {
            s = Config.scanner().nextLine();
            if (s.trim().isEmpty()) {
                System.out.println(RED+"Không được để trống, mời nhập lại: "+RESET);
            } else {
                break;
            }
        }
        return s;
    }

    public static String validatePhoneNumber() {
        String phoneNumber;
        while (true) {
            phoneNumber = Config.scanner().nextLine();
            if (phoneNumber.isEmpty()) {
                System.out.println(RED+"Không được để trống, mời nhập lại: "+RESET);
            } else if (phoneNumber.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                break;
            } else {
                System.out.println(RED+"Không đúng định dạng số điện thoại, mời nhập lại:  "+RESET);
            }
        }
        return phoneNumber;
    }
}
