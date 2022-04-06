package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class facilitates storing and restoring countries and quizzes from the database.
 *
 * Author - Drew Jenkins
 */
public class CountryQuizData {

    public static final String DEBUG_TAG = "CountryQuizData";

    private SQLiteDatabase   db; // database ref for SQL commands
    private SQLiteOpenHelper countryQuizDbHelper;
    private static final String[] quizColumns = {
            CountryQuizDBHelper.QUIZ_COLUMN_ID,
            CountryQuizDBHelper.QUIZ_COLUMN_DATE,
            CountryQuizDBHelper.QUIZ_COLUMN_RESULT
    };
    private static final String[] countryColumns = {
            CountryQuizDBHelper.COUNTRY_COLUMN_ID,
            CountryQuizDBHelper.COUNTRY_COLUMN_NAME,
            CountryQuizDBHelper.COUNTRY_COLUMN_CONTINENT
    };

    public CountryQuizData( Context context ) {
        this.countryQuizDbHelper = CountryQuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = countryQuizDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "CountryQuizData: db open" );
    }

    // Close the database
    public void close() {
        if( countryQuizDbHelper != null ) {
            countryQuizDbHelper.close();
            Log.d(DEBUG_TAG, "CountryQuizData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    // Retrieve all countries and return them as a List.
    // for retrieving the stored countries after having been read from csv file on initial startup.
    public List<Country> retrieveAllCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Execute the select query on a cursor to iterate over stored countries
            cursor = db.query( CountryQuizDBHelper.TABLE_COUNTRY, countryColumns, null, null, null, null, null );

            // collect countries for list object
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    // get all attribute values of this country
                    long id = cursor.getLong( cursor.getColumnIndex( CountryQuizDBHelper.COUNTRY_COLUMN_ID ) );
                    String name = cursor.getString( cursor.getColumnIndex( CountryQuizDBHelper.COUNTRY_COLUMN_NAME ) );
                    String continent = cursor.getString( cursor.getColumnIndex( CountryQuizDBHelper.COUNTRY_COLUMN_CONTINENT ) );

                    // create a new country with all of the new values
                    Country country = new Country( name, continent );
                    country.setId( id ); // seeds auto incremented id
                    countries.add( country ); // add to list
                    Log.d( DEBUG_TAG, "Retrieved country: " + country );
                }
            }
            if( cursor != null ) // print number of total rows
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we must close the cursor to prevent hogging resources
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved countries
        return countries;
    }

    // retrieve all quiz records from database and store in a list
    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;

        try {
            // use select to find all quiz records with a cursor to iterate over
            cursor = db.query( CountryQuizDBHelper.TABLE_QUIZ, quizColumns, null, null, null, null, null );

            // collect quizzes for list object
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    // get all attribute values of this country
                    long id = cursor.getLong( cursor.getColumnIndex( CountryQuizDBHelper.QUIZ_COLUMN_ID ) );
                    String date = cursor.getString( cursor.getColumnIndex( CountryQuizDBHelper.QUIZ_COLUMN_DATE ) );
                    int result = cursor.getInt( cursor.getColumnIndex( CountryQuizDBHelper.QUIZ_COLUMN_RESULT ) );

                    // create a new quiz with all respective values
                    Quiz quiz = new Quiz( date, result );
                    quiz.setId( id ); // seed auto incremented id
                    quizzes.add( quiz ); // adds to list
                    Log.d( DEBUG_TAG, "Retrieved quiz: " + quiz );
                }
            }
            if( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we must close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return list of current quiz records
        return quizzes;
    }

    // storing new quiz into database
    public Quiz storeQuiz( Quiz quiz ) {

        // all values for the database will need to be prepared to insert.
        ContentValues values = new ContentValues();
        values.put( CountryQuizDBHelper.QUIZ_COLUMN_DATE, quiz.getQuizDate());
        values.put( CountryQuizDBHelper.QUIZ_COLUMN_RESULT, quiz.getResult() );

        // inserting the new entry into the database will return the incremented id
        long id = db.insert( CountryQuizDBHelper.TABLE_QUIZ, null, values );

        // store quiz id for retrieving in a persistent fashion
        quiz.setId( id );

        Log.d( DEBUG_TAG, "Stored new quiz with id: " + String.valueOf( quiz.getId() ) );

        return quiz;
    }

    // storing new Country into database
    public Country storeCountry( Country country ) {

        // all values for the database will need to be prepared to insert.
        ContentValues values = new ContentValues();
        values.put( CountryQuizDBHelper.COUNTRY_COLUMN_NAME, country.getCountryName());
        values.put( CountryQuizDBHelper.COUNTRY_COLUMN_CONTINENT, country.getContinent() );

        // inserting the new entry into the database will return the incremented id
        long id = db.insert( CountryQuizDBHelper.TABLE_COUNTRY, null, values );

        // store quiz id for retrieving in a persistent fashion
        country.setId( id );

        Log.d( DEBUG_TAG, "Stored new quiz with id: " + String.valueOf( country.getId() ) );

        return country;
    }


}