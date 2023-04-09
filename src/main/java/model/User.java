package model;

import com.google.gson.annotations.SerializedName;

public class User {
    private String email;
    private String name;
    @SerializedName("user_id")
    private String userId;

    public User() {}
    public User(String email, String name, String userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
