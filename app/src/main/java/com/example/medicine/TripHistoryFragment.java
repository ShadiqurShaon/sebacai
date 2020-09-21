package com.example.medicine;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class TripHistoryFragment extends Fragment {
    private Button upcomming_button,past_button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_trip_history, container, false);
        upcomming_button = fragment.findViewById(R.id.upcomming);
        past_button = fragment.findViewById(R.id.past);
        upcomming_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Your upcomming Appointment", Toast.LENGTH_SHORT).show();
                try {
                    Fragment fragment = UpcommingAppointmentFragment.class.newInstance();
                    FragmentManager fragmentManager = getChildFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent,fragment);

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
//                Toast.makeText(getContext(), "Your previous Appointment", Toast.LENGTH_SHORT).show();
                try {
                    Fragment fragment_past_appo = pastAppointmentFragment.class.newInstance();
                    FragmentManager f_manager = getChildFragmentManager();
                    f_manager.beginTransaction().replace(R.id.flContent,fragment_past_appo);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}