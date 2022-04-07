package edu.uga.cs.countryquiz;

import java.util.ArrayList;
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
    private List<String> totalAnswers = new ArrayList<String>();
    private boolean correctlyAnswered = false;

    public Question()
    {
        this.countryName = null;
        this.continent = null;
        this.description = null;
    }

    public Question(String countryName, String continent) {
        this.countryName = countryName;
        this.continent = continent;
        this.description = "What continent is " + this.countryName + " in?"; // basic generation for Question

        Collections.shuffle(continentsList);
        if (continentsList.get(0).equals(continent)) {
            totalAnswers.add(continentsList.get(2)); // third element in shuffled list
        } else {
            totalAnswers.add(continentsList.get(0)); // first
        }
        if (continentsList.get(1).equals(continent)) {
            totalAnswers.add(continentsList.get(2)); // third
        } else {
            totalAnswers.add(continentsList.get(1)); // second
        }
        totalAnswers.add(continent);
        Collections.shuffle(totalAnswers); // shuffle generated answers for randomization of order
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCorrectlyAnswered(Boolean bool) {
        this.correctlyAnswered = bool;
    }

    public boolean getCorrectlyAnswered() {
        return this.correctlyAnswered;
    }

    public List<String> getTotalAnswers() {
        return totalAnswers;
    }
}
