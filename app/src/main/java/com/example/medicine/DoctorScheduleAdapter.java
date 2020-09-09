package com.example.medicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorScheduleAdapter extends RecyclerView.Adapter<DoctorScheduleAdapter.ViewHolder> {

    List<DoctorScheduleModel> list;
    public DoctorScheduleAdapter(List<DoctorScheduleModel> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public DoctorScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_schedule,parent,false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView vdate;
        private TextView vtime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vdate = itemView.findViewById(R.id.day_id);
            vtime = itemView.findViewById(R.id.time_id);

        }


        public void setData(String data,String time) {
            vdate.setText(data);
            vtime.setText(time);

        }
    }
    @Override
    public void onBindViewHolder(@NonNull DoctorScheduleAdapter.ViewHolder holder, int position) {
        String date = list.get(position).getDay();
        String time  = list.get(position).getTime();
        holder.setData(date,time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
