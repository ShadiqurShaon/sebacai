package com.example.medicine;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiRequest {
    @Headers({"Content-Type: application/json"})
    @POST("registerotp")
    Call<Register_datatype> createRegisterWithOTP(@Body Register_datatype data);

    @Headers({"Content-Type: application/json"})
    @POST("verifyotp")
    Call<JsonObject> verifyOtp(@Header("Authorization") String token, @Body JsonObject otp);

    @Multipart
    @POST("imageUpload")
    Call<JsonObject> imageupload(@Header("Authorization") String token,@Part MultipartBody.Part image);

    @Headers({"Content-Type: application/json"})
    @POST("patientprofile")
    Call<JsonObject> profileUpdate(@Header("Authorization") String token,@Body JsonObject pro);

    @Headers({"Content-Type: application/json"})
    @GET("patientDetails")
    Call<JsonObject> getUserProfile(@Header("Authorization") String token);


    @Headers({"Content-Type: application/json"})
    @GET("getalldoctor/{id}")
    Call<JsonObject> getalldoctor(@Header("Authorization") String token,@Path("id") int id);


    @Headers({"Content-Type: application/json"})
    @GET("viewdoctor/{id}")
    Call<JsonObject> doctordetails(@Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @POST("patientAppointment")
    Call<JsonObject> placeAppointment(@Header("Authorization") String token,@Body JsonObject pro);
}
