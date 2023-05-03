package app.domain;

import com.tinkerpop.blueprints.Edge;

public class Character extends Role {

    String name;


    public Character() {
    }

    public static Character fromEdge(Edge edge) {
        Character character = new Character();
        character.entityAwards = new EntityAwards(new database.EntityAwards(edge.getProperty("awards")));
        character.name = edge.getProperty("name");

        return character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", entityAwards=" + entityAwards +
                '}';
    }
}
