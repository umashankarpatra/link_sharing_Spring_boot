FROM openjdk:8
EXPOSE 8081
ADD target/link-sharing-docker.jar link-sharing-docker.jar
ENTRYPOINT ["java","-jar","/link-sharing-docker.jar"]