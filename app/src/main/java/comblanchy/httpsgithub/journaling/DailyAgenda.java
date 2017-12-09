package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * https://stackoverflow.com/questions/6151290/onfling-in-a-listview-get-the-swiped-item-info
 */
public class DailyAgenda extends Fragment {

    ListView listView;
    private JournalEntry[] dummy = {new JournalEntry(8, 11, 2017, "Stuff", "desc",
            JournalEntry.ARROW, JournalEntry.RED, false, 2, 63.4)};
    ArrayAdapter<JournalEntry> aa;

    private OnFragmentInteractionListener mListener;

    public DailyAgenda() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DailyAgenda newInstance(String param1, String param2) {
        DailyAgenda fragment = new DailyAgenda();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_daily_agenda, container, false);
        listView = (ListView) v.findViewById(R.id.entryList);

        JournalDBHelper db = new JournalDBHelper(getContext());
        ArrayList<JournalEntry> stuff = db.getAllEntries();

        aa = new JournalListAdapter(v.getContext(), R.layout.list_item, stuff);
        listView.setAdapter(aa);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
