package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
    This is the main activity where the application starts. The Main Activity class presents to the user what the application is about
    and the user can begin a new quiz by pressing the "Start Quiz" button or view past results by pressing the "Results" button.
 */
public class MainActivity extends AppCompatActivity {

    Button results;
    Button startQuiz;

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


}
