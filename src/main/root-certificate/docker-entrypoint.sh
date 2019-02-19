#!/bin/sh 

ROOT_SUBJECT=${ROOT_SUBJECT:-"/C=SE/ST=Vastra Gotaland/L=Gothenburg/O=MyTest/CN=root.test/"}
export ROOT_SUBJECT
ROOT_KEY_PASSWORD=${ROOT_KEY_PASSWORD:-"Abcd1234"}
export ROOT_KEY_PASSWORD
DEFAULT_CRL_DAYS=${DEFAULT_CRL_DAYS:-"1"}
export DEFAULT_CRL_DAYS
DEFAULT_DAYS=${DEFAULT_DAYS:-"1"}
export DEFAULT_DAYS
ROOT_CERT_EXPIRATION_DAYS=:${ROOT_CERT_EXPIRATION_DAYS:-"1"}
export ROOT_CERT_EXPIRATION_DAYS

/usr/local/bin/confd -onetime -backend env
/usr/local/bin/create.certificates.sh
