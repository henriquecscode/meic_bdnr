package app.domain;


import java.util.List;

public class EntityAwards {

    List<EntityAward> awards;

    public EntityAwards() {
    }

    public EntityAwards(database.EntityAwards entityAwards) {
        awards = new java.util.ArrayList<>();
        for (database.EntityAward award : entityAwards.getAwards()) {
            awards.add(new EntityAward(award));
        }
    }

    public List<EntityAward> getAwards() {
        return awards;
    }

    public void setAwards(List<EntityAward> awards) {
        this.awards = awards;
    }

    @Override
    public String toString() {
        return "EntityAwards{" +
                "awards=" + awards +
                '}';
    }
}
