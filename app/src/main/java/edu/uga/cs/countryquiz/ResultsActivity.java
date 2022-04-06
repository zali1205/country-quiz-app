package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private final String TAG = "quiz reading";

    private RecyclerView recyclerView;
    private QuizRecyclerAdapter recyclerAdapter;

    private CountryQuizData cqData = null;
    private List<Quiz> quizList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recyclerView = findViewById( R.id.recyclerView );

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        quizList = new ArrayList<Quiz>();
        recyclerAdapter = new QuizRecyclerAdapter( quizList );
        recyclerView.setAdapter( recyclerAdapter );

        cqData = new CountryQuizData( this );
        cqData.open();

        // Execute the retrieval of the job leads in an asynchronous way,
        // without blocking the main UI thread.
        new QuizDBReader().execute();
    }

    // async reading of country table
    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {
        // return current list of countries read from sqlite database async
        @Override
        protected List<Quiz> doInBackground( Void... params ) {
            List<Quiz> quizzesList = cqData.retrieveAllQuizzes();

            Log.d( TAG, "Quizzes retrieved: " + quizzesList.size() );

            return quizzesList;
        }

        // method for adding the results into the country list upon completion
        @Override
        protected void onPostExecute( List<Quiz> qList ) {
            Log.d( TAG, " qList.size(): " + qList.size() );
            quizList = new ArrayList<Quiz>();
            quizList.addAll(qList);
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        // open the database in onResume
        if( cqData != null && !cqData.isDBOpen() )
            cqData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // close the database in onPause
        if( cqData != null )
            cqData.close();
        super.onPause();
    }
}