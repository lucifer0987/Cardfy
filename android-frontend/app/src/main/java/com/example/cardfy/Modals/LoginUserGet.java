package com.example.cardfy.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUserGet {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("sucess")
    @Expose
    private boolean sucess;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_image")
    @Expose
    private String profile_image;

    public LoginUserGet() {
    }

    public LoginUserGet(String token, boolean sucess, String username, String email, String profile_image) {
        super();
        this.token = token;
        this.sucess = sucess;
        this.username = username;
        this.email = email;
        this.profile_image = profile_image;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
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

    @Override
    public String toString() {
        return "LoginUserGet{" +
                "token='" + token + '\'' +
                ", sucess=" + sucess +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", profile_image='" + profile_image + '\'' +
                '}';
    }
}
