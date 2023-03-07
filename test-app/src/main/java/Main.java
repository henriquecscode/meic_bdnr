import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // CONTEXT: Graph of people who are friends

        // Established a connection with the server
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        // Established a connection with the database - open a database session
        ODatabaseSession db = orient.open("test", "admin", "admin");

        // Create the Database Schema
        // - always check if exists before creating each component
        createSchema(db);

        // Create and Query a Graph
        createPeople(db);
        executeAQuery(db);
        executeAnotherQuery(db);

        // Don't forget to close the connections
        db.close();
        orient.close();
    }

    private static void createSchema(ODatabaseSession db) {
        OClass person = db.getClass("Person");

        if (person == null) {
            person = db.createVertexClass("Person");
        }

        if (person.getProperty("name") == null) {
            person.createProperty("name", OType.STRING);
            person.createIndex("Person_name_index", OClass.INDEX_TYPE.NOTUNIQUE, "name");
        }

        if (db.getClass("FriendOf") == null) {
            db.createEdgeClass("FriendOf");
        }
    }

    private static void createPeople(ODatabaseSession db){
        OVertex alice = createPerson(db, "Alice", "Foo");
        OVertex bob = createPerson(db, "Bob", "Bar");
        OVertex jim = createPerson(db, "Jim", "Baz");

        // Alice --FriendOf--> Bob --FriendOf--> Jim
        OEdge edge1 = alice.addEdge(bob, "FriendOf");
        edge1.save();
        OEdge edge2 = bob.addEdge(jim, "FriendOf");
        edge2.save();
        // NOTE: edges are plain documents - get/set on properties exactly like for vertices
    }

    private static OVertex createPerson(ODatabaseSession db, String name, String surname) {
        OVertex result = db.newVertex("Person");
        result.setProperty("name", name);
        result.setProperty("surname", surname);
        // NOTE: even without defining the surname in the schema - schema-mixed
        // ability to add new properties at run time, without having to deal with further schema definitions

        result.save();
        return result;
    }

    private static void executeAQuery(ODatabaseSession db) {
        String query = "SELECT expand(out('FriendOf').out('FriendOf')) from Person where name = ?";
        OResultSet rs = db.query(query, "Alice");

        while (rs.hasNext()) {
            OResult item = rs.next();
            System.out.println("friend: " + item.getProperty("name"));
        }
        // OR while with JAVA STREAMS API:
        // rs.stream().forEach(x -> System.out.println("friend: " + x.getProperty("name")));

        rs.close(); // REMEMBER TO ALWAYS CLOSE THE RESULT SET!!!
    }

    private static void executeAnotherQuery(ODatabaseSession db) {
        String query =
                " MATCH                                           " +
                        "   {class:Person, as:a, where: (name = :name1)}, " +
                        "   {class:Person, as:b, where: (name = :name2)}, " +
                        "   {as:a} -FriendOf-> {as:x} -FriendOf-> {as:b}  " +
                        " RETURN x.name as friend                         ";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name1", "Alice");
        params.put("name2", "Jim");

        OResultSet rs = db.query(query, params);

        while (rs.hasNext()) {
            OResult item = rs.next();
            System.out.println("friend: " + item.getProperty("name"));
        }

        rs.close();
    }
}
