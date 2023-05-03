package app.domain;

public class EntityAward {

    String awardName;
    String receivedOn;

    public EntityAward(database.EntityAward award) {
        this.awardName = award.getAwardName();
        this.receivedOn = award.getReceivedOn();
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
        return "EntityAward{" +
                "awardName='" + awardName + '\'' +
                ", receivedOn='" + receivedOn + '\'' +
                '}';
    }
}
