package comblanchy.httpsgithub.journaling;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;

public class JournalEdit extends FragmentActivity implements WeatherDisplay.OnFragmentInteractionListener {

    private int mm;
    private int dd;
    private int yyyy;

    private int weatherCode = 0;
    private int weatherTemp = 0;

    private ImageView weatherIcon;

    private TextView date;
    private TextView title;
    private TextView description;

    private Switch important;

    private ImageButton increment;
    private ImageButton decrement;
    private Button submit;

    private static final int GREEN = Color.GREEN;
    private static final int RED = Color.RED;
    private static final int BLUE = Color.BLUE;
    private static final int[] bulletArray = {GREEN, RED, BLUE};
    private int bulletIndex = 0;

    private Fragment weatherDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_edit);

        Intent i = getIntent();
        if (i.hasExtra("mm")) {
            mm = i.getIntExtra("mm", 1);
        }
        if (i.hasExtra("dd")) {
            dd = i.getIntExtra("dd", 1);
        }
        if (i.hasExtra("yyyy")) {
            yyyy = i.getIntExtra("yyyy", 2017);
        }
        String dateformat = mm + " / " + dd + " / " + yyyy;

        //weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        //tempView = (TextView) findViewById(R.id.tempView);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.desc);
        date = (TextView) findViewById(R.id.date);

        date.setText(dateformat);

        important = (Switch) findViewById(R.id.flagtoggle);

        submit = (Button) findViewById(R.id.submitbutton);
        submit.setBackgroundColor(GREEN);
        submit.setTextColor(Color.WHITE);

        decrement = (ImageButton) findViewById(R.id.decrement);
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iterate(-1);
            }
        });
        increment = (ImageButton) findViewById(R.id.increment);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iterate(1);
            }
        });

        weatherDisplay = (WeatherDisplay) getSupportFragmentManager().findFragmentById(R.id.weatherfragment);
/**
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragframe, weatherDisplay)
                .commit();
 **/


    }

    public int iterate(int i) {
        bulletIndex = bulletIndex + i;
        if (bulletIndex > bulletArray.length-1) {
            bulletIndex = 0;
        }
        else if (bulletIndex < 0) {
            bulletIndex = bulletArray.length-1;
        }
        submit.setBackgroundColor(bulletArray[bulletIndex]);
        return bulletIndex;
    }

    public void createEntry() {
        String sTitle = (String) title.getText().toString();
        String sDesc = (String) description.getText().toString();
        boolean imp = important.isChecked();
        int color = bulletIndex;
        int code = ((WeatherDisplay) weatherDisplay).getCode();
        double temp = ((WeatherDisplay) weatherDisplay).getTemp();

        Log.v("Entry info", sTitle);
        Log.v("Entry info", sDesc);
        Log.v("Entry info", code + "");
        Log.v("Entry info", temp + "");

        JournalEntry je = new JournalEntry(dd, mm, yyyy,
                sTitle, sDesc,
                JournalEntry.CIRCLE, bulletIndex, imp,
                weatherCode, weatherTemp);

    }

    public void submit(View view) {
        createEntry();

        Intent intent = new Intent(this, DailyView.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
