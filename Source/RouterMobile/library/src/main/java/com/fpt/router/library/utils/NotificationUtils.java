package com.fpt.router.library.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.fpt.router.library.R;
import com.fpt.router.library.config.AppConstants;

import java.util.Date;

import static com.fpt.router.library.config.AppConstants.Vibrator.*;

/**
 * Created by Huynh Quang Thao on 10/29/15.
 */
public class NotificationUtils {
    public static void build(Context context, Activity activity, String title, String content,
                             String pageTwoTitle, String pageTwoContent) {
        // notification testing
        // int notificationId = (int) new Date().getTime();
        int notificationId = 1;

        // Build intent for mobile: open SearchRouteActivity when click to intent
        Intent viewIntent = new Intent(context, activity.getClass());
        PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);

        // build notification for wearable side

        // WearableExtender. Using this for add functionality for wear. (more advanced)
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(false)                 // show app icon
                ;

        // Create second page notification. for longer message. only show on wear. mobile will not see those pages
        NotificationCompat.BigTextStyle wearSecondPageNotif = new NotificationCompat.BigTextStyle();
        wearSecondPageNotif.setBigContentTitle(pageTwoTitle)
                .bigText(pageTwoContent);

        // create notification from builder
        Notification secondPageNotification=
                new NotificationCompat.Builder(context)
                        .setStyle(wearSecondPageNotif)
                        .build();

        wearableExtender.addPage(secondPageNotification);

        NotificationCompat.Builder notificationBuilder=
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.common_signin_btn_icon_pressed_light)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setVibrate(new long[]{DELAY_VIBRATE,ON_VIBRATE,OFF_VIBRATE,ON_VIBRATE,OFF_VIBRATE,ON_VIBRATE})
                        .setColor(Color.BLUE)
                        .extend(wearableExtender)
                        .setContentIntent(viewPendingIntent);


        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager=
                NotificationManagerCompat.from(context);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId,notificationBuilder.build());
    }
}






