package database;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Load {
    // Using document API
    static boolean isDeleteData = false;
    static boolean isLoadData = true;
    static OrientGraphFactory factory;
    static OrientGraph graph;
    static String rootDir = "../";
    static String dataDir = rootDir + "data/processed/";

    public static void main(String[] args) {
        // CONTEXT: Graph of people who are friends
        String dbname = "app_sample";
        String user = "root";
        String password = "root";
        String dbUser = "root";
        String dbPassword = "root";
        // Established a connection with the server
        factory = new OrientGraphFactory("remote:localhost/" + dbname, user, password).setupPool(1, 10);
        graph = factory.getTx();
        // Established a connection with the database - open a database session

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-deleteData")) {
                    isDeleteData = true;
                }
                if (args[i].equals("-noLoadData")) {
                    isLoadData = false;
                }
            }
        }

        if (isDeleteData) {
            deleteData();
        }

        if (isLoadData) {
            createData();
        }
        graph.shutdown();
        factory.close();
        System.out.println("Done");
    }

    private static void deleteData() {
        for (Vertex v : graph.getVertices()) {
            graph.removeVertex(v);
        }
        graph.commit();
    }

    private static void createData() {
        createTitles();
        createWorkers();
        createWorkersRoles();
        createTitlesAwards();
        createWorkersAwards();
    }


    private static void createTitles() {
        String path = dataDir + "titles_info.csv";
        // tconst,titleType,primaryTitle,originalTitle,isAdult,startYear,endYear,runtimeMinutes,genres,averageRating,numVotes,wiki_film,wiki_series,wiki_country,wiki_genres,wiki_producer_company,wiki_location
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                String tid = values[0];
                String type = values[1];
                String name = values[2];
                int startYear = Integer.parseInt(values[5]);
//                int endYear = Integer.parseInt(values[6]);
                String duration = values[7];
                String genres = values[8];
                String wikiSeries = values[12];
                String wikiCountries[] = values[13].split(";");
                String wikiGenres = values[14];
                String wikiProducerCompany = values[15];

                Iterable<Vertex> foundTitles = graph.getVertices("Title.tid", tid);
                if (foundTitles.iterator().hasNext()) {
                    //already created
                    continue;
                }
                Vertex title = graph.addVertex("class:Title");
                title.setProperty("tid", tid);
                title.setProperty("name", name);
                if (!duration.equals("\\N")) {
                    title.setProperty("duration", Double.parseDouble(duration));
                }
                title.setProperty("startYear", startYear);
//                title.setProperty("endYear", endYear);
                title.setProperty("productionCompany", wikiProducerCompany);
                title.setProperty("n_comments", 0);
                title.setProperty("n_votes", 0);
                title.setProperty("awards", new EntityAwards().toString());

                // Get genres
                ArrayList<String> genresArray = new ArrayList<>(Arrays.asList(genres.split(";", -1)));
                ArrayList<String> wikiGenresArray = new ArrayList<>(Arrays.asList(wikiGenres.split(";", -1)));
                List<String> filmGenres = new ArrayList<>();
                genresArray.removeIf(String::isEmpty);
                wikiGenresArray.removeIf(String::isEmpty);
                filmGenres.addAll(genresArray);
                filmGenres.addAll(wikiGenresArray);

                // Genres
                for (String genre : filmGenres) {
                    Iterable<Vertex> foundGenres = graph.getVertices("Genre.name", genre);
                    Vertex genreVertex;
                    if (!foundGenres.iterator().hasNext()) {
                        // Create genre
                        genreVertex = graph.addVertex("class:Genre");
                        genreVertex.setProperty("name", genre);
                    } else {
                        genreVertex = foundGenres.iterator().next();
                    }
                    graph.addEdge(null, title, genreVertex, "HasGenre");
                }

                // Series
                if (!wikiSeries.equals("")) {
                    Iterable<Vertex> foundSeries = graph.getVertices("Series.name", wikiSeries);
                    Vertex seriesVertex;
                    if (!foundSeries.iterator().hasNext()) {
                        // Create series
                        seriesVertex = graph.addVertex("class:Series");
                        seriesVertex.setProperty("name", wikiSeries);
                    } else {
                        seriesVertex = foundSeries.iterator().next();
                    }
                    graph.addEdge(null, title, seriesVertex, "PartOfSeries");
                }

                // Country
                if (wikiCountries.length > 0) {
                    for (String country : wikiCountries) {
                        Iterable<Vertex> foundCountry = graph.getVertices("Country.name", country);
                        Vertex countryVertex;
                        if (!foundCountry.iterator().hasNext()) {
                            // Create country
                            countryVertex = graph.addVertex("class:Country");
                            countryVertex.setProperty("name", country);
                        } else {
                            countryVertex = foundCountry.iterator().next();
                        }
                        graph.addEdge(null, title, countryVertex, "RecordedIn");
                    }
                }
                graph.commit();


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createWorkers() {
        String path = dataDir + "names_info.csv";
//        nconst,primaryName,birthYear,deathYear,primaryProfession,knownForTitles,wiki_name,wiki_country
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                String nid = values[0];
                String name = values[1];
                String birthYear = values[2];
                String wikiCountry = values[7];

                Iterable<Vertex> foundWorkers = graph.getVertices("Worker.nid", nid);
                if (foundWorkers.iterator().hasNext()) {
                    //already created
                    continue;
                }
                Vertex worker = graph.addVertex("class:Worker");
                worker.setProperty("nid", nid);
                worker.setProperty("name", name);
                if (!birthYear.equals("\\N")) {
                    worker.setProperty("birthYear", Integer.parseInt(birthYear));
                }

                // Country
                if (!wikiCountry.equals("")) {
                    Iterable<Vertex> foundCountry = graph.getVertices("Country.name", wikiCountry);
                    Vertex countryVertex;
                    if (!foundCountry.iterator().hasNext()) {
                        // Create country
                        countryVertex = graph.addVertex("class:Country");
                        countryVertex.setProperty("name", wikiCountry);
                    } else {
                        countryVertex = foundCountry.iterator().next();
                    }
                    graph.addEdge(null, worker, countryVertex, "Nationality");
                }
                graph.commit();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createWorkersRoles() {
        createWorkersPrincipals();
        createWorkersCrew();
    }

    private static void createWorkersPrincipals() {

        String path = dataDir + "principals.csv";
//        tconst,ordering,nconst,category,job,characters
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                String tid = values[0];
                String nid = values[2];
                String category = values[3];
                String characters[] = values[5].split(";", -1);

                Iterable<Vertex> foundTitles = graph.getVertices("Title.tid", tid);
                if (!foundTitles.iterator().hasNext()) {
                    //title not found
                    throw new RuntimeException("Title not found");
                }
                Vertex title = foundTitles.iterator().next();

                Iterable<Vertex> foundWorkers = graph.getVertices("Worker.nid", nid);
                if (!foundWorkers.iterator().hasNext()) {
                    //worker not found
                    throw new RuntimeException("Worker not found");
                }
                Vertex worker = foundWorkers.iterator().next();


                if (category.equals("actor")) {
                    Edge role = graph.addEdge(null, title, worker, "Character");
                    role.setProperty("name", characters[0]);
                } else {
                    Edge role = graph.addEdge(null, title, worker, "Crew");
                    role.setProperty("type", category);
                }
                graph.commit();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createWorkersCrew(Vertex title, String type, String nid) {
        Iterable<Vertex> foundWorkers = graph.getVertices("Worker.nid", nid);
        if (!foundWorkers.iterator().hasNext()) {
            //worker not found
            throw new RuntimeException("Worker not found");
        }
        Vertex worker = foundWorkers.iterator().next();

        Edge role = graph.addEdge(null, title, worker, "Crew");
        role.setProperty("type", type);
    }

    private static void createWorkersCrew() {
        String path = dataDir + "crew.csv";
//        tconst,directors,writers
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                String tid = values[0];
                String[] directors = values[1].split(";");
                String[] writers = values[2].split(";");

                Iterable<Vertex> foundTitles = graph.getVertices("Title.tid", tid);
                if (!foundTitles.iterator().hasNext()) {
                    //title not found
                    throw new RuntimeException("Title not found");
                }
                Vertex title = foundTitles.iterator().next();

                if (!directors[0].equals("\\N")) {
                    for (String nid : directors) {
                        createWorkersCrew(title, "director", nid);
                    }
                }
                if (!writers[0].equals("\\N")) {
                    for (String nid : writers) {
                        createWorkersCrew(title, "writer", nid);
                    }
                }
                graph.commit();

            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void createTitlesAwards() {
        String path = dataDir + "titles_awards.csv";
//        tconst,name_award,received_on,imbd_id
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                String tid = values[0];
                String nameAward = values[1];
                String receivedOn = values[2];

                Iterable<Vertex> foundTitles = graph.getVertices("Title.tid", tid);
                if (!foundTitles.iterator().hasNext()) {
                    //title not found
                    throw new RuntimeException("Title not found");
                }
                Vertex title = foundTitles.iterator().next();
                EntityAwards awards = new EntityAwards(title.getProperty("awards"));
                awards.addAward(new EntityAward(nameAward, receivedOn));
                title.setProperty("awards", awards.toString());
                graph.commit();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createWorkersAwards() {
        String path = dataDir + "names_awards.csv";
//        nconst,name_award,received_on,imbd_id
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                String nid = values[0];
                String nameAward = values[1];
                String receivedOn = values[2];
                String tid = values[3];

                if (tid.equals("")) {
                    continue;
                }

                Iterable<Vertex> foundWorkers = graph.getVertices("Worker.nid", nid);
                if (!foundWorkers.iterator().hasNext()) {
                    //worker not found
                    throw new RuntimeException("Worker not found");
                }
                Vertex worker = foundWorkers.iterator().next();

                Iterable<Vertex> foundTitles = graph.getVertices("Title.tid", tid);
                if (!foundTitles.iterator().hasNext()) {
                    //title not found
                    throw new RuntimeException("Title not found");
                }

                Vertex title = foundTitles.iterator().next();

                OrientVertex orientWorker = (OrientVertex) worker;
                OrientVertex orientTitle = (OrientVertex) title;
                Iterable<Edge> edges = orientWorker.getEdges(orientTitle, Direction.IN);
                Edge edge;
                EntityAwards awards;
                if (edges.iterator().hasNext()) {
                    edge = edges.iterator().next();
                    awards = new EntityAwards(edge.getProperty("awards"));
                } else {
                    edge = graph.addEdge(null, worker, title, "Role");
                    awards = new EntityAwards();
                }
                awards.addAward(new EntityAward(nameAward, receivedOn));
                edge.setProperty("awards", awards.toString());
//                for(Edge edge : edges){
//                    if(edge.getVertex(Direction.IN).equals(title)){
//                        EntityAwards awards = new EntityAwards(edge.getProperty("awards"));
//                        awards.addAward(new EntityAward(nameAward, receivedOn));
//                        edge.setProperty("awards", awards.toString());
//                    }
//                }
                graph.commit();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}