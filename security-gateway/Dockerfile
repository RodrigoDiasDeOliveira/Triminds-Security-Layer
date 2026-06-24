

FROM maven:3.9-eclipse-temurin-21 AS build
ARG MODULE=security-gateway
WORKDIR /workspace
COPY pom.xml ./
COPY shared ./shared
COPY ${MODULE} ./${MODULE}
RUN mvn -q -pl shared,${MODULE} -am -DskipTests package

FROM eclipse-temurin:21-jre-alpine
ARG MODULE=security-gateway
ENV MODULE=${MODULE}
WORKDIR /app
COPY --from=build /workspace/${MODULE}/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
