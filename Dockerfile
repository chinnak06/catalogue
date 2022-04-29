FROM lolhens/baseimage-openjre
ADD target/catalogue-service.jar catalogue-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "catalogue-service.jar"]
