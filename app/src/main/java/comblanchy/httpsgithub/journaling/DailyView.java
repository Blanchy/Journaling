package comblanchy.httpsgithub.journaling;

import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class DailyView extends AppCompatActivity implements DailyAgenda.OnFragmentInteractionListener {

    private FloatingActionButton addEntry;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        final TextView agenda = (TextView) findViewById(R.id.todaytext);
        datePicker.init(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        Log.v("datepicker", i + " " + i1 + " " + i2);
                        agenda.setText("Changed to " + i);
                    }
                });

        addEntry = (FloatingActionButton) findViewById(R.id.addEntry);
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
