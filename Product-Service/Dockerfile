FROM openjdk:12
VOLUME /tmp
ADD ./target/Product-Service-0.0.1-SNAPSHOT.jar Product-Service.jar
ENTRYPOINT ["java","-jar","/Product-Service.jar"]