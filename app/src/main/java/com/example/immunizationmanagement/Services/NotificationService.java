package com.example.immunizationmanagement.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import com.example.immunizationmanagement.Activities.BDetailsActivity;
import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.Model.Status;
import com.example.immunizationmanagement.Model.Vaccine;
import com.example.immunizationmanagement.Notification.NotificationPublisher;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;

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

        for(int i = 0; i < bvList.size(); i++){
            BabyVaccine bv = bvList.get(i);

            NotificationManager notificationManager =
            (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.getActiveNotifications();
            StatusBarNotification[] notifications = notificationManager.getActiveNotifications();
            boolean notificationExists = false;
            for (StatusBarNotification notification : notifications) {
                if (notification.getId() == Integer.parseInt(bv.getId())) {
                    // Show the notification if not exists.
                    notificationExists = true;
                    break;
                }
            }

            if(notificationExists) continue;
            boolean showNotification = false;
            if(bv.getStatus() == Status.P && Function.compareDate(bv.getIssueDate(),days)){
                showNotification = true;
            }else if(bv.getStatus() == Status.S && Function.isDateTimeIsNow(bv.getSnoozAt())){
                showNotification = true;
            }
            if(showNotification){
                Baby b = ds.getBaby(bv.getB_id());
                Vaccine v = ds.getVaccine(bv.getV_id());
                sendNotification(b,v,bv);
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


        int NOTIFICATION_ID = Integer.parseInt(bv.getId());
        //create notification chanel for Android oreo 8 and above
        createNotificationChannel();

        Intent intent = new Intent(this, BDetailsActivity.class);
//        intent.putExtra("fromNotification", "notify_vaccine");
        intent.putExtra("Id",b.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent intentSnooze = new Intent(this, BDetailsActivity.class);
        intentSnooze.setAction("SNOOZE");
        intentSnooze.putExtra("Id",b.getId());
        intentSnooze.putExtra("bv_id",bv.getId());
        intentSnooze.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Intent intentDone = new Intent(this, NotificationPublisher.class);
        intentDone.setAction("DONE");
        intentDone.putExtra("bv_id",bv.getId());
        intentDone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //This Intent will be called when Notification will be clicked by user.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID , intent,
                PendingIntent.FLAG_ONE_SHOT);

        //This Intent will be called when Snooze button from notification will be
        //clicked by user.
//        PendingIntent pendingIntentConfirm = PendingIntent.getBroadcast(this, 0, intentSnooze, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentConfirm = PendingIntent.getActivity(this, NOTIFICATION_ID, intentSnooze,
                PendingIntent.FLAG_ONE_SHOT);

        //This Intent will be called when Done button from notification will be
        //clicked by user.
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intentDone,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_idea)
                .setContentTitle("Vaccine "+v.getName()+" Day")
                .setContentText("For your baby "+b.getName())
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationBuilder.addAction(R.drawable.snooze, "Snooze", pendingIntentConfirm);
        notificationBuilder.addAction(R.drawable.ic_tick, "Done", pendingIntentCancel);


        Notification notification = notificationBuilder.build();
        // this is the main thing to do to make a non removable notification
        notification.flags |= Notification.FLAG_NO_CLEAR;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID/* ID of notification */,notification);
    }
}
