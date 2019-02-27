[![Build Status](https://travis-ci.org/luksi1/docker-certificate-builder.svg?branch=master)](https://travis-ci.org/luksi1/docker-certificate-builder)

# Server certificate builder

## Description

Create a server certificate chain with an intermediate, root, and certificate revocation list.

## Use cases

This is primarily intended to create certificates in a CI/CD pipeline. This image can create a certificate chain and a CRL on the fly so that fake certificates can be used for testing.

On the other hand, the certificates here are completely valid, so feel free to use them as you wish!

## Usage

This repository builds three images, which can create a root, intermediate, and server/client certificate. The idea is that you can mix and match however you see fit.

### Up and running

The following command will produce a root, intermediate, and a server certificate that will be valid for one day. Adjust the ROOT_SUBJECT, INTERMEDIATE_SUBJECT, and/or SERVER_SUBJECT to fit your needs.

```
docker run -t -v $(pwd)/certs:/export -e ROOT_SUBJECT=/C=SE/ST=Vastra Gotaland/L=Gothenburg/O=dummy/CN=root.test -e ROOT_KEY_PASSWORD=Abcd1234 luksi1/root-certificate-builder:latest
docker run -t -v $(pwd)/certs:/export -e INTERMEDIATE_SUBJECT=/C=SE/ST=Vastra Gotaland/L=Gothenburg/O=dummy/CN=intermediate.test -e INTERMEDIATE_KEY_PASSWORD=Abcd1234 -e ROOT_KEY_PASSWORD=Abcd1234 luksi1/intermediate-certificate-builder:latest
docker run -t -v $(pwd)/certs:/export -e SERVER_SUBJECT=/C=SE/ST=Vastra Gotaland/L=Gothenburg/O=dummy/CN=dummy.test -e SERVER_KEY_PASSWORD=Abcd1234 INTERMEDIATE_KEY_PASSWORD=Abcd1234 -e luksi1/server-certificate-builder:latest
```

### Root certificate options

You can create a root certificate with following environment variables:

##### ROOT_SUBJECT
This needs need to be specified in forward slash notation, ie: /C=US/ST=New York/L=New York/CN=Acme

##### ROOT_KEY_PASSWORD
Default: Abcd1234

##### DEFAULT_CRL_DAYS
The default amount of days for the Certificate Revocation List
Default: 1

##### DEFAULT_DAYS
The default days for a certificate to be valid.
Default: 1

##### ROOT_CERT_EXPIRATION_DAYS
The amount of days that your root certificate will be valid
Default: 1

### Intermediate certificate options

##### INTERMEDIATE_SUBJECT
This needs need to be specified in forward slash notation, ie: /C=US/ST=New York/L=New York/CN=Acme

##### INTERMEDIATE_KEY_PASSWORD
Default: Abcd1234

##### DEFAULT_CRL_DAYS
The default amount of days for the Certificate Revocation List
Default: 1

##### DEFAULT_DAYS
The default days for a certificate to be valid.
Default: 1

##### INTERMEDIATE_CERT_EXPIRATION_DAYS
The amount of days that your intermediate certificate will be valid
Default: 1

### Server/Client certificate options

##### SERVER_SUBJECT
This needs need to be specified in forward slash notation, ie: /C=US/ST=New York/L=New York/CN=Acme

##### SERVER_KEY_PASSWORD
Default: Abcd1234

##### DEFAULT_CRL_DAYS
The default amount of days for the Certificate Revocation List
Default: 1

##### DEFAULT_DAYS
The default days for a certificate to be valid.
Default: 1

##### SERVER_CERT_EXPIRATION_DAYS
The amount of days that your server certificate will be valid
Default: 1

