package app.domain.derivatives;

import app.domain.User;
import app.domain.Watched;

public class WatchInfo {

    User user;
    Watched watched;

    public WatchInfo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Watched getWatched() {
        return watched;
    }

    public void setWatched(Watched watched) {
        this.watched = watched;
    }

    @Override
    public String toString() {
        return "WatchInfo{" +
                "user=" + user +
                ", watched=" + watched +
                '}';
    }
}
