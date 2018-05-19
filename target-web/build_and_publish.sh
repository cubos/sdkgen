#!/usr/bin/env bash
set -e

NAME=$2
VERSION=1.0.$3

cp $1 /root/stage/api.ts
cd /root/stage

export PATH=$(npm bin):$PATH
json -I -f package.json -e 'this.name="@cubos/'$NAME'"'
npm version $VERSION || true
echo "[tsc-cjs] START"
tsc
echo "[tsc-cjs] END"
echo "[tsc-esmodule] START"
tsc -m es2015 --outFile api.mjs
echo "[tsc-esmodule] END"
npm publish
