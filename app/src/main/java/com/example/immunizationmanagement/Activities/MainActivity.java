package com.example.immunizationmanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.Model.Vaccine;
import com.example.immunizationmanagement.Notification.NotificationPublisher;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Services.NotificationService;
import com.example.immunizationmanagement.Utills.Permission.RunTimePermission;
import com.example.immunizationmanagement.Utills.Permission.RuntimePermissionInterface;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button babyPress;
    private RunTimePermission rp;

    public static final int INTERVAL= (30 * 1000);
//    4*(60*(60 * 1000))

//    public static final String CHANNEL_ID = "notification_scheduler_notifications";



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        rp.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


//    private void scheduleIntent(PendingIntent intent, Calendar startTime) {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, intent);
//    }
//    private void cancelIntents() {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(getNotificationPublisherIntent(null));
//    }
//    private PendingIntent getNotificationPublisherIntent(Notification notification) {
//        Intent intent = new Intent(this, NotificationPublisher.class);
//        if (notification != null) {
//            intent.putExtra(NotificationPublisher.NOTIFICATION, notification);
//        }
//
//        return PendingIntent.getBroadcast(
//                this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        babyPress  = (Button) findViewById(R.id.babyB);


        Intent i = new Intent( MainActivity.this, Main2Activity.class);
        startActivity(i);
        finish();




//        createNotificationChannel();


//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.baby_placeholder)
//                        .setContentTitle("Notifications Example")
//                        .setContentText("This is a test notification")
//                        .addAction(R.drawable.baby_placeholder, "Snooze",
//                                snoozePendingIntent);
//
//        Intent notificationIntent = new Intent(this, Main2Activity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        t.start();





//        Intent intent = new Intent(MainActivity.this, NotificationService.class);
//
//        startService(intent);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 10);
//
//        PendingIntent pIntent = PendingIntent.getService(MainActivity.this,
//                0,  intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Run alarm every 4th hour
//        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
//                HOURS, pIntent);
//        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                cal.getTimeInMillis(), HOURS, pIntent);


//        Context ctx = getApplicationContext();
//        /** this gives us the time for the first trigger.  */
//        Calendar cal = Calendar.getInstance();
//        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
//        long interval = 1000 * 60 * 5; // 5 minutes in milliseconds
//        Intent serviceIntent = new Intent(ctx, NotificationService.class);
//        // make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
//        PendingIntent servicePendingIntent =
//                PendingIntent.getService(ctx,
//                        NotificationService.SERVICE_ID, // integer constant used to identify the service
//                        serviceIntent,
//                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
//        // there are other options like setInexactRepeating, check the docs
//        am.setRepeating(
//                AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
//                cal.getTimeInMillis(),
//                INTERVAL,
//                servicePendingIntent
//        );

//        if (RunTimePermission.aboveMarshmallow()) {
//            rp = new RunTimePermission(this,new RuntimePermissionInterface() {
//                @Override
//                public void doTaskAfterPermissionAccepted() {
//
//                }
//
//                @Override
//                public void doTaskAfterPermissionRejected() {
//                    Log.d("A ", "Storage permission denied");
//                    finish();
//                }
//            });
//            rp.runtimePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }




        babyPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });



    }

//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

}
