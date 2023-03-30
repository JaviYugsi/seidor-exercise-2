##Build stage
FROM maven:3.8.7-openjdk-18-slim
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -Ue -f /home/app/pom.xml clean package


##Package stage
FROM openjdk:18-jre-slim
COPY --from=build /home/app/target/*.jar /usr/local/lib/app1.jar
EXPOSE 8080/tcp

ENTRYPOINT ["java", "-jar", "/usr/local/lib/app1.jar"]

