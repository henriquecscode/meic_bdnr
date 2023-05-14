package app.service;

import app.domain.*;
import app.domain.derivatives.*;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService extends GeneralService {
    public AnalyticsService(OrientGraphFactory factory) {
        super(factory);
    }

    public List<WorkerCountryInfo> getSameCountry() {
        setGraph();

        // go to every worker
        // get the country
        // get the movies and check for the country
        // if the country is the same, return the worker with the films
        List<WorkerCountryInfo> workerCountryInfos = new ArrayList<>();
        for (Vertex workerVertex : getGraph().getVerticesOfClass("Worker")) {
            Iterable<Vertex> foundCountries = workerVertex.getVertices(Direction.OUT, "Nationality");

            if (!foundCountries.iterator().hasNext()) {
                continue;
            }
            Vertex countryVertex = foundCountries.iterator().next();
            Worker worker = Worker.fromVertex(workerVertex);
            Country country = Country.fromVertex(countryVertex);
            List<Title> titles = new ArrayList<>();

            for (Vertex titleVertex : workerVertex.getVertices(Direction.IN, "Role")) {
                Iterable<Vertex> foundCountries2 = titleVertex.getVertices(Direction.OUT, "Role");
                if (!foundCountries2.iterator().hasNext()) {
                    continue;
                }
                Vertex titleCountryVertex = foundCountries2.iterator().next();
                Vertex countryVertex2 = foundCountries2.iterator().next();
                if (countryVertex2.equals(titleCountryVertex)) {
                    Title title = Title.fromVertex(titleVertex);
                    titles.add(title);

                }
            }
            if (titles.isEmpty()) {
                continue;
            }
            WorkerCountryInfo workerCountryInfo = new WorkerCountryInfo(country, worker, titles);
            workerCountryInfos.add(workerCountryInfo);

        }
        shutdownGraph();
        return workerCountryInfos;
    }

    public List<GenreAwards> getGenreWithMostAwards(int limit) {
        setGraph();

        List<GenreAwards> genreAwards = new ArrayList<>();
        for (Vertex genreVertex : getGraph().getVerticesOfClass("Genre")) {
            Genre genre = Genre.fromVertex(genreVertex);
            if (genre.getName().equals("")) {
                continue;
            }
            Integer numberAwards = 0;
            Iterable<Vertex> foundTitles = genreVertex.getVertices(Direction.IN, "HasGenre");

            for (Vertex titleVertex : foundTitles) {
                Title title = Title.fromVertex(titleVertex);
                int titleAwards = title.getAwards().getAwards().size();
                numberAwards += titleAwards;
            }
            if (numberAwards == 0) {
                continue;
            }
            GenreAwards genreAwardsObj = new GenreAwards(genre, numberAwards);
            genreAwards.add(genreAwardsObj);
        }

        shutdownGraph();
        genreAwards.sort((o1, o2) -> o2.getAwards().compareTo(o1.getAwards()));
        return genreAwards.subList(0, limit > genreAwards.size() ? genreAwards.size() : limit);
    }

    public List<WorkerAwards> getWorkerWithMostAwards(int limit) {
        setGraph();

        List<WorkerAwards> workerAwards = new ArrayList<>();
        for (Vertex workerVertex : getGraph().getVerticesOfClass("Worker")) {
            Worker worker = Worker.fromVertex(workerVertex);
            Integer numberAwards = 0;
            Iterable<Edge> foundRoles = workerVertex.getEdges(Direction.IN, "Role");

            for (Edge roleEdge : foundRoles) {
                Role role = Role.fromEdge(roleEdge);
                int roleAwards = role.getEntityAwards().getAwards().size();
                numberAwards += roleAwards;
            }
            if (numberAwards == 0) {
                continue;
            }
            WorkerAwards workerAwardsObj = new WorkerAwards(worker, numberAwards);
            workerAwards.add(workerAwardsObj);
        }
        shutdownGraph();
        workerAwards.sort((o1, o2) -> o2.getAwards().compareTo(o1.getAwards()));
        return workerAwards.subList(0, limit > workerAwards.size() ? workerAwards.size() : limit);
    }

    public List<CountryAwards> getCountryWithMostAwards(int limit) {
        setGraph();

        List<CountryAwards> countryAwards = new ArrayList<>();

        for (Vertex countryVertex : getGraph().getVerticesOfClass("Country")) {
            Country country = Country.fromVertex(countryVertex);
            Integer numberAwards = 0;
            for (Vertex workerVertex : countryVertex.getVertices(Direction.IN, "Nationality")) {
                for (Edge edge : workerVertex.getEdges(Direction.IN, "Role")) {
                    Role role = Role.fromEdge(edge);
                    int roleAwards = role.getEntityAwards().getAwards().size();
                    numberAwards += roleAwards;

                }
            }
            if (numberAwards == 0) {
                continue;
            }
            CountryAwards countryAwardsObj = new CountryAwards(country, numberAwards);
            countryAwards.add(countryAwardsObj);
        }
        shutdownGraph();

        countryAwards.sort((o1, o2) -> o2.getAwards().compareTo(o1.getAwards()));
        return countryAwards.subList(0, limit > countryAwards.size() ? countryAwards.size() : limit);
    }

    public List<WatchedSeriesInfo> getSeriesWatchedByUsers() {
        setGraph();

        List<WatchedSeriesInfo> watchedSeriesInfos = new ArrayList<>();
        for (Vertex seriesVertex : getGraph().getVerticesOfClass("Series")) {
            // Get every title
            Iterable<Vertex> foundTitles = seriesVertex.getVertices(Direction.IN, "PartOfSeries");
            if (!foundTitles.iterator().hasNext()) {
                continue;
            }
            // Get every user that watched the title

            Vertex firstTitle = foundTitles.iterator().next();
            Iterable<Vertex> foundUsers = firstTitle.getVertices(Direction.IN, "Watched");
            Set<Vertex> completedSeriesUsers = new HashSet<>();
            for (Vertex user : foundUsers) {
                completedSeriesUsers.add(user);
            }

            for (Vertex title : foundTitles) {
                Iterable<Vertex> foundUsers2 = title.getVertices(Direction.IN, "Watched");
                Set<Vertex> completedSeriesUsers2 = new HashSet<>();
                for (Vertex user : foundUsers2) {
                    completedSeriesUsers2.add(user);
                }
                completedSeriesUsers.retainAll(completedSeriesUsers2);
            }
            Series series = Series.fromVertex(seriesVertex);
            List<User> seriesUsers = completedSeriesUsers.stream().map(User::fromVertex).collect(Collectors.toList());

            WatchedSeriesInfo watchedSeriesInfo = new WatchedSeriesInfo(series, seriesUsers);
            watchedSeriesInfos.add(watchedSeriesInfo);
        }
        shutdownGraph();
        return watchedSeriesInfos;
    }

    public List<WatchedSeriesInfo> getSeriesWatchedByFriends(String username) {
        setGraph();

        Iterable<Vertex> foundUsers = getGraph().getVertices("User.username", username);
        if (!foundUsers.iterator().hasNext()) {
            shutdownGraph();
            return new ArrayList<>();
        }

        Vertex userVertex = foundUsers.iterator().next();

        Iterable<Vertex> watchedTitles = userVertex.getVertices(Direction.OUT, "Watched");

        Map<Vertex, Set<Vertex>> titlesBySeries = new HashMap<>();
        Map<Vertex, Set<Vertex>> titlesBySeriesAllTitles = new HashMap<>();
        for (Vertex watchedTitle : watchedTitles) {

            Iterable<Vertex> series = watchedTitle.getVertices(Direction.OUT, "PartOfSeries");
            if (!series.iterator().hasNext()) {
                continue;
            }
            Vertex seriesVertex = series.iterator().next();
            //Update series
            if (!titlesBySeriesAllTitles.containsKey(seriesVertex)) {
                titlesBySeries.put(seriesVertex, new HashSet<>());
                titlesBySeriesAllTitles.put(seriesVertex, new HashSet<>());
                Iterable<Vertex> titles = seriesVertex.getVertices(Direction.IN, "PartOfSeries");
                for (Vertex title : titles) {
                    titlesBySeries.get(seriesVertex).add(title);
                    titlesBySeriesAllTitles.get(seriesVertex).add(title);
                }
            }


            // Update the ones that we watched
            titlesBySeries.get(seriesVertex).remove(watchedTitle);
        }

        Map<Series, List<User>> friendsWatchedSeries = new HashMap<>();
        for (Vertex series : titlesBySeries.keySet()) {
            if (titlesBySeries.get(series).isEmpty()) {
                Series seriesObj = Series.fromVertex(series);
                friendsWatchedSeries.put(seriesObj, new ArrayList<>());
                friendsWatchedSeries.get(seriesObj).add(User.fromVertex(userVertex));
            }
        }

        Iterable<Vertex> friends = userVertex.getVertices(Direction.OUT, "Follows");

        for (Vertex friend : friends) {
            Map<Vertex, Set<Vertex>> friendsTitlesBySeries = new HashMap<>();
            Iterable<Vertex> friendWatchedTitles = friend.getVertices(Direction.OUT, "Watched");
            for (Vertex friendWatchedTitle : friendWatchedTitles) {
                Iterable<Vertex> friendSeries = friendWatchedTitle.getVertices(Direction.OUT, "PartOfSeries");
                if (!friendSeries.iterator().hasNext()) {
                    continue;
                }
                Vertex friendSeriesVertex = friendSeries.iterator().next();
                Series friendsSeriesObj = Series.fromVertex(friendSeriesVertex);
                if (!friendsWatchedSeries.containsKey(friendsSeriesObj)) {
                    continue;
                }
                if (!friendsTitlesBySeries.containsKey(friendSeriesVertex)) {
                    friendsTitlesBySeries.put(friendSeriesVertex, new HashSet<>());
                }
                friendsTitlesBySeries.get(friendSeriesVertex).add(friendWatchedTitle);
            }

            for (Vertex series : friendsTitlesBySeries.keySet()) {
                Series seriesObj = Series.fromVertex(series);
                if (friendsTitlesBySeries.get(series).containsAll(titlesBySeriesAllTitles.get(series))) {
                    friendsWatchedSeries.get(seriesObj).add(User.fromVertex(friend));
                }
            }
        }
        List<WatchedSeriesInfo> friendsWatchedSeriesInfo = new ArrayList<>();
        for (Series series : friendsWatchedSeries.keySet()) {
            friendsWatchedSeriesInfo.add(new WatchedSeriesInfo(series, friendsWatchedSeries.get(series)));
        }
        shutdownGraph();
        return friendsWatchedSeriesInfo;
    }
}

