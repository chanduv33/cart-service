FROM azul/zulu-openjdk-alpine:11.0.4
COPY target/cart-service-0.0.1-SNAPSHOT.jar cart-service.jar
ENTRYPOINT ["java","-jar","cart-service.jar"]