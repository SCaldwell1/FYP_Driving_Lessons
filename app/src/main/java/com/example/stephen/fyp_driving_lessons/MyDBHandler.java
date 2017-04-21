package com.example.stephen.fyp_driving_lessons;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;


public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "drivingLessons.db";
    public static final String TABLE_BOOKING = "booking";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LEARNER_NAME = "learnerName";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    public static final String TABLE_INSTRUCTORS = "Instructors";
    public static final String COLUMN_INSTRUCTOR_ID = "_id";
    public static final String COLUMN_INSTRUCTOR_NAME = "Name";
    public static final String COLUMN_INSTRUCTOR_EMAIL = "Email";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_BOOKING
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LEARNER_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_TIME + " TEXT" + ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        onCreate(sqLiteDatabase);
    }

    public void addBooking (Bookings booking){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LEARNER_NAME, booking.getLearnerName());
        values.put(COLUMN_ADDRESS, booking.getAddress());
        values.put(COLUMN_DATE, booking.getDate());
        values.put(COLUMN_TIME, booking.getTime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_BOOKING, null, values);
        db.close();
    }

    public ArrayList<Bookings> getAllBookings(){
        ArrayList<Bookings> bookings = new ArrayList<>();
        String dbString="";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKING;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Bookings b = new Bookings();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("LearnerName"))!=null){
                dbString += c.getString(c.getColumnIndex("LearnerName"));
                b.setLearnerName(dbString);
            }
            if(c.getString(c.getColumnIndex("Address"))!=null){
                dbString += c.getString(c.getColumnIndex("Address"));
                b.setAddress(dbString);
            }
            if(c.getString(c.getColumnIndex("Date"))!=null){
                dbString += c.getString(c.getColumnIndex("Date"));
                b.setDate(dbString);
            }
            if(c.getString(c.getColumnIndex("Time"))!=null){
                dbString += c.getString(c.getColumnIndex("Time"));
                b.setTime(dbString);
            }
            bookings.add(b);
            c.moveToNext();
            System.out.println(c.getCount() + "Bookings");
            System.out.println(b.getLearnerName() +"\n" + b.getAddress() +"\n" + b.getDate()+"\n" +b.getTime());
        }
        db.close();

        return bookings;
    }

    public void deleteAllBookings() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_BOOKING+ ";");
    }

}
