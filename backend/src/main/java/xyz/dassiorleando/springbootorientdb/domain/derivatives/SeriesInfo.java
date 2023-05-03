package xyz.dassiorleando.springbootorientdb.domain.derivatives;

import xyz.dassiorleando.springbootorientdb.domain.Series;
import xyz.dassiorleando.springbootorientdb.domain.Title;

import java.util.List;

public class SeriesInfo {

    Series series;
    List<SeriesTitleInfo> titles;

    public SeriesInfo() {
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public List<SeriesTitleInfo> getTitles() {
        return titles;
    }

    public void setTitles(List<SeriesTitleInfo> titles) {
        this.titles = titles;
    }

    @Override
    public String toString() {
        return "SeriesInfo{" +
                "series=" + series +
                ", titles=" + titles +
                '}';
    }
}
