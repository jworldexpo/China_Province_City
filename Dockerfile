FROM maven:3.8.4-openjdk-8 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY 2019年1月中华人民共和国县以上行政区划代码.json /app/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]