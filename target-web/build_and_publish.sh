#!/usr/bin/env bash

export PATH=$(npm bin):$PATH

NAME=$(basename `git rev-parse --show-toplevel`)
VERSION=1.0.$(git rev-list --count HEAD)

cp $1 /root/api.ts
cd /root

json -I -f package.json -e 'this.name=\"@cubos/'$NAME'\"'
npm version $VERSION || true
tsc
babel api.js -o api.js
npm publish
