package android.bignerdranch.wgu_assistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context2, Intent intent2) {
        NotificationHelper2 notificationHelper2 = new NotificationHelper2(context2);
        NotificationCompat.Builder nb2 = notificationHelper2.getChannelNotification();
        notificationHelper2.getManager().notify(1, nb2.build());
    }
}
