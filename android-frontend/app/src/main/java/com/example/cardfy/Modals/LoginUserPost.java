package com.example.cardfy.Modals;

public class LoginUserPost {

    private String email, password;

    public LoginUserPost(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginUserPost() {
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
        return "LoginUserPost{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
