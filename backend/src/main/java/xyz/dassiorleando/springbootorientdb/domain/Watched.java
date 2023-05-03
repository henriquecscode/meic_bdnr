package xyz.dassiorleando.springbootorientdb.domain;

import com.tinkerpop.blueprints.Edge;

import java.util.Date;

public class Watched {
    Integer vote;
    String comment;
    Date date;

    public Watched() {
    }

    public static Watched fromEdge(Edge edge) {
        Watched watched = new Watched();
        watched.vote = edge.getProperty("vote");
        watched.comment = edge.getProperty("comment");
        watched.date = edge.getProperty("date");

        return watched;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Watched{" +
                "vote=" + vote +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
