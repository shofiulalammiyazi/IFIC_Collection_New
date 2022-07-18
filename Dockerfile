FROM openjdk:8
ADD target/lms-gateway.jar lms-gateway
EXPOSE 8080
ENTRYPOINT ["java","-jar","lms-gateway.jar"]
