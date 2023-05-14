package app.domain.derivatives;

import app.domain.Series;
import app.domain.User;

import java.util.List;

public class WatchedSeriesInfo {
    Series series;
    List<User> users;

    public WatchedSeriesInfo() {
    }

    public WatchedSeriesInfo(Series series, List<User> users) {
        this.series = series;
        this.users = users;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "WatchedSeriesInfo{" +
                "series=" + series +
                ", users=" + users +
                '}';
    }
}
