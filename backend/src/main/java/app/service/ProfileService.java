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

        Iterable<Vertex> foundUsers = graph.getVertices("User.username", username);

        if (!foundUsers.iterator().hasNext()) {
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

        return userInfo;
    }
}
