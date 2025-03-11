FROM maven:eclipse-temurin AS build

ENV MERCADO_PAGO_EXTERNAL_POS_ID=FASTFOOD001
ENV MERCADO_PAGO_SECRET_TOKEN=SECRET_TOKEN
ENV MERCADO_PAGO_USER_ID=

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/fast-food-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]