#!/usr/bin/env bash

NAME=$(basename `git rev-parse --show-toplevel`)
VERSION=1.0.$(git rev-list --count HEAD)

cp $1 /root/stage/api.ts
cd /root/stage
ls -lA
export PATH=$(npm bin):$PATH
json -I -f package.json -e 'this.name="@cubos/'$NAME'"'
npm version $VERSION || true
tsc
babel api.js -o api.js
npm publish
pwd
ls -lA
