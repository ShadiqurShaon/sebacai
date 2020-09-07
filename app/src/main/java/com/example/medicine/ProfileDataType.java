package com.example.medicine;

import com.google.gson.annotations.SerializedName;

public class ProfileDataType {
    @SerializedName("name")
    private String name;
    @SerializedName("profile_pic")
    private String profile_pic;
    @SerializedName("password")
    private String password;
    private String token;
    private String phone;

    private static ProfileDataType singleton;

    static {
        singleton = new ProfileDataType();
    }

    public static ProfileDataType getInstance() {
        return singleton;
    }

//    public ProfileDataType(String name, String profile_pic, String password) {
//        this.name = name;
//        this.profile_pic = profile_pic;
//        this.password = password;
//    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
