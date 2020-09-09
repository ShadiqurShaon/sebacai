package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DoctorScheduleAdapter adapter;
    private TextView doctor_name,doctor_category,doctor_education,doctor_designation,doctor_institute,doctor_fees;
    List<DoctorScheduleModel>list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        toolbar = findViewById(R.id.specificCategoryToolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.doctorSchedule);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        doctor_name = findViewById(R.id.doctorsDetailsNameID);
        doctor_category = findViewById(R.id.doctorsDetailsCaategoryID);
        doctor_education = findViewById(R.id.doctorsDetailsEducationID);
        doctor_designation = findViewById(R.id.doctorsDetailsDesignationID);
        doctor_institute = findViewById(R.id.doctorsDetailsInstituteID);
        doctor_fees = findViewById(R.id.doctorsDetailsFeeID);

        int doctorID = getIntent().getIntExtra("doctorID",1);
        setdoctorDetailsData(doctorID);
//        Toast.makeText(DoctorDetailsActivity.this, "Id id "+position, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Doctor Category");
    }

    public void setdoctorDetailsData(int doctorId){

        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new ApiEnv().getLocaldoctor())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequest api = retrofit.create(ApiRequest.class);
        Call<JsonObject> call = api.doctordetails(doctorId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Log.d("NOT SUCCESS", "onResponse: Code: " + response.code());
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.d("success", "onResponse: Code: " + jsonObject);
                    JSONObject personaldetails = jsonObject.getJSONObject("doctordetails");
                    doctor_name.setText(personaldetails.getString("name"));
                    doctor_category.setText("বিশেষজ্ঞ");
                    doctor_designation.setText(personaldetails.getString("designation"));
                    doctor_education.setText("MBBS,FCPS,FRCS");
                    doctor_institute.setText("ঢাকা মেডিকেল কলেজ");
                    doctor_fees.setText("500");
                    JSONArray schedule = personaldetails.getJSONArray("schedule");
                    Log.d("success", "onResponse: Code: " + personaldetails);
                    Log.d("success", "onResponse: Code: " + schedule.toString());

                    for(int i=0;i<schedule.length();i++){
                        JSONObject schedule1 = schedule.getJSONObject(i);
                        list.add(new DoctorScheduleModel(schedule1.getString("schedule_day"),schedule1.getString("start_time")+"থেকে"+schedule1.getString("end_time")));
                    }

                    adapter = new DoctorScheduleAdapter(list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

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
}