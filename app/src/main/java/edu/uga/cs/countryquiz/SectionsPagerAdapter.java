package edu.uga.cs.countryquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/*
    This class is used to implement the FragmentStateAdapter that is required for the ViewPager2 that is used in the StartQuizActivity class.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {
    private final int size = 7; // Total number of sections the Adapter will have. 6 questions and a results page.

    /*
        Required default constructor.
     */
    public SectionsPagerAdapter(FragmentManager fm, Lifecycle lc) {
        super(fm, lc);
    }

    /*
        createFragment call that creates the QuestionFragment through the newInstance method and inserts the position as well.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return QuestionFragment.newInstance(position + 1);
    }

    /*
        Returns the size of the adapter.
     */
    @Override
    public int getItemCount() {
        return size;
    }

    /*
        Returns the title page of the quiz activity. When swiping, the top action bar will reflect what question it is on or, if it is at the last section,
        it will state that it is at the "Quiz Results" section.
     */
    public CharSequence getPageTitle(int position) {
        int sectionNumber = position + 1;
        if (sectionNumber == 7) {
            return String.valueOf("Quiz Results");
        }
        return String.valueOf("Question " + sectionNumber);
    }
}
