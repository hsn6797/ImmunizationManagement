package com.example.immunizationmanagement.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.immunizationmanagement.Activities.MainActivity;
import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.Model.Status;
import com.example.immunizationmanagement.R;


public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION = "notification";
    public static String NOTIFICATION_ID = "notification-id";

    @Override
    public void onReceive(Context ctx, Intent intent) {

        DataSource ds = new DataSource(ctx);
        String id = intent.getStringExtra("bv_id");
        BabyVaccine bv = ds.getBabyVaccine(id);

        if (intent.getAction().equalsIgnoreCase("SNOOZE")) {

            // TODO - Set status of BabyVaccine to SNOOZE
//            Toast.makeText(ctx, "Your are in snooze mode "+id, Toast.LENGTH_SHORT).show();

            bv.setStatus(Status.S);
            ds.editBabyVaccine(bv);

            NotificationManager notificationManager =
                    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(11111);



        } else if (intent.getAction().equalsIgnoreCase("DONE")) {

            // TODO - Set status of BabyVaccine to DONE

            bv.setStatus(Status.D);
            ds.editBabyVaccine(bv);

            NotificationManager notificationManager =
                    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(11111);

        }


    }

}
