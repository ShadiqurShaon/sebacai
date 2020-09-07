package com.example.medicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpesificCategoryAdapter extends RecyclerView.Adapter<SpesificCategoryAdapter.ViewHolder> {

    private List<SpecificCategoryModel> list;

    public SpesificCategoryAdapter(List<SpecificCategoryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SpesificCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specific_category_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpesificCategoryAdapter.ViewHolder holder, int position) {
        int image = list.get(position).getDiImage();
        String name = list.get(position).getDrName();
        String category = list.get(position).getDrCategory();
        String eduQualification = list.get(position).getDrEduQualification();
        String designation = list.get(position).getDesignation();
        String institute = list.get(position).getInstitute();
        String consultantFee = list.get(position).getConsultantFree();

        holder.setData(image,name,category,eduQualification,designation,institute,consultantFee);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView drImage;
        private TextView drName, drCategory, drEduQualification, drDesignatioln, institute, consultantFee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drImage = itemView.findViewById(R.id.specific_category_profileID);
            drName = itemView.findViewById(R.id.specificCaategoryNameID);
            drCategory = itemView.findViewById(R.id.specificCaategoryID);
            drEduQualification = itemView.findViewById(R.id.specificCaategoryEducationID);
            drDesignatioln = itemView.findViewById(R.id.specificCaategoryDesignationID);
            institute = itemView.findViewById(R.id.specificCaategoryInstituteID);
            consultantFee = itemView.findViewById(R.id.specificCaategoryConsultantfeeID);

        }

        public void setData(int image, String name, String category,
                            String eduQualification, String designation, String institute, String consultantFee ){
            drImage.setImageResource(image);
            drName.setText(name);
            drCategory.setText(category);
            drEduQualification.setText(eduQualification);
            this.institute.setText(institute);
            drDesignatioln.setText(designation);
            this.consultantFee.setText("Consultant Fee - "+consultantFee);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
