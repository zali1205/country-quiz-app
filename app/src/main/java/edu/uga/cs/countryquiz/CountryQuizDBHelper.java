package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * This is a SQLiteOpenHelper class, a singleton for managing the internal sqlite database
 * Borrows from JobLeadsSQLite example
 *
 * Author - Drew Jenkins
 */
public class CountryQuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "CountryQuizDBHelper";

    private static final String DB_NAME = "countryQuiz.db";
    private static final int DB_VERSION = 1;

    // Define the the table & column names for the countries
    public static final String TABLE_COUNTRY = "country";
    public static final String COUNTRY_COLUMN_ID = "_id";
    public static final String COUNTRY_COLUMN_NAME = "name";
    public static final String COUNTRY_COLUMN_CONTINENT = "continents";

    // Define the the table & column names for the quizzes
    public static final String TABLE_QUIZ = "quiz";
    public static final String QUIZ_COLUMN_ID = "_id";
    public static final String QUIZ_COLUMN_DATE = "date";
    public static final String QUIZ_COLUMN_RESULT = "results";

    // This is a reference to the only instance for the helper.
    private static CountryQuizDBHelper helperInstance;

    // Create country table with necessary rules for the columns
    private static final String CREATE_COUNTRY =
            "create table " + TABLE_COUNTRY + " ("
                    + COUNTRY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COUNTRY_COLUMN_NAME + " TEXT, "
                    + COUNTRY_COLUMN_CONTINENT + " TEXT"
                    + ")";

    // Create quiz table with necessary rules for the columns
    private static final String CREATE_QUIZ =
            "create table " + TABLE_QUIZ + " ("
                    + QUIZ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZ_COLUMN_DATE + " TEXT, "
                    + QUIZ_COLUMN_RESULT + " INTEGER"
                    + ")";

    // For singleton the constructor must be private so it is limited to the getInstance method
    private CountryQuizDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    // For finding access for other classes and synchronized to it doesn't get done twice or more
    public static synchronized CountryQuizDBHelper getInstance( Context context ) {
        // if not instantiated create first instance
        if( helperInstance == null ) {
            helperInstance = new CountryQuizDBHelper( context.getApplicationContext() );
            Log.d( DEBUG_TAG, "helper instance created" );
        }
        return helperInstance;
    }

    // If the database does not already exist we will create it and both important tables.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_COUNTRY );
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRY + " created" );
        db.execSQL( CREATE_QUIZ );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZ + " created" );
    }

    // should  the version be changed on the database it will update the tables by dropping them
    // & creating new tables for them
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_COUNTRY );
        db.execSQL( "drop table if exists " + TABLE_QUIZ );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRY + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZ + " upgraded" );
    }
}