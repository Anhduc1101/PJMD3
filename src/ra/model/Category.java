package ra.model;

import java.io.Serializable;

import static ra.config.Color.RESET;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private int categoryId;
    private String categoryName;
    private String description;
    private boolean status=true;

    public Category() {
    }

    public Category(int categoryId, String categoryName, String description, boolean status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String format = "       %-9s|  %-15s          |  %-20s         |  %-20s        ";
        return String.format(format, categoryId, categoryName, (description.length() > 25 ? (description.substring(0, 25) + "...") : description), (status ? "\033[0;32mĐang hoạt động"+RESET : "\033[0;31mNgừng hoạt động"+RESET));
    }
}
