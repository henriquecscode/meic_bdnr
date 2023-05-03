package app.domain.derivatives;

import app.domain.Title;

public class SeriesTitleInfo {
    Integer order;
    Title title;

    public SeriesTitleInfo() {
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SeriesTitleInfo{" +
                "order=" + order +
                ", title='" + title + '\'' +
                '}';
    }
}
