FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /home/app
COPY pom.xml /home/app
RUN  mvn dependency:go-offline
COPY src /home/app/src
RUN mvn install -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/smartbustransport-0.0.1-SNAPSHOT.jar /usr/local/lib/smartbustransport-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/smartbustransport-0.0.1-SNAPSHOT.jar"]