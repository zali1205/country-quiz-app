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
 */
public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder> {

    private List<Quiz> quizList;

    public QuizRecyclerAdapter( List<Quiz> quizList ) {
        this.quizList = quizList;
    }

    // basic holder class for storing one card's worth of data
    class QuizHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView date;
        TextView result;

        public QuizHolder(View itemView ) {
            super(itemView);

            id = (TextView) itemView.findViewById( R.id.id );
            date = (TextView) itemView.findViewById( R.id.date );
            result = (TextView) itemView.findViewById( R.id.result );
        }
    }

    @Override
    public QuizHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        // borrows inflation techniques from jobleadsqlite program
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz_results, parent, false );
        return new QuizHolder( view );
    }

    // supplies the necessary view edits for the relevant cards
    @Override
    public void onBindViewHolder( QuizHolder holder, int position ) {
        Quiz quiz = quizList.get( position );

        holder.id.setText( "Set :\t\t\t" + quiz.getId() );
        holder.date.setText( "Date :\t\t\t" + quiz.getQuizDate() );
        holder.result.setText( "# correct : \t"+ quiz.getResult() );
    }

    @Override
    public int getItemCount() {
        if( quizList != null )
            return quizList.size();
        else
            return 0;
    }
}

