package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by blanchypolangcos on 12/2/17.
 * http://codesfor.in/android-swipe-to-delete-listview/
 * https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
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
        JournalEntry je = (JournalEntry) getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);

            ViewHolder vh = new ViewHolder();

            vh.item = (TextView) convertView.findViewById(R.id.item);
            vh.bullet = (ImageView) convertView.findViewById(R.id.bullet);
            vh.itemContainer = (RelativeLayout) convertView.findViewById(R.id.itemcontainer);

            vh.item.setText(je.getTitle());
            Log.v("list agenda", je.getTitle());
            vh.bullet.setImageDrawable(changeBullet(je.getType(), je.getColor()));
            Log.v("list agenda type", je.getType()+"");
            Log.v("list agenda color", je.getColor()+"");
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
        return convertView;

    }

    public Drawable changeBullet(int type, int color) {
        Drawable d;
        if (color == JournalEntry.GREEN) {
            if (type == JournalEntry.EX) {
                d = context.getResources().getDrawable(R.drawable.bullet_done_green, null);
            }
            else if (type == JournalEntry.ARROW) {
                d = context.getResources().getDrawable(R.drawable.bullet_next_green, null);
            }
            else {
                d = context.getResources().getDrawable(R.drawable.bullet_todo_green, null);
            }
        }
        else if (color == JournalEntry.BLUE) {
            if (type == JournalEntry.EX) {
                d = context.getResources().getDrawable(R.drawable.bullet_done_blue, null);
            }
            else if (type == JournalEntry.ARROW) {
                d = context.getResources().getDrawable(R.drawable.bullet_next_blue, null);
            }
            else {
                d = context.getResources().getDrawable(R.drawable.bullet_todo_blue, null);
            }
        }
        else { //red
            if (type == JournalEntry.EX) {
                d = context.getResources().getDrawable(R.drawable.bullet_done_red, null);
            }
            else if (type == JournalEntry.ARROW) {
                d = context.getResources().getDrawable(R.drawable.bullet_next_red, null);
            }
            else {
                d = context.getResources().getDrawable(R.drawable.bullet_todo_red, null);
            }
        }
        return d;
    }

    public class ViewHolder {
        TextView item;
        ImageView bullet;
        RelativeLayout itemContainer;
    }

}
