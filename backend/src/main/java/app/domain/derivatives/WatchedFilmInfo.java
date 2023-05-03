package app.domain.derivatives;

import app.domain.Title;
import app.domain.Watched;

public class WatchedFilmInfo {

    Watched watched;
    Title title;

    public WatchedFilmInfo() {
    }

    public Watched getWatched() {
        return watched;
    }

    public void setWatched(Watched watched) {
        this.watched = watched;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "WatchedFilmInfo{" +
                "watched=" + watched +
                ", title=" + title +
                '}';
    }
}
