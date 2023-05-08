package app.domain.derivatives;

import app.domain.Country;

public class CountryAwards {
    Country country;
    Integer awards;

    public CountryAwards(Country country, Integer awards) {
        this.country = country;
        this.awards = awards;
    }
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getAwards() {
        return awards;
    }

    public void setAwards(Integer awards) {
        this.awards = awards;
    }

    @Override
    public String toString() {
        return "CountryAwards{" +
                "country=" + country +
                ", awards=" + awards +
                '}';
    }
}
