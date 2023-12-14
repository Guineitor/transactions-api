FROM azul/zulu-openjdk-alpine:21
COPY target/*.jar app.jar
ENV PORT 8080

CMD /usr/bin/java -jar /app.jar --server.port=${PORT}

EXPOSE ${PORT}