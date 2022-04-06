package edu.uga.cs.countryquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    SectionsPagerAdapter mainSectionsPagerAdapter;
    ViewPager2 mainViewPager;
    ActionBar mainActionBar;
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
        CountryQuizData DBAccess = new CountryQuizData(this);

        //shuffledCountry = DBAccess.retrieveAllCountries();

        //Collections.shuffle(shuffledCountry);


        mainActionBar = getSupportActionBar();
        mainSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.getLifecycle());
        mainActionBar.setTitle(mainSectionsPagerAdapter.getPageTitle(0));
        mainViewPager = findViewById(R.id.pager2);

        mainViewPager.setAdapter(mainSectionsPagerAdapter);

        mainViewPager.registerOnPageChangeCallback(new viewPagerOnPageChange());

    }

    /*
        This is a method that loads a section of the quiz. The first 6 sections are a question from the quiz and the last section is the results.
        It has access to the TextView, RadioGroup, and the Radio Buttons in order to set them to the appropriate questions and potential answers.
     */
    public void loadSection(int sectionNumber,TextView textView, RadioGroup radioGroup, RadioButton[] radioButtons) {
        if (sectionNumber == 7) {
            textView.setText("THIS IS THE RESULTS PAGE");
            radioGroup.setVisibility(View.GONE);
        }

        if (sectionNumber == 1) {
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



}