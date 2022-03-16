FROM lolhens/baseimage-openjre
ADD target/catalogue-service.jar catalogue-service.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "catalogue-service.jar"]
