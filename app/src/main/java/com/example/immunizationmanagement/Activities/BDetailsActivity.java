package com.example.immunizationmanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.immunizationmanagement.Adapters.BabyDetailListAdapter;
import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;
import com.example.immunizationmanagement.Utills.ImageFactory;

import java.io.File;
import java.util.List;

public class BDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BabyDetailListAdapter adapter;
    DataSource ds = null;

    private ImageView babyImage;
    private TextView babyName;
    private TextView babyDob;



    void init(){
        recyclerView = (RecyclerView) findViewById(R.id.babyDetailsRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BDetailsActivity.this));

        babyImage = (ImageView) findViewById(R.id.babyDIV);
        babyName = (TextView) findViewById(R.id.babyNameDTV);
        babyDob = (TextView) findViewById(R.id.babyDobDTV);

        Drawable placeHolder = BDetailsActivity.this.getResources().getDrawable( R.drawable.ic_person_black_24dp );
        babyImage.setImageDrawable(placeHolder);

        ds = new DataSource(BDetailsActivity.this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();


        // get id from previous Activity
        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            String id = extras.getString("Id");

            Baby baby = ds.getBaby(id);

            babyName.setText(baby.getName());
            babyDob.setText("Born on: "+Function.timestampToString(baby.getDob()));


            File image = ImageFactory.getImage(baby.getId());

            if(image != null){
                String imagePath = image.getPath();
                babyImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            }


            List<BabyVaccine> bvlist = ds.getAllBabyVaccineByBID(baby.getId());
            if(bvlist != null && bvlist.size() > 0){
                adapter = new BabyDetailListAdapter(BDetailsActivity.this, bvlist);
                recyclerView.setAdapter(adapter);
            }
            ds.closeConnection();

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
