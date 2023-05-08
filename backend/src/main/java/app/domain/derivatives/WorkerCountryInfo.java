package app.domain.derivatives;

import app.domain.Country;
import app.domain.Title;
import app.domain.Worker;

import java.util.List;

public class WorkerCountryInfo {
    Country country;
    Worker worker;

    List<Title> titles;

    public WorkerCountryInfo(Country country, Worker worker, List<Title> titles) {
        this.country = country;
        this.worker = worker;
        this.titles = titles;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    @Override
    public String toString() {
        return "WorkerCountryInfo{" +
                "title=" + titles +
                ", country=" + country +
                ", worker=" + worker +
                '}';
    }
}
