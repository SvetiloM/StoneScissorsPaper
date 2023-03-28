FROM amazoncorretto:17-alpine-jdk
COPY target/server-1.0-SNAPSHOT.jar server-1.0-SNAPSHOT.jar
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.10.0/wait /wait
RUN chmod +x /wait
CMD /wait && java -jar /server-1.0-SNAPSHOT.jar