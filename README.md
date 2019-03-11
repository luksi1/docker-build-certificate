[![Build Status](https://travis-ci.org/luksi1/docker-certificate-builder.svg?branch=master)](https://travis-ci.org/luksi1/docker-certificate-builder)

# Server certificate builder

## Summary

Create a server/client certificate chain with an intermediate, root, and certificate revocation list.

## Description

These images are primarily intended to create certificates in a CI/CD pipeline. This image can create a certificate chain and a CRL on the fly so that dummy certificates can be used for testing.

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

The following would produce a Java truststore with the aforementioned root and intermediate certificates, as well as a Java keystore with your server certificate and key. Additionally, a PKCS12 bundle would created.

```
docker run -t -v $(pwd)/certs:/export -e SERVER_NAME=dummy.test -e CREATE_TRUSTSTORE=true -e CREATE_KEYSTORE=true luksi1/certificate-bundler:latest
```

##### Using Fabric8's docker-maven-plugin

Set the appropriate POM "properties" variables. Additionally note the use of the user option so that you can produce a file owned by your own UID (or whatever UID you wish to own your certificate files).

```maven
  <image>
    <name>luksi1/root-certificate-builder:latest</name>
    <run>
      <user>${user.uid}</user>
      <volumes>
        <bind>
          <volume>${project.basedir}/volumes/certificates:/export</volume>
        </bind>
      </volumes>
      <env>
        <ROOT_SUBJECT>${root.subject}</ROOT_SUBJECT>
      </env>
      <wait>
        <log>certificate created</log>
        <time>10000</time>
      </wait>
    </run>
  </image>
  <image>
    <name>luksi1/intermediate-certificate-builder:latest</name>
    <run>
      <user>${user.uid}</user>
      <volumes>
        <bind>
          <volume>${project.basedir}/volumes/certificates:/export</volume>
        </bind>
      </volumes>
      <env>
        <INTERMEDIATE_SUBJECT>${intermediate.subject}</INTERMEDIATE_SUBJECT>
      </env>
      <wait>
        <log>certificate created</log>
        <time>10000</time>
      </wait>
    </run>
  </image>
  <image>
    <name>luksi1/server-certificate-builder:latest</name>
    <run>
      <user>${user.uid}</user>
      <volumes>
        <bind>
          <volume>${project.basedir}/volumes/certificates:/export</volume>
        </bind>
      </volumes>
      <env>
        <SERVER_SUBJECT>${server.subject}</SERVER_SUBJECT>
        <SERVER_NAME>${server.name}</SERVER_NAME>
      </env>
      <wait>
        <log>certificate created</log>
        <time>10000</time>
      </wait>
    </run>
  </image>
  <image>
    <name>luksi1/certificate-bundler:latest</name>
    <run>
      <user>${user.uid}</user>
      <volumes>
        <bind>
          <volume>${project.basedir}/volumes/certificates:/export</volume>
        </bind>
      </volumes>
      <env>
        <SERVER_NAME>${server.name}</SERVER_NAME>
        <CREATE_TRUSTSTORE>force</CREATE_TRUSTSTORE>
        <CREATE_KEYSTORE>force</CREATE_KEYSTORE>
      </env>
      <wait>
        <log>bundles created</log>
        <time>120000</time>
      </wait>
    </run>
  </image>
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

##### ROOT_KEY_PERMISSIONS
The file permissions for the root key file.
Default: 400


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

##### INTERMEDIATE_KEY_PERMISSIONS
The file permissions for the intermediate key
Default: 400

### Server/Client certificate options

##### SERVER_SUBJECT
This needs need to be specified in forward slash notation, ie: /C=US/ST=New York/L=New York/CN=Acme

##### SERVER_KEY_PASSWORD
Default: Abcd1234

##### SERVER_CERT_EXPIRATION_DAYS
The amount of days that your server certificate will be valid
Default: 1

##### SERVER_KEY_PERMISSIONS
The file permissions for the server key file.
Default: 400

### Certificate bundler

This image provides two functions.

1. Java keystore/truststore under "/export/jks" (optional)
2. A PKCS12 bundle under "/export/pkcs12"

##### SERVER_NAME (required)
The server name which will be used to create a unique P12 file under the directory pkcs12

##### CREATE_TRUSTSTORE
Set to "true" to initialize a Java truststore with your intermediate and root certificates. Alternatively, you can set this to "force" to force remove the truststore first before re-creating.

If this is not set, no truststore will be initialized

##### CREATE_KEYSTORE
Set to "true" to initialize a Java keystore with your server certificates key pair. Alternatively, you can set this to "force" to force remove the keystore first before re-creating.

If this is not set, no keystore will be initialized.

##### ROOT_CERTIFICATE_FILE_PATH
The path to the root certificate. This will be used to insert the root certificate into the truststore.
Default: /export/certs/root.crt

##### INTERMEDIATE_CERTIFICATE_FILE_PATH
The path to the intermediate certificate. This will be used to insert the root certificate into the truststore.
Default: /export/certs/intermediate.crt

##### TRUSTSTORE_PASSWORD
The Java truststore password.
Default: Abcd1234

##### KEYSTORE_PASSWORD
The Java keystore password.
Default: Abcd1234

##### SERVER_KEY_PASSWORD
The server's certificate key password. The default is no password.
Default: empty

##### ROOT_TRUSTSTORE_ALIAS
The alias name of the root certificate in the truststore.
Default: root

##### INTERMEDIATE_TRUSTSTORE_ALIAS
The alias name of the intermediate certificate in the truststore.
Default: intermediate

##### SERVER_KEYSTORE_ALIAS
The alias name of the server certificate in the keystore.
Default: server

##### PKCS12_FILE_PATH
The file path to your PKCS12 file. This will be the PKCS12 file for your server certificate.
Default: /export/pkcs12/<SERVERNAME>.p12

##### TRUSTSTORE_FILE_PERMISSIONS
The file permissions for the Java truststore.
Default: 600

##### KEYSTORE_FILE_PERMISSIONS
The file permissions for the Java keystore.
Default: 600

##### PKCS12_FILE_PERMISSIONS
The file permissions for the PKCS12 file.
Default: 400
