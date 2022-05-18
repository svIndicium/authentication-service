FROM gcr.io/distroless/java:11
COPY build/libs/*-all.jar app.jar
CMD ["app.jar"]