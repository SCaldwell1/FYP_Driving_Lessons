package com.example.stephen.fyp_driving_lessons;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.widget.ArrayAdapter;

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
    public static final String COLUMN_INSTRUCTOR_NAME = "instructorName";
    public static final String COLUMN_INSTRUCTOR_EMAIL = "instructorEmail";
    public static final String COLUMN_INSTRUCTOR_DESC = "instructorDescription";
    public static final String COLUMN_INSTRUCTOR_SITE = "instructorWebsite";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String bookingQuery = "CREATE TABLE " + TABLE_BOOKING
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LEARNER_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_TIME + " TEXT" + ");";
        sqLiteDatabase.execSQL(bookingQuery);

        String instructorQuery = "CREATE TABLE " + TABLE_INSTRUCTORS
                +"("+ COLUMN_INSTRUCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_INSTRUCTOR_NAME + " TEXT, "
                +COLUMN_INSTRUCTOR_EMAIL + " TEXT, "
                +COLUMN_INSTRUCTOR_DESC + " TEXT, "
                +COLUMN_INSTRUCTOR_SITE + " TEXT"
                + ");";
        sqLiteDatabase.execSQL(instructorQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING );
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTORS);
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


    public String getAllBookings(){
        ArrayList<Bookings> bookings = new ArrayList<>();
        String dbString="";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKING;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Bookings b = new Bookings();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("learnerName"))!=null){
                dbString += "Name: " + c.getString(c.getColumnIndex("learnerName"));
                b.setLearnerName(dbString);
                dbString += "\n";
            }
            if(c.getString(c.getColumnIndex("address"))!=null){
                dbString += "Address: " + c.getString(c.getColumnIndex("address"));
                b.setAddress(dbString);
                dbString += "\n";
            }
            if(c.getString(c.getColumnIndex("date"))!=null){
                dbString += "Date: " + c.getString(c.getColumnIndex("date"));
                b.setDate(dbString);
                dbString += "\n";
            }
            if(c.getString(c.getColumnIndex("time"))!=null){
                dbString += "Time: " + c.getString(c.getColumnIndex("time"));
                b.setTime(dbString);
                dbString += "\n________________________________\n";
            }
            bookings.add(b);
            c.moveToNext();
            System.out.println(c.getCount() + "Bookings");
            System.out.println(b.getLearnerName() +"\n" + b.getAddress() +"\n" + b.getDate()+"\n" +b.getTime());
        }
        db.close();

        return dbString;
    }

    public void deleteAllBookings() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_BOOKING+ ";");
    }
    public void deleteAllInstructors() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_INSTRUCTORS+ ";");
    }
    public void addInstructor (Instructors instructors){
        ContentValues instructorValues = new ContentValues();
        instructorValues.put(COLUMN_INSTRUCTOR_NAME, instructors.getInstructorName());
        instructorValues.put(COLUMN_INSTRUCTOR_EMAIL, instructors.getInstructorEmail());
        instructorValues.put(COLUMN_INSTRUCTOR_DESC, instructors.getDescription());
        instructorValues.put(COLUMN_INSTRUCTOR_SITE, instructors.getWebsite());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_INSTRUCTORS, null, instructorValues);
        db.close();
    }
    public String getInstructors(){
        ArrayList<Instructors> instructorsArray = new ArrayList<>();
        String insString="";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INSTRUCTORS;
        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();
        Instructors instructors = new Instructors();
        while (!cur.isAfterLast()){
            if (cur.getString(cur.getColumnIndex("instructorName")) != null){
                insString += "Name: " + cur.getString(cur.getColumnIndex("instructorName"));
                instructors.setInstructorName(insString);
                insString += "\n";
            }
            if (cur.getString(cur.getColumnIndex("instructorEmail")) != null){
                insString += "Email: " +  cur.getString(cur.getColumnIndex("instructorEmail"));
                instructors.setInstructorEmail(insString);
                insString += "\n";
            }
            if (cur.getString(cur.getColumnIndex("instructorDescription")) != null){
                insString += "Description: " + cur.getString(cur.getColumnIndex("instructorDescription"));
                instructors.setDescription(insString);
                insString += "\n";
            }
            if (cur.getString(cur.getColumnIndex("instructorWebsite")) != null){
                insString +="Website: " +  cur.getString(cur.getColumnIndex("instructorWebsite"));
                instructors.setWebsite(insString);
                insString += "\n________________________________\n";
            }
            instructorsArray.add(instructors);
            cur.moveToNext();
            System.out.println(cur.getCount() + " Instructors");
            System.out.println("Name: " + instructors.getInstructorName() + "\nEmail: " + instructors.getInstructorEmail() + "\nDescription: " + instructors.getDescription() + "\nWebsite: " + instructors.getWebsite());
        }

        db.close();
        return insString;
    }


}
