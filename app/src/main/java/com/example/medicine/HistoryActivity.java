package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button upcomming_button,past_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = findViewById(R.id.specificCategoryToolbar);
        setSupportActionBar(toolbar);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Appointment History");
        try {
        Fragment fragment = UpcommingAppointmentFragment.class.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.appo_history,fragment).commit();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (java.lang.InstantiationException e) {
        e.printStackTrace();
    }

        upcomming_button = findViewById(R.id.upcomming);
        past_button = findViewById(R.id.past);

        upcomming_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Log.d("Appo history", "upcomming");
                    Fragment fragment = UpcommingAppointmentFragment.class.newInstance();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.appo_history,fragment).commit();

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

        past_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d("Appo history", "past");
                    Fragment fragment_past_appo = pastAppointmentFragment.class.newInstance();
                    FragmentManager f_manager = getSupportFragmentManager();
                    f_manager.beginTransaction().replace(R.id.appo_history,fragment_past_appo).commit();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(HistoryActivity.this,MainActivity.class));
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