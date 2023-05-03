package xyz.dassiorleando.springbootorientdb.service;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {
    private OrientGraphFactory factory;
    protected OrientGraph graph;

    public GeneralService(final OrientGraphFactory factory) {
        this.factory = factory;
    }

    protected void setGraph() {
        graph = factory.getTx();
    }
}
