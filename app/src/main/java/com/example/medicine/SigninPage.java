package com.example.medicine;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SigninPage extends AppCompatActivity {

    private Button signinBtn;
    private EditText mobileNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);




        signinBtn = (Button)findViewById(R.id.signinBtnID);
        mobileNumber = (EditText)findViewById(R.id.signinNumberID);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                signinBtn.setEnabled(false);
                signinBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                signinBtn.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));
//                signinBtn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#979797")));

                try {
                    signUpWithOtp(mobileNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                Intent intent = new Intent(SigninPage.this,Varification.class);
//                startActivity(intent);
//                finish();
            }

        });

        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mobileNumber.getText())){
                    signinBtn.setEnabled(true);
                    signinBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4286F5")));
                    signinBtn.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
//                    signinBtn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                }else {
                    signinBtn.setEnabled(false);
                    signinBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                    signinBtn.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));
//                    signinBtn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#979797")));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void signUpWithOtp(EditText phoneNumber) throws JSONException {

        progressDialog = new ProgressDialog(SigninPage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Register_datatype phone = new Register_datatype(phoneNumber.getText().toString(),null,null);
        Log.d("TAG", "phone: Code: " + phone.getPhone());
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new ApiEnv().base_url())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequest api = retrofit.create(ApiRequest.class);
        Call<Register_datatype> call = api.createRegisterWithOTP(phone);
        call.enqueue(new Callback<Register_datatype>() {
            @Override
            public void onResponse(Call<Register_datatype> call, Response<Register_datatype> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", "onResponse: Code: " + response.code());
                    return;
                }
                progressDialog.dismiss();
                 Log.d("TAG", "onResponse: ResponseCode: " + response.code());
                    Register_datatype res_data = response.body();
                    Intent intent = new Intent(SigninPage.this,Varification.class)
                    .putExtra("token", "Bearer "+res_data.getToken());
                    startActivity(intent);
                    finish();
            }

            @Override
            public void onFailure(Call<Register_datatype> call, Throwable t) {
                Log.d("TAG", "CheckResponse: onFailure: " + t.getLocalizedMessage());
            }
        });

    }

    protected void onStart() {
        super.onStart();

        //for connetivity detection
        SharedPreferences sp = getSharedPreferences("user_token", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);




        if (token!=null){
            token = "Bearer "+token;
            Intent intent = new Intent(SigninPage.this,MainActivity.class);
            intent.putExtra("token",token);
            startActivity(intent);
            Toast.makeText(this, "User Sign in", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "user not sign in", Toast.LENGTH_SHORT).show();
        }
    }
}