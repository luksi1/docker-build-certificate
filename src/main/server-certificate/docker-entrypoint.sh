#!/bin/sh 

if [ "${SERVER_SUBJECT}x" != "x" ]; then
  echo $SERVER_SUBJECT
  SERVER_NAME=`echo $SERVER_SUBJECT | sed -r -n 's/.*CN=(.*)($|\/.*)/\1/p'`
  echo "server subject"
  echo $SERVER_NAME
elif [ "${COMMON_NAME}x" != "x" ]; then
  SERVER_NAME=${COMMON_NAME}
  echo "common name"
  echo $SERVER_NAME
else
  echo "You must indicate a SERVER_SUBJECT or a COMMON_NAME"
  exit 1
fi

INTERMEDIATE_KEY_PASSWORD=${INTERMEDIATE_KEY_PASSWORD:-"Abcd1234"}
export INTERMEDIATE_KEY_PASSWORD
SERVER_KEY_PASSWORD=${SERVER_KEY_PASSWORD:-"Abcd1234"}
export SERVER_KEY_PASSWORD
DEFAULT_CRL_DAYS=${DEFAULT_CRL_DAYS:-"1"}
export DEFAULT_CRL_DAYS
DEFAULT_DAYS=${DEFAULT_DAYS:-"1"}
export DEFAULT_DAYS
SERVER_CERT_EXPIRATION_DAYS=${SERVER_CERT_EXPIRATION_DAYS:-"1"}
export SERVER_CERT_EXPIRATION_DAYS

/usr/local/bin/confd -onetime -backend env
/usr/local/bin/create.certificates.sh

if [[ $? -eq 0 ]]; then
  echo "certificate created"
fi
