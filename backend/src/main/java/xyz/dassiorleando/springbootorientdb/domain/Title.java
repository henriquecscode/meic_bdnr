package xyz.dassiorleando.springbootorientdb.domain;

import com.tinkerpop.blueprints.Vertex;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.tinkerpop.blueprints.Vertex;
import database.EntityAwards;

import javax.validation.constraints.NotNull;

public class Title {

    @NotNull
    private int tid;
    @NotNull
    private String name;

    private String summary;

    public static Title fromODocument(ODocument oDocument) {
        Title title = new Title();
        title.tid = oDocument.field("tid");
        title.name = oDocument.field("name");
        title.summary = oDocument.field("summary");
        title.duration = oDocument.field("duration");
        title.startYear = oDocument.field("startYear");
        title.awards = new EntityAwards(oDocument.field("awards"));
        title.nComments = oDocument.field("n_comments");
        title.nVotes = oDocument.field("n_votes");

        return title;
    }

    public static Title fromVertex(Vertex vertex) {
        Title title = new Title();
        title.tid = vertex.getProperty("tid");
        title.name = vertex.getProperty("name");
        title.summary = vertex.getProperty("summary");
        title.duration = vertex.getProperty("duration");
        title.startYear = vertex.getProperty("startYear");
        title.awards = new EntityAwards(vertex.getProperty("awards"));
        title.nComments = vertex.getProperty("n_comments");
        title.nVotes = vertex.getProperty("n_votes");

        return title;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public EntityAwards getAwards() {
        return awards;
    }

    public void setAwards(EntityAwards awards) {
        this.awards = awards;
    }

    public int getnComments() {
        return nComments;
    }

    public void setnComments(int nComments) {
        this.nComments = nComments;
    }

    public int getnVotes() {
        return nVotes;
    }

    public void setnVotes(int nVotes) {
        this.nVotes = nVotes;
    }

    private int duration;

    private int startYear;

    private EntityAwards awards;

    private int nComments;

    private int nVotes;

    public Title() {
    }

    @Override
    public String toString() {
        return "Title{" +
                "tid=" + tid + +'\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", duration=" + duration +
                ", startYear=" + startYear +
                ", awards=" + awards +
                ", nComments=" + nComments +
                ", nVotes=" + nVotes +
                '}';
    }
}
