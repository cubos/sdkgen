#!/usr/bin/env bash

NAME=$(basename `git rev-parse --show-toplevel`)
VERSION=1.0.$2

cp $1 /root/stage/api.ts
cd /root/stage

export PATH=$(npm bin):$PATH
json -I -f package.json -e 'this.name="@cubos/'$NAME'"'
npm version $VERSION || true
tsc
babel api.js -o api.js
npm publish
