#!/bin/sh

/usr/local/bin/confd -onetime -backend env
/usr/local/bin/create.certificates.sh
