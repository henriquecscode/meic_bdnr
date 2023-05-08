package app.domain.derivatives;

import app.domain.Genre;

public class GenreAwards {
    Genre genre;
    Integer awards;

    public GenreAwards(Genre genre,Integer awards) {
        this.genre = genre;
        this.awards = awards;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Integer getAwards() {
        return awards;
    }

    public void setAwards(Integer awards) {
        this.awards = awards;
    }

    @Override
    public String toString() {
        return "GenreAwards{" +
                "genre=" + genre +
                ", awards=" + awards +
                '}';
    }
}
