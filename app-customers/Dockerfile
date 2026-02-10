#
# Archivo para crear la imagen
# docker build -t emil2245/app-customers:2.0.0 .
# docker push emil2245/app-customers:2.0.0
#

# entorno que va
FROM eclipse-temurin:21.0.9_10-jre-alpine-3.23

ENV LANGUAGE='en_US:en'

RUN mkdir /app
WORKDIR /app

COPY build/libs/demo.jar app.jar

CMD ["java", "-jar", "app.jar"]


