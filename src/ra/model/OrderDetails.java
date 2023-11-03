package ra.model;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private int orderId;
    private String productName;
    private double unitPrice;
    private int quantity;


    public OrderDetails() {
    }

    public OrderDetails(int productId, int orderId, String productName, double unitPrice, int quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "productId=" + productId +
                ", orderId=" + orderId +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                '}';
    }
}
