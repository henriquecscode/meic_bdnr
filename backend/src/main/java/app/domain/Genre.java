package app.domain;

import com.tinkerpop.blueprints.Vertex;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class Genre {
    private String name;

    public Genre() {
    }

    public static Genre fromODocument(ODocument oDocument) {
        Genre genre = new Genre();
        genre.name = oDocument.field("name");

        return genre;
    }

    public static Genre fromVertex(Vertex vertex) {
        Genre genre = new Genre();
        genre.name = vertex.getProperty("name");

        return genre;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                '}';
    }
}
