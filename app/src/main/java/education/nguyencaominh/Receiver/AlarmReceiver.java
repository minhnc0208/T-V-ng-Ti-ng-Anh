package education.nguyencaominh.Receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.TaskStackBuilder;

import education.nguyencaominh.Service.Music;
import education.nguyencaominh.tuvungtienganh.HenGioThongBao;
import education.nguyenhoanghiep.tuvungtienganh.R;
import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
public class AlarmReceiver extends BroadcastReceiver {
    private String TAG = "RECEIVER -- ALARMRECEIVER";
    private static final String CHANNEL_ID = "com.singhajit.notificationDemo.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, HenGioThongBao.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(HenGioThongBao.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        Notification notification = builder.setContentTitle("Từ vựng tiếng anh")
                .setContentText("Mời bạn bạn học từ vựng tiếng anh")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notification);
        String batTat = intent.getStringExtra("BatTat");
        Intent myIntent = new Intent(context, Music.class);
        myIntent.putExtra("BatTat", batTat);
        context.startService(myIntent);
    }
}
