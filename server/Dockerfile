FROM maven
LABEL maintainer="Fabricio Bedin <fabricioobedin@gmail.com>"

COPY . *.crt /usr/local/share/ca-certificates/
RUN update-ca-certificates

COPY . /usr/src/server
WORKDIR /usr/src/server

ENTRYPOINT mvn package && cd /usr/src/server/target && java -jar server-1.0.0.jar

