package app.domain.derivatives;

import app.domain.Country;
import app.domain.Title;
import app.domain.User;

import java.util.List;

public class UserInfo {

    User user;
    List<Country> countries;
    List<WatchedFilmInfo> films;

    List<Title> toWatch;

    List<User> friends;

    public UserInfo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<WatchedFilmInfo> getFilms() {
        return films;
    }

    public void setFilms(List<WatchedFilmInfo> films) {
        this.films = films;
    }

    public List<Title> getToWatch() {
        return toWatch;
    }

    public void setToWatch(List<Title> toWatch) {
        this.toWatch = toWatch;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user=" + user +
                ", countries=" + countries +
                ", films=" + films +
                ", toWatch=" + toWatch +
                ", friends=" + friends +
                '}';
    }
}
