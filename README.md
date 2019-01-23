[![Build Status](https://travis-ci.org/luksi1/docker-certificate-builder.svg?branch=master)](https://travis-ci.org/luksi1/docker-certificate-builder)

# Server certificate builder

## Description

Create a server certificate chain with an intermediate, root, and certificate revocation list.

## Use cases

This is primarily intended to create certificates in a CI/CD pipeline. This image can create a certificate chain and a CRL on the fly so that fake certificates can be used for testing.

On the other hand, the certificates here are completely valid, so feel free to use them as you wish!

## Usage

### Up and running

The following command will produce a default certificate "ci.test" in a "certs" folder in your current directory:

```
docker run -t -v $(pwd)/certs:/export luksi1/certificate-builder:latest
```

Use any of the following environment variables to produce a certificate more to your liking...

### Environment variables

##### SERVER_NAME
Default: ci.test

##### ROOT_SUBJECT
Default: /C=SE/ST=Vastra Gotaland/L=Gothenburg/O=MyTest/CN=root.test/

##### INTERMEDIATE_SUBJECT
Default: /C=SE/ST=Vastra Gotaland/L=Gothenburg/O=MyTest/CN=intermediate.test/

##### SERVER_SUBJECT
Default: /C=SE/ST=Vastra Gotaland/L=Gothenburg/O=MyTest/CN=${SERVER_NAME}/

##### CRL_DISTRIBUTION_POINTS
http://${SERVER_NAME}.crl.pem

##### ROOT_KEY_PASSWORD
Default: Abcd1234

##### INTERMEDIATE_KEY_PASSWORD
Default: Abcd1234

##### SERVER__KEY_PASSWORD
Default: Abcd1234

##### DEFAULT_CRL_DAYS
Default: 1

##### DEFAULT_DAYS
Default: 1

##### SERVER_CERT_EXPIRATION_DAYS
Default: 1

##### INTERMEDIATE_CERT_EXPIRATION_DAYS
Default: 1

##### ROOT_CERT_EXPIRATION_DAYS
Default: 1

