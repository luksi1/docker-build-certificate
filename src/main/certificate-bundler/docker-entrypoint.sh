#!/bin/sh

if [ ${SERVER_NAME}x == "x" ]; then
  echo "You must indicate a SERVER_NAME"
  exit 1
fi

if [ ${TRUSTSTORE_FILE_PATH}x == "x" ]; then
  mkdir /export/jks || true
  TRUSTSTORE_FILE_PATH="/export/jks/truststore.jks"
  export TRUSTSTORE_FILE_PATH
fi

if [ ${KEYSTORE_FILE_PATH}x == "x" ]; then
  mkdir /export/jks || true
  KEYSTORE_FILE_PATH="/export/jks/keystore.jks"
  export KEYSTORE_FILE_PATH
fi

if [ ${SERVER_KEY_FILE_PATH}x == "x" ]; then
  SERVER_KEY_FILE_PATH="/export/private/${SERVER_NAME}.key"
  export SERVER_KEY_FILE_PATH
fi

if [ ${SERVER_CERTIFICATE_FILE_PATH}x == "x" ]; then
  SERVER_CERTIFICATE_FILE_PATH="/export/certs/${SERVER_NAME}.crt"
  export SERVER_CERTIFICATE_FILE_PATH
fi

if [ ${SERVER_PKCS12_FILE_PATH}x == "x" ]; then
  mkdir /export/pkcs12 || true
  SERVER_PKCS12_FILE_PATH="/export/pkcs12/${SERVER_NAME}.p12"
  export SERVER_PKCS12_FILE_PATH
fi

if [ ${ROOT_CERTIFICATE_FILE_PATH}x == "x" ]; then
  ROOT_CERTIFICATE_FILE_PATH="/export/certs/root.crt"
  export ROOT_CERTIFICATE_FILE_PATH
fi

if [ ${INTERMEDIATE_CERTIFICATE_FILE_PATH}x == "x" ]; then
  INTERMEDIATE_CERTIFICATE_FILE_PATH="/export/certs/intermediate.crt"
  export INTERMEDIATE_CERTIFICATE_FILE_PATH
fi

/usr/local/bin/confd -onetime -backend env

if /start.sh; then
  echo "bundles created"
fi
