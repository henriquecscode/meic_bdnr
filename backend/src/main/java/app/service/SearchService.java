package app.service;

import app.domain.Title;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.record.ORecord;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.record.impl.OVertexDocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import com.tinkerpop.blueprints.Vertex;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final ODatabaseDocument db;

    SearchService(ODatabaseDocument oDatabaseDocument) {
        this.db = oDatabaseDocument;
    }

    public List<Title> findByTitle(String titleSearch) {
        db.activateOnCurrentThread();
        String titleQueryWords[] = titleSearch.split(" ");
        for (int i = 0; i < titleQueryWords.length; i++) {
            titleQueryWords[i] = "*" + titleQueryWords[i] + "*";
        }
        titleSearch = String.join(" ", titleQueryWords);

        String query = "SELECT FROM Title WHERE SEARCH_CLASS(\"name:" + titleSearch + "\") = true";
        OCommandSQL queryCommand = new OCommandSQL(query);
        OResultSet resultSet = db.query(query);
        List<Title> titles = new ArrayList<>();
        for (OResultSet it = resultSet; it.hasNext(); ) {
            OResult result = it.next();
            OVertexDocument titleOVertex = (OVertexDocument) result.getRecord().get();
            Title title = Title.fromODocument(titleOVertex);
            titles.add(title);
        }

        return titles;
    }
}
