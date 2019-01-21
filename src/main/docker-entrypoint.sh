#!/bin/sh

/usr/local/bin/confd -onetime -backend env
cat /usr/local/bin/create.certificates.sh
/usr/local/bin/create.certificates.sh
