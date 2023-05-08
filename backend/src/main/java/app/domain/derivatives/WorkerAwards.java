package app.domain.derivatives;

import app.domain.Worker;

public class WorkerAwards {

    Worker worker;
    Integer awards;

    public WorkerAwards(Worker worker, Integer awards) {
        this.worker = worker;
        this.awards = awards;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Integer getAwards() {
        return awards;
    }

    public void setAwards(Integer awards) {
        this.awards = awards;
    }

    @Override
    public String toString() {
        return "WorkerAwards{" +
                "worker=" + worker +
                ", awards=" + awards +
                '}';
    }
}
