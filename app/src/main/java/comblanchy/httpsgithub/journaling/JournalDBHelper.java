package comblanchy.httpsgithub.journaling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by blanchypolangcos on 12/3/17.
 */

public class JournalDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Journal";
    private static final String DATABASE_TABLE = "Entry";

    private static final String ID = "_id";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String TYPE = "type";
    private static final String COLOR = "color";
    private static final String IMPORTANT = "important";
    private static final String CODE = "code";
    private static final String TEMP = "temp";

    private int entryCount = 0;

    private JournalEntry sample1 = new JournalEntry(8,12,2017,"eat some bread","lots of bread",JournalEntry.CIRCLE,JournalEntry.BLUE,false,0,0);

    private static final String CREATE_DB_TABLE = "CREATE TABLE " + DATABASE_TABLE +
            " (_id INTEGER PRIMARY KEY, " +
                    " year TEXT, " +
                    " month TEXT, " +
                    " day INT, " +
                    " title STRING, " +
                    " description STRING, " +
                    " type INT, " +
                    " color INT, " +
                    " important INT, " +
                    " code INT, " +
                    " temp INT" +
                    ");";;


    public JournalDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
        addEntry(sample1);
        Log.d("DATABASE", "is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addEntry(JournalEntry je) {
        SQLiteDatabase db = this.getWritableDatabase();

        entryCount++;

        ContentValues values = new ContentValues();
        values.put(ID, entryCount);
        values.put(YEAR, je.getYear());
        values.put(MONTH, je.getMonth());
        values.put(DAY, je.getDay());
        values.put(TITLE, je.getTitle());
        values.put(DESCRIPTION, je.getDesc());
        values.put(TYPE, je.getType());
        values.put(COLOR, je.getColor());
        values.put(IMPORTANT, je.isImportant());
        values.put(CODE, je.getWeatherCode());
        values.put(TEMP, je.getWeatherTemp());

        long newRowId = db.insert(DATABASE_TABLE, null, values);

        db.close();
    }

    public void editEntry(JournalEntry je) {

        ContentValues values = new ContentValues();
        values.put(YEAR, je.getYear());
        values.put(MONTH, je.getMonth());
        values.put(DAY, je.getDay());
        values.put(TITLE, je.getTitle());
        values.put(DESCRIPTION, je.getDesc());
        values.put(TYPE, je.getType());
        values.put(COLOR, je.getColor());
        values.put(IMPORTANT, je.isImportant());
        values.put(CODE, je.getWeatherCode());
        values.put(TEMP, je.getWeatherTemp());

        SQLiteDatabase db = this.getWritableDatabase();

        db.update(DATABASE_TABLE, values, ID + " = ?", new String[] {
                String.valueOf(je.get_id())
        });

        db.close();
    }

    public void removeEntry(JournalEntry je) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(DATABASE_TABLE, ID + " = ?", new String[] {String.valueOf(je.get_id())});

        db.close();
    }

    public JournalEntry getEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DATABASE_TABLE,
                new String[] {ID, DAY, MONTH, YEAR, TITLE, DESCRIPTION, TYPE, COLOR, IMPORTANT, CODE, TEMP},
                ID + " = ?",
                new String[] {String.valueOf(id)},
                null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        int imp = c.getInt(8);
        boolean b = false;
        if (imp == 0) {
            b = false;
        }
        else {
            b = true;
        }

        JournalEntry je = new JournalEntry(c.getInt(0),
                c.getInt(1),
                c.getInt(2),
                c.getInt(3),
                c.getString(4),
                c.getString(5),
                c.getInt(6),
                c.getInt(7),
                b,
                c.getInt(9),
                c.getDouble(10));

        db.close();
        return je;
    }

    public ArrayList<JournalEntry> getAllEntries() {
        ArrayList<JournalEntry> entryList = new ArrayList<JournalEntry>();
        String query = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                int imp = c.getInt(8);
                boolean b = false;
                if (imp == 0) {
                    b = false;
                }
                else {
                    b = true;
                }

                JournalEntry je = new JournalEntry(c.getInt(0),
                        c.getInt(1),
                        c.getInt(2),
                        c.getInt(3),
                        c.getString(4),
                        c.getString(5),
                        c.getInt(6),
                        c.getInt(7),
                        b,
                        c.getInt(9),
                        c.getDouble(10));

                entryList.add(je);
            } while (c.moveToNext());
        }

        return entryList;
    }

    public ArrayList<JournalEntry> getEntriesByMonth(int year, int month) {
        ArrayList<JournalEntry> entryList = new ArrayList<JournalEntry>();
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + MONTH + " = " + month
                + " AND " + YEAR + " = " + year;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                int imp = c.getInt(8);
                boolean b = false;
                if (imp == 0) {
                    b = false;
                }
                else {
                    b = true;
                }

                JournalEntry je = new JournalEntry(c.getInt(0),
                        c.getInt(1),
                        c.getInt(2),
                        c.getInt(3),
                        c.getString(4),
                        c.getString(5),
                        c.getInt(6),
                        c.getInt(7),
                        b,
                        c.getInt(9),
                        c.getDouble(10));

                entryList.add(je);
            } while (c.moveToNext());
        }

        return entryList;
    }

    public ArrayList<JournalEntry> getEntriesByDate(int year, int month, int date) {
        ArrayList<JournalEntry> entryList = new ArrayList<JournalEntry>();
        String query = "SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + MONTH + " = " + month +
                " AND " + DAY + " = " + date +
                " AND " + year + " = " + year;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                int imp = c.getInt(8);
                boolean b = false;
                if (imp == 0) {
                    b = false;
                }
                else {
                    b = true;
                }

                JournalEntry je = new JournalEntry(c.getInt(0),
                        c.getInt(1),
                        c.getInt(2),
                        c.getInt(3),
                        c.getString(4),
                        c.getString(5),
                        c.getInt(6),
                        c.getInt(7),
                        b,
                        c.getInt(9),
                        c.getDouble(10));

                entryList.add(je);
            } while (c.moveToNext());
        }

        return entryList;
    }
}
