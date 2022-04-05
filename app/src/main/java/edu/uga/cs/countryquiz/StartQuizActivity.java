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

/*
    This is the quiz activity that is used when the user would like to start a new quiz. This activity uses a ViewPager2 in order to go through the
    quiz questions.
 */
public class StartQuizActivity extends AppCompatActivity {
    SectionsPagerAdapter mainSectionsPagerAdapter;
    ViewPager2 mainViewPager;
    ActionBar mainActionBar;

    /*
        onCreate method that is called when the activity is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

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
            radioButtons[0].setText("THIS IS A TEST FOR QUESTION 1. PLEASE CLICK ME");
            int selectedId = radioGroup.getCheckedRadioButtonId();
        }
        if (sectionNumber == 2) {
            radioButtons[1].setText("THIS IS A TEST FOR QUESTION 2");
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