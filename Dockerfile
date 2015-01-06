FROM java:openjdk-8-jdk
ADD . /usr/src/aquaimoncul
WORKDIR /usr/src/aquaimoncul
RUN ./gradlew run
EXPOSE 8080
CMD ["gradlew", "run"]