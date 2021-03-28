package com.example.cardfy.Modals;

public class User {
    private String token, username, email, password, name, image_url;
    private boolean isVerified;

    public User(String token, String username, String email, String password, String name, boolean isVerified, String image_url) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.isVerified = isVerified;
        this.image_url = image_url;
    }

    public User() {
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", isVerified='" + isVerified + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
