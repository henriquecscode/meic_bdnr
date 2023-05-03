package xyz.dassiorleando.springbootorientdb.service;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;
import xyz.dassiorleando.springbootorientdb.domain.Genre;

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

        for (Vertex vertex : graph.getVerticesOfClass("Genre")) {
            Genre genre = Genre.fromVertex(vertex);
            genres.add(genre);
        }

        return genres;
    }
}
