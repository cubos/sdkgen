#!/usr/bin/env bash
set -e

NAME=$2
VERSION=1.0.$3

cp $1 /root/stage/api.ts
cd /root/stage

export PATH=$(npm bin):$PATH
json -I -f package.json -e 'this.name="@cubos/'$NAME'"'
npm version $VERSION || true
npm dist-tag add @cubos/$NAME@$CI_COMMIT_REF_NAME || true
echo "[tsc] START"
tsc
echo "[tsc] END"
echo "[babel] START"
babel api.js -o api.js
echo "[babel] END"
npm publish
