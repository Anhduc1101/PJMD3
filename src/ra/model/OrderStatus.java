package ra.model;

import static ra.config.Color.*;

public enum OrderStatus {
    WAITING("Chờ xử lý"),
    CONFIRM(GREEN+"Đã xác nhận"+RESET),
    DELIVERY("Đang giao hàng"),
    SUCCESS("Thành công"),
    CANCEL(RED+"Bị hủy"+RESET);

    private final String vietnameseName;

    OrderStatus(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
