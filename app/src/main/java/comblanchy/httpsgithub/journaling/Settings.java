package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    private EditText greeting;
    private EditText city;
    private ToggleButton locate;
    boolean locPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        greeting = (EditText) findViewById(R.id.inGreet);
        city = (EditText) findViewById(R.id.inCity);
        locate = (ToggleButton) findViewById(R.id.toggleButton);

        final SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preferences),Context.MODE_PRIVATE);
        String greet = sharedPref.getString(getString(R.string.greeting_pref), getString(R.string.greeting_default));
        String sCity = sharedPref.getString(getString(R.string.city_pref), "San Francisco");
        locPref = sharedPref.getBoolean(getString(R.string.location_pref), false);


        greeting.setText(greet);
        city.setText(sCity);
        locate.setChecked(locPref);
        locate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checked(b);
            }
        });

        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, DailyView.class);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.location_pref), locPref);
                editor.putString(getString(R.string.greeting_pref), greeting.getText().toString());
                editor.putString(getString(R.string.city_pref), city.getText().toString());
                editor.commit();
                startActivity(intent);
            }
        });
    }

    public void checked(boolean b) {
        locPref = b;
    }
}
