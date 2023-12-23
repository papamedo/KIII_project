FROM maven:3.6.3-openjdk-15 AS build
WORKDIR /home/app

# dependencies
COPY pom.xml /home/app/
RUN mvn verify clean --fail-never

# package
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml clean package -DskipTests
EXPOSE 9876
ENTRYPOINT [ "java", "-jar", "/home/app/target/web_project.jar" ]