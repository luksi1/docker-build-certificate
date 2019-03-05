FROM golang:1.9-alpine as confd

ARG CONFD_VERSION=0.16.0

ADD https://github.com/kelseyhightower/confd/archive/v${CONFD_VERSION}.tar.gz /tmp/

RUN mkdir -p /go/src/github.com/kelseyhightower/confd
WORKDIR /go/src/github.com/kelseyhightower/confd

RUN apk add --no-cache \
    bzip2=1.0.6-r6 \
    make=4.2.1-r2 && \
    tar --strip-components=1 -zxf "/tmp/v${CONFD_VERSION}.tar.gz" && \
    go install github.com/kelseyhightower/confd && \
    rm -rf "/tmp/v${CONFD_VERSION}.tar.gz"

FROM alpine:3.9

ENV TRUSTSTORE_PASSWORD=Abcd1234
ENV KEYSTORE_PASSWORD=Abcd1234
ENV SERVER_KEY_PASSWORD=Abcd1234
ENV ROOT_TRUSTSTORE_ALIAS=root
ENV INTERMEDIATE_TRUSTSTORE_ALIAS=intermediate
ENV SERVER_KEYSTORE_ALIAS=server

COPY --from=confd /go/bin/confd /usr/local/bin/confd
COPY files/confd /etc/confd
COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN apk add --no-cache \
    openssl \
    sed \
    openjdk8 \
    expect && \
    mkdir /ssl && \
    chmod +x /usr/local/bin/confd /docker-entrypoint.sh && \
    chmod 777 /ssl

WORKDIR /ssl
VOLUME /export

ENTRYPOINT /docker-entrypoint.sh
