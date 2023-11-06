package ra.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private String name;
    private String phoneNumber;
    private String address;
    private double total;
    private OrderStatus orderStatus;
    private Map<Integer,Integer> orderDetails;
    private LocalDateTime orderAt;
//    private LocalDateTime deliveryAt;

    public Order() {
    }

    public Order(int orderId, int userId, String name, String phoneNumber, String address, double total, OrderStatus orderStatus, Map<Integer,Integer> orderDetails, LocalDateTime orderAt, LocalDateTime deliveryAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.total = total;
        this.orderStatus = orderStatus;
        this.orderDetails = orderDetails;
        this.orderAt = orderAt;
//        this.deliveryAt = deliveryAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Map<Integer,Integer> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Map<Integer,Integer> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

//    public LocalDateTime getDeliverAt() {
//        return deliveryAt;
//    }

//    public void setDeliverAt(LocalDateTime deliveryAt) {
//        this.deliveryAt = deliveryAt;
//    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", total=" + total +
                ", orderStatus=" + orderStatus +
                ", orderDetails=" + orderDetails +
                ", orderAt=" + orderAt +
//                ", deliveryAt=" + deliveryAt +
                '}';
    }
}
