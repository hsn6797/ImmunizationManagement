package com.example.immunizationmanagement.Adapters;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.example.immunizationmanagement.Activities.AddBabyActivity;
import com.example.immunizationmanagement.Activities.BDetailsActivity;
import com.example.immunizationmanagement.Activities.MainActivity;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;

import java.util.List;


public class BabyListAdapter extends RecyclerView.Adapter<BabyListAdapter.ViewHolder> {

    private Context context;
    public List<Baby> babyList;

    private View view;


    public BabyListAdapter(Context context, List<Baby> listItem) {
        this.context = context;
        this.babyList = listItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(this.view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Baby baby = babyList.get(position);

        holder.babyName.setText(baby.getName());

        holder.babyDob.setText(Function.timestampToString(baby.getDob()));

//        BabyVaccine.saveBabyVaccines(context,baby.getId());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"Card Taped",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, BDetailsActivity.class);
                //TODO  we can also use parcelable for passing Values
                i.putExtra("Id",babyList.get(position).getId());
                context.startActivity(i);
            }
        });


//        holder.setId(Integer.parseInt(account.getA_Id()));

    }

    @Override
    public int getItemCount() {
        return babyList.size();
    }




    public void DeleteRow(int position,Context context) {
        babyList.get(position).deleteBaby(context);
        babyList.remove(position);
        notifyItemRemoved(position);

    }


    public void deleteRowWithConfirmation(final int itemPosition) {

        final Baby b = babyList.get(itemPosition);
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




    // Holder Class Starts
    public class ViewHolder extends RecyclerView.ViewHolder {


        private int id;
        private TextView babyName;
        private TextView babyDob;
        private Button updatebtn,deletebtn;

//        private ImageView handleB;



        public void setId(int id) {
            this.id = id;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            babyName = (TextView) itemView.findViewById(R.id.titleTV);
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
                    Intent i = new Intent(context, AddBabyActivity.class);
                    //TODO  we can also use parcelable for passing Values
                    i.putExtra("Action","Edit");
                    i.putExtra("Id",babyList.get(getAdapterPosition()).getId());
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
