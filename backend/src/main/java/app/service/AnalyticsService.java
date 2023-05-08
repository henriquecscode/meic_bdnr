package app.service;

import app.domain.*;
import app.domain.derivatives.CountryAwards;
import app.domain.derivatives.GenreAwards;
import app.domain.derivatives.WorkerAwards;
import app.domain.derivatives.WorkerCountryInfo;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        for (Vertex workerVertex : graph.getVerticesOfClass("Worker")) {
            Iterable<Vertex> foundCountries = workerVertex.getVertices(Direction.OUT, "Nationality");

            if (!foundCountries.iterator().hasNext()) {
                continue;
            }
            Vertex countryVertex = foundCountries.iterator().next();
            Worker worker = Worker.fromVertex(workerVertex);
            Country country = Country.fromVertex(countryVertex);
            List<Title> titles = new ArrayList<>();

            for (Vertex titleVertex : workerVertex.getVertices(Direction.OUT, "Role")) {
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
        return workerCountryInfos;
    }

    public List<GenreAwards> getGenreWithMostAwards(int limit) {
        setGraph();
        List<GenreAwards> genreAwards = new ArrayList<>();
        for (Vertex genreVertex : graph.getVerticesOfClass("Genre")) {
            Genre genre = Genre.fromVertex(genreVertex);
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

        genreAwards.sort((o1, o2) -> o2.getAwards().compareTo(o1.getAwards()));
        return genreAwards.subList(0, limit > genreAwards.size() ? genreAwards.size() : limit);
    }

    public List<WorkerAwards> getWorkerWithMostAwards(int limit) {
        setGraph();

        List<WorkerAwards> workerAwards = new ArrayList<>();
        for (Vertex workerVertex : graph.getVerticesOfClass("Worker")) {
            Worker worker = Worker.fromVertex(workerVertex);
            Integer numberAwards = 0;
            Iterable<Edge> foundRoles = workerVertex.getEdges(Direction.OUT, "Role");

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

        workerAwards.sort((o1, o2) -> o2.getAwards().compareTo(o1.getAwards()));
        return workerAwards.subList(0, limit > workerAwards.size() ? workerAwards.size() : limit);
    }

    public List<CountryAwards> getCountryWithMostAwards(int limit) {
        setGraph();

        List<CountryAwards> countryAwards = new ArrayList<>();

        for (Vertex countryVertex : graph.getVerticesOfClass("Country")) {
            Country country = Country.fromVertex(countryVertex);
            Integer numberAwards = 0;
            for (Vertex workerVertex : countryVertex.getVertices(Direction.IN, "Nationality")) {
                for (Edge edge : workerVertex.getEdges(Direction.OUT, "Role")) {
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
        countryAwards.sort((o1, o2) -> o2.getAwards().compareTo(o1.getAwards()));
        return countryAwards.subList(0, limit > countryAwards.size() ? countryAwards.size() : limit);
    }
}
