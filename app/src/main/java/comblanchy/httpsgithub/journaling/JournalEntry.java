package comblanchy.httpsgithub.journaling;

import android.graphics.Color;

/**
 * Created by blanchypolangcos on 11/23/17.
 * https://code.tutsplus.com/tutorials/create-a-weather-app-on-android--cms-21587
 */

public class JournalEntry {

    private int _id;
    private int month;
    private int day;
    private int year;
    private String title;
    private String desc;
    private int type;
    private int color;
    private int important;

    private int weatherCode;
    private double weatherTemp;

    static final int EX = 0;
    static final int CIRCLE = 1;
    static final int ARROW = 2;

    static final int GREEN = Color.GREEN;
    static final int RED = Color.RED;
    static final int BLUE = Color.BLUE;
    static final int MAGENTA = Color.MAGENTA;

    public JournalEntry() {

    }

    public JournalEntry(int _id, int day, int month, int year, String title, String desc, int type, int color, boolean important, int weatherCode, double weatherTemp) {
        this._id = _id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.color = color;
        if (important) {
            this.important = 1;
        }
        else {
            this.important = 0;
        }
        this.weatherCode = weatherCode;
        this.weatherTemp = weatherTemp;
    }

    public JournalEntry(int day, int month, int year, String title, String desc, int type, int color, boolean important, int weatherCode, double weatherTemp) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.color = color;
        if (important) {
            this.important = 1;
        }
        else {
            this.important = 0;
        }
        this.weatherCode = weatherCode;
        this.weatherTemp = weatherTemp;
    }

    public int get_id() {
        return _id;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public int isImportant() {
        return important;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public double getWeatherTemp() {
        return weatherTemp;
    }

    public void set_id(int id) {
        _id = id;
    }

    public void setDay(int d) {
        day = d;
    }

    public void setMonth(int m) {
        month = m;
    }

    public void setYear(int y) {
        year = y;
    }

    public void setTitle(String s) {
       title = s;
    }

    public void setDesc(String s) {
        desc = s;
    }

    public void setType(int t) {
        type = t;
    }

    public void setColor(int t) {
        color = t;
    }

    public void setImportant(boolean i) {
        if (i) {
            important = 1;
        }
        else {
            important = 0;
        }
    }

    public void setWeatherCode(int t) {
        weatherCode = t;
    }

    public void setWeatherTemp(int t) {
        weatherTemp = t;
    }
}
