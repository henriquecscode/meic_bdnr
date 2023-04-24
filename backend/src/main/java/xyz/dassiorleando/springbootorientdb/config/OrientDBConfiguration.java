package xyz.dassiorleando.springbootorientdb.config;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
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

    /**
     * Connect and build the OrientDB Bean for Document API
     * @return
     */
    @Bean
    public ODatabaseDocumentTx orientDBfactory() {
        ODatabaseDocumentTx db = new ODatabaseDocumentTx("remote:localhost/app_sample")
                .open("root", "rootpwd");
        return db;
//        return new ODatabaseDocumentTx("remote:localhost/app_sample")
//                .open("root", "rootpwd");

        // or

//        return new ODatabaseDocumentTx("remote:localhost/alibabacloudblog")
//                .open("admin", "admin"); // To avoid the concurrent process access with on the same local server as the administrator
    }

}

