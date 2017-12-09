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

    private SQLiteDatabase db;

    private JournalEntry sample1 = new JournalEntry(8,11,2017,"eat some bread","lots of bread",JournalEntry.CIRCLE,JournalEntry.BLUE,false,0,0);

    private static final String CREATE_DB_TABLE = "CREATE TABLE " + DATABASE_TABLE +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " year INT, " +
                    " month INT, " +
                    " day INT, " +
                    " title STRING, " +
                    " description STRING, " +
                    " type INT, " +
                    " color INT, " +
                    " important INT, " +
                    " code INT, " +
                    " temp INT" +
                    ");";;
    private static final String FIRST_ENTRY = "INSERT INTO " + DATABASE_TABLE +
            " VALUES (0, 2017, 11, 8, 'eat some bread', 'lots of bread', 1, 1, 0, 2, 1.3);";

    public JournalDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
        //sqLiteDatabase.execSQL(FIRST_ENTRY);
        db = sqLiteDatabase;
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
        Log.v("DB addEntry", entryCount+"");
        ContentValues values = new ContentValues();
        //values.put(ID, entryCount);
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
        Log.v("new row id", newRowId + "");
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
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + MONTH + " = " + month;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        Log.v("DB access by month", c.moveToFirst() + "");

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
                Log.v("Making entry with year ",c.getInt(0) + "");
                Log.v("Making entry with year ",c.getDouble(10) + "");
            } while (c.moveToNext());
        }

        return entryList;
    }

    public ArrayList<JournalEntry> getEntriesByDate(int year, int month, int date) {
        ArrayList<JournalEntry> entryList = new ArrayList<JournalEntry>();

        String query = "SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + MONTH + " = " + month + " AND " + YEAR + " = " + year;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Entry where month = " + month, null);
        //Log.v("DB access cursor", "Cursoer is " + c);
        Log.v("DB access by day", c.moveToFirst() + "");

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


                Log.v("DB creating entry","year " + c.getInt(1));
                Log.v("DB creating entry","month " + c.getInt(2));
                Log.v("DB creating entry","date " + c.getInt(3));
                Log.v("DB creating entry","title " + c.getString(4));
                Log.v("DB creating entry","desc " + c.getString(5));
                Log.v("DB creating entry","code " + c.getInt(9));
                Log.v("DB creating entry","temp " + c.getInt(10));
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
