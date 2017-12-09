package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

public class DailyView extends AppCompatActivity implements DailyAgenda.OnFragmentInteractionListener {

    private FloatingActionButton addEntry;
    private DatePicker datePicker;
    private TextView greetings;
    private TextView agenda;
    private CalendarView calendarView;

    private SensorManager sm;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    private int yyyy;
    private int mm;
    private int dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preferences),Context.MODE_PRIVATE);
        String greet = sharedPref.getString(getString(R.string.greeting_pref), getString(R.string.greeting_default));
        Calendar c = Calendar.getInstance();

        if (savedInstanceState != null) {
            int[] date = savedInstanceState.getIntArray("SelectedDate");
            yyyy = date[0];
            mm = date[1];
            dd = date[2];
        }
        else {
            yyyy = c.get(Calendar.YEAR);
            mm = c.get(Calendar.MONTH);
            dd = c.get(Calendar.DAY_OF_MONTH);
        }

        datePicker = (DatePicker) findViewById(R.id.datePicker);


        DatePicker.OnDateChangedListener dateListener = new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    Log.v("datepicker", i + " " + i1 + " " + i2);
                    agenda.setText("Changed to " + i);
                    dateChanged(i, i1, i2);
                }
            };
        datePicker.init(yyyy,
        mm,
        dd,
        dateListener);

        greetings = (TextView) findViewById(R.id.datetext);
        greetings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent toSettings = new Intent(DailyView.this, Settings.class);
                startActivity(toSettings);
                return true;
            }
        });
        greetings.setText(greet);

        agenda = (TextView) findViewById(R.id.todaytext);
        agenda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent toAgenda = new Intent(DailyView.this, DetailedAgenda.class);
                toAgenda.putExtra("yyyy", datePicker.getYear());
                toAgenda.putExtra("mm", datePicker.getMonth());
                toAgenda.putExtra("dd", datePicker.getDayOfMonth());
                startActivity(toAgenda);
                return true;
            }
        });

        final TextView agenda = (TextView) findViewById(R.id.todaytext);
        addEntry = (FloatingActionButton) findViewById(R.id.addEntry);



        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector(
                new ShakeDetector.OnShakeListener() {
                    @Override
                    public void onShake() {
                        Calendar c = Calendar.getInstance();
                        yyyy = c.get(Calendar.YEAR);
                        mm = c.get(Calendar.MONTH);
                        dd = c.get(Calendar.DAY_OF_MONTH);


                        datePicker.updateDate(c.get(Calendar.YEAR),
                                    c.get(Calendar.MONTH),
                                    c.get(Calendar.DAY_OF_MONTH));
                            dateChanged(yyyy, mm, dd);

                    }
                }
        );

    }

    /**
     *
     * @param i year
     * @param i1 month
     * @param i2 day
     */
    public void dateChanged(int i, int i1, int i2) {

    }

    public void toJournalEdit(View view) {
        Intent intent = new Intent(this, JournalEdit.class);
        intent.putExtra("yyyy", yyyy);
        intent.putExtra("mm", mm);
        intent.putExtra("dd", dd);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        int[] date = {yyyy, mm, dd};
        savedInstanceState.putIntArray("SelectedDate", date);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected  void onResume() {
        super.onResume();
    }
}
