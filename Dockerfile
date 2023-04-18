FROM openjdk:17-alpine
MAINTAINER baeldung.com
COPY target/stockdetails-0.0.1-SNAPSHOT.jar stockdetails.jar
ENTRYPOINT ["java","-jar","/stockdetails.jar"]