package education.nguyencaominh.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import education.nguyenhoanghiep.tuvungtienganh.R;

public class Music extends Service {

    private MediaPlayer mediaPlayer;
    private int id;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        System.out.println("********* music*****");
//        System.out.println("********* music*****");
//        System.out.println("********* music*****");
        String isOn = intent.getStringExtra("BatTat");
        if(isOn.equals("on"))
        {
            id = 1;
        }
        else if(isOn.equals("off"))
        {
            id = 0;
        }

        if (id == 1)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.inform_hoctu);
            mediaPlayer.start();
            id = 0;
        }
        else if(id == 0)
        {
            if(mediaPlayer != null)
            {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }
        return START_NOT_STICKY;
    }
}
