package comblanchy.httpsgithub.journaling;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class DetailedAgenda extends FragmentActivity implements DailyAgenda.OnFragmentInteractionListener, DailyAgenda.OnItemSelectedListener{

    private int yyyy;
    private int mm;
    private int dd;
    private TextView agendaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_agenda);

        if (savedInstanceState == null) {
            Log.v("DetailedAgenda", "null state");
            Calendar c = Calendar.getInstance();
            Intent intent = getIntent();
            yyyy = intent.getIntExtra("yyyy", c.get(Calendar.YEAR));
            mm = intent.getIntExtra("mm", c.get(Calendar.MONTH));
            dd = intent.getIntExtra("dd", c.get(Calendar.DAY_OF_MONTH));
        }
        else {
            Log.v("DetailedAgenda", "instance state");
            int[] date = savedInstanceState.getIntArray("SelectedDate");
            yyyy = date[0];
            mm = date[1];
            dd = date[2];
        }

        agendaText = (TextView) findViewById(R.id.agendatext);
        agendaText.setText(String.format(getString(R.string.agenda_date), (mm+1) + "", yyyy + ""));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedAgenda.this, JournalEdit.class);
                intent.putExtra("yyyy", yyyy);
                intent.putExtra("mm", mm);
                intent.putExtra("dd", dd);
                startActivity(intent);
            }
        });

        DailyAgenda portrait = (DailyAgenda) getFragmentManager().findFragmentById(R.id.portrait);
        portrait.setDate(yyyy, mm, dd);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        int[] date = {yyyy, mm, dd};
        savedInstanceState.putIntArray("SelectedDate", date);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onEntrySelected(JournalEntry je) {
        DailyAgendaDetail fragment = (DailyAgendaDetail) getFragmentManager().findFragmentById(R.id.landscape);

        if (fragment != null && fragment.isInLayout()) {
            fragment.setJournalEntry(je);
        }
    }
}
