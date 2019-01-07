#!/usr/bin/env bash

NAME=$2
VERSION=$3

cp $1 /root/stage/api.ts
cd /root/stage

export PATH=$(npm bin):$PATH
json -I -f package.json -e 'this.name="@cubos/'$NAME'"'
npm version $VERSION || true
tsc
babel api.js -o api.js
npm publish
