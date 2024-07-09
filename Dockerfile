FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/springionic-0.0.1-SNAPSHOT.jar springionic-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/springionic-0.0.1-SNAPSHOT.jar"]