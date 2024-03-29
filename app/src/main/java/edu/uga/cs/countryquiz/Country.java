package edu.uga.cs.countryquiz;

/**
 * POJO for a single instance of a country id, name, continent. id will be -1 if not ready
 *
 * Author - Drew Jenkins
 */
public class Country {

    private long   id; // primary key for reference
    private String countryName;
    private String continent;

    public Country() // default
    {
        this.id = -1;
        this.countryName = null;
        this.continent = null;
    }

    public Country( String countryName, String continent ) {
        this.id = -1;  // for setting later
        this.countryName = countryName;
        this.continent = continent;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
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
}
