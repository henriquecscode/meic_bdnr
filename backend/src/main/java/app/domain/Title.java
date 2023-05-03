package app.domain;

import com.tinkerpop.blueprints.Vertex;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.util.List;

public class Title {
    private String tid;

    private String name;

    private String summary;

    private Integer duration;

    private Integer startYear;

    private List<String> productionCompanies;
    private EntityAwards awards;

    private Integer nComments;

    private Integer nVotes;

    public static Title fromODocument(ODocument oDocument) {
        Title title = new Title();
        title.tid = oDocument.field("tid");
        title.name = oDocument.field("name");
        title.summary = oDocument.field("summary");
        title.duration = oDocument.field("duration");
        title.startYear = oDocument.field("startYear");
        title.productionCompanies = oDocument.field("productionCompany");
        title.awards = new EntityAwards(new database.EntityAwards(oDocument.field("awards")));
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
        title.productionCompanies = vertex.getProperty("productionCompany");
        title.awards = new EntityAwards(new database.EntityAwards(vertex.getProperty("awards")));
        title.nComments = vertex.getProperty("n_comments");
        title.nVotes = vertex.getProperty("n_votes");

        return title;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public List<String> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<String> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public EntityAwards getAwards() {
        return awards;
    }

    public void setAwards(EntityAwards awards) {
        this.awards = awards;
    }

    public Integer getnComments() {
        return nComments;
    }

    public void setnComments(Integer nComments) {
        this.nComments = nComments;
    }

    public Integer getnVotes() {
        return nVotes;
    }

    public void setnVotes(Integer nVotes) {
        this.nVotes = nVotes;
    }

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
                ", productionCompanies=" + productionCompanies +
                ", awards=" + awards +
                ", nComments=" + nComments +
                ", nVotes=" + nVotes +
                '}';
    }
}
