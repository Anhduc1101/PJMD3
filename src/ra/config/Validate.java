package ra.config;

public class Validate {
    public static int validateInt() {
        int n;
        while (true) {
            try {
                n = Integer.parseInt(Config.scanner().nextLine());
                if (n >= 0) { // Kiểm tra số không âm
                    break;
                } else {
                    System.out.println("Số không được âm. Mời nhập lại: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Sai định dạng. Mời nhập lại: ");
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
                System.out.println("Email không đúng định dạng mời nhập lại: ");
            }
        }
        return email;
    }

    public static String validateString() {
        String s;
        while (true) {
            s = Config.scanner().nextLine();
            if (s.trim().isEmpty()) {
                System.out.println("Không được để trống, mời nhập lại: ");
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
                System.out.println("Không được để trống, mời nhập lại: ");
            } else if (phoneNumber.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                break;
            } else {
                System.out.println("Không đúng định dạng số điện thoại, mời nhập lại:  ");
            }
        }
        return phoneNumber;
    }
}
