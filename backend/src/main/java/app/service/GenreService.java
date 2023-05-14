package app.service;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;
import app.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService extends GeneralService {

    public GenreService(OrientGraphFactory factory) {
        super(factory);
    }

    public List<Genre> findAll() {
        setGraph();

        List<Genre> genres = new ArrayList<>();

        for (Vertex vertex : getGraph().getVerticesOfClass("Genre")) {
            Genre genre = Genre.fromVertex(vertex);
            genres.add(genre);
        }

        shutdownGraph();
        return genres;
    }
}
