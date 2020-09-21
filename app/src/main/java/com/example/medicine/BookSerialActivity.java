package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookSerialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    String token;

    private Button appointment_button;
    private ProgressDialog progressDialog;
    String date_ame;
    int doctorID;
    private TextView patient_name,patient_age,patient_phone_number;
    List<DoctorScheduleModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_serial);
        toolbar = findViewById(R.id.specificCategoryToolbar);
        setSupportActionBar(toolbar);
        appointment_button = findViewById(R.id.place_appo);
        appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeappointment();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = getIntent().getStringArrayListExtra("days");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        doctorID = getIntent().getIntExtra("doctorID",1);

//      Toast.makeText(DoctorDetailsActivity.this, "Id id "+position, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Book Appointment");
    }

    private void placeappointment() {
        progressDialog = new ProgressDialog(BookSerialActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        patient_name = findViewById(R.id.patient_name);
        patient_age = findViewById(R.id.patient_age);
        patient_phone_number = findViewById(R.id.patient_phone_number);
        SharedPreferences sp = getSharedPreferences("user_token", Context.MODE_PRIVATE);
        token = sp.getString("token",null);
        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonObject p_pro = new JsonObject();
        p_pro.addProperty("patient_name",patient_name.getText().toString());
        p_pro.addProperty("doctor_id",doctorID);
        p_pro.addProperty("appo_date",date_ame);
        p_pro.addProperty("patient_age",patient_age.getText().toString());
        p_pro.addProperty("short_description",patient_phone_number.getText().toString());
        Log.d("token", "Token: " + p_pro.toString());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new ApiEnv().base_url())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequest api = retrofit.create(ApiRequest.class);
        Call<JsonObject> call = api.placeAppointment(token,p_pro);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Log.d("not success", "onResponse: Code: " + response.code());
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject != null) {
                        progressDialog.dismiss();
                        shoeDailog();
                        Log.d("jsonobject", "onResponse: Code: "+jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TAG", "CheckResponse: onFailure: " + t.getLocalizedMessage());
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String date = parent.getItemAtPosition(position).toString();
        date_ame = date.trim().split(" ")[0];

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void shoeDailog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookSerialActivity.this);

        // set title
        alertDialogBuilder.setTitle("ধন্যবাদ");

        // set dialog message
        alertDialogBuilder
                .setMessage("আপনার সিরিয়াল সপ্পন হয়েছে")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
//                .setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//
//                        dialog.cancel();
//                    }
//                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}