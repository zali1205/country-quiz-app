package edu.uga.cs.countryquiz;

/**
 * POJO for a single instance of a country id, name, continent. id will be -1 if not ready
 *
 * Author - Drew Jenkins
 */
public class Question {

    private String countryName;
    private String continent;
    private String description;

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
}
