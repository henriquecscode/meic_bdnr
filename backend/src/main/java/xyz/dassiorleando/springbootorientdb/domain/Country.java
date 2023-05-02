package xyz.dassiorleando.springbootorientdb.domain;

import com.tinkerpop.blueprints.Vertex;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class Country {
    private String name;

    public Country() {
    }

    public static Country fromODocument(ODocument oDocument) {
        Country country = new Country();
        country.name = oDocument.field("name");

        return country;
    }

    public static Country fromVertex(Vertex vertex) {
        Country country = new Country();
        country.name = vertex.getProperty("name");

        return country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                '}';
    }
}
