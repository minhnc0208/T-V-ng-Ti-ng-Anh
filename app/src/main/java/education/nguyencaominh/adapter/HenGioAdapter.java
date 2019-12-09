package education.nguyencaominh.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import education.nguyencaominh.Receiver.AlarmReceiver;
import education.nguyencaominh.model.HenGio;
import education.nguyenhoanghiep.tuvungtienganh.R;

public class HenGioAdapter extends ArrayAdapter<HenGio> {
    private Activity context;
    private int resource;
    private List<HenGio> objects;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH : mm");

    PendingIntent pendingIntent ;


    public HenGioAdapter(Activity context, int resource, List<HenGio> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        final TextView txtHenGio = row.findViewById(R.id.txtHenGio);
        Switch swBatTat = row.findViewById(R.id.swBatTat);
        swBatTat.setChecked(false);

        final AlarmManager alarmManager  = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(context, AlarmReceiver.class);

        HenGio henGio = this.objects.get(position);
        txtHenGio.setText(henGio.getThoiGian());
        if(henGio.isCoHieuLuc())
        {
            swBatTat.setChecked(true);
        }

        txtHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Update dữ liệu người dùng vừa chọn vào controls
                        calendar.set(calendar.HOUR, hourOfDay);
                        calendar.set(calendar.MINUTE, minute);
                        txtHenGio.setText(sdf.format(calendar.getTime()) + "");
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                    context,
                    callback,
                    calendar.get(calendar.HOUR_OF_DAY),
                    calendar.get(calendar.MINUTE),
                    true
                );
                timePickerDialog.show();
            }
        });

        swBatTat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    System.out.println("******calendar.get(calendar.HOUR_OF_DAY)**********" + calendar.get(calendar.HOUR_OF_DAY));
                    System.out.println("******calendar.get(calendar.MINUTE)**********" + calendar.get(calendar.MINUTE));
                    System.out.println("******calendar.getTimeInMillis()**********" + calendar.getTimeInMillis());
                    //TODO: -------------------
                    pendingIntent = PendingIntent.getBroadcast(
                            context,
                            0,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    alarmManager.set(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    Toast.makeText(context, "CHECKED!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //TODO: -------------------
                    Toast.makeText(context, "UNCHECKED!", Toast.LENGTH_LONG).show();
                    pendingIntent.cancel();
                }
            }
        });

        return row;
    }
}
