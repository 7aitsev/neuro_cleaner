package org.appspot.NeuroCleaner.Servise;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class MemoryOfficer extends Service{
    private Context context;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        MemoryChecker checker = new MemoryChecker();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class MemoryChecker extends Thread{
        public MemoryChecker(){
            start();
        }

        @Override
        public void run() {
            super.run();
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent memoryIntent = new Intent(context, MemorySergeant.class);
            PendingIntent pMemoryIntent = PendingIntent.getBroadcast(context, 0, memoryIntent, 0);
            am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60 * 1000, pMemoryIntent);
        }
    }
}
