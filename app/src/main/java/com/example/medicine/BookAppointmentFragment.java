package com.example.medicine;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class BookAppointmentFragment extends AppCompatDialogFragment {
    private EditText patient_name,patient_age,appo_date,short_description;
    private  BookappointmentListener bookAppListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.submit_appointment_fragment,null);
        builder.setView(view).setTitle("Patient Details")
                .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String p_name = patient_name.getText().toString();
                String p_age = patient_age.getText().toString();
                String appo_day = appo_date.getText().toString();
                String short_dis = short_description.getText().toString();
                bookAppListener.setPatientData(p_name,p_age,appo_day,short_dis);
            }
        });

        patient_name = view.findViewById(R.id.patientname);
        patient_age = view.findViewById(R.id.patient_age);
        appo_date = view.findViewById(R.id.appo_date);
        short_description = view.findViewById(R.id.short_description);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            bookAppListener = (BookappointmentListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString()+"Must implement");
        }
    }

    public interface BookappointmentListener{
        void setPatientData(String p_name,String p_age,String appo_day,String short_dis);
    }
    
}
