package com.example.stephen.fyp_driving_lessons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class MyDBHandler extends SQLiteOpenHelper {

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

    public static final String TABLE_LEARNERS = "Learners";
    public static final String COLUMN_LEARNER_ID="id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_LEARNER_ADDRESS = "address";
    public static final String COLUMN_DRIVER_NUMBER = "driverNumber";
    public static final String COLUMN_NUMBER_LESSONS = "numberOfLessens";

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
                + "(" + COLUMN_INSTRUCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_INSTRUCTOR_NAME + " TEXT, "
                + COLUMN_INSTRUCTOR_EMAIL + " TEXT, "
                + COLUMN_INSTRUCTOR_DESC + " TEXT, "
                + COLUMN_INSTRUCTOR_SITE + " TEXT"
                + ");";
        sqLiteDatabase.execSQL(instructorQuery);

        String learnerQuery = "CREATE TABLE " + TABLE_LEARNERS
                + "(" + COLUMN_LEARNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_LEARNER_ADDRESS + " TEXT, "
                + COLUMN_DRIVER_NUMBER + " TEXT, "
                + COLUMN_NUMBER_LESSONS + " TEXT"
                + ");";
        sqLiteDatabase.execSQL(learnerQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTORS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LEARNERS);
        onCreate(sqLiteDatabase);
    }

    public void addLearner(Learners learner){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, learner.getName());
        values.put(COLUMN_EMAIL, learner.getEmail());
        values.put(COLUMN_ADDRESS, learner.getAddress());
        values.put(COLUMN_PHONE, learner.getPhone());
        values.put(COLUMN_DRIVER_NUMBER, learner.getDriverNum());
        values.put(COLUMN_NUMBER_LESSONS, learner.getNumLessons());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LEARNERS, null, values);
        db.close();
    }

    public String getAllLearners(){
        ArrayList<Learners> learners = new ArrayList<>();
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_LEARNERS;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Learners l = new Learners();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("name")) != null) {
                dbString += "Name: " + c.getString(c.getColumnIndex("name"));
                l.setName(dbString);
                dbString += "\n";
            }
            if (c.getString(c.getColumnIndex("email")) != null) {
                dbString += "Email: " + c.getString(c.getColumnIndex("email"));
                l.setName(dbString);
                dbString += "\n";
            }
            if (c.getString(c.getColumnIndex("address")) != null) {
                dbString += "Address: " + c.getString(c.getColumnIndex("address"));
                l.setName(dbString);
                dbString += "\n";
            }
            if (c.getString(c.getColumnIndex("phone")) != null) {
                dbString += "Phone: " + c.getString(c.getColumnIndex("phone"));
                l.setName(dbString);
                dbString += "\n";
            }
            if (c.getString(c.getColumnIndex("driverNumber")) != null) {
                dbString += "Driver Number: " + c.getString(c.getColumnIndex("driverNumber"));
                l.setName(dbString);
                dbString += "\n";
            }
            if (c.getString(c.getColumnIndex("numberOfLessens")) != null) {
                dbString += "Number of Lessons: " + c.getString(c.getColumnIndex("numberOfLessens"));
                l.setName(dbString);
                dbString += "\n________________________________";
            }
            learners.add(l);
            c.moveToNext();
            System.out.println(c.getCount() + "Learners");
            System.out.println(l.getName() + "\n" + l.getEmail() + "\n" + l.getAddress() + "\n" + l.getPhone() + "\n" + l.getDriverNum() + "\n" + l.getNumLessons());
        }
        db.close();
        return dbString;
    }

    public void addBooking(Bookings booking) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LEARNER_NAME, booking.getLearnerName());
        values.put(COLUMN_ADDRESS, booking.getAddress());
        values.put(COLUMN_DATE, booking.getDate());
        values.put(COLUMN_TIME, booking.getTime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_BOOKING, null, values);
        db.close();
    }


    public String getAllBookings() {
        ArrayList<Bookings> bookings = new ArrayList<>();
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKING;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Bookings b = new Bookings();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("learnerName")) != null) {
                dbString += "Name: " + c.getString(c.getColumnIndex("learnerName"));
                b.setLearnerName(dbString);
                dbString += "\n";
            }
            if (c.getString(c.getColumnIndex("address")) != null) {
                dbString += "Address: " + c.getString(c.getColumnIndex("address"));
                b.setAddress(dbString);
                dbString += "\n";
            }
            if (c.getString(c.getColumnIndex("date")) != null) {
                dbString += "Date: " + c.getString(c.getColumnIndex("date"));
                b.setDate(dbString);
                dbString += "\n";
            }
            if (c.getString(c.getColumnIndex("time")) != null) {
                dbString += "Time: " + c.getString(c.getColumnIndex("time"));
                b.setTime(dbString);
                dbString += "\n________________________________\n";
            }
            bookings.add(b);
            c.moveToNext();
            System.out.println(c.getCount() + "Bookings");
            System.out.println(b.getLearnerName() + "\n" + b.getAddress() + "\n" + b.getDate() + "\n" + b.getTime());
        }
        db.close();

        return dbString;
    }

    public void deleteAllBookings() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BOOKING + ";");
    }

    public void deleteAllInstructors() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_INSTRUCTORS + ";");
    }

    public void addInstructor(Instructors instructors) {
        ContentValues instructorValues = new ContentValues();
        instructorValues.put(COLUMN_INSTRUCTOR_NAME, instructors.getInstructorName());
        instructorValues.put(COLUMN_INSTRUCTOR_EMAIL, instructors.getInstructorEmail());
        instructorValues.put(COLUMN_INSTRUCTOR_DESC, instructors.getDescription());
        instructorValues.put(COLUMN_INSTRUCTOR_SITE, instructors.getWebsite());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_INSTRUCTORS, null, instructorValues);
        db.close();
    }

    public String getInstructors() {
        ArrayList<Instructors> instructorsArray = new ArrayList<>();
        String insString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INSTRUCTORS;
        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();
        Instructors instructors = new Instructors();
        while (!cur.isAfterLast()) {
            if (cur.getString(cur.getColumnIndex("instructorName")) != null) {
                insString += "Name: " + cur.getString(cur.getColumnIndex("instructorName"));
                instructors.setInstructorName(insString);
                insString += "\n";
            }
            if (cur.getString(cur.getColumnIndex("instructorEmail")) != null) {
                insString += "Email: " + cur.getString(cur.getColumnIndex("instructorEmail"));
                instructors.setInstructorEmail(insString);
                insString += "\n";
            }
            if (cur.getString(cur.getColumnIndex("instructorDescription")) != null) {
                insString += "Description: " + cur.getString(cur.getColumnIndex("instructorDescription"));
                instructors.setDescription(insString);
                insString += "\n";
            }
            if (cur.getString(cur.getColumnIndex("instructorWebsite")) != null) {
                insString += "Website: " + cur.getString(cur.getColumnIndex("instructorWebsite"));
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

    public List<String> getInstructorName() {
        List<String> names = new ArrayList<String>();
        String instructorName = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT DISTINCT instructorName FROM " + TABLE_INSTRUCTORS;
        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();

        while (!cur.isAfterLast()) {
            names.add(cur.getString(0));
            System.out.println(instructorName);
            cur.moveToNext();
            System.out.println(cur.getCount() );
        }
        db.close();
        return names;
    }

    public List<String> getTimes(){
        List<String> times = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT DISTINCT time FROM " + TABLE_BOOKING;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            times.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return times;
    }

    public List<String> getDates(){
        List<String> times = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT DISTINCT date FROM " + TABLE_BOOKING;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            times.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return times;
    }
}
