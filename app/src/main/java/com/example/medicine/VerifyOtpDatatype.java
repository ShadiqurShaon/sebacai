package com.example.medicine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpDatatype {
    @SerializedName("otp")
    @Expose
    String otp;

    public VerifyOtpDatatype(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
