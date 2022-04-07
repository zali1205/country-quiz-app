package edu.uga.cs.countryquiz;

/**
 * POJO for a single instance of a quiz id, date, array of relevant questions, & result. id & result will be -1 if not ready
 *
 * Author - Drew Jenkins
 */
public class Quiz {

    private long   id; // primary key
    private String quizDate;
    private int result;
    private Question[] countryQs; // store the question array

    public Quiz() // default constructor
    {
        this.id = -1;
        this.quizDate = null;
        this.result = -1;
        this.countryQs = new Question[6];
    }

    public Quiz(String quizDate, int result, Question[] countryQs) { // store country q's with result & date
        this.id = -1;  // for setting later
        this.quizDate = quizDate;
        this.result = result;
        this.countryQs = countryQs;
    }

    public Quiz(String quizDate, int result) { // store result and date for use with database
        this.id = -1;  // for setting later
        this.quizDate = quizDate;
        this.result = result;
        this.countryQs = new Question[6];
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(String quizDate) {
        this.quizDate = quizDate;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void calculateResult() { // method to determine the result of the 6 questions inside the quiz
        int correct = 0;
        for (int i = 0; i < countryQs.length; i++) {
            if (countryQs[i].getCorrectlyAnswered() ==  true) {
                correct++;
            }
        }
        setResult(correct); // update result
    }

    public Question[] getCountryQs() {
        return countryQs;
    }

    public void setCountryQs(Question[] countryQs) {
        this.countryQs = countryQs;
    }

}
