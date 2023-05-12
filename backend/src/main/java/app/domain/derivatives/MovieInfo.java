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

    @Override
    public String toString() {
        return "MovieInfo{" +
                "title=" + title +
                ", roles=" + roles +
                ", watched=" + watched +
                ", genres=" + genres +
                ", series=" + series +
                '}';
    }
}
