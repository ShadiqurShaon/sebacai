package com.example.medicine;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medicine.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PastAppointmentDatatype> mValues;

    public MyItemRecyclerViewAdapter(List<PastAppointmentDatatype> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_past_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.pname.setText(mValues.get(position).getPatient_name());
        holder.dname.setText(mValues.get(position).getDoctor_name());
        holder.ddasignation.setText(mValues.get(position).getDoctor_designation());
        holder.dlocation.setText(mValues.get(position).getDoctor_location());
        holder.date.setText(mValues.get(position).getAppointment_date());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView pname;
        public final TextView dname;
        public final TextView ddasignation;
        public final TextView dlocation;
        public final TextView date;
        public PastAppointmentDatatype mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            pname = (TextView) view.findViewById(R.id.patient_name_past);
            dname = (TextView) view.findViewById(R.id.doctor_name_past);
            ddasignation = (TextView) view.findViewById(R.id.doctor_designamtion_past);
            dlocation = (TextView) view.findViewById(R.id.doctor_location_past);
            date = (TextView) view.findViewById(R.id.appont_date);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}