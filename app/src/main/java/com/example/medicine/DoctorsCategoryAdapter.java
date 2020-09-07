package com.example.medicine;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class DoctorsCategoryAdapter extends RecyclerView.Adapter<DoctorsCategoryAdapter.ViewHolder> {

    private List<DoctorsCategoryModel> list;

    public DoctorsCategoryAdapter(List<DoctorsCategoryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DoctorsCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctors_category_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsCategoryAdapter.ViewHolder holder, int position) {
        int image = list.get(position).getImage();
        String title = list.get(position).getTitle();

        holder.setData(image,title,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title;
        private int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.doctorsCategoryIconID);
            title = itemView.findViewById(R.id.doctorsCategoryNameID);
        }

        public void setData(int image, final String title, final int position){
            this.image.setImageResource(image);
            this.title.setText(title);
            this.position = position;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),SpecificCategory.class);
                    intent.putExtra("category",title);
                    intent.putExtra("position",position);
                    itemView.getContext().startActivity(intent);
                    customType(itemView.getContext(),"left-to-right");
                }
            });
        }
    }
}
