FROM golang:1.9-alpine as confd

ARG CONFD_VERSION=0.16.0

ADD https://github.com/kelseyhightower/confd/archive/v${CONFD_VERSION}.tar.gz /tmp/

RUN mkdir -p /go/src/github.com/kelseyhightower/confd
WORKDIR /go/src/github.com/kelseyhightower/confd 

RUN apk add --no-cache \
    bzip2=1.0.6-r6 \
    make=4.2.1-r2 && \
    tar --strip-components=1 -zxf /tmp/v${CONFD_VERSION}.tar.gz && \
    go install github.com/kelseyhightower/confd && \
    rm -rf /tmp/v${CONFD_VERSION}.tar.gz

FROM alpine:3.8

COPY --from=confd /go/bin/confd /usr/local/bin/confd
RUN chmod +x /usr/local/bin/confd

COPY files/confd /etc/confd

RUN apk update && \
apk add --no-cache openssl=1.0.2q-r0 && \
rm -rf /var/cache/apk/*

COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

RUN mkdir -p /workspace/config
WORKDIR /workspace

ENTRYPOINT ["sh","/docker-entrypoint.sh"]
