package com.example.immunizationmanagement.Adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.Model.Status;
import com.example.immunizationmanagement.Model.Vaccine;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;

import java.util.List;


public class BabyDetailListAdapter extends RecyclerView.Adapter<BabyDetailListAdapter.ViewHolder> {

    private Context context;
    public List<BabyVaccine> babyVaccineList;

    private View view;


    public BabyDetailListAdapter(Context context, List<BabyVaccine> listItem) {
        this.context = context;
        this.babyVaccineList = listItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_baby_row,parent,false);

        return new ViewHolder(this.view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final BabyVaccine bv = babyVaccineList.get(position);

        DataSource ds = new DataSource(context);
        Vaccine v = ds.getVaccine(bv.getV_id());

        holder.vaccineName.setText(v.getName());
        holder.vaccineIssueDate.setText(Function.timestampToString(bv.getIssueDate()));
        Log.d("-----------> ", "BD Adapter: "+Function.timestampToDTString(bv.getSnoozAt()));


        if(bv.getStatus() == Status.S){
            holder.vaccineStatus.setText("Snooze ");

        }else if(bv.getStatus() == Status.P){
            holder.vaccineStatus.setText("Pending");

        }else{
            holder.vaccineStatus.setText("Done   ");
        }

    }

    @Override
    public int getItemCount() {
        return babyVaccineList.size();
    }


    // Holder Class Starts
    public class ViewHolder extends RecyclerView.ViewHolder {


        private int id;

        private TextView vaccineName;
        private TextView vaccineIssueDate;
        private TextView vaccineStatus;


        public void setId(int id) {
            this.id = id;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            vaccineName = (TextView) itemView.findViewById(R.id.vaccineNameTV);
            vaccineIssueDate = (TextView) itemView.findViewById(R.id.vaccineIsuueDateTV);
            vaccineStatus = (TextView) itemView.findViewById(R.id.vaccineStatusTV);


        }

    }
}

