package app.service;

import app.domain.Title;
import app.domain.User;
import app.domain.Watched;
import app.domain.derivatives.WatchInfoByUser;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RecommendationService extends GeneralService {

    public RecommendationService(OrientGraphFactory factory) {
        super(factory);
    }

    public List<WatchInfoByUser> getFriendsFilms(String username, int level) {
        setGraph();

        List<WatchInfoByUser> watchInfoByUsers = new ArrayList<>();
        HashSet<Vertex> prevLevelUsers = new HashSet<>();
        HashSet<Vertex> allLevelUsers = new HashSet<>();
        HashSet<Vertex> levelUsers = new HashSet<>();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        if (!foundUsers.iterator().hasNext()) {
            shutdownGraph();
            return null;
        }
        Vertex userVertex = foundUsers.iterator().next();

        //Get friends
        prevLevelUsers.add(userVertex);
        while (true) {
            for (Vertex prevLevelUser : prevLevelUsers) {
                Iterable<Vertex> foundFriends = prevLevelUser.getVertices(Direction.OUT, "Follows");
                for (Vertex friend : foundFriends) {
                    if (!allLevelUsers.contains(friend)) {
                        allLevelUsers.add(friend);
                        levelUsers.add(friend);
                    }
                }
            }
            level = level - 1;
            if (level == 0) {
                //add the ones in this
                break;
            } else {
                prevLevelUsers.clear();
                prevLevelUsers.addAll(levelUsers);
                levelUsers.clear();
            }
        }

        //Get self films
        Iterable<Vertex> foundFilms = userVertex.getVertices(Direction.OUT, "Watched");
        HashSet<Vertex> selfFilms = new HashSet<>();
        for (Vertex film : foundFilms) {
            selfFilms.add(film);
        }

        // Get recommendations
        for (Vertex friendUser : levelUsers) {
            User friend = User.fromVertex(friendUser);
            Iterable<Edge> foundFriendWatched = friendUser.getEdges(Direction.OUT, "Watched");
            List friendsFilms = new ArrayList<>();
            for (Edge friendWatched : foundFriendWatched) {
                Watched watched = Watched.fromEdge(friendWatched);
                if (watched.getVote() == null) {
                    continue;
                }
                Vertex filmVertex = friendWatched.getVertex(Direction.IN);
                if (selfFilms.contains(filmVertex)) {
                    continue;
                }
                Title title = Title.fromVertex(filmVertex);
                friendsFilms.add(title);
            }

            WatchInfoByUser watchInfoByUser = new WatchInfoByUser(friend, friendsFilms);
            watchInfoByUsers.add(watchInfoByUser);
        }
        shutdownGraph();
        return watchInfoByUsers;
    }

    public List<WatchInfoByUser> getCountryFilms(String username) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        if (!foundUsers.iterator().hasNext()) {
            shutdownGraph();
            return null;
        }
        Vertex userVertex = foundUsers.iterator().next();
        List<WatchInfoByUser> watchInfoByUsers = new ArrayList<>();

        for (Vertex friend : userVertex.getVertices(Direction.OUT, "Follows")) {
            List<Title> friendsFilms = new ArrayList<>();
            User friendUser = User.fromVertex(friend);
            for (Vertex watchedFilm : friend.getVertices(Direction.OUT, "Watched")) {
                Title title = Title.fromVertex(watchedFilm);
                friendsFilms.add(title);
            }
            WatchInfoByUser watchInfoByUser = new WatchInfoByUser(friendUser, friendsFilms);
            watchInfoByUsers.add(watchInfoByUser);
        }
        shutdownGraph();
        return watchInfoByUsers;
    }

    public List<WatchInfoByUser> getAdviseFilms(String username) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        if (!foundUsers.iterator().hasNext()) {
            shutdownGraph();
            return null;
        }
        Vertex userVertex = foundUsers.iterator().next();

        //Films I watched and didn't like
        List<Vertex> filmsIDidntLike = new ArrayList<>();
        for (Edge edge : userVertex.getEdges(Direction.OUT, "Watched")) {
            Watched watched = Watched.fromEdge(edge);
            if (watched.getVote() == null) {
                continue;
            }
            Vertex titleVertex = edge.getVertex(Direction.IN);
            filmsIDidntLike.add(titleVertex);
        }

        List<WatchInfoByUser> watchInfoByUsers = new ArrayList<>();

        for (Vertex friend : userVertex.getVertices(Direction.OUT, "Follows")) {
            List<Title> friendsFilms = new ArrayList<>();
            User friendUser = User.fromVertex(friend);
            for (Vertex filmToWatch : friend.getVertices(Direction.OUT, "ToWatch")) {
                if (!filmsIDidntLike.contains(filmToWatch)) {
                    continue;
                }
                Title title = Title.fromVertex(filmToWatch);
                friendsFilms.add(title);
            }
            WatchInfoByUser watchInfoByUser = new WatchInfoByUser(friendUser, friendsFilms);
            watchInfoByUsers.add(watchInfoByUser);
        }
        shutdownGraph();
        return watchInfoByUsers;
    }
}
