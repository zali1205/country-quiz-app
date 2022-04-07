package edu.uga.cs.countryquiz;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * basic adapter class to show all the previous quiz results
 *
 * Author - Drew Jenkins
 */
public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder> {

    private List<Quiz> quizList; // list for working with recycler

    public QuizRecyclerAdapter( List<Quiz> quizList ) {
        this.quizList = quizList;
    } // constructor takes a list to start

    // basic holder class for storing one card's worth of data
    class QuizHolder extends RecyclerView.ViewHolder {
        // ui controls
        TextView id;
        TextView date;
        TextView result;

        public QuizHolder(View itemView ) { // constructor with item view to attach objects
            super(itemView);

            id = itemView.findViewById( R.id.id );
            date = itemView.findViewById( R.id.date );
            result = itemView.findViewById( R.id.result );
        }
    }

    @Override
    public QuizHolder onCreateViewHolder( ViewGroup parent, int viewType ) { // for proper inflation of the relevant view
        // borrows inflation techniques from jobleadsqlite program
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz_results, parent, false );
        return new QuizHolder( view );
    }

    // supplies the necessary view edits for the relevant cards
    @Override
    public void onBindViewHolder( QuizHolder holder, int position ) {
        Quiz quiz = quizList.get( position ); // find correct position
        // set ui's to proper values from the imported list
        holder.id.setText( "Set # :\t\t\t" + quiz.getId() );
        holder.date.setText( "Date :\t\t\t" + quiz.getQuizDate() );
        holder.result.setText( "# correct : \t"+ quiz.getResult() );
    }

    @Override
    public int getItemCount() { // needed for the recycler to populate
        if( quizList != null )
            return quizList.size();
        else
            return 0;
    }
}

