package com.example.immunizationmanagement.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.immunizationmanagement.Activities.AddVaccineActivity;
import com.example.immunizationmanagement.Adapters.VaccineListAdapter;
import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Vaccine;
import com.example.immunizationmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class VaccineListFragment extends Fragment {

    private RecyclerView recyclerView;
    private VaccineListAdapter adapter;
    DataSource ds = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vaccine_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.vaccineRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ds = new DataSource(getContext());

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.vaccineFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent( getActivity(), AddVaccineActivity.class);
                startActivity(i);

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(),"Fragment Resumed",Toast.LENGTH_LONG).show();

        ds.openConnection();
        List<Vaccine> vlist = ds.getAllVaccine();
        if(vlist.size() > 0){
            adapter = new VaccineListAdapter(getContext(), vlist);
            recyclerView.setAdapter(adapter);

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        ds.closeConnection();
    }
}
