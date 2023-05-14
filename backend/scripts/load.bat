call java -cp target/orientdb-filmfriend-0.0.1-SNAPSHOT-jar-with-dependencies.jar database.DocumentSchema -dropDb
call java -cp target/orientdb-filmfriend-0.0.1-SNAPSHOT-jar-with-dependencies.jar database.Load -deleteData
call java -cp target/orientdb-filmfriend-0.0.1-SNAPSHOT-jar-with-dependencies.jar database.CreateSocialNetwork -deleteData