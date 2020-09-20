package com.example.immunizationmanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.immunizationmanagement.Adapters.BabyDetailListAdapter;
import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.Model.Status;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;
import com.example.immunizationmanagement.Utills.ImageFactory;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class BDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BabyDetailListAdapter adapter;
    DataSource ds = null;

    private ImageView babyImage;
    private TextView babyName;
    private TextView babyDob;

    // For Snooz only
    Calendar c = null;
    int mYear,mMonth,mDay,mHour,mMin;
    BabyVaccine updateBV;
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
    private void getCurrentDate(){
        c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int Day = c.get(Calendar.DAY_OF_MONTH);
        final int Hour = c.get(Calendar.HOUR_OF_DAY);
        final int Min = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BDetailsActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mDay = dayOfMonth;
                        mMonth = monthOfYear;
                        mYear = year;

                        new TimePickerDialog(BDetailsActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mHour = hourOfDay;
                                mMin = minute;

                                if(updateBV != null){
                                    long currentDate = Function.DateTimeToTimeStamp(mDay,mMonth,mYear,mHour,mMin);
                                    Log.d("-----------> ", Function.timestampToDTString(currentDate));

                                    updateBV.setSnoozAt(currentDate);
                                    updateBV.setStatus(Status.S);
                                    updateBV.editBabyVaccine(getApplicationContext());
                                    finish();
                                }


                            }
                        },Hour, Min, true)
                                .show();
                    }
                }, Year, Month, Day);
        datePickerDialog.show();

//        long dob = Function.DateTimeToTimeStamp(mDay,mMonth,mYear,mHour,mMin);
//        Log.d("-----------> ", Function.timestampToString(dob));
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
            String bv_id = extras.getString("bv_id");

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
                for (BabyVaccine bv:
                     bvlist) {
                    if(bv.getId().equalsIgnoreCase(bv_id)){
                        updateBV = bv;
                    }
                }
            }
            ds.closeConnection();

            String action = getIntent().getAction();
            if (action != null && action.equalsIgnoreCase("SNOOZE")) {
                NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(Integer.parseInt(updateBV.getId()));
                getCurrentDate();
            }
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
