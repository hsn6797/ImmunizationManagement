package com.example.immunizationmanagement.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.immunizationmanagement.Activities.AddBabyActivity;
import com.example.immunizationmanagement.Adapters.BabyListAdapter;
import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class BabyListFragment extends Fragment {

    private RecyclerView recyclerView;
    private BabyListAdapter adapter;
    DataSource ds = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_baby_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.babyRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ds = new DataSource(getContext());

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.babyFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent( getActivity(), AddBabyActivity.class);
                startActivity(i);

            }
        });


//        else{
//            Toast.makeText(getContext(),"No babies in the list",Toast.LENGTH_LONG).show();
//        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(),"Fragment Resumed",Toast.LENGTH_LONG).show();

        ds.openConnection();
        List<Baby> blist = ds.getAllBabies();
        if(blist.size() > 0){
            adapter = new BabyListAdapter(getContext(), blist);

            recyclerView.setAdapter(adapter);

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        ds.closeConnection();
    }
}