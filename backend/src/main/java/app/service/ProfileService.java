package app.service;

import app.domain.Country;
import app.domain.Title;
import app.domain.User;
import app.domain.Watched;
import app.domain.derivatives.UserInfo;
import app.domain.derivatives.WatchedFilmInfo;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService extends GeneralService {

    public ProfileService(OrientGraphFactory factory) {
        super(factory);
    }

    public UserInfo getInfoByUsername(String username) {
        setGraph();

        UserInfo userInfo = new UserInfo();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);

        if (!foundUsers.iterator().hasNext()) {
            shutdownGraph();
            return userInfo;
        }

        Vertex userVertex = foundUsers.iterator().next();
        User user = User.fromVertex(userVertex);
        userInfo.setUser(user);

        Iterable<Vertex> foundCountries = userVertex.getVertices(Direction.OUT, "Nationality");

        List<Country> countries = new ArrayList<>();
        for (Vertex countryVertex : foundCountries) {
            Country country = Country.fromVertex(countryVertex);
            countries.add(country);
        }
        userInfo.setCountries(countries);

        Iterable<Edge> foundWatched = userVertex.getEdges(Direction.OUT, "Watched");
        List<WatchedFilmInfo> watchedFilms = new ArrayList<>();
        for (Edge watchedEdge : foundWatched) {
            Watched watched = Watched.fromEdge(watchedEdge);
            Vertex titleVertex = watchedEdge.getVertex(Direction.IN);
            Title title = Title.fromVertex(titleVertex);
            WatchedFilmInfo watchedFilmInfo = new WatchedFilmInfo();
            watchedFilmInfo.setWatched(watched);
            watchedFilmInfo.setTitle(title);
            watchedFilms.add(watchedFilmInfo);
        }
        userInfo.setFilms(watchedFilms);

        Iterable<Vertex> foundToWatch = userVertex.getVertices(Direction.OUT, "ToWatch");
        List<Title> toWatchFilms = new ArrayList<>();
        for (Vertex toWatchTitle : foundToWatch) {
            Title title = Title.fromVertex(toWatchTitle);
            toWatchFilms.add(title);
        }
        userInfo.setToWatch(toWatchFilms);

        Iterable<Vertex> foundFriends = userVertex.getVertices(Direction.OUT, "Follows");
        List<User> friends = new ArrayList<>();
        for (Vertex friendVertex : foundFriends) {
            User friend = User.fromVertex(friendVertex);
            friends.add(friend);
        }
        userInfo.setFriends(friends);

        shutdownGraph();
        return userInfo;
    }

    public boolean addFriend(String username, String friend) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        Iterable<Vertex> foundFriends = getGraph().getVertices("User.username", friend);

        if (!foundUsers.iterator().hasNext() || !foundFriends.iterator().hasNext()) {
            shutdownGraph();
            return false;
        }

        Vertex userVertex = foundUsers.iterator().next();
        Vertex friendVertex = foundFriends.iterator().next();

        if (userVertex.equals(friendVertex)) {
            shutdownGraph();
            return false;
        }

        Iterable<Edge> foundFollows = userVertex.getEdges(Direction.OUT, "Follows");

        for (Edge follows : foundFollows) {
            Vertex followed = follows.getVertex(Direction.IN);
            if (followed.equals(friendVertex)) {
                follows.remove();
                shutdownGraph();
                return false;
            }
        }
        userVertex.addEdge("Follows", friendVertex);

        commitGraph();
        shutdownGraph();
        return true;
    }

    public boolean removeFriend(String username, String friend) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        Iterable<Vertex> foundFriends = getGraph().getVertices("User.username", friend);

        if (!foundUsers.iterator().hasNext() || !foundFriends.iterator().hasNext()) {
            shutdownGraph();
            return false;
        }

        Vertex userVertex = foundUsers.iterator().next();
        Vertex friendVertex = foundFriends.iterator().next();

        if (userVertex.equals(friendVertex)) {
            shutdownGraph();
            return false;
        }
        Iterable<Edge> foundFollows = userVertex.getEdges(Direction.OUT, "Follows");
        for (Edge follows : foundFollows) {
            Vertex followed = follows.getVertex(Direction.IN);
            if (followed.equals(friendVertex)) {
                follows.remove();
                commitGraph();
                shutdownGraph();
                return true;
            }
        }
        shutdownGraph();
        return false;
    }

    private void updateWatchedEdge(Edge edge, Watched watched, Vertex titleVertex) {
        Watched oldWatched = Watched.fromEdge(edge);
        Title title = Title.fromVertex(titleVertex);
        Integer nVotes = title.getnVotes();
        Integer nComments = title.getnComments();
        if (watched.getVote() == null && oldWatched.getVote() != null) {
            titleVertex.setProperty("n_votes", nVotes - 1);
        } else if (watched.getVote() != null && oldWatched.getVote() == null) {
            titleVertex.setProperty("n_votes", nVotes + 1);
        }
        edge.setProperty("vote", watched.getVote());

        if (watched.getDate() != null) {
            edge.setProperty("date", watched.getDate());
        }
        if (watched.getComment() == null && oldWatched.getComment() != null) {
            titleVertex.setProperty("n_comments", nComments - 1);
        } else if (watched.getComment() != null && oldWatched.getComment() == null) {
            titleVertex.setProperty("n_comments", nComments + 1);
        }
        edge.setProperty("comment", watched.getComment());

        commitGraph();
    }

    public boolean addWatched(String username, String title, Watched watched) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        Iterable<Vertex> foundTitles = getGraph().getVertices("Title.tid", title);

        if (!foundUsers.iterator().hasNext() || !foundTitles.iterator().hasNext()) {
            shutdownGraph();
            return false;
        }

        Vertex userVertex = foundUsers.iterator().next();
        Vertex titleVertex = foundTitles.iterator().next();

        Iterable<Edge> foundWatched = userVertex.getEdges(Direction.OUT, "Watched");
        for (Edge watchedEdge : foundWatched) {
            Vertex watchedTitle = watchedEdge.getVertex(Direction.IN);
            if (watchedTitle.equals(titleVertex)) {

                //There is already one watched. We are going to change this
                updateWatchedEdge(watchedEdge, watched, titleVertex);
                shutdownGraph();
                return true;
            }
        }

        //There is no watched. We are going to create one
        Edge watchedEdge = userVertex.addEdge("Watched", titleVertex);
        updateWatchedEdge(watchedEdge, watched, titleVertex);
        shutdownGraph();
        return true;

    }

    public boolean removeWatched(String username, String title) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        Iterable<Vertex> foundTitles = getGraph().getVertices("Title.tid", title);

        if (!foundUsers.iterator().hasNext() || !foundTitles.iterator().hasNext()) {
            shutdownGraph();
            return false;
        }

        Vertex userVertex = foundUsers.iterator().next();
        Vertex titleVertex = foundTitles.iterator().next();

        Iterable<Edge> foundWatched = userVertex.getEdges(Direction.OUT, "Watched");
        for (Edge watchedEdge : foundWatched) {
            Vertex watchedTitle = watchedEdge.getVertex(Direction.IN);
            if (watchedTitle.equals(titleVertex)) {
                Watched watched = Watched.fromEdge(watchedEdge);
                Title titleObj = Title.fromVertex(watchedTitle);
                Integer noVotes = titleObj.getnVotes();
                Integer noComments = titleObj.getnComments();
                if (watched.getVote() != null) {
                    noVotes--;
                }
                if (watched.getComment() != null) {
                    noComments--;
                }
                watchedTitle.setProperty("n_votes", noVotes);
                watchedTitle.setProperty("n_comments", noComments);
                watchedEdge.remove();
                commitGraph();
                shutdownGraph();
                return true;
            }
        }
        shutdownGraph();
        return false;
    }

    public boolean addWatchlist(String username, String title) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        Iterable<Vertex> foundTitles = getGraph().getVertices("Title.tid", title);

        if (!foundUsers.iterator().hasNext() || !foundTitles.iterator().hasNext()) {
            shutdownGraph();
            return false;
        }

        Vertex userVertex = foundUsers.iterator().next();
        Vertex titleVertex = foundTitles.iterator().next();

        Iterable<Edge> foundWatchlist = userVertex.getEdges(Direction.OUT, "ToWatch");
        for (Edge watchlistEdge : foundWatchlist) {
            Vertex watchlistTitle = watchlistEdge.getVertex(Direction.IN);
            if (watchlistTitle.equals(titleVertex)) {
                shutdownGraph();
                return false;
            }
        }

        //There is no watchlist. We are going to create one
        Edge watchlistEdge = userVertex.addEdge("ToWatch", titleVertex);
        commitGraph();
        shutdownGraph();
        return true;
    }

    public boolean removeWatchlist(String username, String title) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        Iterable<Vertex> foundTitles = getGraph().getVertices("Title.tid", title);

        if (!foundUsers.iterator().hasNext() || !foundTitles.iterator().hasNext()) {
            shutdownGraph();
            return false;
        }

        Vertex userVertex = foundUsers.iterator().next();
        Vertex titleVertex = foundTitles.iterator().next();

        Iterable<Edge> foundWatchlist = userVertex.getEdges(Direction.OUT, "ToWatch");
        for (Edge watchlistEdge : foundWatchlist) {
            Vertex watchlistTitle = watchlistEdge.getVertex(Direction.IN);
            if (watchlistTitle.equals(titleVertex)) {
                watchlistEdge.remove();
                commitGraph();
                shutdownGraph();
                return true;
            }
        }
        shutdownGraph();
        return false;
    }
}
