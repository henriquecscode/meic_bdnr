package xyz.dassiorleando.springbootorientdb.service;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;
import xyz.dassiorleando.springbootorientdb.domain.Country;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    private final OrientGraphFactory factory;

    public CountryService(OrientGraphFactory factory) {
        this.factory = factory;
    }

    public List<Country> findAll() {
        OrientGraph graph = factory.getTx();

        List<Country> countries = new ArrayList<>();

        for (Vertex vertex : graph.getVerticesOfClass("Country")) {
            Country country = Country.fromVertex(vertex);
            countries.add(country);
        }

        return countries;
    }
}
