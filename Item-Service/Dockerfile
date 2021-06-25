FROM openjdk:12
VOLUME /tmp
EXPOSE 8002
ADD ./target/Item-Service-0.0.1-SNAPSHOT.jar Item-Service.jar
ENTRYPOINT ["java","-jar","/Item-Service.jar"]