package com.example.immunizationmanagement.Services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import com.example.immunizationmanagement.Activities.Main2Activity;
import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.Model.Status;
import com.example.immunizationmanagement.Model.Vaccine;
import com.example.immunizationmanagement.Notification.NotificationPublisher;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class NotificationService extends IntentService {
    public static final String TAG = "NotificationService";
    public static final String CHANNEL_ID = "notification_scheduler_notifications";
    public static final int SERVICE_ID = 0;


    public NotificationService(){
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG, "onHandleIntent called");
        DataSource ds = new DataSource(getApplicationContext());
        List<BabyVaccine> bvList = ds.getAllBabyVaccine();

        int days = 0;
        try {
            // Get days value from Shared Preferences
            String daysStr = PreferenceManager.getDefaultSharedPreferences(this).getString("before", null);
            days = Integer.parseInt(daysStr);
        }catch (Exception ex){
            Log.d(TAG, "Days parsing Exception: "+ex.getMessage());
        }

        Log.d(TAG, "Days before: "+days);

        for(int i = 0; i < bvList.size(); i++){
            BabyVaccine bv = bvList.get(i);

            // if bv object date matches the current date
//            LocalDate vaccinationDate = Function.timestampToDate(bv.getIssueDate());
//            LocalDate currentDate = Function.timestampToDate(System.currentTimeMillis());


            if(Function.compareDate(bv.getIssueDate(),days)){

                if(bv.getStatus() == Status.S || bv.getStatus() == Status.P){
                    Baby b = ds.getBaby(bv.getB_id());
                    Vaccine v = ds.getVaccine(bv.getV_id());
                    sendNotification(b,v,bv);
                }


//                Log.d(TAG, b.toString());
//                Log.d(TAG, v.toString());
//
//                Log.d(TAG, "onHandleIntent: "+ Function.timestampToDate(bv.getIssueDate()));
//                Log.d(TAG, "onHandleIntent: "+ Function.timestampToDate(System.currentTimeMillis()));
            }



        }

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void sendNotification(Baby b, Vaccine v,BabyVaccine bv) {

        //create notification chanel for Android oreo 8 and above
        createNotificationChannel();

        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("fromNotification", "notify_vaccine");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent intentSnooze = new Intent(this, NotificationPublisher.class);
        intentSnooze.setAction("SNOOZE");
        intentSnooze.putExtra("bv_id",bv.getId());
        intentSnooze.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Intent intentDone = new Intent(this, NotificationPublisher.class);
        intentDone.setAction("DONE");
        intentDone.putExtra("bv_id",bv.getId());
        intentDone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //This Intent will be called when Notification will be clicked by user.
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                        PendingIntent.FLAG_ONE_SHOT);

        //This Intent will be called when Snooze button from notification will be
        //clicked by user.
        PendingIntent pendingIntentConfirm = PendingIntent.getBroadcast(this, 0, intentSnooze, PendingIntent.FLAG_CANCEL_CURRENT);

        //This Intent will be called when Done button from notification will be
        //clicked by user.
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(this, 1, intentDone, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_idea)
                .setContentTitle("Vaccination Day")
                .setContentText("For your baby "+b.getName())
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationBuilder.addAction(R.drawable.snooze, "Snooze", pendingIntentConfirm);
        notificationBuilder.addAction(R.drawable.ic_tick, "Done", pendingIntentCancel);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(11111 /* ID of notification */, notificationBuilder.build());
    }
}
