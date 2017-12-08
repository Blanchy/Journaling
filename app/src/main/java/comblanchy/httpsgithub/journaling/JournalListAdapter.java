package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by blanchypolangcos on 12/2/17.
 * http://codesfor.in/android-swipe-to-delete-listview/
 */

public class JournalListAdapter extends ArrayAdapter {
    private int layout;
    private List<JournalEntry> objects;
    private Context context;

    public JournalListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<JournalEntry> objects) {
        super(context, resource, objects);
        this.objects = objects;
        layout = resource;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);

            ViewHolder vh = new ViewHolder();


            vh.item = (TextView) convertView.findViewById(R.id.itemtext);
            vh.bullet = (ImageView) convertView.findViewById(R.id.bullet);
            convertView.setTag(vh);
        }

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        //HashMap<String, Integer> ingredients = RecipeHolder.getInstance().getIngredients();

        mainViewholder = (ViewHolder) convertView.getTag();
        // add fling listener


        //mainViewholder.item.setText(getItem(position));

        Log.d("list agenda", "got name...");
        return convertView;

    }

    public class ViewHolder {
        TextView item;
        ImageView bullet;
    }

}
