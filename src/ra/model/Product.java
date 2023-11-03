package ra.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import static ra.config.Color.RESET;

public class Product implements Serializable, Comparable<Product> {
    private static final long serialVersionUID = 1L;
    private int productId;
    private String productName;
    private Category category;
    private String description;
    private double unitPrice;
    private int stock;
    private boolean status = true;

    public Product() {
    }

    public Product(int productId, String productName, Category category, String description, double unitPrice, int stock, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    @Override
//    public String toString() {
//        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        return "Product{" +
//                "productId=" + productId +
//                ", productName='" + productName + '\'' +
//                ", category=" + category.getCategoryId() +
//                ", description='" + description + '\'' +
//                ", unitPrice=" + String.format(currencyFormat.format(unitPrice)) +
//                ", stock=" + stock +
//                ", status=" + (status?"đang bán":"ngừng bán") +
//                '}';
//    }

    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String format = "       %-8s|  %-15s         |  %-20s         |       %-10s    |    %-5s  |  %-19s|  %-15s       ";
        return String.format(format, productId, productName, (description.length() > 25 ? (description.substring(0, 25) + "...") : description), String.format(currencyFormat.format(unitPrice)), stock, category.getCategoryName(), (status ? "\033[0;32mMở bán"+RESET : "\033[0;31mNgừng bán"+RESET));
    }


    @Override
    public int compareTo(Product o) {
        return (int) -(o.getUnitPrice() - this.getUnitPrice());
    }
}
