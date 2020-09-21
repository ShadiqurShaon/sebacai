package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 2;
    private String token;
    private EditText name;
    private EditText password;
    private Button profileUpdateButton;
    private ImageView imageview;
    private Uri mImageUri;
    String image_url;
    private ProgressDialog progressDialog;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        token = getIntent().getStringExtra("token");
        name = findViewById(R.id.pro_name);
        password = findViewById(R.id.pro_password);
        profileUpdateButton = (Button)findViewById(R.id.profileUpdateBtn);
        context = getApplicationContext();
        imageview = findViewById(R.id.profile_image);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });


        profileUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Gson gson = new GsonBuilder().serializeNulls().create();
//                ProfileDataType profile = new ProfileDataType(name.getText().toString(),image_url,password.getText().toString());

                JsonObject p_pro = new JsonObject();
                p_pro.addProperty("name",name.getText().toString());
                p_pro.addProperty("password",password.getText().toString());
                p_pro.addProperty("profile_pic",image_url);
                Log.d("Name", p_pro.toString());
                Log.d("Name", token);
//                profile.setToken(token);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(new ApiEnv().base_url())
                        //.addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                ApiRequest api = retrofit.create(ApiRequest.class);
                Call<JsonObject> call = api.profileUpdate(token,p_pro);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Log.d("TAG", "onResponse: Code: " + response.code());
                            return;
                        }


                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());

                            if (jsonObject != null) {

                                String user_name = jsonObject.getJSONObject("user").getString("name").toString();
                                String user_phone =jsonObject.getJSONObject("user").getString("phone").toString();
                                String user_propic  = jsonObject.getJSONObject("user").getString("profile_pic_url").toString();

                                SharedPreferences sharedPref = getSharedPreferences(
                                        "user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("name",user_name);
                                editor.putString("phone",user_phone);
                                editor.putString("image",user_propic);
                                editor.commit();
                                progressDialog.dismiss();
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class)
                        );
                        finish();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        SharedPreferences sharedPref = getSharedPreferences(
//                                "user_token", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putString("token",token);
//                        editor.putString("name",name.getText().toString());
//                        editor.commit();
//                        progressDialog.dismiss();
//                        startActivity(new Intent(ProfileActivity.this, MainActivity.class)
//                                .putExtra("token", token)
//                        );
//                        finish();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("TAG", "CheckResponse: onFailure: " + t.getLocalizedMessage());

                    }
                });
            }
        });


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(password.getText())){

                    profileUpdateButton.setEnabled(true);
                    profileUpdateButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4286F5")));
                    profileUpdateButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));

                    }else {
                    profileUpdateButton.setEnabled(false);
                    profileUpdateButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                    profileUpdateButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(password.getText())){

                    profileUpdateButton.setEnabled(true);
                    profileUpdateButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4286F5")));
                    profileUpdateButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));

                }else {
                    profileUpdateButton.setEnabled(false);
                    profileUpdateButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                    profileUpdateButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    private void upload() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK){
            mImageUri = data.getData();
            try {
                uploadImage();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() throws URISyntaxException {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (mImageUri != null) {
            String path = mImageUri.toString();
//            Log.d("path", "onResponse: Code: " + path);
            File file = new File(new URI(path));
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", file.getName(),reqBody);
            Gson gson = new GsonBuilder().serializeNulls().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(new ApiEnv().base_url())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            ApiRequest api = retrofit.create(ApiRequest.class);
            Call<JsonObject> call =  api.imageupload(token,partImage);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (!response.isSuccessful()) {
                        Log.d("NOT SUCCESS", "onResponse: Code: " + response.toString());

                        return;
                    }
                    Log.d("SUCCESS", "onResponse: Code: " + response.code());

                    Toast.makeText(ProfileActivity.this, "Profile Picture save successfully ", Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        if (jsonObject != null) {
                            image_url = jsonObject.getString("url");
                            Glide.with(context).load(image_url).into(imageview);
                            pd.dismiss();
                          Toast.makeText(ProfileActivity.this,"Image upload successfull",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("TAG", "CheckResponse: onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(ProfileActivity.this,"image not uploaded please try again"+image_url,Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });
        }
    }

}