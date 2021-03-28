package com.example.cardfy.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoGet {

    @SerializedName("isVarified")
    @Expose
    private boolean isVarified;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("__v")
    @Expose
    private long v;
    @SerializedName("profile_image")
    @Expose
    private String profile_image;

    public UserInfoGet() {
    }

    public UserInfoGet(boolean isVarified, String id, String name, String email, String password, String username, long v, String profile_image) {
        super();
        this.isVarified = isVarified;
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.v = v;
        this.profile_image = profile_image;
    }

    public boolean isVarified() {
        return isVarified;
    }

    public void setVarified(boolean varified) {
        isVarified = varified;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "UserInfoGet{" +
                "isVarified=" + isVarified +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", v=" + v +
                ", profile_image=" + profile_image +
                '}';
    }
}
