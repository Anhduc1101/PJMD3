package ra.model;

import java.io.Serializable;

import static ra.config.Color.RESET;

public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean status = true;
    private RoleName role = RoleName.USER;
    private String phone;

    public Users() {
    }


    public Users(int id, String name, String username, String password, String email, boolean status, RoleName role, String phone) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.role = role;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        String format = "        %-10s|  %-15s  |  %-15s  |  %-15s   |  %-20s   |  %-25s    |  %-10s   |  %-15s   ";
        return String.format(format, id, name, username, password, email, (status ? "\033[0;32mĐang hoạt động"+RESET : "\033[0;31mĐã bị khóa"+RESET), role, phone);
    }

    @Override
    public boolean equals(Object obj) {
        Users u = (Users) obj;
        return this.id == u.getId();
    }
}
