package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/*
    This is the main activity where the application starts. The Main Activity class presents to the user what the application is about
    and the user can begin a new quiz by pressing the "Start Quiz" button or view past results by pressing the "Results" button.
 */
public class MainActivity extends AppCompatActivity {

    final String PREFERENCE = "ForFirstBoot";

    private String TAG = "csving";

    Button results;
    Button startQuiz;

    private CountryQuizData countryQuizData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting the startQuiz button
        startQuiz = findViewById(R.id.button);

        // Getting the results button
        results = findViewById(R.id.button2);

        // Setting the onClickListeners for startQuiz button
        startQuiz.setOnClickListener(new StartQuizButtonListener());

        // Setting the onClickListeners for the result button
        results.setOnClickListener(new ResultsButtonListener());

        SharedPreferences settings = getSharedPreferences(PREFERENCE, 0); // find the shared preference from settings
        if( settings.getBoolean("first_open",true) ) { // if true initialize DB

            countryQuizData = new CountryQuizData(this); // create new instance

            countryQuizData.open(); // open data for writing into
            try {
                // Open the CSV data file in the assets folder
                InputStream in_s = getAssets().open("country_continent.csv");

                // read the CSV data
                CSVReader reader = new CSVReader(new InputStreamReader(in_s));
                String[] nextRow;
                while ((nextRow = reader.readNext()) != null) {
                    new CountryDBWriter().execute(new Country(nextRow[0], nextRow[1])); // store the lines into new country objects
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            countryQuizData.close(); // close out connection
            settings.edit().putBoolean("first_open", false ).commit(); // show that the database has been set
        } // if
    }

    /*
        StartQuizButtonListener class that is used to implement the Start Quiz's push button. When the push button is pressed, it will
        create a new intent which will start the Start Quiz Activity class.
     */
    private class StartQuizButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), StartQuizActivity.class);
            startActivity(intent);
        }
    }
    /*
        ResultsButtonListener class that is used to implement the Start Quiz's push button. When the push button is pressed, it will
        create a new intent which will start the Results Activity class.
     */
    private class ResultsButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ResultsActivity.class);
            startActivity(intent);
        }
    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of the countries, asynchronously.
    public class CountryDBWriter extends AsyncTask<Country, Country> {

        // method for background storage of these
        @Override
        protected Country doInBackground( Country... countries ) {
            countryQuizData.storeCountry( countries[0] );
            return countries[0];
        }

        // method for demonstrating the end of storing the country into our database
        @Override
        protected void onPostExecute( Country country ) {
            // Show a quick confirmation message
            // Toast.makeText( getApplicationContext(), "Country created for " + country.getId() + ", " + country.getCountryName() + ", " + country.getContinent(),
            // Toast.LENGTH_SHORT).show();

            Log.d( TAG, "Country saved: " + country );
        }
    }


}
