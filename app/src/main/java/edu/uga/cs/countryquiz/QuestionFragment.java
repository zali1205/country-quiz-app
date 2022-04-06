package edu.uga.cs.countryquiz;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/*
    This is the fragment that is used to create the questions for the quiz activity.
 */
public class QuestionFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private int questionNumber;
    private TextView mainTextView;
    private RadioGroup mainRadioGroup;
    private RadioButton radioButton;
    private RadioButton[] radioButtonGroup = new RadioButton[3];
    private Button startNewQuiz;
    private Button seeResults;
    /*
        newInstance method
     */
    public static QuestionFragment newInstance(int questionNumber) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, questionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /*
        Required default constructor.
     */
    public QuestionFragment() {
    }

    /*
        onCreate method
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        } else {
            questionNumber = -1;
        }
    }

    /*
        onCreateView method that sets the view for the fragment and gets the id's of the main components such as the TextView used for the
        question's description and the mainRadioGroup that holds the selections for the user.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);
        mainTextView = rootView.findViewById((R.id.textView));
        mainRadioGroup = rootView.findViewById(R.id.radioGroup);
        radioButton = rootView.findViewById(R.id.radioButton1);
        radioButtonGroup[0] = radioButton;
        radioButton = rootView.findViewById(R.id.radioButton2);
        radioButtonGroup[1] = radioButton;
        radioButton = rootView.findViewById(R.id.radioButton3);
        radioButtonGroup[2] = radioButton;
        startNewQuiz = rootView.findViewById(R.id.button3);
        seeResults = rootView.findViewById(R.id.button4);

        return rootView;
    }

    /*
        onViewCreated method that will load the appropriate section for the user used the StartQuizActivity's method called loadSection.
     */
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(this.getView(), savedInstanceState);
        if (StartQuizActivity.class.isInstance(getActivity())) {
            ((StartQuizActivity) getActivity()).loadSection(questionNumber, mainTextView, mainRadioGroup, radioButtonGroup, startNewQuiz, seeResults);
        }
    }

}