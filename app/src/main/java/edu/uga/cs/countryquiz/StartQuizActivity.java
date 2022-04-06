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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
    This is the quiz activity that is used when the user would like to start a new quiz. This activity uses a ViewPager2 in order to go through the
    quiz questions.
 */
public class StartQuizActivity extends AppCompatActivity {
    final String TAG = "";

    private CountryQuizData DBAccess = null;
    private SectionsPagerAdapter mainSectionsPagerAdapter;
    private ViewPager2 mainViewPager;
    private ActionBar mainActionBar;
    private List<Country> unshuffledCountry;
    private List<Country> shuffledCountry;
    private String[] continentsArray = {"Asia", "Africa" , "Europe", "North America", "South America" , "Oceania"};
    private List<String> continentsList = Arrays.asList(continentsArray);
    private Question[] allQuestions;
    private Quiz currentQuiz;
    private String pattern = "yyyy-MM-dd";

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

        // Creating the Question objects for the Quiz object. Since the country list has already been shuffled, the questions objects will take the first 6 randomized countries.
        Question question1 = new Question(shuffledCountry.get(0).getCountryName(),shuffledCountry.get(0).getContinent());
        Question question2 = new Question(shuffledCountry.get(1).getCountryName(),shuffledCountry.get(1).getContinent());
        Question question3 = new Question(shuffledCountry.get(2).getCountryName(),shuffledCountry.get(2).getContinent());
        Question question4 = new Question(shuffledCountry.get(3).getCountryName(),shuffledCountry.get(3).getContinent());
        Question question5 = new Question(shuffledCountry.get(4).getCountryName(),shuffledCountry.get(4).getContinent());
        Question question6 = new Question(shuffledCountry.get(5).getCountryName(),shuffledCountry.get(5).getContinent());
        // Creating an array of questions to pass to the Quiz object.
        allQuestions = new Question[]{question1, question2, question3, question4, question5, question6};
        // Creating a new Quiz object and passing the array of Questions to the Quiz object.
        currentQuiz = new Quiz();
        currentQuiz.setCountryQs(allQuestions);

    }

    /*
        This is a method that loads a section of the quiz. The first 6 sections are a question from the quiz and the last section is the results.
        It has access to the TextView, RadioGroup, and the Radio Buttons in order to set them to the appropriate questions and potential answers.
     */
    public void loadSection(int sectionNumber, TextView textView, RadioGroup radioGroup, RadioButton[] radioButtons, Button startNewQuiz, Button seeResults) {

        // Setting all of the appropriate TextView, Buttons, and RadioGroupListeners for each of the questions.
        if (sectionNumber == 1) {
            setTextAndButtons(textView, radioButtons, 0);
            setRadioGroupListeners(radioGroup, 0);
        }
        if (sectionNumber == 2) {
            setTextAndButtons(textView, radioButtons, 1);
            setRadioGroupListeners(radioGroup, 1);
        }
        if (sectionNumber == 3) {
            setTextAndButtons(textView, radioButtons, 2);
            setRadioGroupListeners(radioGroup, 2);
        }
        if (sectionNumber == 4) {
            setTextAndButtons(textView, radioButtons, 3);
            setRadioGroupListeners(radioGroup, 3);
        }
        if (sectionNumber == 5) {
            setTextAndButtons(textView, radioButtons, 4);
            setRadioGroupListeners(radioGroup, 4);
        }
        if (sectionNumber == 6) {
            setTextAndButtons(textView, radioButtons, 5);
            setRadioGroupListeners(radioGroup, 5);
        }
        // Once you have reached the results page, it will calculate the results of the quiz and tell the user how many they got correct.
        // It will also hide the RadioGroup and RadioButtons and make the buttons for starting a new quiz and seeing past results visible to the user.
        if (sectionNumber == 7) {
            currentQuiz.calculateResult();
            textView.setText("You answered " + currentQuiz.getResult() + " questions correctly!");
            radioGroup.setVisibility(View.GONE); // Hiding RadioGroup and its buttons.
            startNewQuiz.setVisibility(View.VISIBLE); // Making Start New Quiz Button visible and setting its onClick.
            startNewQuiz.setOnClickListener(new StartNewQuizButtonListener());
            seeResults.setVisibility(View.VISIBLE); // Making See Results Button visible and setting its onClick.
            seeResults.setOnClickListener(new SeeResultsButtonListener());
        }
    }

    /*
        Helper method that sets the TextView that holds the question for the user and the RadioButtons that hold the answers for the users to the appropriate question.
     */
    private void setTextAndButtons(TextView textView, RadioButton[] radioButtons, int questionNumber) {
        textView.setText(allQuestions[questionNumber].getDescription());
        radioButtons[0].setText(allQuestions[questionNumber].getTotalAnswers().get(0));
        radioButtons[1].setText(allQuestions[questionNumber].getTotalAnswers().get(1));
        radioButtons[2].setText(allQuestions[questionNumber].getTotalAnswers().get(2));
    }

    /*
        Helper method that sets the RadioGroupListeners for all of the questions. Depending on the answer selected, it will set the question's boolean value (correctlyAnswered) to true
        or false.
     */
    private void setRadioGroupListeners(RadioGroup radioGroup, int questionNumber) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton rb = findViewById(checkedId);
                    if (rb.getText().equals(allQuestions[questionNumber].getContinent())) {
                        allQuestions[questionNumber].setCorrectlyAnswered(true);
                        Toast.makeText(getApplicationContext(), "NICE", Toast.LENGTH_SHORT).show();
                    } else {
                        allQuestions[questionNumber].setCorrectlyAnswered(false);
                        Toast.makeText(getApplicationContext(), "WRONG", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
            // Once the user has reached the last section (the results page), it does not allow backwards scrolling. This is so that the user cannot go back and change answers once submitted.
            if (position == 6) {
                mainViewPager.setUserInputEnabled(false);
            }
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
            String dateString = new SimpleDateFormat(pattern).format( new Date());
            Question[] checkRes =currentQuiz.getCountryQs();
            int resultyio = 0;
            for ( int i=0; i<6; i++ )
            {
                if ( checkRes[i].getCorrectlyAnswered() )
                    resultyio++;
            } // for

            currentQuiz.setResult(resultyio);
            currentQuiz.setQuizDate( dateString );

            new QuizDBWriter().execute( currentQuiz ); // store the lines into new country objects

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

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of the countries, asynchronously.
    public class QuizDBWriter extends AsyncTask<Quiz, Quiz> {

        // method for background storage of these
        @Override
        protected Quiz doInBackground( Quiz... quizzes ) {
            DBAccess.storeQuiz( quizzes[0] );
            return quizzes[0];
        }

        // method for demonstrating the end of storing the country into our database
        @Override
        protected void onPostExecute( Quiz quiz ) {
            // Show a quick confirmation message
            Toast.makeText( getApplicationContext(), "Quiz created for " + quiz.getId() + ", " + quiz.getQuizDate() + ", " + quiz.getResult(),
                    Toast.LENGTH_SHORT).show();

            Log.d( TAG, "Quiz saved: " + quiz );
        }
    }

    /*
        onResume method
     */
    @Override
    protected void onResume() {
        // open the database in onResume
        if( DBAccess != null && !DBAccess.isDBOpen() )
            DBAccess.open();
        super.onResume();
    }

    /*
        onPause method
     */
    @Override
    protected void onPause() {
        // close the database in onPause
        if( DBAccess != null )
            DBAccess.close();
        super.onPause();
    }



}