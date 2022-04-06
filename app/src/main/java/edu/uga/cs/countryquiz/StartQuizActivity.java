package edu.uga.cs.countryquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
    This is the quiz activity that is used when the user would like to start a new quiz. This activity uses a ViewPager2 in order to go through the
    quiz questions.
 */
public class StartQuizActivity extends AppCompatActivity {
    final String TAG = "";

    private CountryQuizData DBAccess = null;

    SectionsPagerAdapter mainSectionsPagerAdapter;
    ViewPager2 mainViewPager;
    ActionBar mainActionBar;
    List<Country> unshuffledCountry;
    List<Country> shuffledCountry;
    String[] continentsArray = {"Asia", "Africa" , "Europe", "North America", "South America" , "Oceania"};
    List<String> continentsList = Arrays.asList(continentsArray);

    /*
        onCreate method that is called when the activity is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        //
        DBAccess = new CountryQuizData(this);
        // open for accessing the database
        DBAccess.open();
        CountryDBReader waitUp = new CountryDBReader(); // retrieves the countries to store in list without interruption
        unshuffledCountry = waitUp.doInBackground();


        mainActionBar = getSupportActionBar();
        mainSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.getLifecycle());
        mainActionBar.setTitle(mainSectionsPagerAdapter.getPageTitle(0));
        mainViewPager = findViewById(R.id.pager2);

        mainViewPager.setAdapter(mainSectionsPagerAdapter);

        mainViewPager.registerOnPageChangeCallback(new viewPagerOnPageChange());

        waitUp.onPostExecute( unshuffledCountry );
        Collections.shuffle(shuffledCountry); // shuffle the retrieved data for seeding in our questions

    }

    /*
        This is a method that loads a section of the quiz. The first 6 sections are a question from the quiz and the last section is the results.
        It has access to the TextView, RadioGroup, and the Radio Buttons in order to set them to the appropriate questions and potential answers.
     */
    public void loadSection(int sectionNumber, TextView textView, RadioGroup radioGroup, RadioButton[] radioButtons, Button startNewQuiz, Button seeResults) {
        if (sectionNumber == 7) {
            textView.setText("THIS IS THE RESULTS PAGE");
            radioGroup.setVisibility(View.GONE);
            startNewQuiz.setVisibility(View.VISIBLE);
            startNewQuiz.setOnClickListener(new StartNewQuizButtonListener());
            seeResults.setVisibility(View.VISIBLE);
            seeResults.setOnClickListener(new SeeResultsButtonListener());
        }

        if (sectionNumber == 1) {
            textView.setText( shuffledCountry.get(0).getCountryName());
        }
        if (sectionNumber == 2) {

        }
        if (sectionNumber == 3) {

        }
        if (sectionNumber == 4) {

        }
        if (sectionNumber == 5) {

        }
        if (sectionNumber == 6) {

        }
    }

    /*
        This is the private class that is used to implement the ViewPager2.OnPageChangeCallback.
     */
    private class viewPagerOnPageChange extends ViewPager2.OnPageChangeCallback {
        /*
            onPageSelected method that changes the action bar's title depending on which page it is currently on.
         */
        @Override
        public void onPageSelected(int position) {
            mainActionBar.setTitle(mainSectionsPagerAdapter.getPageTitle(position));
        }

    }

    /*
        This is the OnClickListener for the startNewQuizButton. When the user clicks this, they will be able to start a new quiz.
     */
    private class StartNewQuizButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), StartQuizActivity.class);
            finish();
            startActivity(intent);
        }
    }

    /*
        This is the OnClickListener for the SeeResultsButton. When the user clicks this, they will be taken to the results page.
     */
    private class SeeResultsButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ResultsActivity.class);
            finish();
            startActivity(intent);
        }
    }


    // async reading of country table
    private class CountryDBReader extends AsyncTask<Void, List<Country>> {
        // return current list of countries read from sqlite database async
        @Override
        protected List<Country> doInBackground( Void... params ) {
            List<Country> countriesList = DBAccess.retrieveAllCountries();

            Log.d( TAG, "Countries retrieved: " + countriesList.size() );

            return countriesList;
        }

        // method for adding the results into the country list upon completion
        @Override
        protected void onPostExecute( List<Country> cList ) {
            Log.d( TAG, "cList.size(): " + cList.size() );
            shuffledCountry = new ArrayList<Country>();
            shuffledCountry.addAll(cList);

        }
    }

    @Override
    protected void onResume() {
        // open the database in onResume
        if( DBAccess != null && !DBAccess.isDBOpen() )
            DBAccess.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // close the database in onPause
        if( DBAccess != null )
            DBAccess.close();
        super.onPause();
    }



}