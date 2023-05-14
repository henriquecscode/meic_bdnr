package app.domain;

import com.tinkerpop.blueprints.Vertex;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.util.Objects;

public class Series {
    private String name;

    private String description;

    public Series() {
    }

    public static Series fromODocument(ODocument oDocument) {
        Series series = new Series();
        series.name = oDocument.field("name");
        series.description = oDocument.field("description");

        return series;
    }

    public static Series fromVertex(Vertex vertex) {
        Series series = new Series();
        series.name = vertex.getProperty("name");
        series.description = vertex.getProperty("description");

        return series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Series{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Series series = (Series) o;
        return Objects.equals(getName(), series.getName()) && Objects.equals(getDescription(), series.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription());
    }
}
