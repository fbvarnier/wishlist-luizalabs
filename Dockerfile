FROM openjdk:11-jdk-slim
MAINTAINER fbvarnier
COPY target/*.jar wishlist.jar
ENTRYPOINT ["java", "-jar", "/wishlist.jar"]