package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecificCategory extends AppCompatActivity {

    private List<SpecificCategoryModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private SpesificCategoryAdapter adapter;

    private ProfileDataType profileDataType;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_category);

        toolbar = findViewById(R.id.specificCategoryToolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("category");
        int position = getIntent().getIntExtra("position",1)+1;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);

        recyclerView = (RecyclerView)findViewById(R.id.specific_category_recyclerID);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

//        list.add(new SpecificCategoryModel(R.drawable.doctor_a,"Dr. Habiba Ahmed Rika",
//                "Cardiology,Medicine", "MBBS,BCS,MD(Cardiology)",
//                "Assistan Professor","National Institiute of Cardiovascular Deseases",
//                "700"));
        getAllDoctor(position);



    }

    private void getAllDoctor(int id)
    {
        progressDialog = new ProgressDialog(SpecificCategory.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

//        Toast.makeText(SpecificCategory.this, "Id id "+id, Toast.LENGTH_SHORT).show();
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new ApiEnv().getLocaldoctor())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequest api = retrofit.create(ApiRequest.class);
        Call<JsonObject> call = api.getalldoctor(ProfileDataType.getInstance().getToken(),id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Log.d("Spessic category", "onResponse: Code: " + response.code());
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    for(int i=0;i<jsonObject.getJSONArray("all_doctor").length();i++){
                        JSONObject doctor = jsonObject.getJSONArray("all_doctor").getJSONObject(i);
                        list.add(new SpecificCategoryModel(R.drawable.doctor_a,doctor.getString("name"),
                                doctor.getString("designation"),"MBBS,BCS,MD(Cardiology)",
                                "Rangpur medicle collage",
                                doctor.getString("location"),"500",doctor.getInt("id")));
                    }
                    Log.d("Doctor Details", "onResponse: Code: " + jsonObject);
                    adapter = new SpesificCategoryAdapter(list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
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