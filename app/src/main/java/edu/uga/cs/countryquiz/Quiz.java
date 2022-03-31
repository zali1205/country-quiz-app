package edu.uga.cs.countryquiz;

/**
 * POJO for a single instance of a quiz id, date & result. id will be -1 if not ready
 *
 * Author - Drew Jenkins
 */
public class Quiz {

    private long   id;
    private String quizDate;
    private int result;

    public Quiz()
    {
        this.id = -1;
        this.quizDate = null;
        this.result = -1;
    }

    public Quiz( String quizDate, int result ) {
        this.id = -1;  // for setting later
        this.quizDate = quizDate;
        this.result = result;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getQuizDate()
    {
        return quizDate;
    }

    public void setQuizDate(String quizDate)
    {
        this.quizDate = quizDate;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }
}
