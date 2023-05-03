package xyz.dassiorleando.springbootorientdb.service;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;
import xyz.dassiorleando.springbootorientdb.domain.Country;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService extends GeneralService {


    public CountryService(OrientGraphFactory factory) {
        super(factory);
    }

    public List<Country> findAll() {
        setGraph();

        List<Country> countries = new ArrayList<>();

        for (Vertex vertex : graph.getVerticesOfClass("Country")) {
            Country country = Country.fromVertex(vertex);
            countries.add(country);
        }

        return countries;
    }
}
