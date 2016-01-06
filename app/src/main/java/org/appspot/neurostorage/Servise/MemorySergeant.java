package org.appspot.neurostorage.Servise;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import org.appspot.neurostorage.Activity.NeuroListActivity;
import org.appspot.neurostorage.R;

public class MemorySergeant extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long availMemory = stat.getAvailableBytes()/(1024*1024);
        if(availMemory > 2048)
            return;

        String notifTitle = "Data storage is almost full";
        String notifText1 = "There are only " + availMemory + " mb of free memory. Would you allow Neuro Cleaner to settle it?";

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.abc_btn_radio_to_on_mtrl_015)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_white_24dp))
                .setContentTitle(notifTitle)
                .setContentText("piece of homeless " + notifText1)
                .addAction(R.drawable.abc_btn_default_mtrl_shape, "Manually", PendingIntent.getActivity(context, 0, new Intent(context, NeuroListActivity.class), 0))
                .addAction(R.drawable.abc_btn_default_mtrl_shape, "Neuro OK", PendingIntent.getActivity(context, 0, new Intent(context, NeuroListActivity.class), 0));

        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        bigTextStyle.setBigContentTitle(notifTitle).bigText(notifText1);
        builder.setStyle(bigTextStyle);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(123, notification);
    }
}
