package edu.uga.cs.countryquiz;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * POJO for a single instance of a country id, name, continent. id will be -1 if not ready
 *
 * Author - Drew Jenkins
 */
public class Question {

    private String[] continentsArray = {"Asia", "Africa" , "Europe", "North America", "South America" , "Oceania"};
    private List<String> continentsList = Arrays.asList(continentsArray);
    private String countryName;
    private String continent;
    private String description;
    private String[] wrongAnswers = new String[2];
    private boolean correctlyAnswered = false;

    public Question()
    {
        this.countryName = null;
        this.continent = null;
        this.description = null;
    }

    public Question( String countryName, String continent ) {
        this.countryName = countryName;
        this.continent = continent;
        this.description = "What continent is " + this.countryName + " in?";

        Collections.shuffle(continentsList);
        if (continentsList.get(0).equals(continent)) {
            wrongAnswers[0] = continentsList.get(2);
        } else {
            wrongAnswers[0] = continentsList.get(0);
        }
        if (continentsList.get(1).equals(continent)) {
            wrongAnswers[1] = continentsList.get(2);
        } else {
            wrongAnswers[1] = continentsList.get(1);
        }
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getContinent()
    {
        return continent;
    }

    public void setContinent(String continent)
    {
        this.continent = continent;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setCorrectlyAnswered(Boolean bool) {
        this.correctlyAnswered = bool;
    }

    public boolean getCorrectlyAnswered() {
        return this.correctlyAnswered;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }
}
