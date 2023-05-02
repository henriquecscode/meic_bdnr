package xyz.dassiorleando.springbootorientdb.service;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;
import xyz.dassiorleando.springbootorientdb.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    private final OrientGraphFactory factory;

    public GenreService(OrientGraphFactory factory) {
        this.factory = factory;
    }

    public List<Genre> findAll() {
        OrientGraph graph = factory.getTx();

        List<Genre> genres = new ArrayList<>();

        for (Vertex vertex : graph.getVerticesOfClass("Genre")) {
            Genre genre = Genre.fromVertex(vertex);
            genres.add(genre);
        }

        return genres;
    }
}
