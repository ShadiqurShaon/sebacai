package com.example.medicine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final int CATEGORY_FRAGMENT=0;
    private static final int TRIP_HISTORY_FRAGMENT=1;
    private static final int DISCOUNT_FRAGMENT=2;
    private static final int SETTINGS_FRAGMENT=3;
    private static final int HELP_FRAGMENT=4;

    private ImageView profileimage;
    private TextView name;
    private TextView phone;

    private FrameLayout frameLayout;
    private NavigationView navigationView;
    public static DrawerLayout drawer;

    private Window window;
    private Toolbar toolbar;

    private String token;
    Context context;

    private int currentFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = getIntent().getStringExtra("token");

        setProfile(token);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        context = getApplicationContext();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout=(FrameLayout)findViewById(R.id.mainFrameLayoutID);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        gotoFragment("Easy Health Care",new CategoryFragment(),CATEGORY_FRAGMENT);

    }

    private void setProfile(String token) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new ApiEnv().base_url())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequest api = retrofit.create(ApiRequest.class);
        Call<JsonObject> call = api.getUserProfile(token);
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
                        profileimage =  (ImageView) findViewById(R.id.drawerPprofileID);
                        name =  (TextView) findViewById(R.id.drawerNameID);
                        phone =  (TextView) findViewById(R.id.drawerMobileID);
                        name.setText(jsonObject.getJSONObject("patientdetails").getString("name").toString());
                        phone.setText(jsonObject.getJSONObject("patientdetails").getString("phone").toString());
                        Glide.with(context).load(jsonObject.getJSONObject("patientdetails").getString("profile_pic_url").toString()).into(profileimage);
                        ProfileDataType.getInstance().setName(jsonObject.getJSONObject("patientdetails").getString("name").toString());
                        ProfileDataType.getInstance().setPhone(jsonObject.getJSONObject("patientdetails").getString("phone").toString());
                        ProfileDataType.getInstance().setProfile_pic(jsonObject.getJSONObject("patientdetails").getString("profile_pic_url").toString());


                        Log.d("jsonobject", "onResponse: Code: "+jsonObject);
                    }
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
    public void onBackPressed() {
        if (currentFragment == CATEGORY_FRAGMENT){
            currentFragment = -1;
            super.onBackPressed();
        }else {
            gotoFragment("Easy Health Care", new CategoryFragment(),CATEGORY_FRAGMENT);
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();

        if (id == R.id.nav_home){
            gotoFragment("Easy Health Care",new CategoryFragment(),CATEGORY_FRAGMENT);
        }else if (id == R.id.nav_trip_hostory){
            gotoFragment(getString(R.string.trip_history_title),new TripHistoryFragment(),TRIP_HISTORY_FRAGMENT);
        }else if (id == R.id.nav_discount){
            gotoFragment(getString(R.string.discount_title),new DiscountFragment(),DISCOUNT_FRAGMENT);
        }else if (id == R.id.nav_setting){
            gotoFragment(getString(R.string.settings_title),new SettingsFragment(),SETTINGS_FRAGMENT);
        }else if (id == R.id.nav_help){
            gotoFragment(getString(R.string.help_title),new HelpFragment(),HELP_FRAGMENT);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void gotoFragment(String title, Fragment fragment, int fragment_no){
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment,fragment_no);
    }

    private void setFragment(Fragment fragment, int fragment_No){
        if (fragment_No != currentFragment){
            currentFragment = fragment_No;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayout.getId(),fragment);
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.commit();
        }
    }
}