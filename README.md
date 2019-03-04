[![Build Status](https://travis-ci.org/luksi1/docker-certificate-builder.svg?branch=master)](https://travis-ci.org/luksi1/docker-certificate-builder)

# Server certificate builder

## Description

Create a server certificate chain with an intermediate, root, and certificate revocation list.

## Use cases

This is primarily intended to create certificates in a CI/CD pipeline. This image can create a certificate chain and a CRL on the fly so that fake certificates can be used for testing.

On the other hand, the certificates here are completely valid, so feel free to use them as you wish!

## Usage

This repository builds four images, which can create a root, intermediate, server/client certificate, and a certificate bundler image so that you can bundle Java keystore files and PKCS12 files. The idea is that you can mix and match however you see fit.

### Up and running

##### Creating a certificate chain

The following command will produce a root, intermediate, and a server certificate that will be valid for one day. Adjust the ROOT_SUBJECT, INTERMEDIATE_SUBJECT, and/or SERVER_SUBJECT to fit your needs.

```
docker run -t -v $(pwd)/certs:/export -e ROOT_SUBJECT=/C=SE/ST=Vastra Gotaland/L=Gothenburg/O=dummy/CN=root.test -e ROOT_KEY_PASSWORD=Abcd1234 luksi1/root-certificate-builder:latest
docker run -t -v $(pwd)/certs:/export -e INTERMEDIATE_SUBJECT=/C=SE/ST=Vastra Gotaland/L=Gothenburg/O=dummy/CN=intermediate.test -e INTERMEDIATE_KEY_PASSWORD=Abcd1234 -e ROOT_KEY_PASSWORD=Abcd1234 luksi1/intermediate-certificate-builder:latest
docker run -t -v $(pwd)/certs:/export -e SERVER_SUBJECT=/C=SE/ST=Vastra Gotaland/L=Gothenburg/O=dummy/CN=dummy.test -e SERVER_KEY_PASSWORD=Abcd1234 INTERMEDIATE_KEY_PASSWORD=Abcd1234 -e luksi1/server-certificate-builder:latest
```

##### Producing a Java keystore and PKCS12 bundle

The following would produce a truststore with the aforementioned root and intermediate, as well as a Java keystore with your server certificate and key. Additionally, a PKCS12 bundle would created.

```
docker run -t -v $(pwd)/certs:/export -e SERVER_NAME=dummy.test -e CREATE_TRUSTSTORE=true -e CREATE_KEYSTORE=true luksi1/certificate-bundler:latest
```

### Root certificate options

You can create a root certificate with following environment variables:

##### ROOT_SUBJECT
This needs need to be specified in forward slash notation, ie: /C=US/ST=New York/L=New York/CN=Acme

##### ROOT_KEY_PASSWORD
Default: Abcd1234

##### ROOT_CERT_EXPIRATION_DAYS
The amount of days that your root certificate will be valid
Default: 1

### Intermediate certificate options

##### INTERMEDIATE_SUBJECT
This needs need to be specified in forward slash notation, ie: /C=US/ST=New York/L=New York/CN=Acme

##### INTERMEDIATE_KEY_PASSWORD
Default: Abcd1234

##### CRL_EXPIRATION_DAYS
The amount of days your Certificate Revocation List will be valid
Default: 1

##### INTERMEDIATE_CERT_EXPIRATION_DAYS
The amount of days that your intermediate certificate will be valid
Default: 1

### Server/Client certificate options

##### SERVER_SUBJECT
This needs need to be specified in forward slash notation, ie: /C=US/ST=New York/L=New York/CN=Acme

##### SERVER_KEY_PASSWORD
Default: Abcd1234

##### SERVER_CERT_EXPIRATION_DAYS
The amount of days that your server certificate will be valid
Default: 1

### Certificate bundler

This image provides two functions.

1. Java keystore/truststore under "/export/jks" (optional)
2. A PKCS12 bundle under "/export/pkcs12"

##### SERVER_NAME (required)
The server name which will be used to create a unique P12 file under the directory pkcs12

##### CREATE_TRUSTSTORE
Set to "true" to create a Java truststore with your intermediate and root certificates

If this is not set, no truststore will be created.

##### CREATE_KEYSTORE
Set to "true" to create a Java keystore with your server certificates key pair.

##### ROOT_CERTIFICATE_FILE_PATH
The path to the root certificate. This will be used to insert the root certificate into the truststore

Default: /export/certs/root.crt

##### INTERMEDIATE_CERTIFICATE_FILE_PATH
The path to the intermediate certificate. This will be used to insert the root certificate into the truststore

Default: /export/certs/intermediate.crt

##### TRUSTSTORE_PASSWORD
The Java truststore password

Default: Abcd1234

##### KEYSTORE_PASSWORD
The Java keystore password

Default: Abcd1234

##### SERVER_KEY_PASSWORD
The server's certificate key password

Default: Abcd1234

##### ROOT_TRUSTSTORE_ALIAS
The alias name of the root certificate in the truststore.

Default: root

##### INTERMEDIATE_TRUSTSTORE_ALIAS
The alias name of the intermediate certificate in the truststore.

Default: intermediate

##### SERVER_KEYSTORE_ALIAS
The alias name of the server certificate in the keystore.

Default: server
