package app.domain;

import com.tinkerpop.blueprints.Vertex;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class Person {

    protected String name;

    public Person() {
    }

    public static Person fromODocument(ODocument oDocument) {
        Person person = new Person();
        person.name = oDocument.field("name");

        return person;
    }

    public static Person fromVertex(Vertex vertex) {
        Person person = new Person();
        person.name = vertex.getProperty("name");

        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
