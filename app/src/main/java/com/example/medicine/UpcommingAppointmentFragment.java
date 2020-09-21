package com.example.medicine;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcommingAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcommingAppointmentFragment extends Fragment {
    private List<PastAppointmentDatatype> list = new ArrayList<>();
    private int mColumnCount = 1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpcommingAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcommingAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcommingAppointmentFragment newInstance(String param1, String param2) {
        UpcommingAppointmentFragment fragment = new UpcommingAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_upcomming_appointment, container, false);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcomming_appointment, container, false);

        list.add(new PastAppointmentDatatype("1","patient_name","doctor_name","Doctor_location","Doctor designamtion","5-5-2020"));
        list.add(new PastAppointmentDatatype("2","patient_name1","doctor_name1","Doctor_location1","Doctor designamtion1","5-5-2020"));
        list.add(new PastAppointmentDatatype("2","patient_name2","doctor_name2","Doctor_location2","Doctor designamtion2","5-5-2020"));

        // Set the adapter
//        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.upcomming_appoitment_recycle);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(list));
//        }
        return view;
    }
}