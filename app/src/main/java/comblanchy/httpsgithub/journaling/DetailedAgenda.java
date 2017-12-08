package comblanchy.httpsgithub.journaling;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class DetailedAgenda extends FragmentActivity implements DailyAgenda.OnFragmentInteractionListener{

    private int yyyy;
    private int mm;
    private int dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_agenda);

        if (savedInstanceState == null) {
            Calendar c = Calendar.getInstance();
            Intent intent = getIntent();
            yyyy = intent.getIntExtra("yyyy", c.get(Calendar.YEAR));
            mm = intent.getIntExtra("mm", c.get(Calendar.MONTH));
            dd = intent.getIntExtra("dd", c.get(Calendar.DAY_OF_MONTH));
        }
        else {
            yyyy = savedInstanceState.getInt("yyyy");
            mm = savedInstanceState.getInt("mm");
            dd = savedInstanceState.getInt("dd");
        }


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
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        int[] date = {yyyy, mm, dd};
        savedInstanceState.putIntArray("SelectedDate", date);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
