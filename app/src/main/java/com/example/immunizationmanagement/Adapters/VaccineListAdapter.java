package com.example.immunizationmanagement.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.immunizationmanagement.Activities.AddBabyActivity;
import com.example.immunizationmanagement.Activities.AddVaccineActivity;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.Vaccine;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;

import java.util.List;

public class VaccineListAdapter extends RecyclerView.Adapter<VaccineListAdapter.ViewHolder> {

    private Context context;
    public List<Vaccine> vaccineList;

    private View view;



    public VaccineListAdapter(Context context, List<Vaccine> listItem) {
        this.context = context;
        this.vaccineList = listItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(this.view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Vaccine vaccine = vaccineList.get(position);

        holder.babyName.setText(vaccine.getName());

        holder.babyDobTitle.setText("After");
        holder.babyDob.setText(vaccine.getDaysAfter()+"  days");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"Card Taped",Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Card Taped");
            }
        });


//        holder.setId(Integer.parseInt(account.getA_Id()));

    }

    @Override
    public int getItemCount() {
        return vaccineList.size();
    }




    public void DeleteRow(int position ,Context context) {
        vaccineList.get(position).deleteVaccine(context);
        vaccineList.remove(position);
        notifyItemRemoved(position);
    }


    public void deleteRowWithConfirmation(final int itemPosition) {

        final Vaccine v = vaccineList.get(itemPosition);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteRow(itemPosition,context);

                        dialog.cancel();
                        Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show();

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void EditRow(int itemPosition) {

//        Account mEditingAccount = acountList.get(itemPosition);

//        notifyDataSetChanged();
    }




    // Holder Class Starts
    public class ViewHolder extends RecyclerView.ViewHolder {


        private int id;
        private TextView babyName;
        private TextView babyDob;
        private TextView babyDobTitle;
        private Button updatebtn,deletebtn;
//        private ImageView handleB;



        public void setId(int id) {
            this.id = id;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            babyName = (TextView) itemView.findViewById(R.id.titleTV);
            babyDobTitle = (TextView) itemView.findViewById(R.id.t2TV);

            babyDob = (TextView) itemView.findViewById(R.id.subTitleTV);

            updatebtn = (Button) itemView.findViewById(R.id.editB);
            deletebtn = (Button) itemView.findViewById(R.id.deleteB);

            Drawable deleteImg = context.getResources().getDrawable( R.drawable.ic_baseline_delete_24px );
            Drawable editImg = context.getResources().getDrawable( R.drawable.ic_outline_create_24px );

            deletebtn.setBackground(deleteImg);
            updatebtn.setBackground(editImg);

            updatebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, AddVaccineActivity.class);
                    //TODO  we can also use parcelable for passing Values
                    i.putExtra("Action","Edit");
                    i.putExtra("Id",vaccineList.get(getAdapterPosition()).getId());
                    context.startActivity(i);
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRowWithConfirmation(getAdapterPosition());
                }
            });



        }


    }
}
