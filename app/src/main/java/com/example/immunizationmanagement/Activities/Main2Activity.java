package com.example.immunizationmanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabWidget;
import android.widget.Toolbar;

import com.example.immunizationmanagement.Adapters.PagerAdapter;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Services.NotificationService;
import com.example.immunizationmanagement.Utills.Permission.RunTimePermission;
import com.example.immunizationmanagement.Utills.Permission.RuntimePermissionInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    private RunTimePermission rp;
    public static final int INTERVAL= (60 * 1000)*60; // 1 hour



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        rp.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Ask for storage permission
        if (RunTimePermission.aboveMarshmallow()) {
            rp = new RunTimePermission(this,new RuntimePermissionInterface() {
                @Override
                public void doTaskAfterPermissionAccepted() {

                }

                @Override
                public void doTaskAfterPermissionRejected() {
                    Log.d("A ", "Storage permission denied");
                    finish();
                }
            });
            rp.runtimePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Babies"));
        tabLayout.addTab(tabLayout.newTab().setText("Vaccines"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        // Create Alarm manager class to run service repeatedly

        Context ctx = getApplicationContext();
        /** this gives us the time for the first trigger.  */
        Calendar cal = Calendar.getInstance();
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
//        long interval = 1000 * 60 * 59; // 59 minutes in milliseconds
        Intent serviceIntent = new Intent(ctx, NotificationService.class);
        // make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
        PendingIntent servicePendingIntent =
                PendingIntent.getService(ctx,
                        NotificationService.SERVICE_ID, // integer constant used to identify the service
                        serviceIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
        // there are other options like setInexactRepeating, check the docs
        am.setRepeating(
                AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
                cal.getTimeInMillis(),
                INTERVAL,
                servicePendingIntent
        );

    }

    // Main menu Override Methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Here
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            startActivity(new Intent(this,SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

