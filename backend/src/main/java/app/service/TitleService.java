package app.service;

import app.domain.*;
import app.domain.Character;
import app.domain.derivatives.*;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TitleService extends GeneralService {


    public TitleService(OrientGraphFactory factory) {
        super(factory);
    }


    public Title findById(String id) {
        setGraph();
        Iterable<Vertex> foundTitles = graph.getVertices("Title.tid", id);
        if (!foundTitles.iterator().hasNext()) {
            return null;
        }
        Title title = Title.fromVertex(foundTitles.iterator().next());
        return title;
    }

    public MovieInfo getInfoById(String id) {
        setGraph();
        MovieInfo movieInfo = new MovieInfo();
        Iterable<Vertex> foundTitles = graph.getVertices("Title.tid", id);
        if (!foundTitles.iterator().hasNext()) {
            return null;
        }
        Vertex titleVertex = foundTitles.iterator().next();
        Title title = Title.fromVertex(titleVertex);
        movieInfo.setTitle(title);

        Iterable<Edge> foundRoles = titleVertex.getEdges(Direction.OUT, "Role");
        List<RoleInfo> roles = new ArrayList<>();
        for (Edge roleEdge : foundRoles) {
            Role role;
            if (roleEdge.getLabel().equals("Crew")) {
                Crew crew = Crew.fromEdge(roleEdge);
                role = crew;
            } else if (roleEdge.getLabel().equals("Character")) {

                Character character = Character.fromEdge(roleEdge);
                role = character;
            } else {
                role = Role.fromEdge(roleEdge);
            }

            Vertex workerVertex = roleEdge.getVertex(Direction.IN);
            Worker worker = Worker.fromVertex(workerVertex);
            RoleInfo roleInfo = new RoleInfo();
            roleInfo.setWorker(worker);
            roleInfo.setRole(role);
            roles.add(roleInfo);
        }
        movieInfo.setRoles(roles);

        Iterable<Edge> foundWatches = titleVertex.getEdges(Direction.IN, "Watch");
        List<WatchInfo> watches = new ArrayList<>();
        for (Edge watch : foundWatches) {
            Watched watched = Watched.fromEdge(watch);
            Vertex userVertex = watch.getVertex(Direction.OUT);
            User user = User.fromVertex(userVertex);
            WatchInfo watchInfo = new WatchInfo();
            watchInfo.setWatched(watched);
            watchInfo.setUser(user);
            watches.add(watchInfo);
        }
        movieInfo.setWatched(watches);

        Iterable<Edge> foundSeries = titleVertex.getEdges(Direction.OUT, "PartOfSeries");
        if (foundSeries.iterator().hasNext()) {
            Vertex seriesVertex = foundSeries.iterator().next().getVertex(Direction.IN);
            Series series = Series.fromVertex(seriesVertex);

            Iterable<Edge> foundSeriesTitles = seriesVertex.getEdges(Direction.IN, "PartOfSeries");
            List<SeriesTitleInfo> seriesTitles = new ArrayList<>();
            for (Edge seriesTitle : foundSeriesTitles) {
                Title seriesTitleTitle = Title.fromVertex(seriesTitle.getVertex(Direction.OUT));
                Integer order = seriesTitle.getProperty("film_number");
                SeriesTitleInfo seriesTitleInfo = new SeriesTitleInfo();
                seriesTitleInfo.setTitle(seriesTitleTitle);
                seriesTitleInfo.setOrder(order);
                seriesTitles.add(seriesTitleInfo);
            }
            SeriesInfo seriesInfo = new SeriesInfo();
            seriesInfo.setSeries(series);
            seriesInfo.setTitles(seriesTitles);
        }

        return movieInfo;
    }

    public List<Title> findAll() {
        setGraph();
        List<Title> titles = new ArrayList<>();
        for (Vertex vertex : graph.getVerticesOfClass("Title")) {
            Title title = Title.fromVertex(vertex);
            titles.add(title);
        }
        return titles;
    }

    public List<Title> findByTitle(String title) {
        setGraph();

        String titleQueryWords[] = title.split(" ");
        for (int i = 0; i < titleQueryWords.length; i++) {
            titleQueryWords[i] = "*" + titleQueryWords[i] + "*";
        }
        title = String.join(" ", titleQueryWords);

        String query = "SELECT FROM Title WHERE SEARCH_CLASS(\"name:" + title + "\") = true";
        OCommandSQL queryCommand = new OCommandSQL(query);
        List<Title> titles = new ArrayList<>();
        for (Vertex vertex : graph.getVertices("Title.title", title)) {
            Title foundTitle = Title.fromVertex(vertex);
            titles.add(foundTitle);
        }
        return titles;
    }
}
