package education.nguyencaominh.tuvungtienganh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import education.nguyencaominh.Receiver.AlarmReceiver;
import education.nguyenhoanghiep.tuvungtienganh.R;

public class HenGioThongBao extends AppCompatActivity {

//    private ArrayList<HenGio> dsHenGio;
//    private HenGioAdapter henGioAdapter;
//    private ListView lvHenGio;

    private ImageButton btnBackMain;
    private TextView txtTenChuDe;
    private TimePicker timePicker;
    private TextView txtGioDat;
    private Button btnDatGio, btnHuyHenGio;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH : mm");
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hengio);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtTenChuDe = findViewById(R.id.txtTenChuDe);
        txtTenChuDe.setText("Danh sách hẹn giờ");
        btnBackMain = findViewById(R.id.btnBackDsChuDe);
        timePicker = findViewById(R.id.timePicker);
//        timePicker.setIs24HourView(true);
        txtGioDat = findViewById(R.id.txtGioDat);
        btnDatGio = findViewById(R.id.btnDatGio);
        btnHuyHenGio = findViewById(R.id.btnHuyDatGio);
        txtGioDat.setText(sdf.format(calendar.getTime()));
        alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);

    }

    private void addEvents() {
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HenGioThongBao.this, MainActivity.class);
                startActivity(intent2);
            }
        });
        final Intent intent = new Intent(HenGioThongBao.this, AlarmReceiver.class);
        btnDatGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(calendar.MINUTE, timePicker.getCurrentMinute());
                intent.putExtra("BatTat", "on");
                pendingIntent = PendingIntent.getBroadcast(
                        HenGioThongBao.this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
                txtGioDat.setText(sdf.format(calendar.getTime()));
                timePicker.setVisibility(View.GONE);
                btnDatGio.setVisibility(View.GONE);
                btnHuyHenGio.setVisibility(View.VISIBLE);
            }
        });
        btnHuyHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("BatTat", "off");
                sendBroadcast(intent);
                timePicker.setVisibility(View.VISIBLE);
                btnDatGio.setVisibility(View.VISIBLE);
                btnHuyHenGio.setVisibility(View.GONE);
            }
        });
    }
}
