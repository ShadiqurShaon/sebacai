package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class DoctorDetailsActivity extends AppCompatActivity implements BookAppointmentFragment.BookappointmentListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Button appointment_button,btnSubmit;
    private DoctorScheduleAdapter adapter;
    int doctorID;
    private TextView doctor_name,doctor_category,doctor_education,doctor_designation,doctor_institute,doctor_fees;
    List<DoctorScheduleModel>list = new ArrayList<>();
    private ProgressDialog progressDialog;
    ArrayList<String> dayname = new ArrayList<String>();
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
        appointment_button = findViewById(R.id.appointmentinBtnID);
        appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opendialog();
            }
        });


        doctorID = getIntent().getIntExtra("doctorID",1);

        setdoctorDetailsData(doctorID);
//        Toast.makeText(DoctorDetailsActivity.this, "Id id "+position, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Doctor Category");
    }

    private void opendialog() {

        startActivity(new Intent(DoctorDetailsActivity.this, BookSerialActivity.class)
                .putExtra("doctor_id",doctorID)
                .putStringArrayListExtra("days",dayname)

        );

//        BookAppointmentFragment dialog = new BookAppointmentFragment();
//        dialog.show(getSupportFragmentManager(),"Book Appointment");

    }

    public void setdoctorDetailsData(int doctorId){
        progressDialog = new ProgressDialog(DoctorDetailsActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                        dayname.add(schedule1.getString("schedule_day")+"  "+schedule1.getString("start_time")+"-"+schedule1.getString("end_time"));
                    }

                    adapter = new DoctorScheduleAdapter(list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    private void setFragment(Fragment fragment)
    {
            androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.appoint_fragment_layout,fragment);
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.commit();

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
    public void setPatientData(String p_name, String p_age, String appo_day, String short_dis) {
        Toast.makeText(this, "patient Name is"+p_name, Toast.LENGTH_LONG).show();

    }
}