package com.example.medicine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<DoctorsCategoryModel> list = new ArrayList<>();
    private DoctorsCategoryAdapter adapter;

    public CategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.doctorsCategoryRecylerID);

        list.add(new DoctorsCategoryModel(R.drawable.cardiovascular,"কার্ডিওলজিস্ট"));
        list.add(new DoctorsCategoryModel(R.drawable.teeth,"ডেন্টিস্ট"));
        list.add(new DoctorsCategoryModel(R.drawable.dermatologist,"ডার্মাটোলজিস্টট"));
        list.add(new DoctorsCategoryModel(R.drawable.eye,"চক্ষু"));
        list.add(new DoctorsCategoryModel(R.drawable.gastroenterology,"গ্যাস্ট্রোলজি"));
        list.add(new DoctorsCategoryModel(R.drawable.medicine,"মেডিসিন"));
        list.add(new DoctorsCategoryModel(R.drawable.surgeon,"সার্জারি"));
        list.add(new DoctorsCategoryModel(R.drawable.pregnant,"স্ত্রীরোগ"));
        list.add(new DoctorsCategoryModel(R.drawable.fracture,"অর্থোপেডিক"));
        list.add(new DoctorsCategoryModel(R.drawable.skin,"ত্বক"));
        list.add(new DoctorsCategoryModel(R.drawable.kidney,"ইউরোলজি"));
        list.add(new DoctorsCategoryModel(R.drawable.ent,"নাক, কান,গলা"));
        list.add(new DoctorsCategoryModel(R.drawable.newborn,"শিশু বিশেষজ্ঞ"));
//        list.add(new DoctorsCategoryModel(R.drawable.ic_test_pic,"Psychiatry"));
//        list.add(new DoctorsCategoryModel(R.drawable.ic_test_pic,"Medicine Specialist"));

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),3));

        adapter = new DoctorsCategoryAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }
}