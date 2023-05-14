FROM maven:3.9.1-amazoncorretto-17

WORKDIR /usr/src/backend

COPY . .

RUN mvn dependency:resolve
RUN mvn clean package -DskipTests 
RUN mvn compile assembly:single

# Start Backend
CMD ["sh", "docker_run.sh"]
