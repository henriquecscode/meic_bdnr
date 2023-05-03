package app.domain;

import com.tinkerpop.blueprints.Edge;
import database.EntityAwards;

public class Role {
    protected EntityAwards entityAwards;

    public Role() {
    }

    public static Role fromEdge(Edge edge) {
        Role role = new Role();
        role.entityAwards = new EntityAwards(edge.getProperty("awards"));

        return role;
    }

    public EntityAwards getEntityAwards() {
        return entityAwards;
    }

    public void setEntityAwards(EntityAwards entityAwards) {
        this.entityAwards = entityAwards;
    }


    @Override
    public String toString() {
        return "Role{" +
                "entityAwards=" + entityAwards +
                '}';
    }
}
