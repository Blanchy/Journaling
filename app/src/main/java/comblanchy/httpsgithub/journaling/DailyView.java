package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DailyView extends AppCompatActivity implements DailyAgenda.OnFragmentInteractionListener {

    private FloatingActionButton addEntry;
    private DatePicker datePicker;
    private TextView greetings;
    private TextView agenda;

    private SensorManager sm;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            greetings = (TextView) findViewById(R.id.datetext);
            greetings.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent toSettings = new Intent(DailyView.this, Settings.class);
                    startActivity(toSettings);
                    return true;
                }
            });
        }

        agenda = (TextView) findViewById(R.id.todaytext);
        agenda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent toAgenda = new Intent(DailyView.this, DetailedAgenda.class);
                startActivity(toAgenda);
                return true;
            }
        });


        Calendar c = Calendar.getInstance();
        final TextView agenda = (TextView) findViewById(R.id.todaytext);
        addEntry = (FloatingActionButton) findViewById(R.id.addEntry);

        datePicker.init(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        Log.v("datepicker", i + " " + i1 + " " + i2);
                        agenda.setText("Changed to " + i);
                        dateChanged(i, i1, i2);
                    }
                });


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector(
                new ShakeDetector.OnShakeListener() {
                    @Override
                    public void onShake() {
                        Calendar c = Calendar.getInstance();
                        int y = c.get(Calendar.YEAR);
                        int m = c.get(Calendar.MONTH);
                        int d = c.get(Calendar.DAY_OF_MONTH);

                        datePicker.updateDate(c.get(Calendar.YEAR),
                                c.get(Calendar.MONTH),
                                c.get(Calendar.DAY_OF_MONTH));
                        dateChanged(y,m,d);
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
        intent.putExtra("yyyy", datePicker.getYear());
        intent.putExtra("mm", datePicker.getMonth());
        intent.putExtra("dd", datePicker.getDayOfMonth());
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
