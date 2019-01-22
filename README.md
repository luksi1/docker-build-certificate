# Server certificate builder

## Usage

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

