FROM azul/zulu-openjdk-alpine:21
COPY target/*.jar app.jar

ENV PORT 8080
ENV SPRING_AMQP_DESERIALIZATION_TRUST_ALL true

CMD /usr/bin/java -jar /app.jar --server.port=${PORT}

EXPOSE ${PORT}