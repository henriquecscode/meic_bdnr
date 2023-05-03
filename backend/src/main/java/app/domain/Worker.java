package app.domain;

import com.tinkerpop.blueprints.Vertex;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class Worker extends Person {

    private String nid;

    private Integer birthYear;

    private Integer deathYear;

    public Worker() {
    }

    public static Worker fromODocument(ODocument oDocument) {
        Worker worker = new Worker();
        worker.name = oDocument.field("name");
        worker.nid = oDocument.field("nid");
        worker.birthYear = oDocument.field("birthYear");
        worker.deathYear = oDocument.field("deathYear");

        return worker;
    }

    public static Worker fromVertex(Vertex vertex) {
        Worker worker = new Worker();
        worker.name = vertex.getProperty("name");
        worker.nid = vertex.getProperty("nid");
        worker.birthYear = vertex.getProperty("birthYear");
        worker.deathYear = vertex.getProperty("deathYear");

        return worker;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "nid='" + nid + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", name='" + name + '\'' +
                '}';
    }
}
