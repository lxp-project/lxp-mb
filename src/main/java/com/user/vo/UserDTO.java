package com.user.vo;

public class UserDTO {
    private long userId;
    private String name;
    private String role;

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDTO() {
    }

    public UserDTO(long userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "userId=" + userId +
            ", name='" + name + '\'' +
            ", role='" + role + '\'' +
            '}';
    }
}
