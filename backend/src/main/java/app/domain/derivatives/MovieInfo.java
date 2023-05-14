package app.domain.derivatives;

import app.domain.Genre;
import app.domain.Title;

import java.util.List;

public class MovieInfo {

    Title title;
    List<RoleInfo> roles;
    List<WatchInfo> watched;

    List<Genre> genres;

    SeriesInfo series;
    Double avgVote;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public List<RoleInfo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleInfo> roles) {
        this.roles = roles;
    }

    public List<WatchInfo> getWatched() {
        return watched;
    }

    public void setWatched(List<WatchInfo> watched) {
        this.watched = watched;
    }

    public SeriesInfo getSeries() {
        return series;
    }

    public void setSeries(SeriesInfo series) {
        this.series = series;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Double getAvgVote() {
        return avgVote;
    }

    public void setAvgVote(Double avgVote) {
        this.avgVote = avgVote;
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "title=" + title +
                ", roles=" + roles +
                ", watched=" + watched +
                ", genres=" + genres +
                ", series=" + series +
                ", avgVote=" + avgVote +
                '}';
    }
}
