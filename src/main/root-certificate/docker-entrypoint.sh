#!/bin/sh 

ROOT_SUBJECT=${ROOT_SUBJECT:-"/C=SE/ST=Vastra Gotaland/L=Gothenburg/O=MyTest/CN=root.test/"}
export ROOT_SUBJECT
ROOT_KEY_PASSWORD=${ROOT_KEY_PASSWORD:-"Abcd1234"}
export ROOT_KEY_PASSWORD
ROOT_CERT_EXPIRATION_DAYS=${ROOT_CERT_EXPIRATION_DAYS:-"1"}
export ROOT_CERT_EXPIRATION_DAYS
ROOT_KEY_PERMISSIONS=${ROOT_KEY_PERMISSIONS:-"400"}
export ROOT_KEY_PERMISSIONS

/usr/local/bin/confd -onetime -backend env
if /ssl/create.certificates.sh; then
  echo "certificate created"
fi
