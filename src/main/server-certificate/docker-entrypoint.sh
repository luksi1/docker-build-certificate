#!/bin/sh 
# shellcheck disable=SC2034

if [ "${SERVER_SUBJECT}x" != "x" ]; then
  SERVER_NAME=$(echo "$SERVER_SUBJECT" | sed -r -n 's/.*CN=(.*)($|\/.*)/\1/p')
  export SERVER_NAME
elif [ "${COMMON_NAME}x" != "x" ]; then
  SERVER_NAME="$COMMON_NAME"
  export SERVER_NAME
else
  echo "You must indicate a SERVER_SUBJECT or a COMMON_NAME"
  exit 1
fi

INTERMEDIATE_KEY_PASSWORD=${INTERMEDIATE_KEY_PASSWORD:-"Abcd1234"}
export INTERMEDIATE_KEY_PASSWORD
# SERVER_KEY_PASSWORD=${SERVER_KEY_PASSWORD:-"Abcd1234"}
# export SERVER_KEY_PASSWORD
SERVER_CERT_EXPIRATION_DAYS=${SERVER_CERT_EXPIRATION_DAYS:-"1"}
export SERVER_CERT_EXPIRATION_DAYS

/usr/local/bin/confd -onetime -backend env
if /ssl/create.certificates.sh; then
  echo "certificate created"
fi
