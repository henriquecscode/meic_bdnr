package xyz.dassiorleando.springbootorientdb.domain;

import com.tinkerpop.blueprints.Edge;
import database.EntityAwards;

public class Crew extends Role {

    String type;

    public Crew() {
    }

    public static Crew fromEdge(Edge edge) {
        Crew crew = new Crew();
        crew.entityAwards = new EntityAwards(edge.getProperty("awards"));
        crew.type = edge.getProperty("type");

        return crew;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "type='" + type + '\'' +
                ", entityAwards=" + entityAwards +
                '}';
    }
}