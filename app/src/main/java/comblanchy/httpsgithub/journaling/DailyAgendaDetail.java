package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DailyAgendaDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DailyAgendaDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyAgendaDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ImageView weather;
    TextView temp;
    TextView desc;
    TextView title;
    TextView day;

    public DailyAgendaDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyAgendaDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyAgendaDetail newInstance(String param1, String param2) {
        DailyAgendaDetail fragment = new DailyAgendaDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daily_agenda_detail, container, false);
        weather = (ImageView) v.findViewById(R.id.weather);
        temp = (TextView) v.findViewById(R.id.temp);
        title = (TextView) v.findViewById(R.id.journalTitle);
        desc = (TextView) v.findViewById(R.id.desc);
        day = (TextView) v.findViewById(R.id.date);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setJournalEntry(JournalEntry je) {
        title.setText(je.getTitle());
        temp.setText(je.getWeatherTemp()+ " °F");
        desc.setText(je.getDesc());
        day.setText("Day "+je.getDay());
        //Log.v("getting weather", ""+je.getWeatherCode());
        switch(je.getWeatherCode()) {
            case 1 : weather.setImageResource(R.drawable.sun_icon);
                break;
            case 2 : weather.setImageResource(R.drawable.thunder_icon);
                break;
            case 3 : weather.setImageResource(R.drawable.rain_icon);
                break;
            case 7 : weather.setImageResource(R.drawable.fog_icon);
                break;
            case 8 : weather.setImageResource(R.drawable.cloud_icon);
                break;
            case 6 : weather.setImageResource(R.drawable.snow_icon);
                break;
            case 5 : weather.setImageResource(R.drawable.storm_icon);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
