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
import java.time.Duration;
import java.util.*;


public class CreateSocialNetwork {
    static Random random = new Random(1);
    static int NUMBER_USERS = 20;
    static float FILMS_INTERACTED = 0.5f;
    static float FILMS_WATCHED = 0.5f;
    static float USERS_FOLLOWED = 0.15f;
    static float COMMENT_PROBABILITY = 0.5f;
    static float VOTE_PROBABILITY = 0.5f;
    // Using document API
    static String dbname = "app_sample";
    static String user = "root";
    static String password = "rootpwd";
    static String dbUser = "root";
    static String dbPassword = "rootpwd";
    static OrientGraphFactory factory;
    static OrientGraph graph;
    static String rootDir = "../";
    static String dataDir = rootDir + "data/comments/";
    //read file dataDir/comments.txt
    static BufferedReader br;

    public static void main(String[] args) {
        boolean isDeleteData = false;
        boolean isLoadData = true;
        // CONTEXT: Graph of people who are friends

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
            System.out.println("Deleted Data");
        }

        if (isLoadData) {
            createData();
        }
        graph.shutdown();
        factory.close();
        System.out.println("Done");
    }

    private static void deleteData() {
        for (Vertex v : graph.getVerticesOfClass("User")) {
            graph.removeVertex(v);
        }
        graph.commit();
    }

    private static void createData() {
        createUsers();
    }

    private static void createUsers() {
        float r;
        long startTime = System.currentTimeMillis() - Duration.ofDays(365).toMillis();
        long endTime = System.currentTimeMillis();

        List<CountryFrequency> countryFrequencies = getCountryDistribution();
        List<Integer> countryAbsolutes = getCountryTotals(countryFrequencies);
        int countryWorkerTotal = countryAbsolutes.get(0);
        int countryStudentTotal = countryAbsolutes.get(1);

        List<Vertex> users = new ArrayList<>();
        for (int i = 0; i < NUMBER_USERS; i++) {
            // Get films_interacted amount of vertices from db
//            create user
            OrientVertex user = graph.addVertex("class:User");
            user.setProperty("name", "User" + i);
            user.setProperty("username", "username" + i);
            user.setProperty("password", "password" + i);

            Iterable<Vertex> films = graph.getVerticesOfClass("Title");
            for (Vertex v : films) {
                r = random.nextFloat();
                if (r < FILMS_INTERACTED) {
                    r = random.nextFloat();
                    if (r < FILMS_WATCHED) {
                        Date date = getDate(startTime, endTime);
                        Edge watched = user.addEdge("Watched", v);

                        r = random.nextFloat();
                        if (r < COMMENT_PROBABILITY) {
                            String comment = getNextComment();
                            watched.setProperty("comment", comment);
                        }
                        r = random.nextFloat();
                        if (r < VOTE_PROBABILITY) {
                            int vote = random.nextInt(10) + 1;
                            watched.setProperty("vote", vote);
                        }
                        int vote = random.nextInt(10) + 1;
                        watched.setProperty("date", date);
                    } else {
                        //toWatch
                        Edge toWatch = user.addEdge("ToWatch", v);
                    }
                }
            }

            // Country
            Vertex country = getCountryFromWorkerDistribution(countryFrequencies, countryWorkerTotal);
            Edge livesIn = user.addEdge("Nationality", country);

            users.add(user);
        }

        graph.commit();

        //Follow users
        for (int i = 0; i < users.size(); i++) {
            Vertex v = users.get(i);
            for (int j = 0; j < users.size(); j++) {
                if (i == j) {
                    continue;
                }
                r = random.nextFloat();
                if (r < USERS_FOLLOWED) {
                    Vertex userToFollow = users.get(j);
                    Edge follows = v.addEdge("Follows", userToFollow);
                    follows.setProperty("since", getDate(startTime, endTime));

                }
            }
        }


        // changes accumulates in the film side
        for (Vertex v : graph.getVerticesOfClass("Title")) {
            //Count User with watched edge
            //get watched edges
            int commentCount = 0;
            int voteCount = 0;
            Iterable<Edge> watchedEdges = v.getEdges(Direction.IN, "Watched");
            Iterator<Edge> watchedEdgesIterator = watchedEdges.iterator();
//            int size = iteratorSize(watchedEdges.iterator());
            while (watchedEdgesIterator.hasNext()) {
                Edge edge = watchedEdgesIterator.next();
                String comment = edge.getProperty("comment");
                Integer vote = edge.getProperty("vote");

                if (comment != null) {
                    commentCount++;
                }
                if (vote != null) {
                    voteCount++;
                }
            }
            v.setProperty("n_comments", commentCount);
            v.setProperty("n_votes", voteCount);
        }

        graph.commit();

        closeComments();

    }

    private static List<CountryFrequency> getCountryDistribution() {
        List<CountryFrequency> countryFrequency = new ArrayList<>();
        Iterable<Vertex> countries = graph.getVerticesOfClass("Country");

        for (Vertex v : countries) {
            Iterable<Vertex> workers = v.getVertices(Direction.IN, "Nationality");
            int workersSize = iteratorSize(workers.iterator());
            Iterable<Vertex> films = v.getVertices(Direction.IN, "RecordedIn");
            int filmsSize = iteratorSize(films.iterator());
            countryFrequency.add(new CountryFrequency(v, workersSize, filmsSize));
        }
        return countryFrequency;
    }

    private static List<Integer> getCountryTotals(List<CountryFrequency> countryFrequencies) {
        int workerTotal = 0;
        int filmTotal = 0;
        for (CountryFrequency cf : countryFrequencies) {
            workerTotal += cf.absoluteWorkerFrequency;
            filmTotal += cf.absoluteFilmFrequency;
        }
        return new ArrayList<>(Arrays.asList(workerTotal, filmTotal));
    }

    private static Vertex getCountryFromWorkerDistribution(List<CountryFrequency> countryFrequencies, int total) {
        int random = new Random().nextInt(total);
        int count = 0;
        for (CountryFrequency cf : countryFrequencies) {
            count += cf.absoluteWorkerFrequency;
            if (count >= random) {
                return cf.country;
            }
        }
        return countryFrequencies.get(countryFrequencies.size() - 1).country;
    }

    private static Vertex getCountryFromFilmDistribution(List<CountryFrequency> countryFrequencies, int total) {
        int random = new Random().nextInt(total);
        int count = 0;
        for (CountryFrequency cf : countryFrequencies) {
            count += cf.absoluteFilmFrequency;
            if (count >= random) {
                return cf.country;
            }
        }
        return countryFrequencies.get(countryFrequencies.size() - 1).country;
    }

    private static String getNextComment() {

        if (br == null) {
            try {
                br = new BufferedReader(new FileReader(dataDir + "comments.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        }
        try {
            String line = br.readLine();
            br.readLine();
            return line;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Date getDate(long startTime, long endTime) {
        return new Date(startTime + (long) (random.nextFloat() * (endTime - startTime)));
    }


    private static void closeComments() {
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int iteratorSize(Iterator<?> it) {
        int i = 0;
        while (it.hasNext()) {
            it.next();
            i++;
        }
        return i;
    }

}

class CountryFrequency {
    public Vertex country;
    public int absoluteWorkerFrequency;
    public int absoluteFilmFrequency;

    CountryFrequency(Vertex country, int absoluteWorkerFrequency, int absoluteFilmFrequency) {
        this.country = country;
        this.absoluteWorkerFrequency = absoluteWorkerFrequency;
        this.absoluteFilmFrequency = absoluteFilmFrequency;
    }
}