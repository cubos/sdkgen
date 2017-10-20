#!/usr/bin/env bash

NAME=$2
VERSION=1.0.$3

cp $1 /root/stage/api.ts
cd /root/stage

export PATH=$(npm bin):$PATH
json -I -f package.json -e 'this.name="@cubos/'$NAME'"'
npm version $VERSION || true
ls
echo "tsc will run"
tsc
echo "tsc runned"
ls
echo "babel will run"
babel api.js -o api.js
echo "babel runned"
ls
echo "YAY"
cat .gitignore
npm publish
