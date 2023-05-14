java -cp target/orientdb-filmfriend-0.0.1-SNAPSHOT-jar-with-dependencies.jar database.DocumentSchema -dropDb
java -cp target/orientdb-filmfriend-0.0.1-SNAPSHOT-jar-with-dependencies.jar database.Load -deleteData
java -cp target/orientdb-filmfriend-0.0.1-SNAPSHOT-jar-with-dependencies.jar database.CreateSocialNetwork -deleteData
java -jar target/orientdb-filmfriend-0.0.1-SNAPSHOT.jar