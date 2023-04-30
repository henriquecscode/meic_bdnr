package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntityAwards implements Serializable {
    static private String SEP = ",";
    private List<EntityAward> awards = new ArrayList<>();

    public EntityAwards() {

    }
    public EntityAwards(String string) {
        if(string == null || string.isEmpty()) {
            return;
        }
        String[] awards = string.split(SEP, -1);
        for (String award : awards) {
            addAward(new EntityAward(award));
        }
    }

    public void addAward(EntityAward award) {
        awards.add(award);
    }

    public List<EntityAward> getAwards() {
        return awards;
    }

    public String toString() {
        return String.join(SEP, awards.stream().map(EntityAward::toString).toArray(String[]::new));
    }
}
