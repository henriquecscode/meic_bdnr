package xyz.dassiorleando.springbootorientdb.config;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Basic OrientDB configuration class
 * To configure and provide the bean to inject later for database interactions
 * @author dassiorleando
 */
@Configuration
public class OrientDBConfiguration {

    // The orientdb installation folder
    private static String orientDBFolder = System.getenv("ORIENTDB_HOME");
    String dbname = "app_sample";
    String user = "root";
    String password = "rootpwd";
    String dbUser = "root";
    String dbPassword = "rootpwd";
    /**
     * Connect and build the OrientDB Bean for Document API
     * @return
     */
    @Bean
    public ODatabaseDocumentTx orientDBfactory() {
        ODatabaseDocumentTx db = new ODatabaseDocumentTx("remote:localhost/app_sample")
                .open("root", "rootpwd");
        return db;
    }
//        return new ODatabaseDocumentTx("remote:localhost/app_sample")
//                .open("root", "rootpwd");

        // or

//        return new ODatabaseDocumentTx("remote:localhost/alibabacloudblog")
//                .open("admin", "admin"); // To avoid the concurrent process access with on the same local server as the administrator

    @Bean
    public OrientGraphFactory orientDBGraphfactory(){
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/" + dbname, dbUser, dbPassword);
        return factory;
    }

}

