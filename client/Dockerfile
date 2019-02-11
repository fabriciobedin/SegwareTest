FROM node
LABEL maintainer="Fabricio Bedin <fabricioobedin@gmail.com>"

COPY . *.crt /usr/local/share/ca-certificates/
RUN update-ca-certificates

COPY . /usr/src/client
WORKDIR /usr/src/client

RUN npm config set registry http://registry.npmjs.org/
ENTRYPOINT npm install && npm start