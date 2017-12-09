package comblanchy.httpsgithub.journaling;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

/**
 * Created by blanchypolangcos on 12/8/17.
 */

public class AdapterGestureDetector implements GestureDetector.OnGestureListener {


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.v("Gesture", "Down");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.v("Gesture", "Tap up");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.v("Gesture", "Scroll");
        final float xDist = Math.abs(motionEvent.getX() - motionEvent1.getX());
        final float yDist = Math.abs(motionEvent.getY() - motionEvent1.getY());

        try {
            if (v > 30 && xDist > 30) { // horizontal swipe
                if (motionEvent.getX() > motionEvent1.getX()) {
                    // left to right
                    Log.v("Swipe detected", "Left");
                    //int id = listView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    //JournalEntry je = aa.getItem(id);
                    //Log.v("Swipe detected - journal title", je.getTitle());
                    return true;
                } else {
                    // right to left
                    Log.v("Swipe detected", "Right");
                    //int id = listView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    //JournalEntry je = aa.getItem(id);
                    //Log.v("Swipe detected - journal title", je.getTitle());
                    return true;
                }
            }
        } catch (Exception e) {
            Log.v("Swipe detected", "nothing here");
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.v("Gesture", "Long Press");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        final float xDist = Math.abs(motionEvent.getX() - motionEvent1.getX());
        final float yDist = Math.abs(motionEvent.getY() - motionEvent1.getY());

        try {
            if (v > 30 && xDist > 30) { // horizontal swipe
                if (motionEvent.getX() > motionEvent1.getX()) {
                    // left to right
                    Log.v("Swipe detected", "Left");
                    //int id = listView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    //JournalEntry je = aa.getItem(id);
                    //Log.v("Swipe detected - journal title", je.getTitle());
                    return true;
                } else {
                    // right to left
                    Log.v("Swipe detected", "Right");
                    //int id = listView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    //JournalEntry je = aa.getItem(id);
                    //Log.v("Swipe detected - journal title", je.getTitle());
                    return true;
                }
            }
        } catch (Exception e) {
            Log.v("Swipe detected", "nothing here");
            return true;
        }
        return false;
    }

}
