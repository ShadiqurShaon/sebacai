package com.example.medicine;

import android.widget.EditText;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register_datatype {

    @SerializedName("phone")
    @Expose
    String phone;
    String token;
    String message;

    public Register_datatype(String phone, String token, String message) {
        this.phone = phone;
        this.token = token;
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
