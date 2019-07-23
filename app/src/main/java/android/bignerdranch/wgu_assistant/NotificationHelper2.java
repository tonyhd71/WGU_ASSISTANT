package android.bignerdranch.wgu_assistant;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper2 extends ContextWrapper{
    public static final String channelID2 = "channelID2";
    public static final String channelName2 = "Channel Name2";
    private static NotificationManager mManager2;
    public NotificationHelper2(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel2 = new NotificationChannel(channelID2, channelName2, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel2);
    }
    public NotificationManager getManager() {
        if (mManager2 == null) {
            mManager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager2;
    }
    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID2)
                .setContentTitle("Course Starts today!")
                .setContentText("Your Course Start Date is today!")
                .setSmallIcon(R.drawable.alert);
    }

}
