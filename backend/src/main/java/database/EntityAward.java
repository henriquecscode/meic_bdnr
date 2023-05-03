package database;

import java.io.Serializable;

public class EntityAward implements Serializable {
    static private String SEP = "%";
    private String awardName = "";
    private String receivedOn = "";

    public EntityAward() {

    }

    public EntityAward(String string) {
        String[] awardDetails = string.split(SEP, -1);
        this.awardName = awardDetails[0];
        this.receivedOn = awardDetails[1];
    }


    public EntityAward(String awardName, String receivedOn) {
        this.awardName = awardName;
        this.receivedOn = receivedOn;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
    }

    @Override
    public String toString() {
        return awardName + SEP + receivedOn;
    }
}
