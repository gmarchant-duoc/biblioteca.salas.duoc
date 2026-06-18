FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace

# copy maven files first for dependency caching
COPY pom.xml mvnw mvnw.cmd ./

# copy sources
COPY src ./src

RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
ARG JAR_FILE=/workspace/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
