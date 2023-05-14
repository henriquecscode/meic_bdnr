call mvn dependency:resolve
call mvn clean package -DskipTests
call mvn compile assembly:single