package app.service;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {
    private OrientGraphFactory factory;
    private OrientGraph graph;

    public GeneralService(final OrientGraphFactory factory) {
        this.factory = factory;
    }

    protected void setGraph() {
        graph = factory.getTx();
        graph.setStandardElementConstraints(false);
    }

    protected OrientGraph getGraph() {
        graph.makeActive();
        return graph;
    }

    //
//    protected void setGraphStandardConstraints() {
//        factory.getTx().setStandardElementConstraints(false);
//    }
//
    protected void commitGraph() {
        graph.commit();
    }

    protected void shutdownGraph() {
//        return;
//        graph.shutdown();
    }
}
