package app.domain.derivatives;

import app.domain.Title;
import app.domain.User;

import java.util.List;

public class WatchInfoByUser {
    User user;
    List<Title> titles;

    public WatchInfoByUser(User user, List<Title> titles) {
        this.user = user;
        this.titles = titles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    @Override
    public String toString() {
        return "WatchInfoByUser{" +
                "user=" + user +
                ", title=" + titles +
                '}';
    }
}
