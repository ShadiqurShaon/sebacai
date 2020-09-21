package com.example.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Varification extends AppCompatActivity {

    private EditText otpCode;
    private Button otpButton;
    private String token;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification);

        otpCode = (EditText)findViewById(R.id.varificationOTPID);
        otpButton = (Button)findViewById(R.id.varificationBtnID);

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpButton.setEnabled(false);
                otpButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                otpButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));
                token = getIntent().getStringExtra("token");
                getverifyOtp(otpCode.getText().toString(),token);
//                Intent intent = new Intent(Varification.this,MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        otpCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }





            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(otpCode.getText())){
                    if (otpCode.getText().length() == 5){
                        otpButton.setEnabled(true);
                        otpButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4286F5")));
                        otpButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));

                    }else {
                        otpButton.setEnabled(false);
                        otpButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                        otpButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getverifyOtp(String otp,String token) {

        progressDialog = new ProgressDialog(Varification.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new ApiEnv().base_url())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequest api = retrofit.create(ApiRequest.class);
        JsonObject otpjson = new JsonObject();
        otpjson.addProperty("otp",otp);
        Call<JsonObject> call = api.verifyOtp(token,otpjson);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", "onResponse: Code: " + response.code());

                    return;
                }
                Log.d("verification", "onResponse: Code: " + response.message());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.d("Token", "onResponse: Code: " +jsonObject.toString());
                    if (jsonObject != null) {
                        String token2 = "Bearer " + jsonObject.getString("token");
                        progressDialog.dismiss();

                        startActivity(new Intent(Varification.this, ProfileActivity.class)
                                .putExtra("token", token2)
                        );
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "CheckResponse: onFailure: " + t.getLocalizedMessage());
            }
        });



    }
}