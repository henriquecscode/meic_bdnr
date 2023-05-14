package app.service;

import app.domain.Title;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.record.ORecord;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.record.impl.OVertexDocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService extends GeneralService {
    private final ODatabaseDocument db;

    SearchService(ODatabaseDocument oDatabaseDocument, OrientGraphFactory factory) {
        super(factory);
        this.db = oDatabaseDocument;
    }

    public List<Title> findByTitle(String titleSearch) {
        db.activateOnCurrentThread();
        String titleQueryWords[] = titleSearch.split(" ");
        for (int i = 0; i < titleQueryWords.length; i++) {
            titleQueryWords[i] = "*" + titleQueryWords[i] + "*";
        }
        titleSearch = String.join(" ", titleQueryWords);

        String query = "SELECT FROM Title WHERE SEARCH_CLASS(\"name:" + titleSearch + "\") = true";
        OCommandSQL queryCommand = new OCommandSQL(query);
        OResultSet resultSet = db.query(query);
        List<Title> titles = new ArrayList<>();
        for (OResultSet it = resultSet; it.hasNext(); ) {
            OResult result = it.next();
            OVertexDocument titleOVertex = (OVertexDocument) result.getRecord().get();
            Title title = Title.fromODocument(titleOVertex);
            titles.add(title);
        }

        return titles;
    }

    public List<Title> findByGenre(String genreSearch, List<Title> filter) {
        setGraph();
        genreSearch = genreSearch.toLowerCase();

        List<Title> filteredTitles = new ArrayList<>();
        for (Title title : filter) {
            Iterable<Vertex> foundTitles = graph.getVertices("Title.tid", title.getTid());
            if (!foundTitles.iterator().hasNext()) {
                shutdownGraph();
                return null;
            }
            Vertex titleVertex = foundTitles.iterator().next();
            for (Vertex genreVertex : titleVertex.getVertices(Direction.OUT, "HasGenre")) {
                String genreName = genreVertex.getProperty("name");
                genreName = genreName.toLowerCase();
                if (genreName.contains(genreSearch)) {
                    filteredTitles.add(title);
                    break;
                }
            }
        }
        shutdownGraph();
        return filteredTitles;
    }

    public List<Title> findByGenre(String genreSearch) {
        setGraph();
        genreSearch = genreSearch.toLowerCase();
        List<Title> titles = new ArrayList<>();

        for (Vertex genre : graph.getVerticesOfClass("Genre")) {
            String genreName = genre.getProperty("name");
            genreName = genreName.toLowerCase();
            if (genreName.contains(genreSearch)) {
                for (Vertex titleVertex : genre.getVertices(Direction.IN, "HasGenre")) {
                    Title title = Title.fromVertex(titleVertex);
                    titles.add(title);
                }
            }
        }
        shutdownGraph();
        return titles;
    }

    public List<Title> findByYear(Integer yearSearch, List<Title> titles) {
        List<Title> filteredTitles = new ArrayList<>();
        for (Title title : titles) {
            if (title.getStartYear().equals(yearSearch)) {
                filteredTitles.add(title);
            }
        }

        return filteredTitles;
    }

    public List<Title> findByYear(Integer yearSearch) {
        setGraph();
        List<Title> titles = new ArrayList<>();
        for (Vertex title : graph.getVertices("Title.startYear", yearSearch)) {
            titles.add(Title.fromVertex(title));
        }
        shutdownGraph();
        return titles;
    }


}
