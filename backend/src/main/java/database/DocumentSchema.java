package database;

import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;

public class DocumentSchema {
    // Using document API
    static ODatabaseSession db;

    public static void main(String[] args) {
        // CONTEXT: Graph of people who are friends
        String dbname = "app_sample";
        String user = "root";
        String password = "root";
        String dbUser = "root";
        String dbPassword = "root";
        // Established a connection with the server
        OrientDB orient = new OrientDB("remote:localhost", user, password, OrientDBConfig.defaultConfig());
        // Established a connection with the database - open a database session
    //        System.out.println("Printing orient");
    //        System.out.println(orient);
    //        System.out.println("Printing orient worked");
        boolean dropDb = false;
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-dropDb")) {
                    dropDb = true;
                }
            }
        }
        if (dropDb) {
            if (orient.exists(dbname)) {
                orient.drop(dbname);
                System.out.println("Dropped DB");
            }
        }
        if (!orient.exists(dbname)) {
            orient.create(dbname, ODatabaseType.PLOCAL);
        }
        db = orient.open(dbname, dbUser, dbPassword);

        createSchema();

        db.close();
        orient.close();
        System.out.println("Done schema");
    }

    private static void createSchema() {

        // Nodes

        createTitleSchema();
        createGenreSchema();
        createSeriesSchema();
        createCountrySchema();
        createPersonSchema();
        createWorkerSchema();
        createUserSchema();


        //Edges

        createHasGenreSchema();
        createPartOfSeriesSchema();
        createWatchedSchema();
        createToWatchSchema();
        createFollowsSchema();
        createNationalitySchema();
        createRecordedInSchema();
        createRoleSchema();
        createCrewSchema();
        createCharacterSchema();

        createFullTextIndex();
    }


    private static void createTitleSchema() {
        OClass title = db.getClass("Title");

        if (title != null) {
            return;
        }
        title = db.createVertexClass("Title");

        // Same for tid
        if (title.getProperty("tid") == null) {
            title.createProperty("tid", OType.STRING);
            title.createIndex("Title_tid_index", OClass.INDEX_TYPE.UNIQUE, "tid");
        }

        if (title.getProperty("name") == null) {
            title.createProperty("name", OType.STRING);
            title.createIndex("Title_name_index", OClass.INDEX_TYPE.FULLTEXT, "name");
        }

        // same for summary
        if (title.getProperty("summary") == null) {
            title.createProperty("summary", OType.STRING);
            title.createIndex("Title_summary_index", OClass.INDEX_TYPE.FULLTEXT, "summary");
        }

        //same for duration but int
        if (title.getProperty("duration") == null) {
            title.createProperty("duration", OType.INTEGER);
        }

        //same for startYear but int
        if (title.getProperty("startYear") == null) {
            title.createProperty("startYear", OType.INTEGER);
//            title.createIndex("Title_startYear_index", OClass.INDEX_TYPE.NOTUNIQUE, "startYear");
        }

//        //same for endYear but int
//        if (title.getProperty("endYear") == null) {
//            title.createProperty("endYear", OType.INTEGER);
//            title.createIndex("Title_endYear_index", OClass.INDEX_TYPE.NOTUNIQUE, "endYear");
//        }

        //same for production company but list of strings
        if (title.getProperty("productionCompany") == null) {
            title.createProperty("productionCompany", OType.EMBEDDEDLIST, OType.STRING);
        }

        // same for awards but list of new type
        if (title.getProperty("awards") == null) {
//            OClass titleAward = db.getClass("TitleAward");
//            if (titleAward == null) {
//                titleAward = db.createClass("TitleAward");
//                titleAward.createProperty("name", OType.STRING);
//                titleAward.createProperty("year", OType.DATE);
//
//            }
//            title.createProperty("awards", OType.EMBEDDEDLIST, OType.STRING);
            title.createProperty("awards", OType.STRING);
        }
        if (title.getProperty("n_comments") == null) {
            title.createProperty("n_comments", OType.INTEGER);
        }

        if (title.getProperty("n_votes") == null) {
            title.createProperty("n_votes", OType.INTEGER);
        }


    }

    private static void createGenreSchema() {
        OClass genres = db.getClass("Genre");

        if (genres != null) {
            return;
        }
        genres = db.createVertexClass("Genre");

        if (genres.getProperty("name") == null) {
            genres.createProperty("name", OType.STRING);
            genres.createIndex("Genres_name_index", OClass.INDEX_TYPE.UNIQUE, "name");
        }
    }

    private static void createSeriesSchema() {
        OClass series = db.getClass("Series");

        if (series != null) {
            return;
        }
        series = db.createVertexClass("Series");

        if (series.getProperty("name") == null) {
            series.createProperty("name", OType.STRING);
            series.createIndex("Series_name_index", OClass.INDEX_TYPE.UNIQUE, "name");
        }

        if (series.getProperty("description") == null) {
            series.createProperty("description", OType.STRING);
        }
    }

    private static void createCountrySchema() {
        OClass country = db.getClass("Country");

        if (country != null) {
            return;
        }

        country = db.createVertexClass("Country");

        if (country.getProperty("name") == null) {
            country.createProperty("name", OType.STRING);
            country.createIndex("Country_name_index", OClass.INDEX_TYPE.UNIQUE, "name");
        }
    }

    private static void createPersonSchema() {
        OClass person = db.getClass("Person");

        if (person != null) {
            return;
        }

        person = db.createVertexClass("Person");

        if (person.getProperty("name") == null) {
            person.createProperty("name", OType.STRING);
        }
    }


    private static void createWorkerSchema() {
        OClass worker = db.getClass("Worker");

        if (worker != null) {
            return;
        }

        worker = db.createClass("Worker", "Person");

        if (worker.getProperty("nid") == null) {
            worker.createProperty("nid", OType.STRING);
            worker.createIndex("Worker_nid_index", OClass.INDEX_TYPE.UNIQUE, "nid");
        }
        if (worker.getProperty("name") == null) {
            worker.createProperty("name", OType.STRING);
        }

        if (worker.getProperty("birthYear") == null) {
            worker.createProperty("birthYear", OType.INTEGER);
        }

        if (worker.getProperty("deathYear") == null) {
            worker.createProperty("deathYear", OType.INTEGER);
        }

    }

    private static void createUserSchema() {

        OClass user = db.getClass("User");

        if (user != null) {
            return;
        }

        user = db.createClass("User", "Person");

        if (user.getProperty("name") == null) {
            user.createProperty("name", OType.STRING);
        }

        //username
        if (user.getProperty("username") == null) {
            user.createProperty("username", OType.STRING);
            user.createIndex("User_username_index", OClass.INDEX_TYPE.UNIQUE, "username");
        }
        //password
        if (user.getProperty("password") == null) {
            user.createProperty("password", OType.STRING);
        }

    }

    private static void createHasGenreSchema() {
        OClass hasGenre = db.getClass("HasGenre");

        if (hasGenre != null) {
            return;
        }
        hasGenre = db.createEdgeClass("HasGenre");

    }

    private static void createPartOfSeriesSchema() {
        OClass partOfSeries = db.getClass("PartOfSeries");

        if (partOfSeries != null) {
            return;
        }

        partOfSeries = db.createEdgeClass("PartOfSeries");

        if (partOfSeries.getProperty("film_number") == null) {
            partOfSeries.createProperty("film_number", OType.INTEGER);
            partOfSeries.createIndex("partOfSeries_film_number_index", OClass.INDEX_TYPE.UNIQUE, "film_number");
        }
    }

    private static void createWatchedSchema() {
        OClass watched = db.getClass("Watched");

        if (watched != null) {
            return;
        }

        watched = db.createEdgeClass("Watched");

        if (watched.getProperty("vote") == null) {
            watched.createProperty("vote", OType.INTEGER);
        }

        if (watched.getProperty("comment") == null) {
            watched.createProperty("comment", OType.STRING);
            watched.createIndex("Watched_comment_index", OClass.INDEX_TYPE.FULLTEXT, "comment");
        }

        if (watched.getProperty("date") == null) {
            watched.createProperty("date", OType.DATE);
        }

    }

    private static void createToWatchSchema() {
        OClass toWatch = db.getClass("ToWatch");

        if (toWatch != null) {
            return;
        }

        toWatch = db.createEdgeClass("ToWatch");
    }

    private static void createFollowsSchema() {
        OClass follows = db.getClass("Follows");

        if (follows != null) {
            return;
        }

        follows = db.createEdgeClass("Follows");

        //data property
        if (follows.getProperty("since") == null) {
            follows.createProperty("since", OType.DATE);
        }
    }

    private static void createNationalitySchema() {
        //Person to Country
        OClass nationality = db.getClass("Nationality");

        if (nationality != null) {
            return;
        }

        nationality = db.createEdgeClass("Nationality");
    }

    private static void createRecordedInSchema() {
        OClass recordedIn = db.getClass("RecordedIn");

        if (recordedIn != null) {
            return;
        }

        recordedIn = db.createEdgeClass("RecordedIn");
    }

    private static void createRoleSchema() {
        OClass role = db.getClass("Role");

        if (role != null) {
            return;
        }

        role = db.createEdgeClass("Role");

        if (role.getProperty("role") == null) {
//            OClass workerAward = db.getClass("WorkerAward");
//            if (workerAward == null) {
//                workerAward = db.createClass("WorkerAward");
//                workerAward.createProperty("name", OType.STRING);
//                workerAward.createProperty("year", OType.INTEGER);
//            }
//            role.createProperty("awards", OType.EMBEDDEDLIST, workerAward);
            role.createProperty("awards", OType.STRING);
        }
    }

    private static void createCrewSchema() {
        OClass crew = db.getClass("Crew");

        if (crew != null) {
            return;
        }

        crew = db.createClass("Crew", "Role");

        if (crew.getProperty("type") == null) {
            crew.createProperty("type", OType.STRING);
        }
    }

    private static void createCharacterSchema() {
        OClass actor = db.getClass("Character");

        if (actor != null) {
            return;
        }

        actor = db.createClass("Character", "Role");

        if (actor.getProperty("name") == null) {
            actor.createProperty("name", OType.STRING);
        }
    }

    private static void createFullTextIndex() {
        String Title_name_index_query = "create index Title.name on Title (name) FULLTEXT ENGINE LUCENE METADATA {\"allowLeadingWildcard\": true}";
        String Watched_comment_index_query = "create index Watched.comment on Watched (comment) FULLTEXT ENGINE LUCENE METADATA {\"allowLeadingWildcard\": true}";

        try {
            db.command(Title_name_index_query);
            db.command(Watched_comment_index_query);
        } catch (Exception e) {
            System.out.println("Index already exists");
        }
    }

}
