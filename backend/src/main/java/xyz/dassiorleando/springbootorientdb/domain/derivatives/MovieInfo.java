package xyz.dassiorleando.springbootorientdb.domain.derivatives;

import xyz.dassiorleando.springbootorientdb.domain.Title;
import xyz.dassiorleando.springbootorientdb.domain.Worker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieInfo {

    Title title;
    List<RoleInfo> roles;
    List<WatchInfo> watched;

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

    @Override
    public String toString() {
        return "MovieInfo{" +
                "title=" + title +
                ", roles=" + roles +
                ", watched=" + watched +
                ", series=" + series +
                '}';
    }
}
